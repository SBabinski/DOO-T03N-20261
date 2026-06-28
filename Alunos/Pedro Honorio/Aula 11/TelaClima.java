import javax.swing.*;
import java.awt.*;

public class TelaClima extends JFrame {

    private JTextField campoCidade;
    private JButton botaoBuscar;
    private JTextArea areaResultado;

    private WeatherService service;

    public TelaClima() {

        service = new WeatherService();

        setTitle("Aplicativo do Clima");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        campoCidade = new JTextField(20);
        botaoBuscar = new JButton("Buscar Clima");
        areaResultado = new JTextArea(10, 35);
        areaResultado.setEditable(false);

        add(new JLabel("Digite a cidade:"));
        add(campoCidade);
        add(botaoBuscar);
        add(new JScrollPane(areaResultado));

        botaoBuscar.addActionListener(e -> buscarClima());

        setVisible(true);
    }

    private void buscarClima() {

        String cidade = campoCidade.getText().trim();

        if (cidade.isEmpty()) {
            areaResultado.setText("Digite uma cidade válida!");
            return;
        }

        try {
            Clima clima = service.buscarClima(cidade);

            areaResultado.setText(
                    " Clima em: " + cidade + "\n\n" +
                            "Temperatura atual: " + clima.getTemperaturaAtual() + "°C\n" +
                            "Máxima: " + clima.getTemperaturaMaxima() + "°C\n" +
                            "Mínima: " + clima.getTemperaturaMinima() + "°C\n" +
                            "Umidade: " + clima.getUmidade() + "%\n" +
                            "Condição: " + clima.getCondicao() + "\n" +
                            "Precipitação: " + clima.getPrecipitacao() + " mm\n" +
                            "Vento: " + clima.getVelocidadeVento() + " km/h\n" +
                            "Direção: " + clima.getDirecaoVento() + "°"
            );

        } catch (Exception e) {
            areaResultado.setText("Erro ao buscar clima.");
        }
    }
}
