 import javax.swing.*;
import java.awt.*;

public class VentanaConfiguracion extends JFrame {

    public VentanaConfiguracion() {
        setTitle("Configuración del Sistema");
        setSize(1400, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(240, 230, 140)); // Amarillo pálido
        setLayout(new FlowLayout());

        JLabel lblTitulo = new JLabel("Ventana de Configuración");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo);

        JButton btnVolver = new JButton("Volver al Inicio");
        btnVolver.setBackground(new Color(255, 240, 0));
        btnVolver.setForeground(new Color(173, 75, 52));
        add(btnVolver);

        btnVolver.addActionListener(e -> {
            new VentanaPrincipal();
            dispose();
        });

        setVisible(true);
    }
}
