//janela principal em Swing, com abas de busca e das 3 listas de séries.


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class Tela extends JFrame {

    private final Api api = new Api();
    private final Arquivo arquivo = new Arquivo();

    private Usuario usuario;

    private JTextField campoBusca;
    private JList<Serie> listaResultados;
    private DefaultListModel<Serie> modeloResultados;
    private JTextArea textoDetalhesBusca;

    private DefaultListModel<Serie> modeloFavoritas, modeloAssistidas, modeloQueroAssistir;

    private JLabel rotuloUsuario;

    public Tela() {
        super("TVTRACKER");

        carregarDados();
        montarTela();
        perguntarNomeSeNecessario();
    }

    private void montarTela() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(20, 20, 20));

        JLabel tituloApp = new JLabel("  TVTRACKER");
        tituloApp.setFont(new Font("Arial", Font.BOLD, 22));
        tituloApp.setForeground(new Color(229, 9, 20)); // vermelho Netflix

        rotuloUsuario = new JLabel("Usuario: " + usuario.getNome() + "  ");
        rotuloUsuario.setFont(rotuloUsuario.getFont().deriveFont(Font.BOLD, 14f));
        rotuloUsuario.setForeground(Color.WHITE);

        JButton botaoTrocarUsuario = new JButton("Trocar usuario");
        botaoTrocarUsuario.setBackground(new Color(229, 9, 20));
        botaoTrocarUsuario.setForeground(Color.WHITE);
        botaoTrocarUsuario.setFocusPainted(false);
        botaoTrocarUsuario.addActionListener(e -> trocarNomeUsuario());

        JPanel painelDireitaTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        painelDireitaTopo.setBackground(new Color(20, 20, 20));
        painelDireitaTopo.add(rotuloUsuario);
        painelDireitaTopo.add(botaoTrocarUsuario);

        painelTopo.add(tituloApp, BorderLayout.WEST);
        painelTopo.add(painelDireitaTopo, BorderLayout.EAST);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Buscar Series", criarAbaBusca());
        abas.addTab("Favoritas", criarAbaLista("favoritas"));
        abas.addTab("Ja Assistidas", criarAbaLista("assistidas"));
        abas.addTab("Quero Assistir", criarAbaLista("queroAssistir"));

        setLayout(new BorderLayout());
        add(painelTopo, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvarDados();
                dispose();
                System.exit(0);
            }
        });
    }

    private JPanel criarAbaBusca() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelBusca = new JPanel(new BorderLayout(8, 8));
        campoBusca = new JTextField();
        JButton botaoBuscar = new JButton("Buscar");
        painelBusca.add(new JLabel("Nome da serie:"), BorderLayout.WEST);
        painelBusca.add(campoBusca, BorderLayout.CENTER);
        painelBusca.add(botaoBuscar, BorderLayout.EAST);

        campoBusca.addActionListener(e -> buscar());
        botaoBuscar.addActionListener(e -> buscar());

        modeloResultados = new DefaultListModel<>();
        listaResultados = new JList<>(modeloResultados);
        listaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        textoDetalhesBusca = new JTextArea();
        textoDetalhesBusca.setEditable(false);
        textoDetalhesBusca.setLineWrap(true);
        textoDetalhesBusca.setWrapStyleWord(true);

        listaResultados.addListSelectionListener(e ->
                textoDetalhesBusca.setText(montarDetalhes(listaResultados.getSelectedValue())));

        JScrollPane scrollResultados = new JScrollPane(listaResultados);
        scrollResultados.setPreferredSize(new Dimension(280, 0));
        JScrollPane scrollDetalhes = new JScrollPane(textoDetalhesBusca);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 8, 0));
        JButton botaoFavorita = new JButton("+ Favoritas");
        JButton botaoAssistida = new JButton("+ Ja Assistida");
        JButton botaoQuero = new JButton("+ Quero Assistir");

        botaoFavorita.addActionListener(e -> adicionarNaLista("favoritas"));
        botaoAssistida.addActionListener(e -> adicionarNaLista("assistidas"));
        botaoQuero.addActionListener(e -> adicionarNaLista("queroAssistir"));

        painelBotoes.add(botaoFavorita);
        painelBotoes.add(botaoAssistida);
        painelBotoes.add(botaoQuero);

        JPanel painelDireita = new JPanel(new BorderLayout(8, 8));
        painelDireita.add(scrollDetalhes, BorderLayout.CENTER);
        painelDireita.add(painelBotoes, BorderLayout.SOUTH);

        JSplitPane divisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollResultados, painelDireita);
        divisor.setDividerLocation(280);

        painel.add(painelBusca, BorderLayout.NORTH);
        painel.add(divisor, BorderLayout.CENTER);
        return painel;
    }

    private void buscar() {
        String texto = campoBusca.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome de uma serie para buscar.",
                    "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            List<Serie> resultados = api.buscarPorNome(texto);

            modeloResultados.clear();
            for (Serie s : resultados) {
                modeloResultados.addElement(s);
            }

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma serie encontrada com esse nome.",
                        "Sem resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ErroApi erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(),
                    "Erro ao buscar na API", JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private JPanel criarAbaLista(String tipo) {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<Serie> modelo = new DefaultListModel<>();
        JList<Serie> lista = new JList<>(modelo);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTextArea textoDetalhes = new JTextArea();
        textoDetalhes.setEditable(false);
        textoDetalhes.setLineWrap(true);
        textoDetalhes.setWrapStyleWord(true);

        lista.addListSelectionListener(e ->
                textoDetalhes.setText(montarDetalhes(lista.getSelectedValue())));

        if (tipo.equals("favoritas")) {
            modeloFavoritas = modelo;
        } else if (tipo.equals("assistidas")) {
            modeloAssistidas = modelo;
        } else {
            modeloQueroAssistir = modelo;
        }

        JPanel painelOrdenacao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelOrdenacao.add(new JLabel("Ordenar por:"));
        JComboBox<String> comboOrdenacao = new JComboBox<>(new String[]{
                "Nome (A-Z)", "Nota geral", "Estado", "Data de estreia"
        });
        painelOrdenacao.add(comboOrdenacao);
        comboOrdenacao.addActionListener(e ->
                ordenarLista(modelo, (String) comboOrdenacao.getSelectedItem()));

        JButton botaoRemover = new JButton("Remover da lista");
        botaoRemover.addActionListener(e -> removerDaLista(lista, modelo, tipo));

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelOrdenacao, BorderLayout.WEST);
        painelSuperior.add(botaoRemover, BorderLayout.EAST);

        JScrollPane scrollLista = new JScrollPane(lista);
        scrollLista.setPreferredSize(new Dimension(280, 0));
        JScrollPane scrollDetalhes = new JScrollPane(textoDetalhes);

        JSplitPane divisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLista, scrollDetalhes);
        divisor.setDividerLocation(280);

        painel.add(painelSuperior, BorderLayout.NORTH);
        painel.add(divisor, BorderLayout.CENTER);

        preencherModelo(modelo, obterLista(tipo));
        return painel;
    }

    private List<Serie> obterLista(String tipo) {
        if (tipo.equals("favoritas")) return usuario.getFavoritas();
        if (tipo.equals("assistidas")) return usuario.getAssistidas();
        return usuario.getQueroAssistir();
    }

    private void preencherModelo(DefaultListModel<Serie> modelo, List<Serie> lista) {
        modelo.clear();
        for (Serie s : lista) {
            modelo.addElement(s);
        }
    }

    private void adicionarNaLista(String tipo) {
        try {
            Serie selecionada = listaResultados.getSelectedValue();
            if (selecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma serie na lista de resultados primeiro.",
                        "Nenhuma serie selecionada", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean adicionou;
            if (tipo.equals("favoritas")) {
                adicionou = usuario.adicionarFavorita(selecionada);
            } else if (tipo.equals("assistidas")) {
                adicionou = usuario.adicionarAssistida(selecionada);
            } else {
                adicionou = usuario.adicionarQueroAssistir(selecionada);
            }

            if (!adicionou) {
                JOptionPane.showMessageDialog(this, "Essa serie ja esta nessa lista.",
                        "Ja adicionada", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            atualizarTodasAsListas();
            salvarDados();
        } catch (Exception erro) {
            mostrarErro(erro);
        }
    }

    private void removerDaLista(JList<Serie> lista, DefaultListModel<Serie> modelo, String tipo) {
        try {
            Serie selecionada = lista.getSelectedValue();
            if (selecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma serie da lista para remover.",
                        "Nenhuma serie selecionada", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (tipo.equals("favoritas")) {
                usuario.removerFavorita(selecionada);
            } else if (tipo.equals("assistidas")) {
                usuario.removerAssistida(selecionada);
            } else {
                usuario.removerQueroAssistir(selecionada);
            }

            preencherModelo(modelo, obterLista(tipo));
            salvarDados();
        } catch (Exception erro) {
            mostrarErro(erro);
        }
    }

    private void atualizarTodasAsListas() {
        if (modeloFavoritas != null) preencherModelo(modeloFavoritas, usuario.getFavoritas());
        if (modeloAssistidas != null) preencherModelo(modeloAssistidas, usuario.getAssistidas());
        if (modeloQueroAssistir != null) preencherModelo(modeloQueroAssistir, usuario.getQueroAssistir());
    }

    private void ordenarLista(DefaultListModel<Serie> modelo, String opcaoEscolhida) {
        Comparator<Serie> comparador;
        if ("Nota geral".equals(opcaoEscolhida)) {
            comparador = Ordenar.porNota();
        } else if ("Estado".equals(opcaoEscolhida)) {
            comparador = Ordenar.porEstado();
        } else if ("Data de estreia".equals(opcaoEscolhida)) {
            comparador = Ordenar.porDataEstreia();
        } else {
            comparador = Ordenar.porNome();
        }

        Vector<Serie> elementos = new Vector<>();
        for (int i = 0; i < modelo.size(); i++) {
            elementos.add(modelo.get(i));
        }
        elementos.sort(comparador);

        modelo.clear();
        for (Serie s : elementos) {
            modelo.addElement(s);
        }
    }

    private String montarDetalhes(Serie serie) {
        if (serie == null) {
            return "Selecione uma serie para ver os detalhes.";
        }
        StringBuilder texto = new StringBuilder();
        texto.append("Nome: ").append(serie.getNome()).append("\n\n");
        texto.append("Idioma: ").append(valor(serie.getIdioma())).append("\n\n");
        texto.append("Generos: ").append(serie.getGenerosTexto()).append("\n\n");
        texto.append("Nota geral: ").append(serie.getNota() != null ? serie.getNota() : "Sem nota").append("\n\n");
        texto.append("Estado: ").append(valor(serie.getEstado())).append("\n\n");
        texto.append("Data de estreia: ").append(valor(serie.getDataEstreia())).append("\n\n");
        texto.append("Data de termino: ").append(valor(serie.getDataTermino())).append("\n\n");
        texto.append("Emissora: ").append(valor(serie.getEmissora()));
        return texto.toString();
    }

    private String valor(String texto) {
        return texto != null ? texto : "Nao informado";
    }

    private void perguntarNomeSeNecessario() {
        if ("Usuario".equals(usuario.getNome())) {
            trocarNomeUsuario();
        }
    }

    private void trocarNomeUsuario() {
        String novoNome = JOptionPane.showInputDialog(this, "Digite seu nome ou apelido:",
                usuario.getNome());
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            usuario.setNome(novoNome.trim());
            rotuloUsuario.setText(" Usuario: " + usuario.getNome());
            salvarDados();
        }
    }

    private void carregarDados() {
        try {
            usuario = arquivo.carregar();
        } catch (ErroArquivo erro) {
            usuario = new Usuario();
            JOptionPane.showMessageDialog(null,
                    "Nao foi possivel carregar dados salvos anteriormente.\n" +
                            "Um novo perfil em branco foi criado.\n\nDetalhe: " + erro.getMessage(),
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void salvarDados() {
        try {
            arquivo.salvar(usuario);
        } catch (ErroArquivo erro) {
            JOptionPane.showMessageDialog(this,
                    "Nao foi possivel salvar os dados.\n\nDetalhe: " + erro.getMessage(),
                    "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarErro(Exception erro) {
        JOptionPane.showMessageDialog(this,
                "Ocorreu um erro, mas o programa vai continuar funcionando.\n\nDetalhe: " + erro.getMessage(),
                "Erro inesperado", JOptionPane.ERROR_MESSAGE);
    }
}