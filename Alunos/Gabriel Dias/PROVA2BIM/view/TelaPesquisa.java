package view;

import exception.ExcecaoApi;
import exception.ExcecaoRede;
import exception.ExcecaoUsuario;
import model.Serie;
import model.Usuario;
import service.ServicoSerie;
import util.UtilitarioExcecao;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tela de pesquisa de séries. Exibe os resultados como cartões com capa,
 * nome, nota, gêneros e metadados. Duplo clique abre os detalhes.
 */
public class TelaPesquisa extends JPanel {

    private final Usuario usuario;
    private final ServicoSerie servicoSerie;
    private final TelaPrincipal telaPrincipal;

    private JTextField campoPesquisa;
    private JPanel painelResultados;
    private JLabel rotuloStatus;
    private List<Serie> seriesEncontradas = new ArrayList<>();

    /**
     * Constrói a tela de pesquisa.
     */
    public TelaPesquisa(Usuario usuario, ServicoSerie servicoSerie, TelaPrincipal telaPrincipal) {
        this.usuario = usuario;
        this.servicoSerie = servicoSerie;
        this.telaPrincipal = telaPrincipal;
        construirInterface();
    }

    /**
     * Monta os componentes visuais da tela.
     */
    private void construirInterface() {
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(242, 243, 250));

        // ── Cabeçalho de busca ──────────────────────────────────────────────
        JPanel painelTopo = new JPanel(new BorderLayout(0, 8));
        painelTopo.setOpaque(false);

        JLabel rotuloTitulo = new JLabel("Pesquisar Séries");
        rotuloTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        rotuloTitulo.setForeground(new Color(25, 25, 112));

        JPanel painelCampo = new JPanel(new BorderLayout(8, 0));
        painelCampo.setOpaque(false);

        campoPesquisa = new JTextField();
        campoPesquisa.setFont(new Font("SansSerif", Font.PLAIN, 15));
        campoPesquisa.setPreferredSize(new Dimension(0, 38));
        campoPesquisa.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 185, 220), 1),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));

        JButton botaoPesquisar = new JButton("  🔍  Pesquisar  ");
        botaoPesquisar.setBackground(new Color(25, 25, 112));
        botaoPesquisar.setForeground(Color.WHITE);
        botaoPesquisar.setFocusPainted(false);
        botaoPesquisar.setBorderPainted(false);
        botaoPesquisar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoPesquisar.setPreferredSize(new Dimension(160, 38));
        botaoPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botaoPesquisar.addActionListener(e -> executarPesquisa());
        campoPesquisa.addActionListener(e -> executarPesquisa());

        painelCampo.add(campoPesquisa, BorderLayout.CENTER);
        painelCampo.add(botaoPesquisar, BorderLayout.EAST);

        rotuloStatus = new JLabel("Digite o nome de uma série e pressione Enter ou clique em Pesquisar.");
        rotuloStatus.setFont(new Font("SansSerif", Font.ITALIC, 11));
        rotuloStatus.setForeground(new Color(130, 130, 160));

        painelTopo.add(rotuloTitulo, BorderLayout.NORTH);
        painelTopo.add(painelCampo, BorderLayout.CENTER);
        painelTopo.add(rotuloStatus, BorderLayout.SOUTH);

        // ── Área de resultados (cartões empilhados verticalmente) ───────────
        painelResultados = new JPanel();
        painelResultados.setLayout(new BoxLayout(painelResultados, BoxLayout.Y_AXIS));
        painelResultados.setBackground(new Color(242, 243, 250));
        painelResultados.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        JScrollPane scroll = new JScrollPane(painelResultados);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(new Color(242, 243, 250));
        scroll.getViewport().setBackground(new Color(242, 243, 250));

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Executa a pesquisa e renderiza os cartões de resultado.
     */
    private void executarPesquisa() {
        String termo = campoPesquisa.getText();
        painelResultados.removeAll();
        seriesEncontradas.clear();

        rotuloStatus.setText("Pesquisando...");
        rotuloStatus.setForeground(new Color(25, 25, 112));

        // Pesquisa em thread separada para não travar a UI
        SwingWorker<List<Serie>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Serie> doInBackground() throws Exception {
                return servicoSerie.pesquisarSeries(termo);
            }

            @Override
            protected void done() {
                try {
                    List<Serie> resultados = get();
                    seriesEncontradas = resultados;
                    renderizarResultados(resultados);
                    rotuloStatus.setText(resultados.size() + " série(s) encontrada(s) para \"" + termo + "\".");
                    rotuloStatus.setForeground(new Color(34, 110, 34));
                } catch (java.util.concurrent.ExecutionException ex) {
                    Throwable causa = ex.getCause();
                    if (causa instanceof ExcecaoRede) {
                        rotuloStatus.setText("⚠  Sem conexão com a internet.");
                        rotuloStatus.setForeground(new Color(180, 80, 0));
                        UtilitarioExcecao.exibirAviso(TelaPesquisa.this,
                                "Não foi possível conectar à API do TVMaze.\n\n"
                                + causa.getMessage());
                    } else {
                        rotuloStatus.setText("Erro na pesquisa.");
                        rotuloStatus.setForeground(Color.RED);
                        UtilitarioExcecao.exibirErro(TelaPesquisa.this, causa.getMessage());
                    }
                } catch (Exception ex) {
                    rotuloStatus.setText("Erro inesperado.");
                    rotuloStatus.setForeground(Color.RED);
                }
            }
        };
        worker.execute();
    }

    /**
     * Renderiza a lista de séries como cartões no painel de resultados.
     */
    private void renderizarResultados(List<Serie> series) {
        painelResultados.removeAll();

        if (series.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma série encontrada.", SwingConstants.CENTER);
            vazio.setFont(new Font("SansSerif", Font.ITALIC, 14));
            vazio.setForeground(Color.GRAY);
            vazio.setAlignmentX(CENTER_ALIGNMENT);
            painelResultados.add(Box.createVerticalStrut(40));
            painelResultados.add(vazio);
        } else {
            for (Serie serie : series) {
                PainelCartaoSerie cartao = new PainelCartaoSerie(serie);
                cartao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
                cartao.setAlignmentX(LEFT_ALIGNMENT);
                cartao.definirAcaoClique(() -> abrirDetalhesSerie(serie));
                painelResultados.add(cartao);
                painelResultados.add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }

        painelResultados.revalidate();
        painelResultados.repaint();
    }

    /**
     * Abre a tela de detalhes para a série informada.
     */
    private void abrirDetalhesSerie(Serie serie) {
        TelaDetalhesSerie tela = new TelaDetalhesSerie(serie, usuario, servicoSerie, telaPrincipal);
        tela.setVisible(true);
    }
}
