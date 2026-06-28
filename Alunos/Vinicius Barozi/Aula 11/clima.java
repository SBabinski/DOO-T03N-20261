import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class clima {

    private static final String APP_TITLE = "Clima Tempo";
    private static final String API_KEY = "VA62EC4U66QKUTYEPE93GHESR"; 
    private static final String API_URL_TEMPLATE =
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&include=current&key=%s&elements=datetime,temp,tempmax,tempmin,humidity,conditions,precip,winddir,windspeed";

    private JTextField cidadeCampo;
    private JTextArea saidaArea;
    private JButton buscarBotao;
    private JFrame frame;
    private String apiKey;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new clima().mostrarGUI());
    }

    private void mostrarGUI() {
        // Primeiro tenta usar a chave fixa definida no código.
        apiKey = API_KEY;
        if (apiKey == null || apiKey.trim().isEmpty()) {
            // Se não tiver chave fixa, tenta a variável de ambiente.
            apiKey = System.getenv("VC_API_KEY");
        }
        if (apiKey == null || apiKey.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "A aplicação precisa da chave da API para funcionar.\n" +
                    "Coloque a chave em API_KEY no código ou na variável VC_API_KEY.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        frame = new JFrame(APP_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 380));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topo.add(new JLabel("Cidade / Local:"));

        cidadeCampo = new JTextField(22);
        cidadeCampo.setText("São Paulo");
        topo.add(cidadeCampo);

        buscarBotao = new JButton("Buscar");
        buscarBotao.addActionListener(e -> buscarClima());
        topo.add(buscarBotao);

        JButton limpar = new JButton("Limpar");
        limpar.addActionListener(e -> saidaArea.setText(""));
        topo.add(limpar);

        frame.add(topo, BorderLayout.NORTH);

        saidaArea = new JTextArea();
        saidaArea.setEditable(false);
        saidaArea.setLineWrap(true);
        saidaArea.setWrapStyleWord(true);
        frame.add(new JScrollPane(saidaArea), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void buscarClima() {
        final String local = cidadeCampo.getText().trim();
        if (local.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Informe uma cidade ou local válido.",
                    "Entrada inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        buscarBotao.setEnabled(false);
        saidaArea.setText("Buscando dados do clima para '" + local + "'...\n");

        new Thread(() -> {
            try {
                String endereco = montarUrl(local);
                String json = buscarJson(endereco);
                if (json == null || json.isEmpty()) {
                    mostrarResultado("Não foi possível obter dados da API.");
                    return;
                }

                String cidade = extrairString(json, "resolvedAddress");
                String atual = extrairObjeto(json, "currentConditions");
                String dia = extrairElementoArray(json, "days", 0);

                if (atual == null || dia == null) {
                    mostrarResultado("Resposta inesperada da API.");
                    return;
                }

                double tempAgora = extrairDouble(atual, "temp");
                double umidade = extrairDouble(atual, "humidity");
                String cond = extrairString(atual, "conditions");
                double precip = extrairDouble(atual, "precip");
                double ventoVel = extrairDouble(atual, "windspeed");
                double ventoDir = extrairDouble(atual, "winddir");

                double tempMax = extrairDouble(dia, "tempmax");
                double tempMin = extrairDouble(dia, "tempmin");

                String texto = String.format("""
                        Clima para: %s
                        Temperatura agora: %.1f °C
                        Máxima do dia: %.1f °C
                        Mínima do dia: %.1f °C
                        Humidade: %.0f %%
                        Condição do tempo: %s
                        Precipitação: %.1f mm
                        Vento: %.1f km/h, direção %.0f°
                        """,
                        cidade, tempAgora, tempMax, tempMin,
                        umidade, cond, precip, ventoVel, ventoDir);

                mostrarResultado(texto);
            } catch (IOException ex) {
                mostrarResultado("Erro ao acessar a API: " + ex.getMessage());
            } catch (RuntimeException ex) {
                mostrarResultado("Erro inesperado: " + ex.getMessage());
            } finally {
                SwingUtilities.invokeLater(() -> buscarBotao.setEnabled(true));
            }
        }).start();
    }

    private void mostrarResultado(final String texto) {
        SwingUtilities.invokeLater(() -> saidaArea.setText(texto));
    }

    private String montarUrl(String local) throws UnsupportedEncodingException {
        String localEnc = URLEncoder.encode(local, StandardCharsets.UTF_8.toString());
        String chaveEnc = URLEncoder.encode(apiKey, StandardCharsets.UTF_8.toString());
        return String.format(API_URL_TEMPLATE, localEnc, chaveEnc);
    }

    private String buscarJson(String endereco) throws IOException {
        URL url = new URL(endereco);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(10000);
        conexao.setReadTimeout(10000);

        int status = conexao.getResponseCode();
        InputStream in;
        if (status >= 200 && status < 300) {
            in = conexao.getInputStream();
        } else {
            in = conexao.getErrorStream();
        }

        if (in == null) {
            conexao.disconnect();
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                sb.append(linha).append('\n');
            }
            return sb.toString();
        } finally {
            conexao.disconnect();
        }
    }

    private static String extrairObjeto(String json, String chave) {
        int pos = json.indexOf('"' + chave + '"');
        if (pos < 0) return null;
        int inicio = json.indexOf('{', pos);
        if (inicio < 0) return null;
        int prof = 0;
        for (int i = inicio; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') prof++;
            else if (c == '}') {
                prof--;
                if (prof == 0) return json.substring(inicio, i + 1);
            }
        }
        return null;
    }

    private static String extrairElementoArray(String json, String chave, int idx) {
        int pos = json.indexOf('"' + chave + '"');
        if (pos < 0) return null;
        int inicio = json.indexOf('[', pos);
        if (inicio < 0) return null;
        int prof = 0;
        int cont = -1;
        int startElem = -1;
        for (int i = inicio + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (prof == 0) cont++;
                if (cont == idx) startElem = i;
                prof++;
            } else if (c == '}') {
                prof--;
                if (startElem >= 0 && prof == 0) return json.substring(startElem, i + 1);
            }
        }
        return null;
    }

    private static String extrairString(String json, String chave) {
        String raw = extrairValor(json, chave);
        return raw == null ? "" : raw;
    }

    private static double extrairDouble(String json, String chave) {
        String raw = extrairValor(json, chave);
        if (raw == null || raw.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static String extrairValor(String json, String chave) {
        int pos = json.indexOf('"' + chave + '"');
        if (pos < 0) return null;
        int doisPontos = json.indexOf(':', pos);
        if (doisPontos < 0) return null;
        int i = doisPontos + 1;
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) i++;
        if (i >= json.length()) return null;
        char c = json.charAt(i);
        if (c == '"') {
            i++;
            StringBuilder sb = new StringBuilder();
            while (i < json.length()) {
                char ch = json.charAt(i);
                if (ch == '"') return sb.toString();
                if (ch == '\\' && i + 1 < json.length()) {
                    i++;
                    sb.append(json.charAt(i));
                } else sb.append(ch);
                i++;
            }
            return sb.toString();
        }
        int inicio = i;
        while (i < json.length() && "-0123456789.eE".indexOf(json.charAt(i)) >= 0) i++;
        if (inicio == i) return null;
        return json.substring(inicio, i);
    }
}
