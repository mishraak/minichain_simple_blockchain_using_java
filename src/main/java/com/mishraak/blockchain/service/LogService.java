/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardobork.blockchain.service;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author leonardobork
 */
@Service
public class LogService {

    private static Logger log = Logger.getLogger("My Node");
    
    public void write(String line){    
        log.info(line);
    }
}
