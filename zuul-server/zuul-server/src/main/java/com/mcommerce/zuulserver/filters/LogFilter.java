package com.mcommerce.zuulserver.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class LogFilter extends ZuulFilter {

	 Logger log = LoggerFactory.getLogger(this.getClass());
	 
	 /*
	  * pre : permet d'exécuter du code avant la redirection de la requête vers sa destination finale. 
		post : permet d'exécuter du code après que la requête a été redirigée. 
		route : permet d'agir sur la façon de rediriger les requêtes. 
		error : permet d'agir en cas d'erreur lors de la redirection de la requête.
	  */
	 
	  @Override
	  public String filterType() {
	      return "pre";
	  }

	  //dans votre API Gateway ZUUL, vous aurez forcément des dizaines de filtres. Cette méthode détermine l'ordre d'exécution de ce filtre.
	  @Override
	  public int filterOrder() {
	      return 1;
	  }
	  
	  //permet d'écrire les conditions qui doivent être remplies pour que le filtre s'exécute.
	  @Override
	  public boolean shouldFilter() {
	      return true;
	  }
	  
	  //c'est ici que va la logique de votre filtre
	  @Override
	  public Object run() throws ZuulException {

	      HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
	      //2019-08-03 22:16:43.312  INFO 16004 --- [nio-9004-exec-5] c.m.zuulserver.filters.LogFilter  : **** Requête interceptée ! L'URL est : http://localhost:9004/microservice-produits/Produits 
	      log.info("**** Requête interceptée ! L'URL est : {} " , req.getRequestURL());

	      return null;
	  }
}
