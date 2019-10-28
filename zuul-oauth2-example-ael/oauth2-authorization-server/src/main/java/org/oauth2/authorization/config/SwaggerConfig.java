package org.oauth2.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//http://localhost:9001/swagger-ui.html#/product-controller
//http://localhost:9001/v2/api-docs  la documentation complète de votre Microservice, générée au format JSON.

//http://localhost:8081/spring-security-oauth-server/swagger-ui.html#/

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	//https://springfox.github.io/springfox/docs/current/#docket-spring-java-configuration 
	@Bean
	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any()).build();
//	}
//	
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("org.oauth2.authorization"))
				.paths(PathSelectors.regex("/spring-security-oauth-server.*")).build();
	}

}
