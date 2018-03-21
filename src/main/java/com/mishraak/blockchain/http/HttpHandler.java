/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardobork.blockchain.http;

/**
 *
 * @author leonardobork
 */
public interface HttpHandler {
    
    public int put(String url, String content);
    
    public int post(String url, String content);
}
