package com.clientui.proxies;

import com.clientui.beans.PaiementBean;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "microservice-paiement", url = "localhost:9003")
//@FeignClient(name = "zuul-server")
@FeignClient(name = "api-gateway", url = "localhost:9201")
@RibbonClient(name = "microservice-paiement")
public interface MicroservicePaiementProxy {

   // @PostMapping(value = "/paiement") call directely 
    @PostMapping(value = "/microservice-paiement/paiement") // call by ZULL
    ResponseEntity<PaiementBean> payerUneCommande(@RequestBody PaiementBean paiement);

}
