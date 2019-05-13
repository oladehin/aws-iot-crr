package com.ooladehin.awsiot.registry.repository;

import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.model.AddThingToThingGroupRequest;
import com.amazonaws.services.iot.model.AttributePayload;
import com.amazonaws.services.iot.model.CreateThingGroupRequest;
import com.amazonaws.services.iot.model.DeleteThingGroupRequest;
import com.amazonaws.services.iot.model.InternalFailureException;
import com.amazonaws.services.iot.model.InvalidRequestException;
import com.amazonaws.services.iot.model.RemoveThingFromThingGroupRequest;
import com.amazonaws.services.iot.model.ResourceAlreadyExistsException;
import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.amazonaws.services.iot.model.ThingGroupProperties;
import com.amazonaws.services.iot.model.ThrottlingException;
import com.amazonaws.services.iot.model.UpdateThingGroupRequest;
import com.amazonaws.services.iot.model.VersionConflictException;
import com.ooladehin.awsiot.registry.models.thinggroups.ReplicateThingGroupMembershipEvent;
import com.ooladehin.awsiot.registry.models.thinggroups.ReplicateThingGroupEvent;

public class ThingGroupEventRepository {

	private final static int ARN_IDENTIFIER = 2;
	private final static int REGISTRY_NAME_INDEX = 1;
	

	private final AWSIot awsIoTClient;

	public ThingGroupEventRepository(AWSIot awsIoTClient) {
		this.awsIoTClient = awsIoTClient;
	}

	public void createThingGroupInRegistry(ReplicateThingGroupEvent request) {
		try{
			CreateThingGroupRequest createThingGroupRequest = new CreateThingGroupRequest();
			ThingGroupProperties thingGroupProperties = new ThingGroupProperties();
			AttributePayload attributePayload = new AttributePayload();
			
			attributePayload.setAttributes(request.getAttributes());
			thingGroupProperties.setThingGroupDescription(request.getDescription());
			thingGroupProperties.setAttributePayload(attributePayload);
			
			//TODO: Note there is currently a bug where the parent Group Name is being set to the
			//AWS Account Id. Leaving this commented out until this is fixed. So what will
			//happen today is the groups will be "flat"
			//if(request.getParentGroupName() != null) {
			//	createThingGroupRequest.setParentGroupName(request.getParentGroupName());
			//}
			
			createThingGroupRequest.setThingGroupName(request.getThingGroupName());		
			createThingGroupRequest.setThingGroupProperties(thingGroupProperties);
	
			awsIoTClient.createThingGroup(createThingGroupRequest);
		}catch(InternalFailureException e) {
			
		}catch(InvalidRequestException e) {
			
		}catch(ResourceAlreadyExistsException e) {
			
		}catch(ThrottlingException e) {
			
		}
		
	}

	public void updateThingGroupInRegistry(ReplicateThingGroupEvent request) {
		try{
			UpdateThingGroupRequest updateThingGroupRequest = new UpdateThingGroupRequest();
			
			//TODO: Need to log how this is handled during update to see if this should be deprecated
			//By 1 in order to have the right version in place
			//updateThingGroupRequest.setExpectedVersion(request.getVersionNumber() - 1); //TODO: This should be the previous version that is expected
			updateThingGroupRequest.setThingGroupName(request.getThingGroupName());
			ThingGroupProperties thingGroupProperties = new ThingGroupProperties();
			AttributePayload attributePayload = new AttributePayload();
			
			attributePayload.setAttributes(request.getAttributes());
			thingGroupProperties.setThingGroupDescription(request.getDescription());
			thingGroupProperties.setAttributePayload(attributePayload);
			updateThingGroupRequest.setThingGroupProperties(thingGroupProperties);
		
			awsIoTClient.updateThingGroup(updateThingGroupRequest);
		}catch(InternalFailureException e) {

		}catch(InvalidRequestException e) {
			
		}catch(ResourceNotFoundException e) {
			
		}catch(ThrottlingException e) {
			
		}catch(VersionConflictException e) {
			
		}
		
	}

	public void deleteThingGroupInRegistry(ReplicateThingGroupEvent request) {
		try{
			DeleteThingGroupRequest deleteThingGroupRequest = new DeleteThingGroupRequest();
			deleteThingGroupRequest.setExpectedVersion(request.getVersion());
			deleteThingGroupRequest.setThingGroupName(request.getThingGroupName());
			awsIoTClient.deleteThingGroup(deleteThingGroupRequest);
		}catch(InternalFailureException e) {
			
		}catch(InvalidRequestException e) {
			
		}catch(ThrottlingException e) {
			
		}catch(VersionConflictException e) {
			
		}
	}

	public Void addThingToThingGroup(ReplicateThingGroupMembershipEvent request) {

		try{
			AddThingToThingGroupRequest addThingToThingGroupRequest = new AddThingToThingGroupRequest();
			String thingGroup = parseThingGroup(request.getGroupArn());
			String thingName = parseThingName(request.getThingArn());
			
			if(thingGroup != null && thingName != null) {
				addThingToThingGroupRequest.setThingGroupName(thingGroup);
				addThingToThingGroupRequest.setThingName(thingName);
				this.awsIoTClient.addThingToThingGroup(addThingToThingGroupRequest);
			}
		}catch(Exception e) {
			
		}
		return null;
	}

	public Void removeThingFromThingGroup(ReplicateThingGroupMembershipEvent request) {
		try{
			RemoveThingFromThingGroupRequest removeThingFromThingGroupRequest = new RemoveThingFromThingGroupRequest();
			String thingGroup = parseThingGroup(request.getGroupArn());
			String thingName = parseThingName(request.getThingArn());
			
			if(thingGroup != null && thingName != null) {
				removeThingFromThingGroupRequest.setThingGroupName(thingGroup);
				removeThingFromThingGroupRequest.setThingName(thingName);
				this.awsIoTClient.removeThingFromThingGroup(removeThingFromThingGroupRequest);
			}
		}catch(Exception e) {
			
		}
		return null;
	}
	
	private String parseThingName(String thingArn) {
		String[] thingArnFields = thingArn.split("/");
		if(thingArnFields.length == ARN_IDENTIFIER) {
			return thingArnFields[REGISTRY_NAME_INDEX];
		}
		return null;
	}

	private String parseThingGroup(String groupArn) {
		String[] groupArnFields = groupArn.split("/");
		if(groupArnFields.length == ARN_IDENTIFIER) {
			return groupArnFields[REGISTRY_NAME_INDEX];
		}
		return null;
	}
}
