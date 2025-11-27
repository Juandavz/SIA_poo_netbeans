package UI;

import Data.Asignatura;
import Data.DataManager;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelBuscador extends JPanel {

    // Variables globales del módulo
    private CardLayout cardLayoutIzquierda;
    private CardLayout cardLayoutDerecha;
    private JPanel panelBusquedaDinamico;
    private JPanel panelDerecho;
    private JPanel panelCriteriosActivos;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    
    // Colores y Fuentes
    private final Color COLOR_FONDO = Color.WHITE;
    private final Font FUENTE_TITULOS = new Font("Arial", Font.BOLD, 11);

    public PanelBuscador() {
        setLayout(new BorderLayout(10, 0));
        setBackground(COLOR_FONDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Inicializamos los componentes
        add(crearPanelIzquierdo(), BorderLayout.WEST);
        add(crearPanelDerecho(), BorderLayout.CENTER);
    }

    // =======================================================
    //              ZONA IZQUIERDA (CONTROLES)
    // =======================================================
    private JPanel crearPanelIzquierdo() {
        // Usamos un panel contenedor con BorderLayout para evitar 
        // que los componentes se estiren hasta abajo.
        JPanel contenedorGeneral = new JPanel(new BorderLayout());
        contenedorGeneral.setBackground(COLOR_FONDO);
        contenedorGeneral.setPreferredSize(new Dimension(220, 0));

        // Panel que apila los elementos verticalmente
        JPanel stackPanel = new JPanel();
        stackPanel.setLayout(new BoxLayout(stackPanel, BoxLayout.Y_AXIS));
        stackPanel.setBackground(COLOR_FONDO);

        // --- 1. VIDEO TUTORIAL ---
        stackPanel.add(crearHeaderSeccion("VER VIDEO TUTORIAL"));
        
        JLabel lblVideo = new JLabel();
        lblVideo.setAlignmentX(Component.LEFT_ALIGNMENT); // Alineación corregida
        lblVideo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        try {
            ImageIcon iconVid = new ImageIcon("iconTutorial.png");
            // Ajuste de tamaño seguro
            if (iconVid.getIconWidth() > 0) {
                Image img = iconVid.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
                lblVideo.setIcon(new ImageIcon(img));
            } else {
                lblVideo.setText("<html><br>[ VIDEO ]<br><br></html>");
                lblVideo.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) { lblVideo.setText("Error img"); }
        stackPanel.add(lblVideo);

        // --- 2. CRITERIOS DISPONIBLES ---
        stackPanel.add(Box.createVerticalStrut(20));
        stackPanel.add(crearHeaderSeccion("CRITERIOS DISPONIBLES"));

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 5, 0));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(220, 50));

        JButton btnAsig = crearBotonIcono("iconAsignatura.png");
        btnAsig.addActionListener(e -> reiniciarBusqueda());

        JButton btnPlan = crearBotonIcono("iconPlan.png");
        btnPlan.addActionListener(e -> mostrarPopupPlan());

        JButton btnHorario = crearBotonIcono("iconHorario.png");
        btnHorario.addActionListener(e -> mostrarPopupHorario());

        JButton btnTipo = crearBotonIcono("iconTipologia.png");
        btnTipo.addActionListener(e -> mostrarPopupTipologia());

        panelBotones.add(btnAsig);
        panelBotones.add(btnPlan);
        panelBotones.add(btnHorario);
        panelBotones.add(btnTipo);
        stackPanel.add(panelBotones);

        // --- 3. ÁREA DINÁMICA (BUSCADOR / RESUMEN) ---
        stackPanel.add(Box.createVerticalStrut(20));
        
        cardLayoutIzquierda = new CardLayout();
        panelBusquedaDinamico = new JPanel(cardLayoutIzquierda);
        panelBusquedaDinamico.setBackground(COLOR_FONDO);
        panelBusquedaDinamico.setAlignmentX(Component.LEFT_ALIGNMENT);

        // VISTA A: Formulario Texto
        JPanel vistaFormulario = new JPanel(new BorderLayout());
        vistaFormulario.setBackground(COLOR_FONDO);
        
        // Título externo para que coincida con la imagen del usuario
        JLabel lblTituloBusqueda = crearHeaderSeccion("BUSQUEDA BASICA");
        
        JPanel cajaGris = new JPanel(new BorderLayout(0, 5));
        cajaGris.setBackground(new Color(250, 250, 250)); // Gris muy claro
        cajaGris.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cajaGris.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblInstruccion = new JLabel("<html><b>Asignatura</b><br><font size='2' color='gray'>Ingrese parte del nombre o código de la asignatura.</font></html>");
        JTextField txtBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        
        cajaGris.add(lblInstruccion, BorderLayout.NORTH);
        cajaGris.add(txtBuscar, BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        btnPanel.add(btnBuscar);
        cajaGris.add(btnPanel, BorderLayout.SOUTH);

        JPanel wrapperForm = new JPanel(new BorderLayout());
        wrapperForm.setBackground(COLOR_FONDO);
        wrapperForm.add(lblTituloBusqueda, BorderLayout.NORTH);
        wrapperForm.add(cajaGris, BorderLayout.CENTER);
        
        vistaFormulario.add(wrapperForm, BorderLayout.NORTH);

        // VISTA B: Resumen Criterios
        panelCriteriosActivos = new JPanel();
        panelCriteriosActivos.setLayout(new BoxLayout(panelCriteriosActivos, BoxLayout.Y_AXIS));
        panelCriteriosActivos.setBackground(COLOR_FONDO);
        
        JPanel wrapperResumen = new JPanel(new BorderLayout());
        wrapperResumen.setBackground(COLOR_FONDO);
        JLabel lblResumenTitulo = new JLabel("RESUMEN DE CRITERIOS APLICADOS");
        lblResumenTitulo.setFont(FUENTE_TITULOS);
        lblResumenTitulo.setForeground(Color.GRAY);
        lblResumenTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        wrapperResumen.add(lblResumenTitulo, BorderLayout.NORTH);
        wrapperResumen.add(panelCriteriosActivos, BorderLayout.CENTER);
        
        JButton btnNueva = new JButton("Nueva Búsqueda");
        btnNueva.addActionListener(e -> reiniciarBusqueda());
        wrapperResumen.add(btnNueva, BorderLayout.SOUTH);

        panelBusquedaDinamico.add(vistaFormulario, "FORMULARIO");
        panelBusquedaDinamico.add(wrapperResumen, "RESUMEN");

        stackPanel.add(panelBusquedaDinamico);

        // Agregamos el stack al NORTE del contenedor general
        // Esto evita que se estire hasta abajo
        contenedorGeneral.add(stackPanel, BorderLayout.NORTH);
        
        // Lógica del buscador de texto
        ActionListener accionBuscar = e -> {
            String txt = txtBuscar.getText();
            if(!txt.isEmpty()){
                filtrarTabla(txt);
                agregarCriterioResumen("Asignatura", txt, "iconAsignatura.png");
                cardLayoutIzquierda.show(panelBusquedaDinamico, "RESUMEN");
                cardLayoutDerecha.show(panelDerecho, "RESULTADOS"); // Cambia la derecha a resultados
            }
        };
        btnBuscar.addActionListener(accionBuscar);
        txtBuscar.addActionListener(accionBuscar);

        return contenedorGeneral;
    }

    // =======================================================
    //              ZONA DERECHA (RESULTADOS / INSTRUCCIONES)
    // =======================================================
    private JPanel crearPanelDerecho() {
    cardLayoutDerecha = new CardLayout();
    panelDerecho = new JPanel(cardLayoutDerecha);
    panelDerecho.setBackground(COLOR_FONDO);
    panelDerecho.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

    ImageIcon icon = new ImageIcon("instruccionesCatalogoAsignatura.png");
    Image img = icon.getImage();
    Image imgEscalada = img.getScaledInstance(670, 600, Image.SCALE_SMOOTH);
    ImageIcon iconEscalado = new ImageIcon(imgEscalada);

    // ⬅️ Aquí va el JLabel que sí es un componente
    JLabel lblImagen = new JLabel(iconEscalado);

    // Añadir a panelDerecho
    panelDerecho.add(lblImagen, "instrucciones");

     
        // --- VISTA 1: INSTRUCCIONES ---
        JLabel lblInstrucciones = new JLabel();
        lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelDerecho.add(lblInstrucciones, "INSTRUCCIONES");

        // --- VISTA 2: TABLA RESULTADOS ---
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBackground(COLOR_FONDO);

        // Header amarillo de advertencia
        JPanel panelAdvertencia = new JPanel(new BorderLayout());
        panelAdvertencia.setBackground(new Color(255, 255, 204));
        panelAdvertencia.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(255, 204, 0)));
        JLabel lblAdv = new JLabel("<html><b>[!]</b> Haga clic sobre la <b>asignatura</b> para ver los grupos disponibles.<br>&nbsp;&nbsp;&nbsp;&nbsp;Haga clic sobre el <b>grupo</b> para ver la información detallada.</html>");
        lblAdv.setFont(new Font("Arial", Font.PLAIN, 11));
        lblAdv.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelAdvertencia.add(lblAdv, BorderLayout.CENTER);

        JPanel headerContainer = new JPanel(new BorderLayout());
        headerContainer.setBackground(COLOR_FONDO);
        JLabel lblInfo = new JLabel(" Resultado de Búsqueda: Se encontraron asignaturas.");
        lblInfo.setFont(FUENTE_TITULOS);
        lblInfo.setForeground(Color.GRAY);
        headerContainer.add(lblInfo, BorderLayout.NORTH);
        headerContainer.add(panelAdvertencia, BorderLayout.CENTER);
        
        panelResultados.add(headerContainer, BorderLayout.NORTH);

        // Tabla
        String[] cols = {"Código", "Asignatura", "Facultad", "Créditos", "Tipología", "Opción"};
        Object[][] data = DataManager.cargarCatalogo();
        modeloTabla = new DefaultTableModel(data, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloTabla);
        sorter = new TableRowSorter<>(modeloTabla);
        tabla.setRowSorter(sorter);
        diseñarTablaResultados(tabla);
        
        // Listener para "Ver más"
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 1) { // 1 clic para ver más, o doble clic
                    int row = tabla.getSelectedRow();
                    int col = tabla.getSelectedColumn();
                    // Si clic en columna "Opción" (índice 5) o doble clic en cualquier lado
                    if (row != -1 && (col == 5 || e.getClickCount() == 2)) {
                         int modelRow = tabla.convertRowIndexToModel(row);
                         mostrarDetalleAsignatura(modelRow);
                    }
                }
            }
        });

        panelResultados.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelDerecho.add(panelResultados, "RESULTADOS");

        return panelDerecho;
    }

    // =======================================================
    //              MÉTODOS AUXILIARES
    // =======================================================

    private void mostrarDetalleAsignatura(int index) {
        if(index < DataManager.listaAsignaturas.size()) {
            Asignatura asig = DataManager.listaAsignaturas.get(index);
            JOptionPane.showMessageDialog(this, 
                "DETALLE DE ASIGNATURA\n\n" +
                "Asignatura: " + asig.getNombre() + "\n" +
                "Tipología: " + asig.getTipologia() + "\n" +
                "Créditos: " + asig.getCreditos() + "\n" +
                "Cupos Disponibles: " + asig.getCuposDisponibles() + "\n\n" +
                "Grupos disponibles: 1 (Grupo 1 - Lun/Mie 7-9)",
                "Información SIA",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void diseñarTablaResultados(JTable t) {
        t.setRowHeight(24);
        t.setShowVerticalLines(false);
        t.setShowHorizontalLines(true);
        t.setGridColor(new Color(220, 220, 220));
        t.getTableHeader().setBackground(new Color(200, 200, 200));
        t.getTableHeader().setFont(FUENTE_TITULOS);
        
        // Renderizador para la columna "Opción" (índice 5)
        t.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSel, boolean hasFocus, int row, int col) {
                JLabel l = new JLabel(" ver más ");
                l.setHorizontalAlignment(SwingConstants.CENTER);
                l.setOpaque(true);
                l.setBackground(new Color(240, 240, 240));
                l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                l.setFont(new Font("Arial", Font.PLAIN, 10));
                l.setForeground(Color.GRAY);
                return l;
            }
        });
        // Ancho fijo para "ver más"
        t.getColumnModel().getColumn(5).setMaxWidth(70);
        t.getColumnModel().getColumn(5).setMinWidth(70);
    }

    private JLabel crearHeaderSeccion(String titulo) {
        JLabel l = new JLabel(titulo);
        l.setFont(FUENTE_TITULOS);
        l.setForeground(Color.GRAY);
        // Padding izquierdo para que no se corte el texto
        l.setBorder(new EmptyBorder(0, 5, 5, 0)); 
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JButton crearBotonIcono(String nombreIcono) {
        JButton btn = new JButton();
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            ImageIcon icon = new ImageIcon(nombreIcono);
            btn.setIcon(icon);
        } catch (Exception e) {}
        return btn;
    }

    private void agregarCriterioResumen(String titulo, String valor, String icono) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(new Color(245, 245, 245));
        item.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        JLabel lblIcon = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(icono);
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            lblIcon.setIcon(new ImageIcon(img));
        } catch(Exception e){}
        
        JPanel texto = new JPanel(new GridLayout(2, 1));
        texto.setOpaque(false);
        JLabel l1 = new JLabel(titulo); l1.setFont(new Font("Arial", Font.BOLD, 11));
        JLabel l2 = new JLabel(valor); l2.setFont(new Font("Arial", Font.PLAIN, 10)); l2.setForeground(Color.GRAY);
        texto.add(l1); texto.add(l2);
        
        JLabel lblX = new JLabel(" [X] ");
        lblX.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblX.setForeground(Color.RED);
        lblX.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                panelCriteriosActivos.remove(item);
                sorter.setRowFilter(null);
                panelCriteriosActivos.revalidate();
                panelCriteriosActivos.repaint();
                if(panelCriteriosActivos.getComponentCount() == 0) reiniciarBusqueda();
            }
        });

        item.add(lblIcon, BorderLayout.WEST);
        item.add(texto, BorderLayout.CENTER);
        item.add(lblX, BorderLayout.EAST);
        
        // Controlar que no se estire
        item.setMaximumSize(new Dimension(220, 50));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCriteriosActivos.add(item);
        panelCriteriosActivos.revalidate();
    }

    private void reiniciarBusqueda() {
        panelCriteriosActivos.removeAll();
        sorter.setRowFilter(null);
        cardLayoutIzquierda.show(panelBusquedaDinamico, "FORMULARIO");
        // Opcional: Si reinicias búsqueda, ¿vuelves a instrucciones o te quedas en tabla?
        // cardLayoutDerecha.show(panelDerecho, "INSTRUCCIONES"); 
    }

    private void filtrarTabla(String texto) {
        if (texto.trim().length() == 0) sorter.setRowFilter(null);
        else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }
    
    // --- POPUPS ---
    private void mostrarPopupPlan() {
        JPanel p = new JPanel(new GridLayout(4, 1));
        p.add(new JLabel("Seleccione Nivel Académico y Plan:"));
        JRadioButton rbPre = new JRadioButton("pre-grado", true);
        JRadioButton rbPos = new JRadioButton("pos-grado");
        ButtonGroup bg = new ButtonGroup(); bg.add(rbPre); bg.add(rbPos);
        JPanel pRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pRadio.add(rbPre); pRadio.add(rbPos);
        p.add(pRadio);
        JComboBox<String> combo = new JComboBox<>(new String[]{"-- seleccione --", "INGENIERÍA", "CIENCIAS", "HUMANAS"});
        p.add(combo);
        
        if (JOptionPane.showConfirmDialog(this, p, "Plan de Estudio", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if(combo.getSelectedIndex() > 0) {
                agregarCriterioResumen("Plan", combo.getSelectedItem().toString(), "iconPlan.png");
                cardLayoutIzquierda.show(panelBusquedaDinamico, "RESUMEN");
                cardLayoutDerecha.show(panelDerecho, "RESULTADOS");
            }
        }
    }
    
    private void mostrarPopupTipologia() {
        JPanel p = new JPanel(new GridLayout(3, 1));
        p.add(new JLabel("Seleccione Tipología:"));
        JComboBox<String> combo = new JComboBox<>(new String[]{"-- seleccione --", "DISCIPLINAR", "FUNDAMENTACION", "LIBRE ELECCION", "NIVELACION"});
        p.add(combo);
        if (JOptionPane.showConfirmDialog(this, p, "Tipología", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
             if(combo.getSelectedIndex() > 0) {
                 String sel = combo.getSelectedItem().toString();
                 filtrarTabla(sel);
                 agregarCriterioResumen("Tipología", sel, "iconTipologia.png");
                 cardLayoutIzquierda.show(panelBusquedaDinamico, "RESUMEN");
                 cardLayoutDerecha.show(panelDerecho, "RESULTADOS");
             }
        }
    }
    
    private void mostrarPopupHorario() {
        JOptionPane.showMessageDialog(this, "Filtro de horario simulado aplicado.");
        agregarCriterioResumen("Horario", "Franja Horaria", "iconHorario.png");
        cardLayoutIzquierda.show(panelBusquedaDinamico, "RESUMEN");
        cardLayoutDerecha.show(panelDerecho, "RESULTADOS");
    }
}