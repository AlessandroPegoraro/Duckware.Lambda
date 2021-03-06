package com.amazonaws.lambda.changepassword;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Workflow, Response> {

    @Override
    public Response handleRequest(Workflow input, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://dynamodb.eu-west-1.amazonaws.com", "eu-west-1"))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);
        String tableName = "Users";
        Table table = dynamoDB.getTable(tableName);
        Item checkPresence = null;
    	checkPresence = table.getItem("UserID",input.getUsername());
    	if(checkPresence != null && checkPresence.get("Password").toString().equals(input.getPassword()) && input.getNewPassword() != null && !input.getNewPassword().isEmpty()) {
    		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("UserID", input.getUsername())
					.withUpdateExpression("set Password = :wf") 
					.withValueMap(new ValueMap().with(":wf", input.getNewPassword()));
			try {
				table.updateItem(updateItemSpec);
				return new Response(200);
			}catch(Exception e) {
				return new Response(500);
			}
    	}        
        return new Response(500);
    }

}
