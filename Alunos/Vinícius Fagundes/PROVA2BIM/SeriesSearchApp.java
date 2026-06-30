
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;

/**
 * Aplicação Swing para pesquisar séries no TVmaze e administrar listas
 * pessoais.
 * A janela contém apenas regras de apresentação; modelos, API e persistência
 * estão
 * em classes próprias do pacote.
 */
@SuppressWarnings("serial")
public final class SeriesSearchApp extends JFrame {
    private static final Color BG_DARK = new Color(0x181825);
    private static final Color BG_PANEL = new Color(0x24243A);
    private static final Color BG_FIELD = new Color(0x303047);
    private static final Color ACCENT = new Color(0x7C5CFC);
    private static final Color TEXT_LIGHT = new Color(0xF2F2F7);
    private static final Color TEXT_MUTED = new Color(0xB1B1C3);
    private static final Color SUCCESS = new Color(0x45B97C);

    private static final int POSTER_LARGURA = 210;
    private static final int POSTER_ALTURA = 295;

    private static final int MINIATURA_LARGURA = 40;
    private static final int MINIATURA_ALTURA = 56;

    /** Cache de pôsteres grandes já baixados, compartilhado entre os painéis. */
    private static final Map<String, ImageIcon> CACHE_POSTERES = new ConcurrentHashMap<>();

    /** Cache das miniaturas exibidas nas listas. */
    private static final Map<String, ImageIcon> CACHE_MINIATURAS = new ConcurrentHashMap<>();

    /** URLs cuja miniatura já está sendo baixada, para não repetir o download. */
    private static final Set<String> MINIATURAS_EM_CARGA = ConcurrentHashMap.newKeySet();

    private final TvMazeService tvMazeService;
    private final RepositorioUsuarios repositorio;
    private final Usuario usuario;

    private JTextField campoBusca;
    private JButton botaoBuscar;
    private JLabel lblUsuario;
    private JLabel lblStatus;
    private JTabbedPane abas;
    private PainelSeries painelBusca;
    private PainelSeries painelFavoritos;
    private PainelSeries painelAssistidas;
    private PainelSeries painelDesejadas;
    private List<Serie> resultadosBusca = new ArrayList<>();

