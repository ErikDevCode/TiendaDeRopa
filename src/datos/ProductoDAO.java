package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Marca> listarMarcas() {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT id, marcaNombre FROM marca";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                marcas.add(new Marca(rs.getInt("id"), rs.getString("marcaNombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marcas;
    }

    public List<Producto> listarProductosPorMarca(int marcaId) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE marcaId = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, marcaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id"),
                    rs.getInt(marcaId),
                    rs.getString("nombreProducto"),
                    rs.getDouble("precio"),
                    rs.getString("talla"),
                    rs.getString("color"),
                    rs.getString("material"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
    
    public boolean crearProducto(Producto producto) {
        String sql = "INSERT INTO productos (marcaId, nombreProducto, precio, talla, color, material, stock) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, producto.getMarcaId());
            ps.setString(2, producto.getNombreProducto());
            ps.setDouble(3, producto.getPrecio());
            ps.setString(4, producto.getTalla());
            ps.setString(5, producto.getColor());
            ps.setString(6, producto.getMaterial());
            ps.setInt(7, producto.getStock());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombreProducto=?, precio=?, talla=?, color=?, material=?, stock=? WHERE id=?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombreProducto());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getTalla());
            ps.setString(4, producto.getColor());
            ps.setString(5, producto.getMaterial());
            ps.setInt(6, producto.getStock());
            ps.setInt(7, producto.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Producto> listarTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id"),
                    rs.getInt("marcaId"),
                    rs.getString("nombreProducto"),
                    rs.getDouble("precio"),
                    rs.getString("talla"),
                    rs.getString("color"),
                    rs.getString("material"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
    
    public void actualizarStock(int productoId, int nuevoStock) {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setInt(2, productoId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Producto p = new Producto(id, id, sql, id, sql, sql, sql, id);
                p.setId(rs.getInt("id"));
                p.setNombreProducto(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setMarcaId(rs.getInt("marcaId"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
