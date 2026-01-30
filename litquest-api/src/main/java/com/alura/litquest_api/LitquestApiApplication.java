package com.alura.litquest_api;

import com.alura.litquest_api.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//indicamos que funcionara por la consola
public class LitquestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LitquestApiApplication.class, args);
	}
    // metodo que busca spring para arrancar la logica
    @Override
        public void run(String... args)throws Exception{
        Principal principal = new Principal();
        principal.ejecutarAplicacion();
    }

}
