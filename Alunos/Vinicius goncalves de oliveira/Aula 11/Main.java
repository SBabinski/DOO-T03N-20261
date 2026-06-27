import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private final JPasswordField campoKey = new JPasswordField(30);
    private final JTextField campoCidade = new JTextField("Cascavel-PR", 20);
    private final JButton btnBuscar = new JButton("Buscar");
    private final JLabel lblTemp = new JLabel("--\u00B0C", SwingConstants.CENTER);
    private final JLabel lblCidade = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel lblDetalhes = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel lblStatus = new JLabel(" ", SwingConstants.CENTER);

    public Main() {
        super("APP clima e tempo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        build();
    }

    private void build() {
        Color fundo = new Color(18, 18, 18);
        Color painel = fundo;
        Color texto = new Color(236, 244, 249);
        Color textoSuave = new Color(190, 190, 190);
        Color destaque = new Color(210, 210, 210);
        Color campo = Color.WHITE;

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(fundo);
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topo = new JPanel(new GridLayout(3, 2, 6, 6));
        topo.setBackground(fundo);
        JLabel lblKey = new JLabel("Insira a key:");
        lblKey.setForeground(texto);
        topo.add(lblKey);
        campoKey.setEchoChar('\u25CF');
        campoKey.setForeground(Color.BLACK);
        campoKey.setCaretColor(destaque);
        topo.add(campoKey);
        JLabel lblCidadeInput = new JLabel("Informe a cidade:");
        lblCidadeInput.setForeground(texto);
        topo.add(lblCidadeInput);
        campoCidade.setForeground(Color.BLACK);
        campoCidade.setCaretColor(destaque);
        topo.add(campoCidade);
        JLabel espaco = new JLabel();
        topo.add(espaco);
        btnBuscar.setBackground(destaque);
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        topo.add(btnBuscar);
        root.add(topo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(4, 1, 4, 4));
        centro.setBackground(painel);
        centro.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        lblTemp.setFont(new Font("Segoe UI", Font.BOLD, 50));
        lblTemp.setForeground(texto);
        lblCidade.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCidade.setForeground(texto);
        lblDetalhes.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDetalhes.setForeground(textoSuave);
        centro.add(lblTemp);
        centro.add(lblCidade);
        centro.add(lblDetalhes);
        root.add(centro, BorderLayout.CENTER);

        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblStatus.setForeground(textoSuave);
        root.add(lblStatus, BorderLayout.SOUTH);

        add(root);

        btnBuscar.addActionListener(e -> buscar());
        campoCidade.addActionListener(e -> buscar());
    }

    private void buscar() {
        String key = new String(campoKey.getPassword()).trim();
        String cidade = campoCidade.getText().trim();

        if (key.isEmpty()) {
            status("Informe a API Key.", Color.ORANGE);
            return;
        }
        if (cidade.isEmpty()) {
            status("Informe a cidade.", Color.ORANGE);
            return;
        }

        new SwingWorker<DadosCLima, Void>() {
            @Override
            protected DadosCLima doInBackground() throws Exception {
                return new ClimaHttp(key).buscar(cidade);
            }

            @Override
            protected void done() {
                btnBuscar.setEnabled(true);
                try {
                    mostrar(get());
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    status(cause.getMessage(), Color.RED);
                }
            }
        }.execute();
    }

    private void mostrar(DadosCLima d) {
        lblTemp.setText(String.format("%.0f\u00B0C", d.getTempAtual()));
        lblCidade.setText(d.getCidade());
        lblDetalhes.setText(String.format(
            "Max %.0f\u00B0 | Min %.0f\u00B0 | Hum %.0f%% | Chuva %.1fmm | Vento %.0fkm/h %s",
            d.getTempMax(), d.getTempMin(), d.getHumidade(),
            d.getPrecipitacao(), d.getVento(), d.getDirecaoTexto()));
    }

    private void status(String msg, Color cor) {
        lblStatus.setText(msg);
        lblStatus.setForeground(cor);
    }

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
