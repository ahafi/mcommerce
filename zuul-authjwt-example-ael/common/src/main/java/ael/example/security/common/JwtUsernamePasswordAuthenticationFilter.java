package ael.example.security.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Authenticate the request to url /login by POST with json body '{ username, password }'.
 * If successful, response the client with header 'Authorization: Bearer jwt-token'.
 *
 * @author ael 2019/10/18
 */
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtAuthenticationConfig config;
    private final ObjectMapper mapper;
    public ThreadLocal<String> myThreadLocal = new ThreadLocal<String>();
    public  User u= new User();

    public JwtUsernamePasswordAuthenticationFilter(JwtAuthenticationConfig config, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(config.getUrl(), "POST"));
        setAuthenticationManager(authManager);
        this.config = config;
        this.mapper = new ObjectMapper();
    }
    
//get login from basic authentification 
    public String[] login(HttpServletRequest req) {
    final String authorization = req.getHeader("Authorization");
    String[] values = new String[2];
    
    if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
        // Authorization: Basic base64credentials
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
          values = credentials.split(":", 2);
    	}
	return values;
    }
    
    
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse rsp)
            throws AuthenticationException, IOException {
      
    	//User u = mapper.readValue(req.getInputStream(), User.class);
    	
    	String[] values = login(req);
    	u.setUsername(values[0]);
    	u.setPassword(values[1]);
    	if (u.getUsername()== null || u.getUsername() ==null) {
			return SecurityContextHolder.getContext().getAuthentication();
    	}
    	
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                u.getUsername(), u.getPassword(), Collections.emptyList()
        ));
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse rsp, FilterChain chain,
                                            Authentication auth) throws UnsupportedEncodingException {
        Instant now = Instant.now();
        final int MAX_AGE_SECONDS =60 * 60 * 24;//1day
        String token; 
        
    	if (u.getUsername() != null && u.getUsername() !=null) {
    	
          final Resource resource = new ClassPathResource("public.txt");
          String publicKey = null;
          try {
          publicKey = IOUtils.toString(resource.getInputStream());
          } catch (final IOException e) {
          throw new RuntimeException(e);
          }

         token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, publicKey.getBytes())
                .compact();
       
         token = config.getPrefix() + " " + token;
        
    	}else
    	{
    		  token = req.getHeader(config.getHeader());
    	}
    	// j'ai défenie 3 façoon pour stocker le token afin de le renvoyer ay client 
    	 myThreadLocal.set(token);
    	 rsp.addHeader(config.getHeader(), token);
         Cookie cookie = new Cookie(config.getHeader(), URLEncoder.encode( token, "UTF-8" )   );
         cookie.setPath("/");
         cookie.setMaxAge(MAX_AGE_SECONDS);  // 24h in seconds  
         cookie.setHttpOnly(false);
         cookie.setSecure(false);
         rsp.addCookie(cookie);
    	
    }

    @Getter
    @Setter
    private static class User {
        private String username, password;
    }
}
