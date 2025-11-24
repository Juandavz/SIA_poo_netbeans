import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Página Principal");
        setSize(1400, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(241, 226, 159)); 
        panel2.setLayout(null);
        add(panel2);

       

//Encabezado
        // 🔸 RECUADRO SUPERIOR (TÍTULO)
        JPanel recuadro4 = new JPanel();
        recuadro4.setBackground(new Color(255, 203, 0));
        recuadro4.setBounds(0, 0, 1400, 80);
        recuadro4.setLayout(null);
        recuadro4.setBorder(BorderFactory.createLineBorder(new Color(201, 153, 18), 3, true));
        panel2.add(recuadro4);

        ImageIcon iconoOriginal = new ImageIcon("LogoUN.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
        JLabel lblImagen = new JLabel(new ImageIcon(imagenEscalada));
        lblImagen.setBounds(40, -10, 120, 100);
        recuadro4.add(lblImagen);
        panel2.add(recuadro4);
        
        // 🔸 RECUADRO SUPERIOR (TÍTULO)
        JPanel recuadro3 = new JPanel();
        recuadro3.setBackground(new Color(158, 124, 0));
        recuadro3.setBounds(0, 80, 1400, 30);
        recuadro3.setLayout(null);
        recuadro3.setBorder(BorderFactory.createLineBorder(new Color(201, 153, 18), 3, true));
        panel2.add(recuadro3);

        JLabel lblSIA = new JLabel("Sistema de Información Académica                                                           Visitante");
        lblSIA.setForeground(new Color(253, 250, 234));
        lblSIA.setFont(new Font("Arial", Font.BOLD, 14));
        lblSIA.setBounds(80, 5, 1300, 25);
        recuadro3.add(lblSIA);
//Menu

        
        setVisible(true);
    }
}
