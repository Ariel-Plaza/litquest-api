package com.alura.litquest_api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
                           @JsonAlias("title") String titulo,
                           @JsonAlias("authors") List<DatosAutor> autores,
                           @JsonAlias("languages") List<String> idiomas,
                           @JsonAlias("download_count") Integer numerodescargas
){

    @Override
    public String toString() {
        //manejo autores vacios
        String nombreAutor = (autores != null && !autores.isEmpty())
                ? autores.get(0).nombre()
                : "Autor desconocido";
        //manejo idiomas vacios
        String idioma = (idiomas != null && !idiomas.isEmpty())
                ? idiomas.get(0)
                : "Desconocido";

        return String.format("----- LIBRO ------\n" +
                        "Titulo: %s\n" +
                        "Autor: %s\n" +
                        "Idioma: %s\n" +
                        "Descargas: %s",
                titulo, nombreAutor, idioma, numerodescargas);
    }
}
