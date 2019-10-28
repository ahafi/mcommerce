package ael.example.security.auth.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ael.example.security.auth.model.Authorities;




@Repository
public interface AuthoritiesDao extends CrudRepository<Authorities, Integer> {
	
	Authorities findByRole(String role);
	
}