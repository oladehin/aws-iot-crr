package com.ooladehin.awsiot.registry.services;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.iot.AWSIot;
import com.ooladehin.awsiot.registry.models.RegistryConstants;
import com.ooladehin.awsiot.registry.models.things.ReplicateThingEvent;
import com.ooladehin.awsiot.registry.repository.ThingEventRepository;

public class ThingEventService {

    private final ThingEventRepository thingEventRepository;

	public ThingEventService(AWSIot awsIoTClient) {
		this.thingEventRepository = new ThingEventRepository(awsIoTClient);
	}
	
	public void processThingEvent(Map<String, AttributeValue> dynamoStreamRecord) {
		
		ReplicateThingEvent request = ReplicateThingEvent.createFromDynamoRecord(dynamoStreamRecord);			
		System.out.println("Process Event");
		if(request.getOperation().equalsIgnoreCase(RegistryConstants.CREATED)) {
			this.thingEventRepository.createDeviceInRegistry(request);
		}else if(request.getOperation().equalsIgnoreCase(RegistryConstants.UPDATED)) {
			this.thingEventRepository.updateDeviceInRegistry(request);
		}else if(request.getOperation().equalsIgnoreCase(RegistryConstants.DELETED)) {
			this.thingEventRepository.deleteDeviceInRegistry(request);
		}

		return;
	}
	

}
