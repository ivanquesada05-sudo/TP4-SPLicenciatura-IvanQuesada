package entidades;

public class Producto {

    // =========================
    // ATRIBUTOS
    // =========================
    
    private int idProducto;
    private String nombre;
    private int stockMinimo;

    // =========================
    // CONSTRUCTOR
    // =========================
    
    public Producto(int idProducto,
                    String nombre,
                    int stockMinimo) {

        this.idProducto = idProducto;
        this.nombre = nombre;
        this.stockMinimo = stockMinimo;
    }

    // =========================
    // GETTERS
    // =========================    
    
    public String getNombre() {
        return nombre;
    }
    
    public int getIdProducto() {
        return idProducto;
    }
    
    public int getStockMinimo() {
        return stockMinimo;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
