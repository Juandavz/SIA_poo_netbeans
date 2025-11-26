import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaPrincipal extends JFrame {

    // --- COLORES EXACTOS DEL SIA ---
    private final Color COLOR_HEADER_TOP = new Color(255, 204, 0);   // Amarillo UNAL
    private final Color COLOR_HEADER_BOT = new Color(102, 51, 0);    // Marrón Oscuro
    private final Color COLOR_MENU_BG    = new Color(255, 255, 224); // Beige claro
    private final Color COLOR_MENU_ITEM  = new Color(102, 51, 0);    // Marrón texto
    
    // Colores específicos para REPORTE DE NOTAS
    private final Color COLOR_REPORTE_BG = new Color(235, 235, 235); // Gris recuadros
    private final Color COLOR_TABLA_HEAD_HISTORIA = new Color(240, 240, 240); // Gris cabecera notas
    
    // Colores para TABLAS GENERALES (Ocre)
    private final Color COLOR_TABLA_HEAD_GEN = new Color(153, 51, 0); // Ocre/Rojo Ladrillo

    private JPanel panelContenido;
    private CardLayout cardLayout;
    private String usuarioActual;
    private JLabel lblRuta;

    public VentanaPrincipal(String usuario) {
        this.usuarioActual = usuario;

        setTitle("Sistema de Información Académica - Universidad Nacional de Colombia");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. ESTRUCTURA BASE
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearMenuLateral(), BorderLayout.WEST);
        add(crearPanelDerecho(), BorderLayout.EAST);

        // 2. CONTENIDO CENTRAL
        JPanel centroWrapper = new JPanel(new BorderLayout());
        centroWrapper.setBackground(Color.WHITE);
        
        // Barra de Ruta
        JPanel panelRuta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRuta.setBackground(Color.WHITE);
        panelRuta.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        lblRuta = new JLabel("Está en: Inicio");
        lblRuta.setFont(new Font("Verdana", Font.PLAIN, 10));
        lblRuta.setForeground(Color.GRAY);
        panelRuta.add(lblRuta);
        centroWrapper.add(panelRuta, BorderLayout.NORTH);

        // Paneles cambiantes
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(Color.WHITE);
        
        // --- AQUÍ ESTÁN TODOS LOS PANELES CARGADOS ---
        panelContenido.add(crearPanelBienvenida(), "INICIO");
        panelContenido.add(crearPanelHistoria(), "HISTORIA");
        panelContenido.add(crearPanelHorario(), "HORARIO");
        panelContenido.add(crearPanelDatos(), "DATOS");
        panelContenido.add(crearPanelBuscador(), "BUSCADOR");

        centroWrapper.add(panelContenido, BorderLayout.CENTER);
        add(centroWrapper, BorderLayout.CENTER);

        setVisible(true);
    }

    // =======================================================
    //                 ENCABEZADO Y MENÚ
    // =======================================================
    private JPanel crearEncabezado() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JPanel top = new JPanel(null);
        top.setBackground(COLOR_HEADER_TOP);
        top.setPreferredSize(new Dimension(1280, 75));
        
        JLabel lblUn = new JLabel("<html><b>UNIVERSIDAD NACIONAL</b><br>DE COLOMBIA</html>");
        lblUn.setFont(new Font("Arial", Font.BOLD, 16));
        lblUn.setBounds(30, 15, 300, 40);
        top.add(lblUn);
        
        try {
            ImageIcon icon = new ImageIcon("UNlogo.png");
            if (icon.getIconWidth() > 0) {
                Image img = icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
                JLabel lblImg = new JLabel(new ImageIcon(img));
                lblImg.setBounds(10, -5, 100, 90);
                lblUn.setBounds(120, 15, 300, 40);
                top.add(lblImg);
            }
        } catch (Exception e) {}

        JPanel bot = new JPanel(new BorderLayout());
        bot.setBackground(COLOR_HEADER_BOT);
        bot.setPreferredSize(new Dimension(1280, 25));
        
        JLabel lblSia = new JLabel("  SISTEMA DE INFORMACIÓN ACADÉMICA (SIA)");
        lblSia.setForeground(Color.WHITE);
        lblSia.setFont(new Font("Verdana", Font.BOLD, 10));
        JLabel lblUser = new JLabel("Usuario: " + usuarioActual + "  |  Perfil: Estudiante  ");
        lblUser.setForeground(new Color(255, 255, 200));
        lblUser.setFont(new Font("Verdana", Font.PLAIN, 10));

        bot.add(lblSia, BorderLayout.WEST);
        bot.add(lblUser, BorderLayout.EAST);
        header.add(top); header.add(bot);
        return header;
    }

    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(COLOR_MENU_BG);
        menu.setPreferredSize(new Dimension(240, 0));
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        JLabel titulo = new JLabel(" MENÚ PRINCIPAL");
        titulo.setOpaque(true);
        titulo.setBackground(new Color(220, 220, 200));
        titulo.setForeground(COLOR_MENU_ITEM);
        titulo.setFont(new Font("Verdana", Font.BOLD, 11));
        titulo.setMaximumSize(new Dimension(240, 25));
        titulo.setBorder(new EmptyBorder(5, 5, 5, 5));
        menu.add(titulo);

        menu.add(itemMenu("Inicio", false, e -> { cardLayout.show(panelContenido, "INICIO"); lblRuta.setText("Está en: Inicio"); }));
        menu.add(categoriaMenu("Apoyo Académico"));
        menu.add(subItemMenu("Mi Horario", e -> { cardLayout.show(panelContenido, "HORARIO"); lblRuta.setText("Está en: Apoyo Académico >> Mi Horario"); }));
        menu.add(categoriaMenu("Archivo"));
        menu.add(subItemMenu("Historia Académica", e -> { cardLayout.show(panelContenido, "HISTORIA"); lblRuta.setText("Está en: Archivo >> Historia Académica"); }));
        menu.add(subItemMenu("Datos Personales", e -> { cardLayout.show(panelContenido, "DATOS"); lblRuta.setText("Está en: Archivo >> Datos Personales"); }));
        menu.add(categoriaMenu("Buscador"));
        menu.add(subItemMenu("Catálogo de Cursos", e -> { cardLayout.show(panelContenido, "BUSCADOR"); lblRuta.setText("Está en: Buscador >> Cursos"); }));
        
        menu.add(Box.createVerticalStrut(20));
        JButton btnSalir = new JButton("Cerrar Sesión");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> { new VentanaLogin().setVisible(true); dispose(); });
        menu.add(btnSalir);
        return menu;
    }

    private JLabel categoriaMenu(String texto) {
        JLabel l = new JLabel("[+] " + texto);
        l.setFont(new Font("Verdana", Font.BOLD, 11)); l.setForeground(COLOR_MENU_ITEM);
        l.setBorder(new EmptyBorder(5, 10, 2, 10)); l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }
    private JLabel itemMenu(String texto, boolean esSub, ActionListener action) {
        JLabel l = new JLabel((esSub ? "   • " : "") + texto);
        l.setFont(new Font("Verdana", Font.PLAIN, 11)); l.setForeground(Color.BLACK);
        l.setBorder(new EmptyBorder(2, 15, 2, 10)); l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setCursor(new Cursor(Cursor.HAND_CURSOR));
        l.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { action.actionPerformed(null); } });
        return l;
    }
    private JLabel subItemMenu(String texto, ActionListener action) {
        JLabel l = new JLabel("      " + texto);
        l.setFont(new Font("Verdana", Font.PLAIN, 11)); l.setForeground(new Color(50, 50, 50));
        l.setBorder(new EmptyBorder(2, 10, 2, 10)); l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setCursor(new Cursor(Cursor.HAND_CURSOR));
        l.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { action.actionPerformed(null); } });
        return l;
    }
    
    private JPanel crearPanelDerecho() {
        JPanel p = new JPanel();
        p.setBackground(new Color(240, 240, 230));
        p.setPreferredSize(new Dimension(150, 0));
        p.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
        return p;
    }

    // =======================================================
    //                 PANTALLAS DE CONTENIDO (COMPLETAS)
    // =======================================================

    private JPanel crearPanelBienvenida() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.add(new JLabel("<html><center><h2>Bienvenido al SIA</h2></center></html>", SwingConstants.CENTER));
        return p;
    }

    // --- 1. HISTORIA ACADÉMICA (Estilo Reporte Gris) ---
    private JPanel crearPanelHistoria() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Cabecera Reporte
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        JPanel headerInfo = new JPanel(new BorderLayout());
        headerInfo.setBackground(Color.WHITE);
        JLabel title = new JLabel("REPORTE DE NOTAS");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        JLabel date = new JLabel("Generado: " + fecha);
        date.setFont(new Font("Arial", Font.PLAIN, 11)); date.setForeground(Color.GRAY);
        headerInfo.add(title, BorderLayout.WEST); headerInfo.add(date, BorderLayout.EAST);
        topPanel.add(headerInfo); topPanel.add(Box.createVerticalStrut(10));

        topPanel.add(crearCajaInfo("1023941 [ " + usuarioActual + " ]"));
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(crearCajaInfo("2541 INGENIERÍA DE SISTEMAS"));
        topPanel.add(Box.createVerticalStrut(15));
        p.add(topPanel, BorderLayout.NORTH);

        // Tabla
        String[] cols = {"Asignatura", "Créditos", "Nota", "Periodo", "Calificación"};
        Object[][] data = DataManager.cargarHistoria(usuarioActual);

        DefaultTableModel model = new DefaultTableModel(data, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable t = new JTable(model);
        t.setRowHeight(20); t.setFont(new Font("Arial", Font.PLAIN, 11));
        t.setShowVerticalLines(false); t.setGridColor(Color.LIGHT_GRAY);
        
        JTableHeader th = t.getTableHeader();
        th.setBackground(COLOR_TABLA_HEAD_HISTORIA);
        th.setForeground(Color.BLACK);
        th.setFont(new Font("Arial", Font.BOLD, 11));
        th.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        p.add(new JScrollPane(t), BorderLayout.CENTER);
        return p;
    }
    
    private JPanel crearCajaInfo(String texto) {
        JPanel box = new JPanel(new FlowLayout(FlowLayout.LEFT));
        box.setBackground(COLOR_REPORTE_BG);
        box.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        box.add(lbl);
        return box;
    }

    // --- 2. DATOS PERSONALES (ESTILO FICHA COMPLETA) ---
    private JPanel crearPanelDatos() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("MIS DATOS PERSONALES");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(153, 0, 0));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(titulo); p.add(Box.createVerticalStrut(20));

        JPanel ficha = new JPanel(new GridLayout(0, 2, 10, 10)); // Grid para formato ficha
        ficha.setBackground(Color.WHITE);
        ficha.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
                "Información Básica", 
                TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, 
                new Font("Arial", Font.BOLD, 12)
        ));

        // CAMPOS COMPLETOS
        agregarCampoFicha(ficha, "Nombre Completo:", usuarioActual.toUpperCase());
        agregarCampoFicha(ficha, "Documento de Identidad:", "1000" + (int)(Math.random()*9000));
        agregarCampoFicha(ficha, "Tipo de Sangre:", "O+");
        agregarCampoFicha(ficha, "Fecha de Nacimiento:", "01/01/2000");
        agregarCampoFicha(ficha, "Estado Civil:", "Soltero(a)");
        agregarCampoFicha(ficha, "Dirección:", "Carrera 30 # 45-03");
        agregarCampoFicha(ficha, "Teléfono:", "3165000");
        agregarCampoFicha(ficha, "Correo Institucional:", usuarioActual + "@unal.edu.co");

        ficha.setMaximumSize(new Dimension(800, 250)); // Tamaño suficiente para ver todo
        ficha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        p.add(ficha);
        p.add(Box.createVerticalGlue()); // Relleno hacia abajo
        return p;
    }
    
    private void agregarCampoFicha(JPanel panel, String label, String valor) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        JLabel l = new JLabel(label + "  ");
        l.setFont(new Font("Arial", Font.BOLD, 11));
        l.setForeground(new Color(100, 100, 100)); // Gris oscuro
        
        JLabel v = new JLabel(valor);
        v.setFont(new Font("Arial", Font.PLAIN, 11));
        v.setForeground(Color.BLACK);
        
        row.add(l, BorderLayout.WEST);
        row.add(v, BorderLayout.CENTER);
        panel.add(row);
    }

    // --- 3. HORARIO (CON BOTÓN AMARILLO Y FUNCIONALIDAD) ---
    private JPanel crearPanelHorario() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        
        JLabel titulo = new JLabel("Horario de Clases 2025-1");
        titulo.setFont(new Font("Verdana", Font.BOLD, 14));
        titulo.setForeground(new Color(153, 0, 0));
        
        JButton btnPrint = new JButton("Versión para impresión");
        btnPrint.setBackground(new Color(255, 204, 0));
        btnPrint.setFont(new Font("Verdana", Font.BOLD, 10));
        btnPrint.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnPrint.setPreferredSize(new Dimension(160, 25));
        btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnPrint.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Seleccione 'Microsoft Print to PDF' en la ventana de impresión.");
            try {
                JTable t = (JTable) ((JScrollPane) p.getComponent(1)).getViewport().getView();
                t.print(JTable.PrintMode.FIT_WIDTH, new java.text.MessageFormat("Horario UNAL"), new java.text.MessageFormat("Página {0}"));
            } catch(Exception ex){ JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        });

        top.add(titulo, BorderLayout.WEST);
        top.add(btnPrint, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);

        String[] cols = {"Cód", "Asignatura", "Grp", "Docente", "Día", "Hora", "Salón"};
        Object[][] data = DataManager.cargarHorario(usuarioActual);
        
        JTable t = diseñarTablaSIA(data, cols);
        p.add(new JScrollPane(t), BorderLayout.CENTER);
        return p;
    }

    // --- 4. BUSCADOR (COMPLETO CON FORMULARIO Y TABLA) ---
    private JPanel crearPanelBuscador() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Formulario Gris
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        form.setBackground(new Color(240, 240, 240));
        form.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Criterios", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Verdana", Font.BOLD, 11)));
        
        JTextField txt = new JTextField(20);
        JButton btn = new JButton("Buscar");
        btn.setBackground(new Color(220, 220, 220));
        form.add(new JLabel("Palabra Clave: ")); form.add(txt); form.add(btn);
        p.add(form, BorderLayout.NORTH);
        
        // Tabla Resultados
        String[] cols = {"Código", "Asignatura", "Facultad", "Créditos", "Cupos"};
        Object[][] data = DataManager.cargarCatalogo(); // Carga desde TXT
        
        DefaultTableModel model = new DefaultTableModel(data, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable t = new JTable(model);
        diseñarEstiloTabla(t); // Aplica estilo Ocre
        
        t.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic
                    int filaSeleccionada = t.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        // Convertimos el índice de la vista al del modelo (por si filtraste)
                        int filaModelo = t.convertRowIndexToModel(filaSeleccionada);
                        
                        // Obtenemos el objeto desde nuestra lista en DataManager usando el índice
                        Asignatura asignaturaSeleccionada = DataManager.listaAsignaturas.get(filaModelo);
                        
                        // Simulamos la ventana emergente del video
                        JOptionPane.showMessageDialog(null, 
                            "DETALLE DE ASIGNATURA\n\n" +
                            "Nombre: " + asignaturaSeleccionada.getNombre() + "\n" +
                            "Código: " + asignaturaSeleccionada.getCodigo() + "\n" +
                            "Facultad: " + asignaturaSeleccionada.getFacultad() + "\n" +
                            "Créditos: " + asignaturaSeleccionada.getCreditos() + "\n" +
                            "Cupos Disponibles: " + asignaturaSeleccionada.getCuposDisponibles() + "\n\n" +
                            "Estado: ABIERTA PARA INSCRIPCIÓN",
                            "Información del Grupo",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        t.setRowSorter(sorter);
        
        ActionListener buscarAction = e -> {
            String text = txt.getText();
            if (text.trim().length() == 0) sorter.setRowFilter(null);
            else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        };
        btn.addActionListener(buscarAction);
        txt.addActionListener(buscarAction);
        
        p.add(new JScrollPane(t), BorderLayout.CENTER);
        return p;
    }

    // =======================================================
    //             MÉTODOS DE ESTILO (NO BORRAR)
    // =======================================================
    private JTable diseñarTablaSIA(Object[][] data, String[] cols) {
        DefaultTableModel model = new DefaultTableModel(data, cols) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable t = new JTable(model);
        diseñarEstiloTabla(t);
        return t;
    }

    private void diseñarEstiloTabla(JTable t) {
        t.setRowHeight(22);
        t.setFont(new Font("Verdana", Font.PLAIN, 11));
        t.setShowVerticalLines(true);
        t.setGridColor(Color.LIGHT_GRAY);
        
        // CABECERA COLOR OCRE/ROJO LADRILLO
        JTableHeader header = t.getTableHeader();
        header.setBackground(COLOR_TABLA_HEAD_GEN);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Verdana", Font.BOLD, 11));
        
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 245));
                }
                return c;
            }
        });
    }
}