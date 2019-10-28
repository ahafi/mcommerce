package ael.example.security.auth.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ael.example.security.auth.dao.UserDao;
import ael.example.security.auth.dto.UserDTO;
import ael.example.security.auth.model.Authorities;
import ael.example.security.auth.model.DAOUser;



@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(username);
		
		Collection<? extends GrantedAuthority> roles= new ArrayList<>();
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		if (user.getAuthorities()!= null && !user.getAuthorities().isEmpty()) {
			// le même traitemet mais avec les new de java 8
			List<String> listRole = new ArrayList <>();
			user.getAuthorities().forEach(item->listRole.add(item.getRole()));
			roles = listRole.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				roles);
	}
	// save the user with his role
	public DAOUser save(UserDTO user) {
		DAOUser newUser = new DAOUser();
		DAOUser oldUser = new DAOUser();
		oldUser = userDao.findByUsername(user.getUsername());
		if (oldUser !=null) {
			return oldUser;
		}
	
		if(user != null) {
			Authorities authorities1 = new Authorities("ROLE_USER");
			Authorities authorities2 = new Authorities("ROLE_ADMIN");
			Authorities authorities3 = new Authorities("ROLE_GUEST");
			switch(user.getRole())
	        {
	            case "admin":
	            newUser= new DAOUser(user.getUsername(),authorities1,authorities2 );
	            break;
	            case "user":
	            	newUser= new DAOUser(user.getUsername(),authorities1);
	            break;
	            default:
	            	newUser= new DAOUser(user.getUsername(),authorities3);
	            break;
	        }
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		}
		newUser.setEnabled(true);
		
		return userDao.save(newUser);
	}
}