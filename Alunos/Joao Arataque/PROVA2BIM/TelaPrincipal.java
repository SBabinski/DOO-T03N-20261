import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {
    private Usuario usuario;
    private GerenciadorSeries gerenciador;
    private TVMazeAPI api;
    private PersistenciaJson persistencia;
    private Serie serieAtual;

    private JTextField campoBusca;
    private JTextArea areaDetalhes;
    private JList<Serie> listaVisual;
    private DefaultListModel<Serie> modeloLista;
    private JComboBox<String> comboLista;

    public TelaPrincipal() {
        persistencia = new PersistenciaJson();

        if (persistencia.arquivoExiste()) {
            usuario = persistencia.carregarUsuario();
        }

        if (usuario == null) {
            String nome = JOptionPane.showInputDialog("Digite seu nome ou apelido:");

            if (nome == null || nome.trim().isEmpty()) {
                nome = "Usuário";
            }

            usuario = new Usuario(nome);
        }

        gerenciador = new GerenciadorSeries(usuario);
        api = new TVMazeAPI();

        setTitle("PROVA02 - Sistema de Séries");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        criarTela();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                persistencia.salvarUsuario(usuario);
            }
        });

        setVisible(true);
    }

    private void criarTela() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel painelTopo = new JPanel();

        campoBusca = new JTextField(25);
        JButton btnBuscar = new JButton("Buscar Série");

        painelTopo.add(new JLabel("Série:"));
        painelTopo.add(campoBusca);
        painelTopo.add(btnBuscar);

        JPanel painelCentro = new JPanel(new GridLayout(1, 2, 10, 10));

        areaDetalhes = new JTextArea();
        areaDetalhes.setEditable(false);
        areaDetalhes.setFont(new Font("Arial", Font.PLAIN, 14));

        modeloLista = new DefaultListModel<>();
        listaVisual = new JList<>(modeloLista);
        listaVisual.setFont(new Font("Arial", Font.PLAIN, 14));

        painelCentro.add(new JScrollPane(areaDetalhes));
        painelCentro.add(new JScrollPane(listaVisual));

        JPanel painelBotoes = new JPanel(new GridLayout(5, 3, 5, 5));

        JButton btnAddFavorita = new JButton("Add Favorita");
        JButton btnAddAssistida = new JButton("Add Assistida");
        JButton btnAddDeseja = new JButton("Add Deseja Assistir");

        JButton btnRemoverFavorita = new JButton("Remover Favorita");
        JButton btnRemoverAssistida = new JButton("Remover Assistida");
        JButton btnRemoverDeseja = new JButton("Remover Deseja");

        JButton btnMostrarLista = new JButton("Mostrar Lista");
        JButton btnDetalhesSelecionada = new JButton("Detalhes Selecionada");
        JButton btnSalvar = new JButton("Salvar JSON");

        JButton btnOrdenarNome = new JButton("Ordenar Nome");
        JButton btnOrdenarNota = new JButton("Ordenar Nota");
        JButton btnOrdenarStatus = new JButton("Ordenar Status");

        JButton btnOrdenarEstreia = new JButton("Ordenar Estreia");
        JButton btnLimpar = new JButton("Limpar");
        comboLista = new JComboBox<>(new String[]{"Favoritas", "Assistidas", "Deseja Assistir"});

        painelBotoes.add(btnAddFavorita);
        painelBotoes.add(btnAddAssistida);
        painelBotoes.add(btnAddDeseja);

        painelBotoes.add(btnRemoverFavorita);
        painelBotoes.add(btnRemoverAssistida);
        painelBotoes.add(btnRemoverDeseja);

        painelBotoes.add(comboLista);
        painelBotoes.add(btnMostrarLista);
        painelBotoes.add(btnDetalhesSelecionada);

        painelBotoes.add(btnOrdenarNome);
        painelBotoes.add(btnOrdenarNota);
        painelBotoes.add(btnOrdenarStatus);

        painelBotoes.add(btnOrdenarEstreia);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);

        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(painelCentro, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        btnBuscar.addActionListener(e -> buscarSerie());

        btnAddFavorita.addActionListener(e -> adicionarFavorita());
        btnAddAssistida.addActionListener(e -> adicionarAssistida());
        btnAddDeseja.addActionListener(e -> adicionarDesejaAssistir());

        btnRemoverFavorita.addActionListener(e -> removerFavorita());
        btnRemoverAssistida.addActionListener(e -> removerAssistida());
        btnRemoverDeseja.addActionListener(e -> removerDesejaAssistir());

        btnMostrarLista.addActionListener(e -> atualizarListaVisual(getListaEscolhida()));
        btnDetalhesSelecionada.addActionListener(e -> mostrarDetalhesSelecionada());

        btnOrdenarNome.addActionListener(e -> {
            gerenciador.ordenarPorNome(getListaEscolhida());
            atualizarListaVisual(getListaEscolhida());
        });

        btnOrdenarNota.addActionListener(e -> {
            gerenciador.ordenarPorNota(getListaEscolhida());
            atualizarListaVisual(getListaEscolhida());
        });

        btnOrdenarStatus.addActionListener(e -> {
            gerenciador.ordenarPorStatus(getListaEscolhida());
            atualizarListaVisual(getListaEscolhida());
        });

        btnOrdenarEstreia.addActionListener(e -> {
            gerenciador.ordenarPorEstreia(getListaEscolhida());
            atualizarListaVisual(getListaEscolhida());
        });

        btnSalvar.addActionListener(e -> {
            persistencia.salvarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Dados salvos no arquivo usuario.json.");
        });

        btnLimpar.addActionListener(e -> {
            areaDetalhes.setText("");
            modeloLista.clear();
        });
    }

    private void buscarSerie() {
        try {
            String nome = campoBusca.getText();

            if (nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o nome de uma série.");
                return;
            }

            serieAtual = api.buscarSerie(nome);
            areaDetalhes.setText(serieAtual.detalhes());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar série: " + e.getMessage());
        }
    }

    private void adicionarFavorita() {
        if (serieAtual == null) {
            JOptionPane.showMessageDialog(this, "Busque uma série primeiro.");
            return;
        }

        gerenciador.adicionarFavorita(serieAtual);
        atualizarListaVisual(gerenciador.getFavoritas());
        JOptionPane.showMessageDialog(this, "Série adicionada aos favoritos.");
    }

    private void adicionarAssistida() {
        if (serieAtual == null) {
            JOptionPane.showMessageDialog(this, "Busque uma série primeiro.");
            return;
        }

        gerenciador.adicionarAssistida(serieAtual);
        atualizarListaVisual(gerenciador.getAssistidas());
        JOptionPane.showMessageDialog(this, "Série adicionada às assistidas.");
    }

    private void adicionarDesejaAssistir() {
        if (serieAtual == null) {
            JOptionPane.showMessageDialog(this, "Busque uma série primeiro.");
            return;
        }

        gerenciador.adicionarDesejaAssistir(serieAtual);
        atualizarListaVisual(gerenciador.getDesejaAssistir());
        JOptionPane.showMessageDialog(this, "Série adicionada à lista deseja assistir.");
    }

    private void removerFavorita() {
        Serie selecionada = listaVisual.getSelectedValue();

        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma série na lista.");
            return;
        }

        gerenciador.removerFavorita(selecionada);
        atualizarListaVisual(gerenciador.getFavoritas());
        JOptionPane.showMessageDialog(this, "Série removida dos favoritos.");
    }

    private void removerAssistida() {
        Serie selecionada = listaVisual.getSelectedValue();

        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma série na lista.");
            return;
        }

        gerenciador.removerAssistida(selecionada);
        atualizarListaVisual(gerenciador.getAssistidas());
        JOptionPane.showMessageDialog(this, "Série removida das assistidas.");
    }

    private void removerDesejaAssistir() {
        Serie selecionada = listaVisual.getSelectedValue();

        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma série na lista.");
            return;
        }

        gerenciador.removerDesejaAssistir(selecionada);
        atualizarListaVisual(gerenciador.getDesejaAssistir());
        JOptionPane.showMessageDialog(this, "Série removida da lista deseja assistir.");
    }

    private List<Serie> getListaEscolhida() {
        String escolha = (String) comboLista.getSelectedItem();

        if ("Assistidas".equals(escolha)) {
            return gerenciador.getAssistidas();
        }

        if ("Deseja Assistir".equals(escolha)) {
            return gerenciador.getDesejaAssistir();
        }

        return gerenciador.getFavoritas();
    }

    private void atualizarListaVisual(List<Serie> lista) {
        modeloLista.clear();

        for (Serie serie : lista) {
            modeloLista.addElement(serie);
        }

        if (lista.isEmpty()) {
            areaDetalhes.setText("Nenhuma série cadastrada nessa lista.");
        }
    }

    private void mostrarDetalhesSelecionada() {
        Serie selecionada = listaVisual.getSelectedValue();

        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma série na lista.");
            return;
        }

        areaDetalhes.setText(selecionada.detalhes());
    }
}