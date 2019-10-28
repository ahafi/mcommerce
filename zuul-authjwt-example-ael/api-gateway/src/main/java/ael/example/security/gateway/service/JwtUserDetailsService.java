package ael.example.security.gateway.service;

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

import ael.example.security.gateway.dao.UserDao;
import ael.example.security.gateway.model.Authorities;
import ael.example.security.gateway.model.DAOUser;





@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	
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
//			List<String> listRole = new ArrayList <>();
//			user.getAuthorities().forEach(item->listRole.add(item.getRole()));
//			roles = listRole.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			
			roles= getGAuthorities(user.getAuthorities());
//			}
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				roles);
	}
	//User(String username, String password, boolean enabled,boolean accountNonExpired, boolean credentialsNonExpired,boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
	
	
	private Collection<? extends GrantedAuthority> getGAuthorities(
			  Collection<Authorities> authorities) {
			    List<GrantedAuthority> gAuthorities
			      = new ArrayList<>();
			    for (Authorities authoritie: authorities) {
			    	gAuthorities.add(new SimpleGrantedAuthority(authoritie.getRole()));
//			    	authoritie.getPrivileges().stream()
//			         .map(p -> new SimpleGrantedAuthority(p.getName()))
//			         .forEach(gAuthorities::add);
			    }
			    return gAuthorities;
			}
	
//methode pour test utiliser dans les test unitaires
	public DAOUser loadUserByUsername1(String username) throws UsernameNotFoundException {
		String toto = null;
		DAOUser user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return user ;
	}
	
}