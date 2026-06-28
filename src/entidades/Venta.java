package entidades;

// Representa una operación de venta de productos.
public class Venta extends Operacion {

    public Venta(
            int idOperacion,
            int cantidad,
            Producto producto) {

        super(idOperacion, cantidad, producto);
    }

    @Override
    public void registrar() {
        // La persistencia de la venta se realiza
        // desde ServicioOperacion mediante OperacionDAO.
    }

    @Override
    public String toString() {

        return "VENTA | Producto: "
                + producto.getNombre()
                + " | Cantidad: "
                + cantidad;
    }
}