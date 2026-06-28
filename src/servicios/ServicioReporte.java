package servicios;

import entidades.Stock;

import java.util.List;

// Servicio encargado de generar los reportes del sistema
public class ServicioReporte {

    private final ServicioStock servicioStock;
    private final ServicioOperacion servicioOperacion;

    // =========================
    // CONSTRUCTOR
    // =========================

    public ServicioReporte(
            ServicioStock servicioStock,
            ServicioOperacion servicioOperacion) {

        this.servicioStock = servicioStock;
        this.servicioOperacion = servicioOperacion;
    }

    // =========================
    // REPORTE GENERAL DE STOCK
    // =========================

    public String generarReporteStock() {

        List<Stock> stocks = servicioStock.listarStock();

        StringBuilder sb = new StringBuilder();

        sb.append("=====================================\n");
        sb.append("      REPORTE GENERAL DE STOCK\n");
        sb.append("=====================================\n\n");

        if (stocks.isEmpty()) {
            sb.append("No existen productos registrados.");
            return sb.toString();
        }

        for (Stock stock : stocks) {

            sb.append("Producto : ")
              .append(stock.getProducto().getNombre())
              .append("\n");

            sb.append("Local    : ")
              .append(stock.getLocal().getNombre())
              .append("\n");

            sb.append("Cantidad : ")
              .append(stock.getCantidad())
              .append("\n");

            sb.append("Estado   : ")
              .append(stock.validarBajo()
                      ? "STOCK BAJO"
                      : "Disponible")
              .append("\n");

            sb.append("-------------------------------------\n");
        }

        return sb.toString();
    }

    // =========================
    // REPORTE DE ALERTAS
    // =========================

    public String generarReporteAlertas() {

        List<Stock> stocks = servicioStock.listarStock();

        StringBuilder sb = new StringBuilder();

        sb.append("=====================================\n");
        sb.append("      ALERTAS DE STOCK BAJO\n");
        sb.append("=====================================\n\n");

        boolean hayAlertas = false;

        for (Stock stock : stocks) {

            if (!stock.validarBajo()) {
                continue;
            }

            hayAlertas = true;

            sb.append(stock.getProducto().getNombre())
              .append(" - ")
              .append(stock.getLocal().getNombre())
              .append(" (")
              .append(stock.getCantidad())
              .append(" unidades)\n");
        }

        if (!hayAlertas) {
            sb.append("No existen productos con stock bajo.");
        }

        return sb.toString();
    }

    // =========================
    // REPORTE DE MOVIMIENTOS
    // =========================

    public String generarReporteMovimientos() {
        return servicioOperacion.generarReporteMovimientos();
    }

}