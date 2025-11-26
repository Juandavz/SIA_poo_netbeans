import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    public VentanaLogin() {
    
        setTitle("Inicio de Sesión");
        setSize(1400, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // PANEL PRINCIPAL
        JPanel panel = new JPanel();
        panel.setBackground(new Color(241, 226, 159)); 
        panel.setLayout(null);
        add(panel);
//encabezado        
        // 🔸 RECUADRO SUPERIOR (TÍTULO)
        JPanel recuadro4 = new JPanel();
        recuadro4.setBackground(new Color(255, 203, 0));
        recuadro4.setBounds(0, 0, 1400, 80);
        recuadro4.setLayout(null);
        recuadro4.setBorder(BorderFactory.createLineBorder(new Color(201, 153, 18), 3, true));
        panel.add(recuadro4);

        ImageIcon iconoOriginal = new ImageIcon("LogoUN.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
        JLabel lblImagen = new JLabel(new ImageIcon(imagenEscalada));
        lblImagen.setBounds(40, -10, 120, 100);
        recuadro4.add(lblImagen);
        // 🔸 4. Agregar el recuadro al panel principal
        panel.add(recuadro4);
        
        // 🔸 RECUADRO SUPERIOR (TÍTULO)
        JPanel recuadro3 = new JPanel();
        recuadro3.setBackground(new Color(158, 124, 0));
        recuadro3.setBounds(0, 80, 1400, 30);
        recuadro3.setLayout(null);
        recuadro3.setBorder(BorderFactory.createLineBorder(new Color(201, 153, 18), 3, true));
        panel.add(recuadro3);

        JLabel lblSIA = new JLabel("Sistema de Información Académica                                                           Visitante [ No identificado ]");
        lblSIA.setForeground(new Color(253, 250, 234));
        lblSIA.setFont(new Font("Arial", Font.BOLD, 14));
        lblSIA.setBounds(80, 5, 1300, 25);
        recuadro3.add(lblSIA);
        
//Menu        
        ImageIcon inicio = new ImageIcon("Inicio.png");
        Image inicioEscalado = inicio.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        JLabel lblImagen2 = new JLabel(new ImageIcon(inicioEscalado));
        lblImagen2.setBounds(80, -80, 1400, 1000);
        panel.add(lblImagen2);
        
        ImageIcon inicio2 = new ImageIcon("Inicio2.png");
        Image inicioEscaladoa = inicio2.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel lblImagen3 = new JLabel(new ImageIcon(inicioEscaladoa));
        lblImagen3.setBounds(-450, 20, 1400, 1000);
        panel.add(lblImagen3);
        
        

        // 🔸 TÍTULO DEL FORMULARIO
        JPanel recuadroTitulo = new JPanel();
        recuadroTitulo.setBackground(new Color(255, 196, 0));
        recuadroTitulo.setBounds(100, 130, 300, 50);
        recuadroTitulo.setLayout(null);
        recuadroTitulo.setBorder(BorderFactory.createLineBorder(new Color(201, 153, 18), 3, true));
        panel.add(recuadroTitulo);

        JLabel lblINICIE = new JLabel("INICIE SESIÓN AQUÍ");
        lblINICIE.setForeground(new Color(201, 153, 18));
        lblINICIE.setFont(new Font("Arial", Font.BOLD, 14));
        lblINICIE.setBounds(80, 10, 160, 25);
        recuadroTitulo.add(lblINICIE);

        // 🔸 PANEL DE LOGIN
        JPanel recuadroLogin = new JPanel();
        recuadroLogin.setBackground(new Color(241, 226, 159));
        recuadroLogin.setBounds(100, 180, 300, 170);
        recuadroLogin.setLayout(null);
        recuadroLogin.setBorder(BorderFactory.createLineBorder(new Color(201, 153, 18), 3, true));
        panel.add(recuadroLogin);

        JLabel lblUsuario = new JLabel("Nombre de usuario:");
        lblUsuario.setForeground(new Color(201, 153, 18));
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuario.setBounds(50, 20, 160, 25);
        recuadroLogin.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(50, 40, 200, 25);
        recuadroLogin.add(txtUsuario);

        JLabel lblClave = new JLabel("Clave:");
        lblClave.setForeground(new Color(201, 153, 18));
        lblClave.setFont(new Font("Arial", Font.BOLD, 14));
        lblClave.setBounds(50, 70, 80, 25);
        recuadroLogin.add(lblClave);

        JPasswordField txtClave = new JPasswordField();
        txtClave.setBounds(50, 90, 200, 25);
        recuadroLogin.add(txtClave);
        
        JButton btnEntrar = new JButton("Iniciar sesión");
        btnEntrar.setBounds(50, 120, 200, 30);
        btnEntrar.setBackground(new Color(255, 240, 0));
        btnEntrar.setForeground(new Color(173, 75, 52));
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 13));
        recuadroLogin.add(btnEntrar);

        // --- AQUÍ ESTÁ EL CAMBIO ---
        btnEntrar.addActionListener(e -> {
                    // Dentro del ActionListener del botón:
        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());

        // USAR EL NUEVO GESTOR DE DATOS
        if (DataManager.validarUsuario(usuario, clave)) {
            new VentanaPrincipal(usuario); // <--- AHORA PASAMOS EL USUARIO
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas (Verifique usuarios.csv)");
        }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
    }
}
