package com.clientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.clientui")
@EnableDiscoveryClient
public class ClientUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientUiApplication.class, args);

	/*	Feign.builder()
				.contract(new SpringMvcContract())
				.errorDecoder(new CustomErrorDecoder())
				.target(MicroserviceProduitsProxy.class, "localhost:9001");
				*/
	}


}
