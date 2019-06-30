package com.clientui.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	
	
	private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoqueur, Response reponse) {

        if(reponse.status() == 400 ) {
            return new ProductBadRequestException(
                    "Requête incorrecte "
            );
            // "Erreur de au format 4XX "
//        }else if(reponse.status() > 400 && reponse.status() <=499 ) {
//            return new Product4XXException(
//            		reponse.body().toString()
//            );
//        }
        } else if (reponse.status() == 404 ) {
                return new ProductNotFoundException(
                        "Produit non trouvé "
                );
            }

        return defaultErrorDecoder.decode(invoqueur, reponse);
    }
    
}
