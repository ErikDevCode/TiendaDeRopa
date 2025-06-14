package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DescuentoDAO {

    public void guardarDescuento(int marcaId, double porcentaje) {
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement("""
                INSERT INTO descuentos (marcaId, porcentaje)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE porcentaje = ?
            """);
            ps.setInt(1, marcaId);
            ps.setDouble(2, porcentaje);
            ps.setDouble(3, porcentaje);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Descuento> obtenerTodos() {
        List<Descuento> lista = new ArrayList<>();
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement("""
                SELECT d.id, d.marcaId, m.nombre, d.porcentaje
                FROM descuentos d
                JOIN marcas m ON d.marcaId = m.id
            """);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Descuento d = new Descuento();
                d.setId(rs.getInt("id"));
                d.setMarcaId(rs.getInt("marcaId"));
                d.setNombreMarca(rs.getString("nombre"));
                d.setPorcentaje(rs.getDouble("porcentaje"));
                lista.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public double obtenerDescuentoPorMarca(int marcaId) {
        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement("SELECT porcentaje FROM descuentos WHERE marcaId = ?");
            ps.setInt(1, marcaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("porcentaje");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    public boolean guardarOActualizarDescuento(int marcaId, double porcentaje) {
        String sqlSelect = "SELECT COUNT(*) FROM descuentos WHERE marcaId = ?";
        String sqlInsert = "INSERT INTO descuentos (marcaId, porcentaje) VALUES (?, ?)";
        String sqlUpdate = "UPDATE descuentos SET porcentaje = ? WHERE marcaId = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {

            psSelect.setInt(1, marcaId);
            ResultSet rs = psSelect.executeQuery();
            rs.next();
            boolean existe = rs.getInt(1) > 0;

            PreparedStatement ps = con.prepareStatement(existe ? sqlUpdate : sqlInsert);
            if (existe) {
                ps.setDouble(1, porcentaje);
                ps.setInt(2, marcaId);
            } else {
                ps.setInt(1, marcaId);
                ps.setDouble(2, porcentaje);
            }

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

