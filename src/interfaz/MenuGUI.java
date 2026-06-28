package interfaz;

import entidades.*;
import servicios.SistemaInventario;

import javax.swing.*;
import java.util.List;

// Interfaz gráfica del sistema
// Presenta las opciones disponibles según rol del usuario
public class MenuGUI {


    // =========================
    // ATRIBUTOS
    // =========================
    private final SistemaInventario sistema; 
    private final Usuario usuario; 
    private boolean cerrarSesion; 

    // Constructor del menú.
    public MenuGUI(SistemaInventario sistema,
                    Usuario usuario){
        
         this.sistema = sistema;
         this.usuario = usuario;
         this.cerrarSesion = false;
         
    }
    
    // Muestra el menú correspondiente al rol autenticado.
    public void mostrarMenu() {

        if (usuario instanceof Encargado) {
            menuEncargado();
        } else {
            menuEmpleado();
        }
    }
    
    // =========================
    // MENÚ ENCARGADO
    // =========================
    
    private void menuEncargado() {

        int opcion;

        do {

            opcion = leerOpcion("""
                    MENÚ ENCARGADO

                    1 - Registrar Compra
                    2 - Registrar Transferencia
                    3 - Reporte General de Stock
                    4 - Alertas
                    5 - Movimientos
                    6 - Cerrar sesión
                    0 - Salir
                    """);

            switch (opcion) {

                case 1 -> compra();
                case 2 -> transferencia();
                case 3 -> reporteStock();
                case 4 -> alertas();
                case 5 -> movimientos();
                case 6 -> {
                    cerrarSesion = true;
                    opcion = 0;
                }   

                case 0 -> { }
                default -> JOptionPane.showMessageDialog(
                        null,
                        "Opción inválida.");
            }        
        } while (opcion != 0);
    }

    // =========================
    // MENÚ EMPLEADO
    // =========================
    private void menuEmpleado() {

        int opcion;

        do {

            opcion = leerOpcion("""
                    MENÚ EMPLEADO

                    1 - Registrar Venta
                    2 - Reporte General de stock          
                    3 - Alertas
                    4 - Cerrar sesión
                    0 - Salir
                    """);

            switch (opcion) {

                case 1 -> venta();
                case 2 -> reporteStock();
                case 3 -> alertas();
                case 4 -> {
                    cerrarSesion = true;
                    opcion = 0;
                }

                case 0 -> { }
                default -> JOptionPane.showMessageDialog(
                        null,
                        "Opción inválida.");
            }
        } while (opcion != 0);
    }

    // =========================
    // MÉTODOS AUXILIARES
    // =========================

    // Lee una opción ingresada por el usuario.
    private int leerOpcion(String mensaje) {

        try {

            String dato = JOptionPane.showInputDialog(mensaje);

            if (dato == null) {
                return 0;
            }

            return Integer.parseInt(dato);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Debe ingresar un número válido.");

            return -1;
        }
    }
    
    // Solicita una cantidad válida.
    private int solicitarCantidad() {
        try {

            String dato = JOptionPane.showInputDialog("Cantidad:");

            if (dato == null) {
                return -1;
            }

            int cantidad = Integer.parseInt(dato);

            if (cantidad <= 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "La cantidad debe ser mayor que cero.");

                return -1;
            }

            return cantidad;

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Cantidad inválida.");
            return -1;
        }

    }

    // Muestra el resultado de una operación.
    private void mostrarResultado(boolean correcto,
                                  String mensajeOk,
                                  String mensajeError) {

        JOptionPane.showMessageDialog(
                null,
                correcto ? mensajeOk : mensajeError);
    }
    
    private void mostrarTexto(String texto) {

    JOptionPane.showMessageDialog(
            null,
            texto);

    }
    
    // =========================
    // OPERACIONES
    // =========================

    private void compra() {
         registrarOperacion(true);
    }

    private void venta() {
         registrarOperacion(false);
    }
    
    // Registra una compra o una venta.
    private void registrarOperacion(boolean esCompra) {

        Producto producto = seleccionarProducto();
        if (producto == null)
            return;
        
        Local local = seleccionarLocal();
        if (local == null) 
            return;
                       
        int cantidad = solicitarCantidad();                          
        if (cantidad <= 0) 
            return;
        
        boolean correcto = esCompra
        ? sistema.registrarCompra(producto, local, cantidad, usuario)
        : sistema.registrarVenta(producto, local, cantidad, usuario);

    mostrarResultado(
            correcto,
            esCompra
                    ? "Compra registrada correctamente."
                    : "Venta registrada correctamente.",
            esCompra
                    ? "No fue posible registrar la compra."
                    : "No fue posible registrar la venta.");
    }
    
    // Registra una transferencia entre locales.
    private void transferencia() {

        Producto producto = seleccionarProducto();
            if (producto == null) return;

        JOptionPane.showMessageDialog(
                null,
                "Seleccione el local de origen.");

        Local origen = seleccionarLocal();
            if (origen == null) return;

        JOptionPane.showMessageDialog(
                null,
                "Seleccione el local de destino.");

        Local destino = seleccionarLocal();
            if (destino == null) return;
              if (origen.getIdLocal() == destino.getIdLocal()) {
                  JOptionPane.showMessageDialog(
                      null,
                      "El origen y el destino deben ser diferentes.");
            return;
        }

        int cantidad = solicitarCantidad();
        if (cantidad <= 0) return;

        boolean correcto = sistema.registrarTransferencia(
                producto,
                origen,
                destino,
                cantidad,
                usuario);

        mostrarResultado(
                correcto,
                "Transferencia realizada correctamente.",
                "No fue posible realizar la transferencia.");
    }
    
    // =========================
    // REPORTES
    // =========================

    private void reporteStock() {
        mostrarTexto(sistema.generarReporte());
    }
    
    // Muestra las alertas de stock.
    private void alertas() {

        String alertas = sistema.generarAlertas();

        if (alertas == null || alertas.isBlank()) {
             alertas = "No existen alertas de stock.";
        }

    JOptionPane.showMessageDialog(
            null,
            alertas);

    }
    
    private void movimientos() {
        mostrarTexto(sistema.generarReporteMovimientos());
    }

    // =========================
    // SELECCIÓN DINÁMICA
    // =========================

    private int seleccionarOpcion(String titulo, List<String> opciones) {

    if (opciones.isEmpty()) {

        JOptionPane.showMessageDialog(
                null,
                "No existen " + titulo.toLowerCase() + " registrados.");

        return -1;
    }

    StringBuilder sb = new StringBuilder(titulo + " disponibles:\n\n");

    for (int i = 0; i < opciones.size(); i++) {

        sb.append(i + 1)
          .append(" - ")
          .append(opciones.get(i))
          .append("\n");
    }

    return leerOpcion(sb.toString());
    }
    
    private Producto seleccionarProducto() {

    List<Producto> productos = sistema.listarProductos();

    int opcion = seleccionarOpcion(
            "Productos",
            productos.stream()
                     .map(Producto::getNombre)
                     .toList());

    if (opcion < 1 || opcion > productos.size()) {
        return null;
    }

    return productos.get(opcion - 1);
    }
    
    private Local seleccionarLocal() {

    List<Local> locales = sistema.listarLocales();

    int opcion = seleccionarOpcion(
            "Locales",
            locales.stream()
                   .map(Local::getNombre)
                   .toList());

    if (opcion < 1 || opcion > locales.size()) {
        return null;
    }

    return locales.get(opcion - 1);
    }

    // =========================
    // CONTROL DE SESIÓN
    // =========================

    public boolean isCerrarSesion() {
       return cerrarSesion;
    }
}