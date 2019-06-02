package com.ooladehin.awsiot.registry.main;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ooladehin.awsiot.registry.models.PersistenceLogic;


/***
 * Default Routing Function class for use in Lambda and Step Functions Registry workflow.
 * By default this class takes no action other than routing any event as a successful event
 * that needs to be registered. This mode would be a workflow used in the case of active|passive
 * replication. In this approach only one region is writing data to the event data store and all 
 * events that are received are subsequently forwarded.
 * 
 * @author oladehin
 *
 */
public class RoutingRegistryFunction implements RequestStreamHandler {

	@Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, final Context context) 
    		throws JsonParseException, JsonMappingException, IOException {
    	
		PersistenceLogic logic = new PersistenceLogic(false);
		
		new ObjectMapper().writeValue(outputStream, logic);
	    outputStream.close();
		return;
    }

}

