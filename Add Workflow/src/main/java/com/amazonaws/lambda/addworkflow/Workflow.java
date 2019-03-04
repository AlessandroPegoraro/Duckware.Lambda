package com.amazonaws.lambda.addworkflow;

public class Workflow {
	private String username;
    private String password;
    private String workflowID;
    private String workflowDefinition;
    private Integer workflowIndex;
    
	public String getUsername() {
        return username;
    }
	
	public void setUsername(String username) {
        this.username=username;
    }
	
	public String getPassword() {
        return password;
    }
	
	public void setPassword(String password) {
        this.password=password;
    }
	
	public String getWorkflowID() {
        return workflowID;
    }
	
	public void setWorkflowID(String workflowID) {
        this.workflowID=workflowID;
    }
	
	public String getWorkflowDefinition() {
        return workflowDefinition;
    }
	
	public void setWorkflowDefinition(String workflowDefinition) {
        this.workflowDefinition=workflowDefinition;
    }
	
	public Integer getWorkflowIndex() {
        return workflowIndex;
    }
	
	public void setWorkflowIndex(Integer workflowIndex) {
        this.workflowIndex=workflowIndex;
    }
	
    public Workflow(String username, String password, String workflowID, String workflowDefinition, Integer workflowIndex) {
        this.username = username;
        this.password = password;
        this.workflowID = workflowID;
        this.workflowDefinition = workflowDefinition;
        this.workflowIndex = workflowIndex;
    }
    
    public Workflow() {
    	
    }
	
}
