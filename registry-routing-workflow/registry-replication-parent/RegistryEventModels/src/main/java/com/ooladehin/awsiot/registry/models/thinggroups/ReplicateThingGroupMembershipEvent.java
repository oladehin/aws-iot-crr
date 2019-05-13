package com.ooladehin.awsiot.registry.models.thinggroups;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicateThingGroupMembershipEvent {

	private final static String GROUP_ARN = "groupArn";
	private final static String GROUP_ID = "groupId";
	private final static String THING_ARN = "thingArn";
	private final static String THING_ID = "thingId";
	private final static String MEMBERSHIP_ID = "membershipId";
	
	private String groupArn;
	private String groupId;
	private String thingArn;
	private String thingId;
	private String membershipId;

	public static ReplicateThingGroupMembershipEvent createReplicateThingGroupMembershipRequest(
			Map<String, AttributeValue> dynamoStreamRecord) {
		
		ReplicateThingGroupMembershipEvent replicateThingGroupMembershipEvent = new ReplicateThingGroupMembershipEvent();
		if(dynamoStreamRecord.containsKey(EVENT_TYPE)) {
			replicateThingGroupMembershipEvent.setEventType(dynamoStreamRecord.get(EVENT_TYPE).getS());
		}
		if(dynamoStreamRecord.containsKey(EVENT_ID)) {
			replicateThingGroupMembershipEvent.setEventId(dynamoStreamRecord.get(EVENT_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(TIMESTAMP)) {
			replicateThingGroupMembershipEvent.setTimestamp(convertToLong(dynamoStreamRecord.get(TIMESTAMP).getS()));
		}
		
		if(dynamoStreamRecord.containsKey(OPERATION)) {
			replicateThingGroupMembershipEvent.setOperation(dynamoStreamRecord.get(OPERATION).getS());
		}
		if(dynamoStreamRecord.containsKey(ACCOUNT_ID)) {
			replicateThingGroupMembershipEvent.setAccountId(dynamoStreamRecord.get(ACCOUNT_ID).getS());
		}
		
		if(dynamoStreamRecord.containsKey(GROUP_ARN)) {
			replicateThingGroupMembershipEvent.setGroupArn(dynamoStreamRecord.get(GROUP_ARN).getS());
		}
		
		if(dynamoStreamRecord.containsKey(GROUP_ID)) {
			replicateThingGroupMembershipEvent.setGroupId(dynamoStreamRecord.get(GROUP_ID).getS());
		}
		
		if(dynamoStreamRecord.containsKey(THING_ARN)) {
			replicateThingGroupMembershipEvent.setThingArn(dynamoStreamRecord.get(THING_ARN).getS());
		}
		
		if(dynamoStreamRecord.containsKey(THING_ID)) {
			replicateThingGroupMembershipEvent.setThingId(dynamoStreamRecord.get(THING_ID).getS());
		}
		
		if(dynamoStreamRecord.containsKey(MEMBERSHIP_ID)) {
			replicateThingGroupMembershipEvent.setMembershipId(dynamoStreamRecord.get(MEMBERSHIP_ID).getS());
		}
		
		return replicateThingGroupMembershipEvent;
	}
		
	public ReplicateThingGroupMembershipEvent() {}
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
		 
		 if(this.getGroupArn()!= null) {
				item.withString(GROUP_ARN, this.getGroupArn());
		 }
		 
		 if(this.getGroupId()!= null) {
				item.withString(GROUP_ID, this.getGroupId());
		 }
		 
		 if(this.getThingArn() != null) {
				item.withString(THING_ARN, this.getThingArn());
		 }
		 
		 if(this.getThingId() != null) {
				item.withString(THING_ID, this.getThingId());
		 }
		 
		 if(this.getMembershipId() != null) {
				item.withString(MEMBERSHIP_ID, this.getMembershipId());
		 }
		 return item;
	}
	
    @JsonProperty("groupArn")
	public String getGroupArn() {
		return groupArn;
	}
	
	public void setGroupArn(String groupArn) {
		this.groupArn = groupArn;
	}
	
    @JsonProperty("groupId")
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
    @JsonProperty("thingArn")
	public String getThingArn() {
		return thingArn;
	}
	
	public void setThingArn(String thingArn) {
		this.thingArn = thingArn;
	}
	
    @JsonProperty("thingId")
	public String getThingId() {
		return thingId;
	}
	
	public void setThingId(String thingId) {
		this.thingId = thingId;
	}
	
    @JsonProperty("membershipId")
	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}


	@Override
	public String toString() {
		return "ReplicateThingGroupMembershipRequest [eventType=" + this.getEventType() + ", eventId=" + this.getEventId() + ", timestamp="
				+ this.getTimestamp() + ", operation=" + this.getOperation() + ", accountId=" + this.getAccountId() + ", groupArn=" + groupArn
				+ ", groupId=" + groupId + ", thingArn=" + thingArn + ", thingId=" + thingId + ", membershipId="
				+ membershipId + "]";
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
