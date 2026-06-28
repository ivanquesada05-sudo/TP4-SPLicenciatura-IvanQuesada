package entidades;

public class Local {

    // =========================
    // ATRIBUTOS
    // =========================

    private int idLocal;
    private String nombre;

    // =========================
    // CONSTRUCTOR
    // =========================

    public Local(int idLocal,
                 String nombre) {

        this.idLocal = idLocal;
        this.nombre = nombre;       
    }

    // =========================
    // GETTERS
    // =========================

    public int getIdLocal() {
        return idLocal;
    }
    
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}