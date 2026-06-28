package persistencia;

import entidades.Local;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO encargado de la persistencia de Local
public class LocalDAO {

    // =========================
    // MÉTODOS
    // =========================
    public List<Local> listarLocales() {

        List<Local> lista = new ArrayList<>();

        String sql = """
            SELECT id_local, nombre
            FROM local
        """;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                Local l = new Local(
                        resultado.getInt("id_local"),
                        resultado.getString("nombre")
                );

                lista.add(l);
            }

        } catch (SQLException e) {
            System.out.println("Error listar locales: " + e.getMessage());
        }

        return lista;
    }
    
    public Local buscarPorId(int id) {

        String sql = """
            SELECT id_local, nombre
            FROM local
            WHERE id_local = ?
        """;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {

                return new Local(
                        resultado.getInt("id_local"),
                        resultado.getString("nombre")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error buscar local: " + e.getMessage());
        }

        return null;
    }
}
