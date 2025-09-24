package co.com.crediya.sqs;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public class PublicadorSQS {


    private final SqsClient sqsClient;

    public PublicadorSQS() {
        this.sqsClient = SqsClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }

    public void enviarMensaje(String mensaje, String evento) {
        MensajeSQS mensajeSQS = new MensajeSQS();
        try {
            SendMessageRequest mensajeEnviar = mensajeSQS.generarMensaje(mensaje, evento);
            SendMessageResponse result = sqsClient.sendMessage(mensajeEnviar);
            System.out.println("Publicando mensajes...: " + result.messageId());
        } catch (Exception e) {
            System.err.println("Error sending message to SQS--->: " + e.getMessage());
            throw new RuntimeException("Failed to send message to SQS", e);
        }
    }
}
