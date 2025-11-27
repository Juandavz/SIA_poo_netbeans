package UI;

import javax.swing.*;
import java.awt.*;

public class VentanaConfiguracion extends JFrame {
    
    private String usuarioActual; // Guardamos quién entró

    public VentanaConfiguracion(String usuario) {
        this.usuarioActual = usuario;

        setTitle("Configuración del Sistema");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo institucional suave
        getContentPane().setBackground(new Color(255, 253, 208)); 
        setLayout(new FlowLayout());

        JLabel lblTitulo = new JLabel("Configuración de Cuenta");
        lblTitulo.setFont(new Font("Verdana", Font.BOLD, 18));
        add(lblTitulo);

        JButton btnVolver = new JButton("<< Volver al SIA");
        btnVolver.setBackground(new Color(148, 172, 59)); // Verde institucional
        btnVolver.setForeground(Color.WHITE);
        add(btnVolver);

        btnVolver.addActionListener(e -> {
            // AQUÍ ESTABA EL ERROR: Ahora devolvemos el usuario
            new VentanaPrincipal(usuarioActual); 
            dispose();
        });

        setVisible(true);
    }
}