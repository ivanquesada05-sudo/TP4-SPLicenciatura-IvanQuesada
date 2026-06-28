package persistencia;

import entidades.Empleado;
import entidades.Encargado;
import entidades.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// DAO encargado de la persistencia de Usuario
public class UsuarioDAO {

    // =========================
    // MÉTODOS
    // =========================
        
    // Construye el objeto Usuario correspondiente
    private Usuario crearUsuario(ResultSet resultado)
            throws SQLException {

        int id = resultado.getInt("id_usuario");
        String nombre = resultado.getString("nombre");
        String usuario = resultado.getString("usuario");
        String contraseña = resultado.getString("contraseña");
        String tipo = resultado.getString("tipo_usuario");

        return switch (tipo.toUpperCase()) {

            case "ENCARGADO" ->
                new Encargado(
                        id,
                        nombre,
                        usuario,
                        contraseña);

            default ->
                new Empleado(
                        id,
                        nombre,
                        usuario,
                        contraseña);
        };
    }
    
    // Busca un usuario por nombre de usuario y contraseña,
    // devuelve un Empleado o un Encargado
    public Usuario iniciarSesion(String usuario,
                                 String contraseña) {

        String sql = """
                SELECT id_usuario,
                       nombre,
                       usuario,
                       contraseña,
                       tipo_usuario
                FROM usuario
                WHERE usuario = ?
                  AND contraseña = ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setString(1, usuario);
            sentencia.setString(2, contraseña);

            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {

                return crearUsuario(resultado);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al iniciar sesión: "
                    + e.getMessage());
        }

        return null;
    }

    // Inserta un nuevo usuario.
    public boolean insertarUsuario(Usuario usuario,
                                   String tipoUsuario) {

        String sql = """
                     INSERT INTO usuario
                     (nombre,
                      usuario,
                      contraseña,
                      tipo_usuario)
                     VALUES (?,?,?,?)
                     """;

        try (Connection conexion =
                    ConexionBD.obtenerConexion();

             PreparedStatement sentencia =
                    conexion.prepareStatement(sql)) {

            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getUsuario());
            sentencia.setString(3, usuario.getContraseña());
            sentencia.setString(4, tipoUsuario);

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error al insertar usuario: "
                    + e.getMessage());

            return false;
        }
    }
}
