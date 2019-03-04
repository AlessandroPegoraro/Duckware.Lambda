package com.amazonaws.lambda.deleteworkflow;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.lambda.deleteworkflow.Response;
import com.amazonaws.lambda.deleteworkflow.Workflow;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;
import java.util.List;

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
    	if(checkPresence != null && input.getWorkflowIndex() != null && input.getWorkflowIndex()>=0) {
    		List<Map<String,String>> listlength = null;
    		listlength = checkPresence.getList("Workflow");
    		if(listlength == null || input.getWorkflowIndex()>=listlength.size()) {
    			return new Response(500);
    		}else {
    			UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("UserID", input.getUsername())
    					.withUpdateExpression("REMOVE Workflow["+input.getWorkflowIndex()+"]");
    			try {
    				table.updateItem(updateItemSpec);
    				return new Response(200);
    			}catch(Exception e) {
    				return new Response(500);
    			}
    		}
    	}
    	return new Response(500);
    }
}
