package com.clientui.beans;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//"data"
//})
public class JwtResponse  {

	//private static final long serialVersionUID = -8091879091924046844L;
	//@JsonProperty(value = "token")
	private  String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	
}