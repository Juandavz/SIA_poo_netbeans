package UI;

import Data.DataManager;
import Logic.CalculadoraAcademica;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PanelHistoria {

    // ==============================
    // VARIABLES DE INSTANCIA
    // ==============================
    private final String usuarioActual;
    private double promedioAcademico;
    private double papa;
    private double porcentajeAvance;

    private final int CREDITOS_TOTALES_EXIGIDOS = 195;

    // ==============================
    // CONSTRUCTOR
    // ==============================
    public PanelHistoria(String usuario) {
        this.usuarioActual = usuario;

        List<Object[]> asignaturasDelEstudiante =
                DataManager.obtenerAsignaturasPorEstudiante(usuario);

        CalculadoraAcademica calc = new CalculadoraAcademica(asignaturasDelEstudiante);

        double[] resultados = calc.calcularMetricas();

        this.papa = resultados[0];
        this.promedioAcademico = resultados[0];

        int totalCreditosAprobados = (int) resultados[1];

        this.porcentajeAvance =
                calc.calcularPorcentajeAvance(totalCreditosAprobados, CREDITOS_TOTALES_EXIGIDOS);
    }

    // ==============================
    // PANEL PRINCIPAL
    // ==============================
    public JPanel crearPanelHistoria() {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        // -----------------------------------------------------
        // CABECERA
        // -----------------------------------------------------
        JPanel headerInfo = new JPanel(new BorderLayout());
        headerInfo.setBackground(Color.WHITE);

        JLabel title = new JLabel("REPORTE DE NOTAS");
        title.setFont(new Font("Arial", Font.BOLD, 14));

        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        JLabel date = new JLabel("Generado: " + fecha);
        date.setFont(new Font("Arial", Font.PLAIN, 11));
        date.setForeground(Color.GRAY);

        headerInfo.add(title, BorderLayout.WEST);
        headerInfo.add(date, BorderLayout.EAST);

        topPanel.add(headerInfo);
        topPanel.add(Box.createVerticalStrut(10));

        // Datos básicos del estudiante
        topPanel.add(crearCajaInfo("1023941 [ " + usuarioActual + " ]"));
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(crearCajaInfo("2541 INGENIERÍA DE MECATRONICA"));
        topPanel.add(Box.createVerticalStrut(15));

        // -----------------------------------------------------
        // HISTORIA ACADÉMICA
        // -----------------------------------------------------
        topPanel.add(crearSeccionPeriodo("HISTORIA ACADÉMICA COMPLETA", usuarioActual));

        // -----------------------------------------------------
        // RESÚMENES
        // -----------------------------------------------------
        topPanel.add(Box.createVerticalStrut(20));

        JPanel resumen = new JPanel();
        resumen.setLayout(new BoxLayout(resumen, BoxLayout.Y_AXIS));
        resumen.setBackground(Color.WHITE);

        resumen.add(crearCajaInfo("Resumen academico:"));
        resumen.add(Box.createVerticalStrut(5));

        topPanel.add(resumen);

        topPanel.add(crearPanelResumenPrincipal());
        topPanel.add(Box.createVerticalStrut(15));

        topPanel.add(crearTablaResumenCreditos());
        topPanel.add(Box.createVerticalStrut(15));

        topPanel.add(crearTablaResumenFinal());
        topPanel.add(Box.createVerticalStrut(10));

        JScrollPane scroll = new JScrollPane(topPanel);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    // ==============================
    // CAJA DE INFORMACIÓN SIMPLE
    // ==============================
    private JPanel crearCajaInfo(String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel(text));
        p.setBackground(Color.LIGHT_GRAY);
        p.setBorder(new EmptyBorder(2, 5, 2, 5));
        return p;
    }

    // ==============================
    // SECCIÓN DE UN PERIODO
    // ==============================
    private JPanel crearSeccionPeriodo(String titulo, String usuarioData) {

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);

        p.add(Box.createVerticalStrut(10));
        p.add(crearCajaInfo(titulo));
        p.add(Box.createVerticalStrut(5));

        String[] cols = {"Asignatura", "Créditos", "Nota", "Periodo", "Tipologia"};
        Object[][] data = DataManager.cargarHistoria(usuarioData);

        JTable tabla = new JTable(data, cols);

        tabla.setRowHeight(22);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFillsViewportHeight(true);

        int alturaTabla =
                tabla.getRowHeight() * tabla.getRowCount() +
                tabla.getTableHeader().getPreferredSize().height;

        JScrollPane sp = new JScrollPane(tabla);
        sp.setPreferredSize(new Dimension(tabla.getPreferredSize().width, alturaTabla));

        p.add(sp);
        return p;
    }

    // ==============================
    // PANEL RESUMEN PRINCIPAL
    // ==============================
    private JPanel crearPanelResumenPrincipal() {

        JPanel p = new JPanel(new BorderLayout(20, 0));
        p.setBackground(Color.WHITE);

        // ----------- Panel Izquierdo (promedios + avance)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        // Promedio Académico
        JLabel labelPA = new JLabel("Promedio Académico");
        labelPA.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel valuePA = new JLabel("[" + String.format("%.1f", promedioAcademico) + "]");
        valuePA.setFont(new Font("Arial", Font.BOLD, 18));
        valuePA.setForeground(new Color(17, 120, 100));

        JPanel promAcadPanel = new JPanel(new BorderLayout());
        promAcadPanel.setBackground(Color.WHITE);
        promAcadPanel.add(labelPA, BorderLayout.WEST);
        promAcadPanel.add(valuePA, BorderLayout.EAST);

        // PAPA
        JLabel labelPAPA = new JLabel("Promedio Aritmético Ponderado Acumulado - PAPA");
        labelPAPA.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel valuePAPA = new JLabel("[" + String.format("%.1f", papa) + "]");
        valuePAPA.setFont(new Font("Arial", Font.BOLD, 18));
        valuePAPA.setForeground(new Color(17, 120, 100));

        JPanel papaPanel = new JPanel(new BorderLayout());
        papaPanel.setBackground(Color.WHITE);
        papaPanel.add(labelPAPA, BorderLayout.WEST);
        papaPanel.add(valuePAPA, BorderLayout.EAST);

        leftPanel.add(promAcadPanel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(papaPanel);
        leftPanel.add(Box.createVerticalStrut(10));

        // Barra de progreso
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBackground(Color.WHITE);

        JProgressBar progress = new JProgressBar(0, 100);
        progress.setValue((int) porcentajeAvance);
        progress.setString(String.format("%.1f%%", porcentajeAvance));
        progress.setStringPainted(true);
        progress.setForeground(new Color(255, 165, 0));
        progress.setBackground(Color.LIGHT_GRAY);
        progress.setPreferredSize(new Dimension(300, 20));

        JLabel progressText =
                new JLabel("% de avance en los créditos exigidos del plan de estudios");
        progressText.setFont(new Font("Arial", Font.PLAIN, 10));

        progressPanel.add(progress, BorderLayout.WEST);
        progressPanel.add(progressText, BorderLayout.CENTER);

        leftPanel.add(progressPanel);

        // ----------- Gráfico de avance (Panel derecho)
        JPanel grafico = new JPanel() {
           Image imagen1 = new ImageIcon("muneco1.png").getImage();
           Image imagen2 = new ImageIcon("muneco2.png").getImage();
           Image imagen3 = new ImageIcon("muneco3.png").getImage();
           Image imagen4 = new ImageIcon("muneco4.png").getImage();
           Image imagen5 = new ImageIcon("muneco5.png").getImage();
           Image imagen6 = new ImageIcon("muneco6.png").getImage();
           Image imagen7 = new ImageIcon("muneco7.png").getImage();
           Image imagen8 = new ImageIcon("muneco8.png").getImage();
           Image imagen9 = new ImageIcon("muneco9.png").getImage();
           Image imagen10 = new ImageIcon("muneco10.png").getImage();
           

    Image imagenActual = imagen1;  // Por defecto

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo
        g.setColor(new Color(245, 245, 245));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Seleccionar imagen según porcentaje, usando switch por rangos
        if (porcentajeAvance >= 0 && porcentajeAvance < 20) {
         imagenActual = imagen1;
        }
        else if (porcentajeAvance >= 20 && porcentajeAvance < 30) {
         imagenActual = imagen2;
        }
        else if (porcentajeAvance >= 30 && porcentajeAvance < 40) {
        imagenActual = imagen3;
        }
        else if (porcentajeAvance >= 40 && porcentajeAvance < 50) {
        imagenActual = imagen4;
        }
        else if (porcentajeAvance >= 50 && porcentajeAvance < 60) {
        imagenActual = imagen5;
        }
        else if (porcentajeAvance >= 60 && porcentajeAvance < 70) {
        imagenActual = imagen6;
        }
        else if (porcentajeAvance >= 70 && porcentajeAvance < 80) {
        imagenActual = imagen7;
        }
        else if (porcentajeAvance >= 80 && porcentajeAvance < 90) {
        imagenActual = imagen8;
        }
        else if (porcentajeAvance >= 90 && porcentajeAvance < 100) {
        imagenActual = imagen9;
        }
        else if (porcentajeAvance >= 100) {
        imagenActual = imagen10;
        }

        int w = imagenActual.getWidth(null);
        int h = imagenActual.getHeight(null);

        
        g.drawImage(imagenActual, 0, 0, null);
    }
        };

        grafico.setBackground(Color.WHITE);

        p.add(leftPanel, BorderLayout.WEST);
        p.add(grafico, BorderLayout.EAST);

        return p;
    }

    // ==============================
    // TABLA DE RESUMEN DE CRÉDITOS
    // ==============================
    private JPanel crearTablaResumenCreditos() {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.add(Box.createVerticalStrut(15), BorderLayout.NORTH);

        String[] cols = {
                "créditos", "fm. oblig", "fm. optativ",
                "dc. oblig", "dc. optativ",
                "tb. grado", "libre elec.",
                "total", "nivel", "total estudiante"
        };

        Object[][] data = DataManager.cargarDatosResumen();

        JTable tabla = new JTable(data, cols);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(22);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setBackground(new Color(200, 200, 200));

        for (int i = 1; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            tabla.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            tabla.getColumnModel().getColumn(i).setPreferredWidth(60);
        }

        tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(0).setHeaderRenderer(headerRenderer);

        JScrollPane sp = new JScrollPane(tabla);

        int altura =
                tabla.getRowHeight() * tabla.getRowCount() +
                tabla.getTableHeader().getPreferredSize().height;

        sp.setPreferredSize(new Dimension(800, altura));
        p.add(sp, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(Color.WHITE);

        bottom.add(crearCajaInfoCreditos("Total Créditos Excedentes", "0", Color.GREEN));
        bottom.add(crearCajaInfoCreditos("Total Créditos Cancelados en los Periodos Cursados", "0", Color.GREEN));

        p.add(bottom, BorderLayout.SOUTH);

        return p;
    }

    private JPanel crearCajaInfoCreditos(String title, String value, Color color) {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel valuePanel = new JPanel();
        valuePanel.setBackground(color.brighter());
        valuePanel.setBorder(BorderFactory.createLineBorder(color.darker()));

        JLabel v = new JLabel(value);
        v.setFont(new Font("Arial", Font.BOLD, 12));

        valuePanel.add(v);

        p.add(t, BorderLayout.WEST);
        p.add(valuePanel, BorderLayout.EAST);

        return p;
    }

    // ==============================
    // TABLA RESUMEN FINAL
    // ==============================
    private JPanel crearTablaResumenFinal() {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        JLabel title = new JLabel("resumen de créditos");
        title.setFont(new Font("Arial", Font.BOLD, 11));

        String[] cols = {"", ""};
        Object[][] data = DataManager.cargarResumenFinal();

        JTable tabla = new JTable(data, cols);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(22);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 0));

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        rightRenderer.setBackground(new Color(240, 240, 240));

        tabla.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

        JScrollPane sp = new JScrollPane(tabla);

        int altura = tabla.getRowHeight() * tabla.getRowCount();
        sp.setPreferredSize(new Dimension(300, altura));

        p.add(title, BorderLayout.NORTH);
        p.add(sp, BorderLayout.CENTER);

        return p;
    }
}
