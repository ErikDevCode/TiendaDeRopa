package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public Usuario validarLogin(String correo, String contrasena) {
    	String sql = "SELECT u.*, r.rolNombre " +
                "FROM usuarios u " +
                "JOIN roles r ON u.rolId = r.id " +
                "WHERE u.correo = ? AND u.contrasena = ?";


        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setNombreCompleto(rs.getString("nombreCompleto"));
                u.setRolId(rs.getInt("rolId"));
                u.setRolNombre(rs.getString("rolNombre"));
                return u;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean registrar(Usuario usuario) {
        try (Connection con = ConexionBD.conectar()) {
            String sql = "INSERT INTO usuarios (correo, contrasena, nombreCompleto, rolId) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setInt(4, usuario.getRolId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
