package com.ooladehin.awsiot.registry.models.thingtypes;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicateThingTypeAssociationEvent {
	
	public final static String EVENT_TYPE = "eventType";
	public final static String EVENT_ID = "eventId";
	public final static String TIMESTAMP = "timestamp";
	public final static String OPERATION = "operation";
	public final static String ACCOUNT_ID = "accountId";
	
	private String eventType;
	private String eventId;
	private Long timestamp;
	private String operation;
	private String accountId;
	

	protected static Long convertToLong(String attribute) {
		if(attribute != null) {
			Long.parseLong(attribute);
		}
		return 0L;
	}

	protected static Map<String, String> convertToAttributes(Map<String, AttributeValue> attributes) {
		Map<String,String> thingRegistryAttributes = new HashMap<String,String>();
		attributes.forEach((key,value)-> thingRegistryAttributes.put(key, value.getS()));
		return thingRegistryAttributes;
	}

    @JsonProperty("eventType")
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
    @JsonProperty("eventId")
	public String getEventId() {
		return eventId;
	}
	
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
    @JsonProperty("timestamp")
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
    @JsonProperty("operation")
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}

    @JsonProperty("accountId")
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	private final static String THING_ID = "thingId";
	private final static String THING_NAME = "thingName";
	private final static String THING_TYPE_NAME = "thingTypeName";
	
	private String thingName;
	private String thingTypeName;
	private String thingId;
	
	public static ReplicateThingTypeAssociationEvent createReplicateThingTypeAssociationRequest(
			Map<String, AttributeValue> dynamoStreamRecord) {
		
		ReplicateThingTypeAssociationEvent replicateThingTypeAssociationEvent = new ReplicateThingTypeAssociationEvent();
		if(dynamoStreamRecord.containsKey(EVENT_TYPE)) {
			replicateThingTypeAssociationEvent.setEventType(dynamoStreamRecord.get(EVENT_TYPE).getS());
		}
		if(dynamoStreamRecord.containsKey(EVENT_ID)) {
			replicateThingTypeAssociationEvent.setEventId(dynamoStreamRecord.get(EVENT_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(TIMESTAMP)) {
			replicateThingTypeAssociationEvent.setTimestamp(convertToLong(dynamoStreamRecord.get(TIMESTAMP).getS()));
		}
		
		if(dynamoStreamRecord.containsKey(OPERATION)) {
			replicateThingTypeAssociationEvent.setOperation(dynamoStreamRecord.get(OPERATION).getS());
		}
		if(dynamoStreamRecord.containsKey(ACCOUNT_ID)) {
			replicateThingTypeAssociationEvent.setAccountId(dynamoStreamRecord.get(ACCOUNT_ID).getS());
		}
		
		if(dynamoStreamRecord.containsKey(THING_ID)) {
			replicateThingTypeAssociationEvent.setThingId(dynamoStreamRecord.get(THING_ID).getS());
		}
		
		if(dynamoStreamRecord.containsKey(THING_NAME)) {
			replicateThingTypeAssociationEvent.setThingName(dynamoStreamRecord.get(THING_NAME).getS());
		}
		
		if(dynamoStreamRecord.containsKey(THING_TYPE_NAME)) {
			replicateThingTypeAssociationEvent.setThingTypeName(dynamoStreamRecord.get(THING_TYPE_NAME).getS());
		}

		return replicateThingTypeAssociationEvent;
	}
	
	public ReplicateThingTypeAssociationEvent() { }
	
	public Item convertToDynamoItem() {
		
		Item item = new Item().withPrimaryKey(EVENT_ID, this.getEventId());
		 
		 if(this.getEventType() != null) {
				item.withString(EVENT_TYPE, this.getEventType());
		 }
		 if(this.getTimestamp() != null) {
				item.withNumber(TIMESTAMP, this.getTimestamp());
		 }
		 if(this.getOperation()!= null) {
				item.withString(OPERATION, this.getOperation());
		 }
		 if(this.getAccountId() != null) {
				item.withString(ACCOUNT_ID, this.getAccountId());
		 }
                
		 if(this.getThingId() != null) {
				item.withString(THING_ID, this.getThingId());
		 }
		 
		 if(this.getThingName() != null) {
				item.withString(THING_NAME, this.getThingName());
		 }
		 
		 if(this.getThingTypeName()!= null) {
				item.withString(THING_TYPE_NAME, this.getThingTypeName());
		 }   
		 
		 return item;
	}

    @JsonProperty("thingName")
	public String getThingName() {
		return thingName;
	}
	
	public void setThingName(String thingName) {
		this.thingName = thingName;
	}
	
    @JsonProperty("thingTypeName")
	public String getThingTypeName() {
		return thingTypeName;
	}
	
	public void setThingTypeName(String thingTypeName) {
		this.thingTypeName = thingTypeName;
	}
	
    @JsonProperty("thingId")
	public String getThingId() {
		return thingId;
	}
	
	public void setThingId(String thingId) {
		this.thingId = thingId;
	}

	@Override
	public String toString() {
		return "ReplicateThingTypeAssociationRequest [eventType=" + this.getEventType() + ", eventId=" + this.getEventId() + ", timestamp="
				+ this.getTimestamp() + ", operation=" + this.getOperation() + ", accountId=" + this.getAccountId() + ", thingName=" + thingName
				+ ", thingTypeName=" + thingTypeName + ", thingId=" + thingId + "]";
	}

}
