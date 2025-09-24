package co.com.crediya.ses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

public class PublicadorSES {

    private static final Logger logger = LoggerFactory.getLogger(PublicadorSES.class);
    private final SesClient sesClient;
    private final MensajeSES mensajeSES;

    public PublicadorSES() {
        this.sesClient = SesClient.builder()
                .region(Region.US_EAST_2)
                .build();
        this.mensajeSES = new MensajeSES();
    }

    public void enviarMensaje(String mensaje) {
        logger.debug("Se ha recibido el siguiente mensaje para enviar por email -> {}", mensaje);
        SendEmailRequest mensajeenviar = this.mensajeSES.crearSolicitudCorreo(mensaje);
        this.sesClient.sendEmail(mensajeenviar);
        logger.debug("Se ha enviado el mensaje hacia -> {}", mensajeenviar.destination().toAddresses().stream().toList());
    }

}
