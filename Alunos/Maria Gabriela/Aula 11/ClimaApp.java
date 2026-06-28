import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;

public class ClimaApp extends JFrame {

    private JTextField campoCidade;
    private JPanel cardClima;
    private JPanel fundo;

    private JPopupMenu popup = new JPopupMenu();
    private JList<String> lista = new JList<>();

    private static final String API_KEY = "YNSH5SQNDK5FS7N8W3AKMKFJM";

    public ClimaApp() {
        setTitle("Clima");
        setSize(420, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        fundo = new JPanel(new BorderLayout(10, 10));
        fundo.setBackground(new Color(10, 12, 18));

        JPanel topo = new JPanel();
        topo.setOpaque(false);

        campoCidade = new JTextField(15);
        campoCidade.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        campoCidade.setBackground(new Color(35, 37, 45));
        campoCidade.setForeground(Color.WHITE);
        campoCidade.setCaretColor(Color.WHITE);
        campoCidade.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(0, 122, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);

        topo.add(campoCidade);
        topo.add(btnBuscar);

        cardClima = new JPanel();
        cardClima.setLayout(new BoxLayout(cardClima, BoxLayout.Y_AXIS));
        cardClima.setBackground(new Color(30, 32, 40));
        cardClima.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        wrapper.add(cardClima);

        fundo.add(topo, BorderLayout.NORTH);
        fundo.add(wrapper, BorderLayout.CENTER);

        add(fundo);

        btnBuscar.addActionListener((ActionEvent e) -> buscarClima());

        campoCidade.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { buscarSugestoes(campoCidade.getText()); }
            public void removeUpdate(DocumentEvent e) { buscarSugestoes(campoCidade.getText()); }
            public void changedUpdate(DocumentEvent e) { buscarSugestoes(campoCidade.getText()); }
        });
    }

    private void buscarSugestoes(String texto) {
        if (texto.length() < 2) return;

        try {
            String urlStr = "https://geocoding-api.open-meteo.com/v1/search?name="
                    + texto + "&count=5&language=pt&format=json";

            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            JSONObject json = new JSONObject(sb.toString());

            if (!json.has("results")) return;

            DefaultListModel<String> model = new DefaultListModel<>();
            JSONArray results = json.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject c = results.getJSONObject(i);

                String nome = c.getString("name");
                String estado = c.optString("admin1", "");
                String pais = c.getString("country");

                model.addElement(nome + " - " + estado + ", " + pais);
            }

            mostrarPopup(model);

        } catch (Exception e) {
        }
    }

    private void mostrarPopup(DefaultListModel<String> model) {
        if (model.isEmpty()) return;

        lista.setModel(model);
        lista.setBackground(new Color(30, 32, 40));
        lista.setForeground(Color.WHITE);

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                campoCidade.setText(lista.getSelectedValue());
                popup.setVisible(false);
            }
        });

        popup.removeAll();
        popup.add(new JScrollPane(lista));
        popup.show(campoCidade, 0, campoCidade.getHeight());
    }

    private void buscarClima() {
        try {
            String cidadeDigitada = campoCidade.getText().split(" - ")[0];

            String urlString = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                    + cidadeDigitada + "?unitGroup=metric&key=" + API_KEY + "&contentType=json";

            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(this, "Cidade não encontrada");
                return;
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            JSONObject json = new JSONObject(sb.toString());

            JSONObject atual = json.getJSONObject("currentConditions");
            JSONObject hoje = json.getJSONArray("days").getJSONObject(0);

            String cidade = json.getString("resolvedAddress");

            double temp = atual.getDouble("temp");
            double max = hoje.getDouble("tempmax");
            double min = hoje.getDouble("tempmin");
            double hum = atual.getDouble("humidity");
            double vento = atual.getDouble("windspeed");
            String cond = atual.getString("conditions");

            aplicarTema(cond);
            atualizarUI(cidade, temp, max, min, hum, vento, cond);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clima");
        }
    }

    private void aplicarTema(String cond) {
        cond = cond.toLowerCase();

        if (cond.contains("rain")) fundo.setBackground(new Color(18, 22, 30));
        else if (cond.contains("cloud")) fundo.setBackground(new Color(25, 28, 38));
        else if (cond.contains("clear")) fundo.setBackground(new Color(10, 12, 18));
        else fundo.setBackground(new Color(15, 18, 25));
    }

    private void atualizarUI(String cidade, double temp, double max, double min,
                             double hum, double vento, String cond) {

        cardClima.removeAll();

        cardClima.add(titulo(cidade));
        cardClima.add(Box.createVerticalStrut(10));
        cardClima.add(tempGrande(temp + "°"));
        cardClima.add(subtitulo(traduzirCondicao(cond)));

        cardClima.add(Box.createVerticalStrut(25));

        cardClima.add(linha("Máx", max + "°"));
        cardClima.add(linha("Mín", min + "°"));
        cardClima.add(linha("Umidade", hum + "%"));
        cardClima.add(linha("Vento", vento + " km/h"));

        cardClima.revalidate();
        cardClima.repaint();
    }

    private String traduzirCondicao(String cond) {
        cond = cond.toLowerCase();

        if (cond.contains("rain")) return "Chuva";
        if (cond.contains("cloud")) return "Nublado";
        if (cond.contains("clear")) return "Céu limpo";
        if (cond.contains("overcast")) return "Encoberto";

        return cond;
    }

    private JLabel titulo(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 20));
        l.setForeground(Color.WHITE);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JLabel tempGrande(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 60));
        l.setForeground(Color.WHITE);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JLabel subtitulo(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        l.setForeground(new Color(180, 180, 180));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JPanel linha(String a, String b) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel l1 = new JLabel(a);
        JLabel l2 = new JLabel(b);

        l1.setForeground(new Color(160, 160, 160));
        l2.setForeground(Color.WHITE);

        p.add(l1, BorderLayout.WEST);
        p.add(l2, BorderLayout.EAST);

        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClimaApp().setVisible(true));
    }
}