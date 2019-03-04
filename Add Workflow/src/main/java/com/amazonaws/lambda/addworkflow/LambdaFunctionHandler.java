package com.amazonaws.lambda.addworkflow;

import com.amazonaws.lambda.addworkflow.Workflow;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.lambda.addworkflow.Response;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import java.util.Map;
import java.util.HashMap;
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
        try {
        	checkPresence = table.getItem("UserID",input.getUsername());
        	if(checkPresence == null || !checkPresence.get("Password").toString().equals(input.getPassword())){
        		return new Response(500);
        	}
        }catch(Exception e) {
        	return new Response(500);
        }
        if(input.getWorkflowID().isEmpty() || input.getWorkflowID()==null || input.getWorkflowDefinition().isEmpty() || input.getWorkflowDefinition()==null || input.getWorkflowIndex()<0 || input.getWorkflowIndex()==null) {
        	return new Response(500);
        }
        List<Map<String,String>> listlength = null;
		listlength = checkPresence.getList("Workflow");
		if(listlength == null || input.getWorkflowIndex()>listlength.size()) {
			return new Response(500);
		}else {
			Map<String,String> addWF = new HashMap<>();
			addWF.put("WorkflowID",input.getWorkflowID());
			addWF.put("WorkflowDefinition",input.getWorkflowDefinition());
        
			UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("UserID", input.getUsername())
					.withUpdateExpression("set Workflow["+input.getWorkflowIndex()+"] = :wf")
					.withValueMap(new ValueMap().with(":wf", addWF));

			try {
				table.updateItem(updateItemSpec);
				return new Response(200);
			}
			catch (Exception e) {        	
				return new Response(500);
			}
		}
    }

}
