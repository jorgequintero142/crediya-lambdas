package co.com.crediya;


import co.com.crediya.sqs.PublicadorSQS;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

public class LambdaReportes implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        context.getLogger().log("invocando lamda reportes..");
        PublicadorSQS publicadorSQS =  new PublicadorSQS();
        for (SQSMessage msg : sqsEvent.getRecords()) {
            context.getLogger().log("leyendo mensaje reportes.." + msg.getBody());
            publicadorSQS.enviarMensaje(msg.getBody());
        }
        return null;
    }








}