package co.com.crediya.ses;

import software.amazon.awssdk.services.ses.model.*;

public class MensajeSES {
    private static final String ASUNTO = "Notificación cambio de estado solicitud Crediya!";


    SendEmailRequest crearSolicitudCorreo(String mensaje) {
        String[] datosEnvio = mensaje.split(",");
        Destination destino = generarDestino(datosEnvio[3]);
        Message mensajeEnviar = generarMensaje(datosEnvio);

        return SendEmailRequest
                .builder()
                .source(System.getenv("SOURCE_EMAIL_SES"))
                .destination(destino)
                .message(mensajeEnviar)
                .build();

    }

    private Destination generarDestino(String email) {
        return Destination.builder()
                .toAddresses(email)
                .build();
    }


    private Message generarMensaje(String[] datosCuerpo) {
        return Message.builder()
                .subject(Content.builder().data(ASUNTO).build())
                .body(Body.builder()
                        .text(Content.builder().data(generarPlantilla(datosCuerpo)).build())
                        .build())
                .build();
    }


    private String generarPlantilla(String[] datosCuerpo) {

        return String.format(
                "Hola %s,%nTu solicitud de crédito con ID %s ha cambiado  al estado %s.%nCordialmente%nAsesores crediya!",
                datosCuerpo[0],
                datosCuerpo[1],
                datosCuerpo[2]
        );
    }
}
