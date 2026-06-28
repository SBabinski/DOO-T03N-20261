import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class TelaClima extends JFrame {

    private JTextField txtCidade;

    private JLabel lblTemp;
    private JLabel lblMax;
    private JLabel lblMin;
    private JLabel lblUmidade;
    private JLabel lblCondicao;
    private JLabel lblPrecipitacao;
    private JLabel lblVelocidade;
    private JLabel lblDirecao;
    private JLabel lblStatus;

    public TelaClima() {

        setTitle("Portal do Clima");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color fundo = new Color(240, 244, 248);
        Color azul = new Color(33, 150, 243);
        Color azulEscuro = new Color(25, 118, 210);

        getContentPane().setBackground(fundo);
        setLayout(new BorderLayout());

        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(azul);

        JLabel titulo = new JLabel("PORTAL DO CLIMA");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        painelTitulo.add(titulo);

        add(painelTitulo, BorderLayout.NORTH);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(15, 15));
        painelPrincipal.setBackground(fundo);
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelBusca = new JPanel();
        painelBusca.setBackground(Color.WHITE);
        painelBusca.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel lblCidade = new JLabel("Cidade:");
        lblCidade.setFont(new Font("Segoe UI", Font.BOLD, 15));

        txtCidade = new JTextField(25);
        txtCidade.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JButton btnBuscar = new JButton("Buscar");

        btnBuscar.setBackground(azulEscuro);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        painelBusca.add(lblCidade);
        painelBusca.add(txtCidade);
        painelBusca.add(btnBuscar);

        painelPrincipal.add(painelBusca, BorderLayout.NORTH);

        JPanel painelDados = new JPanel(new GridLayout(4, 2, 15, 15));
        painelDados.setBackground(fundo);

        lblTemp = criarCard("Temperatura Atual");
        lblMax = criarCard("Temperatura Máxima");
        lblMin = criarCard("Temperatura Mínima");
        lblUmidade = criarCard("Umidade");
        lblCondicao = criarCard("Condição");
        lblPrecipitacao = criarCard("Precipitação");
        lblVelocidade = criarCard("Velocidade do Vento");
        lblDirecao = criarCard("Direção do Vento");

        painelDados.add(criarPainelCard(lblTemp));
        painelDados.add(criarPainelCard(lblMax));
        painelDados.add(criarPainelCard(lblMin));
        painelDados.add(criarPainelCard(lblUmidade));
        painelDados.add(criarPainelCard(lblCondicao));
        painelDados.add(criarPainelCard(lblPrecipitacao));
        painelDados.add(criarPainelCard(lblVelocidade));
        painelDados.add(criarPainelCard(lblDirecao));

        painelPrincipal.add(painelDados, BorderLayout.CENTER);

        JPanel painelRodape = new JPanel();
        painelRodape.setBackground(fundo);

        lblStatus = new JLabel("Digite uma cidade e clique em Buscar");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        painelRodape.add(lblStatus);

        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

        add(painelPrincipal);

        btnBuscar.addActionListener(e -> buscarClima());

        getRootPane().setDefaultButton(btnBuscar);

        setVisible(true);
    }

    private JLabel criarCard(String texto) {

        JLabel label = new JLabel(texto + ": -");
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));

        return label;
    }

    private JPanel criarPainelCard(JLabel label) {

        JPanel painel = new JPanel(new BorderLayout());

        painel.setBackground(Color.WHITE);

        painel.setBorder(new CompoundBorder(
                new LineBorder(new Color(210, 210, 210)),
                new EmptyBorder(20, 20, 20, 20)));

        painel.add(label);

        return painel;
    }

    private void buscarClima() {

        String cidade = txtCidade.getText().trim();

        if (cidade.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe uma cidade.");

            return;
        }

        try {

            lblStatus.setText("Consultando informações climáticas...");

            ClimaService service = new ClimaService();

            Clima clima = service.buscarClima(cidade);

            lblTemp.setText("Temperatura Atual: "
                    + clima.getTemperatura() + " °C");

            lblMax.setText("Temperatura Máxima: "
                    + clima.getTemperaturaMaxima() + " °C");

            lblMin.setText("Temperatura Mínima: "
                    + clima.getTemperaturaMinima() + " °C");

            lblUmidade.setText("Umidade: "
                    + clima.getUmidade() + "%");

            lblCondicao.setText("Condição: "
                    + clima.getCondicao());

            lblPrecipitacao.setText("Precipitação: "
                    + clima.getPrecipitacao() + " mm");

            lblVelocidade.setText("Velocidade do vento: "
                    + clima.getVelocidadeVento() + " km/h");

            lblDirecao.setText("Direção do vento: "
                    + clima.getDirecaoVento() + "°");

            lblStatus.setText("Consulta realizada com sucesso.");

        } catch (Exception e) {

            lblStatus.setText("Erro na consulta.");

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao consultar a cidade.\n\n" + e.getMessage());
        }
    }
}