package co.com.crediya.sqs;

import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.Map;

public class MensajeSQS {

    public static final String EVENTO_CAMBIO_ESTADO = "cambioEstado";
    public static final String EVENTO_REPORTAR_APROBACION= "reportarAprobacion";
    private final String queueUrlEstado = System.getenv("QUEUE_URL");
    private final String queueUrlReporte = System.getenv("QUEUE_URL_REPORTE");
    public SendMessageRequest generarMensaje(String mensaje, String evento) {


        String queueUrlElegida = EVENTO_REPORTAR_APROBACION.equals(evento)
                ? queueUrlReporte
                : queueUrlEstado;

        System.out.println("generando mensaje a--> "+queueUrlElegida);
        return SendMessageRequest.builder()
                .queueUrl(queueUrlElegida)
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
