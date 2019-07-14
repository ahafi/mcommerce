package com.clientui.proxies;

import com.clientui.beans.CommandeBean;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "microservice-commandes", url = "localhost:9002")
@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-commandes")
public interface MicroserviceCommandeProxy {

    // @PostMapping(value = "/commandes") call directely 
    @PostMapping(value = "/microservice-commandes/commandes") // call by ZULL
    CommandeBean ajouterCommande(@RequestBody CommandeBean commande);
}
