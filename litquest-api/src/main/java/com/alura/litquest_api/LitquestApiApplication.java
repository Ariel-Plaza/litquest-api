package com.alura.litquest_api;

import com.alura.litquest_api.principal.Principal;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//indicamos que funcionara por la consola
public class LitquestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")  // Busca en la raíz del proyecto
                .ignoreIfMissing()
                .load();

        // Imprime para verificar que se cargó (quita después)
        System.out.println("DB_HOST cargado: " + dotenv.get("DB_HOST"));

        // Cargar como propiedades del sistema
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        SpringApplication.run(LitquestApiApplication.class, args);
	}
    // metodo que busca spring para arrancar la logica
    @Override
        public void run(String... args)throws Exception{
        Principal principal = new Principal();
        principal.ejecutarAplicacion();
    }

}
