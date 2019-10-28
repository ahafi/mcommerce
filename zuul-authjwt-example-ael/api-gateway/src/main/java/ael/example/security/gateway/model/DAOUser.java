package ael.example.security.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.*;



@Data
@EqualsAndHashCode(exclude = "authorities")
@Entity
@Table(name = "user")
public class DAOUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String username;
	@Column
	@JsonIgnore
	private String password;
	
	@Column
	private boolean enabled;

	 @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	    private Set<Authorities> authorities;

	    public DAOUser(String username, Authorities... authorities) {
	        this.username = username;
	        this.authorities = Stream.of(authorities).collect(Collectors.toSet());
	        this.authorities.forEach(x -> x.setUser(this));
	    }
	
	    public DAOUser() {
	    	 
	    }
	 
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}




	public Set<Authorities> getAuthorities() {
		return authorities;
	}




	public void setAuthorities(Set<Authorities> authorities) {
		this.authorities = authorities;
	}
	
	
	
}