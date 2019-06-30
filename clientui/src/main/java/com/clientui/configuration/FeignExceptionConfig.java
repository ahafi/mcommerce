package com.clientui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clientui.exceptions.CustomErrorDecoder;

@Configuration
	public class FeignExceptionConfig {

	    @Bean
	    public CustomErrorDecoder mCustomErrorDecoder(){
	        return new CustomErrorDecoder();
	    }
	}

	
