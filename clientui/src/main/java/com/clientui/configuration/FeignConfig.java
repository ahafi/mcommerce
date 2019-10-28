package com.clientui.configuration;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

import com.clientui.controller.ClientController;
import com.clientui.proxies.*;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
//https://howtoprogram.xyz/2016/11/22/basic-authentication-open-feign/ 
@Configuration
public class FeignConfig {

	private static final String HTTP_BIN_URL = "http://httpbin.org/";

	public static final String USERNAME = "ael"; // ael or admin

	public static final String PASSWORD = "ael"; // ael or admin
	
	public ThreadLocal<String> myThreadLocal = new ThreadLocal<String>();
	

	 
	// private String token = (String) myThreadLocal.get(); 
			 
// transporter le login et mdp via basic authentification 
	@Bean
	public BasicAuthRequestInterceptor mBasicAuthRequestInterceptor() {
		//for ZULL 1er
		//return new BasicAuthRequestInterceptor("utilisateur", "mdp");
		
		//for ZULL 2eme
		return new BasicAuthRequestInterceptor(USERNAME, PASSWORD);
	}
	

	//récuprer le token depuis la session et l'envoyer à ZUUL dans chaque request 
    @Bean
    public RequestInterceptor requestTokenBearerInterceptor(HttpSession session) {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
            	List<String> tokens = (List<String>) session.getAttribute("MY_SESSION_TOKENS");
            	//String token = (String) myThreadLocal.get();
            	
            	if (tokens!=null) {
            		String token = tokens.get(0);
                	if (token !=null) {
                    requestTemplate.header("Authorization", token);
                	}
            	}
            	
            }
        };
    }
     
    
    
//    @Bean
//    AuthInterceptor authFeign() {
//        return new AuthInterceptor();
//    }
//    
//    
//    class AuthInterceptor implements RequestInterceptor {
//
//        @Override
//        public void apply(RequestTemplate template) {
//            template.header("Authorization", token);
//
//        }
//
//    }
    
    
    
    
	
//    @Bean
//    public BasicAuthRequestInterceptor requestInterceptor() {
//    	
//    	
//    	
//        @Override
//        public void apply(RequestTemplate template) {
//            template.header("Authorization", String myThreadLocal.get(0));
//          }
//        
//       
//    }
	
    
    
	/*
	 * .encoder((Encoder) new Jackson2JsonEncoder())

		        .decoder((Decoder) new Jackson2JsonDecoder())
	 */
	

//	@Bean
//	public AuthStatus getAuthenticatedUser() {
//
//
//
//		BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(USERNAME,
//
//		        PASSWORD);
//
//		HttpBinResource bookResource = Feign.builder().requestInterceptor(interceptor)
//
//		        .target(HttpBinResource.class);
//
//		return bookResource.getAuthenticatedUser();
//
//
//
//	}
//	
	
	
	

//	@Bean
//	public OAuth2FeignRequestInterceptor requestInterceptor() {
//		OAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
//		OAuth2ProtectedResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
//		resourceDetails.setUsername("username");
//		resourceDetails.setPassword("password");
//		return new OAuth2FeignRequestInterceptor(clientContext, resourceDetails);
//	}
//	
//	
//	@Value("${security.jwt.token}")
//    private String jwtToken;
//

	

}