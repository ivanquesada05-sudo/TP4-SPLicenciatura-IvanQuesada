package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Gestiona la conexión con MySQL.
public class ConexionBD {
    
    // Evita la creación de instancias.
    private ConexionBD() {
    }   
    private static final String URL =
            "jdbc:mysql://localhost:3306/clipo_inventario";

    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    public static Connection obtenerConexion() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL.");
        }

        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}