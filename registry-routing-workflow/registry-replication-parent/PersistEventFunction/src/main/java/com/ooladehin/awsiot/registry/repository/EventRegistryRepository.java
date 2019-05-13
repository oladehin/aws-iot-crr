package com.ooladehin.awsiot.registry.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class EventRegistryRepository {

	//TODO: Instead of global certificate need to have each ca have a map of the regions where it exists and just duplicate the data
	private final AmazonDynamoDB dynamoDBClient; 
    private final DynamoDB dynamoDB;	
    private final Table registryEventTable;
    

	public EventRegistryRepository(String dynamoDBRegion, String registryEventTableName) {
        this.dynamoDBClient = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamoDB = new DynamoDB(this.dynamoDBClient);
		this.registryEventTable = this.dynamoDB.getTable(registryEventTableName);
	}

	//TODO: Handle errors
	public void storeRegistryEvent(Item convertedItem) {
		this.registryEventTable.putItem(convertedItem);
	}
	
}
