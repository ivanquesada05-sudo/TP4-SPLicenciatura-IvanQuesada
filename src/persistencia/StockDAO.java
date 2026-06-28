package persistencia;

import entidades.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO encargado de la persistencia de Stock
public class StockDAO {

    // =========================
    // MÉTODOS
    // =========================
    
    // Construye un objeto Stock a partir del
    private Stock crearStock(ResultSet resultado)
            throws SQLException {

        Producto producto = new Producto(
                resultado.getInt("id_producto"),
                resultado.getString("nombre_producto"),
                resultado.getInt("stock_minimo"));

        Local local = new Local(
                resultado.getInt("id_local"),
                resultado.getString("nombre_local"));

        return new Stock(
                producto,
                local,
                resultado.getInt("cantidad"));
    }
    
    public boolean insertar(Stock stock) {

        String sql = """
            INSERT INTO stock 
                     (id_producto, 
                     id_local, 
                     cantidad)
            VALUES (?, ?, ?)
        """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, 
                    stock.getProducto().getIdProducto());
            
            sentencia.setInt(2,
                    stock.getLocal().getIdLocal());
            
            sentencia.setInt(3, 
                    stock.getCantidad());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insertar stock: " + e.getMessage());
            return false;
        }
    }

    public Stock buscar(int idProducto, int idLocal) {

        String sql = """
            SELECT s.cantidad,
                   p.id_producto,
                     p.nombre AS nombre_producto, 
                     p.categoria, 
                     p.precio, 
                     p.stock_minimo,
                     l.id_local, 
                     l.nombre AS nombre_local
            FROM stock s
            JOIN producto p ON s.id_producto = p.id_producto
            JOIN local l ON s.id_local = l.id_local
            WHERE s.id_producto = ?
              AND s.id_local = ?
        """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idProducto);
            sentencia.setInt(2, idLocal);

            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                
                return crearStock(resultado);
                
            }

        } catch (SQLException e) {
            System.out.println("Error buscar stock: " + e.getMessage());
        }

        return null;
    }

    public List<Stock> listar() {

        List<Stock> lista = new ArrayList<>();

        String sql = """
            SELECT s.cantidad,
                   p.id_producto,
                     p.nombre AS nombre_producto,
                     p.categoria, 
                     p.precio,
                     p.stock_minimo,
                     l.id_local, 
                     l.nombre AS nombre_local, 
                     l.direccion
            FROM stock s
            JOIN producto p ON s.id_producto = p.id_producto
            JOIN local l ON s.id_local = l.id_local
            ORDER BY p.nombre, l.nombre
        """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                  lista.add(crearStock(resultado));
            }

        } catch (SQLException e) {
            System.out.println("Error listar stock: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarCantidad(Stock stock) {

        String sql = """
            UPDATE stock
            SET cantidad = ?
            WHERE id_producto = ? 
              AND id_local = ?
        """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1,
                    stock.getCantidad());
            
            sentencia.setInt(2, 
                    stock.getProducto().getIdProducto());
            
            sentencia.setInt(3, 
                    stock.getLocal().getIdLocal());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error actualizar stock: " + e.getMessage());
            return false;
        }
    }

    public boolean existe(int idProducto, int idLocal) {

        String sql = "SELECT 1"
                + " FROM stock "
                + "WHERE id_producto=? "
                + "AND id_local=?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idProducto);
            sentencia.setInt(2, idLocal);

            return sentencia.executeQuery().next();

        } catch (SQLException e) {
            return false;
        }
    }
}
