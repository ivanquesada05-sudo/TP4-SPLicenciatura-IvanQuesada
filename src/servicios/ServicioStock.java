package servicios;

import entidades.Local;
import entidades.Producto;
import entidades.Stock;
import persistencia.StockDAO;

import java.util.List;

// Servicio encargado de la lógica de negocio
// relacionada con el stock
public class ServicioStock {

    // DAO responsable de la persistencia de Stock
    private final StockDAO stockDAO;

    // =========================
    // CONSTRUCTOR
    // =========================

    public ServicioStock() {
        this.stockDAO = new StockDAO();
    }

    // =========================
    // CONSULTAS
    // =========================

    // Devuelve el stock completo registrado.
    public List<Stock> listarStock() {
        return stockDAO.listar();
    }

    // Busca el stock de un producto en un local determinado.
    public Stock buscarStock(
            Producto producto,
            Local local) {

        return stockDAO.buscar(
                producto.getIdProducto(),
                local.getIdLocal());
    }

    // =========================
    // ACTUALIZACIÓN DE STOCK
    // =========================

    public boolean aumentarStock(
            Producto producto,
            Local local,
            int cantidad) {

        Stock stock = buscarStock(producto, local);

        if (stock == null) {
            return false;
        }

        Stock nuevoStock = new Stock(
                producto,
                local,
                stock.getCantidad() + cantidad);

        return stockDAO.actualizarCantidad(nuevoStock);
    }

    public boolean disminuirStock(
            Producto producto,
            Local local,
            int cantidad) {

        Stock stock = buscarStock(producto, local);

        if (stock == null) {
            return false;
        }

        if (stock.getCantidad() < cantidad) {
            return false;
        }

        Stock nuevoStock = new Stock(
                producto,
                local,
                stock.getCantidad() - cantidad);

        return stockDAO.actualizarCantidad(nuevoStock);
    }
}