package com.alura.litquest_api.principal;

import com.alura.litquest_api.client.GutendexClient;
import com.alura.litquest_api.model.Data;
import com.alura.litquest_api.service.ConversorDatos;

import java.util.List;

public class Principal {

    private GutendexClient gutendexClient;
    private Data data;
    private ConversorDatos conversorDatos;
//Conexion API
    public void ejecutarAplicacion() {
        gutendexClient = new GutendexClient();
        conversorDatos = new ConversorDatos();
        try {
            String json = gutendexClient.get();
            System.out.println(json);
            Data dataconvertida = convertirDatos(json);
            System.out.println(dataconvertida);

        } catch (Exception e) {
            System.out.println("Error al recuperar la informacion de la API" + e.getMessage());
        }
    }

    public Data convertirDatos(String json ){
        return conversorDatos.convierteDatos(json, Data.class);
    };
}
