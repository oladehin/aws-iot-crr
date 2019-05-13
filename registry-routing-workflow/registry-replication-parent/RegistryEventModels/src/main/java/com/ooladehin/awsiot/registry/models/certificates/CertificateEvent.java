package com.ooladehin.awsiot.registry.models.certificates;

import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CertificateEvent {

	
	private String globalCertificateId;
	private Map<String,String>  globalCaCertificates;
	private String certPemFile;
	private String thingName;
	private String policyName;
	
	public static CertificateEvent createCertificateEvent(Map<String, AttributeValue> dynamoStreamRecord) {
		
		CertificateEvent certificateEvent = new CertificateEvent();
		if(dynamoStreamRecord.containsKey(EVENT_TYPE)) {
			certificateEvent.setEventType(dynamoStreamRecord.get(EVENT_TYPE).getS());
		}
		if(dynamoStreamRecord.containsKey(EVENT_ID)) {
			certificateEvent.setEventId(dynamoStreamRecord.get(EVENT_ID).getS());
		}
		if(dynamoStreamRecord.containsKey(TIMESTAMP)) {
			certificateEvent.setTimestamp(convertToLong(dynamoStreamRecord.get(TIMESTAMP).getS()));
		}
		
		if(dynamoStreamRecord.containsKey(OPERATION)) {
			certificateEvent.setOperation(dynamoStreamRecord.get(OPERATION).getS());
		}
		if(dynamoStreamRecord.containsKey(ACCOUNT_ID)) {
			certificateEvent.setAccountId(dynamoStreamRecord.get(ACCOUNT_ID).getS());
		}

		if(dynamoStreamRecord.containsKey("globalCertificateId")) {
			certificateEvent.setGlobalCertificateId(dynamoStreamRecord.get("globalCertificateId").getS());
		}
		if(dynamoStreamRecord.containsKey("globalCaCertificates")) {
			certificateEvent.setGlobalCaCertificates(convertToAttributes(dynamoStreamRecord.get("globalCaCertificates").getM()));
		}
		if(dynamoStreamRecord.containsKey("certPemFile")) {
			certificateEvent.setCertPemFile(dynamoStreamRecord.get("certPemFile").getS());
		}
		if(dynamoStreamRecord.containsKey("thingName")) {
			certificateEvent.setThingName(dynamoStreamRecord.get("thingName").getS());
		}
		if(dynamoStreamRecord.containsKey("policyName")) {
			certificateEvent.setPolicyName(dynamoStreamRecord.get("policyName").getS());
		}
		return certificateEvent;
	}
	
    @JsonProperty("globalCertificateId")
	public String getGlobalCertificateId() {
		return globalCertificateId;
	}

	public void setGlobalCertificateId(String globalCertificateId) {
		this.globalCertificateId = globalCertificateId;
	}

    @JsonProperty("globalCaCertificates")
	public Map<String,String>  getGlobalCaCertificates() {
		return globalCaCertificates;
	}

	public void setGlobalCaCertificates(Map<String,String>  globalCaCertificates) {
		this.globalCaCertificates = globalCaCertificates;
	}

    @JsonProperty("certPemFile")
	public String getCertPemFile() {
		return certPemFile;
	}

	public void setCertPemFile(String certPemFile) {
		this.certPemFile = certPemFile;
	}

    @JsonProperty("thingName")
	public String getThingName() {
		return thingName;
	}

	public void setThingName(String thingName) {
		this.thingName = thingName;
	}

    @JsonProperty("policyName")
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
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

	@Override
	public String toString() {
		return "CertificateEvent [globalCertificateId=" + globalCertificateId + ", globalCaCertificates="
				+ globalCaCertificates + ", certPemFile=" + certPemFile + ", thingName=" + thingName + ", policyName="
				+ policyName + ", eventType=" + eventType + ", eventId=" + eventId + ", timestamp=" + timestamp
				+ ", operation=" + operation + ", accountId=" + accountId + "]";
	}
	
}

