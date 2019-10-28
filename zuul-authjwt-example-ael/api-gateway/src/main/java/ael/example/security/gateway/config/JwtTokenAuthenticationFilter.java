package ael.example.security.gateway.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ael.example.security.common.JwtAuthenticationConfig;
import ael.example.security.common.JwtTokenUtil;
import ael.example.security.gateway.dao.UserDao;
import ael.example.security.gateway.model.DAOUser;
import ael.example.security.gateway.service.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;



/**
 * Authenticate requests with header 'Authorization: Bearer jwt-token'.
 *
 * @author ael  2019/09/19
 */
@SuppressWarnings("deprecation")
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	
	

    private final JwtAuthenticationConfig config;
	private JwtTokenUtil jwtTokenUtil;
	private JwtUserDetailsService jwtUserDetailsService;
   


    
  @Autowired
    public DAOUser daoUser ;
    
    String username = null;
	String jwtToken = null;
    
    
    public JwtTokenAuthenticationFilter(JwtAuthenticationConfig config, JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.config = config;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
//first method 
//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain)
//            throws ServletException, IOException {
//        String token = req.getHeader(config.getHeader());
//        if (token != null && token.startsWith(config.getPrefix() + " ")) {
//            token = token.replace(config.getPrefix() + " ", "");
//            try {
//                 final Resource resource = new ClassPathResource("public.txt");
//                 String publicKey = null;
//                 try {
//                 publicKey = IOUtils.toString(resource.getInputStream());
//                 } catch (final IOException e) {
//                 throw new RuntimeException(e);
//                 }
//                Claims claims = Jwts.parser()
//                        .setSigningKey(publicKey.getBytes())
//                        .parseClaimsJws(token)
//                        .getBody();
//                String username = claims.getSubject();
//                
//               // UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
//              //  System.out.println("User is"+userDetails.getUsername() +"role: "+userDetails.getAuthorities());
//                
////                daoUser =  jwtUserDetailsService.loadUserByUsername1(username);
////                System.out.println("User is"+daoUser.getUsername() +"role: "+daoUser.getPassword());
//                
//                @SuppressWarnings("unchecked")
//                List<String> authorities = claims.get("authorities", List.class);
//                if (username != null) {
//                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
//                            authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//                    SecurityContextHolder.getContext().setAuthentication(auth);
//                }
//            } catch (Exception ignore) {
//                SecurityContextHolder.clearContext();
//            }
//        }
//        filterChain.doFilter(req, rsp);
//    }
    
    
    //second method une autre façon d'implimenter la méthode doFilterInternal with code revieuw 
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = req.getHeader(config.getHeader());
        if (jwtToken != null && jwtToken.startsWith(config.getPrefix() + " ")) {
        	jwtToken = jwtToken.replace(config.getPrefix() + " ", "");
            try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken, config);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
            
        }else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
        
        
     // Once we get the token validate it. first check username and if is the first authentification
     		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

     			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

     			// if token is valid configure Spring Security to manually set
     			// authentication
     			if (jwtTokenUtil.validateToken(jwtToken, userDetails,config)) {

     				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
     						userDetails, null, userDetails.getAuthorities());
     				usernamePasswordAuthenticationToken
     						.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
     				// After setting the Authentication in the context, we specify
     				// that the current user is authenticated. So it passes the
     				// Spring Security Configurations successfully.
     				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
     			}
     		}
        
        
        filterChain.doFilter(req, rsp);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
