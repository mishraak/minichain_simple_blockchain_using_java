/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardobork.blockchain.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leonardobork.blockchain.domain.Block;
import com.leonardobork.blockchain.http.HttpHandlerApacheImplementationService;
import com.leonardobork.blockchain.utility.LocalDateTimeConverter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author leonardobork
 */
@Service
public class NetworkService {

    @Autowired
    HttpHandlerApacheImplementationService httpHandler;

    private static List<String> PEERS = new ArrayList<String>();

    public void addPeer(String url) {
        PEERS.add(url);
    }

    public void broadcast(List<Block> blockchain) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
                .create();

        String jsonBlockchain = gson.toJson(blockchain);

        PEERS.forEach((url) -> {
            httpHandler.post(url, jsonBlockchain);
        });
    }
}
