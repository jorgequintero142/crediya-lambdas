package co.com.crediya.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public class PublicadorSQS {

    private static final Logger logger = LoggerFactory.getLogger(PublicadorSQS.class);
    private final SqsClient sqsClient;
    private static final String PREFIX_SQS= "reporte->";
    public PublicadorSQS() {
        this.sqsClient = SqsClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }

    public void enviarMensaje(String mensaje) {
        MensajeSQS mensajeSQS = new MensajeSQS();
        try {
            SendMessageRequest mensajeEnviar = mensajeSQS.generarMensaje(PREFIX_SQS.concat(mensaje));
            SendMessageResponse result = sqsClient.sendMessage(mensajeEnviar);
            logger.debug("Se publicó el mensaje...: {} " , result.messageId());
        } catch (Exception e) {
            logger.error("Error sending message to SQS--->: {}", e.getMessage());
            throw new RuntimeException("Failed to send message to SQS", e);
        }
    }
}
