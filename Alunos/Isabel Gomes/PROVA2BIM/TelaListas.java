package fag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class TelaListas extends JFrame {
	
	private Sistema sistema;

    private JTabbedPane abas;

    private JList<Serie> listaFavoritos;
    private JList<Serie> listaAssistidas;
    private JList<Serie> listaDesejos;

    private DefaultListModel<Serie> modeloFavoritos;
    private DefaultListModel<Serie> modeloAssistidas;
    private DefaultListModel<Serie> modeloDesejos;

    private JButton btnRemover;

    private JComboBox<String> comboOrdenar;
    private JButton btnOrdenar;

    private JTextArea txtDetalhes;

    public TelaListas(Sistema sistema) {

        this.sistema = sistema;

        setTitle("Minhas Listas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        carregarListas();

        setVisible(true);
    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        // titulo

        JPanel painelNorte = new JPanel(new BorderLayout());
        painelNorte.setBackground(new Color(45, 62, 80));

        JLabel lblTitulo = new JLabel("MINHAS LISTAS");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);

        painelNorte.add(lblTitulo, BorderLayout.CENTER);
        add(painelNorte, BorderLayout.NORTH);

        // modelos
        modeloFavoritos = new DefaultListModel<>();
        modeloAssistidas = new DefaultListModel<>();
        modeloDesejos = new DefaultListModel<>();

        // listas
        listaFavoritos = new JList<>(modeloFavoritos);
        listaAssistidas = new JList<>(modeloAssistidas);
        listaDesejos = new JList<>(modeloDesejos);

        // detalhes
        txtDetalhes = new JTextArea();
        txtDetalhes.setEditable(false);

        JScrollPane scrollDetalhes = new JScrollPane(txtDetalhes);

        scrollDetalhes.setPreferredSize(
                new java.awt.Dimension(350, 0)
        );

        add(scrollDetalhes, BorderLayout.EAST);
        // abas
        abas = new JTabbedPane();
        abas.addTab("Favoritos", new JScrollPane(listaFavoritos));
        abas.addTab("Assistidas", new JScrollPane(listaAssistidas));
        abas.addTab("Desejos", new JScrollPane(listaDesejos));

        add(abas, BorderLayout.CENTER);

        // botao remover
        btnRemover = new JButton("Remover Série");
        btnRemover.addActionListener(e -> removerSerie());

        // ordenaca9o
        comboOrdenar = new JComboBox<>();
        comboOrdenar.addItem("Nome");
        comboOrdenar.addItem("Nota");
        comboOrdenar.addItem("Status");
        comboOrdenar.addItem("Data de estreia");

        btnOrdenar = new JButton("Ordenar");
        btnOrdenar.addActionListener(e -> ordenarAtual());

        JPanel painelOrdenacao = new JPanel();
        painelOrdenacao.add(comboOrdenar);
        painelOrdenacao.add(btnOrdenar);

        // painelbotoes
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnRemover);

        //painel dos botoes
        JPanel painelSul = new JPanel(new BorderLayout());
        painelSul.add(painelOrdenacao, BorderLayout.NORTH);
        painelSul.add(painelBotoes, BorderLayout.SOUTH);

        add(painelSul, BorderLayout.SOUTH);

        // detalhes da lista
        listaFavoritos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                mostrarDetalhes(listaFavoritos.getSelectedValue());
        });

        listaAssistidas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                mostrarDetalhes(listaAssistidas.getSelectedValue());
        });

        listaDesejos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                mostrarDetalhes(listaDesejos.getSelectedValue());
        });
    }

    //carrega listas
    private void carregarListas() {

        modeloFavoritos.clear();
        modeloAssistidas.clear();
        modeloDesejos.clear();

        for (Serie s : sistema.getUsuario().getFavoritos())
            modeloFavoritos.addElement(s);

        for (Serie s : sistema.getUsuario().getAssistidas())
            modeloAssistidas.addElement(s);

        for (Serie s : sistema.getUsuario().getDesejaAssistir())
            modeloDesejos.addElement(s);
    }

    // remover
    private void removerSerie() {

        int aba = abas.getSelectedIndex();
        Serie s = null;

        if (aba == 0) {
            s = listaFavoritos.getSelectedValue();
            if (s == null) {
            	JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma série antes de remover.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
            	return;
            }
            sistema.removerFavorito(s);

        } else if (aba == 1) {
            s = listaAssistidas.getSelectedValue();
            if (s == null) {
            	JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma série antes de remover.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
            	return;
            }
            sistema.removerAssistida(s);

        } else {
            s = listaDesejos.getSelectedValue();
            if (s == null) {
            	JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma série antes de remover.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
            	return;
            }
            sistema.removerDesejaAssistir(s);
        }

        carregarListas();
        
        JOptionPane.showMessageDialog(
                this,
                "Série removida com sucesso!",
                "AVISO",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ordenar
    private void ordenarAtual() {

        int aba = abas.getSelectedIndex();
        List<Serie> lista;

        if (aba == 0)
            lista = sistema.getUsuario().getFavoritos();
        else if (aba == 1)
            lista = sistema.getUsuario().getAssistidas();
        else
            lista = sistema.getUsuario().getDesejaAssistir();

        String opcao = (String) comboOrdenar.getSelectedItem();

        switch (opcao) {

            case "Nome":
                sistema.getUsuarioService().ordenarPorNome(lista);
                break;

            case "Nota":
                sistema.getUsuarioService().ordenarPorNota(lista);
                break;

            case "Status":
                sistema.getUsuarioService().ordenarPorStatus(lista);
                break;

            case "Data de estreia":
                sistema.getUsuarioService().ordenarPorDataEstreia(lista);
                break;
        }

        carregarListas();
    }

    // detalhes
    private void mostrarDetalhes(Serie s) {

        if (s == null) return;

        txtDetalhes.setText(
            "Nome: " + s.getNome() +
            "\nNota: " + s.getNotaGeral() +
            "\nIdioma: " + s.getIdioma() +
            "\nStatus: " + s.getStatus() +
            "\nEstreia: " + s.getDataInicio() +
            "\nFim: " + s.getDataFim() +
            "\nEmissora: " + s.getEmissora() +
            "\nGêneros: " + s.getGeneros()
        );
    }
	
	
	
	
	
	
}
