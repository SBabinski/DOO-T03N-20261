import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Locale;

public class JanelaClima extends JFrame {
    private static final String VALOR_INICIAL = "-";

    private JTextField campoCidade;
    private JButton botaoPesquisar;

    private JLabel valorCidade;
    private JLabel valorTemperatura;
    private JLabel valorMaxima;
    private JLabel valorMinima;
    private JLabel valorUmidade;
    private JLabel valorCondicao;
    private JLabel valorChuva;
    private JLabel valorVento;
    private JLabel valorDirecaoVento;

    public JanelaClima() {
        setTitle("Consulta de Clima");
        setSize(620, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(14, 18, 18, 18));

        montarComponentes();

        setVisible(true);
    }

    private void montarComponentes() {
        add(montarPainelPesquisa(), BorderLayout.NORTH);
        add(montarPainelResultado(), BorderLayout.CENTER);
    }

    private JPanel montarPainelPesquisa() {
        JPanel painelTopo = new JPanel(new BorderLayout(8, 8));
        JLabel titulo = new JLabel("Consulta de clima por cidade");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel linhaPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        campoCidade = new JTextField(26);
        botaoPesquisar = new JButton("Consultar");

        linhaPesquisa.add(new JLabel("Cidade:"));
        linhaPesquisa.add(campoCidade);
        linhaPesquisa.add(botaoPesquisar);

        painelTopo.add(titulo, BorderLayout.NORTH);
        painelTopo.add(linhaPesquisa, BorderLayout.CENTER);
        return painelTopo;
    }

    private JPanel montarPainelResultado() {
        JPanel painelResultado = new JPanel(new GridLayout(9, 2, 8, 8));
        painelResultado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Resumo do clima"),
                new EmptyBorder(10, 12, 10, 12)
        ));

        valorCidade = novoValor();
        valorTemperatura = novoValor();
        valorMinima = novoValor();
        valorMaxima = novoValor();
        valorUmidade = novoValor();
        valorCondicao = novoValor();
        valorChuva = novoValor();
        valorVento = novoValor();
        valorDirecaoVento = novoValor();

        adicionarLinha(painelResultado, "Cidade:", valorCidade);
        adicionarLinha(painelResultado, "Temperatura atual:", valorTemperatura);
        adicionarLinha(painelResultado, "Minima do dia:", valorMinima);
        adicionarLinha(painelResultado, "Maxima do dia:", valorMaxima);
        adicionarLinha(painelResultado, "Umidade:", valorUmidade);
        adicionarLinha(painelResultado, "Condicao:", valorCondicao);
        adicionarLinha(painelResultado, "Chuva:", valorChuva);
        adicionarLinha(painelResultado, "Velocidade do vento:", valorVento);
        adicionarLinha(painelResultado, "Direcao do vento:", valorDirecaoVento);

        return painelResultado;
    }

    private JLabel novoValor() {
        return new JLabel(VALOR_INICIAL);
    }

    private void adicionarLinha(JPanel painel, String texto, JLabel valor) {
        painel.add(new JLabel(texto));
        painel.add(valor);
    }

    public String obterCidadeDigitada() {
        return campoCidade.getText().trim().replaceAll("\\s+", " ");
    }

    public void aoPesquisar(ActionListener listener) {
        botaoPesquisar.addActionListener(listener);
        campoCidade.addActionListener(listener);
    }

    public void prepararNovaBusca() {
        botaoPesquisar.setEnabled(false);
        botaoPesquisar.setText("Buscando...");
    }

    public void finalizarBusca() {
        botaoPesquisar.setEnabled(true);
        botaoPesquisar.setText("Consultar");
    }

    public void limparResultado() {
        valorCidade.setText(VALOR_INICIAL);
        valorTemperatura.setText(VALOR_INICIAL);
        valorMinima.setText(VALOR_INICIAL);
        valorMaxima.setText(VALOR_INICIAL);
        valorUmidade.setText(VALOR_INICIAL);
        valorCondicao.setText(VALOR_INICIAL);
        valorChuva.setText(VALOR_INICIAL);
        valorVento.setText(VALOR_INICIAL);
        valorDirecaoVento.setText(VALOR_INICIAL);
    }

    public void mostrarResultado(ClimaAtual clima) {
        valorCidade.setText(clima.getNomeCidade());
        valorTemperatura.setText(formatarDecimal(clima.getTemperaturaAtual()) + " C");
        valorMinima.setText(formatarDecimal(clima.getTemperaturaMinima()) + " C");
        valorMaxima.setText(formatarDecimal(clima.getTemperaturaMaxima()) + " C");
        valorUmidade.setText(formatarDecimal(clima.getUmidade()) + " %");
        valorCondicao.setText(clima.getDescricaoCondicao());
        valorChuva.setText(formatarDecimal(clima.getVolumeChuva()) + " mm");
        valorVento.setText(formatarDecimal(clima.getVelocidadeVento()) + " km/h");
        valorDirecaoVento.setText(clima.getDirecaoVentoGraus() + " graus");
    }

    public void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private String formatarDecimal(double valor) {
        return String.format(new Locale("pt", "BR"), "%.1f", valor);
    }
}
