import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private static final Color COR_FUNDO = new Color(18, 18, 18);
    private static final Color COR_PAINEL = new Color(25, 25, 25);
    private static final Color COR_CARD_AREA = new Color(12, 12, 12);
    private static final Color COR_TEXTO = new Color(245, 245, 245);
    private static final Color COR_TEXTO_SECUNDARIO = new Color(180, 180, 180);
    private static final Color COR_DESTAQUE = new Color(229, 9, 20);

    private SerieRepository serieRepository;
    private TvMazeService tvMazeService;

    private JTextField campoPesquisa;

    private JComboBox<String> comboVisualizacao;
    private JComboBox<String> comboOrdenacao;

    private JPanel painelCards;
    private JScrollPane scrollCards;

    private JTextArea areaDetalhes;
    private JLabel labelMensagem;
    private JLabel labelTitulo;
    private JLabel labelUsuario;

    private JButton botaoFavorito;
    private JButton botaoAssistida;
    private JButton botaoDesejo;
    private JButton botaoRemover;

    private List<Serie> seriesExibidas;
    private List<SerieCardPanel> cardsExibidos;
    private Serie serieSelecionada;
    private Timer timerPesquisa;

    public MainFrame(SerieRepository serieRepository, TvMazeService tvMazeService) {
        this.serieRepository = serieRepository;
        this.tvMazeService = tvMazeService;
        this.seriesExibidas = new ArrayList<>();
        this.cardsExibidos = new ArrayList<>();

        configurarJanela();
        criarComponentes();
        montarLayout();
        configurarEventos();

        carregarUsuarioNaTela();
        atualizarTabelaComListaAtual();
    }

    private void configurarJanela() {
        setTitle("Controle de Séries - TVmaze");
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(COR_FUNDO);
    }

    private void criarComponentes() {
        labelTitulo = new JLabel("Minhas Séries");
        labelTitulo.setForeground(COR_TEXTO);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 30));

        campoPesquisa = new JTextField(28);

        estilizarCampo(campoPesquisa);

        comboVisualizacao = new JComboBox<>(new String[]{
                "Pesquisa",
                "Favoritos",
                "Já assistidas",
                "Deseja assistir"
        });

        comboOrdenacao = new JComboBox<>(new String[]{
                "Sem ordenação",
                "Nome",
                "Nota",
                "Estado",
                "Data de estreia"
        });

        estilizarCombo(comboVisualizacao);
        estilizarCombo(comboOrdenacao);

        painelCards = new JPanel(new GridLayout(0, 5, 18, 18));
        painelCards.setBackground(COR_CARD_AREA);
        painelCards.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        scrollCards = new JScrollPane(painelCards);
        scrollCards.getViewport().setBackground(COR_CARD_AREA);
        scrollCards.setBorder(BorderFactory.createEmptyBorder());

        areaDetalhes = new JTextArea();
        areaDetalhes.setEditable(false);
        areaDetalhes.setLineWrap(true);
        areaDetalhes.setWrapStyleWord(true);
        areaDetalhes.setBackground(new Color(22, 22, 22));
        areaDetalhes.setForeground(COR_TEXTO);
        areaDetalhes.setCaretColor(COR_TEXTO);
        areaDetalhes.setFont(new Font("Arial", Font.PLAIN, 14));
        areaDetalhes.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        labelMensagem = new JLabel("Digite o nome de uma série para pesquisar.");
        labelMensagem.setForeground(COR_TEXTO_SECUNDARIO);
        labelMensagem.setFont(new Font("Arial", Font.PLAIN, 13));

        labelUsuario = criarLabel("");
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 13));

        botaoFavorito = criarBotao("Favoritar");
        botaoAssistida = criarBotao("Já assisti");
        botaoDesejo = criarBotao("Quero assistir");
        botaoRemover = criarBotaoSecundario("Remover da lista");

        timerPesquisa = new Timer(600, evento -> pesquisarSeriesAutomaticamente());
        timerPesquisa.setRepeats(false);
    }

    private void montarLayout() {
        setLayout(new BorderLayout());

        add(criarPainelTopo(), BorderLayout.NORTH);
        add(criarPainelCentro(), BorderLayout.CENTER);
        add(criarPainelDetalhes(), BorderLayout.EAST);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelTopo() {
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(COR_FUNDO);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(18, 22, 10, 22));

        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(COR_FUNDO);
        painelTitulo.add(labelTitulo);

        JPanel painelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelUsuario.setBackground(COR_FUNDO);
        painelUsuario.add(labelUsuario);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(COR_FUNDO);
        painelSuperior.add(painelTitulo, BorderLayout.WEST);
        painelSuperior.add(painelUsuario, BorderLayout.EAST);

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelPesquisa.setBackground(COR_FUNDO);
        painelPesquisa.add(criarLabel("Pesquisar:"));
        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(criarLabel("Visualizar:"));
        painelPesquisa.add(comboVisualizacao);
        painelPesquisa.add(criarLabel("Ordenar:"));
        painelPesquisa.add(comboOrdenacao);

        JPanel painelMensagem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelMensagem.setBackground(COR_FUNDO);
        painelMensagem.add(labelMensagem);

        JPanel painelInferior = new JPanel(new GridLayout(2, 1));
        painelInferior.setBackground(COR_FUNDO);
        painelInferior.add(painelPesquisa);
        painelInferior.add(painelMensagem);

        painelTopo.add(painelSuperior, BorderLayout.NORTH);
        painelTopo.add(painelInferior, BorderLayout.SOUTH);

        return painelTopo;
    }

    private JPanel criarPainelCentro() {
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(COR_CARD_AREA);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(0, 22, 10, 10));
        painelCentro.add(scrollCards, BorderLayout.CENTER);

        return painelCentro;
    }

    private JPanel criarPainelDetalhes() {
        JPanel painelDetalhes = new JPanel(new BorderLayout());
        painelDetalhes.setBackground(COR_PAINEL);
        painelDetalhes.setPreferredSize(new Dimension(330, 650));
        painelDetalhes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(45, 45, 45)),
                BorderFactory.createEmptyBorder(15, 15, 15, 22)
        ));

        JLabel labelDetalhes = new JLabel("Detalhes");
        labelDetalhes.setForeground(COR_TEXTO);
        labelDetalhes.setFont(new Font("Arial", Font.BOLD, 22));
        labelDetalhes.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        painelDetalhes.add(labelDetalhes, BorderLayout.NORTH);
        painelDetalhes.add(new JScrollPane(areaDetalhes), BorderLayout.CENTER);

        return painelDetalhes;
    }

    private JPanel criarPainelBotoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(COR_FUNDO);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(8, 22, 16, 22));

        painelBotoes.add(botaoFavorito);
        painelBotoes.add(botaoAssistida);
        painelBotoes.add(botaoDesejo);
        painelBotoes.add(botaoRemover);

        return painelBotoes;
    }

    private void configurarEventos() {
        botaoFavorito.addActionListener(evento -> adicionarSerieFavorita());
        botaoAssistida.addActionListener(evento -> adicionarSerieAssistida());
        botaoDesejo.addActionListener(evento -> adicionarSerieDesejo());
        botaoRemover.addActionListener(evento -> removerSerieDaListaAtual());

        comboVisualizacao.addActionListener(evento -> atualizarTabelaComListaAtual());
        comboOrdenacao.addActionListener(evento -> aplicarOrdenacaoNaTela());

        campoPesquisa.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent evento) {
                reiniciarTimerPesquisa();
            }

            @Override
            public void removeUpdate(DocumentEvent evento) {
                reiniciarTimerPesquisa();
            }

            @Override
            public void changedUpdate(DocumentEvent evento) {
                reiniciarTimerPesquisa();
            }
        });
    }

    private void carregarUsuarioNaTela() {
        labelUsuario.setText("Bem-vindo, " + serieRepository.getUsuario().getNomeOuApelido());
    }

    private void reiniciarTimerPesquisa() {
        comboVisualizacao.setSelectedItem("Pesquisa");
        timerPesquisa.restart();
    }

    private void pesquisarSeriesAutomaticamente() {
        String textoPesquisa = campoPesquisa.getText();

        if (textoPesquisa == null || textoPesquisa.trim().isEmpty()) {
            seriesExibidas = new ArrayList<>();
            serieSelecionada = null;
            preencherCards(seriesExibidas);
            areaDetalhes.setText("");
            labelMensagem.setText("Digite o nome de uma série para pesquisar.");
            return;
        }

        labelMensagem.setText("Pesquisando séries...");

        new Thread(() -> {
            List<Serie> resultado = tvMazeService.buscarSeriesPorNome(textoPesquisa);

            SwingUtilities.invokeLater(() -> {
                if (resultado.isEmpty()) {
                    if (tvMazeService.teveErroNaUltimaBusca()) {
                        seriesExibidas = new ArrayList<>();
                        serieSelecionada = null;
                        preencherCards(seriesExibidas);

                        String mensagemErro = tvMazeService.getUltimaMensagemErro();
                        labelMensagem.setText(mensagemErro);
                        areaDetalhes.setText(mensagemErro);
                        return;
                    }

                    List<Serie> sugestoes = DadosPreCarregados.criarSugestoes();

                    seriesExibidas = sugestoes;
                    serieSelecionada = null;
                    preencherCards(seriesExibidas);

                    labelMensagem.setText(
                            "Nenhum resultado encontrado para \"" + textoPesquisa + "\". Mas você poderia gostar dessas sugestões."
                    );

                    areaDetalhes.setText("Nenhum resultado encontrado.\n\nMas você poderia gostar das séries sugeridas no catálogo.");
                    return;
                }

                seriesExibidas = resultado;
                serieSelecionada = null;
                aplicarOrdenacaoNaTela();
                labelMensagem.setText("Resultados encontrados: " + resultado.size());
            });
        }).start();
    }

    private void atualizarTabelaComListaAtual() {
        String visualizacao = (String) comboVisualizacao.getSelectedItem();

        if (visualizacao == null) {
            return;
        }

        switch (visualizacao) {
            case "Favoritos":
                seriesExibidas = serieRepository.getFavoritos();
                labelMensagem.setText("Visualizando favoritos.");
                break;

            case "Já assistidas":
                seriesExibidas = serieRepository.getSeriesAssistidas();
                labelMensagem.setText("Visualizando séries já assistidas.");
                break;

            case "Deseja assistir":
                seriesExibidas = serieRepository.getSeriesDesejo();
                labelMensagem.setText("Visualizando séries que deseja assistir.");
                break;

            case "Pesquisa":
            default:
                pesquisarSeriesAutomaticamente();
                return;
        }

        serieSelecionada = null;
        areaDetalhes.setText("");
        aplicarOrdenacaoNaTela();
    }

    private void aplicarOrdenacaoNaTela() {
        String tipoOrdenacao = (String) comboOrdenacao.getSelectedItem();

        if (seriesExibidas == null) {
            seriesExibidas = new ArrayList<>();
        }

        List<Serie> seriesOrdenadas;

        if ("Sem ordenação".equals(tipoOrdenacao)) {
            seriesOrdenadas = new ArrayList<>(seriesExibidas);
        } else {
            seriesOrdenadas = SerieSorter.ordenarPorTipo(seriesExibidas, tipoOrdenacao);
        }

        preencherCards(seriesOrdenadas);
    }

    private void preencherCards(List<Serie> series) {
        painelCards.removeAll();
        cardsExibidos.clear();

        if (series == null || series.isEmpty()) {
            JLabel labelVazio = new JLabel("Nenhuma série para exibir.");
            labelVazio.setForeground(COR_TEXTO_SECUNDARIO);
            labelVazio.setFont(new Font("Arial", Font.PLAIN, 18));
            painelCards.add(labelVazio);
        } else {
            for (Serie serie : series) {
                SerieCardPanel card = new SerieCardPanel(serie, this::selecionarSerie);
                cardsExibidos.add(card);
                painelCards.add(card);
            }
        }

        painelCards.revalidate();
        painelCards.repaint();
    }

    private void selecionarSerie(Serie serie) {
        this.serieSelecionada = serie;

        for (SerieCardPanel card : cardsExibidos) {
            card.setSelecionado(card.getSerie().equals(serie));
        }

        areaDetalhes.setText(montarTextoDetalhes(serie));
    }

    private void adicionarSerieFavorita() {
        Serie serie = getSerieSelecionada();

        if (serie == null) {
            mostrarMensagemSelecaoObrigatoria();
            return;
        }

        boolean adicionou = serieRepository.adicionarFavorito(serie);

        if (adicionou) {
            JOptionPane.showMessageDialog(this, "Série adicionada aos favoritos.");
        } else {
            JOptionPane.showMessageDialog(this, "Essa série já está nos favoritos.");
        }
    }

    private void adicionarSerieAssistida() {
        Serie serie = getSerieSelecionada();

        if (serie == null) {
            mostrarMensagemSelecaoObrigatoria();
            return;
        }

        boolean adicionou = serieRepository.adicionarSerieAssistida(serie);

        if (adicionou) {
            JOptionPane.showMessageDialog(this, "Série adicionada às já assistidas.");
        } else {
            JOptionPane.showMessageDialog(this, "Essa série já está nas assistidas.");
        }
    }

    private void adicionarSerieDesejo() {
        Serie serie = getSerieSelecionada();

        if (serie == null) {
            mostrarMensagemSelecaoObrigatoria();
            return;
        }

        boolean adicionou = serieRepository.adicionarSerieDesejo(serie);

        if (adicionou) {
            JOptionPane.showMessageDialog(this, "Série adicionada à lista de desejo.");
        } else {
            JOptionPane.showMessageDialog(this, "Essa série já está na lista de desejo.");
        }
    }

    private void removerSerieDaListaAtual() {
        Serie serie = getSerieSelecionada();

        if (serie == null) {
            mostrarMensagemSelecaoObrigatoria();
            return;
        }

        String visualizacao = (String) comboVisualizacao.getSelectedItem();
        boolean removeu = false;

        if ("Favoritos".equals(visualizacao)) {
            removeu = serieRepository.removerFavorito(serie);
        } else if ("Já assistidas".equals(visualizacao)) {
            removeu = serieRepository.removerSerieAssistida(serie);
        } else if ("Deseja assistir".equals(visualizacao)) {
            removeu = serieRepository.removerSerieDesejo(serie);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Para remover, primeiro selecione uma lista: Favoritos, Já assistidas ou Deseja assistir."
            );
            return;
        }

        if (removeu) {
            JOptionPane.showMessageDialog(this, "Série removida com sucesso.");
            atualizarTabelaComListaAtual();
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível remover a série.");
        }
    }

    private Serie getSerieSelecionada() {
        return serieSelecionada;
    }

    private String montarTextoDetalhes(Serie serie) {
        StringBuilder detalhes = new StringBuilder();

        detalhes.append("Nome: ").append(serie.getNome()).append("\n\n");
        detalhes.append("Idioma: ").append(serie.getIdioma()).append("\n\n");
        detalhes.append("Gêneros: ").append(serie.getGenerosFormatados()).append("\n\n");
        detalhes.append("Nota geral: ").append(serie.getNotaFormatada()).append("\n\n");
        detalhes.append("Estado: ").append(serie.getEstado()).append("\n\n");
        detalhes.append("Data de estreia: ").append(serie.getDataEstreia()).append("\n\n");
        detalhes.append("Data de término: ").append(serie.getDataTermino()).append("\n\n");
        detalhes.append("Emissora: ").append(serie.getEmissora()).append("\n");

        return detalhes.toString();
    }

    private void mostrarMensagemSelecaoObrigatoria() {
        JOptionPane.showMessageDialog(
                this,
                "Selecione uma série primeiro.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(COR_TEXTO_SECUNDARIO);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        return label;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(COR_DESTAQUE);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        return botao;
    }

    private JButton criarBotaoSecundario(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(55, 55, 55));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        return botao;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBackground(new Color(35, 35, 35));
        campo.setForeground(COR_TEXTO);
        campo.setCaretColor(COR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
    }

    private void estilizarCombo(JComboBox<String> combo) {
        combo.setBackground(new Color(35, 35, 35));
        combo.setForeground(COR_TEXTO);
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
    }
}
