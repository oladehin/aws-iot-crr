package com.ooladehin.awsiot.registry.main;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.ooladehin.awsiot.registry.models.EventTypeEnum;
import com.ooladehin.awsiot.registry.models.RegistryEvent;
import com.ooladehin.awsiot.registry.models.thinggroups.ReplicateThingGroupEvent;
import com.ooladehin.awsiot.registry.models.thinggroups.ReplicateThingGroupMembershipEvent;
import com.ooladehin.awsiot.registry.models.things.ReplicateThingEvent;
import com.ooladehin.awsiot.registry.models.thingtypes.ReplicateThingTypeAssociationEvent;
import com.ooladehin.awsiot.registry.models.thingtypes.ReplicateThingTypeEvent;
import com.ooladehin.awsiot.registry.repository.EventRegistryRepository;


public class PersistEventFunction implements RequestStreamHandler {   

	private static final String IOT_SOURCE_REGION = System.getenv("IOT_SOURCE_REGION");
	private static final String REGISTRY_EVENT_TABLENAME = System.getenv("REGISTRY_EVENT_TABLENAME");
	private final EventRegistryRepository eventRegistryRepository;
	
	/**
     * One-time initialization of resources for this Lambda function.
     */
    
	public PersistEventFunction( ) {
        this.eventRegistryRepository = new EventRegistryRepository(IOT_SOURCE_REGION, REGISTRY_EVENT_TABLENAME);
    }

    public void handleRequest(InputStream inputStream, OutputStream outputStream, final Context context) 
    		throws JsonParseException, JsonMappingException, IOException {
    	
			    
    	LambdaLogger logger = context.getLogger();
    	
    	@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap = new ObjectMapper().readValue(inputStream, Map.class);
    	logger.log(jsonMap.toString());
    	
    	if(jsonMap.containsKey(RegistryEvent.EVENT_TYPE)) {
    		String eventType = (String) jsonMap.get(RegistryEvent.EVENT_TYPE);
    		Item convertedItem = convertRegistryEvent(eventType, jsonMap, logger); 
        	logger.log("item is: " + convertedItem);
        	if(convertedItem != null) {
        		eventRegistryRepository.storeRegistryEvent(convertedItem);
        	}
    	}
		return;
    }

    private Item convertRegistryEvent(String eventType, Map<String,Object> json, LambdaLogger logger) throws JsonParseException, JsonMappingException, IOException {
		EventTypeEnum eventTypeEnum = EventTypeEnum.fromEnumValue(eventType);
		logger.log(eventTypeEnum.toString());
		Item item = null;
    	switch (eventTypeEnum) {
	        case THING:  
	        	ReplicateThingEvent replicateThingEvent = new ObjectMapper().convertValue(json, ReplicateThingEvent.class);
	        	item = replicateThingEvent.convertToDynamoItem();
	        	break;
	        case THING_GROUP:
	        	ReplicateThingGroupEvent replicateThingGroupEvent = new ObjectMapper().convertValue(json, ReplicateThingGroupEvent.class);
	        	item = replicateThingGroupEvent.convertToDynamoItem();
	        	break;
	        case THING_GROUP_MEMBERSHIP: 
	        	ReplicateThingGroupMembershipEvent replicateThingGroupMembershipEvent = new ObjectMapper().convertValue(json, ReplicateThingGroupMembershipEvent.class);
	        	item = replicateThingGroupMembershipEvent.convertToDynamoItem();
				break;
	        case THING_TYPE:  
	        	ReplicateThingTypeEvent replicateThingTypeEvent = new ObjectMapper().convertValue(json, ReplicateThingTypeEvent.class);
	        	item = replicateThingTypeEvent.convertToDynamoItem();
	            break;
	        case THING_TYPE_ASSOCIATION:  
	        	ReplicateThingTypeAssociationEvent replicateThingTypeAssociationEvent = new ObjectMapper().convertValue(json, ReplicateThingTypeAssociationEvent.class);
	        	item = replicateThingTypeAssociationEvent.convertToDynamoItem();
	            break;
	        default: 
	        	logger.log("Can Not Find Type: " + eventType);
	        	logger.log(json.toString());
	            break;
    	}
    	
    	return item;
    }

}

