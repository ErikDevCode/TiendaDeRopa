package gui;

import datos.Marca;
import datos.Orden;
import datos.OrdenItem;
import datos.Producto;
import datos.Usuario;
import logica.OrdenService;
import logica.ProductoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class DialogoVenderCarrito extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Marca> comboMarca;
    private JComboBox<Producto> comboProducto;
    private JTextField txtCantidad;
    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;
    private JLabel lblDescuento;
    private final ProductoService service = new ProductoService();
    private List<Producto> productosSeleccionados = new ArrayList<>();
    private Usuario usuario;

    public DialogoVenderCarrito(Usuario usuario) {
    	this.usuario = usuario;
        setTitle("Carrito de Compras");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        // Panel de selección
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboMarca = new JComboBox<>();
        comboProducto = new JComboBox<>();
        txtCantidad = new JTextField(5);
        JButton btnAgregar = new JButton("Agregar al carrito");

        panelSeleccion.add(new JLabel("Marca:"));
        panelSeleccion.add(comboMarca);
        panelSeleccion.add(new JLabel("Producto:"));
        panelSeleccion.add(comboProducto);
        panelSeleccion.add(new JLabel("Cantidad:"));
        panelSeleccion.add(txtCantidad);
        panelSeleccion.add(btnAgregar);

        add(panelSeleccion, BorderLayout.NORTH);

        // Tabla carrito
        String[] columnas = {"Producto", "Precio", "Cantidad", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaCarrito = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaCarrito);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior con total y confirmación
        JPanel panelInferior = new JPanel(new BorderLayout());
        JPanel panelInfoTotal = new JPanel(new GridLayout(2, 1));
        panelInfoTotal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        
        lblTotal = new JLabel("Sub Total: S/ 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        panelInferior.add(lblTotal);

        lblDescuento = new JLabel(); 
        lblDescuento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDescuento.setForeground(Color.BLUE);
        panelInfoTotal.add(lblDescuento);

        panelInferior.add(panelInfoTotal, BorderLayout.WEST);
        
        JPanel botones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar seleccionado");
        JButton btnConfirmar = new JButton("Confirmar Compra");
        btnConfirmar.setBackground(new Color(52, 168, 83));
        btnConfirmar.setForeground(Color.WHITE);

        botones.add(btnEliminar);
        botones.add(btnConfirmar);
        panelInferior.add(botones, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        // Eventos
        comboMarca.addActionListener(e -> cargarProductos());
        btnAgregar.addActionListener(this::agregarProducto);
        btnEliminar.addActionListener(this::eliminarSeleccionado);
        btnConfirmar.addActionListener(e -> confirmarCompra());

        // Inicialización
        cargarMarcas();
        cargarProductos();
    }

    private void cargarMarcas() {
        comboMarca.removeAllItems();
        for (Marca m : service.obtenerMarcas()) {
            comboMarca.addItem(m);
        }
    }

    private void cargarProductos() {
        comboProducto.removeAllItems();
        Marca marca = (Marca) comboMarca.getSelectedItem();
        if (marca != null) {
            for (Producto p : service.obtenerProductosPorMarca(marca.getId())) {
                comboProducto.addItem(p);
            }
        }
    }

    private void agregarProducto(ActionEvent e) {
        Producto producto = (Producto) comboProducto.getSelectedItem();
        if (producto == null) return;

        try {
            int cantidadNueva = Integer.parseInt(txtCantidad.getText());
            if (cantidadNueva <= 0 || cantidadNueva > producto.getStock()) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida o excede el stock disponible.");
                return;
            }

            // Buscar si el producto ya está en la tabla
            boolean encontrado = false;
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String nombreExistente = modeloTabla.getValueAt(i, 0).toString();
                if (nombreExistente.equals(producto.getNombreProducto())) {
                    // Producto ya existe, actualiza cantidad y subtotal
                    int cantidadActual = Integer.parseInt(modeloTabla.getValueAt(i, 2).toString());
                    int nuevaCantidad = cantidadActual + cantidadNueva;

                    if (nuevaCantidad > producto.getStock()) {
                        JOptionPane.showMessageDialog(this, "La cantidad total supera el stock disponible.");
                        return;
                    }

                    double nuevoSubtotal = nuevaCantidad * producto.getPrecio();
                    modeloTabla.setValueAt(nuevaCantidad, i, 2);
                    modeloTabla.setValueAt("S/ " + String.format("%.2f", nuevoSubtotal), i, 3);
                    encontrado = true;
                    break;
                }
            }

            // Si no está en la tabla, se agrega como nuevo
            if (!encontrado) {
                double subtotal = cantidadNueva * producto.getPrecio();
                modeloTabla.addRow(new Object[]{
                        producto.getNombreProducto(),
                        "S/ " + producto.getPrecio(),
                        cantidadNueva,
                        "S/ " + String.format("%.2f", subtotal)
                });
                productosSeleccionados.add(producto);
            }

            actualizarTotal();
            txtCantidad.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.");
        }
    }

    private void actualizarTotal() {
    	double total = 0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String subStr = modeloTabla.getValueAt(i, 3).toString().replace("S/ ", "");
            total += Double.parseDouble(subStr);
        }
        lblTotal.setText("Sub Total: S/ " + String.format("%.2f", total));

        // Mostrar descuento si existe
        Marca marcaSeleccionada = (Marca) comboMarca.getSelectedItem();
        double porcentajeDescuento = service.obtenerDescuentoPorMarca(marcaSeleccionada != null ? marcaSeleccionada.getId() : -1);
        
        if (porcentajeDescuento > 0) {
            lblDescuento.setText(String.format("Descuento aplicado: %.0f%%", porcentajeDescuento * 100));
        } else {
            lblDescuento.setText("");
        }
    }

    private void eliminarSeleccionado(ActionEvent e) {
        int row = tablaCarrito.getSelectedRow();
        if (row >= 0) {
            modeloTabla.removeRow(row);
            productosSeleccionados.remove(row);
            actualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto a eliminar del carrito.");
        }
    }


    private void confirmarCompra() {
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        double total = obtenerTotal();

        Marca marcaSeleccionada = (Marca) comboMarca.getSelectedItem();
        double porcentajeDescuento = service.obtenerDescuentoPorMarca(marcaSeleccionada != null ? marcaSeleccionada.getId() : -1);

        DialogoResumenCompra resumen = new DialogoResumenCompra(total, porcentajeDescuento);

        resumen.setVisible(true);
        if (!resumen.isConfirmado()) {
            return;
        }
        
        List<Producto> productos = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String nombreProducto = modeloTabla.getValueAt(i, 0).toString();
            Producto producto = service.obtenerTodosLosProductos().stream()
                    .filter(p -> p.getNombreProducto().equals(nombreProducto))
                    .findFirst().orElse(null);
            if (producto != null) {
                productos.add(producto);
                int cantidad = Integer.parseInt(modeloTabla.getValueAt(i, 2).toString());
                cantidades.add(cantidad);
            }
        }

        Orden orden = new Orden();
        OrdenService ordenService = new OrdenService();
        orden.setCodigo(ordenService.generarCodigoOrden());
        orden.setTotal(total);
        orden.setUsuarioId(usuario.getId());

        List<OrdenItem> items = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cantidad = cantidades.get(i); 

            OrdenItem item = new OrdenItem();
            item.setProductoId(p.getId());
            item.setCantidad(cantidad);
            item.setPrecio(p.getPrecio());
            items.add(item);
        }


        orden.setItems(items);
        boolean ok = ordenService.registrarOrden(orden);

        if (ok) {
        	for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                int cantidad = cantidades.get(i);
                int nuevoStock = p.getStock() - cantidad;
                service.actualizarStock(p.getId(), nuevoStock);
            }
            JOptionPane.showMessageDialog(this, "Orden registrada exitosamente. Código: " + orden.getCodigo());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Hubo un error al registrar la orden.");
        }
    }


    private double obtenerTotal() {
        double total = 0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String subStr = modeloTabla.getValueAt(i, 3).toString().replace("S/ ", "");
            total += Double.parseDouble(subStr);
        }
        return total;
    }


	public List<Producto> getProductosSeleccionados() {
		return productosSeleccionados;
	}

	public void setProductosSeleccionados(List<Producto> productosSeleccionados) {
		this.productosSeleccionados = productosSeleccionados;
	}
}
