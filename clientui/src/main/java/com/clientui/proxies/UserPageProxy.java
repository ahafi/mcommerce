package com.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "api-gateway", url = "localhost:9201")
public interface UserPageProxy {

	@GetMapping(value = "/backend/user") // call by ZULL
    String getValueUser();
}
