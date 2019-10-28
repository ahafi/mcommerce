//package com.clientui.beans;
//
//import java.io.Serializable;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//
//public class UserR  {
//
//	private long id;
//	private String username;
//	private String password;
//	private boolean enabled;
//
//	 private Set<Authorities> authorities;
//
//	    public UserR(String username, Authorities... authorities) {
//	        this.username = username;
//	        this.authorities = Stream.of(authorities).collect(Collectors.toSet());
//	        this.authorities.forEach(x -> x.setUser(this));
//	    }
//	
//	    public UserR() {
//	    	 
//	    }
//	 
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public boolean isEnabled() {
//		return enabled;
//	}
//
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}
//
//	public Set<Authorities> getAuthorities() {
//		return authorities;
//	}
//
//	public void setAuthorities(Set<Authorities> authorities) {
//		this.authorities = authorities;
//	}
//
//	@Transient
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Transient
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Transient
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//	
//}