import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {

    private JTextField txtCidade;
    private JTextArea areaResultado;
    private JButton btnBuscar;

    public Principal() {

        setTitle("Consulta Meteorológica");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtCidade = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        areaResultado = new JTextArea();

        areaResultado.setEditable(false);

        JPanel painel = new JPanel();

        painel.add(new JLabel("Cidade:"));
        painel.add(txtCidade);
        painel.add(btnBuscar);

        add(painel, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarClima());

        setVisible(true);
    }

    private void buscarClima() {

        try {

            ServicoClima service = new ServicoClima();

            Clima clima =
                    service.buscarClima(txtCidade.getText());

            areaResultado.setText(
                    "Temperatura Atual: " + clima.getTempAtual() + "°C\n" +
                            "Máxima: " + clima.getTempMax() + "°C\n" +
                            "Mínima: " + clima.getTempMin() + "°C\n" +
                            "Condição: " + clima.getCondicao() + "\n" +
                            "Umidade: " + clima.getUmidade() + "%\n" +
                            "Chuva: " + clima.getChuva() + " mm\n" +
                            "Vento: " + clima.getVelVento() + " km/h\n" +
                            "Direção: " + clima.getDirVento() + "°"
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