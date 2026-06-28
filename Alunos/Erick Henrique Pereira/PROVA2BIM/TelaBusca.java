package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import objetos.Serie;
import servicos.TvMazeService;
import servicos.UsuarioServicos;

public class TelaBusca extends JFrame {

    private final UsuarioServicos usuarioService;
    private final TvMazeService tvMazeService;

    private JTextField campoBusca;
    private JList<Serie> listaSeries;
    private DefaultListModel<Serie> modeloLista;

    // cores do tema escuro
    private static final Color BG_ESCURO     = new Color(28, 28, 40);
    private static final Color BG_MEDIO      = new Color(40, 40, 60);
    private static final Color BG_ITEM       = new Color(50, 50, 70);
    private static final Color BG_SELECIONADO = new Color(88, 86, 214);
    private static final Color TEXTO_CLARO   = new Color(220, 220, 255);
    private static final Color TEXTO_SECUNDARIO = new Color(160, 160, 200);

    public TelaBusca(UsuarioServicos usuarioService) {
        this.usuarioService = usuarioService;
        this.tvMazeService = new TvMazeService();
        configurarJanela();
        construirLayout();
    }

    private void configurarJanela() {
        setTitle("Buscar Séries");
        setSize(700, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_ESCURO);
    }

    private void construirLayout() {
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setBackground(BG_ESCURO);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //inicio
        JLabel lblTitulo = new JLabel("Buscar Séries", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(TEXTO_CLARO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        //busca
        JPanel painelBusca = new JPanel(new BorderLayout(8, 0));
        painelBusca.setBackground(BG_ESCURO);

        campoBusca = new JTextField();
        campoBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        campoBusca.setBackground(BG_MEDIO);
        campoBusca.setForeground(TEXTO_CLARO);
        campoBusca.setCaretColor(TEXTO_CLARO);
        campoBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(88, 86, 214), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JButton btnBuscar = criarBotao("Buscar", new Color(88, 86, 214));
        campoBusca.addActionListener(e -> realizarBusca());
        btnBuscar.addActionListener(e -> realizarBusca());

        painelBusca.add(campoBusca, BorderLayout.CENTER);
        painelBusca.add(btnBuscar, BorderLayout.EAST);

        //lista de resultados
        modeloLista = new DefaultListModel<>();
        listaSeries = new JList<>(modeloLista);
        listaSeries.setBackground(BG_MEDIO);
        listaSeries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //renderer para cada item da lista
        listaSeries.setCellRenderer((list, serie, index, isSelected, cellHasFocus) -> {
            JPanel item = new JPanel(new BorderLayout(8, 2));
            item.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            item.setBackground(isSelected ? BG_SELECIONADO : (index % 2 == 0 ? BG_ITEM : BG_MEDIO));

            JLabel nome = new JLabel(serie.getNome());
            nome.setFont(new Font("Arial", Font.BOLD, 13));
            nome.setForeground(TEXTO_CLARO);

            JLabel info = new JLabel(serie.getStatus() + "  •  Estreia: " + serie.getEstreia() + "  •  Nota: " + String.format("%.1f", serie.getNota()));
            info.setFont(new Font("Arial", Font.PLAIN, 11));
            info.setForeground(TEXTO_SECUNDARIO);

            item.add(nome, BorderLayout.CENTER);
            item.add(info, BorderLayout.SOUTH);
            return item;
        });

        listaSeries.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) exibirDetalhes();
            }
        });

        JScrollPane scrollPane = new JScrollPane(listaSeries);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 80)));
        scrollPane.getViewport().setBackground(BG_MEDIO);

        //botões de ação
        JPanel painelInferior = new JPanel(new BorderLayout(0, 8));
        painelInferior.setBackground(BG_ESCURO);

        JPanel painelAcoes = new JPanel(new GridLayout(1, 3, 8, 0));
        painelAcoes.setBackground(BG_ESCURO);

        JButton btnFavorito     = criarBotao("Favoritar",       new Color(180, 140, 20));
        JButton btnAssistida    = criarBotao("Já Assisti",       new Color(40, 140, 80));
        JButton btnQueroAssistir = criarBotao("Quero Assistir", new Color(52, 120, 180));

        btnFavorito.addActionListener(e -> adicionarNaLista("favoritos"));
        btnAssistida.addActionListener(e -> adicionarNaLista("assistidas"));
        btnQueroAssistir.addActionListener(e -> adicionarNaLista("queroAssistir"));

        painelAcoes.add(btnFavorito);
        painelAcoes.add(btnAssistida);
        painelAcoes.add(btnQueroAssistir);

        JButton btnVoltar = criarBotao("← Voltar ao Menu", new Color(80, 80, 100));
        painelInferior.add(painelAcoes, BorderLayout.CENTER);
        painelInferior.add(btnVoltar, BorderLayout.SOUTH);
        btnVoltar.addActionListener(e -> dispose());

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(BG_ESCURO);
        topo.add(lblTitulo, BorderLayout.NORTH);
        topo.add(painelBusca, BorderLayout.CENTER);

        painel.add(topo, BorderLayout.NORTH);
        painel.add(scrollPane, BorderLayout.CENTER);
        painel.add(painelInferior, BorderLayout.SOUTH);

        add(painel);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(cor.darker()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(cor); }
        });
        return btn;
    }

    private void realizarBusca() {
        String termo = campoBusca.getText().trim();
        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome de uma série para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingWorker<List<Serie>, Void> worker = new SwingWorker<>() {
            @Override protected List<Serie> doInBackground() throws Exception {
                return tvMazeService.buscarPorNome(termo);
            }
            @Override protected void done() {
                try {
                    List<Serie> resultados = get();
                    modeloLista.clear();
                    if (resultados.isEmpty()) {
                        JOptionPane.showMessageDialog(TelaBusca.this, "Nenhuma série encontrada para: " + termo, "Sem resultados", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        for (Serie s : resultados) modeloLista.addElement(s);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TelaBusca.this, "Erro ao buscar séries: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void exibirDetalhes() {
        Serie serie = listaSeries.getSelectedValue();
        if (serie == null) return;

        String detalhes = String.format(
                "Nome: %s\nIdioma: %s\nGêneros: %s\nNota: %.1f\nEstado: %s\n" +
                "Estreia: %s\nEncerramento: %s\nEmissora: %s\n\nSinopse:\n%s",
                serie.getNome(), serie.getIdioma(), serie.getGeneros(),
                serie.getNota(), serie.getStatus(), serie.getEstreia(),
                serie.getFim(), serie.getEmissora(), serie.getSumario()
        );

        JTextArea textArea = new JTextArea(detalhes);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        textArea.setBackground(BG_MEDIO);
        textArea.setForeground(TEXTO_CLARO);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(450, 350));

        JOptionPane.showMessageDialog(this, scroll, "Detalhes: " + serie.getNome(), JOptionPane.PLAIN_MESSAGE);
    }

    private void adicionarNaLista(String lista) {
        Serie serie = listaSeries.getSelectedValue();
        if (serie == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma série primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        switch (lista) {
            case "favoritos"     -> usuarioService.adicionarFavorito(serie);
            case "assistidas"    -> usuarioService.adicionarAssistidos(serie);
            case "queroAssistir" -> usuarioService.adicionarAssistir(serie);
        }
        JOptionPane.showMessageDialog(this, "\"" + serie.getNome() + "\" adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}