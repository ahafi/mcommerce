package com.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;

import com.clientui.configuration.AuthStatus;

import feign.Headers;
import feign.RequestLine;

@Headers({
    "Content-Type:" +MediaType.APPLICATION_JSON_VALUE })
@FeignClient(name = "api-gateway", url = "localhost:9201")
public  
interface HttpBinResource {



	@RequestLine("POST /login")
	AuthStatus getAuthenticatedUser();

}