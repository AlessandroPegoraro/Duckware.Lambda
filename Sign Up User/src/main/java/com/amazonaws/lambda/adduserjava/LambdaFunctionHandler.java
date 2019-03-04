package com.amazonaws.lambda.adduserjava;

import java.util.HashMap;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class LambdaFunctionHandler implements RequestHandler<SignUpUser, Response> {

    @Override
    public Response handleRequest(SignUpUser input, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://dynamodb.eu-west-1.amazonaws.com", "eu-west-1"))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);
        String tableName = "Users";
        Table table = dynamoDB.getTable(tableName);
        Item prova = null;
        try {
        	prova = table.getItem("UserID",input.getUsername());
        	if(prova != null) {
        		return new Response(500);
        	}
        	else {
        		Item item=new Item()
                		.withPrimaryKey("UserID", input.getUsername())
                		.withString("Password", input.getPassword())
                		.withList("Workflow", new HashMap<String, String>());
                table.putItem(item);
        	    return new Response(200);
        	}        	
        }catch (Exception e) {
        	return new Response(500);
        }
    }
}
     
