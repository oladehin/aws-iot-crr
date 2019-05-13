package com.ooladehin.awsiot.registry.models.thingtypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicateThingTypeEvent {

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
	
	private final static String THING_TYPE_ID = "thingTypeId";
	private final static String THING_TYPE_NAME = "thingTypeName";
	private final static String IS_DEPRECATED = "isDeprecated";
	private final static String SEARCHABLE_ATTRIBUTES = "searchableAttributes";
	private final static String DESCRIPTION = "description";
	
	private String thingTypeId;
	private String thingTypeName;
	private Boolean isDeprecated;
	private List<String> searchableAttributes;
	private String description;
	
	
	public static ReplicateThingTypeEvent createReplicateThingTypeRequest(
			Map<String, AttributeValue> dynamoStreamRecord) {
		
		ReplicateThingTypeEvent replicateThingTypeEvent = new ReplicateThingTypeEvent();
		
		if(dynamoStreamRecord.containsKey(EVENT_TYPE)) {
			replicateThingTypeEvent.setEventType(dynamoStreamRecord.get(EVENT_TYPE).getS());
		}
		if(dynamoStreamRecord.containsKey(EVENT_ID)) {
			replicateThingTypeEvent.setEventId(dynamoStreamRecord.get(EVENT_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(TIMESTAMP)) {
			replicateThingTypeEvent.setTimestamp(convertToLong(dynamoStreamRecord.get(TIMESTAMP).getS()));
		}
		
		if(dynamoStreamRecord.containsKey(OPERATION)) {
			replicateThingTypeEvent.setOperation(dynamoStreamRecord.get(OPERATION).getS());
		}
		if(dynamoStreamRecord.containsKey(ACCOUNT_ID)) {
			replicateThingTypeEvent.setAccountId(dynamoStreamRecord.get(ACCOUNT_ID).getS());
		}

		if(dynamoStreamRecord.containsKey(THING_TYPE_ID)) {
			replicateThingTypeEvent.setThingTypeId(dynamoStreamRecord.get(THING_TYPE_ID).getS());
		}

		if(dynamoStreamRecord.containsKey(THING_TYPE_NAME)) {
			replicateThingTypeEvent.setThingTypeName(dynamoStreamRecord.get(THING_TYPE_NAME).getS());
		}

		if(dynamoStreamRecord.containsKey(IS_DEPRECATED)) {
			replicateThingTypeEvent.setIsDeprecated(dynamoStreamRecord.get(IS_DEPRECATED).getBOOL());
		}

		if(dynamoStreamRecord.containsKey(SEARCHABLE_ATTRIBUTES)) {
			replicateThingTypeEvent.setSearchableAttributes(dynamoStreamRecord.get(SEARCHABLE_ATTRIBUTES).getSS());
		}

		if(dynamoStreamRecord.containsKey(DESCRIPTION)) {
			replicateThingTypeEvent.setDescription(dynamoStreamRecord.get(DESCRIPTION).getS());
		}
		
		return replicateThingTypeEvent;
	}

	public ReplicateThingTypeEvent() {
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
		
		if(this.getThingTypeId()!= null) {
			item.withString(THING_TYPE_ID, this.getThingTypeId());
		}
		
		if(this.getIsDeprecated() != null) {
			item.withBoolean(IS_DEPRECATED, this.getIsDeprecated());
		}
		if(this.getThingTypeName() != null) {
			item.withString(THING_TYPE_NAME, this.getThingTypeName());
		}
		
		if(this.getSearchableAttributes() != null) {
			item.withList(SEARCHABLE_ATTRIBUTES, this.getSearchableAttributes());
		}
		
		if(this.getDescription()!= null) {
			item.withString(DESCRIPTION, this.getDescription());
		}
                
        return item;               
	}
	
    @JsonProperty("thingTypeId")
	public String getThingTypeId() {
		return thingTypeId;
	}
	
	public void setThingTypeId(String thingTypeId) {
		this.thingTypeId = thingTypeId;
	}

    @JsonProperty("thingTypeName")
	public String getThingTypeName() {
		return thingTypeName;
	}
	
	public void setThingTypeName(String thingTypeName) {
		this.thingTypeName = thingTypeName;
	}

    @JsonProperty("isDeprecated")
	public Boolean getIsDeprecated() {
		return isDeprecated;
	}
	
	public void setIsDeprecated(Boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}
	
    @JsonProperty("searchableAttributes")
	public List<String> getSearchableAttributes() {
		return searchableAttributes;
	}
	
	public void setSearchableAttributes(List<String> searchableAttributes) {
		this.searchableAttributes = searchableAttributes;
	}
	
    @JsonProperty("description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

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
	
	@Override
	public String toString() {
		return "ReplicateThingTypeRequest [eventType=" + this.getEventType() + ", eventId=" + this.getEventId() + ", timestamp=" + this.getTimestamp()
				+ ", operation=" + this.getOperation() + ", accountId=" + this.getAccountId() + ", thingTypeId=" + thingTypeId
				+ ", thingTypeName=" + thingTypeName + ", isDeprecated=" + isDeprecated  + ", searchableAttributes=" + searchableAttributes + ", description=" + description
				+ "]";
	}

}
