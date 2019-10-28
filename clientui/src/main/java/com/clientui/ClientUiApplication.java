package com.clientui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NullCipher;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

@SpringBootApplication
@EnableFeignClients("com.clientui")
@EnableDiscoveryClient
public class ClientUiApplication {
	private ThreadLocal myThreadLocal = new ThreadLocal();
	 Logger log = LoggerFactory.getLogger(this.getClass());
	 
	 @Bean
	    public Filter requestDetailsFilter() {
	        return new RequestDetailsFilter();
	    }

	public static void main(String[] args) {
		SpringApplication.run(ClientUiApplication.class, args);

	/*	Feign.builder()
				.contract(new SpringMvcContract())
				.errorDecoder(new CustomErrorDecoder())
				.target(MicroserviceProduitsProxy.class, "localhost:9001");
				*/
	}

	
	 private class RequestDetailsFilter implements Filter {
	        @Override
	        public void init(FilterConfig filterConfig) throws ServletException {

	        }

	        @Override
	        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	            String userName = ((HttpServletRequest)servletRequest).getHeader("Z-User-Details");
	            String pass = ((HttpServletRequest)servletRequest).getHeader("X-User-Details");
	           
	            
	            log.info("lancement de la request pour l authorisation");
	        	
	        	//jwtProxy = Feign.builder().encoder(new Jackson2JsonEncoder())
	        	
	        	Class<? extends ServletResponse>  httpServletResponse = servletResponse.getClass() ; 
	        			
	        			
	        	
	        	 String token = ((HttpServletResponse) servletResponse).getHeader("Authorization");
	        	 String token2 = ((HttpServletRequest) servletRequest).getHeader("Authorization");
	        	 
	        	 
	             if (token != null && token.startsWith("Bearer ")) {
	                 token = token.replace("Bearer ", "");
	             }
	             
	        	myThreadLocal.set(token);
	        	log.info("le token est "+token +"for my threadlocal" +myThreadLocal.get());

	            
	            
	            
	            
//	            if (pass != null)
//	                pass = decrypt(pass);
//	            SecurityContext secure = new SecurityContextImpl();
//	            //org.springframework.security.core.Authentication token = new UsernamePasswordAuthenticationToken(userName, pass);
//	            secure.setAuthentication(token);
	            
	            
	            
	          //  SecurityContextHolder.setContext(secure);
	            filterChain.doFilter(servletRequest, servletResponse);
	        }

	        @Override
	        public void destroy() {

	        }
	    }
	    private String decrypt(String str) {
	        try {
	            Cipher dcipher = new NullCipher();

	            // Decode base64 to get bytes
	            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

	            // Decrypt
	            byte[] utf8 = dcipher.doFinal(dec);

	            // Decode using utf-8
	            return new String(utf8, "UTF8");
	        } catch (javax.crypto.BadPaddingException e) {
	        } catch (IllegalBlockSizeException e) {
	        } catch (UnsupportedEncodingException e) {
	        } catch (java.io.IOException e) {
	        }
	        return null;
	    }
	
	
	
	

}
