package com.clientui.beans;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class TokensList {
	
	private Map<String, JwtResponse> users = new HashMap<>();

	@JsonAnySetter
	public void setUsers(String name, JwtResponse value) {
	this.users.put(name, value);
	    }

}
