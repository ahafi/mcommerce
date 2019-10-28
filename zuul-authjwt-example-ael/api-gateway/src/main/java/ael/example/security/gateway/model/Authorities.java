package ael.example.security.gateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class Authorities {
	
	
	private static final long serialVersionUID = -3295618803288063735L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_role_id;
	@Column
	private String role;
	
	@ManyToOne
    @JoinColumn
	private DAOUser user;
	
    public Authorities() {
 
    }
 
    public Authorities(String role) {
        this.role = role;
    }
 
    public Authorities(String role, DAOUser user) {
        this.user = user;
        this.role = role;
    }

	public long getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(long user_role_id) {
		this.user_role_id = user_role_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public DAOUser getUser() {
		return user;
	}

	public void setUser(DAOUser user) {
		this.user = user;
	}
	

}
