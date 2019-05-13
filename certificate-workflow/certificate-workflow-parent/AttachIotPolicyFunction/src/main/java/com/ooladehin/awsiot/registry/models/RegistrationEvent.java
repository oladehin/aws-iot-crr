package com.ooladehin.awsiot.registry.models;

import java.util.Date;

public class RegistrationEvent {

	private String certificateId;
	private String caCertificateId;
	private Long timestamp;
	private String certificateStatus;
	private String awsAccountId;
	private String certificateRegistrationTimestamp;
	private String pemFile;
	private Date validThroughDate;
	private Date validFromDate;
	private String certificateArn;
	private String thingName;
	
	public RegistrationEvent() { }
	
	public RegistrationEvent(String certificateId, String caCertificateId, Long timestamp, String certificateStatus,
			String awsAccountId, String certificateRegistrationTimestamp, String pemFile, Date validThroughDate,
			Date validFromDate, String certificateArn, String thingName) {
		super();
		this.certificateId = certificateId;
		this.caCertificateId = caCertificateId;
		this.timestamp = timestamp;
		this.certificateStatus = certificateStatus;
		this.awsAccountId = awsAccountId;
		this.certificateRegistrationTimestamp = certificateRegistrationTimestamp;
		this.pemFile = pemFile;
		this.validThroughDate = validThroughDate;
		this.validFromDate = validFromDate;
		this.certificateArn = certificateArn;
		this.thingName = thingName;
	}

	public String getCertificateId() {
		return certificateId;
	}
	
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	
	public String getCaCertificateId() {
		return caCertificateId;
	}
	
	public void setCaCertificateId(String caCertificateId) {
		this.caCertificateId = caCertificateId;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getCertificateStatus() {
		return certificateStatus;
	}
	
	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}
	
	public String getAwsAccountId() {
		return awsAccountId;
	}
	
	public void setAwsAccountId(String awsAccountId) {
		this.awsAccountId = awsAccountId;
	}
	
	public String getCertificateRegistrationTimestamp() {
		return certificateRegistrationTimestamp;
	}
	
	public void setCertificateRegistrationTimestamp(String certificateRegistrationTimestamp) {
		this.certificateRegistrationTimestamp = certificateRegistrationTimestamp;
	}
	
	public String getPemFile() {
		return pemFile;
	}
	
	public void setPemFile(String pemFile) {
		this.pemFile = pemFile;
	}
	
	public Date getValidThroughDate() {
		return validThroughDate;
	}
	
	public void setValidThroughDate(Date validThroughDate) {
		this.validThroughDate = validThroughDate;
	}
	
	public Date getValidFromDate() {
		return validFromDate;
	}
	
	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}
	
	public String getCertificateArn() {
		return certificateArn;
	}
	
	public void setCertificateArn(String certificateArn) {
		this.certificateArn = certificateArn;
	}
	
	public String getThingName() {
		return thingName;
	}
	
	public void setThingName(String thingName) {
		this.thingName = thingName;
	}
		
}