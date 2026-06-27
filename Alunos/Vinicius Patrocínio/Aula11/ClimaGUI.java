import javax.swing.*;
import java.awt.*;

public class ClimaGUI extends JFrame {

    private JTextField txtCidade;
    private JTextArea areaResultado;
    private JButton btnBuscar;

    public ClimaGUI() {

        setTitle("🌤 Consulta de Clima");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color fundo = new Color(15, 23, 42);
        Color painelCor = new Color(30, 41, 59);
        Color campoCor = new Color(51, 65, 85);
        Color destaque = new Color(14, 165, 233);
        Color texto = Color.WHITE;

        getContentPane().setBackground(fundo);
        setLayout(new BorderLayout(15, 15));

        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(painelCor);
        painelTopo.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel lblCidade = new JLabel("Cidade:");
        lblCidade.setForeground(texto);
        lblCidade.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtCidade = new JTextField(20);
        txtCidade.setBackground(campoCor);
        txtCidade.setForeground(texto);
        txtCidade.setCaretColor(texto);
        txtCidade.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(destaque);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        painelTopo.add(lblCidade);
        painelTopo.add(txtCidade);
        painelTopo.add(btnBuscar);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setBackground(painelCor);
        areaResultado.setForeground(texto);
        areaResultado.setCaretColor(texto);
        areaResultado.setFont(new Font("Consolas", Font.PLAIN, 15));
        areaResultado.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarClima());

        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnBuscar.setBackground(new Color(2, 132, 199));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnBuscar.setBackground(destaque);
            }
        });

        setVisible(true);
    }

    private void buscarClima() {

        try {

            ClimaEngine service = new ClimaEngine();

            Clima clima = service.buscarClima(
                    txtCidade.getText()
            );

            areaResultado.setText(
                    "🌡 Temperatura Atual: " + clima.getTempAtual() + "°C\n\n" +
                            "📈 Máxima: " + clima.getTempMax() + "°C\n" +
                            "📉 Mínima: " + clima.getTempMin() + "°C\n\n" +
                            "☁ Condição: " + clima.getCondicao() + "\n" +
                            "💧 Umidade: " + clima.getUmidade() + "%\n" +
                            "🌧 Chuva: " + clima.getChuva() + " mm\n\n" +
                            "💨 Velocidade do Vento: " + clima.getVelVento() + " km/h\n" +
                            "🧭 Direção do Vento: " + clima.getDirVento() + "°"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}