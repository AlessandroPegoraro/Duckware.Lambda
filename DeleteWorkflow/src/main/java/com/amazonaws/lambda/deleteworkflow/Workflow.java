package com.amazonaws.lambda.deleteworkflow;

public class Workflow {
	private String username;
    private String password;
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
	
	public Integer getWorkflowIndex() {
        return workflowIndex;
    }
	
	public void setWorkflowIndex(Integer workflowIndex) {
        this.workflowIndex=workflowIndex;
    }
	
    public Workflow(String username, String password, Integer workflowIndex) {
        this.username = username;
        this.password = password;
        this.workflowIndex = workflowIndex;
    }
    
    public Workflow() {
    	
    }
	
}
