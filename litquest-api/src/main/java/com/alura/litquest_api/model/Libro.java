package com.alura.litquest_api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;

    private String idiomas;
    private Integer numerodescargas;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //Tabla intermedia
    @JoinTable(
            //nombre tabla
            name = "libro_autor",
            //columna libro
            joinColumns = @JoinColumn(name = "libro_id"),
            //columna autor
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        //evalua si la lista es difernete a null , la lista es diferente a vacio
        this.idiomas = datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty()
                //union con ,
                ? String.join(",", datosLibro.idiomas())
                //si es falso rellena con ""
                : "";
        this.numerodescargas = datosLibro.numerodescargas();

        if (datosLibro.autores() != null && !datosLibro.autores().isEmpty()) {
            this.autores = datosLibro.autores().stream()
                    .map(datosAutor -> new Autor(datosAutor)) // Llama al constructor Autor(DatosAutor)
                    .collect(Collectors.toList());
        }
    }



    @Override
    public String toString() {

        String nombreAutor = autores.stream()
                .map(a -> a.getNombre())
                .collect(Collectors.joining(","));
        return String.format("----- LIBRO ------\n" +
                        "Titulo: %s\n" +
                        "Autor: %s\n" +
                        "Idioma: %s\n" +
                        "Descargas: %s\n",
                titulo, nombreAutor, idiomas, numerodescargas);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumerodescargas() {
        return numerodescargas;
    }

    public void setNumerodescargas(Integer numerodescargas) {
        this.numerodescargas = numerodescargas;
    }

    public List<Autor>  getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autor) {
        this.autores = autores;
    }
}