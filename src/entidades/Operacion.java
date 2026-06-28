package entidades;

// Clase base que representa una operación realizada
// sobre el inventario 
// Contiene la información común
// utilizada por compras, ventas y transferencias
public abstract class Operacion {

    // =========================
    // ATRIBUTOS
    // =========================
    
    protected int idOperacion;
    protected int cantidad;
    protected Producto producto;

    // =========================
    // CONSTRUCTOR
    // =========================
    
    public Operacion(
            int idOperacion,
            int cantidad,
            Producto producto) {

        this.idOperacion = idOperacion;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    // =========================
    // GETTERS
    // =========================

    public int getIdOperacion() {
        return idOperacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    // Método que implementa cada tipo de operación.
    public abstract void registrar();
}
