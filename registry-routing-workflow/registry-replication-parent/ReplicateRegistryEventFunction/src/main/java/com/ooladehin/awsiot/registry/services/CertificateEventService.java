package com.ooladehin.awsiot.registry.services;


import java.util.Map;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.ooladehin.awsiot.registry.models.certificates.CertificateEvent;
import com.ooladehin.awsiot.registry.repository.CertificateEventRepository;

public class CertificateEventService {
	
	private final CertificateEventRepository certificateEventRepository;

	public CertificateEventService(AWSIot awsIoTClient) {
		this.certificateEventRepository = new CertificateEventRepository(awsIoTClient);
	}
	
	public void processCertificateEvent(Map<String, AttributeValue> dynamoStreamRecord, LambdaLogger logger) {
		logger.log("Started creating event");
		CertificateEvent event = CertificateEvent.createCertificateEvent(dynamoStreamRecord);
		logger.log("Ended event:" +event.toString());

		String certificateArn = this.certificateEventRepository.createCertificateInRegistry(event);
		logger.log("CertificateArn:" + certificateArn);

		this.certificateEventRepository.attachPolicyToCertificate(event.getPolicyName(), certificateArn);
		this.certificateEventRepository.attachPrincipalToThing(event.getThingName(), certificateArn);
		logger.log("Ended cert process");
		return;
	}
}
