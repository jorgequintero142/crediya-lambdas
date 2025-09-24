package co.com.crediya.sqs;

import co.com.crediya.endeudamiento.DesicionEnum;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.Map;

public class MensajeSQS {
    private final String queueUrl = System.getenv("QUEUE_URL");

    public SendMessageRequest generarMensaje(String mensaje, String evento) {
        System.out.println("generando mensaje a--> "+queueUrl);

        return SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageAttributes(generarAtributos(evento))
                .messageBody(mensaje)
                .build();
    }

    private Map<String, MessageAttributeValue> generarAtributos(String evento) {
        return  Map.of(
                "evento", MessageAttributeValue.builder().dataType("String").stringValue(evento).build()
        );
    }
}
