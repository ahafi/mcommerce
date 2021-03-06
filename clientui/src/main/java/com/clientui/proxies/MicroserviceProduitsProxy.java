package com.clientui.proxies;

import com.clientui.beans.ProductBean;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

//@FeignClient(name = "microservice-produits", url = "localhost:9001")
//@FeignClient(name = "microservice-produits") // call directly services
//@FeignClient(name = "zuul-server") // call services by ZULL
@FeignClient(name = "api-gateway", url = "localhost:9201")
@RibbonClient(name = "microservice-produits")
public interface MicroserviceProduitsProxy {

 // @PostMapping(value = "/Produits") call directely 
	@GetMapping(value = "/microservice-produits/Produits") // call by ZULL
    List<ProductBean> listeDesProduits();

    /*
    * Notez ici la notation @PathVariable("id") qui est différente de celle qu'on utlise dans le contrôleur
    **/
   // @GetMapping( value = "/Produits/{id}")
    @GetMapping( value = "/microservice-produits/Produits/{id}")
    ProductBean recupererUnProduit(@PathVariable("id") int id);



}
