/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardobork.blockchain.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author leonardobork
 */
public class LocalDateTimeConverter implements JsonSerializer<LocalDateTime>{

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @Override
    public JsonElement serialize(LocalDateTime t, java.lang.reflect.Type type, JsonSerializationContext jsc) {
        return new JsonPrimitive(FORMATTER.format(t));
    }
}
