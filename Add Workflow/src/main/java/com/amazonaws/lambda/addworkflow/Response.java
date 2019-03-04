package com.amazonaws.lambda.addworkflow;

public class Response {
	private Integer statusCode;
	
	public Integer getStatusCode() {
        return statusCode;
    }
	
	public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Response(Integer statusCode) {
        this.statusCode = statusCode;
    }
    
    public Response() {
        
    }
    
}
