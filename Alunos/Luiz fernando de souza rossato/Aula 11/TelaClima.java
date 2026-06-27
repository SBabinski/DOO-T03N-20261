package clima;

import clima.Clima;
import clima.climaParser;
import clima.API;

import javax.swing.*;
import java.awt.*;

public class TelaClima extends JFrame {
    
    private JTextField cidadeField;
    private JButton buscarButton;
    private JTextArea resultadoArea;
    
    
    
    
    public TelaClima() {
        setTitle("Previsão do Tempo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Previsão do Tempo", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

         JPanel painel = new JPanel();

        cidadeField = new JTextField(20);
        buscarButton = new JButton("Buscar");

        painel.add(new JLabel("Cidade:"));
        painel.add(cidadeField);
        painel.add(buscarButton);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);

        add(painel, BorderLayout.NORTH);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        buscarButton.addActionListener(e -> buscarClima());

        setVisible(true);




    }
    private void buscarClima() {
    String cidade = cidadeField.getText().trim();
    if (cidade.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, insira o nome da cidade.",
         "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    

    }
    try {
        String json =API.buscarClima(cidade);
    
        
        Clima clima = (Clima) climaParser.converter(json);

        resultadoArea.setText("Cidade: " + clima.getCidade() + "\n" +
                "Condição: " + clima.getCondicao() + "\n" +
                "Umidade: " + clima.getUmidade() + "%\n" +
                "Precipitação: " + clima.getPrecipitacao() + "mm\n" +
                "Vento: " + clima.getVento() + " km/h\n" +
                "Direção do Vento: " + clima.getDirecaoVento() + "°\n" +
                "Temperatura Máxima: " + clima.getMaxima() + "°C\n" +
                "Temperatura Mínima: " + clima.getMinima() + "°C\n");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao buscar o clima: " + ex.getMessage(),
         "Erro", JOptionPane.ERROR_MESSAGE);

    }

    
}
}