package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import objetos.Serie;
import servicos.UsuarioServicos;

public class TelaListas extends JFrame {

    private final UsuarioServicos usuarioService;

    private JTabbedPane abas;
    private DefaultListModel<Serie> modeloFavoritos;
    private DefaultListModel<Serie> modeloAssistidas;
    private DefaultListModel<Serie> modeloQueroAssistir;

    // cores do tema escuro
    private static final Color BG_ESCURO      = new Color(28, 28, 40);
    private static final Color BG_MEDIO       = new Color(40, 40, 60);
    private static final Color BG_ITEM        = new Color(50, 50, 70);
    private static final Color BG_SELECIONADO = new Color(88, 86, 214);
    private static final Color TEXTO_CLARO    = new Color(220, 220, 255);
    private static final Color TEXTO_SECUNDARIO = new Color(160, 160, 200);

    public TelaListas(UsuarioServicos usuarioService) {
        this.usuarioService = usuarioService;
        configurarJanela();
        construirLayout();
        carregarListas();
    }

    private void configurarJanela() {
        setTitle("Minhas Listas");
        setSize(700, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_ESCURO);
    }

    private void construirLayout() {
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setBackground(BG_ESCURO);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- cabeçalho ---
        JLabel lblTitulo = new JLabel("Minhas Listas", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(TEXTO_CLARO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // --- abas ---
        abas = new JTabbedPane();
        abas.setBackground(BG_MEDIO);
        abas.setForeground(TEXTO_CLARO);
        abas.setFont(new Font("Arial", Font.BOLD, 13));
        abas.addTab("Favoritos",      criarPainelLista("favoritos"));
        abas.addTab("Já Assisti",     criarPainelLista("assistidas"));
        abas.addTab("Quero Assistir", criarPainelLista("queroAssistir"));

        // --- painel inferior ---
        JPanel painelInferior = new JPanel(new BorderLayout(0, 8));
        painelInferior.setBackground(BG_ESCURO);

        // botões de ordenação
        JPanel painelOrdenacao = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        painelOrdenacao.setBackground(BG_ESCURO);

        JLabel lblOrdenar = new JLabel("Ordenar por:");
        lblOrdenar.setFont(new Font("Arial", Font.BOLD, 12));
        lblOrdenar.setForeground(TEXTO_SECUNDARIO);

        JButton btnNome   = criarBotaoOrdem("Nome");
        JButton btnNota   = criarBotaoOrdem("Nota");
        JButton btnStatus = criarBotaoOrdem("Status");
        JButton btnData   = criarBotaoOrdem("Data de Estreia");

        btnNome.addActionListener(e -> ordenarListaAtual("nome"));
        btnNota.addActionListener(e -> ordenarListaAtual("nota"));
        btnStatus.addActionListener(e -> ordenarListaAtual("status"));
        btnData.addActionListener(e -> ordenarListaAtual("data"));

        painelOrdenacao.add(lblOrdenar);
        painelOrdenacao.add(btnNome);
        painelOrdenacao.add(btnNota);
        painelOrdenacao.add(btnStatus);
        painelOrdenacao.add(btnData);

        JButton btnVoltar = criarBotao("← Voltar ao Menu", new Color(80, 80, 100));
        btnVoltar.addActionListener(e -> dispose());

        painelInferior.add(painelOrdenacao, BorderLayout.CENTER);
        painelInferior.add(btnVoltar, BorderLayout.SOUTH);

        painel.add(lblTitulo, BorderLayout.NORTH);
        painel.add(abas, BorderLayout.CENTER);
        painel.add(painelInferior, BorderLayout.SOUTH);

        add(painel);
    }

    private JPanel criarPainelLista(String tipoLista) {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBackground(BG_ESCURO);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        DefaultListModel<Serie> modelo = new DefaultListModel<>();
        switch (tipoLista) {
            case "favoritos"     -> modeloFavoritos = modelo;
            case "assistidas"    -> modeloAssistidas = modelo;
            case "queroAssistir" -> modeloQueroAssistir = modelo;
        }

        JList<Serie> jList = new JList<>(modelo);
        jList.setBackground(BG_MEDIO);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jList.setCellRenderer((list, serie, index, isSelected, cellHasFocus) -> {
            JPanel item = new JPanel(new BorderLayout(8, 2));
            item.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            item.setBackground(isSelected ? BG_SELECIONADO : (index % 2 == 0 ? BG_ITEM : BG_MEDIO));

            JLabel nome = new JLabel(serie.getNome());
            nome.setFont(new Font("Arial", Font.BOLD, 13));
            nome.setForeground(TEXTO_CLARO);

            JLabel info = new JLabel(
                serie.getStatus() + "  •  Estreia: " + serie.getEstreia() +
                "  •  Fim: " + serie.getFim() + "  •  Nota: " + String.format("%.1f", serie.getNota())
            );
            info.setFont(new Font("Arial", Font.PLAIN, 11));
            info.setForeground(TEXTO_SECUNDARIO);

            item.add(nome, BorderLayout.CENTER);
            item.add(info, BorderLayout.SOUTH);
            return item;
        });

        jList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) exibirDetalhes(jList.getSelectedValue());
            }
        });

        JScrollPane scroll = new JScrollPane(jList);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 80)));
        scroll.getViewport().setBackground(BG_MEDIO);

        JButton btnRemover = criarBotao("Remover da lista", new Color(160, 50, 50));
        btnRemover.addActionListener(e -> removerDaLista(tipoLista, jList, modelo));

        painel.add(scroll, BorderLayout.CENTER);
        painel.add(btnRemover, BorderLayout.SOUTH);
        return painel;
    }

    private void carregarListas() {
        carregarModelo(modeloFavoritos,     usuarioService.getUsuario().getFavoritos());
        carregarModelo(modeloAssistidas,    usuarioService.getUsuario().getAssistidos());
        carregarModelo(modeloQueroAssistir, usuarioService.getUsuario().getAssistir());
    }

    private void carregarModelo(DefaultListModel<Serie> modelo, List<Serie> series) {
        modelo.clear();
        for (Serie s : series) modelo.addElement(s);
    }

    private void removerDaLista(String tipoLista, JList<Serie> jList, DefaultListModel<Serie> modelo) {
        Serie serie = jList.getSelectedValue();
        if (serie == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma série para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        switch (tipoLista) {
            case "favoritos"     -> usuarioService.removerFavorito(serie);
            case "assistidas"    -> usuarioService.removerAssistidos(serie);
            case "queroAssistir" -> usuarioService.removerAssistir(serie);
        }
        modelo.removeElement(serie);
        JOptionPane.showMessageDialog(this, "\"" + serie.getNome() + "\" removida da lista.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void ordenarListaAtual(String criterio) {
        int abaSelecionada = abas.getSelectedIndex();
        List<Serie> lista = switch (abaSelecionada) {
            case 0 -> usuarioService.getUsuario().getFavoritos();
            case 1 -> usuarioService.getUsuario().getAssistidos();
            case 2 -> usuarioService.getUsuario().getAssistir();
            default -> null;
        };
        if (lista == null) return;
        switch (criterio) {
            case "nome"   -> usuarioService.ordenarPorNome(lista);
            case "nota"   -> usuarioService.ordenarPorNota(lista);
            case "status" -> usuarioService.ordenarPorStatus(lista);
            case "data"   -> usuarioService.ordenarPorDataEstreia(lista);
        }
        carregarListas();
    }

    private void exibirDetalhes(Serie serie) {
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

    private JButton criarBotaoOrdem(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setBackground(new Color(60, 60, 85));
        btn.setForeground(TEXTO_CLARO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(new Color(80, 80, 110)); }
            @Override public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(new Color(60, 60, 85)); }
        });
        return btn;
    }
}