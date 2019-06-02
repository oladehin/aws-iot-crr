package com.ooladehin.awsiot.registry.main;


import java.util.Map;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.ooladehin.awsiot.registry.models.EventTypeEnum;
import com.ooladehin.awsiot.registry.services.CertificateEventService;
import com.ooladehin.awsiot.registry.services.ThingEventService;
import com.ooladehin.awsiot.registry.services.ThingGroupEventService;
import com.ooladehin.awsiot.registry.services.ThingTypeEventService;

public class ReplicateRegistryFunction implements RequestHandler<DynamodbEvent, Void> {   

	private static final String IOT_SOURCE_REGION = System.getenv("IOT_SOURCE_REGION");
	private static final String IOT_TARGET_REGION = System.getenv("IOT_TARGET_REGION");
	private static final String GLOBAL_TABLE_UPDATEREGION = "aws:rep:updateregion";
	
    private final AWSIot awsIoTClient;

    private final ThingEventService thingEventService;
    private final ThingTypeEventService thingTypeEventService;
    private final ThingGroupEventService thingGroupEventService;
    private final CertificateEventService certificateEventService;

    /**
     * One-time initialization of resources for this Lambda function.
     */
    public ReplicateRegistryFunction( ) {
        this.awsIoTClient = AWSIotClientBuilder.standard()
        						.withRegion(IOT_SOURCE_REGION)
        						.withCredentials(new DefaultAWSCredentialsProviderChain())
        						.build();
        this.thingEventService = new ThingEventService(this.awsIoTClient);
        this.thingTypeEventService = new ThingTypeEventService(this.awsIoTClient);
        this.thingGroupEventService = new ThingGroupEventService(this.awsIoTClient);
        this.certificateEventService = new CertificateEventService(this.awsIoTClient);
    }

    /****
     * Primary Lambda Function handler which loops through DynamoDB Streams and 
     * processes the event in the target region
     * 
     * @param ddbEvent
     * @param context
     */
    public Void handleRequest(DynamodbEvent ddbEvent, final Context context) {
    	LambdaLogger logger = context.getLogger();
    	
    	for (DynamodbStreamRecord record : ddbEvent.getRecords()) {
    		try {
	    		Map<String, AttributeValue> dynamoStreamRecord = record.getDynamodb().getNewImage();
				logger.log("Processing Event:" + dynamoStreamRecord.toString());
	
	    		if(shouldBeReplicated(dynamoStreamRecord)) {
					processRegistryEvent(dynamoStreamRecord, logger);
				}
    		}catch(Exception e) {
    			logger.log(e.getMessage().toString());
    		}
    	}
		return null;
    }

    private void processRegistryEvent(Map<String, AttributeValue> dynamoStreamRecord, LambdaLogger logger) {
		String eventType = dynamoStreamRecord.get("eventType").getS();
		EventTypeEnum eventTypeEnum = EventTypeEnum.fromEnumValue(eventType);
		logger.log("Processing Request:" + eventType);

    	switch (eventTypeEnum) {
	        case THING:  
				logger.log("Processing Thing Request: " + dynamoStreamRecord.toString());
				this.thingEventService.processThingEvent(dynamoStreamRecord);
	            break;
	        case THING_GROUP:
	        	logger.log("Processing Thing Group Request: " + dynamoStreamRecord.toString());
				this.thingGroupEventService.processGroupEvent(dynamoStreamRecord);
	            break;
	        case THING_GROUP_MEMBERSHIP: 
	        	logger.log("Processing Thing Group Membeship Request: " + dynamoStreamRecord.toString());
				this.thingGroupEventService.processGroupMembershipEvent(dynamoStreamRecord);
	            break;
	        case THING_TYPE:  
	        	logger.log("Processing Thing Type Request: " + dynamoStreamRecord.toString());
				this.thingTypeEventService.processThingTypeEvent(dynamoStreamRecord);
	            break;
	        case THING_TYPE_ASSOCIATION:  
	        	logger.log("Processing Thing Type Association Request: " + dynamoStreamRecord.toString());
				this.thingTypeEventService.processThingTypeAssociationEvent(dynamoStreamRecord);
	            break;
	        case CERTIFICATE:
	        	logger.log("Processing Certificate Request: " + dynamoStreamRecord.toString());
				this.certificateEventService.processCertificateEvent(dynamoStreamRecord, logger);
	            break;
	        default:
	        	logger.log("Unable to process IoT Event Record: " + dynamoStreamRecord.toString());
	        	break;        	
    	}
    }
    
    /****
     * Since this implementation uses Global Tables, the logic always checks if the DynamoDB update was
     * created from its own region and if so it can safely ignore it.
     * @param dynamoStreamRecord
     * @return
     */
	private boolean shouldBeReplicated(Map<String, AttributeValue> dynamoStreamRecord) {
		String updateRegion = dynamoStreamRecord.get(GLOBAL_TABLE_UPDATEREGION).getS();
		if(updateRegion.equalsIgnoreCase(IOT_TARGET_REGION)) {
			return true;
		}
		return false;
	}

}

