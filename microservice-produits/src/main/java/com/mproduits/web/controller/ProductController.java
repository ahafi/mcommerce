package com.mproduits.web.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mproduits.configurations.ApplicationPropertiesConfiguration;
import com.mproduits.dao.ProductDao;
import com.mproduits.model.Product;
import com.mproduits.web.exceptions.ProductNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//une description pour chaque API grâce à l'annotation  @Api , comme illustré ci-après :
@Api( description="API pour les opérations CRUD sur les produits.")
@RestController
public class ProductController implements HealthIndicator {

	@Autowired
	ProductDao productDao;
//curl -X GET "http://localhost:9001/Produits/1" -H "accept: */*"
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationPropertiesConfiguration appProperties;

	// for spring actuator health endpoint si la liste est vide ou contient juste 2
	// produit renvoie status down grace http://localhost:9001/actuator/health
	@Override
	public Health health() {
		List<Product> products = productDao.findAll();
		log.info("la liste des produits:"+products.size());

		if (products.isEmpty() || products.size() == 8) {
			return Health.down().build();
		}
		return Health.up().build();
	}

//    // Affiche la liste de tous les produits disponibles
//    @GetMapping(value = "/Produits")
//    public List<Product> listeDesProduits(){
//
//        List<Product> products = productDao.findAll();
//
//        if(products.isEmpty()) throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
//
//        return products;
//
//    }

	// Affiche la liste de tous les produits disponibles
    @ApiOperation(value = "Récupère la liste des produits ")
	@GetMapping(value = "/Produits")
	public List<Product> listeDesProduits() {

		List<Product> products = productDao.findAll();

		if (products.isEmpty())
			throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");

		List<Product> listeLimitee = products.subList(0, appProperties.getLimitDeProduits());
		// String port = environment.getProperty("local.server.port");
		
		//SimpleBeanPropertyFilter est une implémentation de PropertyFilter qui permet d'établir les règles de filtrage sur un Bean 
		//serializeAllExcept qui exclut uniquement les propriétés que nous souhaitons ignorer. 
		//Inversement,la méthode filterOutAllExcept qui marque toutes les propriétés comme étant à ignorer sauf celles passées en argument.
	       SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prix");
	       //Jackson à quel Bean l'appliquer, s'appliquer à tous les Bean qui sont annotés avec monFiltreDynamique
	       FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

	       MappingJacksonValue produitsFiltres = new MappingJacksonValue(products);
	       //MappingJacksonValue. Cela permet de donner accès aux méthodes qui nous intéressent, comme setFilters
	       produitsFiltres.setFilters(listDeNosFiltres);

	     //  return produitsFiltres;
	       
	       
		log.info("Récupération de la liste des produits");

		return listeLimitee;

	}

	@Value("${app.message}")
	private String welcomeMessage;

	@GetMapping("/info")
	public String getDataBaseConnectionDetails() {
		return welcomeMessage;
	}

	// Récuperer un produit par son id
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
	@GetMapping(value = "/Produits/{id}")
	public Optional<Product> recupererUnProduit(@PathVariable int id) {

		Optional<Product> product = productDao.findById(id);

		if (!product.isPresent())
			throw new ProductNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");

		return product;
	}

	/*	
	Il nous faut également indiquer dans notre contrôleur que le produit reçu de l'utilisateur est à valider. Pour ce faire, il faut ajouter l'annotation @Valid
	public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {
	  ....
	}
	   @Autowired
	    private ProductDao productDao;
	  Iterable<Product> produits = productDao.findAll();

	@GetMapping(value = "test/produits/{recherche}")
	    public List<Product> testeDeRequetes(@PathVariable String recherche) {
	        return productDao.findByNomLike("%"+recherche+"%");
	    }
	@GetMapping(value = "test/produits/{prixLimit}")
	public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
	    return productDao.findByPrixGreaterThan(400);
	}
	@DeleteMapping (value = "/Produits/{id}")
	   public void supprimerProduit(@PathVariable int id) {
	       productDao.delete(id);
	   }
	@PutMapping (value = "/Produits")
	  public void updateProduit(@RequestBody Product product) {
	      productDao.save(product);
	  }
	@Query("SELECT id, nom, prix FROM Product p WHERE p.prix > :prixLimit")
	   List<Product>  chercherUnProduitCher(@Param("prixLimit") int prix);
	L'annotation @Param permet de spécifier le nom du paramètre que vous allez recevoir. Cette annotation est optionnelle. Si elle n'est pas utilisée, l'ordre dans lequel les arguments sont fournis est utilisé. Vous aurez alors une requête de type :
	SELECT id, nom, prix FROM Product p WHERE p.prix > ?1

*/
	
	
}
