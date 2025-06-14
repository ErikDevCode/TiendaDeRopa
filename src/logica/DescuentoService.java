package logica;

import java.util.List;

import datos.Descuento;
import datos.DescuentoDAO;

public class DescuentoService {
    private final DescuentoDAO dao = new DescuentoDAO();

    public void guardarDescuento(int marcaId, double porcentaje) {
        dao.guardarDescuento(marcaId, porcentaje);
    }

    public List<Descuento> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public double obtenerDescuentoPorMarca(int marcaId) {
        return dao.obtenerDescuentoPorMarca(marcaId);
    }
}
