package gui;

import datos.Orden;
import logica.OrdenService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DialogoVerVentas extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DialogoVerVentas() {
        setTitle("Ventas Registradas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Código", "Total", "Usuario", "Fecha"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);

        OrdenService ordenService = new OrdenService();
        List<Orden> ordenes = ordenService.obtenerTodasLasOrdenes();

        for (Orden o : ordenes) {
            modelo.addRow(new Object[]{
                    o.getId(),
                    o.getCodigo(),
                    "S/ " + o.getTotal(),
                    o.getNombreUsuario(),
                    o.getFecha()
            });
        }

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
