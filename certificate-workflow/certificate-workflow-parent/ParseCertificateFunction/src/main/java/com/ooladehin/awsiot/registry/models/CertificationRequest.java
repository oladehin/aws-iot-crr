package com.ooladehin.awsiot.registry.models;

public class CertificationRequest {

	private String certificateId;
	private String caCertificateId;
	private Long timestamp;
	private String certificateStatus;
	private String awsAccountId;
	private String certificateRegistrationTimestamp;
	
	
	public CertificationRequest( ) { }
	
	public CertificationRequest(String certificateId, String caCertificateId, Long timestamp, String certificateStatus,
			String awsAccountId, String certificateRegistrationTimestamp) {
		super();
		this.certificateId = certificateId;
		this.caCertificateId = caCertificateId;
		this.timestamp = timestamp;
		this.certificateStatus = certificateStatus;
		this.awsAccountId = awsAccountId;
		this.certificateRegistrationTimestamp = certificateRegistrationTimestamp;
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
	
}
