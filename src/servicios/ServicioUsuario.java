package servicios;

import entidades.Usuario;
import persistencia.UsuarioDAO;

// Servicio encargado de la gestión de usuarios
// Intermediario entre interfaz y DAO
public class ServicioUsuario {

    // DAO responsable de acceso a la base de datos
    private final UsuarioDAO usuarioDAO;

    // =========================
    // CONSTRUCTOR
    // =========================
    
    public ServicioUsuario() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // =========================
    // MÉTODOS
    // =========================
    
    // Registra un nuevo usuario en la base de datos
    public boolean agregarUsuario(
            Usuario usuario, 
            String tipoUsuario) {
        
        return usuarioDAO.insertarUsuario(
                usuario, 
                tipoUsuario);
    }

    // Valida las credenciales ingresadas por el usuario
    public Usuario iniciarSesion(   
            String usuario, 
            String contraseña) {
        
        return usuarioDAO.iniciarSesion(
                usuario, 
                contraseña);
  
    }
}
