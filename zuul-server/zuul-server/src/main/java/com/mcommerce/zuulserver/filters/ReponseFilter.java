package com.mcommerce.zuulserver.filters;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ReponseFilter extends ZuulFilter {

	Logger log = LoggerFactory.getLogger(this.getClass());

	// Le 2 éme filtre récupère toutes les réponses et change le code en 400
	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return false;
	}
	
	//c.m.zuulserver.filters.ReponseFilter     :  CODE HTTP 400
	@Override
	public Object run() throws ZuulException {

		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

		response.setStatus(400);

		log.info(" CODE HTTP {} ", response.getStatus());

		return null;
	}
}
