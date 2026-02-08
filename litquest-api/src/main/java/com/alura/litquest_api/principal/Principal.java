package com.alura.litquest_api.principal;

import com.alura.litquest_api.client.GutendexClient;
import com.alura.litquest_api.model.Autor;
import com.alura.litquest_api.model.Data;
import com.alura.litquest_api.model.DatosLibro;
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

    private Scanner teclado = new Scanner(System.in);
    //Conexion API
    public List<DatosLibro> obtenerDataAPI(){

        try {
            String json = gutendexClient.get();
            Data datos = conversorDatos.convierteDatos(json, Data.class);
            List<DatosLibro> libros = extraerLibros(datos);
//            System.out.println(libros);
            return  libros;


    //            System.out.println(dataconvertida);
    //            List<DatosLibro> libros = extraerLibros(dataconvertida);
    //            System.out.println(libros);
    //            List<Autor> autor = obtenerAutoresPorTitulo(libros, "Frankenstein; Or, The Modern Prometheus");
    //            System.out.println(autor);

        } catch (Exception e) {
            System.out.println("Error al recuperar la informacion de la API" + e.getMessage());
            return null;
        }
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
                        List libros =  obtenerDataAPI();
                        System.out.println("Ingrese el nombre del libro que desea buscar:");
                        var libro = teclado.nextLine();
                        String libroBuscado =  obtenerLibroPorNombre(libros, libro);
                        System.out.println(libroBuscado);
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




    public Data convertirDatos(String json ){

        return conversorDatos.convierteDatos(json, Data.class);
    }

    public List<DatosLibro> extraerLibros(Data data) {
        return data.resultados();
    }

//    buscar por el nombre del libro, -> Titulo, Autor, Idioma, Numero descargas.
    public String obtenerLibroPorNombre(List<DatosLibro> libro, String libroBuscado) {
        return libro.stream()
                .filter(l -> l.titulo().toUpperCase().contains(libroBuscado.toUpperCase()))
                .findFirst()
                .map(l -> String.format("----- LIBRO ------\n" +
                                "Titulo: %s\n" +
                                "Autor: %s\n" +
                                "Idioma: %s\n" +
                                "Descargas: %s",
                        l.titulo(), l.autores().get(0).nombre(), l.idiomas().get(0), l.numerodescargas()))
                .orElse("Libro no encontrado");
    };
};





//
//    public List<Autor> obtenerAutoresPorTitulo(List<DatosLibro> listaDeLibros, String tituloBuscado) {
//        return listaDeLibros.stream()
//                // filtra por el titulo, convierte el titulo en mayuscula, y busca en datoslibro el titulo que contenga el buscado
//                .filter(l -> l.titulo().toUpperCase().contains(tituloBuscado.toUpperCase()))
//                // obtener el primero que coincida
//                .findFirst()
//                // Si lo encuentra navega hacia la lista de autores
//                .map(l -> l.autores())
//                //Si no hubo coincidencias, devolvemos una lista vacía (evita errores)
//                .orElse(Collections.emptyList());
//    }
//}
