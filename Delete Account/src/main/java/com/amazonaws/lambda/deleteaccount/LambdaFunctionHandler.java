package com.amazonaws.lambda.deleteaccount;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;


public class LambdaFunctionHandler implements RequestHandler<DeleteUser, Response> {

    @Override
    public Response handleRequest(DeleteUser input, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://dynamodb.eu-west-1.amazonaws.com", "eu-west-1"))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);
        String tableName = "Users";
        Table table = dynamoDB.getTable(tableName);
        context.getLogger().log("Input: " + input);
        Item checkPresence = null;
        
        try {
        	checkPresence = table.getItem("UserID",input.getUsername());
        }catch(Exception e) {
        	return new Response(500);
        }    	
    	if(checkPresence != null && checkPresence.get("Password").toString().equals(input.getPassword())) {
    		DeleteItemSpec deleteAccount = new DeleteItemSpec().withPrimaryKey(new PrimaryKey("UserID", input.getUsername()));
    		
    		try {
    			table.deleteItem(deleteAccount);
        		return new Response(200);    			
    		}catch(Exception e) {
    			return new Response(500);
    		}  			
    	} else {
    	    return new Response(500);
    	}        	
    }

}
