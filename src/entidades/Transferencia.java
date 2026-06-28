package entidades;

// Representa una transferencia de productos entre locales.
public class Transferencia extends Operacion {

    public Transferencia(
            int idOperacion,
            int cantidad,
            Producto producto) {

        super(idOperacion, cantidad, producto);
    }

    @Override
    public void registrar() {
        // La persistencia de la transferencia se realiza
        // desde ServicioOperacion mediante OperacionDAO.
    }

    @Override
    public String toString() {

        return "TRANSFERENCIA | Producto: "
                + producto.getNombre()
                + " | Cantidad: "
                + cantidad;
    }
}