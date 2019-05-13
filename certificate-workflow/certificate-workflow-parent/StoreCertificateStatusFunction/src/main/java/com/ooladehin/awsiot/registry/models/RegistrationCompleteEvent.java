package com.ooladehin.awsiot.registry.models;

public class RegistrationCompleteEvent {

	private String message;
	private String thingName;
	private String certificateArn;
	
	public RegistrationCompleteEvent( ){ }
	
	public RegistrationCompleteEvent(String message, String thingName, String certificateArn) {
		super();
		this.message = message;
		this.thingName = thingName;
		this.certificateArn = certificateArn;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getThingName() {
		return thingName;
	}
	
	public void setThingName(String thingName) {
		this.thingName = thingName;
	}
	
	public String getCertificateArn() {
		return certificateArn;
	}
	
	public void setCertificateArn(String certificateArn) {
		this.certificateArn = certificateArn;
	}
	
}
