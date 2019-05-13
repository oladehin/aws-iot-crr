package com.ooladehin.awsiot.registry.models.certificates;

import java.util.Map;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ooladehin.awsiot.registry.models.RegistryEvent;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CertificateEvent extends RegistryEvent {

	public static final String CERT_PEM_FILE = "certPemFile";
	public static final String THING_NAME = "thingName";
	public static final String POLICY_NAME = "policyName";
	
	private String certPemFile;
	private String thingName;
	private String policyName;
	
	public static CertificateEvent createCertificateEvent(Map<String, AttributeValue> dynamoStreamRecord) {
		String eventType = dynamoStreamRecord.get(EVENT_TYPE).getS();
		String eventId = dynamoStreamRecord.get(EVENT_ID).getS();
		Long timestamp = convertToLong(dynamoStreamRecord.get(TIMESTAMP).getS());

		String operation = dynamoStreamRecord.get(OPERATION).getS();
		String accountId = dynamoStreamRecord.get(ACCOUNT_ID).getS();
		String certPemFile = dynamoStreamRecord.get("certPemFile").getS();
		String thingName = dynamoStreamRecord.get("thingName").getS();
		String policyName = dynamoStreamRecord.get("policyName").getS();

		return new CertificateEvent(eventId, eventType, timestamp,  operation,  accountId, certPemFile,  thingName,  policyName);
	}
	
	public CertificateEvent(String eventId, String eventType, Long timestamp, String operation, String accountId, 
							String certPemFile, String thingName, String policyName) {
		super(eventType, eventId, timestamp, operation, accountId);
		this.certPemFile = certPemFile;
		this.thingName = thingName;
		this.policyName = policyName;
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

	@Override
	public String toString() {
		return "CertificateEvent [certPemFile=" + certPemFile + ", thingName=" + thingName + ", policyName="
				+ policyName + ", getEventType()=" + getEventType() + ", getEventId()=" + getEventId()
				+ ", getTimestamp()=" + getTimestamp() + ", getOperation()=" + getOperation() + ", getAccountId()="
				+ getAccountId() + "]";
	}

}

