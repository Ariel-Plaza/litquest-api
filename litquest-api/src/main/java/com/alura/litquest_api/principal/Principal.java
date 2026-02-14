package com.alura.litquest_api.principal;

import com.alura.litquest_api.client.GutendexClient;
import com.alura.litquest_api.model.Autor;
import com.alura.litquest_api.model.Data;
import com.alura.litquest_api.model.DatosLibro;
import com.alura.litquest_api.model.Libro;
import com.alura.litquest_api.repository.LibroRepository;
import com.alura.litquest_api.service.ConversorDatos;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
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
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        System.out.println("Ingrese el año vivo de autor(es) que desea buscar:");
                        Integer annioBusqueda = teclado.nextInt();
                        buscarAutoresVivosPorFecha(annioBusqueda);
                        break;
                    case 5:
                        listarLibrosPorIdioma();
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
    @Transactional
    private void listarAutoresRegistrados(){
        List<Libro> libros = repositorio.findAll();
        List<Autor> autores = libros.stream()
                .flatMap(l -> l.getAutores().stream())
                .distinct()
                .collect(Collectors.toList());
        if (autores.isEmpty()) {
            System.out.println("No existen autores registrados");
            return;
        }
        autores.forEach(a -> a.getLibros().size());
        autores.forEach(System.out::println);
    }

    //CASE 4 Listar autores vivos en un determinado año
    @Transactional
    private void buscarAutoresVivosPorFecha(Integer annioBusqueda){

        try{
            List<Autor> autoresVivos = repositorio.findAutoresVivosEnAnio(annioBusqueda);
            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + annioBusqueda);
                return;
            }
            autoresVivos.forEach(a -> a.getLibros().size());
            autoresVivos.forEach(System.out::println);
        }catch(Exception e){
            System.out.println("Error al encontrar autores"+ e.getMessage());
        }
    }

    //CASE 5  Listar libros por idioma
    private void listarLibrosPorIdioma() {
        String menuIdiomas = """
            Ingrese el idioma para buscar los libros:
            es - Español
            en - Inglés
            fr - Francés
            pt - Portugués
            """;

        System.out.println(menuIdiomas);
        String idiomaSeleccionado = teclado.nextLine().toLowerCase().trim();

        if (!esIdiomaValido(idiomaSeleccionado)) {
            System.out.println("Idioma no válido. Use: es, en, fr, pt");
            return;
        }

        List<Libro> libros = repositorio.findAll();
        List<Libro> librosPorIdioma = libros.stream()
                .filter(l -> l.getIdiomas().contains(idiomaSeleccionado))
                .collect(Collectors.toList());

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + nombreIdioma(idiomaSeleccionado));
        } else {
            System.out.println("\n--- LIBROS EN " + nombreIdioma(idiomaSeleccionado).toUpperCase() + " ---");
            librosPorIdioma.forEach(System.out::println);
        }
    }

    private boolean esIdiomaValido(String idioma) {
        return idioma.equals("es") || idioma.equals("en") ||
                idioma.equals("fr") || idioma.equals("pt");
    }

    private String nombreIdioma(String codigo) {
        return switch (codigo) {
            case "es" -> "Español";
            case "en" -> "Inglés";
            case "fr" -> "Francés";
            case "pt" -> "Portugués";
            default -> codigo;
        };
    }







};




