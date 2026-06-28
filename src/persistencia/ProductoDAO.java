package persistencia;

import entidades.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO encargado de la persistencia de Producto
public class ProductoDAO {

    // =========================
    // MÉTODOS
    // =========================
    public List<Producto> listarProductos() {

        List<Producto> lista = new ArrayList<>();

        String sql = """
            SELECT id_producto,
                     nombre,  
                     stock_minimo
            FROM producto
        """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                Producto producto = new Producto(
                        resultado.getInt("id_producto"),
                        resultado.getString("nombre"),
                        resultado.getInt("stock_minimo")
                );

                lista.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error listar productos: " + e.getMessage());
        }

        return lista;
    }

    public Producto buscarPorId(int id) {

        String sql = """
            SELECT id_producto, 
                     nombre,  
                     stock_minimo
            FROM producto
            WHERE id_producto = ?
        """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {

                return new Producto(
                        resultado.getInt("id_producto"),
                        resultado.getString("nombre"),
                        resultado.getInt("stock_minimo")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error buscar producto: " + e.getMessage());
        }

        return null;
    }
}
