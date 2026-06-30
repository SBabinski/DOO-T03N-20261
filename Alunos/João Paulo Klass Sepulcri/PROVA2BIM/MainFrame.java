import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Tela principal do sistema usando Java Swing
public class MainFrame extends JFrame {

    private SerieController controller;
    private TvMazeService tvMazeService;
    private JsonStorageService storageService;

    private JTextField campoBusca;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JComboBox<String> comboLista;
    private JComboBox<String> comboOrdenacao;
    private JLabel labelStatus;

    private List<Serie> resultadoBusca;
    private String listaAtual;

    public MainFrame(SerieController controller, TvMazeService tvMazeService, JsonStorageService storageService) {
        this.controller = controller;
        this.tvMazeService = tvMazeService;
        this.storageService = storageService;

        this.resultadoBusca = new ArrayList<>();
        this.listaAtual = "Favoritos";

        configurarJanela();
        criarTela();

        mostrarLista("Favoritos");
    }

    private void configurarJanela() {
        setTitle("Sistema de Séries - TVMaze");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void criarTela() {
        setLayout(new BorderLayout());

        criarPainelSuperior();
        criarTabela();
        criarPainelInferior();
    }

    private void criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout());

        JLabel labelUsuario = new JLabel("Usuário: " + controller.getUsuario().getNome());
        labelUsuario.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel painelBusca = new JPanel(new FlowLayout());

        campoBusca = new JTextField(25);
        JButton botaoBuscar = new JButton("Buscar");

        painelBusca.add(new JLabel("Nome da série:"));
        painelBusca.add(campoBusca);
        painelBusca.add(botaoBuscar);

        labelStatus = new JLabel("Exibindo: Favoritos");
        labelStatus.setBorder(BorderFactory.createEmptyBorder(5, 8, 8, 8));

        painel.add(labelUsuario, BorderLayout.NORTH);
        painel.add(painelBusca, BorderLayout.CENTER);
        painel.add(labelStatus, BorderLayout.SOUTH);

        add(painel, BorderLayout.NORTH);

