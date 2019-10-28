package com.clientui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Response;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clientui.beans.CommandeBean;
import com.clientui.beans.JwtRequest;
import com.clientui.beans.JwtResponse;
import com.clientui.beans.PaiementBean;
import com.clientui.beans.ProductBean;
import com.clientui.beans.TokensList;
import com.clientui.beans.UserDTO;
import com.clientui.beans.UserReponse;
import com.clientui.proxies.AdminPageProxy;
import com.clientui.proxies.JwtProxy;
import com.clientui.proxies.MicroserviceCommandeProxy;
import com.clientui.proxies.MicroservicePaiementProxy;
import com.clientui.proxies.MicroserviceProduitsProxy;
import com.clientui.proxies.UserPageProxy;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@Controller
public class ClientController {

    @Autowired
    private MicroserviceProduitsProxy ProduitsProxy;

    @Autowired
    private MicroserviceCommandeProxy CommandesProxy;

    @Autowired
    private MicroservicePaiementProxy paiementProxy;
    
    @Autowired
    public JwtProxy jwtProxy;
    
    @Autowired
    public AdminPageProxy adminPageProxy;
    
    @Autowired
    public UserPageProxy userPageProxy;
    
    @Autowired
    public ObjectMapper mapper ;
    @Autowired
    public Gson gson = new Gson();
    
    // access httpservltet from api rest
//    @Context 
//    private HttpServletResponse httpServletResponse;
//
//    @Context 
//    private HttpServletRequest httpServletRequest;
    
    Logger log = LoggerFactory.getLogger(this.getClass());

    private ThreadLocal<String> myThreadLocal = new ThreadLocal<String>();

    String admin = "admin";
    
    //acceder à la page d'admin
    @RequestMapping("/adminPage")
    public String adminUser(Model model){
    	
    	String value = adminPageProxy.getValueAdmin();
    	if (value !=null) {
    		model.addAttribute("messageAdmin", value);
    	}
    	return "PageAdmin";
    }
    
    @RequestMapping("/userPage")
    public String User(Model model){
    	
    	String value = userPageProxy.getValueUser();
    	if (value !=null) {
    		model.addAttribute("messageUser", value);
    	}
    	return "PageUser";
    }

    // s'authentifier avec basic authtification and filter
    @RequestMapping("/authent")
    public String admin(Model model, HttpServletRequest request){
    	
    	log.info("lancement de la request pour l authorisation");
			
    	ResponseEntity<JwtResponse> jwtResponse = (ResponseEntity<JwtResponse>) jwtProxy.getToken();
    	
    	//String token =  (String) myThreadLocal.get();
    	if (jwtResponse != null) {
    		request.getSession().invalidate();
    		HttpHeaders httpHeaders = jwtResponse.getHeaders();
    		List<String> tokens = httpHeaders.get("Authorization");
    		String token = tokens.get(0);
         if (token != null && token.startsWith("Bearer ")) {
             token = token.replace("Bearer ", "");
             model.addAttribute("token", token);
             log.info("le token est "+token);
             myThreadLocal.set(token);
             request.getSession().setAttribute("MY_SESSION_TOKENS", tokens);
         }
    	}
    	@SuppressWarnings("unchecked")
		List<String> tokens = (List<String>) request.getSession().getAttribute("MY_SESSION_TOKENS");

		if (tokens == null) {
			tokens = new ArrayList<>();
		}
		model.addAttribute("sessionTokens", tokens);
    	
    	//jwtProxy = Feign.builder().encoder(new Jackson2JsonEncoder())
    	
   // 	FeignConfig service = new FeignConfig();

	//	AuthStatus authStatus = service.getAuthenticatedUser();
//
//		String user = authStatus.getUser();
//		boolean var = authStatus.isAuthenticated();           
//		
	
        return "Admin";
    }

    //with body and controler
    @RequestMapping(value = "/authent2")
    public String authenticate( Model model, HttpServletRequest request) throws ParseException, IOException{

    	JwtRequest jwtRequest = new JwtRequest();

        //on reseigne les détails du user 
    	jwtRequest.setPassword("ael");
    	jwtRequest.setUsername("ael");
    	
    	ResponseEntity<?> jwtResponse  = jwtProxy.getTokenbyzuul(jwtRequest);
    			
    	
//    	if (jwtResponse != null) {
//    		request.getSession().invalidate();
//    		List<String> tokens = new ArrayList<>();
//    		String token = jwtResponse.getToken();
     	if (jwtResponse.getBody() != null) {
     		 mapper = new ObjectMapper();
    		request.getSession().invalidate();
    		List<String> tokens = new ArrayList<>();
    		//String body = (String) jwtResponse.getBody();
    		LinkedHashMap map = (LinkedHashMap) jwtResponse.getBody();
    	//Gson library from Google to convert any object to JSON string. Here is an example to convert LinkedHashMap to json - 
    		String json = gson.toJson(map,LinkedHashMap.class);
    		String jsonString = new JSONObject(map).toString();
    		//String jsonString2 = new Gson().toJson(map, Map.class);
    		//JwtResponse sample = new ObjectMapper().readValue(jwtResponse.getBody(), TokensList.class);
    		//JwtResponse pojo = mapper.convertValue(jwtResponse.getBody(), JwtResponse.class);
    		
    		String token = (String) map.get("token");
    		
    	//	String responseXml = EntityUtils.toString((HttpEntity) jwtResponse);
    	//	String toto = EntityUtils.toString((HttpEntity) jwtResponse.getBody(), "UTF-8");
    	
    		//JwtResponse sample = new ObjectMapper().readValue(jwtResponse.getBody(), TokensList.class);
    		
         if (token != null) {
           //  token = token.replace("Bearer ", "");
             model.addAttribute("token", token);
             log.info("le token est "+token);
             myThreadLocal.set(token);
             token = "Bearer "+token;
             tokens.add(token);
             request.getSession().setAttribute("MY_SESSION_TOKENS", tokens);
         }
    	}
    	@SuppressWarnings("unchecked")
		List<String> tokens = (List<String>) request.getSession().getAttribute("MY_SESSION_TOKENS");

		if (tokens == null) {
			tokens = new ArrayList<>();
		}
		model.addAttribute("sessionTokens", tokens);
		
        return "Admin";
    }
    
    
  
    
    @RequestMapping("/createUser")
    public String saveUser(Model model){
    	UserDTO userDTO = new UserDTO();
    	userDTO.setUsername("ael");
    	userDTO.setPassword("ael");
    	userDTO.setRole("user");
    	UserReponse response = jwtProxy.getRegister(userDTO);;
        model.addAttribute("user", response);
        return "register";
    }
    
