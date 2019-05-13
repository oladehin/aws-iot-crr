package com.ooladehin.awsiot.registry.main;


import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ooladehin.awsiot.registry.models.EventTypeEnum;
import com.ooladehin.awsiot.registry.models.RegistrationCompleteEvent;
import com.ooladehin.awsiot.registry.models.RegistrationEvent;
import com.ooladehin.awsiot.registry.models.RegistryConstants;
import com.ooladehin.awsiot.registry.models.certificates.CertificateEvent;
import com.ooladehin.awsiot.registry.repository.CertificateEventRepository;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context; 

/**
 * Amazon adds the certificate in the state, PENDING_ACTIVATION, 
 * and sends a message to a custom AWS IoT topic ($aws/events/certificates/registered/<caCertificateID>)
 * @author oladehin
 *
 */

public class StoreCertificateStatusFunction implements RequestHandler<RegistrationEvent, RegistrationCompleteEvent> {


    private static final String GLOBAL_IOT_POLICY_NAME = System.getenv("GLOBAL_IOT_POLICY_NAME");
    private static final String IOT_SOURCE_REGION = System.getenv("IOT_SOURCE_REGION");
	private static final String REGISTRY_EVENT_TABLENAME = System.getenv("REGISTRY_EVENT_TABLENAME");
	
    private final CertificateEventRepository certificateEventRepository;
    
    /**
     * One-time initialization of resources for this Lambda function.
     */
    public StoreCertificateStatusFunction() {
    	this.certificateEventRepository = new CertificateEventRepository(IOT_SOURCE_REGION, REGISTRY_EVENT_TABLENAME);
    }
	    
	@Override
    public RegistrationCompleteEvent handleRequest(RegistrationEvent event, Context context) {
		
		CertificateEvent certificateEvent = new CertificateEvent(UUID.randomUUID().toString(), "CERTIFICATE_EVENT",event.getTimestamp(), 
				RegistryConstants.CREATED, event.getAwsAccountId(),  event.getPemFile(), event.getThingName(), GLOBAL_IOT_POLICY_NAME);
		this.certificateEventRepository.persistCertificate(certificateEvent);

		return new RegistrationCompleteEvent("Registration completed successfully", event.getThingName(), event.getCertificateArn());
    }

   
}
