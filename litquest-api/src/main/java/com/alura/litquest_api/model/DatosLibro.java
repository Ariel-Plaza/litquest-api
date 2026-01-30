package com.alura.litquest_api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
                           @JsonAlias("title") String titulo,
                           @JsonAlias("authors") ArrayList<Autor> autores,
                           @JsonAlias("languages") List<String> idiomas,
                           @JsonAlias("download_count") Integer numerodescargas
){

}
