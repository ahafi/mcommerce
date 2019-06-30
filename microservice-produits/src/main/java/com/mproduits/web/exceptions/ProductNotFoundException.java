package com.mproduits.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException {


    public ProductNotFoundException(String message) {
        super(message);
    }
}



