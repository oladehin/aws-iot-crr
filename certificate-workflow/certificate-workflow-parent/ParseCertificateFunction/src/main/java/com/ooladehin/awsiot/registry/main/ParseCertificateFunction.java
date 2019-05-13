package com.ooladehin.awsiot.registry.main;


import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ooladehin.awsiot.registry.models.CertificationRequest;
import com.ooladehin.awsiot.registry.models.RegistrationEvent;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.iot.model.CertificateDescription;
import com.amazonaws.services.iot.model.DescribeCertificateRequest;
import com.amazonaws.services.iot.model.DescribeCertificateResult;
import com.amazonaws.services.lambda.runtime.Context; 

/**
 * Amazon adds the certificate in the state, PENDING_ACTIVATION, 
 * and sends a message to a custom AWS IoT topic ($aws/events/certificates/registered/<caCertificateID>)
 * @author oladehin
 *
 */

public class ParseCertificateFunction implements RequestHandler<CertificationRequest, RegistrationEvent> {

	private static final String IOT_SOURCE_REGION = System.getenv("IOT_SOURCE_REGION");
    private final AWSIot awsIoTClient;

    /**
     * One-time initialization of resources for this Lambda function.
     */
    public ParseCertificateFunction()
    {
        this.awsIoTClient = AWSIotClientBuilder.standard()
        						.withRegion(IOT_SOURCE_REGION)
        						.withCredentials(new DefaultAWSCredentialsProviderChain())
        						.build();
    }
	    
	@Override
    public RegistrationEvent handleRequest(CertificationRequest request, Context context) {
    	DescribeCertificateRequest describeCertificateRequest = new DescribeCertificateRequest();
    	describeCertificateRequest.setCertificateId(request.getCertificateId());
    	DescribeCertificateResult certificateResult = this.awsIoTClient.describeCertificate(describeCertificateRequest);
    	CertificateDescription certificateDescription = certificateResult.getCertificateDescription();
    	
    	String pemFile = certificateDescription.getCertificatePem();
    	Date validNotAfter = certificateDescription.getValidity().getNotAfter();
    	Date validNotBefore = certificateDescription.getValidity().getNotBefore();
    	String certificateArn = certificateDescription.getCertificateArn();
    	X509Certificate certificate = convertToX509Cert(pemFile); 
    	String serialNumber = certificate.getSerialNumber().toString();
    	
    	return new RegistrationEvent(request.getCertificateId(), request.getCaCertificateId(), request.getTimestamp(), 
    			request.getCertificateStatus(), request.getAwsAccountId(), request.getCertificateRegistrationTimestamp(), 
    			pemFile, validNotAfter, validNotBefore, certificateArn, serialNumber);    	
    }
	
	public static X509Certificate convertToX509Cert(String certificateString) {
	    X509Certificate certificate = null;
	    CertificateFactory cf = null;
	    try {
   
	           certificateString = certificateString.replace("-----BEGIN CERTIFICATE-----\n", "")
							.replace("\n", "")
							.replace("-----END CERTIFICATE-----", "");
            byte[] certificateData = Base64.getDecoder().decode(certificateString);
            cf = CertificateFactory.getInstance("X509");
            certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData));      
	    } catch (CertificateException e) {
	        return null;
	    	//TODO: Add error handling post reinvent
	        //throw new CertificateException(e);
	    }
	    return certificate;
	}
}
