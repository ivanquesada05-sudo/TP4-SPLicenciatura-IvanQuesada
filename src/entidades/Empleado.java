package entidades;

// Representa un empleado del sistema
public class Empleado extends Usuario {

    public Empleado(int idUsuario,
                    String nombre,
                    String usuario,
                    String contraseña) {

        super(idUsuario, nombre, usuario, contraseña);
    }
}
