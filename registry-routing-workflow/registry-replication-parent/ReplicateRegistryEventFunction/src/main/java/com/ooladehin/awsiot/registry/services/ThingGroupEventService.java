package com.ooladehin.awsiot.registry.services;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.iot.AWSIot;
import com.ooladehin.awsiot.registry.models.RegistryConstants;
import com.ooladehin.awsiot.registry.models.thinggroups.ReplicateThingGroupMembershipEvent;
import com.ooladehin.awsiot.registry.models.thinggroups.ReplicateThingGroupEvent;
import com.ooladehin.awsiot.registry.repository.ThingGroupEventRepository;

public class ThingGroupEventService {
	
	private final ThingGroupEventRepository thingGroupEventRepository;

	public ThingGroupEventService(AWSIot awsIoTClient) {
		this.thingGroupEventRepository = new ThingGroupEventRepository(awsIoTClient);
	}

	public void processGroupMembershipEvent(Map<String, AttributeValue> dynamoStreamRecord) {
		
		ReplicateThingGroupMembershipEvent request = ReplicateThingGroupMembershipEvent.createReplicateThingGroupMembershipRequest(dynamoStreamRecord);
		
		if(request.getOperation().equalsIgnoreCase(RegistryConstants.ADDED)) {
			 this.thingGroupEventRepository.addThingToThingGroup(request);
		}else if(request.getOperation().equalsIgnoreCase(RegistryConstants.REMOVED)) {
			 this.thingGroupEventRepository.removeThingFromThingGroup(request);
		}
	}

	public void processGroupEvent(Map<String, AttributeValue> dynamoStreamRecord) {
		
		ReplicateThingGroupEvent request = ReplicateThingGroupEvent.createReplicateThingGroupRequest(dynamoStreamRecord);
		
		if(request.getOperation().equalsIgnoreCase(RegistryConstants.CREATED)) {
			this.thingGroupEventRepository.createThingGroupInRegistry(request);
		}else if(request.getOperation().equalsIgnoreCase(RegistryConstants.UPDATED)) {
			this.thingGroupEventRepository.updateThingGroupInRegistry(request);
		}else if(request.getOperation().equalsIgnoreCase(RegistryConstants.DELETED)) {
			this.thingGroupEventRepository.deleteThingGroupInRegistry(request);
		}
	}
	
}
