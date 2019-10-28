package ael.example.security.auth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ael.example.security.auth.model.DAOUser;


//http://localhost:9090/h2-console/. Dans le champ JDBC URL, saisissez  jdbc:h2:mem:testdb afin de configurer la connexion 
//vers la base de données testdb située en mémoire vive.


@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
	
	
	@Query(" select u from DAOUser u " +
	        " where u.username = ?1")
	Optional<DAOUser> findUserWithName(String username);
}

//
//userRepository.updateLastLogin(new Date());
//@Query("select twt from Tweet twt  JOIN twt.likes as lk where lk = ?#{ principal?.username } or twt.owner = ?#{ principal?.username }")
//
//Page<Tweet> getMyTweetsAndTheOnesILiked(Pageable pageable);
//
//https://github.com/eugenp/tutorials/blob/BAEL-3285-update-readme/spring-security-mvc-boot/src/main/java/com/baeldung/data/repositories/UserRepository.java
//
//@Query("UPDATE AppUser u SET u.lastLogin=:lastLogin WHERE u.username = ?#{ principal?.username }")
//
//@Modifying
//
//@Transactional
//
//public void updateLastLogin(@Param("lastLogin") Date lastLogin);