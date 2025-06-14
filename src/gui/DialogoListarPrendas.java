package gui;

import datos.Marca;
import datos.Producto;
import logica.ProductoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DialogoListarPrendas extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Marca> comboMarca;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final ProductoService service = new ProductoService();

    public DialogoListarPrendas() {
        setTitle("Listado de Prendas");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        // Panel superior con filtro
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        comboMarca = new JComboBox<>();
        panelSuperior.add(new JLabel("Filtrar por Marca:"));
        panelSuperior.add(comboMarca);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Marca", "Nombre", "Precio", "Talla", "Color", "Material", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarMarcas();
        listarProductos(null);

        comboMarca.addActionListener(e -> {
            Marca m = (Marca) comboMarca.getSelectedItem();
            listarProductos(m != null ? m.getId() : null);
        });
    }

    private void cargarMarcas() {
        comboMarca.addItem(new Marca(0, "Todas"));
        for (Marca m : service.obtenerMarcas()) {
            comboMarca.addItem(m);
        }
    }

    private void listarProductos(Integer marcaId) {
        modeloTabla.setRowCount(0);
        List<Producto> productos = service.obtenerTodosLosProductos();

        if (marcaId != null && marcaId != 0) {
            productos = productos.stream()
                    .filter(p -> p.getMarcaId() == marcaId)
                    .collect(Collectors.toList());
        }

        for (Producto p : productos) {
            String nombreMarca = service.obtenerMarcas().stream()
                    .filter(m -> m.getId() == p.getMarcaId())
                    .findFirst()
                    .map(Marca::getMarcaNombre)
                    .orElse("Sin marca");

            modeloTabla.addRow(new Object[]{
                p.getId(),
                nombreMarca,
                p.getNombreProducto(),
                "S/ " + p.getPrecio(),
                p.getTalla(),
                p.getColor(),
                p.getMaterial(),
                p.getStock()
            });
        }
    }
}
