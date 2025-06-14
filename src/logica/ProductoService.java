package logica;

import datos.DescuentoDAO;
import datos.Marca;
import datos.Producto;
import datos.ProductoDAO;

import java.util.List;

public class ProductoService {
    private final ProductoDAO dao = new ProductoDAO();
    private final DescuentoDAO descuentoDAO = new DescuentoDAO();

    public List<Marca> obtenerMarcas() {
        return dao.listarMarcas();
    }

    public List<Producto> obtenerProductosPorMarca(int marcaId) {
        return dao.listarProductosPorMarca(marcaId);
    }
    
    public boolean crearProducto(Producto producto) {
        return dao.crearProducto(producto);
    }
    
    public boolean actualizarProducto(Producto producto) {
        return dao.actualizarProducto(producto);
    }
    
    public List<Producto> obtenerTodosLosProductos() {
        return dao.listarTodosLosProductos();
    }
    
    public void actualizarStock(int productoId, int nuevoStock) {
        dao.actualizarStock(productoId, nuevoStock);
    }
    
    public Producto obtenerProductoPorId(int id) {
        return dao.obtenerPorId(id);
    }
    
    public double obtenerDescuentoPorMarca(int marcaId) {
        if (marcaId <= 0) return 0;
        return descuentoDAO.obtenerDescuentoPorMarca(marcaId);
    }
    
    public boolean guardarDescuentoMarca(int marcaId, double porcentaje) {
        return descuentoDAO.guardarOActualizarDescuento(marcaId, porcentaje);
    }

}
