package com.mproduits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/*
 * 
 * @ConfigurationProperties - Used to bind a class with an externalized property file. Very powerful and must be used to separate out bean classes with configuration entity class.
 *@Configuration - Creates a Spring bean of configuration stereotype.
 * @EnableConfigurationProperties - Creates a binding between a configuration entity class and Spring configuration stereotype so that after injection within a service properties can be retrieved easily.
 */

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient // for eureka 
public class MproduitsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MproduitsApplication.class, args);
	}
}
