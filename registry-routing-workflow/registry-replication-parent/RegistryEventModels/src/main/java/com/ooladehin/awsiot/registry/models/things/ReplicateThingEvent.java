package com.ooladehin.awsiot.registry.models.things;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicateThingEvent {
	
	private final static String THING_ID = "thingId";
	private final static String THING_NAME = "thingName";
	private final static String THING_TYPE_NAME = "thingTypeName";
	private final static String VERSION = "version";
	private final static String ATTRIBUTES = "attributes";
	
	private String thingId;
	private String thingName;
	private Long version;
	private String thingTypeName;
	private Map<String,String> attributes;	
	
	public ReplicateThingEvent() {}
	
	public static ReplicateThingEvent createFromDynamoRecord(Map<String, AttributeValue> dynamoStreamRecord) {
		
		ReplicateThingEvent replicateThingEvent = new ReplicateThingEvent();
		if(dynamoStreamRecord.containsKey(EVENT_TYPE)) {
			replicateThingEvent.setEventType(dynamoStreamRecord.get(EVENT_TYPE).getS());
		}
		if(dynamoStreamRecord.containsKey(EVENT_ID)) {
			replicateThingEvent.setEventId(dynamoStreamRecord.get(EVENT_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(TIMESTAMP)) {
			replicateThingEvent.setTimestamp(convertToLong(dynamoStreamRecord.get(TIMESTAMP).getS()));
		}
		
		if(dynamoStreamRecord.containsKey(OPERATION)) {
			replicateThingEvent.setOperation(dynamoStreamRecord.get(OPERATION).getS());
		}
		if(dynamoStreamRecord.containsKey(ACCOUNT_ID)) {
			replicateThingEvent.setAccountId(dynamoStreamRecord.get(ACCOUNT_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(THING_ID)) {
			replicateThingEvent.setThingId(dynamoStreamRecord.get(THING_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(THING_NAME)) {
			replicateThingEvent.setThingName(dynamoStreamRecord.get(THING_NAME).getS());
		}
		if(dynamoStreamRecord.containsKey(THING_TYPE_NAME)) {
			replicateThingEvent.setThingTypeName(dynamoStreamRecord.get(THING_TYPE_NAME).getS());
		}
		if(dynamoStreamRecord.containsKey(VERSION)) {
			replicateThingEvent.setVersion(convertToLong(dynamoStreamRecord.get(VERSION).getN()));
		}
		if(dynamoStreamRecord.containsKey(VERSION)) {
			replicateThingEvent.setAttributes(convertToAttributes(dynamoStreamRecord.get(ATTRIBUTES).getM()));
		}
		return replicateThingEvent;
	}

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
		 if(this.getAccountId() != null) {
				item.withString(THING_ID, this.getThingId());
		 }
		 
		 if(this.getThingName() != null) {
				item.withString(THING_NAME, this.getThingName());
		 }
		 
		 if(this.getThingTypeName() != null) {
			item.withString(THING_TYPE_NAME, this.getThingTypeName());
		 }
		 
		 if(this.getVersion() != null) {
			item.withNumber(VERSION, this.getVersion());
		 }
		 
		 if(this.getAttributes()!= null) {
			item.withMap(ATTRIBUTES, this.getAttributes());
		 }
		 
		 return item;
	}

    @JsonProperty("thingId")
	public String getThingId() {
		return thingId;
	}
	
	public void setThingId(String thingId) {
		this.thingId = thingId;
	}
	
    @JsonProperty("thingName")
	public String getThingName() {
		return thingName;
	}
	
	public void setThingName(String thingName) {
		this.thingName = thingName;
	}
	
    @JsonProperty("version")
	public Long getVersion() {
    	return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
	
    @JsonProperty("thingTypeName")
	public String getThingTypeName() {
		return thingTypeName;
	}
	
	public void setThingTypeName(String thingTypeName) {
		this.thingTypeName = thingTypeName;
	}

    @JsonProperty("attributes")
	public Map<String,String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String,String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "ReplicateThingRequest [eventType=" + this.getEventType() + ", eventId=" + this.getEventId() + ", timestamp=" + this.getTimestamp()
				+ ", operation=" + this.getOperation() + ", accountId=" + this.getAccountId() + ", thingId=" + thingId + ", thingName="
				+ thingName + ", versionNumber=" + version + ", thingTypeName=" + thingTypeName + ", attributes="
				+ attributes + "]";
	}
	
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
	
}
