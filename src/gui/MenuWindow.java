package gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

import datos.Usuario;

public class MenuWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public MenuWindow(Usuario usuario) {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(100, 100, 746, 823);
    	setTitle("Aura Moda - Sistema de Tienda de Ropa");

    	// Inicializa contentPane
    	contentPane = new JPanel();
    	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    	contentPane.setLayout(null);
    	setContentPane(contentPane);

    	// Escalar imagen al tamaño del JFrame
    	ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/aura-moda.png"));
    	Image scaledImage = originalIcon.getImage().getScaledInstance(765, 830, Image.SCALE_SMOOTH);
    	ImageIcon scaledIcon = new ImageIcon(scaledImage);
    	JLabel backgroundLabel = new JLabel(scaledIcon);
    	backgroundLabel.setBounds(0, 0, 739, 769);

    	// Agregar al fondo
    	contentPane.add(backgroundLabel);
    	contentPane.setComponentZOrder(backgroundLabel, contentPane.getComponentCount() - 1);

        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        // Menú Archivo (para todos)
        JMenu mnArchivo = new JMenu("Archivo");
        menuBar.add(mnArchivo);
        JMenuItem mntmSalir = new JMenuItem("Salir");
        mntmSalir.addActionListener(e -> System.exit(0));
        mnArchivo.add(mntmSalir);

        
        // Mostrar menú según el rol
        int rol = usuario.getRolId();

        if (rol != 2) {
            // Menú Mantenimiento
            JMenu mnMantenimiento = new JMenu("Mantenimiento");
            menuBar.add(mnMantenimiento);

            JMenuItem mntmConsultar = new JMenuItem("Consultar prenda");
            mntmConsultar.addActionListener(e -> new DialogoConsultar().setVisible(true));
            mnMantenimiento.add(mntmConsultar);

            JMenuItem mntmCrear = new JMenuItem("Crear prenda");
            mntmCrear.addActionListener(e -> new DialogoCrearProducto().setVisible(true));
            mnMantenimiento.add(mntmCrear);

            JMenuItem mntmModificar = new JMenuItem("Modificar prenda");
            mntmModificar.addActionListener(e -> new DialogoModificarPrenda().setVisible(true));
            mnMantenimiento.add(mntmModificar);

            JMenuItem mntmListar = new JMenuItem("Listar prendas");
            mntmListar.addActionListener(e -> new DialogoListarPrendas().setVisible(true));
            mnMantenimiento.add(mntmListar);

            // Menú Configuración
            JMenu mnConfiguracion = new JMenu("Configuración");
            menuBar.add(mnConfiguracion);

            JMenuItem mntmDescuentos = new JMenuItem("Configurar descuentos");
            mntmDescuentos.addActionListener(e -> new DialogoDescuentos().setVisible(true));
            mnConfiguracion.add(mntmDescuentos);
            
            // Menú Ver Ventas
            JMenuItem mnVerVentas = new JMenu("Ver Ventas");
            menuBar.add(mnVerVentas);
            JMenuItem mntmVerVentas = new JMenuItem("Ver");
            mntmVerVentas.addActionListener(e -> new DialogoVerVentas().setVisible(true));
            mnVerVentas.add(mntmVerVentas);
        }
        
        // Menú Ventas
        JMenu mnCompras = new JMenu("Compras");
        menuBar.add(mnCompras);
        
        JMenuItem mntmCompras = new JMenuItem("Comprar");
        mntmCompras.addActionListener(e -> new DialogoVenderCarrito(usuario).setVisible(true));
        mnCompras.add(mntmCompras);
        
        // Menú Ayuda
        JMenu mnAyuda = new JMenu("Ayuda");
        menuBar.add(mnAyuda);
        
        JMenuItem mntmAcercaDe = new JMenuItem("Acerca de Tienda");
        mntmAcercaDe.addActionListener(e -> new DialogoAcercaDe().setVisible(true));
        mnAyuda.add(mntmAcercaDe);
        
        // Panel de bienvenida
        JLabel lblBienvenido = new JLabel("Bienvenido a Aura Moda - Sistema de Tienda de Ropa");
        lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenido.setBounds(10, 11, 614, 36);
        contentPane.add(lblBienvenido);
        
        JLabel lblInstrucciones = new JLabel("Utilice los menús superiores para acceder a las funciones del sistema");
        lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstrucciones.setBounds(10, 58, 614, 14);
        contentPane.add(lblInstrucciones);
        
        this.setVisible(true);
    }
}




