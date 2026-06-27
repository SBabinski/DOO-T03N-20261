
import javax.swing.*;
import java.awt.*;

public class TelaClima extends JFrame {

    private JTextField txtCidade;
    private JTextArea areaResultado;
    private JButton btnBuscar;

    public TelaClima() {

        setTitle("☁️💜 Consulta Meteorológica 💜☁️");
        setSize(550, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color fundo = new Color(245, 235, 255);
        Color roxoClaro = new Color(230, 210, 255);
        Color roxoMedio = new Color(186, 145, 255);
        Color roxoEscuro = new Color(110, 70, 170);

        getContentPane().setBackground(fundo);

        txtCidade = new JTextField(20);
        txtCidade.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnBuscar = new JButton("🔍💜 Pesquisar");
        btnBuscar.setBackground(roxoMedio);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setFocusPainted(false);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        areaResultado.setBackground(new Color(255, 250, 255));
        areaResultado.setForeground(roxoEscuro);

        JPanel painel = new JPanel();
        painel.setBackground(fundo);

        JLabel lblCidade = new JLabel("🌸 Cidade:");
        lblCidade.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCidade.setForeground(roxoEscuro);

        painel.add(lblCidade);
        painel.add(txtCidade);
        painel.add(btnBuscar);

        JScrollPane scroll = new JScrollPane(areaResultado);

        add(painel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarClima());

        setVisible(true);
    }

    private void buscarClima() {

        try {
            ServicoClima service = new ServicoClima();

            Clima clima = service.buscarClima(txtCidade.getText());

            areaResultado.setText(
                    "🌡️ Temperatura Atual: " + clima.getTempAtual() + "°C\n\n" +
                            "☀️ Temperatura Máxima: " + clima.getTempMax() + "°C\n" +
                            "❄️ Temperatura Mínima: " + clima.getTempMin() + "°C\n\n" +
                            "☁️ Condição Climática: " + clima.getCondicao() + "\n\n" +
                            "💧 Umidade do Ar: " + clima.getUmidade() + "%\n\n" +
                            "🌧️ Volume de Chuva: " + clima.getChuva() + " mm\n\n" +
                            "🍃 Velocidade do Vento: " + clima.getVelVento() + " km/h\n\n" +
                            "🧭 Direção do Vento: " + clima.getDirVento() + "°\n\n"

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