        botaoBuscar.addActionListener(e -> buscarSeries());
        campoBusca.addActionListener(e -> buscarSeries());
    }

    private void criarTabela() {
        String[] colunas = {
                "Nome", "Idioma", "Gêneros", "Nota", "Estado",
                "Estreia", "Término", "Emissora"
        };

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void criarPainelInferior() {
        JPanel painelPrincipal = new JPanel(new GridLayout(2, 1));

        JPanel linha1 = new JPanel(new FlowLayout());
        JPanel linha2 = new JPanel(new FlowLayout());

        comboLista = new JComboBox<>(new String[]{
                "Busca", "Favoritos", "Assistidas", "Desejo assistir"
        });

        comboOrdenacao = new JComboBox<>(new String[]{
                "Nome", "Nota", "Estado", "Data de estreia"
        });

        JButton botaoOrdenar = new JButton("Ordenar");
        JButton botaoDetalhes = new JButton("Ver detalhes");
        JButton botaoFavorito = new JButton("Adicionar favoritos");
        JButton botaoAssistida = new JButton("Adicionar assistidas");
        JButton botaoDesejo = new JButton("Adicionar desejo");
        JButton botaoRemover = new JButton("Remover");

        linha1.add(new JLabel("Exibir lista:"));
        linha1.add(comboLista);
        linha1.add(new JLabel("Ordenar por:"));
        linha1.add(comboOrdenacao);
        linha1.add(botaoOrdenar);

        linha2.add(botaoDetalhes);
        linha2.add(botaoFavorito);
        linha2.add(botaoAssistida);
        linha2.add(botaoDesejo);
        linha2.add(botaoRemover);

        painelPrincipal.add(linha1);
        painelPrincipal.add(linha2);

        add(painelPrincipal, BorderLayout.SOUTH);

        comboLista.addActionListener(e -> mostrarLista((String) comboLista.getSelectedItem()));

        // Ordena automaticamente ao escolher Nome, Nota, Estado ou Data
        comboOrdenacao.addActionListener(e -> ordenarListaAtual(false));

        // Também tem botão, caso o professor queira clicar
        botaoOrdenar.addActionListener(e -> ordenarListaAtual(true));

        botaoDetalhes.addActionListener(e -> mostrarDetalhes());
        botaoFavorito.addActionListener(e -> adicionarFavorito());
        botaoAssistida.addActionListener(e -> adicionarAssistida());
        botaoDesejo.addActionListener(e -> adicionarDesejo());
        botaoRemover.addActionListener(e -> removerDaLista());
    }

    private void buscarSeries() {
        try {
            String nome = campoBusca.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o nome de uma série.");
                return;
            }

            resultadoBusca = tvMazeService.buscarSeries(nome);

            if (resultadoBusca == null) {
                resultadoBusca = new ArrayList<>();
            }

            comboLista.setSelectedItem("Busca");
            mostrarLista("Busca");

            if (resultadoBusca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma série encontrada.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar série: " + e.getMessage());
        }
    }

    private void mostrarLista(String nomeLista) {
        if (nomeLista == null) {
            return;
        }

        listaAtual = nomeLista;
        preencherTabela(getListaAtual());
        atualizarStatus();
    }

    private void ordenarListaAtual(boolean mostrarMensagem) {
        try {
            List<Serie> lista = getListaAtual();

            if (lista == null || lista.size() < 2) {
                if (mostrarMensagem) {
                    JOptionPane.showMessageDialog(this, "A lista precisa ter pelo menos duas séries para ordenar.");
                }
                return;
            }

            String tipo = (String) comboOrdenacao.getSelectedItem();

            if (tipo.equals("Nome")) {
                lista.sort(Comparator.comparing(
                        serie -> textoSeguro(serie.getNome()),
                        String.CASE_INSENSITIVE_ORDER
                ));
            } else if (tipo.equals("Nota")) {
                lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
            } else if (tipo.equals("Estado")) {
                lista.sort(Comparator.comparing(
                        serie -> textoSeguro(serie.getEstado()),
                        String.CASE_INSENSITIVE_ORDER
                ));
            } else if (tipo.equals("Data de estreia")) {
                lista.sort(Comparator.comparing(
                        serie -> dataSegura(serie.getDataEstreia()),
                        String.CASE_INSENSITIVE_ORDER
                ));
            }

            if (!listaAtual.equals("Busca")) {
                storageService.salvarUsuario(controller.getUsuario());
            }

            preencherTabela(lista);
            atualizarStatus();

            if (mostrarMensagem) {
                JOptionPane.showMessageDialog(this, "Lista ordenada por " + tipo + ".");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao ordenar lista: " + e.getMessage());
        }
    }

    private void adicionarFavorito() {
        Serie serie = pegarSerieSelecionada();

        if (serie != null) {
            controller.adicionarFavorito(serie);
            storageService.salvarUsuario(controller.getUsuario());
            comboLista.setSelectedItem("Favoritos");
            mostrarLista("Favoritos");
            JOptionPane.showMessageDialog(this, "Série adicionada aos favoritos.");
        }
    }

    private void adicionarAssistida() {
        Serie serie = pegarSerieSelecionada();

        if (serie != null) {
            controller.adicionarAssistida(serie);
            storageService.salvarUsuario(controller.getUsuario());
            comboLista.setSelectedItem("Assistidas");
            mostrarLista("Assistidas");
            JOptionPane.showMessageDialog(this, "Série adicionada às assistidas.");
        }
    }

    private void adicionarDesejo() {
        Serie serie = pegarSerieSelecionada();

        if (serie != null) {
            controller.adicionarDesejaAssistir(serie);
            storageService.salvarUsuario(controller.getUsuario());
            comboLista.setSelectedItem("Desejo assistir");
            mostrarLista("Desejo assistir");
            JOptionPane.showMessageDialog(this, "Série adicionada ao desejo assistir.");
        }
    }

    private void removerDaLista() {
        try {
            if (listaAtual.equals("Busca")) {
                JOptionPane.showMessageDialog(this, "Não é possível remover uma série da busca.");
                return;
            }

            Serie serie = pegarSerieSelecionada();

            if (serie == null) {
                return;
            }

            if (listaAtual.equals("Favoritos")) {
                controller.removerFavorito(serie);
            } else if (listaAtual.equals("Assistidas")) {
                controller.removerAssistida(serie);
            } else if (listaAtual.equals("Desejo assistir")) {
                controller.removerDesejaAssistir(serie);
            }

            storageService.salvarUsuario(controller.getUsuario());
            mostrarLista(listaAtual);

            JOptionPane.showMessageDialog(this, "Série removida.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover série: " + e.getMessage());
        }
    }

    private void mostrarDetalhes() {
        Serie serie = pegarSerieSelecionada();

        if (serie != null) {
            JOptionPane.showMessageDialog(
                    this,
                    serie.getDetalhes(),
                    "Detalhes da Série",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private Serie pegarSerieSelecionada() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma série na tabela.");
            return null;
        }

        List<Serie> lista = getListaAtual();

        if (lista == null || linha >= lista.size()) {
            JOptionPane.showMessageDialog(this, "Série inválida.");
            return null;
        }

        return lista.get(linha);
    }

    private List<Serie> getListaAtual() {
        if (listaAtual.equals("Busca")) {
            return resultadoBusca;
        }

        if (listaAtual.equals("Favoritos")) {
            return controller.getFavoritos();
        }

        if (listaAtual.equals("Assistidas")) {
            return controller.getAssistidas();
        }

        if (listaAtual.equals("Desejo assistir")) {
            return controller.getDesejaAssistir();
        }

        return new ArrayList<>();
    }

    private void preencherTabela(List<Serie> lista) {
        modeloTabela.setRowCount(0);

        if (lista == null) {
            return;
        }

        for (Serie serie : lista) {
            modeloTabela.addRow(new Object[]{
                    serie.getNome(),
                    serie.getIdioma(),
                    serie.getGenerosComoTexto(),
                    serie.getNota(),
                    serie.getEstado(),
                    serie.getDataEstreia(),
                    serie.getDataTermino(),
                    serie.getEmissora()
            });
        }
    }

    private void atualizarStatus() {
        labelStatus.setText("Exibindo: " + listaAtual + " | Quantidade: " + tabela.getRowCount());
    }

    private String textoSeguro(String texto) {
        if (texto == null || texto.isBlank() || texto.equals("Não informado")) {
            return "";
        }

        return texto;
    }

    private String dataSegura(String data) {
        if (data == null || data.isBlank() || data.equals("Não informado")) {
            return "9999-99-99";
        }

        return data;
    }
}