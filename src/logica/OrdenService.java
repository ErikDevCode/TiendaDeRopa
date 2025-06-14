package logica;

import datos.ConexionBD;
import datos.Orden;
import datos.OrdenDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class OrdenService {
    private final OrdenDAO ordenDAO = new OrdenDAO();

    public boolean registrarOrden(Orden orden) {
        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            int ordenId = ordenDAO.insertarOrden(conn, orden);
            if (ordenId == -1) throw new SQLException("No se pudo registrar la orden.");

            ordenDAO.insertarItemsOrden(conn, ordenId, orden.getItems());

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Orden> obtenerTodasLasOrdenes() {
        return ordenDAO.obtenerTodasLasOrdenes();
    }

    public String generarCodigoOrden() {
        Random r = new Random();
        int numero = 10000 + r.nextInt(90000);
        return "ORD" + numero;
    }
}