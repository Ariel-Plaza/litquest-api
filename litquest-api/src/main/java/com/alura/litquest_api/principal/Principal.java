package com.alura.litquest_api.principal;

import com.alura.litquest_api.client.GutendexClient;
import com.alura.litquest_api.model.Autor;
import com.alura.litquest_api.model.Data;
import com.alura.litquest_api.model.DatosLibro;
import com.alura.litquest_api.repository.LibroRepository;
import com.alura.litquest_api.service.ConversorDatos;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;



public class Principal {

    private GutendexClient gutendexClient;
    private Data data;
    private ConversorDatos conversorDatos;
    private DatosLibro datosLibro;
    private LibroRepository repositorio;

    private Scanner teclado = new Scanner(System.in);

    public Principal(LibroRepository repository){
        this.repositorio = repository;
    }

    public void ejecutarAplicacion() {
        gutendexClient = new GutendexClient();
        conversorDatos = new ConversorDatos();


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
                        break;
                    case 3:
                        System.out.println("opcion3");
                        break;
                    case 4:
                        System.out.println("opcion4");
                        break;
                    case 5:
                        System.out.println("opcion5");
                        break;
                    case 6:
                        System.exit(0);
                        break;
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


    private void buscarLibroAPI(String librobuscado){
        List libros =  obtenerDataAPI(librobuscado);
        System.out.println(libros);
        if(libros !=null && !libros.isEmpty()){
            String resultado =  obtenerLibroPorNombre(libros, librobuscado);
            System.out.println(resultado);

        }else{
            System.out.println("No se encontro el libro: " + librobuscado);
        }
    }

    //Conexion API
    public List<DatosLibro> obtenerDataAPI(String libro){
        try {
            String json = gutendexClient.get(libro);
            Data datos = conversorDatos.convierteDatos(json, Data.class);
            return datos.resultados();

        } catch (Exception e) {
            System.out.println("Error al recuperar la informacion de la API" + e.getMessage());
            return null;
        }
    }

//    buscar por el nombre del libro, -> Titulo, Autor, Idioma, Numero descargas.
    public String obtenerLibroPorNombre(List<DatosLibro> libro, String libroBuscado) {
        return libro.stream()
                .filter(l -> l.titulo().toUpperCase().contains(libroBuscado.toUpperCase()))
                .findFirst()
                .map(l -> {
                    //validacion que existe autor
                    String nombreAutor = l.autores().isEmpty() ? "Autor desconocido" : l.autores().get(0).nombre();
                    String idioma = l.idiomas().isEmpty() ? "Desconocido" : l.idiomas().get(0);

                    return String.format("----- LIBRO ------\n" +
                                    "Titulo: %s\n" +
                                    "Autor: %s\n" +
                                    "Idioma: %s\n" +
                                    "Descargas: %s",
                            l.titulo(), nombreAutor, idioma, l.numerodescargas());
                })
                .orElse("Libro no encontrado");
    };
};

