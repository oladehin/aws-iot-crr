package com.ooladehin.awsiot.registry.main;


import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ooladehin.awsiot.registry.models.RegistrationEvent;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.iot.model.AttachThingPrincipalRequest;
import com.amazonaws.services.lambda.runtime.Context; 

/**
 * Amazon adds the certificate in the state, PENDING_ACTIVATION, 
 * and sends a message to a custom AWS IoT topic ($aws/events/certificates/registered/<caCertificateID>)
 * @author oladehin
 *
 */

public class AttachCertificateFunction implements RequestHandler<RegistrationEvent, RegistrationEvent> {

	private static final String IOT_SOURCE_REGION = System.getenv("IOT_SOURCE_REGION");
    private final AWSIot awsIoTClient;

    /**
     * One-time initialization of resources for this Lambda function.
     */
    public AttachCertificateFunction()
    {
        this.awsIoTClient = AWSIotClientBuilder.standard()
        						.withRegion(IOT_SOURCE_REGION)
        						.withCredentials(new DefaultAWSCredentialsProviderChain())
        						.build();
    }
	    
    //TODO: Look at javadocs later on in order to handle all the error states
	@Override
    public RegistrationEvent handleRequest(RegistrationEvent event, Context context) {
		
		AttachThingPrincipalRequest attachThingPrincipalRequest = new AttachThingPrincipalRequest( );
		attachThingPrincipalRequest.setPrincipal( event.getCertificateArn( ) );
		attachThingPrincipalRequest.setThingName( event.getThingName() );
		this.awsIoTClient.attachThingPrincipal( attachThingPrincipalRequest );
		
    	return event;    	
    }
	
	
}
