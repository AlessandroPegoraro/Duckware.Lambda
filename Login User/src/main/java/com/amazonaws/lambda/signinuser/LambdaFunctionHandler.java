package com.amazonaws.lambda.signinuser;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.lambda.signinuser.Response;
import com.amazonaws.lambda.signinuser.SignInUser;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class LambdaFunctionHandler implements RequestHandler<SignInUser, Response> {

    @Override
    public Response handleRequest(SignInUser input, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://dynamodb.eu-west-1.amazonaws.com", "eu-west-1"))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);
        String tableName = "Users";
        Table table = dynamoDB.getTable(tableName);
        Item checkPresence = null;
        try {
        	checkPresence = table.getItem("UserID",input.getUsername());
        }catch(Exception e) {
        	return new Response(500,"-");
        }    	
    	if(checkPresence != null && checkPresence.get("Password").toString().equals(input.getPassword())) {
    		Object risposta = checkPresence.get("Workflow");
    		if(risposta != null)
    			return new Response(200,risposta.toString());
    		return new Response(200,"{}");   			
    	}
    	else {
    	    return new Response(500,"-");
    	}        	
    }
}
