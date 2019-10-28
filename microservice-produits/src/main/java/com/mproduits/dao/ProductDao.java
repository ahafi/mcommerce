package com.mproduits.dao;

import com.mproduits.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// http://localhost:9090/h2-console/. Dans le champ JDBC URL, saisissez  jdbc:h2:mem:testdb afin de configurer la connexion 
//vers la base de données testdb située en mémoire vive.
//https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html 
//https://docs.spring.io/spring-data/data-jpa/docs/1.1.x/reference/html/#jpa.query-methods.query-creation 

@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{
}
