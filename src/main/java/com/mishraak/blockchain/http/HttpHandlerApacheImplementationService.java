/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardobork.blockchain.http;

import com.leonardobork.blockchain.service.LogService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author leonardobork
 */
@Service
public class HttpHandlerApacheImplementationService implements HttpHandler {
    
    @Autowired LogService logService;

    @Override
    public int put(String url, String content) {
        int responseCode = -1;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPut request = new HttpPut(url);
            StringEntity params = new StringEntity(content, "UTF-8");
            params.setContentType("application/json");
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept", "application/json");
            request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
            request.addHeader("Accept-Language", "en-US,en;q=0.8");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            responseCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            this.logService.write("Execeção em url " + url + "; METODO: PUT; DADOS: " + e.toString());
        }

        return responseCode;
    }

    @Override
    public int post(String url, String content) {
        int responseCode = -1;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        StringEntity params = new StringEntity(content, "UTF-8");
        post.setEntity(params);
            System.out.println(post);
            HttpResponse response = httpClient.execute(post);
            return response.getStatusLine().getStatusCode();         
        } catch (Exception e) {
            this.logService.write("Execeção em url " + url + "; METODO: POST; DADOS: " + e.toString());
        }
        return responseCode;
    }

}
