package com.ooladehin.awsiot.registry.main;


import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ooladehin.awsiot.registry.models.RegistrationEvent;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.iot.model.CertificateStatus;
import com.amazonaws.services.iot.model.UpdateCertificateRequest;
import com.amazonaws.services.lambda.runtime.Context; 

/**
 * Amazon adds the certificate in the state, PENDING_ACTIVATION, 
 * and sends a message to a custom AWS IoT topic ($aws/events/certificates/registered/<caCertificateID>)
 * @author oladehin
 *
 */

public class ActivateCertificateFunction implements RequestHandler<RegistrationEvent, RegistrationEvent> {

	private static final String IOT_SOURCE_REGION = System.getenv("IOT_SOURCE_REGION");

    private final AWSIot awsIoTClient;

    /**
     * One-time initialization of resources for this Lambda function.
     */
    public ActivateCertificateFunction()
    {
        this.awsIoTClient = AWSIotClientBuilder.standard()
        						.withRegion(IOT_SOURCE_REGION)
        						.withCredentials(new DefaultAWSCredentialsProviderChain())
        						.build();
    }
	    
    //TODO: Look at javadocs later on in order to handle all the error states
	@Override
    public RegistrationEvent handleRequest(RegistrationEvent event, Context context) {
		
		
		//Check if cert is on revocation list
		//TODO:
		//Check if cert is in pending activation status or non revoked status
		//TODO:
		UpdateCertificateRequest updateCertificateRequest = new UpdateCertificateRequest();
		updateCertificateRequest.setCertificateId(event.getCertificateId());
		updateCertificateRequest.setNewStatus(CertificateStatus.ACTIVE);
		this.awsIoTClient.updateCertificate(updateCertificateRequest);
		//TODO: No errors assume success; need to add exception handling here
    	return event;    	
    	
    }
	
	
}
