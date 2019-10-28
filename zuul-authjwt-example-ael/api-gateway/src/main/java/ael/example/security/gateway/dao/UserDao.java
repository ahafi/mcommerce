package ael.example.security.gateway.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ael.example.security.gateway.model.DAOUser;



@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
	
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