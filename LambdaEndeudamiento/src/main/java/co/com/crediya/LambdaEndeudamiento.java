package co.com.crediya;

import co.com.crediya.endeudamiento.ProcesadorSolicitud;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

public class LambdaEndeudamiento implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        context.getLogger().log("invocando lamda endeudamiento..");
        ProcesadorSolicitud procesadorSolicitud = new ProcesadorSolicitud();
        for (SQSMessage msg : sqsEvent.getRecords()) {
            context.getLogger().log("leyendo mensaje endeudamiento.." + msg.getBody());
            procesadorSolicitud.procesarSolicitud(msg.getBody());
        }
        return null;
    }








}
