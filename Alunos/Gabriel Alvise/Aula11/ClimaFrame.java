import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ClimaFrame extends JFrame {

    private JTextField txtCidade;
    private JButton btnBuscar;

    private JLabel lblCidade;
    private JLabel lblTempAtual;
    private JLabel lblTempMax;
    private JLabel lblTempMin;
    private JLabel lblUmidade;
    private JLabel lblCondicao;
    private JLabel lblPrecipitacao;
    private JLabel lblVento;

    private final ClimaService climaService = new ClimaService();

    public ClimaFrame() {
        setTitle("Consulta de Clima");
        setSize(720, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(680, 500));

        inicializarTela();

        setVisible(true);
    }

    private void inicializarTela() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(new Color(235, 240, 248));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));
        painelTopo.setOpaque(false);

        JLabel titulo = new JLabel("Previsão do Tempo");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(new Color(30, 45, 70));

        JLabel subtitulo = new JLabel("Busque informações atuais do clima");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(90, 100, 115));

        JPanel textosTopo = new JPanel(new GridLayout(2, 1));
        textosTopo.setOpaque(false);
        textosTopo.add(titulo);
        textosTopo.add(subtitulo);

        painelTopo.add(textosTopo, BorderLayout.CENTER);

        JPanel painelBusca = new JPanel(new BorderLayout(12, 12));
        painelBusca.setOpaque(false);

        txtCidade = new JTextField();
        txtCidade.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCidade.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 225)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.setBackground(new Color(45, 105, 220));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));

        painelBusca.add(txtCidade, BorderLayout.CENTER);
        painelBusca.add(btnBuscar, BorderLayout.EAST);

        JPanel painelConteudo = new JPanel(new BorderLayout(15, 15));
        painelConteudo.setOpaque(false);

        JPanel cardPrincipal = criarCardPrincipal();
        JPanel cardsDetalhes = criarCardsDetalhes();

        painelConteudo.add(cardPrincipal, BorderLayout.NORTH);
        painelConteudo.add(cardsDetalhes, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> consultarClima());
        txtCidade.addActionListener(e -> consultarClima());

        painelPrincipal.add(painelTopo);
        painelPrincipal.add(Box.createVerticalStrut(20));
        painelPrincipal.add(painelBusca);
        painelPrincipal.add(Box.createVerticalStrut(20));
        painelPrincipal.add(painelConteudo);

        add(painelPrincipal);
    }

    private JPanel criarCardPrincipal() {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                BorderFactory.createEmptyBorder(22, 24, 22, 24)
        ));

        lblCidade = new JLabel("Cidade: -");
        lblCidade.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCidade.setForeground(new Color(45, 55, 72));

        lblTempAtual = new JLabel("-- °C");
        lblTempAtual.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTempAtual.setForeground(new Color(30, 90, 200));

        lblCondicao = new JLabel("Condição do tempo: -");
        lblCondicao.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCondicao.setForeground(new Color(80, 90, 105));

        JPanel infoPrincipal = new JPanel(new GridLayout(3, 1));
        infoPrincipal.setOpaque(false);
        infoPrincipal.add(lblCidade);
        infoPrincipal.add(lblTempAtual);
        infoPrincipal.add(lblCondicao);

        card.add(infoPrincipal, BorderLayout.CENTER);

        return card;
    }

    private JPanel criarCardsDetalhes() {
        JPanel painel = new JPanel(new GridLayout(2, 3, 12, 12));
        painel.setOpaque(false);

        lblTempMax = criarCardInfo(painel, "Máxima do dia", "- °C");
        lblTempMin = criarCardInfo(painel, "Mínima do dia", "- °C");
        lblUmidade = criarCardInfo(painel, "Umidade do ar", "- %");
        lblPrecipitacao = criarCardInfo(painel, "Precipitação", "- mm");
        lblVento = criarCardInfo(painel, "Vento", "- km/h");

        return painel;
    }

    private JLabel criarCardInfo(JPanel painel, String titulo, String valorInicial) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitulo.setForeground(new Color(100, 110, 125));

        JLabel lblValor = new JLabel(valorInicial);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblValor.setForeground(new Color(45, 55, 72));

        card.add(lblTitulo);
        card.add(lblValor);

        painel.add(card);

        return lblValor;
    }

    private void consultarClima() {
        try {
            String cidade = txtCidade.getText().trim();

            if (cidade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe uma cidade.");
                return;
            }

            btnBuscar.setEnabled(false);
            btnBuscar.setText("Buscando...");

            Map<String, String> dados = climaService.buscarClima(cidade);

            lblCidade.setText("Cidade: " + dados.get("cidade"));
            lblTempAtual.setText(dados.get("tempAtual") + " °C");
            lblTempMax.setText(dados.get("tempMax") + " °C");
            lblTempMin.setText(dados.get("tempMin") + " °C");
            lblUmidade.setText(dados.get("umidade") + "%");
            lblCondicao.setText("Condição do tempo: " + traduzirCondicao(dados.get("condicao")));
            lblPrecipitacao.setText(dados.get("precipitacao") + " mm");

            double direcao = Double.parseDouble(dados.get("ventoDirecao"));

            lblVento.setText(
                    dados.get("ventoVelocidade")
                            + " km/h - "
                            + converterDirecaoVento(direcao)
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            btnBuscar.setEnabled(true);
            btnBuscar.setText("Buscar");
        }
    }

    private String traduzirCondicao(String condicao) {
        if (condicao == null) return "-";

         switch (condicao) {
            case "Clear":
                return "Céu limpo";
            case "Partially cloudy":
                 return "Parcialmente nublado";
            case "Overcast":
                 return "Nublado";
            case "Rain":
                 return "Chuva";
            case "Snow":
                 return "Neve";
            default:
                 return condicao;
        }
    }

    private String converterDirecaoVento(double graus) {
        if (graus >= 337.5 || graus < 22.5) return "Norte";
        if (graus < 67.5) return "Nordeste";
        if (graus < 112.5) return "Leste";
        if (graus < 157.5) return "Sudeste";
        if (graus < 202.5) return "Sul";
        if (graus < 247.5) return "Sudoeste";
        if (graus < 292.5) return "Oeste";
        return "Noroeste";
    }
}