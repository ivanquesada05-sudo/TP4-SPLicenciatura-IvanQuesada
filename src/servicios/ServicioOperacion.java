package servicios;

import entidades.Local;
import entidades.Producto;
import entidades.Stock;
import entidades.Usuario;
import persistencia.OperacionDAO;

import java.util.List;

// Servicio encargado de gestionar las operaciones
// del inventario (compras, ventas y transferencias)
public class ServicioOperacion {

    // DAO responsable de registrar las operaciones.
    private final OperacionDAO operacionDAO;

    // Servicio utilizado para actualizar el stock.
    private final ServicioStock servicioStock;

    // =========================
    // CONSTRUCTOR
    // =========================

    public ServicioOperacion(
            ServicioStock servicioStock) {

        this.servicioStock = servicioStock;
        this.operacionDAO = new OperacionDAO();
    }

    // =========================
    // COMPRA
    // =========================

    // Registra una compra e incrementa el stock.
    public boolean registrarCompra(
            Producto producto,
            Local local,
            int cantidad,
            Usuario responsable) {

        boolean actualizado =
                servicioStock.aumentarStock(
                        producto,
                        local,
                        cantidad);

        if (!actualizado) {
            return false;
        }

        return operacionDAO.insertarCompra(
                producto.getIdProducto(),
                local.getIdLocal(),
                cantidad,
                responsable.getIdUsuario());
    }

    // =========================
    // VENTA
    // =========================

    // Registra una venta y reduce el stock.
    public boolean registrarVenta(
            Producto producto,
            Local local,
            int cantidad,
            Usuario responsable) {

        boolean actualizado =
                servicioStock.disminuirStock(
                        producto,
                        local,
                        cantidad);

        if (!actualizado) {
            return false;
        }

        return operacionDAO.insertarVenta(
                producto.getIdProducto(),
                local.getIdLocal(),
                cantidad,
                responsable.getIdUsuario());
    }

    // =========================
    // TRANSFERENCIA
    // =========================

    // Transfiere stock entre dos locales.
    public boolean registrarTransferencia(
            Producto producto,
            Local origen,
            Local destino,
            int cantidad,
            Usuario responsable) {

        // Verifica que exista stock suficiente en el origen.
        Stock stockOrigen =
                servicioStock.buscarStock(producto, origen);

        if (stockOrigen == null ||
                stockOrigen.getCantidad() < cantidad) {

            return false;
        }

        // Descuenta unidades del local de origen.
        if (!servicioStock.disminuirStock(
                producto,
                origen,
                cantidad)) {

            return false;
        }

        // Agrega unidades al local de destino.
        if (!servicioStock.aumentarStock(
                producto,
                destino,
                cantidad)) {

            return false;
        }

        // Registra la operación realizada.
        return operacionDAO.insertarTransferencia(
                producto.getIdProducto(),
                origen.getIdLocal(),
                destino.getIdLocal(),
                cantidad,
                responsable.getIdUsuario());
    }

    // =========================
    // MOVIMIENTOS
    // =========================

    // Devuelve la lista de movimientos registrados.
    public List<String> listarMovimientos() {
        return operacionDAO.listarMovimientos();
    }

    // Genera el reporte de movimientos.
    public String generarReporteMovimientos() {

        List<String> movimientos =
                operacionDAO.listarMovimientos();

        StringBuilder sb = new StringBuilder();

        sb.append("MOVIMIENTOS DE INVENTARIO\n\n");

        if (movimientos.isEmpty()) {

            sb.append("No hay movimientos registrados.");

            return sb.toString();
        }

        for (String movimiento : movimientos) {
            sb.append(movimiento).append("\n");
        }

        return sb.toString();
    }
}
