package com.ooladehin.awsiot.registry.repository;

import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.model.AttachPolicyRequest;
import com.amazonaws.services.iot.model.AttachThingPrincipalRequest;
import com.amazonaws.services.iot.model.CertificateStatus;
import com.amazonaws.services.iot.model.RegisterCertificateRequest;
import com.amazonaws.services.iot.model.RegisterCertificateResult;
import com.ooladehin.awsiot.registry.models.certificates.CertificateEvent;

public class CertificateEventRepository {

	private final AWSIot awsIoTClient;
		
	public CertificateEventRepository(AWSIot awsIoTClient) {
		this.awsIoTClient = awsIoTClient;
	}
		
	public String createCertificateInRegistry(CertificateEvent event) {
		RegisterCertificateRequest registerCertificateRequest = new RegisterCertificateRequest();
		registerCertificateRequest.setCertificatePem(event.getCertPemFile());
		registerCertificateRequest.setStatus(CertificateStatus.ACTIVE);
		RegisterCertificateResult registerCertificateResult= this.awsIoTClient.registerCertificate(registerCertificateRequest);
		return registerCertificateResult.getCertificateArn();
	}
	
	public void attachPolicyToCertificate(String policyName, String certificateArn) {
		AttachPolicyRequest attachPolicyRequest = new AttachPolicyRequest();
		attachPolicyRequest.setPolicyName(policyName);
		attachPolicyRequest.setTarget(certificateArn);
		this.awsIoTClient.attachPolicy(attachPolicyRequest);
	}
	
	public void attachPrincipalToThing(String thingName, String certificateArn) {
		AttachThingPrincipalRequest attachThingPrincipalRequest = new AttachThingPrincipalRequest( );
		attachThingPrincipalRequest.setPrincipal( certificateArn );
		attachThingPrincipalRequest.setThingName( thingName );
		this.awsIoTClient.attachThingPrincipal( attachThingPrincipalRequest );
	}

}
