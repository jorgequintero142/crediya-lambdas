package co.com.crediya.ses;

import software.amazon.awssdk.services.ses.model.*;

public class MensajeSES {
    private final String emailDestino;
    private final String mensaje;
    private final String asunto;


    public MensajeSES(String asunto, String emailDestino,String mensaje ) {
        this.asunto = asunto;
        this.emailDestino = emailDestino;
        this.mensaje = mensaje;

    }

    public SendEmailRequest generarMensaje() {

        Destination destination = Destination.builder()
                .toAddresses(this.emailDestino)
                .build();

        Message message = Message.builder()
                .subject(Content.builder().data(this.asunto).build())
                .body(Body.builder()
                        .text(Content.builder().data(this.mensaje).build())
                        .build())
                .build();

        return SendEmailRequest.builder()
                .source("jorgequintero142@gmail.com")
                .destination(destination)
                .message(message)
                .build();
    }
}