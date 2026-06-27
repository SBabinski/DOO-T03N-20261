import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

/**
 * Janela principal do SeriesFlix. Reune a busca na API, a navegacao entre as
 * tres listas, a ordenacao, a exibicao em cartoes e a alternancia entre o modo
 * janela e o modo tela cheia, preservando todas as funcionalidades.
 */
public class MainFrame extends JFrame {

    private enum Visao { BUSCA, FAVORITOS, ASSISTIDAS, DESEJA_ASSISTIR }

    private final Usuario usuario;
    private final TvMazeService api;
    private final PersistenceService persistencia;

    private final JTextField campoBusca = new JTextField(22);
    private final JButton botaoBuscar = UI.botaoVermelho("Pesquisar");
    private final JComboBox<CriterioOrdenacao> comboOrdenacao = new JComboBox<>(CriterioOrdenacao.values());
    private final JPanel painelCartoes = new JPanel(new WrapLayout(FlowLayout.LEFT, 16, 16));
    private final JLabel rotuloStatus = new JLabel(" ");
    private final JLabel rotuloUsuario = new JLabel();
    private final JLabel rotuloTitulo = new JLabel("Busca de series");

    private Visao visaoAtual = Visao.BUSCA;
    private List<Serie> resultadosBusca = new ArrayList<>();
    private boolean telaCheia = false;

    public MainFrame(Usuario usuario, TvMazeService api, PersistenceService persistencia) {
        super("SeriesFlix - Acompanhe suas series");
        this.usuario = usuario;
        this.api = api;
        this.persistencia = persistencia;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setSize(1120, 720);
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.FUNDO);
        raiz.add(criarTopo(), BorderLayout.NORTH);
        raiz.add(criarCentro(), BorderLayout.CENTER);
        raiz.add(criarRodape(), BorderLayout.SOUTH);
        setContentPane(raiz);

        configurarAtalhos();
        configurarFechamento();

