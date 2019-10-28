package com.mproduits.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.sampler.Sampler;

@Configuration
public class SleuthConfig {


	@Bean
    public Sampler defaultSampler(){
    	//demande que toutes les requêtes soient marquées par des ID et soient exportables vers d'autres services comme Zipkin
		//return new AlwaysSampler(); 
        return Sampler.ALWAYS_SAMPLE;
    }
}