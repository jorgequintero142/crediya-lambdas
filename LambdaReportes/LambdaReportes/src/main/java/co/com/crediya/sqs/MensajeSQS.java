package co.com.crediya.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class MensajeSQS {
    private final String queueUrl = System.getenv("QUEUE_URL");
    private static final Logger logger = LoggerFactory.getLogger(PublicadorSQS.class);

    public SendMessageRequest generarMensaje(String mensaje) {
        logger.debug("generando mensaje de reporte a--> " + queueUrl);

        return SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(mensaje)
                .build();
    }
}
