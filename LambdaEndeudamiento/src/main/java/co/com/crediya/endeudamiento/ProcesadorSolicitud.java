package co.com.crediya.endeudamiento;

import co.com.crediya.aprobacion.Amortizacion;
import co.com.crediya.dto.CapacidadEndeudamientoDto;
import co.com.crediya.ses.PublicadorSES;
import co.com.crediya.sqs.PublicadorSQS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcesadorSolicitud {

    private static final Logger logger = LoggerFactory.getLogger(ProcesadorSolicitud.class);
    private final PublicadorSQS publicadorMensaje;
    private final PublicadorSES publicadorSES;
    private static final String EVENTO_CAMBIO_ESTADO = "cambioEstado";
    private static final String ASUNTO = "Aprobación credito crediya!!";
    private static final String ESTADO_APROBAR = "Aprobada";

    public ProcesadorSolicitud() {
        publicadorMensaje = new PublicadorSQS();
        publicadorSES = new PublicadorSES();
    }

    public void procesarSolicitud(String body) {
        logger.info("Calculando procesarSolicitud");
        ValidadorEndeudamiento validadorEndeudamiento = new ValidadorEndeudamiento();
        CapacidadEndeudamientoDto capacidadEndeutamiento = validadorEndeudamiento.obtenerCapacidadEndeudamiento(body);
        DesicionEnum desicion = validarCapacidadEndeudamiento(capacidadEndeutamiento);
        notificarCambioEstado(desicion, capacidadEndeutamiento.getIdSolicitud());
        notificarDesicion(capacidadEndeutamiento, desicion);
    }


    private DesicionEnum validarCapacidadEndeudamiento(CapacidadEndeudamientoDto body) {
        ValidadorEndeudamiento validadorEndeudamiento = new ValidadorEndeudamiento();

        DesicionEnum desicion = validadorEndeudamiento.validaCapacidadEndeudamiento(body);
        logger.debug("Se ha tomado la desicion solicitud {} {} ", body.getIdSolicitud(), desicion.getEstado());
        return desicion;
    }

    private void notificarCambioEstado(DesicionEnum desicion, int idSolicitud) {
        logger.debug("Notificar CambioEstado");
        String messageBody = String.format("%d,%d", idSolicitud, desicion.getCodigo());
        publicadorMensaje.enviarMensaje(messageBody, EVENTO_CAMBIO_ESTADO);
    }

    private void notificarDesicion(CapacidadEndeudamientoDto capacidadEndeutamiento, DesicionEnum desicion) {
        logger.debug("Notificar decision  {}", desicion.getEstado());

        if (ESTADO_APROBAR.equals(desicion.getEstado())) {
            logger.debug("aprobando  decision OK");
            String planPagos = generarPlanillaPlanPagos(capacidadEndeutamiento, desicion);
            logger.debug("enviando email hacia {}", capacidadEndeutamiento.getEmail());
            publicadorSES.sendEmail(ASUNTO, capacidadEndeutamiento.getEmail(), planPagos);
        } else {
            logger.debug("No es necesario enviar email");
        }
    }

    private String generarPlanillaPlanPagos(CapacidadEndeudamientoDto capacidadEndeutamiento, DesicionEnum desicion) {
        Amortizacion amortizacion = new Amortizacion();
        StringBuilder sbPlanPagos = new StringBuilder();
        String introduccion = String.format("Hola %s, tu solicitud con id %s ha sido %s; Te compartimos el plan de pagos.%n",
                capacidadEndeutamiento.getNombre(),
                capacidadEndeutamiento.getIdSolicitud(),
                desicion.getEstado());
        sbPlanPagos.append(introduccion);

        String planPago = amortizacion.obtenerPlanPago(capacidadEndeutamiento.getMonto(),
                capacidadEndeutamiento.getTasaInteres(), capacidadEndeutamiento.getPlazo());
        sbPlanPagos.append(String.format("%s", planPago));
        return sbPlanPagos.toString();
    }
}
