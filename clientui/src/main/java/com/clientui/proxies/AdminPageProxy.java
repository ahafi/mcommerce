package com.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.clientui.beans.PaiementBean;

@FeignClient(name = "api-gateway", url = "localhost:9201")
public interface AdminPageProxy {

	@GetMapping(value = "/backend/admin") // call by ZULL
    String getValueAdmin();
	
}
