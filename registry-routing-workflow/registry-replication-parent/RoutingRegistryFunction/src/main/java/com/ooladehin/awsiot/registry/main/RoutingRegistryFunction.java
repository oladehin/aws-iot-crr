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


public class RoutingRegistryFunction implements RequestStreamHandler {

	@Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, final Context context) 
    		throws JsonParseException, JsonMappingException, IOException {
    	
		//Parse DirectiveRequest
		
		//Invoke the directive for a device
		//AlexaResponse alexaResponse = this.alexaDirectiveService.invokeAlexaDirectiveWithCommand(directiveRequest);

		PersistenceLogic logic = new PersistenceLogic(false);
		
		new ObjectMapper().writeValue(outputStream, logic);
	    outputStream.close();
		return;
    }

}

