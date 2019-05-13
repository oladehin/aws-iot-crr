package com.ooladehin.awsiot.registry.repository;

import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.model.CreateThingTypeRequest;
import com.amazonaws.services.iot.model.DeleteThingTypeRequest;
import com.amazonaws.services.iot.model.DeprecateThingTypeRequest;
import com.amazonaws.services.iot.model.InternalException;
import com.amazonaws.services.iot.model.InternalFailureException;
import com.amazonaws.services.iot.model.InvalidRequestException;
import com.amazonaws.services.iot.model.ResourceAlreadyExistsException;
import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.amazonaws.services.iot.model.ServiceUnavailableException;
import com.amazonaws.services.iot.model.ThingTypeProperties;
import com.amazonaws.services.iot.model.ThrottlingException;
import com.amazonaws.services.iot.model.UnauthorizedException;
import com.amazonaws.services.iot.model.UpdateThingRequest;
import com.amazonaws.services.iot.model.VersionConflictException;
import com.ooladehin.awsiot.registry.models.thingtypes.ReplicateThingTypeAssociationEvent;
import com.ooladehin.awsiot.registry.models.thingtypes.ReplicateThingTypeEvent;

public class ThingTypeEventRepository {

    private final AWSIot awsIoTClient;

	public ThingTypeEventRepository(AWSIot awsIoTClient) {
		this.awsIoTClient = awsIoTClient;
	}

	public void createThingTypeInRegistry(ReplicateThingTypeEvent request) {
		try{
			CreateThingTypeRequest createThingTypeRequest = new CreateThingTypeRequest();
			ThingTypeProperties thingTypeProperties = new ThingTypeProperties();
			
			thingTypeProperties.setSearchableAttributes(request.getSearchableAttributes());
			thingTypeProperties.setThingTypeDescription(request.getDescription());
			createThingTypeRequest.setThingTypeName(request.getThingTypeName());
			createThingTypeRequest.setThingTypeProperties(thingTypeProperties);
			
			this.awsIoTClient.createThingType(createThingTypeRequest);
		}catch(InvalidRequestException e) {
			
		}catch(ThrottlingException e) {
			
		}catch(UnauthorizedException e) {
			
		}catch(ServiceUnavailableException e) {
			
		}catch(InternalFailureException e) {
			
		}catch(ResourceAlreadyExistsException e) {
			
		}
	}

	public void deleteThingTypeInRegistry(ReplicateThingTypeEvent request) {
		
		try{
			DeleteThingTypeRequest deleteThingTypeRequest = new DeleteThingTypeRequest();
			deleteThingTypeRequest.setThingTypeName(request.getThingTypeName());
		
			this.awsIoTClient.deleteThingType(deleteThingTypeRequest);
		}catch(InternalException e){
			
		}catch(InvalidRequestException e) {
			
		}catch(ServiceUnavailableException e) {
			
		}catch(UnauthorizedException e) {
			
		}
		
	}

	public void deprecateThingTypeInRegistry(ReplicateThingTypeEvent request) {
		try{
			DeprecateThingTypeRequest deprecateThingTypeRequest = new DeprecateThingTypeRequest();
			deprecateThingTypeRequest.setThingTypeName(request.getThingTypeName());
			
			if(!request.getIsDeprecated()) {
				deprecateThingTypeRequest.setUndoDeprecate(true);
			}
			
			this.awsIoTClient.deprecateThingType(deprecateThingTypeRequest);
		}catch(ResourceNotFoundException e) {
			
		}catch(InvalidRequestException e) {
			
		}catch(ThrottlingException e) {
			
		}catch(UnauthorizedException e) {
			
		}catch(ServiceUnavailableException e) {
			
		}catch(InternalFailureException e) {
			
		}
	}

	public void addThingToThingType(ReplicateThingTypeAssociationEvent request) {
		try{
			UpdateThingRequest updateThingRequest = new UpdateThingRequest().withThingTypeName(request.getThingTypeName())
					.withThingName(request.getThingName());
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

	public void removeThingFromThingType(ReplicateThingTypeAssociationEvent request) {
		try{
			UpdateThingRequest updateThingRequest = new UpdateThingRequest().withRemoveThingType(true)
																			.withThingName(request.getThingName());
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

}
