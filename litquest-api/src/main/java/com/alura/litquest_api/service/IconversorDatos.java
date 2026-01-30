package com.alura.litquest_api.service;

public interface IconversorDatos {
    <T> T convierteDatos(String json, Class<T> clase);
}