        atualizarRotuloUsuario();
        mostrarBusca();
    }

    // ---- Construcao da interface ---------------------------------------

    private JPanel criarTopo() {
        JPanel topo = new JPanel();
        topo.setLayout(new BoxLayoutVertical(topo));
        topo.setBackground(Tema.FUNDO_BARRA);
        topo.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        // Linha 1: logo, navegacao, usuario e tela cheia
        JPanel linha1 = new JPanel(new BorderLayout());
        linha1.setOpaque(false);

        JLabel logo = new JLabel("VibeSeries");
        logo.setFont(Tema.LOGO);
        logo.setForeground(Tema.VERMELHO);
        linha1.add(logo, BorderLayout.WEST);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        nav.setOpaque(false);
        nav.add(navBotao("Buscar", () -> mostrarBusca()));
        nav.add(navBotao("Favoritos", () -> mostrarLista(Visao.FAVORITOS)));
        nav.add(navBotao("Assistidas", () -> mostrarLista(Visao.ASSISTIDAS)));
        nav.add(navBotao("Quero assistir", () -> mostrarLista(Visao.DESEJA_ASSISTIR)));
        linha1.add(nav, BorderLayout.CENTER);

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        direita.setOpaque(false);
        rotuloUsuario.setForeground(Tema.TEXTO);
        rotuloUsuario.setFont(Tema.NORMAL);
        JButton trocar = UI.botaoNeutro("Trocar apelido");
        trocar.addActionListener(e -> trocarApelido());
        JButton fs = UI.botaoNeutro("Tela cheia (F11)");
        fs.addActionListener(e -> alternarTelaCheia());
        direita.add(rotuloUsuario);
        direita.add(trocar);
        direita.add(fs);
        linha1.add(direita, BorderLayout.EAST);

        // Linha 2: busca e ordenacao
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        linha2.setOpaque(false);

        rotuloTitulo.setForeground(Tema.TEXTO);
        rotuloTitulo.setFont(Tema.TITULO);

        campoBusca.setFont(Tema.NORMAL);
        campoBusca.setPreferredSize(new Dimension(260, 34));
        campoBusca.addActionListener(e -> buscar());
        botaoBuscar.addActionListener(e -> buscar());

        JLabel lblOrdenar = new JLabel("Ordenar por:");
        lblOrdenar.setForeground(Tema.TEXTO_FRACO);
        lblOrdenar.setFont(Tema.PEQUENA);
        comboOrdenacao.setFont(Tema.PEQUENA);
        comboOrdenacao.addActionListener(e -> renderizarVisaoAtual());

        linha2.add(rotuloTitulo);
        linha2.add(Box.createHorizontalStrut(20));
        linha2.add(campoBusca);
        linha2.add(botaoBuscar);
        linha2.add(Box.createHorizontalStrut(20));
        linha2.add(lblOrdenar);
        linha2.add(comboOrdenacao);

        topo.add(linha1);
        topo.add(linha2);
        return topo;
    }

    private JButton navBotao(String texto, Runnable acao) {
        JButton b = UI.botaoNeutro(texto);
        b.addActionListener(e -> acao.run());
        return b;
    }

    private JScrollPane criarCentro() {
        painelCartoes.setBackground(Tema.FUNDO);
        painelCartoes.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JScrollPane sp = new JScrollPane(painelCartoes);
        sp.setBorder(null);
        sp.getViewport().setBackground(Tema.FUNDO);
        sp.getVerticalScrollBar().setUnitIncrement(18);
        return sp;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 6));
        rodape.setBackground(Tema.FUNDO_BARRA);
        rotuloStatus.setForeground(Tema.TEXTO_FRACO);
        rotuloStatus.setFont(Tema.PEQUENA);
        rodape.add(rotuloStatus);
        return rodape;
    }

    private void configurarAtalhos() {
        JComponent rp = getRootPane();
        rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "telaCheia");
        rp.getActionMap().put("telaCheia", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { alternarTelaCheia(); }
        });
        rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "sairTelaCheia");
        rp.getActionMap().put("sairTelaCheia", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { if (telaCheia) alternarTelaCheia(); }
        });
    }

    private void configurarFechamento() {
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                salvar();
                dispose();
                System.exit(0);
            }
        });
    }

    // ---- Acoes ---------------------------------------------------------

    private void buscar() {
        final String termo = campoBusca.getText();
        if (termo == null || termo.trim().isEmpty()) {
            UI.aviso(this, "Digite o nome de uma serie para pesquisar.");
            return;
        }
        botaoBuscar.setEnabled(false);
        rotuloStatus.setText("Buscando \"" + termo.trim() + "\"...");
        visaoAtual = Visao.BUSCA;
        rotuloTitulo.setText("Resultados da busca");

        new SwingWorker<List<Serie>, Void>() {
            @Override protected List<Serie> doInBackground() throws AppException {
                return api.buscarPorNome(termo);
            }
            @Override protected void done() {
                botaoBuscar.setEnabled(true);
                try {
                    resultadosBusca = get();
                    if (resultadosBusca.isEmpty()) {
                        rotuloStatus.setText("Nenhuma serie encontrada para \"" + termo.trim() + "\".");
                    } else {
                        rotuloStatus.setText(resultadosBusca.size() + " serie(s) encontrada(s).");
                    }
                    renderizar(resultadosBusca);
                } catch (java.util.concurrent.ExecutionException ex) {
                    Throwable causa = ex.getCause();
                    String msg = (causa instanceof AppException)
                            ? causa.getMessage()
                            : "Ocorreu um erro inesperado durante a busca.";
                    rotuloStatus.setText("Falha na busca.");
                    UI.erro(MainFrame.this, msg);
                } catch (Exception ex) {
                    rotuloStatus.setText("Falha na busca.");
                    UI.erro(MainFrame.this, "Ocorreu um erro inesperado durante a busca.");
                }
            }
        }.execute();
    }

    private void mostrarBusca() {
        visaoAtual = Visao.BUSCA;
        rotuloTitulo.setText("Busca de series");
        renderizar(resultadosBusca);
        rotuloStatus.setText(resultadosBusca.isEmpty()
                ? "Digite o nome de uma serie e clique em Pesquisar."
                : resultadosBusca.size() + " resultado(s) da ultima busca.");
    }

    private void mostrarLista(Visao visao) {
        visaoAtual = visao;
        TipoLista tipo = tipoDaVisao(visao);
        rotuloTitulo.setText(tipo.getRotulo());
        List<Serie> lista = usuario.getLista(tipo);
        renderizar(lista);
        rotuloStatus.setText(lista.isEmpty()
                ? "Esta lista esta vazia. Adicione series pela busca ou pelos detalhes."
                : lista.size() + " serie(s) em \"" + tipo.getRotulo() + "\".");
    }

    private void renderizarVisaoAtual() {
        if (visaoAtual == Visao.BUSCA) {
            renderizar(resultadosBusca);
        } else {
            renderizar(usuario.getLista(tipoDaVisao(visaoAtual)));
        }
    }

    /** Ordena e exibe a lista de series como cartoes. */
    private void renderizar(List<Serie> series) {
        List<Serie> ordenada = new ArrayList<>(series);
        CriterioOrdenacao criterio = (CriterioOrdenacao) comboOrdenacao.getSelectedItem();
        if (criterio != null) {
            ordenada.sort(criterio.comparator());
        }

        painelCartoes.removeAll();
        if (ordenada.isEmpty()) {
            JLabel vazio = new JLabel("Nada para exibir aqui.");
            vazio.setForeground(Tema.TEXTO_FRACO);
            vazio.setFont(Tema.TITULO);
            painelCartoes.add(vazio);
        } else {
            for (Serie s : ordenada) {
                painelCartoes.add(new SerieCard(s, this::abrirDetalhes));
            }
        }
        painelCartoes.revalidate();
        painelCartoes.repaint();
    }

    private void abrirDetalhes(Serie serie) {
        DetalhesDialog d = new DetalhesDialog(this, usuario, serie, () -> {
            salvar();
            // Ao remover/adicionar enquanto vendo uma lista, atualiza a grade.
            if (visaoAtual != Visao.BUSCA) renderizarVisaoAtual();
        });
        d.setVisible(true);
    }

    private void trocarApelido() {
        String atual = usuario.getApelido();
        String novo = javax.swing.JOptionPane.showInputDialog(this,
                "Como devemos chamar voce?", atual);
        if (novo == null) return; // cancelado
        if (novo.trim().isEmpty()) {
            UI.aviso(this, "O apelido nao pode ficar em branco.");
            return;
        }
        usuario.setApelido(novo);
        atualizarRotuloUsuario();
        salvar();
    }

    private void atualizarRotuloUsuario() {
        String nome = usuario.getApelido();
        rotuloUsuario.setText("Ola, " + (nome.isBlank() ? "visitante" : nome) + "  ");
    }

    /** Alterna entre janela e tela cheia recriando o peer nativo sem perder estado. */
    private void alternarTelaCheia() {
        telaCheia = !telaCheia;
        dispose();
        setUndecorated(telaCheia);
        if (telaCheia) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setExtendedState(JFrame.NORMAL);
            setSize(1120, 720);
            setLocationRelativeTo(null);
        }
        setVisible(true);
    }

    private void salvar() {
        try {
            persistencia.salvar(usuario);
        } catch (AppException e) {
            UI.erro(this, e.getMessage());
        }
    }

    // ---- Auxiliares ----------------------------------------------------

    private TipoLista tipoDaVisao(Visao v) {
        switch (v) {
            case FAVORITOS:       return TipoLista.FAVORITOS;
            case ASSISTIDAS:      return TipoLista.ASSISTIDAS;
            case DESEJA_ASSISTIR: return TipoLista.DESEJA_ASSISTIR;
            default:              return TipoLista.FAVORITOS;
        }
    }

    /** Pequeno BoxLayout vertical com fundo herdado, para empilhar as duas linhas do topo. */
    private static class BoxLayoutVertical extends javax.swing.BoxLayout {
        BoxLayoutVertical(java.awt.Container alvo) {
            super(alvo, javax.swing.BoxLayout.Y_AXIS);
        }
    }

    // Mantem referencia de cor usada em validacoes visuais futuras.
    @SuppressWarnings("unused")
    private static final Color ACENTO = Tema.VERMELHO;
}
