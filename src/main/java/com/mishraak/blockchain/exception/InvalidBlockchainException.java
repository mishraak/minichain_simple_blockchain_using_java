/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardobork.blockchain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author leonardobork
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidBlockchainException extends RuntimeException{
    
    public InvalidBlockchainException(){
        super();
    }
    
    public InvalidBlockchainException(String message){
        super(message);
    }
}
