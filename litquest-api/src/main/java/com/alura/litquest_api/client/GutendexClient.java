package com.alura.litquest_api.client;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component  // Permite que Spring inyecte esta clase en otras
public class GutendexClient {

    private final HttpClient client;  // Cliente HTTP reutilizable
    private final static String APIURL = "https://gutendex.com/books/";

    public GutendexClient() {
        // Crea el cliente HTTP una sola vez
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)  // Sigue redirecciones automáticamente
                .build();
    }

    public String get() throws IOException, InterruptedException {

        // Crea la petición HTTP GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIURL)) // URL a la que hacer petición
                .GET()
                .build();

        // Envía la petición y recibe respuesta
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()  // Convierte respuesta a String
        );

        // Retorna el cuerpo de la respuesta (JSON)
        return response.body();
    }
}