package entidades;

// Representa un encargado del sistema
public class Encargado extends Usuario {

    public Encargado(int idUsuario,
                     String nombre,
                     String usuario,
                     String contraseña) {

        super(idUsuario, nombre, usuario, contraseña);
    }
}