    @RequestMapping("/createAdmin")
    public String saveAdmin(Model model){
    	UserDTO userDTO = new UserDTO();
    	userDTO.setUsername("admin");
    	userDTO.setPassword("admin");
    	userDTO.setRole("admin");
    	UserReponse response = jwtProxy.getRegister(userDTO);;
        model.addAttribute("user", response);
//    	ResponseEntity<?> response = jwtProxy.getRegister(userDTO);;
//        model.addAttribute("user", response.getBody());
        return "register";
    }
    
    @PostMapping("/persistMessage")
	public String persistMessage(@RequestParam("tkn") String tkn, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> tkns = (List<String>) request.getSession().getAttribute("MY_SESSION_TOKENS");
		if (tkns == null) {
			tkns = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_TOKENS", tkns);
		}
		tkns.add(tkn);
		request.getSession().setAttribute("MY_SESSION_TOKENS", tkns);
		return "redirect:/authent";
	}

	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/authent";
	}
    
    

    /*
    * Étape (1)
    * Opération qui récupère la liste des produits et on les affichent dans la page d'accueil.
    * Les produits sont récupérés grâce à ProduitsProxy
    * On fini par rentourner la page Accueil.html à laquelle on passe la liste d'objets "produits" récupérés.
    * */
    @RequestMapping("/")
    public String accueil(Model model){
    	
    	log.info("Envoi requête vers microservice-produits");
    	
        List<ProductBean> produits =  ProduitsProxy.listeDesProduits();

        model.addAttribute("produits", produits);

        return "Accueil";
    }

    /*
    * Étape (2)
    * Opération qui récupère les détails d'un produit
    * On passe l'objet "produit" récupéré et qui contient les détails en question à  FicheProduit.html
    * */
    @RequestMapping("/details-produit/{id}")
    public String ficheProduit(@PathVariable int id,  Model model){

        ProductBean produit = ProduitsProxy.recupererUnProduit(id);

        model.addAttribute("produit", produit);

        return "FicheProduit";
    }

    /*
    * Étape (3) et (4)
    * Opération qui fait appel au microservice de commande pour placer une commande et récupérer les détails de la commande créée
    * */
    @RequestMapping(value = "/commander-produit/{idProduit}/{montant}")
    public String passerCommande(@PathVariable int idProduit, @PathVariable Double montant,  Model model){


        CommandeBean commande = new CommandeBean();

        //On renseigne les propriétés de l'objet de type CommandeBean que nous avons crée
        commande.setProductId(idProduit);
        commande.setQuantite(1);
        commande.setDateCommande(new Date());

        //appel du microservice commandes grâce à Feign et on récupère en retour les détails de la commande créée, notamment son ID (étape 4).
        CommandeBean commandeAjoutee = CommandesProxy.ajouterCommande(commande);

        //on passe à la vue l'objet commande et le montant de celle-ci afin d'avoir les informations nécessaire pour le paiement
        model.addAttribute("commande", commandeAjoutee);
        model.addAttribute("montant", montant);

        return "Paiement";
    }

    /*
    * Étape (5)
    * Opération qui fait appel au microservice de paiement pour traiter un paiement
    * */
    @RequestMapping(value = "/payer-commande/{idCommande}/{montantCommande}")
    public String payerCommande(@PathVariable int idCommande, @PathVariable Double montantCommande, Model model){

        PaiementBean paiementAExcecuter = new PaiementBean();

        //on reseigne les détails du produit
        paiementAExcecuter.setIdCommande(idCommande);
        paiementAExcecuter.setMontant(montantCommande);
        paiementAExcecuter.setNumeroCarte(numcarte()); // on génère un numéro au hasard pour simuler une CB

        // On appel le microservice et (étape 7) on récupère le résultat qui est sous forme ResponseEntity<PaiementBean> ce qui va nous permettre de vérifier le code retour.
        ResponseEntity<PaiementBean> paiement = paiementProxy.payerUneCommande(paiementAExcecuter);

        Boolean paiementAccepte = false;
        //si le code est autre que 201 CREATED, c'est que le paiement n'a pas pu aboutir.
        if(paiement.getStatusCode() == HttpStatus.CREATED)
                paiementAccepte = true;

        model.addAttribute("paiementOk", paiementAccepte); // on envoi un Boolean paiementOk à la vue

        return "confirmation";
    }

    //Génére une serie de 16 chiffres au hasard pour simuler vaguement une CB
    private Long numcarte() {

        return ThreadLocalRandom.current().nextLong(1000000000000000L,9000000000000000L );
    }
}
