package entidades;

//Gestiona la cantidad disponible  
//de un producto dentro de un local determinado
public class Stock {

    // =========================
    // ATRIBUTOS
    // =========================
    
    private Producto producto;
    private Local local;
    private int cantidad;

    // =========================
    // CONSTRUCTOR
    // =========================
    
    public Stock(Producto producto,
                 Local local,
                 int cantidad) {

        this.producto = producto;
        this.local = local;
        this.cantidad = cantidad;
    }

    // =========================
    // MÉTODOS
    // =========================    
    
    //Determina si el stock alcanzó el mínimo
    //definido
    public boolean validarBajo() {
        return this.cantidad <= producto.getStockMinimo();
    }

    // =========================
    // GETTERS
    // =========================    
    
    public Producto getProducto() {
        return producto;
    }

    public Local getLocal() {
        return local;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {

        return producto.getNombre()
                + " | "
                + local.getNombre()
                + " | "
                + cantidad;
    }
}
