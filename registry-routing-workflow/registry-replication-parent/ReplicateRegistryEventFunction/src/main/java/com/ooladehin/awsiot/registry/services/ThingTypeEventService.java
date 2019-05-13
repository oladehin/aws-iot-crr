package com.ooladehin.awsiot.registry.services;

import java.util.Map;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.iot.AWSIot;
import com.ooladehin.awsiot.registry.models.RegistryConstants;
import com.ooladehin.awsiot.registry.models.thingtypes.ReplicateThingTypeAssociationEvent;
import com.ooladehin.awsiot.registry.models.thingtypes.ReplicateThingTypeEvent;
import com.ooladehin.awsiot.registry.repository.ThingTypeEventRepository;

public class ThingTypeEventService {

    private final ThingTypeEventRepository thingTypeEventRepository;
    
	public ThingTypeEventService(AWSIot awsIoTClient) {
		this.thingTypeEventRepository = new ThingTypeEventRepository(awsIoTClient);
	}
	
	public void processThingTypeEvent(Map<String, AttributeValue> dynamoStreamRecord) {
		ReplicateThingTypeEvent request = ReplicateThingTypeEvent.createReplicateThingTypeRequest(dynamoStreamRecord);

		if(request.getOperation().equalsIgnoreCase(RegistryConstants.CREATED)) {
			this.thingTypeEventRepository.createThingTypeInRegistry(request);
		}else if(request.getOperation().equalsIgnoreCase(RegistryConstants.UPDATED)) {
			this.thingTypeEventRepository.deprecateThingTypeInRegistry(request);
		}else if(request.getOperation().equalsIgnoreCase(RegistryConstants.DELETED)) {
			this.thingTypeEventRepository.deleteThingTypeInRegistry(request);
		}

		return;
	}

	public void processThingTypeAssociationEvent(Map<String, AttributeValue> dynamoStreamRecord) {
		ReplicateThingTypeAssociationEvent request = ReplicateThingTypeAssociationEvent.createReplicateThingTypeAssociationRequest(dynamoStreamRecord);
		if(request.getOperation().equalsIgnoreCase(RegistryConstants.ADDED)) {
			this.thingTypeEventRepository.addThingToThingType(request);
		}else if(request.getOperation().equalsIgnoreCase(RegistryConstants.REMOVED)) {
			this.thingTypeEventRepository.removeThingFromThingType(request);
		}
				
		return;
	}

}
