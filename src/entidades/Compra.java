package entidades;

//Representa una operación de compra de productos
public class Compra extends Operacion {

    public Compra(
            
        int idOperacion,
        int cantidad,
        Producto producto) {

    super(idOperacion,cantidad,producto);
    }

    @Override
    public void registrar() {
     // La persistencia de la operación
     // se realiza desde ServicioOperacion mediante OperacionDAO
    }
    
    //La actualización del stock
    //se realiza desde SistemaInventario
    @Override
    public String toString() {

    return "COMPRA | Producto: "
            + producto.getNombre()
            + " | Cantidad: "
            + cantidad;
    }
}
