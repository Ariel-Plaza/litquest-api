package com.alura.litquest_api.principal;

import com.alura.litquest_api.client.GutendexClient;
import com.alura.litquest_api.model.Autor;
import com.alura.litquest_api.model.Data;
import com.alura.litquest_api.model.DatosLibro;
import com.alura.litquest_api.model.Libro;
import com.alura.litquest_api.repository.LibroRepository;
import com.alura.litquest_api.service.ConversorDatos;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Principal {

    private GutendexClient gutendexClient;
    private ConversorDatos conversorDatos;
    private LibroRepository repositorio;

    private Scanner teclado = new Scanner(System.in);

    public Principal(LibroRepository repository){
        this.repositorio = repository;
        gutendexClient = new GutendexClient();
        conversorDatos = new ConversorDatos();
    }

    public void ejecutarAplicacion() {

        String mensaje = """
                ----------
                Elija la opción a travéz de su número:
                1- Buscar libro por titulo
                2- Listar libros registrados
                3- Listar autores registrados
                4- Listar autores vivos en un determinado año
                5- Listar libros por idioma
                6- Salir
                """;
        Integer opcion = -1;

        while (opcion != 0) {
            try {
                System.out.println(mensaje);
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el nombre del libro que desea buscar:");
                        var libro = teclado.nextLine();
                        buscarLibroAPI(libro);


                        break;
                    case 2:
                        System.out.println("opcion2");
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        System.out.println("opcion3");
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        System.out.println("opcion4");
                        break;
                    case 5:
                        System.out.println("opcion5");
                        break;
                    case 6:
                        teclado.close();
                        System.exit(0);
                        return;
                    default:
                        System.out.println("La opcion ingresada no es válida...");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar una de las opciones");
                teclado.nextLine();
                opcion = -1;
            }
        }
    }

//CASE 1
    private void buscarLibroAPI(String librobuscado){
        List<DatosLibro> libros =  obtenerDataAPI(librobuscado);
        if(libros !=null && !libros.isEmpty()){
            String resultado =  obtenerLibroPorNombre(libros, librobuscado);
            System.out.println(resultado);
        }else{
            System.out.println("No se encontro el libro: " + librobuscado);
        }
    }

    //Conexion API
    private List<DatosLibro> obtenerDataAPI(String libro){
        try {
            String json = gutendexClient.get(libro);
            Data datos = conversorDatos.convierteDatos(json, Data.class);
            return datos.resultados();

        } catch (Exception e) {
            System.out.println("Error al recuperar la informacion de la API" + e.getMessage());
            return null;
        }
    }

    private String obtenerLibroPorNombre(List<DatosLibro> libros, String libroBuscado) {
        return libros.stream()
                .filter(l -> l.titulo().toUpperCase().contains(libroBuscado.toUpperCase()))
                .findFirst()
                .map(l -> {
                    Libro libroParaBD = new Libro(l);
                    repositorio.save(libroParaBD);

                    return l.toString();
                })
                .orElse("Libro no encontrado");
    }

    //CASE 2 Listar Libros
    private void listarLibrosRegistrados(){
        List<Libro> libros = repositorio.findAll();
        if(libros.isEmpty()){
            System.out.println("No existen libros registrados");
        }else{
            System.out.println();
            libros.forEach(System.out::println);
        }
    }

    ///CASE 3  Listar autores registrados

    private void listarAutoresRegistrados(){
        List<Libro> libros = repositorio.findAll();
        List<Autor> autores = libros.stream()
                .flatMap(l -> l.getAutores().stream())
                .distinct()
                .collect(Collectors.toList());

        // IMPORTANTE: Forzar la carga de libros ANTES de imprimir
        autores.forEach(a -> a.getLibros().size());

        // Ahora sí imprime con los libros cargados
        autores.forEach(System.out::println);
    }

    //CASE 4 Listar autores vivos en un determinado año

    //CASE 5  Listar libros por idioma







};




