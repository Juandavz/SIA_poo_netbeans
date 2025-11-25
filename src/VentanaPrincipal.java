import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter; // <--- AGREGAR ESTA
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;         // <--- AGREGAR ESTA
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VentanaPrincipal extends JFrame {

    private JPanel panelContenido;
    private CardLayout cardLayout;

    public VentanaPrincipal() {
        setTitle("SIA - Sistema de Información Académica");
        setSize(1400, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. HEADER
        add(crearEncabezado(), BorderLayout.NORTH);

        // 2. MENÚ LATERAL (IZQUIERDA)
        add(crearMenuLateral(), BorderLayout.WEST);

        // 3. NOTICIAS (DERECHA) - ¡NUEVO!
        add(crearPanelNoticias(), BorderLayout.EAST);

        // 4. CONTENIDO (CENTRO)
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(Color.WHITE);
        
        // Agregar vistas
        panelContenido.add(crearPanelBienvenida(), "INICIO");
        panelContenido.add(crearPanelHistoriaAcademica(), "HISTORIA");
        panelContenido.add(crearPanelHorario(), "HORARIO");
        panelContenido.add(crearPanelDatosPersonales(), "DATOS");
        panelContenido.add(crearPanelBuscador(), "BUSCADOR");
        
        add(panelContenido, BorderLayout.CENTER);

        setVisible(true);
    }

    // --- DISEÑO DEL ENCABEZADO ---
    private JPanel crearEncabezado() {
        JPanel headerContainer = new JPanel();
        headerContainer.setLayout(new BoxLayout(headerContainer, BoxLayout.Y_AXIS));
        headerContainer.setPreferredSize(new Dimension(1400, 110));

        JPanel recuadro4 = new JPanel(null);
        recuadro4.setBackground(new Color(255, 203, 0)); // Amarillo UNAL
        recuadro4.setPreferredSize(new Dimension(1400, 80));
        
        ImageIcon iconoOriginal = new ImageIcon("LogoUN.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
        JLabel lblImagen = new JLabel(new ImageIcon(imagenEscalada));
        lblImagen.setBounds(40, -10, 120, 100);
        recuadro4.add(lblImagen);

        JPanel recuadro3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 80, 5));
        recuadro3.setBackground(new Color(158, 124, 0)); // Ocre Oscuro
        recuadro3.setPreferredSize(new Dimension(1400, 30));

        JLabel lblSIA = new JLabel("Sistema de Información Académica  |  Rol: Estudiante");
        lblSIA.setForeground(new Color(253, 250, 234));
        lblSIA.setFont(new Font("Arial", Font.BOLD, 14));
        recuadro3.add(lblSIA);

        headerContainer.add(recuadro4);
        headerContainer.add(recuadro3);
        return headerContainer;
    }

    // --- DISEÑO DEL MENÚ LATERAL (TIPO ACORDEÓN) ---
    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel();
        menu.setBackground(new Color(245, 245, 220)); 
        menu.setPreferredSize(new Dimension(260, 0));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(200, 200, 150)));

        menu.add(Box.createVerticalStrut(20));
        
        // Botón Inicio
        agregarBotonMenu(menu, "Inicio", e -> cardLayout.show(panelContenido, "INICIO"));

        // SECCIÓN: APOYO ACADÉMICO (Ver PDF pág 2)
        agregarTituloSeccion(menu, "APOYO ACADÉMICO");
        agregarSubMenu(menu, "Mi Horario", e -> cardLayout.show(panelContenido, "HORARIO"));
        agregarSubMenu(menu, "Mis Calificaciones", e -> cardLayout.show(panelContenido, "HISTORIA"));
        agregarSubMenu(menu, "Inscripción Materias", e -> JOptionPane.showMessageDialog(null, "Fuera de fechas"));

        // SECCIÓN: ARCHIVO (Ver PDF pág 2)
        agregarTituloSeccion(menu, "ARCHIVO");
        agregarSubMenu(menu, "Mis Datos Personales", e -> cardLayout.show(panelContenido, "DATOS"));
        agregarSubMenu(menu, "Historia Académica", e -> cardLayout.show(panelContenido, "HISTORIA"));

        // SECCIÓN: LIBRE ACCESO
        agregarTituloSeccion(menu, "LIBRE ACCESO");
        agregarSubMenu(menu, "Buscador de Cursos", e -> cardLayout.show(panelContenido, "BUSCADOR"));

        menu.add(Box.createVerticalGlue());
        
        JButton btnSalir = new JButton("Cerrar Sesión");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setBackground(new Color(173, 75, 52));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.addActionListener(e -> {
            new VentanaLogin().setVisible(true);
            dispose();
        });
        menu.add(btnSalir);
        menu.add(Box.createVerticalStrut(20));

        return menu;
    }

    // Helpers para el menú
    private void agregarTituloSeccion(JPanel panel, String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(100, 100, 100));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(15));
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
    }

    private void agregarBotonMenu(JPanel panel, String texto, ActionListener accion) {
        JButton btn = new JButton(texto);
        estilizarBotonMenu(btn);
        btn.setBackground(new Color(255, 240, 0));
        btn.addActionListener(accion);
        panel.add(btn);
    }

    private void agregarSubMenu(JPanel panel, String texto, ActionListener accion) {
        JButton btn = new JButton("  ↳ " + texto); // Indentación visual
        estilizarBotonMenu(btn);
        btn.setBackground(new Color(250, 250, 240)); // Más claro
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.addActionListener(accion);
        panel.add(btn);
        panel.add(Box.createVerticalStrut(3));
    }

    private void estilizarBotonMenu(JButton btn) {
        btn.setMaximumSize(new Dimension(220, 30));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // --- PANTALLAS (PANELES) ---

    private JPanel crearPanelBienvenida() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        JLabel l = new JLabel("<html><center><h1>Bienvenido al SIA</h1><p>Seleccione un servicio del menú lateral.</p></center></html>", SwingConstants.CENTER);
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    // REQUERIMIENTO: HORARIO (Descargar como PDF vía Impresora Virtual)
    private JPanel crearPanelHorario() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        // --- Panel Superior ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel(" Mi Horario (Periodo 2025-1)", SwingConstants.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Botón corregido
        JButton btnPDF = new JButton("Guardar como PDF / Imprimir");
        btnPDF.setBackground(new Color(255, 203, 0));
        btnPDF.setFocusPainted(false);
        btnPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));

        topPanel.add(titulo, BorderLayout.WEST);
        topPanel.add(btnPDF, BorderLayout.EAST);
        p.add(topPanel, BorderLayout.NORTH);

        // --- Tabla de Datos ---
        String[] col = {"Cód", "Asignatura", "Grp", "Docente", "Día", "Hora", "Salón"};
        Object[][] data = {
            {"2016", "Cálculo Integral", "1", "Pedro Pérez", "LUN/MIE", "07:00 - 09:00", "404-201"},
            {"2025", "Física II", "2", "Maria Lopez", "MAR/JUE", "09:00 - 11:00", "453-102"},
            {"1001", "Programación OOP", "1", "Juan Dev", "VIE", "14:00 - 17:00", "Sala 3"},
            {"2019", "Ecuaciones Diferenciales", "3", "Carlos Mateus", "LUN/VIE", "11:00 - 13:00", "405-202"}
        };

        // Modelo NO editable
        DefaultTableModel modelo = new DefaultTableModel(data, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable t = new JTable(modelo);
        t.setRowHeight(25);
        t.getTableHeader().setBackground(new Color(230, 180, 50));
        t.getTableHeader().setForeground(Color.WHITE);
        t.getTableHeader().setReorderingAllowed(false);
        
        // Ajustamos el ancho de las columnas para que se vea bien en PDF
        t.getColumnModel().getColumn(1).setPreferredWidth(200); // Asignatura más ancha
        
        p.add(new JScrollPane(t), BorderLayout.CENTER);

        // --- LÓGICA DE PDF (Sin errores de imagen) ---
        btnPDF.addActionListener(e -> {
            // Mensaje previo para guiar al usuario
            JOptionPane.showMessageDialog(this, 
                "Para guardar como PDF, seleccione 'Microsoft Print to PDF' \no 'Guardar como PDF' en la siguiente ventana.",
                "Instrucciones", JOptionPane.INFORMATION_MESSAGE);

            try {
                // Encabezados SIMPLES (Texto puro para evitar error "archivo no encontrado")
                java.text.MessageFormat header = new java.text.MessageFormat("Universidad Nacional de Colombia - Horario 2025-1");
                java.text.MessageFormat footer = new java.text.MessageFormat("Página {0,number,integer}");
                
                // Abre el diálogo de impresión del sistema
                boolean complete = t.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                
                if (complete) {
                    JOptionPane.showMessageDialog(null, "Proceso finalizado.", "Estado", JOptionPane.INFORMATION_MESSAGE);
                } 
            } catch (java.awt.print.PrinterException pe) {
                JOptionPane.showMessageDialog(null, "Error: " + pe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return p;
    }

    // REQUERIMIENTO: HISTORIA ACADÉMICA (SOLO LECTURA)
    private JPanel crearPanelHistoriaAcademica() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        
        JLabel titulo = new JLabel(" Historia Académica", SwingConstants.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        p.add(titulo, BorderLayout.NORTH);

        String[] columnas = {"Código", "Asignatura", "Créditos", "Calificación", "Estado"};
        Object[][] datos = {
            {"2015698", "Cálculo Diferencial", "4", "3.8", "APROBADA"},
            {"2015702", "Programación Orientada a Objetos", "3", "4.5", "APROBADA"},
            {"2015123", "Física Mecánica", "4", "3.0", "APROBADA"},
            {"2025001", "Lecto-Escritura", "2", "4.2", "APROBADA"},
        };

        // BLOQUEO DE EDICIÓN
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(230, 180, 50));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);
        
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        JPanel panelPromedio = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelPromedio.add(new JLabel("Promedio Aritmético Ponderado (P.A.P.A): 3.9"));
        panelPromedio.setBackground(new Color(245, 245, 220));
        p.add(panelPromedio, BorderLayout.SOUTH);

        return p;
    }

    // REQUERIMIENTO: DATOS PERSONALES
    private JPanel crearPanelDatosPersonales() {
        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Mis Datos Personales");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(20, 20, 300, 30);
        p.add(titulo);

        // Simulamos campos de solo lectura
        agregarCampoDato(p, "Nombre Completo:", "Pepito Pérez", 60);
        agregarCampoDato(p, "Documento:", "1.000.123.456", 100);
        agregarCampoDato(p, "Correo Institucional:", "pperez@unal.edu.co", 140);
        agregarCampoDato(p, "Programa Curricular:", "Ingeniería de Sistemas", 180);
        agregarCampoDato(p, "Sede:", "Bogotá", 220);

        return p;
    }

    private void agregarCampoDato(JPanel p, String label, String valor, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setBounds(50, y, 150, 25);
        p.add(lbl);

        JTextField txt = new JTextField(valor);
        txt.setEditable(false);
        txt.setBackground(new Color(250, 250, 250));
        txt.setBounds(200, y, 250, 25);
        p.add(txt);
    }
    
    // REQUERIMIENTO: BUSCADOR DE CURSOS (SOLO LECTURA)
    private JPanel crearPanelBuscador() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        // --- 1. Panel Superior (Barra de Búsqueda) ---
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(new Color(245, 245, 220));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblBuscar = new JLabel("Buscar Asignatura:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        
        JTextField txtBuscar = new JTextField(20);
        
        JButton btnBuscar = new JButton("Filtrar");
        btnBuscar.setBackground(new Color(255, 203, 0));

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        p.add(panelBusqueda, BorderLayout.NORTH);

        // --- 2. Tabla de Cursos Ofertados ---
        String[] columnas = {"Código", "Asignatura", "Facultad", "Créditos", "Cupos"};
        Object[][] datos = {
            {"1000001", "Cálculo Diferencial", "Ciencias", "4", "45"},
            {"1000002", "Cálculo Integral", "Ciencias", "4", "40"},
            {"1000003", "Álgebra Lineal", "Ciencias", "4", "50"},
            {"2001562", "Programación Orientada a Objetos", "Ingeniería", "3", "30"},
            {"2001563", "Estructuras de Datos", "Ingeniería", "3", "25"},
            {"2001564", "Ingeniería de Software", "Ingeniería", "3", "25"},
            {"3005210", "Historia de Colombia", "Ciencias Humanas", "3", "100"},
            {"3005211", "Antropología Social", "Ciencias Humanas", "3", "80"},
            {"1000045", "Física Mecánica", "Ciencias", "4", "60"},
            {"1000046", "Física de Electromagnetismo", "Ciencias", "4", "55"}
        };

        // --- AQUÍ ESTÁ EL CAMBIO IMPORTANTE ---
        // Sobrescribimos el modelo para impedir la edición
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ESTO HACE QUE NO SE PUEDA EDITAR NADA
            }
        };

        JTable tabla = new JTable(modelo);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(158, 124, 0));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false); // También impide mover columnas

        p.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // --- 3. Lógica del Filtro ---
        KeyAdapter eventoTeclado = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtro = txtBuscar.getText();
                if (filtro.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filtro));
                }
            }
        };  
        
        txtBuscar.addKeyListener(eventoTeclado);
        
        btnBuscar.addActionListener(e -> {
            String filtro = txtBuscar.getText();
            if (filtro.trim().length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filtro));
            }
        });

        return p;
    }
    
    // REQUERIMIENTO: SERVICIOS EXTERNOS Y NOTICIAS (Zona Derecha - PDF Pág 7)
    private JPanel crearPanelNoticias() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(245, 245, 220)); // Beige fondo
        p.setPreferredSize(new Dimension(200, 0));
        p.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(200, 200, 150))); // Borde izquierdo
        
        // Espaciado inicial
        p.add(Box.createVerticalStrut(20));

        // Módulo 1: Noticias
        agregarWidgetDerecho(p, "ÚLTIMAS NOTICIAS", 
            "<html>- Inscripción Examen Saber Pro 2025<br><br>- Cierre de evaluacion docente el 20 Nov.</html>");

        // Módulo 2: Atención
        agregarWidgetDerecho(p, "ATENCIÓN A USUARIO", 
            "<html>- Sistema de Quejas y Reclamos<br><br>- Mesa de Ayuda (3165000 ext 123)</html>");

        // Módulo 3: Enlaces de Interés
        agregarWidgetDerecho(p, "ENLACES RÁPIDOS", 
            "<html>- Bibliotecas<br>- Bienestar Universitario<br>- Campus Virtual</html>");

        p.add(Box.createVerticalGlue()); // Relleno final
        return p;
    }

    // Helper para crear los "cajoncitos" de la derecha
    private void agregarWidgetDerecho(JPanel panel, String titulo, String contenido) {
        // Contenedor del widget
        JPanel widget = new JPanel(new BorderLayout());
        widget.setMaximumSize(new Dimension(180, 150));
        widget.setBorder(BorderFactory.createLineBorder(new Color(200, 160, 50), 1)); // Borde ocre
        
        // Título del widget (Fondo amarillo oscuro)
        JLabel lblTitulo = new JLabel(" " + titulo);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(210, 180, 80)); 
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 11));
        lblTitulo.setPreferredSize(new Dimension(180, 25));
        
        // Contenido del widget
        JLabel lblContenido = new JLabel(contenido);
        lblContenido.setFont(new Font("Arial", Font.PLAIN, 10));
        lblContenido.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lblContenido.setVerticalAlignment(SwingConstants.TOP);
        
        widget.add(lblTitulo, BorderLayout.NORTH);
        widget.add(lblContenido, BorderLayout.CENTER);
        
        panel.add(widget);
        panel.add(Box.createVerticalStrut(15)); // Espacio entre widgets
    }
}