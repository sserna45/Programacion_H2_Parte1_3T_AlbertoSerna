package paquePrincipal;

import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Hito {

    // Primero pondré los datospara conectarme a la base de datos MySQL.
    static final String URL = "jdbc:mysql://localhost:3306/cine_albertosernaa";
    static final String USER = "root";
    static final String PASSWORD = "curso";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        // Aquí creo el menú
        do {
            System.out.println("1 - Ver películas");
            System.out.println("2 - Salir");
            System.out.print("Selecciona una opción: ");
            try {
                opcion = sc.nextInt(); // Esto lee la opción que a elegido el usuario
            } catch (Exception e) {
                System.out.println("Opción no válida.");
                sc.next(); 
                continue;
            }

            // Haremos un switch para que se muestren las peliculas o que se salga del sistema, si ninguna es correcta saldrá un mesaje de opcion no valida.
            switch (opcion) {
                case 1:
                    mostrarPeliculas(); 
                    break;
                case 2:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Opción no válida."); 
            }
        } while (opcion != 2); 
        sc.close(); 
    }

    public static void mostrarPeliculas() {
        // Ahora pondré la consulta SQL que une las tablas de películas y géneros
        String sql = "SELECT p.id_pelicula, p.titulo, p.duracion, p.año_lanzamiento, " +
                     "p.edad_recomendada, g.nombre AS genero " +
                     "FROM peliculas p JOIN generos g ON p.id_genero = g.id_genero";

        try {
            // Esto conecta a la base de datos con los datos de conexion que puse arriba.
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Ahora se muestran todas las películas.
            while (rs.next()) {
                System.out.println("Código: " + rs.getString("id_pelicula"));
                System.out.println("Título: " + rs.getString("titulo"));
                System.out.println("Duración: " + rs.getInt("duracion") + " minutos");
                System.out.println("Año de lanzamiento: " + rs.getString("año_lanzamiento"));
                System.out.println("Edad recomendada: " + rs.getString("edad_recomendada"));
                System.out.println("Género: " + rs.getString("genero"));
                System.out.println("-----------------");
            }

            // Por último se cierra todo.
            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            // Si hay algún problema o error se mostrará un mensaje.
            System.out.println("Error al acceder a la base de datos: " + e.getMessage());
        }
    }
}

