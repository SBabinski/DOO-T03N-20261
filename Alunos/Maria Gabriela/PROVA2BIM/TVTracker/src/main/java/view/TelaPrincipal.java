package view;

import model.Serie;
import model.Usuario;
import service.TvMazeService;
import service.JsonService;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class TelaPrincipal extends JFrame {

    private JTextField txtBusca;
    private JButton btnBuscar;
    private JLabel lblUsuario;

    private JTabbedPane abas;

    private JTable tabelaBusca;
    private SerieTableModel modeloBusca;
    private JButton btnFavoritar;
    private JButton btnAssistida;
    private JButton btnDesejo;

    private JTable tabelaFavoritos;
    private SerieTableModel modeloFavoritos;
    private JButton btnRemoverFavorito;

    private JTable tabelaAssistidas;
    private SerieTableModel modeloAssistidas;
    private JButton btnRemoverAssistida;

    private JTable tabelaDesejo;
    private SerieTableModel modeloDesejo;
    private JButton btnRemoverDesejo;

    private Usuario usuario;

    private final TvMazeService api = new TvMazeService();
    private final JsonService json = new JsonService();

    private final List<Serie> resultadosBusca = new ArrayList<>();

    public TelaPrincipal() {

        setTitle("TV Tracker");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // salvar usuario automaticamente ao fechar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    if (usuario != null) {
                        json.salvar(usuario);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        montarMenu();
        montarTela();

        // carregar automaticamente ao abrir
        carregarUsuario();
        atualizarTodasAsTabelas();
    }

    private void montarMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menuConta = new JMenu("Conta");
        JMenuItem itemSair = new JMenuItem("Sair da conta");
        itemSair.addActionListener(e -> sairDaConta());

        menuConta.add(itemSair);
        menuBar.add(menuConta);

        setJMenuBar(menuBar);
    }

    private void montarTela() {

        setLayout(new BorderLayout());

        JPanel topo = new JPanel(new BorderLayout());

        JPanel painelBusca = new JPanel();
        txtBusca = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        painelBusca.add(new JLabel("Série:"));
        painelBusca.add(txtBusca);
        painelBusca.add(btnBuscar);

        lblUsuario = new JLabel("", SwingConstants.RIGHT);
        lblUsuario.setFont(lblUsuario.getFont().deriveFont(Font.BOLD));
        lblUsuario.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        topo.add(painelBusca, BorderLayout.WEST);
        topo.add(lblUsuario, BorderLayout.EAST);

        add(topo, BorderLayout.NORTH);

        abas = new JTabbedPane();

        abas.addTab("Busca", montarAbaBusca());
        abas.addTab("Favoritos", montarAbaFavoritos());
        abas.addTab("Assistidas", montarAbaAssistidas());
        abas.addTab("Desejo Assistir", montarAbaDesejo());

        add(abas, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscar());
        txtBusca.addActionListener(e -> buscar());
    }

    private JPanel montarAbaBusca() {

        JPanel painel = new JPanel(new BorderLayout());

        modeloBusca = new SerieTableModel(resultadosBusca);
        tabelaBusca = criarTabela(modeloBusca);
        adicionarDuploClique(tabelaBusca, modeloBusca);

        painel.add(new JScrollPane(tabelaBusca), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        btnFavoritar = new JButton("Favoritar");
        btnAssistida = new JButton("Já assisti");
        btnDesejo = new JButton("Quero assistir");

        botoes.add(btnFavoritar);
        botoes.add(btnAssistida);
        botoes.add(btnDesejo);

        painel.add(botoes, BorderLayout.SOUTH);

        btnFavoritar.addActionListener(e -> adicionarNaLista(tabelaBusca, modeloBusca, usuario.getFavoritos(), "favoritos"));
        btnAssistida.addActionListener(e -> adicionarNaLista(tabelaBusca, modeloBusca, usuario.getAssistidas(), "já assistidas"));
        btnDesejo.addActionListener(e -> adicionarNaLista(tabelaBusca, modeloBusca, usuario.getDesejaAssistir(), "deseja assistir"));

        return painel;
    }

    private JPanel montarAbaFavoritos() {

        JPanel painel = new JPanel(new BorderLayout());

        modeloFavoritos = new SerieTableModel(new ArrayList<>());
        tabelaFavoritos = criarTabela(modeloFavoritos);
        adicionarDuploClique(tabelaFavoritos, modeloFavoritos);

        painel.add(new JScrollPane(tabelaFavoritos), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        btnRemoverFavorito = new JButton("Remover");
        botoes.add(btnRemoverFavorito);
        painel.add(botoes, BorderLayout.SOUTH);

        btnRemoverFavorito.addActionListener(e ->
                removerDaLista(tabelaFavoritos, modeloFavoritos, usuario.getFavoritos()));

        return painel;
    }

    private JPanel montarAbaAssistidas() {

        JPanel painel = new JPanel(new BorderLayout());

        modeloAssistidas = new SerieTableModel(new ArrayList<>());
        tabelaAssistidas = criarTabela(modeloAssistidas);
        adicionarDuploClique(tabelaAssistidas, modeloAssistidas);

        painel.add(new JScrollPane(tabelaAssistidas), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        btnRemoverAssistida = new JButton("Remover");
        botoes.add(btnRemoverAssistida);
        painel.add(botoes, BorderLayout.SOUTH);

        btnRemoverAssistida.addActionListener(e ->
                removerDaLista(tabelaAssistidas, modeloAssistidas, usuario.getAssistidas()));

        return painel;
    }

    private JPanel montarAbaDesejo() {

        JPanel painel = new JPanel(new BorderLayout());

        modeloDesejo = new SerieTableModel(new ArrayList<>());
        tabelaDesejo = criarTabela(modeloDesejo);
        adicionarDuploClique(tabelaDesejo, modeloDesejo);

        painel.add(new JScrollPane(tabelaDesejo), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        btnRemoverDesejo = new JButton("Remover");
        botoes.add(btnRemoverDesejo);
        painel.add(botoes, BorderLayout.SOUTH);

        btnRemoverDesejo.addActionListener(e ->
                removerDaLista(tabelaDesejo, modeloDesejo, usuario.getDesejaAssistir()));

        return painel;
    }

    private JTable criarTabela(SerieTableModel modelo) {

        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoCreateRowSorter(false);

        TableRowSorter<SerieTableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(220);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
        return tabela;
    }

    private void adicionarDuploClique(JTable tabela, SerieTableModel modelo) {

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linhaView = tabela.getSelectedRow();
                    if (linhaView == -1) return;

                    int linhaModelo = tabela.convertRowIndexToModel(linhaView);
                    Serie s = modelo.getSerieNaLinha(linhaModelo);

                    if (s != null) {
                        mostrarDetalhes(s);
                    }
                }
            }
        });
    }

    private void mostrarDetalhes(Serie s) {

        JTextArea texto = new JTextArea(s.toString());
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(texto);
        scroll.setPreferredSize(new Dimension(420, 260));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Detalhes - " + s.getNome(),
                JOptionPane.PLAIN_MESSAGE);
    }

    private Serie getSelecionada(JTable tabela, SerieTableModel modelo) {

        int linhaView = tabela.getSelectedRow();

        if (linhaView == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma série na tabela.");
            return null;
        }

        int linhaModelo = tabela.convertRowIndexToModel(linhaView);
        return modelo.getSerieNaLinha(linhaModelo);
    }

    private void carregarUsuario() {

        try {
            usuario = json.carregar();

            if (usuario == null) {

                String nome = JOptionPane.showInputDialog(this, "Digite seu apelido:");

                if (nome == null || nome.trim().isEmpty()) {
                    nome = "Usuário Local";
                }

                usuario = new Usuario(nome);
                json.salvar(usuario);
            }

            lblUsuario.setText(usuario.getNome());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuário");
            usuario = new Usuario("Usuário Local");
            lblUsuario.setText(usuario.getNome());
        }
    }

    private void sairDaConta() {

        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair da conta " + usuario.getNome() + "?",
                "Sair da conta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            json.salvar(usuario);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar antes de sair");
        }

        String nome = JOptionPane.showInputDialog(this, "Digite o apelido do novo usuário:");

        if (nome == null || nome.trim().isEmpty()) {
            nome = "Usuário Local";
        }

        usuario = new Usuario(nome);

        try {
            json.salvar(usuario);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar novo usuário");
        }

        lblUsuario.setText(usuario.getNome());

        resultadosBusca.clear();
        modeloBusca.atualizar(resultadosBusca);
        txtBusca.setText("");

        atualizarTodasAsTabelas();
    }

    private void buscar() {

        try {

            String texto = txtBusca.getText();

            if (texto.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o nome da série");
                return;
            }

            resultadosBusca.clear();
            resultadosBusca.addAll(api.buscar(texto));

            modeloBusca.atualizar(resultadosBusca);

            if (resultadosBusca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma série encontrada.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro na busca: " + e.getMessage());
        }
    }

    private void adicionarNaLista(JTable tabela, SerieTableModel modelo, List<Serie> lista, String nomeLista) {

        Serie s = getSelecionada(tabela, modelo);

        if (s == null) {
            return;
        }

        if (jaEstaNaLista(lista, s)) {
            JOptionPane.showMessageDialog(this, "\"" + s.getNome() + "\" já está em " + nomeLista + ".");
            return;
        }

        lista.add(s);
        JOptionPane.showMessageDialog(this, "\"" + s.getNome() + "\" adicionada em " + nomeLista + ".");

        atualizarTodasAsTabelas();
    }

    private void removerDaLista(JTable tabela, SerieTableModel modelo, List<Serie> lista) {

        Serie s = getSelecionada(tabela, modelo);

        if (s == null) {
            return;
        }

        lista.remove(s);
        atualizarTodasAsTabelas();
    }

    private boolean jaEstaNaLista(List<Serie> lista, Serie s) {

        for (Serie item : lista) {
            if (item.getNome().equalsIgnoreCase(s.getNome())) {
                return true;
            }
        }
        return false;
    }

    private void atualizarTodasAsTabelas() {

        if (usuario == null) {
            return;
        }

        modeloFavoritos.atualizar(usuario.getFavoritos());
        modeloAssistidas.atualizar(usuario.getAssistidas());
        modeloDesejo.atualizar(usuario.getDesejaAssistir());
    }

    private static class SerieTableModel extends AbstractTableModel {

        private final String[] colunas = {"Nome", "Nota", "Estado", "Estreia", "Emissora", "Gêneros"};
        private List<Serie> series;

        SerieTableModel(List<Serie> series) {
            this.series = series;
        }

        void atualizar(List<Serie> novaLista) {
            this.series = new ArrayList<>(novaLista);
            fireTableDataChanged();
        }

        Serie getSerieNaLinha(int linha) {
            if (linha < 0 || linha >= series.size()) {
                return null;
            }
            return series.get(linha);
        }

        @Override
        public int getRowCount() {
            return series.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public String getColumnName(int column) {
            return colunas[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 1) {
                return Double.class;
            }
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            Serie s = series.get(rowIndex);

            switch (columnIndex) {
                case 0: return s.getNome();
                case 1: return s.getNota();
                case 2: return s.getEstado();
                case 3: return s.getEstreia();
                case 4: return s.getEmissora();
                case 5: return s.getGeneros();
                default: return "";
            }
        }
    }
}
