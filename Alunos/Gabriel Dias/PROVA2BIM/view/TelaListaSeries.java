package view;

import model.Serie;
import model.Usuario;
import service.ServicoSerie;
import util.UtilitarioExcecao;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Painel base abstrato para as telas que exibem listas de séries do usuário.
 * Usa layout de cartões com capa em vez de JTable.
 * Subclasses implementam título, lista-fonte e lógica de remoção.
 */
public abstract class TelaListaSeries extends JPanel {

    protected final Usuario usuario;
    protected final ServicoSerie servicoSerie;

    private JPanel painelCartoes;
    private JComboBox<String> comboCriterio;
    private JLabel rotuloContador;

    /**
     * Constrói o painel base de lista.
     */
    public TelaListaSeries(Usuario usuario, ServicoSerie servicoSerie) {
        this.usuario = usuario;
        this.servicoSerie = servicoSerie;
        construirInterface();
    }

    protected abstract String obterTituloTela();
    protected abstract List<Serie> obterListaSeries();
    protected abstract void removerSerie(Serie serie) throws exception.ExcecaoUsuario;

    /**
     * Monta os componentes visuais do painel.
     */
    private void construirInterface() {
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(242, 243, 250));

        // ── Cabeçalho ───────────────────────────────────────────────────────
        JPanel painelCabecalho = new JPanel(new BorderLayout(10, 0));
        painelCabecalho.setOpaque(false);

        JLabel rotuloTitulo = new JLabel(obterTituloTela());
        rotuloTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        rotuloTitulo.setForeground(new Color(25, 25, 112));

        rotuloContador = new JLabel("0 séries");
        rotuloContador.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rotuloContador.setForeground(new Color(120, 120, 160));

        JPanel painelTituloContador = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelTituloContador.setOpaque(false);
        painelTituloContador.add(rotuloTitulo);
        painelTituloContador.add(rotuloContador);

        // Controles direita: ordenação + remover
        JPanel painelControles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        painelControles.setOpaque(false);

        JLabel rotuloOrdenar = new JLabel("Ordenar por:");
        rotuloOrdenar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        comboCriterio = new JComboBox<>(new String[]{"Nome", "Nota", "Estado", "Data de Estreia"});
        comboCriterio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        comboCriterio.addActionListener(e -> atualizarTabela());

        JButton botaoRemover = new JButton("🗑  Remover");
        botaoRemover.setBackground(new Color(190, 35, 35));
        botaoRemover.setForeground(Color.WHITE);
        botaoRemover.setFocusPainted(false);
        botaoRemover.setBorderPainted(false);
        botaoRemover.setFont(new Font("SansSerif", Font.BOLD, 13));
        botaoRemover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoRemover.addActionListener(e -> removerSerieSelecionada());

        painelControles.add(rotuloOrdenar);
        painelControles.add(comboCriterio);
        painelControles.add(botaoRemover);

        painelCabecalho.add(painelTituloContador, BorderLayout.WEST);
        painelCabecalho.add(painelControles, BorderLayout.EAST);

        // ── Área de cartões ──────────────────────────────────────────────────
        painelCartoes = new JPanel();
        painelCartoes.setLayout(new BoxLayout(painelCartoes, BoxLayout.Y_AXIS));
        painelCartoes.setBackground(new Color(242, 243, 250));
        painelCartoes.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        JScrollPane scroll = new JScrollPane(painelCartoes);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getViewport().setBackground(new Color(242, 243, 250));

        add(painelCabecalho, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Atualiza os cartões com a lista atual e ordenação selecionada.
     */
    public void atualizarTabela() {
        painelCartoes.removeAll();

        String criterio = (String) comboCriterio.getSelectedItem();
        List<Serie> listaSeries = servicoSerie.ordenarSeries(obterListaSeries(), criterio);

        rotuloContador.setText(listaSeries.size() + (listaSeries.size() == 1 ? " série" : " séries"));

        if (listaSeries.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma série nesta lista ainda.", SwingConstants.CENTER);
            vazio.setFont(new Font("SansSerif", Font.ITALIC, 14));
            vazio.setForeground(new Color(160, 160, 180));
            vazio.setAlignmentX(CENTER_ALIGNMENT);
            painelCartoes.add(Box.createVerticalStrut(50));
            painelCartoes.add(vazio);
        } else {
            for (Serie serie : listaSeries) {
                PainelCartaoSerie cartao = new PainelCartaoSerie(serie);
                cartao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
                cartao.setAlignmentX(LEFT_ALIGNMENT);
                // Clique simples seleciona, duplo clique não é necessário aqui;
                // mantemos duplo clique por consistência com a tela de pesquisa
                cartao.definirAcaoClique(() -> {
                    // Remove via botão dedicado — duplo clique não remove diretamente
                });
                painelCartoes.add(cartao);
                painelCartoes.add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }

        painelCartoes.revalidate();
        painelCartoes.repaint();
    }

    /**
     * Remove a série do cartão clicado mais recentemente (pergunta antes).
     * Como não há seleção nativa nos cartões, mostramos um JOptionPane
     * com lista para o usuário escolher qual remover.
     */
    private void removerSerieSelecionada() {
        List<Serie> lista = obterListaSeries();
        if (lista.isEmpty()) {
            UtilitarioExcecao.exibirAviso(this, "A lista está vazia.");
            return;
        }

        // Monta array de nomes para o diálogo de escolha
        String[] nomes = lista.stream()
                .map(Serie::obterNome)
                .toArray(String[]::new);

        String escolhida = (String) JOptionPane.showInputDialog(
                this,
                "Selecione a série a remover:",
                "Remover série",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomes,
                nomes[0]
        );

        if (escolhida == null) return; // cancelou

        Serie serieParaRemover = lista.stream()
                .filter(s -> s.obterNome().equals(escolhida))
                .findFirst()
                .orElse(null);

        if (serieParaRemover == null) return;

        int conf = JOptionPane.showConfirmDialog(
                this,
                "Remover \"" + serieParaRemover.obterNome() + "\" desta lista?",
                "Confirmar remoção",
                JOptionPane.YES_NO_OPTION
        );
        if (conf == JOptionPane.YES_OPTION) {
            try {
                removerSerie(serieParaRemover);
                atualizarTabela();
                UtilitarioExcecao.exibirInformacao(this, "\"" + serieParaRemover.obterNome() + "\" removida com sucesso.");
            } catch (exception.ExcecaoUsuario e) {
                UtilitarioExcecao.exibirExcecao(this, e);
            }
        }
    }

    // Mantido para compatibilidade com subclasses que usam formatarData
    protected String formatarData(String data) {
        if (data == null || data.isEmpty()) return "—";
        if (data.length() == 10 && data.charAt(4) == '-') {
            return data.substring(8) + "/" + data.substring(5, 7) + "/" + data.substring(0, 4);
        }
        return data;
    }

    protected String emptyParaDash(String texto) {
        return (texto == null || texto.isEmpty()) ? "—" : texto;
    }
}
