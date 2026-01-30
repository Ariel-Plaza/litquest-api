package com.alura.litquest_api.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorDatos implements  IconversorDatos{
//  ObjectMapper es el traductor
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convierteDatos(String json, Class<T> clase){
        try{
//          ObjectMapper toma el json y ocupa la clase como manual.
            return  objectMapper.readValue(json,clase);
        }
        catch (JsonProcessingException e){
            throw new RuntimeException("No pude convertir el JSON", e);
        }
    }
}
