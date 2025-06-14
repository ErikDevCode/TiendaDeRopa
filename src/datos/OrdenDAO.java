package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenDAO {

    public int insertarOrden(Connection conn, Orden orden) throws SQLException {
        String sql = "INSERT INTO ordenes (codigo, total, fecha, usuarioId) VALUES (?, ?, NOW(), ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, orden.getCodigo());
            stmt.setDouble(2, orden.getTotal());
            stmt.setInt(3, orden.getUsuarioId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public void insertarItemsOrden(Connection conn, int ordenId, List<OrdenItem> items) throws SQLException {
        String sql = "INSERT INTO ordenesItems (ordenId, productoId, cantidad, precio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (OrdenItem item : items) {
                stmt.setInt(1, ordenId);
                stmt.setInt(2, item.getProductoId());
                stmt.setInt(3, item.getCantidad());
                stmt.setDouble(4, item.getPrecio());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public List<Orden> obtenerTodasLasOrdenes() {
        List<Orden> ordenes = new ArrayList<>();
        String sql = """
            SELECT o.id, o.codigo, o.total, o.fecha, u.nombreCompleto
            FROM ordenes o
            JOIN usuarios u ON o.usuarioId = u.id
        """;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Orden orden = new Orden();
                orden.setId(rs.getInt("id"));
                orden.setCodigo(rs.getString("codigo"));
                orden.setTotal(rs.getDouble("total"));
                orden.setFecha(rs.getTimestamp("fecha"));
                orden.setNombreUsuario(rs.getString("nombreCompleto"));
                ordenes.add(orden);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordenes;
    }
}