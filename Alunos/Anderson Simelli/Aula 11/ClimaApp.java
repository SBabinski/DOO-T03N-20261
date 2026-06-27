import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;

public class ClimaApp extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String API_KEY = "SUA API AQ";

    private JComboBox<String> comboEstado;
    private JTextField campoCidade;
    private JTextArea areaResultado;

    public ClimaApp() {
        setTitle("Tempo Fácil - Consulta Climática");
        setSize(720, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Tempo Fácil", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitulo = new JLabel("Sistema de consulta climática por cidade e estado", JLabel.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 15));

        JPanel painelTitulo = new JPanel(new GridLayout(2, 1));
        painelTitulo.add(titulo);
        painelTitulo.add(subtitulo);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        String[] estados = {
                "AC", "AL", "AP", "AM", "BA", "CE", "DF",
                "ES", "GO", "MA", "MT", "MS", "MG", "PA",
                "PB", "PR", "PE", "PI", "RJ", "RN", "RS",
                "RO", "RR", "SC", "SP", "SE", "TO"
        };

        comboEstado = new JComboBox<>(estados);
        comboEstado.setSelectedItem("PR");

        campoCidade = new JTextField(22);

        JButton botaoConsultar = new JButton("Consultar Clima");
        JButton botaoLimpar = new JButton("Limpar");

        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        painelFormulario.add(comboEstado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelFormulario.add(new JLabel("Cidade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        painelFormulario.add(campoCidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelFormulario.add(botaoConsultar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        painelFormulario.add(botaoLimpar, gbc);

        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
        painelSuperior.add(painelTitulo, BorderLayout.NORTH);
        painelSuperior.add(painelFormulario, BorderLayout.CENTER);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResultado.setText("Informe o estado e a cidade para consultar o clima.");

        JScrollPane scroll = new JScrollPane(areaResultado);

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);

        botaoConsultar.addActionListener(e -> consultarClima());

        botaoLimpar.addActionListener(e -> {
            campoCidade.setText("");
            comboEstado.setSelectedItem("PR");
            areaResultado.setText("Informe o estado e a cidade para consultar o clima.");
        });

        setVisible(true);
    }

    private void consultarClima() {
        String estado = comboEstado.getSelectedItem().toString();
        String cidade = campoCidade.getText().trim();

        if (cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome da cidade.");
            return;
        }

        if (cidade.length() < 3) {
            JOptionPane.showMessageDialog(this, "Digite uma cidade válida.");
            return;
        }

        areaResultado.setText("Consultando dados climáticos...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            private String resultadoFinal;

            protected Void doInBackground() {
                try {
                    String local = cidade + "," + estado + ",Brasil";
                    String localFormatado = URLEncoder.encode(local, StandardCharsets.UTF_8);

                    String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                            + localFormatado
                            + "/today?unitGroup=metric&include=current&key="
                            + API_KEY
                            + "&contentType=json";

                    HttpClient client = HttpClient.newHttpClient();

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() != 200) {
                        resultadoFinal = "Cidade não encontrada ou erro na consulta.\nCódigo do erro: "
                                + response.statusCode();
                        return null;
                    }

                    String json = response.body();

                    String enderecoRetornado = buscarCampoTexto(json, "\"resolvedAddress\":\"");

                    if (!cidadeFoiEncontrada(cidade, enderecoRetornado)) {
                        resultadoFinal =
                                "Cidade não encontrada.\n\n" +
                                "Verifique se o nome da cidade foi digitado corretamente.\n" +
                                "Exemplo: Cascavel, Curitiba, Rio de Janeiro, São Paulo.";
                        return null;
                    }

                    String blocoAtual = buscarObjeto(json, "\"currentConditions\"");
                    String blocoDia = buscarPrimeiroObjetoArray(json, "\"days\"");

                    String temperaturaAtual = buscarCampo(blocoAtual, "\"temp\":");
                    String temperaturaMaxima = buscarCampo(blocoDia, "\"tempmax\":");
                    String temperaturaMinima = buscarCampo(blocoDia, "\"tempmin\":");
                    String umidade = buscarCampo(blocoAtual, "\"humidity\":");
                    String condicao = buscarCampoTexto(blocoAtual, "\"conditions\":\"");
                    String precipitacao = buscarCampo(blocoAtual, "\"precip\":");
                    String velocidadeVento = buscarCampo(blocoAtual, "\"windspeed\":");
                    String direcaoVento = buscarCampo(blocoAtual, "\"winddir\":");

                    resultadoFinal =
                            "==================================================\n" +
                            "                 RELATÓRIO DO CLIMA\n" +
                            "==================================================\n\n" +
                            "Local consultado: " + enderecoRetornado + "\n\n" +
                            "Temperatura atual: " + temperaturaAtual + " °C\n" +
                            "Temperatura máxima do dia: " + temperaturaMaxima + " °C\n" +
                            "Temperatura mínima do dia: " + temperaturaMinima + " °C\n" +
                            "Umidade do ar: " + umidade + " %\n" +
                            "Condição do tempo: " + traduzirCondicao(condicao) + "\n" +
                            "Precipitação: " + precipitacao + " mm\n" +
                            "Velocidade do vento: " + velocidadeVento + " km/h\n" +
                            "Direção do vento: " + direcaoVento + "°\n\n" +
                            "==================================================\n" +
                            "Consulta realizada com sucesso!";

                } catch (IOException | InterruptedException erro) {
                    resultadoFinal = "Erro ao conectar com a API.";
                } catch (Exception erro) {
                    resultadoFinal = "Erro inesperado: " + erro.getMessage();
                }

                return null;
            }

            protected void done() {
                areaResultado.setText(resultadoFinal);
            }
        };

        worker.execute();
    }

    private boolean cidadeFoiEncontrada(String cidadeDigitada, String enderecoRetornado) {
        if (enderecoRetornado == null || enderecoRetornado.equals("Não encontrado")) {
            return false;
        }

        String cidadeNormalizada = normalizarTexto(cidadeDigitada);
        String enderecoNormalizado = normalizarTexto(enderecoRetornado);

        return enderecoNormalizado.contains(cidadeNormalizada);
    }

    private String normalizarTexto(String texto) {
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        textoNormalizado = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return textoNormalizado.toLowerCase();
    }

    private String buscarObjeto(String json, String chaveObjeto) {
        int inicioChave = json.indexOf(chaveObjeto);

        if (inicioChave == -1) {
            return "";
        }

        int inicioObjeto = json.indexOf("{", inicioChave);
        int contador = 0;

        for (int i = inicioObjeto; i < json.length(); i++) {
            char caractere = json.charAt(i);

            if (caractere == '{') {
                contador++;
            } else if (caractere == '}') {
                contador--;
            }

            if (contador == 0) {
                return json.substring(inicioObjeto, i + 1);
            }
        }

        return "";
    }

    private String buscarPrimeiroObjetoArray(String json, String chaveArray) {
        int inicioArray = json.indexOf(chaveArray);

        if (inicioArray == -1) {
            return "";
        }

        int inicioObjeto = json.indexOf("{", inicioArray);
        int contador = 0;

        for (int i = inicioObjeto; i < json.length(); i++) {
            char caractere = json.charAt(i);

            if (caractere == '{') {
                contador++;
            } else if (caractere == '}') {
                contador--;
            }

            if (contador == 0) {
                return json.substring(inicioObjeto, i + 1);
            }
        }

        return "";
    }

    private String buscarCampo(String json, String chave) {
        int inicio = json.indexOf(chave);

        if (inicio == -1) {
            return "Não encontrado";
        }

        inicio += chave.length();

        int fimVirgula = json.indexOf(",", inicio);
        int fimChave = json.indexOf("}", inicio);

        int fim;

        if (fimVirgula == -1) {
            fim = fimChave;
        } else if (fimChave == -1) {
            fim = fimVirgula;
        } else {
            fim = Math.min(fimVirgula, fimChave);
        }

        return json.substring(inicio, fim);
    }

    private String buscarCampoTexto(String json, String chave) {
        int inicio = json.indexOf(chave);

        if (inicio == -1) {
            return "Não encontrado";
        }

        inicio += chave.length();

        int fim = json.indexOf("\"", inicio);

        return json.substring(inicio, fim);
    }

    private String traduzirCondicao(String condicao) {
        return condicao
                .replace("Rain", "Chuva")
                .replace("Partially cloudy", "Parcialmente nublado")
                .replace("Overcast", "Nublado")
                .replace("Clear", "Céu limpo")
                .replace("Cloudy", "Nublado")
                .replace("Snow", "Neve")
                .replace("Fog", "Neblina");
    }

    public static void main(String[] args) {
        new ClimaApp();
    }
}