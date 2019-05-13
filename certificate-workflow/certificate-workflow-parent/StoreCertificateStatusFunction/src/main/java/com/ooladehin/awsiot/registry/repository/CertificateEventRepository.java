package com.ooladehin.awsiot.registry.repository;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.ooladehin.awsiot.registry.models.certificates.CertificateEvent;

public class CertificateEventRepository {

	
	//TODO: Instead of global certificate need to have each ca have a map of the regions where it exists and just duplicate the data
	private final AmazonDynamoDB dynamoDBClient; 
    private final DynamoDB dynamoDB;	
    private final Table certificateTable;
    

	public CertificateEventRepository(String dynamoDBRegion, String registryTableName) {
        this.dynamoDBClient = AmazonDynamoDBClientBuilder.standard()
				.withRegion(dynamoDBRegion)
				.withCredentials(new DefaultAWSCredentialsProviderChain())
				.build();
        this.dynamoDB = new DynamoDB(this.dynamoDBClient);
		this.certificateTable = this.dynamoDB.getTable(registryTableName);

	}
	

	public void persistCertificate(CertificateEvent certificateEvent) {
        try {
        	Item certificateItem = createItemFromCertificate(certificateEvent);
        	this.certificateTable.putItem(certificateItem);
        } catch (Exception e) {
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());
        }
	}

	private Item createItemFromCertificate(CertificateEvent certificateEvent) {
		
		return new Item().withPrimaryKey(CertificateEvent.EVENT_ID, certificateEvent.getEventId())
				.withString(CertificateEvent.ACCOUNT_ID, certificateEvent.getAccountId())
				.withString(CertificateEvent.CERT_PEM_FILE, certificateEvent.getCertPemFile())
                .withString(CertificateEvent.EVENT_TYPE, certificateEvent.getEventType())
                .withString(CertificateEvent.THING_NAME, certificateEvent.getThingName())
                .withString(CertificateEvent.OPERATION, certificateEvent.getOperation())
                .withString(CertificateEvent.POLICY_NAME, certificateEvent.getPolicyName())
                .withNumber(CertificateEvent.TIMESTAMP, certificateEvent.getTimestamp());
	}

}
