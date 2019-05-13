package com.ooladehin.awsiot.registry.repository;

import java.util.Map;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.model.AttributePayload;
import com.amazonaws.services.iot.model.CreateThingRequest;
import com.amazonaws.services.iot.model.DeleteThingRequest;
import com.amazonaws.services.iot.model.InternalFailureException;
import com.amazonaws.services.iot.model.InvalidRequestException;
import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.amazonaws.services.iot.model.ServiceUnavailableException;
import com.amazonaws.services.iot.model.ThrottlingException;
import com.amazonaws.services.iot.model.UnauthorizedException;
import com.amazonaws.services.iot.model.UpdateThingRequest;
import com.amazonaws.services.iot.model.VersionConflictException;
import com.ooladehin.awsiot.registry.models.things.ReplicateThingEvent;

public class ThingEventRepository {

    private final AWSIot awsIoTClient;
	
	public ThingEventRepository(AWSIot awsIoTClient) {
		this.awsIoTClient = awsIoTClient;
	}
	
	public void deleteDeviceInRegistry(ReplicateThingEvent request) {
		try{
			DeleteThingRequest deleteThingRequest = new DeleteThingRequest();
			deleteThingRequest.setThingName(request.getThingName());
			deleteThingRequest.setExpectedVersion(request.getVersion());
			this.awsIoTClient.deleteThing(deleteThingRequest);
		}catch(ResourceNotFoundException e) {
			//The specified resource does not exist.
		}catch(InvalidRequestException e) {
			
		}catch(ThrottlingException e) {
			
		}catch(UnauthorizedException e) {
			
		}catch(ServiceUnavailableException e) {
			
		}catch(InternalFailureException e) {
			
		}catch(VersionConflictException e) {
			
		}
		
	}

	public void updateDeviceInRegistry(ReplicateThingEvent request) {
		try{
			UpdateThingRequest updateThingRequest = new UpdateThingRequest();
			updateThingRequest.setThingName(request.getThingName());
			
			AttributePayload attributePayload = createAttributeFromMap(request.getAttributes());
			if(request.getThingTypeName() != null) {
				updateThingRequest.setThingTypeName(request.getThingTypeName() );
			}
			if(attributePayload != null) {
				updateThingRequest.setAttributePayload(attributePayload);
			}
			this.awsIoTClient.updateThing(updateThingRequest);
		}catch(ResourceNotFoundException e) {
			//The specified resource does not exist.
		}catch(InvalidRequestException e) {
			
		}catch(ThrottlingException e) {
			
		}catch(UnauthorizedException e) {
			
		}catch(ServiceUnavailableException e) {
			
		}catch(InternalFailureException e) {
			
		}catch(VersionConflictException e) {
			
		}
	
	}

	public void createDeviceInRegistry(ReplicateThingEvent request) {
		try{
			CreateThingRequest createThingRequest = new CreateThingRequest();
			createThingRequest.setThingName(request.getThingName());
			
			AttributePayload attributePayload = createAttributeFromMap(request.getAttributes());
			if(attributePayload != null) {
				createThingRequest.setAttributePayload(attributePayload);
			}
			if(request.getThingTypeName() != null) {
				createThingRequest.setThingTypeName(request.getThingTypeName());
			}
			this.awsIoTClient.createThing(createThingRequest);

		}catch(ResourceNotFoundException e) {
			//The specified resource does not exist.
		}catch(InvalidRequestException e) {
			
		}catch(ThrottlingException e) {
			
		}catch(UnauthorizedException e) {
			
		}catch(ServiceUnavailableException e) {
			
		}catch(InternalFailureException e) {
			
		}catch(VersionConflictException e) {
			
		}
		
	}

	private AttributePayload createAttributeFromMap(Map<String, String> attributes) {
		if(attributes != null) {
			AttributePayload attributePayload = new AttributePayload();
			attributePayload.setAttributes(attributes);
			return attributePayload;
		}else{
			return null;
		}
	}

}
