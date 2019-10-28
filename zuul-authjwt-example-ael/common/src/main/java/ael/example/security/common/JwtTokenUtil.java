package ael.example.security.common;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;
	
	//validate token
		public Boolean validateToken(String token, UserDetails userDetails,JwtAuthenticationConfig config) {
			final String username = getUsernameFromToken(token, config);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token,config));
		}
	
//retrieve username from jwt token
	public String getUsernameFromToken(String token, JwtAuthenticationConfig config) {
		return getClaimFromToken(token, Claims::getSubject, config);
	}
	
			//check if the token has expired
			private Boolean isTokenExpired(String token, JwtAuthenticationConfig config) {
				final Date expiration = getExpirationDateFromToken(token, config);
				return expiration.before(new Date());
			}
			
			//retrieve expiration date from jwt token
			public Date getExpirationDateFromToken(String token, JwtAuthenticationConfig config) {
				return getClaimFromToken(token, Claims::getExpiration, config);
			}
			
			
			 public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, JwtAuthenticationConfig config) {
				 final Claims claims = getAllClaimsFromToken(token, config);
				  return claimsResolver.apply(claims);
			   }
			
			       //for retrieveing any information from token we will need the secret key (object Claims)
				  private Claims getAllClaimsFromToken(String token, JwtAuthenticationConfig config) {
					  return Jwts.parser().setSigningKey(config.getSecret().getBytes()).parseClaimsJws(token).getBody();
				  }

	//generate token for user
	public String generateToken(UserDetails userDetails, JwtAuthenticationConfig config) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails, config);
		}

		//while creating the token -
		//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
		//add authorities 
		//2. Sign the JWT using the HS512 algorithm and secret key.
		//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
		//   compaction of the JWT to a URL-safe string 
		private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails, JwtAuthenticationConfig config) {
			Instant now = Instant.now();
			return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
					.claim("authorities", userDetails.getAuthorities().stream()
	                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()) )
					.setIssuedAt(Date.from(now))
					.setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
					.signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes())
	                .compact();
					//setIssuedAt(new Date(System.currentTimeMillis())) en miliseconde
					//.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) en miliseconde
			}
}
