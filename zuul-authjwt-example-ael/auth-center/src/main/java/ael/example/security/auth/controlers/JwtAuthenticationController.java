package ael.example.security.auth.controlers;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ael.example.security.auth.Service.JwtUserDetailsService;
import ael.example.security.auth.dao.UserDao;
import ael.example.security.auth.dto.JwtRequest;
import ael.example.security.auth.dto.JwtResponse;
import ael.example.security.auth.dto.UserDTO;
import ael.example.security.auth.exceptions.ImpossibleAjouterTokenException;
import ael.example.security.auth.exceptions.ImpossibleAjouterUserException;
import ael.example.security.auth.model.DAOUser;
import ael.example.security.common.JwtAuthenticationConfig;
import ael.example.security.common.JwtTokenUtil;


@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	@Autowired 
	public HttpServletResponse rsp; 
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired 
	private JwtAuthenticationConfig config;    
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
//	@Autowired JwtAuthenticationConfig config;
	  public ThreadLocal myThreadLocal;
	  Logger log = LoggerFactory.getLogger(this.getClass());
	  
	  //générer le token depuis le filtre 1er méthode login et mdp transporté via le basic authentification 
	//@RequestBody JwtRequest authenticationRequest
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken() throws Exception {
		
		// deux façons pour récupérer le token 
		String token2 = rsp.getHeader("header");
		String token = (String) myThreadLocal.get();
		log.info("le token" +token+"letoken2"+token2);
		return ResponseEntity.ok(new JwtResponse(token2));
	}
	
	// 2 methode le login et mdp sont transporter via le body 
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)//@RequestBody JwtRequest authenticationRequest
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails, config);
		JwtResponse jwtResponse = new JwtResponse(token);
		 if(jwtResponse == null) throw new ImpossibleAjouterTokenException("Impossible de générer le token");
		
		 ResponseEntity<?> entity = new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
		 
		 return entity;
		//return ResponseEntity.ok(new JwtResponse(token));
		 //return ResponseEntity.ok().headers(responseHeaders).body(user);
		 // return new ResponseEntity<>(user, responseHeaders, HttpStatus.OK);
	}
	
	
//	@RequestMapping(value = "/employees/{id}")
//    public ResponseEntity<EmployeeVO> getEmployeeById (@PathVariable("id") int id)
//    {
//        if (id <= 3) {
//            EmployeeVO employee = new EmployeeVO(1,"Lokesh","Gupta","howtodoinjava@gmail.com");
//            return new ResponseEntity<EmployeeVO>(employee, HttpStatus.OK);
//        }
//        return new ResponseEntity(HttpStatus.NOT_FOUND);
//    }
//}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST) //@RequestBody UserDTO user
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		
		DAOUser dAOUser = userDetailsService.save(user);

        if(dAOUser == null) throw new ImpossibleAjouterUserException("Impossible d'ajouter cet user"+user.getUsername());

        return new ResponseEntity<DAOUser>(dAOUser, HttpStatus.CREATED);
		//return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			
//			  UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
//		        String name = auth.getName();
//		        String password = auth.getCredentials()
//		                .toString();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	
	
}
