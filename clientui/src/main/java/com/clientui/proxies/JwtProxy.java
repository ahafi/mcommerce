package com.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.clientui.beans.JwtRequest;
import com.clientui.beans.UserDTO;
import com.clientui.beans.UserReponse;

import feign.Headers;
//https://github.com/OpenFeign/feign

//@FeignClient(name = "microservice-commandes", url = "localhost:9002")
//@FeignClient(name = "zuul-server")

@FeignClient(name = "api-gateway", url = "localhost:9201")
public interface JwtProxy {

		
	//@Body("{\"username\":\"admin\", \"password\":\"admin\"}")
    // @PostMapping(value = "/commandes") call directely 
	@Headers({
	    "Content-Type:" +MediaType.APPLICATION_JSON_VALUE })
	@PostMapping(value = "/login"  ) // call by ZULL  , produces = {MediaType.APPLICATION_JSON_VALUE}
	ResponseEntity<?> getToken();
	
	//JwtResponse
	@Headers({
	    "Content-Type:" +MediaType.APPLICATION_JSON_VALUE })
	@PostMapping(value = "/authenticate"  ) 
	ResponseEntity<?> getTokenbyzuul(@RequestBody JwtRequest jwtRequest);
	
	
	@Headers({
	    "Content-Type:" +MediaType.APPLICATION_JSON_VALUE })
	@PostMapping(value = "/register"  ) 
	UserReponse getRegister(@RequestBody UserDTO userDTO);
	
//   @PostMapping(value = "/login"  ) 
//   AuthStatus getAuthenticateUser();
    
    
//    @Headers({
//        "Content-Type:" + MediaType.APPLICATION_JSON_UTF8_VALUE, "userIdentifier: {userIdentifier}"
//    })
//    @RequestLine("POST /rootpath/{code}/unblock")
//    Boolean unblock(
//        @Param("userIdentifier") String userIdentifier,
//        @Param("code") String promocode,
//        BookingDTO booking);
//
//
//    static PromocodeClient connect() {
//        return Feign.builder()
//            .encoder(new GsonEncoder())
//            .decoder(new GsonDecoder())
//            .target(PromocodeClient.class, Urls.SERVICE_URL.toString());
//             //Url.SERVICE_URL = http://localhost:8082/1.0
//    }
//    
    
}


