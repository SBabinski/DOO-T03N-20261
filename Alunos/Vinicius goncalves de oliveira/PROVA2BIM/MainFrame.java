package com.seriestv;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private Usuario usuario;
    private PersistenciaJSON persistencia;
    private ApiTVMaze api;

    // Componentes de busca
    private JTextField campoBusca;
    private DefaultTableModel modeloBusca;
    private JTable tabelaBusca;
    private List<Serie> seriesEncontradas;

    // Painel de listas
    private ListaPanel listaPanel;

    // Troca de telas
    private CardLayout cardLayout;
    private JPanel painelConteudo;

    public MainFrame(Usuario usuario, PersistenciaJSON persistencia) {
        this.usuario = usuario;
        this.persistencia = persistencia;
        this.api = new ApiTVMaze();

        setTitle("SeriesTV - " + usuario.getNome());
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                salvarESair();
            }
        });

        criarTela();
    }

    private void criarTela() {
        // Botoes do topo
        JButton btnBusca = new JButton("Buscar Series");
        JButton btnListas = new JButton("Minhas Listas");

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Ola, " + usuario.getNome() + "!  "));
        topo.add(btnBusca);
        topo.add(btnListas);
        add(topo, BorderLayout.NORTH);

        // Telas
        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);

        painelConteudo.add(criarPainelBusca(), "busca");

        listaPanel = new ListaPanel(usuario, persistencia);
        painelConteudo.add(listaPanel, "listas");

        add(painelConteudo, BorderLayout.CENTER);

        // Acoes
        btnBusca.addActionListener(e -> cardLayout.show(painelConteudo, "busca"));
        btnListas.addActionListener(e -> {
            listaPanel.atualizar();
            cardLayout.show(painelConteudo, "listas");
        });
    }

    private JPanel criarPainelBusca() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo de busca
        campoBusca = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        JPanel topoBusca = new JPanel(new BorderLayout(5, 0));
        topoBusca.add(new JLabel("Nome da serie: "), BorderLayout.WEST);
        topoBusca.add(campoBusca, BorderLayout.CENTER);
        topoBusca.add(btnBuscar, BorderLayout.EAST);
        painel.add(topoBusca, BorderLayout.NORTH);

        // Tabela de resultados
        String[] colunas = {"Imagem", "Nome", "Idioma", "Generos", "Nota", "Status", "Estreia", "Termino", "Emissora"};
        modeloBusca = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }

            public Class<?> getColumnClass(int coluna) {
                return coluna == 0 ? ImageIcon.class : Object.class;
            }
        };
        tabelaBusca = new JTable(modeloBusca);
        tabelaBusca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        configurarTabelaComImagem(tabelaBusca);
        painel.add(new JScrollPane(tabelaBusca), BorderLayout.CENTER);

        // Botoes de acao
        JButton btnFavorito    = new JButton("+ Favoritos");
        JButton btnAssistida   = new JButton("+ Ja Assisti");
        JButton btnQuero       = new JButton("+ Quero Assistir");

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rodape.add(new JLabel("Adicionar a lista: "));
        rodape.add(btnFavorito);
        rodape.add(btnAssistida);
        rodape.add(btnQuero);
        painel.add(rodape, BorderLayout.SOUTH);

        // Acoes
        btnBuscar.addActionListener(e -> buscar());
        campoBusca.addActionListener(e -> buscar()); // Enter tambem busca

        btnFavorito.addActionListener(e -> adicionarNaLista("favorito"));
        btnAssistida.addActionListener(e -> adicionarNaLista("assistida"));
        btnQuero.addActionListener(e -> adicionarNaLista("quero"));

        return painel;
    }

    private void buscar() {
        String termo = campoBusca.getText().trim();
        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome de uma serie.");
            return;
        }

        modeloBusca.setRowCount(0);

        // Busca em background para nao travar a tela
        SwingWorker<ResultadoBuscaTela, Void> worker = new SwingWorker<>() {
            protected ResultadoBuscaTela doInBackground() throws Exception {
                List<Serie> series = api.buscarSeries(termo);
                List<Object[]> linhas = new ArrayList<>();

                for (Serie s : series) {
                    linhas.add(criarLinhaSerie(s));
                }

                return new ResultadoBuscaTela(series, linhas);
            }

            protected void done() {
                try {
                    ResultadoBuscaTela resultado = get();
                    seriesEncontradas = resultado.series;

                    if (seriesEncontradas.isEmpty()) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Nenhuma serie encontrada.");
                        return;
                    }

                    for (Object[] linha : resultado.linhas) {
                        modeloBusca.addRow(linha);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                        "Erro ao buscar: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    private Object[] criarLinhaSerie(Serie s) {
        return new Object[]{
            ImagemUtil.carregarCapa(s.getImageUrl()),
            s.getName(), s.getLanguage(), s.getGenresFormatted(),
            s.getRating() > 0 ? s.getRating() : "-",
            s.getStatus(), s.getPremiered(), s.getEnded(), s.getNetwork()
        };
    }

    private void configurarTabelaComImagem(JTable tabela) {
        tabela.setRowHeight(90);
        TableColumn colunaImagem = tabela.getColumnModel().getColumn(0);
        colunaImagem.setMinWidth(70);
        colunaImagem.setPreferredWidth(70);
        colunaImagem.setMaxWidth(80);
        colunaImagem.setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, "", isSelected, hasFocus, row, column
                );
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setIcon(value instanceof Icon ? (Icon) value : null);
                return label;
            }
        });
    }

    private static class ResultadoBuscaTela {
        private final List<Serie> series;
        private final List<Object[]> linhas;

        private ResultadoBuscaTela(List<Serie> series, List<Object[]> linhas) {
            this.series = series;
            this.linhas = linhas;
        }
    }

    private void adicionarNaLista(String lista) {
        int linha = tabelaBusca.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma serie primeiro.");
            return;
        }

        Serie serie = seriesEncontradas.get(linha);

        switch (lista) {
            case "favorito"  -> usuario.adicionarFavorito(serie);
            case "assistida" -> usuario.adicionarJaAssistida(serie);
            case "quero"     -> usuario.adicionarQueroAssistir(serie);
        }

        try {
            persistencia.salvar(usuario);
            JOptionPane.showMessageDialog(this, "\"" + serie.getName() + "\" adicionada!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    private void salvarESair() {
        try {
            persistencia.salvar(usuario);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
        System.exit(0);
    }
}
