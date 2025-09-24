package co.com.crediya;

import co.com.crediya.ses.PublicadorSES;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

public class LambdaLeerSQSAprobarRechazarSolicitud implements RequestHandler<SQSEvent, Void> {

    private final PublicadorSES publicadorSES;

    public LambdaLeerSQSAprobarRechazarSolicitud() {
        publicadorSES = new PublicadorSES();
    }

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        context.getLogger().log("Lambda AprobarRechazarSolicitud....>");
        for (SQSMessage msg : sqsEvent.getRecords()) {
            processMessage(msg, context);
        }
        return null;
    }

    private void processMessage(SQSMessage msg, Context context) {
        context.getLogger().log("procesando mensaje....>");
        try {
            publicadorSES.enviarMensaje(msg.getBody());
            context.getLogger().log("Nuevo Mensaje enviado --> " + msg.getBody());
        } catch (Exception e) {
            context.getLogger().log("Error enviando mensaje " + e.getMessage());
            throw e;
        }
    }


}