    public SeriesSearchApp(Usuario usuario, TvMazeService tvMazeService,
            RepositorioUsuarios repositorio) {
        super("SérieFinder — TVmaze");
        this.usuario = usuario;
        this.tvMazeService = tvMazeService;
        this.repositorio = repositorio;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(1080, 680);
        setMinimumSize(new Dimension(860, 560));
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evento) {
                fecharAplicacao();
            }
        });

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(BG_DARK);
        setContentPane(raiz);
        raiz.add(criarBarraSuperior(), BorderLayout.NORTH);
        raiz.add(criarConteudo(), BorderLayout.CENTER);
        raiz.add(criarRodape(), BorderLayout.SOUTH);
        atualizarInterfaceUsuario();
    }

    private JComponent criarBarraSuperior() {
        JPanel topo = new JPanel(new BorderLayout(16, 0));
        topo.setBackground(BG_PANEL);
        topo.setBorder(new EmptyBorder(12, 16, 12, 16));

        JPanel identidade = new JPanel();
        identidade.setOpaque(false);
        identidade.setLayout(new BoxLayout(identidade, BoxLayout.Y_AXIS));
        JLabel logo = new JLabel("🎬  SérieFinder");
        logo.setFont(new Font("SansSerif", Font.BOLD, 19));
        logo.setForeground(TEXT_LIGHT);
        lblUsuario = new JLabel();
        lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblUsuario.setForeground(TEXT_MUTED);
        identidade.add(logo);
        identidade.add(lblUsuario);

        campoBusca = new JTextField();
        campoBusca.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoBusca.setBackground(BG_FIELD);
        campoBusca.setForeground(TEXT_LIGHT);
        campoBusca.setCaretColor(TEXT_LIGHT);
        campoBusca.setBorder(new EmptyBorder(9, 12, 9, 12));
        campoBusca.setToolTipText("Digite o nome da série e pressione Enter");
        campoBusca.addActionListener(evento -> buscar());

        botaoBuscar = criarBotao("Buscar no TVmaze", ACCENT);
        botaoBuscar.addActionListener(evento -> buscar());

        JPanel pesquisa = new JPanel(new BorderLayout(8, 0));
        pesquisa.setOpaque(false);
        pesquisa.add(campoBusca, BorderLayout.CENTER);
        pesquisa.add(botaoBuscar, BorderLayout.EAST);

        JButton botaoApelido = criarBotao("Alterar nome", BG_FIELD);
        botaoApelido.addActionListener(evento -> alterarApelido());

        JButton botaoTrocar = criarBotao("Trocar usuário", BG_FIELD);
        botaoTrocar.addActionListener(evento -> trocarUsuario());

        JPanel acoesUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        acoesUsuario.setOpaque(false);
        acoesUsuario.add(botaoApelido);
        acoesUsuario.add(botaoTrocar);

        topo.add(identidade, BorderLayout.WEST);
        topo.add(pesquisa, BorderLayout.CENTER);
        topo.add(acoesUsuario, BorderLayout.EAST);
        return topo;
    }

    private JComponent criarConteudo() {
        painelBusca = new PainelSeries("Pesquise uma série pelo nome para começar.");
        painelFavoritos = new PainelSeries("Sua lista de favoritos está vazia.");
        painelAssistidas = new PainelSeries("Sua lista de séries já assistidas está vazia.");
        painelDesejadas = new PainelSeries("Sua lista de séries que deseja assistir está vazia.");

        abas = new JTabbedPane();
        abas.setFont(new Font("SansSerif", Font.BOLD, 13));
        abas.addTab("Busca", painelBusca);
        abas.addTab("Favoritos", painelFavoritos);
        abas.addTab("Já assistidas", painelAssistidas);
        abas.addTab("Quero assistir", painelDesejadas);
        atualizarListasUsuario();
        return abas;
    }

    private JComponent criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(BG_PANEL);
        rodape.setBorder(new EmptyBorder(5, 12, 5, 12));

        lblStatus = new JLabel("Pronto");
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblStatus.setForeground(TEXT_MUTED);

        JButton credito = new JButton("Dados fornecidos por TVmaze ↗");
        credito.setFont(new Font("SansSerif", Font.PLAIN, 11));
        credito.setForeground(TEXT_MUTED);
        credito.setOpaque(false);
        credito.setContentAreaFilled(false);
        credito.setBorderPainted(false);
        credito.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        credito.setToolTipText("Abrir https://www.tvmaze.com");
        credito.addActionListener(evento -> abrirTvMaze());

        rodape.add(lblStatus, BorderLayout.WEST);
        rodape.add(credito, BorderLayout.EAST);
        return rodape;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("SansSerif", Font.BOLD, 13));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(new EmptyBorder(9, 15, 9, 15));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void buscar() {
        String termo = campoBusca.getText().trim();
        if (termo.isEmpty()) {
            avisar("Digite o nome de uma série antes de buscar.");
            campoBusca.requestFocusInWindow();
            return;
        }

        definirPesquisaEmAndamento(true);
        lblStatus.setText("Consultando o TVmaze por \"" + termo + "\"...");
        new SwingWorker<List<Serie>, Void>() {
            @Override
            protected List<Serie> doInBackground() throws TvMazeException {
                return tvMazeService.buscarPorNome(termo);
            }

            @Override
            protected void done() {
                try {
                    resultadosBusca = get();
                    painelBusca.atualizarSeries(resultadosBusca);
                    atualizarTitulosAbas();
                    if (resultadosBusca.isEmpty()) {
                        lblStatus.setText("Nenhuma série encontrada para \"" + termo + "\".");
                    } else {
                        lblStatus.setText(resultadosBusca.size() + " resultado(s) encontrado(s) para \""
                                + termo + "\".");
                    }
                    abas.setSelectedIndex(0);
                } catch (InterruptedException erro) {
                    Thread.currentThread().interrupt();
                    mostrarErro("A pesquisa foi interrompida.", erro);
                } catch (ExecutionException erro) {
                    Throwable causa = erro.getCause();
                    String mensagem = causa instanceof TvMazeException
                            ? causa.getMessage()
                            : "Ocorreu um erro inesperado durante a pesquisa.";
                    mostrarErro(mensagem, causa);
                    lblStatus.setText("Não foi possível concluir a pesquisa.");
                } finally {
                    definirPesquisaEmAndamento(false);
                }
            }
        }.execute();
    }

    private void definirPesquisaEmAndamento(boolean pesquisando) {
        botaoBuscar.setEnabled(!pesquisando);
        campoBusca.setEnabled(!pesquisando);
        setCursor(Cursor.getPredefinedCursor(
                pesquisando ? Cursor.WAIT_CURSOR : Cursor.DEFAULT_CURSOR));
    }

    private void alternarSerie(TipoLista tipo, Serie serie) {
        if (serie == null) {
            return;
        }
        boolean estavaNaLista = usuario.contem(tipo, serie);
        if (estavaNaLista) {
            usuario.remover(tipo, serie);
        } else {
            usuario.adicionar(tipo, serie);
        }

        try {
            repositorio.salvar();
            atualizarListasUsuario();
            lblStatus.setText(serie.getNome() + (estavaNaLista ? " removida de " : " adicionada a ")
                    + tipo.getDescricao().toLowerCase() + ".");
        } catch (PersistenciaException erro) {
            if (estavaNaLista) {
                usuario.adicionar(tipo, serie);
            } else {
                usuario.remover(tipo, serie);
            }
            atualizarListasUsuario();
            mostrarErro("A alteração foi desfeita porque não foi possível salvar o arquivo JSON.", erro);
        }
    }

    private void atualizarListasUsuario() {
        painelFavoritos.atualizarSeries(usuario.getSeries(TipoLista.FAVORITOS));
        painelAssistidas.atualizarSeries(usuario.getSeries(TipoLista.ASSISTIDAS));
        painelDesejadas.atualizarSeries(usuario.getSeries(TipoLista.DESEJO_ASSISTIR));
        for (PainelSeries painel : todosOsPaineis()) {
            painel.atualizarBotoesDeLista();
        }
        if (abas != null) {
            atualizarTitulosAbas();
        }
    }

    private List<PainelSeries> todosOsPaineis() {
        return List.of(painelBusca, painelFavoritos, painelAssistidas, painelDesejadas);
    }

    private void atualizarTitulosAbas() {
        abas.setTitleAt(0, "Busca (" + resultadosBusca.size() + ")");
        abas.setTitleAt(1, "Favoritos (" + usuario.quantidade(TipoLista.FAVORITOS) + ")");
        abas.setTitleAt(2, "Já assistidas (" + usuario.quantidade(TipoLista.ASSISTIDAS) + ")");
        abas.setTitleAt(3, "Quero assistir (" + usuario.quantidade(TipoLista.DESEJO_ASSISTIR) + ")");
    }

    private void alterarApelido() {
        String novoApelido = JOptionPane.showInputDialog(this,
                "Como você gostaria de ser chamado?", usuario.getApelido());
        if (novoApelido == null) {
            return;
        }
        novoApelido = novoApelido.trim();
        if (novoApelido.isEmpty()) {
            avisar("O nome ou apelido não pode ficar vazio.");
            return;
        }
        if (novoApelido.length() > 60) {
            avisar("Use um nome ou apelido com no máximo 60 caracteres.");
            return;
        }

        try {
            repositorio.renomear(usuario, novoApelido);
            atualizarInterfaceUsuario();
            lblStatus.setText("Usuário alterado para " + usuario.getApelido() + ".");
        } catch (IllegalArgumentException erro) {
            avisar(erro.getMessage());
        } catch (PersistenciaException erro) {
            mostrarErro("Não foi possível salvar o novo apelido.", erro);
        }
    }

    private void trocarUsuario() {
        try {
            repositorio.salvar();
        } catch (PersistenciaException erro) {
            int escolha = JOptionPane.showConfirmDialog(this,
                    "Não foi possível salvar os dados do usuário atual. Trocar mesmo assim?",
                    "Falha ao salvar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (escolha != JOptionPane.YES_OPTION) {
                return;
            }
        }
        dispose();
        SwingUtilities.invokeLater(() -> iniciarComSelecaoDeUsuario(tvMazeService, repositorio));
    }

    private void atualizarInterfaceUsuario() {
        lblUsuario.setText("Olá, " + usuario.getApelido());
    }

    private void fecharAplicacao() {
        try {
            repositorio.salvar();
            dispose();
            System.exit(0);
        } catch (PersistenciaException erro) {
            int escolha = JOptionPane.showConfirmDialog(this,
                    "Não foi possível salvar seus dados. Deseja fechar mesmo assim?",
                    "Falha ao salvar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (escolha == JOptionPane.YES_OPTION) {
                dispose();
                System.exit(0);
            }
        }
    }

    private void abrirTvMaze() {
        try {
            if (!Desktop.isDesktopSupported()) {
                throw new IOException("O navegador do sistema não está disponível.");
            }
            Desktop.getDesktop().browse(new URI("https://www.tvmaze.com"));
        } catch (IOException | URISyntaxException erro) {
            mostrarErro("Não foi possível abrir o site do TVmaze.", erro);
        }
    }

    private void avisar(String mensagem) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErro(String mensagem, Throwable erro) {
        if (erro != null) {
            erro.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private final class PainelSeries extends JPanel {
        private final String mensagemVazia;
        private final DefaultListModel<Serie> modelo = new DefaultListModel<>();
        private final JList<Serie> lista = new JList<>(modelo);
        private final JComboBox<OrdenacaoSerie> cmbOrdenacao = new JComboBox<>(OrdenacaoSerie.values());
        private final JLabel lblVazio = new JLabel("", SwingConstants.CENTER);
        private final JLabel lblNome = criarValorDetalhe("Selecione uma série");
        private final JLabel lblIdioma = criarValorDetalhe("—");
        private final JLabel lblGeneros = criarValorDetalhe("—");
        private final JLabel lblNota = criarValorDetalhe("—");
        private final JLabel lblEstado = criarValorDetalhe("—");
        private final JLabel lblEstreia = criarValorDetalhe("—");
        private final JLabel lblTermino = criarValorDetalhe("—");
        private final JLabel lblEmissora = criarValorDetalhe("—");
        private final JTextArea txtResumo = new JTextArea();
        private final JLabel lblPoster = new JLabel();
        private final Map<TipoLista, JToggleButton> botoesLista = new EnumMap<>(TipoLista.class);
        private List<Serie> series = new ArrayList<>();
        private SwingWorker<ImageIcon, Void> carregadorPoster;

        private PainelSeries(String mensagemVazia) {
            super(new BorderLayout());
            this.mensagemVazia = mensagemVazia;
            setBackground(BG_DARK);
            add(criarBarraOrdenacao(), BorderLayout.NORTH);
            add(criarDivisao(), BorderLayout.CENTER);
            cmbOrdenacao.addActionListener(evento -> reordenar());
            lista.addListSelectionListener(evento -> {
                if (!evento.getValueIsAdjusting()) {
                    mostrarDetalhes(lista.getSelectedValue());
                }
            });
            atualizarSeries(List.of());
        }

        private JComponent criarBarraOrdenacao() {
            JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 7));
            barra.setBackground(BG_DARK);
            JLabel rotulo = new JLabel("Ordenar por:");
            rotulo.setForeground(TEXT_MUTED);
            cmbOrdenacao.setFont(new Font("SansSerif", Font.PLAIN, 12));
            barra.add(rotulo);
            barra.add(cmbOrdenacao);
            return barra;
        }

        private JComponent criarDivisao() {
            lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lista.setBackground(BG_PANEL);
            lista.setForeground(TEXT_LIGHT);
            lista.setFixedCellHeight(62);
            lista.setCellRenderer(new SerieCellRenderer());

            JScrollPane scrollLista = new JScrollPane(lista);
            scrollLista.setBorder(null);
            scrollLista.getViewport().setBackground(BG_PANEL);

            lblVazio.setForeground(TEXT_MUTED);
            lblVazio.setFont(new Font("SansSerif", Font.ITALIC, 13));
            JPanel lateral = new JPanel(new BorderLayout());
            lateral.setBackground(BG_PANEL);
            lateral.add(scrollLista, BorderLayout.CENTER);
            lateral.add(lblVazio, BorderLayout.SOUTH);

            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lateral, criarDetalhes());
            split.setDividerLocation(340);
            split.setResizeWeight(0.32);
            split.setDividerSize(3);
            split.setBorder(null);
            return split;
        }

        private JComponent criarDetalhes() {
            JPanel painel = new JPanel(new BorderLayout(0, 14));
            painel.setBackground(BG_DARK);
            painel.setBorder(new EmptyBorder(18, 22, 18, 22));

            JPanel informacoes = new JPanel(new GridBagLayout());
            informacoes.setOpaque(false);
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(3, 3, 3, 10);
            c.anchor = GridBagConstraints.NORTHWEST;
            c.fill = GridBagConstraints.HORIZONTAL;
            adicionarLinha(informacoes, c, 0, "Nome", lblNome);
            adicionarLinha(informacoes, c, 1, "Idioma", lblIdioma);
            adicionarLinha(informacoes, c, 2, "Gêneros", lblGeneros);
            adicionarLinha(informacoes, c, 3, "Nota geral", lblNota);
            adicionarLinha(informacoes, c, 4, "Estado", lblEstado);
            adicionarLinha(informacoes, c, 5, "Data de estreia", lblEstreia);
            adicionarLinha(informacoes, c, 6, "Data de término", lblTermino);
            adicionarLinha(informacoes, c, 7, "Emissora / streaming", lblEmissora);

            lblPoster.setHorizontalAlignment(SwingConstants.CENTER);
            lblPoster.setVerticalAlignment(SwingConstants.TOP);
            lblPoster.setPreferredSize(new Dimension(POSTER_LARGURA, POSTER_ALTURA));
            lblPoster.setMinimumSize(new Dimension(POSTER_LARGURA, POSTER_ALTURA));
            lblPoster.setBorder(BorderFactory.createLineBorder(BG_FIELD));
            lblPoster.setOpaque(true);
            lblPoster.setBackground(BG_PANEL);
            lblPoster.setForeground(TEXT_MUTED);
            lblPoster.setFont(new Font("SansSerif", Font.ITALIC, 12));

            JPanel topo = new JPanel(new BorderLayout(16, 0));
            topo.setOpaque(false);
            topo.add(lblPoster, BorderLayout.WEST);
            topo.add(informacoes, BorderLayout.CENTER);

            txtResumo.setLineWrap(true);
            txtResumo.setWrapStyleWord(true);
            txtResumo.setEditable(false);
            txtResumo.setBackground(BG_FIELD);
            txtResumo.setForeground(TEXT_LIGHT);
            txtResumo.setFont(new Font("SansSerif", Font.PLAIN, 13));
            txtResumo.setBorder(new EmptyBorder(10, 10, 10, 10));
            JScrollPane scrollResumo = new JScrollPane(txtResumo);
            scrollResumo.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(BG_FIELD), "Resumo", 0, 0,
                    new Font("SansSerif", Font.BOLD, 12), TEXT_MUTED));

            JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            acoes.setOpaque(false);
            for (TipoLista tipo : TipoLista.values()) {
                JToggleButton botao = new JToggleButton(tipo.getDescricao());
                botao.setFocusPainted(false);
                botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                botao.setToolTipText("Clique para adicionar ou remover desta lista");
                botao.addActionListener(evento -> alternarSerie(tipo, lista.getSelectedValue()));
                botoesLista.put(tipo, botao);
                acoes.add(botao);
            }

            painel.add(topo, BorderLayout.NORTH);
            painel.add(scrollResumo, BorderLayout.CENTER);
            painel.add(acoes, BorderLayout.SOUTH);
            return painel;
        }

        private JLabel criarValorDetalhe(String texto) {
            JLabel label = new JLabel(texto);
            label.setForeground(TEXT_LIGHT);
            label.setFont(new Font("SansSerif", Font.PLAIN, 13));
            return label;
        }

        private void adicionarLinha(JPanel painel, GridBagConstraints c, int linha,
                String titulo, JLabel valor) {
            JLabel rotulo = new JLabel(titulo + ":");
            rotulo.setForeground(TEXT_MUTED);
            rotulo.setFont(new Font("SansSerif", Font.BOLD, 12));
            c.gridx = 0;
            c.gridy = linha;
            c.weightx = 0;
            painel.add(rotulo, c);
            c.gridx = 1;
            c.weightx = 1;
            painel.add(valor, c);
        }

        private void atualizarSeries(List<Serie> novasSeries) {
            this.series = new ArrayList<>(novasSeries);
            reordenar();
        }

        private void reordenar() {
            Serie selecionada = lista.getSelectedValue();
            long idSelecionado = selecionada == null ? -1 : selecionada.getId();
            OrdenacaoSerie ordenacao = (OrdenacaoSerie) cmbOrdenacao.getSelectedItem();
            if (ordenacao == null) {
                ordenacao = OrdenacaoSerie.NOME;
            }
            List<Serie> ordenadas = new ArrayList<>(series);
            ordenadas.sort(ordenacao.getComparador());
            modelo.clear();
            for (Serie serie : ordenadas) {
                modelo.addElement(serie);
            }

            int indice = localizarIndice(idSelecionado);
            if (indice < 0 && !modelo.isEmpty()) {
                indice = 0;
            }
            if (indice >= 0) {
                lista.setSelectedIndex(indice);
            } else {
                mostrarDetalhes(null);
            }
            lblVazio.setText(modelo.isEmpty() ? mensagemVazia : " ");
        }

        private int localizarIndice(long id) {
            for (int i = 0; i < modelo.size(); i++) {
                if (modelo.get(i).getId() == id) {
                    return i;
                }
            }
            return -1;
        }

        private void mostrarDetalhes(Serie serie) {
            if (serie == null) {
                lblNome.setText("Selecione uma série");
                lblIdioma.setText("—");
                lblGeneros.setText("—");
                lblNota.setText("—");
                lblEstado.setText("—");
                lblEstreia.setText("—");
                lblTermino.setText("—");
                lblEmissora.setText("—");
                txtResumo.setText("");
                carregarPoster(null);
            } else {
                lblNome.setText(serie.getNome());
                lblIdioma.setText(serie.getIdioma());
                lblGeneros.setText(serie.getGenerosFormatados());
                lblNota.setText("⭐ " + serie.getNotaFormatada());
                lblEstado.setText(serie.getEstado().getDescricao());
                lblEstreia.setText(serie.getDataEstreiaFormatada());
                lblTermino.setText(serie.getDataTerminoFormatada());
                lblEmissora.setText(serie.getEmissora());
                txtResumo.setText(serie.getResumo());
                txtResumo.setCaretPosition(0);
                carregarPoster(serie);
            }
            atualizarBotoesDeLista();
        }

        /**
         * Exibe o pôster da série, baixando-o em segundo plano. Usa cache para
         * imagens já vistas e cancela qualquer carregamento anterior pendente,
         * evitando que um pôster antigo apareça na série recém-selecionada.
         */
        private void carregarPoster(Serie serie) {
            if (carregadorPoster != null && !carregadorPoster.isDone()) {
                carregadorPoster.cancel(true);
            }
            lblPoster.setIcon(null);

            String url = serie == null ? "" : serie.getUrlImagem();
            if (url == null || url.isBlank()) {
                lblPoster.setText("Sem imagem");
                return;
            }

            ImageIcon emCache = CACHE_POSTERES.get(url);
            if (emCache != null) {
                lblPoster.setText(null);
                lblPoster.setIcon(emCache);
                return;
            }

            lblPoster.setText("Carregando...");
            long idAlvo = serie.getId();
            carregadorPoster = new SwingWorker<>() {
                @Override
                protected ImageIcon doInBackground() throws IOException {
                    return baixarImagem(url, POSTER_LARGURA, POSTER_ALTURA, CACHE_POSTERES);
                }

                @Override
                protected void done() {
                    if (isCancelled()) {
                        return;
                    }
                    // Ignora o resultado se o usuário já trocou de série.
                    Serie atual = lista.getSelectedValue();
                    if (atual == null || atual.getId() != idAlvo) {
                        return;
                    }
                    try {
                        ImageIcon icone = get();
                        if (icone == null) {
                            lblPoster.setIcon(null);
                            lblPoster.setText("Sem imagem");
                        } else {
                            lblPoster.setText(null);
                            lblPoster.setIcon(icone);
                        }
                    } catch (InterruptedException erro) {
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException erro) {
                        lblPoster.setIcon(null);
                        lblPoster.setText("Imagem indisponível");
                    }
                }
            };
            carregadorPoster.execute();
        }

        private void atualizarBotoesDeLista() {
            Serie serie = lista.getSelectedValue();
            for (TipoLista tipo : TipoLista.values()) {
                JToggleButton botao = botoesLista.get(tipo);
                boolean selecionado = serie != null && usuario.contem(tipo, serie);
                botao.setEnabled(serie != null);
                botao.setSelected(selecionado);
                botao.setForeground(selecionado ? SUCCESS.darker() : Color.DARK_GRAY);
            }
        }
    }

    private static final class SerieCellRenderer extends JPanel implements ListCellRenderer<Serie> {
        private final JLabel titulo = new JLabel();
        private final JLabel meta = new JLabel();
        private final JLabel miniatura = new JLabel();

        private SerieCellRenderer() {
            setLayout(new BorderLayout(10, 0));
            setBorder(new EmptyBorder(3, 13, 3, 13));
            miniatura.setHorizontalAlignment(SwingConstants.CENTER);
            miniatura.setVerticalAlignment(SwingConstants.CENTER);
            miniatura.setPreferredSize(new Dimension(MINIATURA_LARGURA, MINIATURA_ALTURA));
            JPanel coluna = new JPanel();
            coluna.setOpaque(false);
            coluna.setLayout(new BoxLayout(coluna, BoxLayout.Y_AXIS));
            titulo.setFont(new Font("SansSerif", Font.BOLD, 14));
            meta.setFont(new Font("SansSerif", Font.PLAIN, 11));
            coluna.add(titulo);
            coluna.add(Box.createVerticalStrut(3));
            coluna.add(meta);
            add(miniatura, BorderLayout.WEST);
            add(coluna, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Serie> lista, Serie serie,
                int indice, boolean selecionado, boolean focado) {
            titulo.setText(serie.getNome());
            meta.setText(serie.getEstado().getDescricao() + "  •  ⭐ " + serie.getNotaFormatada());
            miniatura.setIcon(obterMiniatura(serie.getUrlImagem(), lista));
            if (selecionado) {
                setBackground(ACCENT);
                titulo.setForeground(Color.WHITE);
                meta.setForeground(new Color(0xE6E0FF));
            } else {
                setBackground(BG_PANEL);
                titulo.setForeground(TEXT_LIGHT);
                meta.setForeground(TEXT_MUTED);
            }
            setOpaque(true);
            return this;
        }
    }

    /**
     * Devolve a miniatura já em cache para a URL, ou {@code null} se ainda não
     * foi baixada. Quando ausente, dispara o download em segundo plano e repinta
     * a lista assim que a imagem chega.
     */
    private static ImageIcon obterMiniatura(String url, JList<? extends Serie> lista) {
        if (url == null || url.isBlank()) {
            return null;
        }
        ImageIcon emCache = CACHE_MINIATURAS.get(url);
        if (emCache != null) {
            return emCache;
        }
        if (!MINIATURAS_EM_CARGA.add(url)) {
            return null; // Já está sendo baixada por outra célula.
        }
        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws IOException {
                return baixarImagem(url, MINIATURA_LARGURA, MINIATURA_ALTURA, CACHE_MINIATURAS);
            }

            @Override
            protected void done() {
                MINIATURAS_EM_CARGA.remove(url);
                try {
                    if (get() != null) {
                        lista.repaint();
                    }
                } catch (InterruptedException erro) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException ignorado) {
                    // Sem miniatura para esta série; a lista segue com o espaço vazio.
                }
            }
        }.execute();
        return null;
    }

    /**
     * Baixa a imagem da URL, redimensiona para o tamanho informado e a guarda no
     * cache fornecido. Retorna {@code null} se a imagem não puder ser decodificada.
     */
    private static ImageIcon baixarImagem(String url, int largura, int altura,
            Map<String, ImageIcon> cache) throws IOException {
        ImageIcon emCache = cache.get(url);
        if (emCache != null) {
            return emCache;
        }
        BufferedImage original = ImageIO.read(new URL(url));
        if (original == null) {
            return null;
        }
        Image redimensionada = original.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        ImageIcon icone = new ImageIcon(redimensionada);
        cache.put(url, icone);
        return icone;
    }

    /**
     * Abre a tela de seleção de usuário e, conforme a escolha, abre o app ou
     * encerra. Garante que sempre haja ao menos um usuário disponível.
     */
    private static void iniciarComSelecaoDeUsuario(TvMazeService tvMazeService,
            RepositorioUsuarios repositorio) {
        // Janela-âncora invisível: garante que a tela de seleção e os diálogos
        // iniciais apareçam à frente, em vez de surgirem atrás de outras janelas.
        JFrame ancora = new JFrame("SérieFinder");
        ancora.setUndecorated(true);
        ancora.setSize(1, 1);
        ancora.setLocationRelativeTo(null);
        ancora.setAlwaysOnTop(true);
        ancora.setVisible(true);
        try {
            if (repositorio.estaVazio() && !criarPrimeiroUsuario(ancora, repositorio)) {
                System.exit(0);
                return;
            }

            TelaSelecaoUsuario selecao = new TelaSelecaoUsuario(ancora, repositorio);
            selecao.setVisible(true);
            Usuario escolhido = selecao.getUsuarioEscolhido();
            if (escolhido == null) {
                System.exit(0);
                return;
            }
            new SeriesSearchApp(escolhido, tvMazeService, repositorio).setVisible(true);
        } finally {
            ancora.dispose();
        }
    }

    /**
     * Pede o nome do primeiro usuário quando não há nenhum salvo. Retorna
     * {@code false} se o usuário cancelar a criação.
     */
    private static boolean criarPrimeiroUsuario(JFrame ancora, RepositorioUsuarios repositorio) {
        while (true) {
            String apelido = JOptionPane.showInputDialog(ancora,
                    "Bem-vindo ao SérieFinder! Qual é seu nome ou apelido?",
                    "Criar usuário local", JOptionPane.QUESTION_MESSAGE);
            if (apelido == null) {
                return false;
            }
            try {
                repositorio.criar(apelido);
                return true;
            } catch (IllegalArgumentException erro) {
                JOptionPane.showMessageDialog(ancora, erro.getMessage(),
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            } catch (PersistenciaException erro) {
                erro.printStackTrace();
                JOptionPane.showMessageDialog(ancora,
                        "O usuário foi criado, mas ainda não foi possível salvar o arquivo JSON.",
                        "Falha ao salvar", JOptionPane.WARNING_MESSAGE);
                return true;
            }
        }
    }

    private static void instalarTratamentoGlobalDeErros() {
        Thread.setDefaultUncaughtExceptionHandler((thread, erro) -> {
            erro.printStackTrace();
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Ocorreu um erro inesperado, mas o programa continuará aberto.\n" +
                            (erro.getMessage() == null ? erro.getClass().getSimpleName() : erro.getMessage()),
                    "Erro inesperado", JOptionPane.ERROR_MESSAGE));
        });
    }

    public static void main(String[] args) {
        instalarTratamentoGlobalDeErros();
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException erro) {
                System.err.println("Não foi possível aplicar o tema do sistema: " + erro.getMessage());
            }
            PersistenciaJson persistencia = new PersistenciaJson();
            RepositorioUsuarios repositorio = abrirRepositorio(persistencia);
            iniciarComSelecaoDeUsuario(new TvMazeService(), repositorio);
        });
    }

    /**
     * Abre o repositório a partir do arquivo padrão. Se o arquivo existente
     * estiver corrompido, avisa o usuário e abre um repositório vazio em outro
     * arquivo, preservando o original.
     */
    private static RepositorioUsuarios abrirRepositorio(PersistenciaJson persistencia) {
        try {
            return new RepositorioUsuarios(persistencia);
        } catch (PersistenciaException erro) {
            erro.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "O arquivo de dados existente não pôde ser lido. O programa será aberto "
                            + "vazio, sem apagar o arquivo anterior.",
                    "Falha ao carregar dados", JOptionPane.WARNING_MESSAGE);
            try {
                return new RepositorioUsuarios(new PersistenciaJson(Path.of(
                        System.getProperty("user.home"), ".seriefinder", "dados-novo.json")));
            } catch (PersistenciaException falha) {
                throw new IllegalStateException("Não foi possível iniciar o repositório.", falha);
            }
        }
    }
}
