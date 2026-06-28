package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// DAO encargado de la persistencia de Operaciones
public class OperacionDAO {

    // =========================
    // MÉTODOS
    // =========================
    
    public boolean insertarCompra(int idProducto,
                                  int idLocal,
                                  int cantidad,
                                  int idUsuario) {

        return insertar(
                "COMPRA",
                idProducto,
                idLocal,
                -1,
                cantidad,
                idUsuario);
    }

    public boolean insertarVenta(int idProducto,
                                 int idLocal,
                                 int cantidad,
                                 int idUsuario) {

        return insertar(
                "VENTA",
                idProducto,
                idLocal,
                -1,
                cantidad,
                idUsuario);
    }

    public boolean insertarTransferencia(int idProducto,
                                         int idLocalOrigen,
                                         int idLocalDestino,
                                         int cantidad,
                                         int idUsuario) {

        return insertar(
                "TRANSFERENCIA",
                idProducto,
                idLocalOrigen,
                idLocalDestino,
                cantidad,
                idUsuario);
    }

    // Inserta una operación y su detalle correspondiente
    private boolean insertar(String tipoOperacion,
                             int idProducto,
                             int idLocalOrigen,
                             int idLocalDestino,
                             int cantidad,
                             int idUsuario) {

        String sqlOperacion = """
                INSERT INTO operacion
                (cantidad,
                 tipo_operacion,
                 id_usuario_responsable)
                VALUES (?, ?, ?)
                """;

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            conexion.setAutoCommit(false);

            PreparedStatement sentenciaOperacion =
                    conexion.prepareStatement(
                            sqlOperacion,
                            Statement.RETURN_GENERATED_KEYS);

            sentenciaOperacion.setInt(1, cantidad);
            sentenciaOperacion.setString(2, tipoOperacion);
            sentenciaOperacion.setInt(3, idUsuario);

            sentenciaOperacion.executeUpdate();

            ResultSet resultado =
                    sentenciaOperacion.getGeneratedKeys();

            if (!resultado.next()) {

                conexion.rollback();
                return false;
            }

            int idOperacion = resultado.getInt(1);

            switch (tipoOperacion) {

                case "COMPRA" -> {

                    PreparedStatement sentenciaDetalle =
                            conexion.prepareStatement("""
                                    INSERT INTO compra
                                    VALUES (?, ?, ?)
                                    """);

                    sentenciaDetalle.setInt(1, idOperacion);
                    sentenciaDetalle.setInt(2, idProducto);
                    sentenciaDetalle.setInt(3, idLocalOrigen);

                    sentenciaDetalle.executeUpdate();
                }

                case "VENTA" -> {

                    PreparedStatement sentenciaDetalle =
                            conexion.prepareStatement("""
                                    INSERT INTO venta
                                    VALUES (?, ?, ?)
                                    """);

                    sentenciaDetalle.setInt(1, idOperacion);
                    sentenciaDetalle.setInt(2, idProducto);
                    sentenciaDetalle.setInt(3, idLocalOrigen);

                    sentenciaDetalle.executeUpdate();
                }

                case "TRANSFERENCIA" -> {

                    PreparedStatement sentenciaDetalle =
                            conexion.prepareStatement("""
                                    INSERT INTO transferencia
                                    VALUES (?, ?, ?, ?)
                                    """);

                    sentenciaDetalle.setInt(1, idOperacion);
                    sentenciaDetalle.setInt(2, idProducto);
                    sentenciaDetalle.setInt(3, idLocalOrigen);
                    sentenciaDetalle.setInt(4, idLocalDestino);

                    sentenciaDetalle.executeUpdate();
                }
            }

            conexion.commit();
            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error registrar operación: "
                    + e.getMessage());

            return false;
        }
    }

    public List<String> listarMovimientos() {

        List<String> movimientos = new ArrayList<>();

        String sql = """
                SELECT o.id_operacion,
                       o.tipo_operacion,
                       o.cantidad,
                       u.nombre,
                       o.fecha
                FROM operacion o
                JOIN usuario u
                     ON u.id_usuario = o.id_usuario_responsable
                ORDER BY o.id_operacion DESC
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion.prepareStatement(sql);
             ResultSet resultado =
                     sentencia.executeQuery()) {

            while (resultado.next()) {

                movimientos.add(
                        resultado.getInt("id_operacion")
                        + " | "
                        + resultado.getString("tipo_operacion")
                        + " | "
                        + resultado.getInt("cantidad")
                        + " | "
                        + resultado.getString("nombre")
                        + " | "
                        + resultado.getTimestamp("fecha"));
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error listar movimientos: "
                    + e.getMessage());
        }

        return movimientos;
    }
}