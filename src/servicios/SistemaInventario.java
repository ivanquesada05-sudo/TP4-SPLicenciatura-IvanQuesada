package servicios;

import entidades.Encargado;
import entidades.Local;
import entidades.Producto;
import entidades.Stock;
import entidades.Usuario;
import persistencia.LocalDAO;
import persistencia.ProductoDAO;

import java.util.List;

// Fachada principal del sistema
// Centraliza el acceso a los distintos servicios utilizados
// por la interfaz gráfica
public class SistemaInventario {

    private final ServicioUsuario servicioUsuario;
    private final ServicioStock servicioStock;
    private final ServicioOperacion servicioOperacion;
    private final ServicioReporte servicioReporte;

    private final ProductoDAO productoDAO;
    private final LocalDAO localDAO;

    // =========================
    // CONSTRUCTOR
    // =========================

    public SistemaInventario() {

        servicioUsuario = new ServicioUsuario();
        servicioStock = new ServicioStock();
        servicioOperacion = new ServicioOperacion(servicioStock);
        servicioReporte = new ServicioReporte(
                servicioStock,
                servicioOperacion);

        productoDAO = new ProductoDAO();
        localDAO = new LocalDAO();
    }

    // =========================
    // USUARIOS
    // =========================

    // Inicia sesión con las credenciales indicadas.
    public Usuario iniciarSesion(
            String usuario,
            String contraseña) {

        return servicioUsuario.iniciarSesion(
                usuario,
                contraseña);
    }

    // Registra un nuevo usuario.
    public void agregarUsuario(Usuario usuario) {

        String tipoUsuario =
                (usuario instanceof Encargado)
                        ? "ENCARGADO"
                        : "EMPLEADO";

        servicioUsuario.agregarUsuario(
                usuario,
                tipoUsuario);
    }

    // =========================
    // PRODUCTOS Y LOCALES
    // =========================

    // Devuelve todos los productos registrados.
    public List<Producto> listarProductos() {
        return productoDAO.listarProductos();
    }

    // Devuelve todos los locales registrados.
    public List<Local> listarLocales() {
        return localDAO.listarLocales();
    }

    // =========================
    // STOCK
    // =========================

    // Devuelve el stock completo.
    public List<Stock> listarStock() {
        return servicioStock.listarStock();
    }

    // =========================
    // OPERACIONES
    // =========================

    // Registra una compra.
    public boolean registrarCompra(
            Producto producto,
            Local local,
            int cantidad,
            Usuario usuario) {

        return servicioOperacion.registrarCompra(
                producto,
                local,
                cantidad,
                usuario);
    }

    // Registra una venta.
    public boolean registrarVenta(
            Producto producto,
            Local local,
            int cantidad,
            Usuario usuario) {

        return servicioOperacion.registrarVenta(
                producto,
                local,
                cantidad,
                usuario);
    }

    // Registra una transferencia entre locales.
    public boolean registrarTransferencia(
            Producto producto,
            Local origen,
            Local destino,
            int cantidad,
            Usuario usuario) {

        return servicioOperacion.registrarTransferencia(
                producto,
                origen,
                destino,
                cantidad,
                usuario);
    }

    // =========================
    // REPORTES
    // =========================

    // Genera el reporte general del stock.
    public String generarReporte() {
        return servicioReporte.generarReporteStock();
    }

    // Genera las alertas de stock bajo.
    public String generarAlertas() {
        return servicioReporte.generarReporteAlertas();
    }

    // Genera el historial de movimientos.
    public String generarReporteMovimientos() {
        return servicioReporte.generarReporteMovimientos();
    }
}