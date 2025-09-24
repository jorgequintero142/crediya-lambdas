package co.com.crediya.ses;

import com.amazonaws.services.lambda.runtime.Context;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;


public class PublicadorSES {
    private final SesClient sesClient;


    public PublicadorSES() {
        this.sesClient = SesClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }

    public void sendEmail(String asunto, String emailDestino, String mensaje) {
        System.out.println("enviando email hacia "+emailDestino);
        MensajeSES mensajeSES = new MensajeSES(asunto,emailDestino,mensaje);
        sesClient.sendEmail(mensajeSES.generarMensaje());
    }



}
