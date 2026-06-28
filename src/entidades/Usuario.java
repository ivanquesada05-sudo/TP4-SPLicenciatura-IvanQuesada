package entidades;

// Clase base para todos los usuarios del sistema.
public abstract class Usuario {

    // =========================
    // ATRIBUTOS
    // =========================
    
    private int idUsuario;
    private String nombre;
    private String usuario;
    private String contraseña;

    // =========================
    // CONSTRUCTOR
    // =========================
    
    public Usuario(
            int idUsuario,
            String nombre,
            String usuario,
            String contraseña) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    // =========================
    // GETTERS
    // =========================

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }
}
