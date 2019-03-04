package com.amazonaws.lambda.signinuser;

public class Response {
	private Integer statusCode;
	private String userData;
	
	public Integer getStatusCode() {
        return statusCode;
    }
	
	public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
	
	public String getUserData() {
        return userData;
    }
	
	public void setUserData(String userData) {
        this.userData = userData;
    }

    public Response(Integer statusCode, String userData) {
        this.statusCode = statusCode;
        this.userData = userData;
    }
    
    public Response() {
        
    }
    
}
