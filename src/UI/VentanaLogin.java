package UI;

import Data.DataManager;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaLogin extends JFrame {

    // --- PALETA DE COLORES EXACTA (SIA 2015) ---
    private static final Color COLOR_FONDO_MAIN = new Color(244, 237, 199); 
    private static final Color COLOR_HEADER_TOP = new Color(247, 203, 28);  
    private static final Color COLOR_HEADER_BOT = new Color(123, 105, 40);   
    private static final Color COLOR_BORDE_CONTENEDOR = new Color(190, 183, 139); 
    private static final Color COLOR_TEXTO_BLANCO = new Color(244, 237, 199); // Crema claro para textos sobre oscuro
    private static final Color COLOR_TEXTO_GENERICO = new Color(123, 105, 40);

    // Colores Columna Izquierda
    private static final Color COLOR_LOGIN_HEADER_BG = new Color(255, 229, 105); 
    private static final Color COLOR_LOGIN_HEADER_TEXT = new Color(123, 105, 40); 
    private static final Color COLOR_LOGIN_BODY_BG = new Color(255, 255, 236); 
    private static final Color COLOR_LOGIN_BORDER = new Color(228, 218, 174); 
    private static final Color COLOR_LOGIN_LABEL = new Color(123, 105, 40); 
    private static final Color COLOR_BTN_LOGIN_BG = new Color(249, 240, 67); 
    private static final Color COLOR_BTN_LOGIN_BORDER = new Color(200, 190, 60); 

    private static final Color COLOR_AZUL_HEADER_BG = new Color(153, 204, 255); 
    private static final Color COLOR_AZUL_HEADER_TEXT = new Color(50, 80, 120); 
    private static final Color COLOR_AZUL_BODY_BG = new Color(209, 230, 252); 
    private static final Color COLOR_AZUL_BORDER = new Color(140, 180, 220); 
    private static final Color COLOR_AZUL_TEXT = new Color(60, 90, 140); 

    private static final Color COLOR_NARANJA_HEADER_BG = new Color(255, 204, 51); 
    private static final Color COLOR_NARANJA_HEADER_TEXT = new Color(120, 80, 20); 
    private static final Color COLOR_NARANJA_BODY_BG = new Color(247, 203, 28); 
    private static final Color COLOR_NARANJA_BORDER = new Color(220, 186, 60); 
    private static final Color COLOR_NARANJA_TEXT = new Color(100, 70, 10); 
    private static final Color COLOR_BTN_CONSULTAR_NARANJA = new Color(252, 244, 156); 

    // Colores Panel Central
    private static final Color COLOR_BIENVENIDA_BG = new Color(228, 220, 148);
    private static final Color COLOR_NOTICIA_BG = new Color(244, 244, 188);
    private static final Color COLOR_DIVISORIA = new Color(200, 195, 160);

    // Colores Servicios
    private static final Color COLOR_SERV_HEADER = new Color(168, 158, 40); 
    private static final Color COLOR_YELLOW_HEADER = new Color(252, 236, 68);
    private static final Color COLOR_YELLOW_BODY = new Color(252, 244, 156);
    private static final Color COLOR_OLIVE_HEADER = new Color(214, 209, 155); 
    private static final Color COLOR_OLIVE_BODY = new Color(228, 224, 180);

    // Colores Footer y RSS
    private static final Color COLOR_RSS_FONDO = new Color(220, 223, 228); 
    private static final Color COLOR_RSS_HEADER = new Color(200, 205, 210);
    private static final Color COLOR_FOOTER_BG = new Color(212, 207, 160); 
    private static final Color COLOR_FOOTER_LINE = new Color(0, 102, 51); 

    public VentanaLogin() {
        setTitle("Sistema de Información Académica - SIA");
        setSize(1366, 950); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true); 

        // 1. PANEL BASE (Con Scroll)
        JPanel panelBase = new JPanel();
        panelBase.setBackground(COLOR_FONDO_MAIN);
        panelBase.setLayout(null);
        
        // AUMENTAMOS LA ALTURA TOTAL DEL SCROLL PARA QUE QUEPA TODO
        panelBase.setPreferredSize(new Dimension(1366, 1350)); 

        JScrollPane scrollPane = new JScrollPane(panelBase);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);

        // ==========================================
        // 2. ENCABEZADOS
        // ==========================================
        
        // --- HEADER SUPERIOR (Amarillo) ---
        JPanel headerTop = new JPanel();
        headerTop.setBackground(COLOR_HEADER_TOP);
        headerTop.setBounds(0, 0, 1366, 85);
        headerTop.setLayout(null);
        panelBase.add(headerTop);

        JLabel lblImagen = cargarImagenSegura("LogoUN.png", -1, 90);
        lblImagen.setBounds(20, 7, 400, 70);
        lblImagen.setHorizontalAlignment(SwingConstants.LEFT);
        headerTop.add(lblImagen);
        
        // --- HEADER INFERIOR (Café/Oliva) ---
        JPanel headerBot = new JPanel();
        headerBot.setBackground(COLOR_HEADER_BOT);
        headerBot.setBounds(0, 85, 1366, 35);
        headerBot.setLayout(null);
        panelBase.add(headerBot);

        // 1. Texto Izquierda
        JLabel lblSIA = new JLabel("Sistema de Información Académica");
        lblSIA.setForeground(COLOR_TEXTO_BLANCO);
        lblSIA.setFont(new Font("Arial", Font.BOLD, 14));
        lblSIA.setBounds(20, 0, 400, 35);
        headerBot.add(lblSIA);

        // 2. Texto Centro-Derecha (Visitante)
        JLabel lblVisitante = new JLabel("Visitante [ no identificado ]");
        lblVisitante.setForeground(COLOR_TEXTO_BLANCO);
        lblVisitante.setFont(new Font("Arial", Font.BOLD, 14));
        lblVisitante.setBounds(580, 0, 300, 35); 
        headerBot.add(lblVisitante);

        // 3. Texto Derecha (USUARIO ACTIVO) - MOVIDO AQUÍ
        JLabel lblUsuarioActivo = new JLabel("← USUARIO ACTIVO");
        lblUsuarioActivo.setForeground(COLOR_TEXTO_BLANCO); // Color claro para contraste
        lblUsuarioActivo.setFont(new Font("Arial", Font.BOLD, 12));
        // Alineado a la derecha dentro de la barra café
        lblUsuarioActivo.setBounds(1150, 0, 200, 35); 
        headerBot.add(lblUsuarioActivo);

        // ==========================================
        // 3. CONTENEDOR CENTRAL (EXPANDIDO)
        // ==========================================
        JPanel contenedorCentral = new JPanel();
        contenedorCentral.setBackground(COLOR_FONDO_MAIN);
        contenedorCentral.setLayout(null);
        contenedorCentral.setBorder(new LineBorder(COLOR_BORDE_CONTENEDOR, 1));
        contenedorCentral.setBounds(83, 130, 1200, 950); 
        panelBase.add(contenedorCentral);

        // --- COLUMNA 1: IZQUIERDA ---
        
        // A. LOGIN
        JPanel loginHeaderPanel = new JPanel();
        loginHeaderPanel.setBackground(COLOR_LOGIN_HEADER_BG);
        loginHeaderPanel.setBounds(10, 10, 250, 35); 
        loginHeaderPanel.setLayout(null);
        loginHeaderPanel.setBorder(new LineBorder(COLOR_LOGIN_BORDER, 1));
        contenedorCentral.add(loginHeaderPanel);

        JLabel lblInicieT = new JLabel("INICIE SESIÓN AQUÍ");
        lblInicieT.setForeground(COLOR_LOGIN_HEADER_TEXT);
        lblInicieT.setFont(new Font("Arial", Font.BOLD, 13));
        lblInicieT.setBounds(15, 0, 200, 35);
        loginHeaderPanel.add(lblInicieT);

        JPanel loginBodyPanel = new JPanel();
        loginBodyPanel.setBackground(COLOR_LOGIN_BODY_BG);
        loginBodyPanel.setBounds(10, 44, 250, 180);
        loginBodyPanel.setLayout(null);
        loginBodyPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, COLOR_LOGIN_BORDER));
        contenedorCentral.add(loginBodyPanel);

        JLabel lblUser = new JLabel("↓ nombre de usuario:");
        lblUser.setForeground(COLOR_LOGIN_LABEL);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUser.setBounds(15, 15, 200, 20);
        loginBodyPanel.add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(15, 35, 220, 25);
        txtUser.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_LOGIN_BORDER), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
        loginBodyPanel.add(txtUser);

        JLabel lblPass = new JLabel("↓ clave:");
        lblPass.setForeground(COLOR_LOGIN_LABEL);
        lblPass.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPass.setBounds(15, 70, 200, 20);
        loginBodyPanel.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(15, 90, 220, 25);
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_LOGIN_BORDER), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
        loginBodyPanel.add(txtPass);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBounds(15, 135, 220, 30);
        btnLogin.setBackground(COLOR_BTN_LOGIN_BG);
        btnLogin.setForeground(COLOR_LOGIN_LABEL);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(new LineBorder(COLOR_BTN_LOGIN_BORDER, 1));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btnLogin.setBackground(new Color(255, 250, 100)); }
            public void mouseExited(MouseEvent evt) { btnLogin.setBackground(COLOR_BTN_LOGIN_BG); }
        });
        loginBodyPanel.add(btnLogin);

        // B. ADMITIDOS
        int yAzul = 235;
        JPanel azulHeader = new JPanel();
        azulHeader.setBackground(COLOR_AZUL_HEADER_BG);
        azulHeader.setBounds(10, yAzul, 250, 30);
        azulHeader.setLayout(null);
        azulHeader.setBorder(new LineBorder(COLOR_AZUL_BORDER));
        contenedorCentral.add(azulHeader);

        JLabel lblAdmitidos = new JLabel("ADMITIDOS AL 2015-I");
        lblAdmitidos.setForeground(COLOR_AZUL_HEADER_TEXT); 
        lblAdmitidos.setFont(new Font("Arial", Font.BOLD, 13));
        lblAdmitidos.setBounds(10, 0, 200, 30);
        azulHeader.add(lblAdmitidos);

        JPanel azulBody = new JPanel();
        azulBody.setBackground(COLOR_AZUL_BODY_BG);
        azulBody.setBounds(10, yAzul + 30, 250, 75);
        azulBody.setLayout(null);
        azulBody.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, COLOR_AZUL_BORDER));
        contenedorCentral.add(azulBody);

        JPanel btnConsultarAzul = new JPanel(new BorderLayout());
        btnConsultarAzul.setBackground(Color.WHITE);
        btnConsultarAzul.setBounds(10, 10, 230, 25);
        btnConsultarAzul.setBorder(new LineBorder(COLOR_AZUL_BORDER));
        btnConsultarAzul.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel lblTextoConsultarAzul = new JLabel(" consultar info.");
        lblTextoConsultarAzul.setFont(new Font("Arial", Font.PLAIN, 12));
        btnConsultarAzul.add(lblTextoConsultarAzul, BorderLayout.CENTER);
        JLabel lblFlechaAzul = new JLabel("➡ ");
        lblFlechaAzul.setForeground(Color.RED);
        btnConsultarAzul.add(lblFlechaAzul, BorderLayout.EAST);
        azulBody.add(btnConsultarAzul);

        JTextArea txtInfoAzul = new JTextArea("consulte el proceso de registros y matrícula y diligencie el formulario de registro en línea.");
        txtInfoAzul.setLineWrap(true);
        txtInfoAzul.setWrapStyleWord(true);
        txtInfoAzul.setBackground(COLOR_AZUL_BODY_BG);
        txtInfoAzul.setForeground(COLOR_AZUL_TEXT);
        txtInfoAzul.setFont(new Font("Arial", Font.PLAIN, 11));
        txtInfoAzul.setEditable(false);
        txtInfoAzul.setBounds(10, 40, 230, 30);
        azulBody.add(txtInfoAzul);

        // C. PAGOS ELECTRÓNICOS
        int yNaranja = 350;
        JPanel naranjaHeader = new JPanel();
        naranjaHeader.setBackground(COLOR_NARANJA_HEADER_BG);
        naranjaHeader.setBounds(10, yNaranja, 250, 30);
        naranjaHeader.setLayout(null);
        naranjaHeader.setBorder(new LineBorder(COLOR_NARANJA_BORDER));
        contenedorCentral.add(naranjaHeader);

        JLabel lblPagos = new JLabel("<html>PAGOS ELECTRÓNICOS A<br>TRAVÉS DE INTERNET</html>");
        lblPagos.setForeground(COLOR_NARANJA_HEADER_TEXT); 
        lblPagos.setFont(new Font("Arial", Font.BOLD, 11));
        lblPagos.setBounds(10, 0, 230, 30);
        naranjaHeader.add(lblPagos);

        JPanel naranjaBody = new JPanel();
        naranjaBody.setBackground(COLOR_NARANJA_BODY_BG);
        naranjaBody.setBounds(10, yNaranja + 30, 250, 115);
        naranjaBody.setLayout(null);
        naranjaBody.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, COLOR_NARANJA_BORDER));
        contenedorCentral.add(naranjaBody);

        JPanel btnConsultarNaranja = new JPanel(new BorderLayout());
        btnConsultarNaranja.setBackground(COLOR_BTN_CONSULTAR_NARANJA);
        btnConsultarNaranja.setBounds(10, 10, 230, 25);
        btnConsultarNaranja.setBorder(new LineBorder(COLOR_NARANJA_BORDER));
        btnConsultarNaranja.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblTextoConsultarNaranja = new JLabel(" consultar info.");
        lblTextoConsultarNaranja.setFont(new Font("Arial", Font.PLAIN, 12));
        btnConsultarNaranja.add(lblTextoConsultarNaranja, BorderLayout.CENTER);
        JLabel lblFlechaNaranja = new JLabel("➡ ");
        lblFlechaNaranja.setForeground(Color.RED);
        btnConsultarNaranja.add(lblFlechaNaranja, BorderLayout.EAST);
        naranjaBody.add(btnConsultarNaranja);

        JTextArea txtInfoNaranja = new JTextArea("La Gerencia Nacional Financiera y Administrativa y la Dirección Nacional de Informática y Comunicaciones, informan que se encuentra disponible la opción de pagos electrónicos para matrículas de pregrado.");
        txtInfoNaranja.setLineWrap(true);
        txtInfoNaranja.setWrapStyleWord(true);
        txtInfoNaranja.setBackground(COLOR_NARANJA_BODY_BG);
        txtInfoNaranja.setForeground(COLOR_NARANJA_TEXT);
        txtInfoNaranja.setFont(new Font("Arial", Font.PLAIN, 11));
        txtInfoNaranja.setEditable(false);
        txtInfoNaranja.setBounds(10, 40, 230, 70);
        naranjaBody.add(txtInfoNaranja);

        // --- COLUMNA 2: CENTRO ---

        JLabel lblBreadcrumb = new JLabel("inicio");
        lblBreadcrumb.setForeground(new Color(150, 150, 150));
        lblBreadcrumb.setBounds(280, 10, 200, 20);
        contenedorCentral.add(lblBreadcrumb);

        // BIENVENIDA
        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setLayout(null);
        panelBienvenida.setBackground(COLOR_BIENVENIDA_BG); 
        panelBienvenida.setBounds(280, 40, 350, 250); 
        panelBienvenida.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, COLOR_DIVISORIA));
        contenedorCentral.add(panelBienvenida);

        JLabel lblTituloBienvenida = new JLabel("<html><b>[♟] Bienvenido Visitante,</b></html>");
        lblTituloBienvenida.setForeground(new Color(100, 80, 20));
        lblTituloBienvenida.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTituloBienvenida.setBounds(20, 20, 300, 25);
        panelBienvenida.add(lblTituloBienvenida);

        String htmlBienvenida = "<html><body style='width: 260px; text-align: justify;'>" + 
                "<p style='margin-bottom:8px'><font color='#FFFFFF'><b>&#8599;</b></font> <font color='#999966'>Si usted es un <b>usuario activo</b> de la Universidad<br>por favor inicie su sesión.</font></p>" +
                "<p style='margin-bottom:8px'><font color='#FFFFFF'><b>&#8599;</b></font> <font color='#999966'>Si usted fue <b>admitido</b> a la Universidad por favor<br>consulte el proceso de registro.</font></p>" +
                "<p><font color='#FFFFFF'><b>&#8595;</b></font> <font color='#999966'>Si no cumple ningún caso anterior por favor<br>seleccione el servicio que desea consultar</font></p>" +
                "</body></html>";

        JLabel lblTextoBienvenida = new JLabel(htmlBienvenida);
        lblTextoBienvenida.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTextoBienvenida.setBounds(20, 50, 300, 200); 
        lblTextoBienvenida.setVerticalAlignment(SwingConstants.TOP); 
        panelBienvenida.add(lblTextoBienvenida);

        // NOTICIAS
        JPanel panelNoticia = new JPanel();
        panelNoticia.setLayout(null);
        panelNoticia.setBackground(COLOR_NOTICIA_BG); 
        panelNoticia.setBounds(630, 40, 330, 250); 
        panelNoticia.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, COLOR_DIVISORIA));
        contenedorCentral.add(panelNoticia);

        JLabel lblTituloNoticia = new JLabel("<html><b>[ ! ] Noticia Destacada</b></html>");
        lblTituloNoticia.setForeground(new Color(80, 60, 10));
        lblTituloNoticia.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTituloNoticia.setBounds(20, 20, 300, 25); 
        panelNoticia.add(lblTituloNoticia);

        JLabel lblFecha = new JLabel("+09 de octubre 2014");
        lblFecha.setForeground(Color.GRAY);
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 11));
        lblFecha.setBounds(20, 50, 200, 15);
        panelNoticia.add(lblFecha);

        JLabel lblNoticiaTitulo = new JLabel("<html><font color='#CC3333'><b>Descuento Electoral Estudiantes Sede Bogotá para 2015-I</b></font></html>");
        lblNoticiaTitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblNoticiaTitulo.setBounds(20, 70, 280, 40);
        panelNoticia.add(lblNoticiaTitulo);

        JTextArea txtNoticiaCuerpo = new JTextArea("Descuento Electoral Estudiantes Sede Bogotá para 2015-I");
        txtNoticiaCuerpo.setLineWrap(true);
        txtNoticiaCuerpo.setWrapStyleWord(true);
        txtNoticiaCuerpo.setBackground(COLOR_NOTICIA_BG); 
        txtNoticiaCuerpo.setForeground(new Color(100, 100, 80));
        txtNoticiaCuerpo.setFont(new Font("Arial", Font.PLAIN, 12));
        txtNoticiaCuerpo.setEditable(false);
        txtNoticiaCuerpo.setBounds(20, 115, 250, 50);
        panelNoticia.add(txtNoticiaCuerpo);

        JLabel lblVerMas = new JLabel("ver noticia completa >>");
        lblVerMas.setForeground(new Color(200, 100, 50));
        lblVerMas.setFont(new Font("Arial", Font.PLAIN, 11));
        lblVerMas.setBounds(20, 190, 200, 20);
        panelNoticia.add(lblVerMas);

        // PANEL DE SERVICIOS INFERIOR (AJUSTADO)
        JPanel panelServicios = new JPanel();
        panelServicios.setLayout(null);
        panelServicios.setBounds(280, 300, 680, 610); 
        panelServicios.setBackground(COLOR_FONDO_MAIN);
        panelServicios.setBorder(new LineBorder(COLOR_BORDE_CONTENEDOR));
        contenedorCentral.add(panelServicios);

        // Header Servicios
        JPanel headerServicios = new JPanel();
        headerServicios.setBackground(COLOR_SERV_HEADER);
        headerServicios.setBounds(0, 0, 680, 30);
        headerServicios.setLayout(null);
        panelServicios.add(headerServicios);

        JLabel lblTituloServ = new JLabel("SELECCIONE UN SERVICIO                                               [ VISITANTE ]");
        lblTituloServ.setForeground(new Color(240, 240, 230));
        lblTituloServ.setFont(new Font("Arial", Font.BOLD, 12));
        lblTituloServ.setBounds(10, 0, 600, 30);
        headerServicios.add(lblTituloServ);

        // 1. APOYO ACADÉMICO
        JPanel subHeader1 = new JPanel();
        subHeader1.setBackground(COLOR_YELLOW_HEADER); 
        subHeader1.setBounds(0, 30, 680, 25);
        subHeader1.setLayout(null);
        panelServicios.add(subHeader1);
        
        JLabel lblApoyo = new JLabel("apoyo académico");
        lblApoyo.setForeground(new Color(100, 80, 20));
        lblApoyo.setFont(new Font("Arial", Font.BOLD, 12));
        lblApoyo.setBounds(10, 0, 200, 25);
        subHeader1.add(lblApoyo);

        JPanel body1 = new JPanel();
        body1.setBackground(COLOR_YELLOW_BODY); 
        body1.setBounds(0, 55, 680, 90); // Alto: 90
        body1.setLayout(null);
        panelServicios.add(body1);

        JLabel lblLink1 = new JLabel("› citación cancelación");
        lblLink1.setForeground(new Color(100, 80, 20));
        lblLink1.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLink1.setBounds(10, 10, 200, 20); 
        body1.add(lblLink1);
        
        JLabel lblIconoMochila = cargarImagenSegura("maleta.gif", 70, 70);
        lblIconoMochila.setBounds(605, 10, 70, 70); 
        body1.add(lblIconoMochila);

        // 2. CATÁLOGO
        JPanel subHeader2 = new JPanel();
        subHeader2.setBackground(COLOR_YELLOW_HEADER);
        subHeader2.setBounds(0, 145, 680, 25);
        subHeader2.setLayout(null);
        panelServicios.add(subHeader2);

        JLabel lblCatalogo = new JLabel("catálogo prog. curriculares");
        lblCatalogo.setForeground(new Color(100, 80, 20));
        lblCatalogo.setFont(new Font("Arial", Font.BOLD, 12));
        lblCatalogo.setBounds(10, 0, 300, 25);
        subHeader2.add(lblCatalogo);

        JPanel body2 = new JPanel();
        body2.setBackground(COLOR_YELLOW_BODY);
        body2.setBounds(0, 170, 680, 90);
        body2.setLayout(null);
        panelServicios.add(body2);

        JLabel lblLink2 = new JLabel("› programas posgrado                    › programas pregrado");
        lblLink2.setForeground(new Color(100, 80, 20));
        lblLink2.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLink2.setBounds(10, 10, 400, 20);
        body2.add(lblLink2);

        // 3. DOCUMENTACIÓN
        JPanel subHeader3 = new JPanel();
        subHeader3.setBackground(COLOR_YELLOW_HEADER);
        subHeader3.setBounds(0, 260, 680, 25);
        subHeader3.setLayout(null);
        panelServicios.add(subHeader3);

        JLabel lblDocu = new JLabel("documentación");
        lblDocu.setForeground(new Color(100, 80, 20));
        lblDocu.setFont(new Font("Arial", Font.BOLD, 12));
        lblDocu.setBounds(10, 0, 300, 25);
        subHeader3.add(lblDocu);

        JPanel body3 = new JPanel();
        body3.setBackground(COLOR_YELLOW_BODY);
        body3.setBounds(0, 285, 680, 90);
        body3.setLayout(null);
        panelServicios.add(body3);

        JLabel lblLink3 = new JLabel("<html>› consulta de manuales &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; › guías rápidas<br></html>");
        lblLink3.setForeground(new Color(100, 80, 20));
        lblLink3.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLink3.setBounds(10, 10, 400, 40);
        body3.add(lblLink3);

        JLabel lblIconoLibros = cargarImagenSegura("libros.gif", 70, 70);
        lblIconoLibros.setBounds(605, 10, 70, 70);
        body3.add(lblIconoLibros);

        // 4. LIBRE ACCESO
        JPanel subHeader4 = new JPanel();
        subHeader4.setBackground(COLOR_OLIVE_HEADER); 
        subHeader4.setBounds(0, 375, 680, 25);
        subHeader4.setLayout(null);
        panelServicios.add(subHeader4);

        JLabel lblLibre = new JLabel("libre acceso");
        lblLibre.setForeground(new Color(80, 60, 10));
        lblLibre.setFont(new Font("Arial", Font.BOLD, 12));
        lblLibre.setBounds(10, 0, 300, 25);
        subHeader4.add(lblLibre);

        JPanel body4 = new JPanel();
        body4.setBackground(COLOR_OLIVE_BODY); 
        body4.setBounds(0, 400, 680, 90);
        body4.setLayout(null);
        panelServicios.add(body4);

        String textLibre = "<html><table width='500'><tr><td>› comentarios y sugerencias</td><td>› consultar correo oficial</td></tr>" +
                           "<tr><td>› mapa del sitio</td><td>› preguntas frecuentes</td></tr>" +
                           "<tr><td>› terminos de uso</td><td></td></tr></table></html>";
        JLabel lblLink4 = new JLabel(textLibre);
        lblLink4.setForeground(new Color(100, 80, 20));
        lblLink4.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLink4.setBounds(10, 10, 500, 60);
        body4.add(lblLink4);

        JLabel lblIconoCandado = cargarImagenSegura("candado.gif", 70, 70);
        lblIconoCandado.setBounds(605, 10, 70, 70);
        body4.add(lblIconoCandado);

        // 5. ZONA DE BÚSQUEDAS
        JPanel subHeader5 = new JPanel();
        subHeader5.setBackground(COLOR_OLIVE_HEADER); 
        subHeader5.setBounds(0, 490, 680, 25);
        subHeader5.setLayout(null);
        panelServicios.add(subHeader5);

        JLabel lblZona = new JLabel("zona de búsquedas");
        lblZona.setForeground(new Color(80, 60, 10));
        lblZona.setFont(new Font("Arial", Font.BOLD, 12));
        lblZona.setBounds(10, 0, 300, 25);
        subHeader5.add(lblZona);

        JPanel body5 = new JPanel();
        body5.setBackground(COLOR_OLIVE_BODY);
        body5.setBounds(0, 515, 680, 90);
        body5.setLayout(null);
        panelServicios.add(body5);

        JLabel lblLink5 = new JLabel("› buscador de cursos                  › contenido de asignaturas");
        lblLink5.setForeground(new Color(100, 80, 20));
        lblLink5.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLink5.setBounds(10, 10, 500, 20);
        body5.add(lblLink5);

        JLabel lblIconoLupa = cargarImagenSegura("lupa.gif", 70, 70);
        lblIconoLupa.setBounds(605, 10, 70, 70);
        body5.add(lblIconoLupa);


        // --- COLUMNA 3: DERECHA ---
        
        JPanel panelRSS = new JPanel();
        panelRSS.setLayout(null);
        panelRSS.setBackground(COLOR_RSS_FONDO);
        panelRSS.setBounds(970, 40, 200, 250); 
        panelRSS.setBorder(new LineBorder(Color.GRAY));
        contenedorCentral.add(panelRSS);

        JPanel headerRSS = new JPanel();
        headerRSS.setBackground(COLOR_RSS_HEADER);
        headerRSS.setBounds(0, 0, 200, 30);
        headerRSS.setLayout(null);
        headerRSS.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        panelRSS.add(headerRSS);

        JLabel lblRSS = new JLabel("ÚLTIMAS NOTICIAS");
        lblRSS.setForeground(Color.DARK_GRAY);
        lblRSS.setFont(new Font("Arial", Font.BOLD, 11));
        lblRSS.setBounds(5, 0, 150, 30);
        headerRSS.add(lblRSS);

        JTextArea txtRSSContent = new JTextArea(
            "\n+25 de mayo 2015\n" +
            "Inscripción para la presentación del Examen Saber Pro - 2015, ver noticia completa...\n\n" +
            "+26 de septiembre 2014\n" +
            "No entregue su usuario y clave de correo electrónico... ver noticia completa"
        );
        txtRSSContent.setFont(new Font("Arial", Font.PLAIN, 11));
        txtRSSContent.setForeground(Color.DARK_GRAY);
        txtRSSContent.setBackground(COLOR_RSS_FONDO);
        txtRSSContent.setEditable(false);
        txtRSSContent.setLineWrap(true);
        txtRSSContent.setWrapStyleWord(true);
        txtRSSContent.setBounds(5, 35, 190, 210);
        panelRSS.add(txtRSSContent);

        // BOTONES LATERALES
        int yBtns = 300;

        JPanel btnAtencion = new JPanel();
        btnAtencion.setLayout(null);
        btnAtencion.setBackground(new Color(255, 255, 204));
        btnAtencion.setBounds(970, yBtns, 200, 45);
        btnAtencion.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 100), 1));
        contenedorCentral.add(btnAtencion);
        
        JLabel lblAtn = new JLabel("<html><center><font color='#990000'><b>Atención</b></font><br><font color='#990000'>a problemas</font></center></html>");
        lblAtn.setBounds(10, 0, 100, 45);
        btnAtencion.add(lblAtn);
        JLabel lblIconoMail = new JLabel("✉"); 
        lblIconoMail.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        lblIconoMail.setBounds(150, 5, 40, 35);
        btnAtencion.add(lblIconoMail);

        JPanel btnQuejas = new JPanel();
        btnQuejas.setLayout(null);
        btnQuejas.setBackground(new Color(196, 214, 186));
        btnQuejas.setBounds(970, yBtns + 55, 200, 45);
        btnQuejas.setBorder(BorderFactory.createLineBorder(new Color(100, 120, 100), 1));
        contenedorCentral.add(btnQuejas);

        JLabel lblQuejas = new JLabel("<html><center>sistema de<br><b>QUEJAS Y RECLAMOS</b><br>sede bogota</center></html>");
        lblQuejas.setFont(new Font("Arial", Font.PLAIN, 11));
        lblQuejas.setForeground(new Color(50, 70, 50));
        lblQuejas.setBounds(0, 0, 200, 45);
        lblQuejas.setHorizontalAlignment(SwingConstants.CENTER);
        btnQuejas.add(lblQuejas);

        JPanel btnEncuesta = new JPanel();
        btnEncuesta.setLayout(new BorderLayout());
        btnEncuesta.setBackground(Color.WHITE);
        btnEncuesta.setBounds(970, yBtns + 110, 200, 45);
        btnEncuesta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        contenedorCentral.add(btnEncuesta);

        JLabel lblIconoEncuesta = cargarImagenSegura("image_2.gif", 198, 43);
        btnEncuesta.add(lblIconoEncuesta, BorderLayout.CENTER);

        JPanel btnCert = new JPanel();
        btnCert.setLayout(new BorderLayout());
        btnCert.setBackground(Color.WHITE);
        btnCert.setBounds(970, yBtns + 165, 200, 45);
        btnCert.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        contenedorCentral.add(btnCert);

        JLabel lblIconoCert = cargarImagenSegura("image_5.gif", 198, 43);
        btnCert.add(lblIconoCert, BorderLayout.CENTER);


        // ==========================================
        // 4. PIE DE PÁGINA (FOOTER)
        // ==========================================
        
        int footerY = 1090; 
        
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(null);
        footerPanel.setBackground(COLOR_FOOTER_BG);
        footerPanel.setBounds(0, footerY, 1366, 200);
        panelBase.add(footerPanel);

        JPanel greenLine = new JPanel();
        greenLine.setBackground(COLOR_FOOTER_LINE);
        greenLine.setBounds(0, 0, 1366, 7);
        footerPanel.add(greenLine);

        String textLinks = "<html><center>inicio | comentarios y sugerencias | preguntas frecuentes | descarga de archivos | mapa del sitio | noticias | términos de uso<br>" +
                           "Sistema de Información Académica [Nivel Nacional]</center></html>";
        JLabel lblFooterLinks = new JLabel(textLinks);
        lblFooterLinks.setForeground(new Color(100, 80, 20));
        lblFooterLinks.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooterLinks.setBounds(0, 20, 1366, 40);
        lblFooterLinks.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(lblFooterLinks);

        JPanel dottedSep = new JPanel();
        dottedSep.setOpaque(false);
        dottedSep.setBounds(100, 65, 1166, 2);
        dottedSep.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(150, 140, 100)));
        footerPanel.add(dottedSep);

        String textAddr = "<html><center>Ciudad Universitaria, Cra 30 # 45-03, Centro de Computo, Bogotá ( Colombia ) | Conmutador (57) (1) 3165000 | webmaster@unal.edu.co</center></html>";
        JLabel lblFooterAddr = new JLabel(textAddr);
        lblFooterAddr.setForeground(new Color(100, 80, 20));
        lblFooterAddr.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooterAddr.setBounds(0, 75, 1366, 20);
        lblFooterAddr.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(lblFooterAddr);

        // LÓGICA DE ACCESO
        btnLogin.addActionListener(e -> {
            String usuario = txtUser.getText();
            String clave = new String(txtPass.getPassword());

            if (DataManager.validarUsuario(usuario, clave)) {
                new VentanaPrincipal(usuario).setVisible(true); 
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas (Verifique usuarios.csv)");
            }
        });
    }

    // --- MÉTODO DE AYUDA PARA CARGAR IMÁGENES DE FORMA SEGURA ---
    private JLabel cargarImagenSegura(String nombreArchivo, int ancho, int alto) {
        ImageIcon icon = new ImageIcon(nombreArchivo);
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            java.net.URL url = getClass().getResource("/" + nombreArchivo);
            if (url != null) {
                icon = new ImageIcon(url);
            }
        }

        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        } else {
            JLabel lblError = new JLabel("❌ " + nombreArchivo);
            lblError.setForeground(Color.RED);
            lblError.setBorder(BorderFactory.createLineBorder(Color.RED));
            lblError.setHorizontalAlignment(SwingConstants.CENTER);
            System.err.println("ERROR: No se pudo cargar la imagen: " + nombreArchivo);
            return lblError;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
    }
}