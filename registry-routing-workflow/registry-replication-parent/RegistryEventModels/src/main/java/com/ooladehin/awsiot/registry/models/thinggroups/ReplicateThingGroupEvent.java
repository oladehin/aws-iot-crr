package com.ooladehin.awsiot.registry.models.thinggroups;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicateThingGroupEvent {
	
	private final static String DESCRIPTION = "description";
	private final static String THING_GROUP_ID = "thingGroupId";
	private final static String THING_GROUP_NAME = "thingGroupName";
	private final static String VERSION = "version";
	private final static String PARENT_GROUP_NAME = "parentGroupName";
	private final static String PARENT_GROUP_ID = "parentGroupId";
	private final static String ATTRIBUTES = "attributes";

	private String description;
	private String thingGroupId;
	private String thingGroupName;
	private Long version;
	private String parentGroupName;
	private String parentGroupId;
	private Map<String,String> attributes;
	
	public static ReplicateThingGroupEvent createReplicateThingGroupRequest(
			Map<String, AttributeValue> dynamoStreamRecord) {
		
		ReplicateThingGroupEvent replicateThingGroupEvent = new ReplicateThingGroupEvent();
		if(dynamoStreamRecord.containsKey(EVENT_TYPE)) {
			replicateThingGroupEvent.setEventType(dynamoStreamRecord.get(EVENT_TYPE).getS());
		}
		if(dynamoStreamRecord.containsKey(EVENT_ID)) {
			replicateThingGroupEvent.setEventId(dynamoStreamRecord.get(EVENT_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(TIMESTAMP)) {
			replicateThingGroupEvent.setTimestamp(convertToLong(dynamoStreamRecord.get(TIMESTAMP).getS()));
		}
		
		if(dynamoStreamRecord.containsKey(OPERATION)) {
			replicateThingGroupEvent.setOperation(dynamoStreamRecord.get(OPERATION).getS());
		}
		if(dynamoStreamRecord.containsKey(ACCOUNT_ID)) {
			replicateThingGroupEvent.setAccountId(dynamoStreamRecord.get(ACCOUNT_ID).getS());
		}
		
		if(dynamoStreamRecord.containsKey(DESCRIPTION)) {
			replicateThingGroupEvent.setAccountId(dynamoStreamRecord.get(DESCRIPTION).getS());
		}

		if(dynamoStreamRecord.containsKey(THING_GROUP_ID)) {
			replicateThingGroupEvent.setThingGroupId(dynamoStreamRecord.get(THING_GROUP_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(THING_GROUP_NAME)) {
			replicateThingGroupEvent.setThingGroupName(dynamoStreamRecord.get(THING_GROUP_NAME).getS());
		}
		if(dynamoStreamRecord.containsKey(VERSION)) {
			replicateThingGroupEvent.setVersion(convertToLong(dynamoStreamRecord.get(VERSION).getS()));
		}
		if(dynamoStreamRecord.containsKey(PARENT_GROUP_NAME)) {
			replicateThingGroupEvent.setParentGroupName(dynamoStreamRecord.get(PARENT_GROUP_NAME).getS());
		}
		if(dynamoStreamRecord.containsKey(PARENT_GROUP_ID)) {
			replicateThingGroupEvent.setParentGroupId(dynamoStreamRecord.get(PARENT_GROUP_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(ATTRIBUTES)) {
			replicateThingGroupEvent.setAttributes(convertToAttributes(dynamoStreamRecord.get(ATTRIBUTES).getM()));
		}
		
		return replicateThingGroupEvent;

	}
	
	public ReplicateThingGroupEvent() { }

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
                
		 if(this.getDescription() != null) {
			 item.withString(DESCRIPTION, this.getDescription());
		 }
		 
		 if(this.getThingGroupId() != null) {
			 item.withString(THING_GROUP_ID, this.getThingGroupId());
		 }
		 
		 if(this.getThingGroupName()!= null) {
			 item.withString(THING_GROUP_NAME, this.getThingGroupName());
		 }  
               
		 if(this.getVersion() != null) {
			 item.withNumber(VERSION, this.getVersion());
		 }
                
		 if(this.getParentGroupName() != null) {
			 item.withString(PARENT_GROUP_NAME, this.getParentGroupName());
		 }
		 
		 if(this.getParentGroupId() != null) {
			 item.withString(PARENT_GROUP_ID, this.getParentGroupId());
		 }
		 
		 if(this.getAttributes() != null) {
			 item.withMap(ATTRIBUTES, this.getAttributes());
		 }
		 return item;
	}
	
    @JsonProperty("description")
	public String getDescription() {
    	return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
    @JsonProperty("thingGroupId")
	public String getThingGroupId() {
		return thingGroupId;
	}
	
	public void setThingGroupId(String thingGroupId) {
		this.thingGroupId = thingGroupId;
	}
	
    @JsonProperty("thingGroupName")
	public String getThingGroupName() {
		return thingGroupName;
	}
	
	public void setThingGroupName(String thingGroupName) {
		this.thingGroupName = thingGroupName;
	}
	
    @JsonProperty("version")
	public Long getVersion() {
    	return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
	
    @JsonProperty("parentGroupName")
	public String getParentGroupName() {
    	return parentGroupName;
	}
	
	public void setParentGroupName(String parentGroupName) {
		this.parentGroupName = parentGroupName;
	}
	
	public String getParentGroupId() {
		return parentGroupId;
	}
	
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	
	public Map<String,String> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<String,String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "ReplicateThingGroupRequest [eventType=" + this.getEventType() + ", eventId=" + this.getEventId() + ", timestamp="
				+ this.getTimestamp() + ", operation=" + this.getOperation() + ", description=" + description + ", accountId=" + this.getAccountId()
				+ ", thingGroupId=" + thingGroupId + ", thingGroupName=" + thingGroupName + ", versionNumber="
				+ version + ", parentGroupName=" + parentGroupName + ", parentGroupId=" + parentGroupId
				+ ", attributes=" + attributes + "]";
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
