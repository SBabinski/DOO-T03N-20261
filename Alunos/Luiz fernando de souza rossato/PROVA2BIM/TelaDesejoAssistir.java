package tv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TelaDesejoAssistir extends JFrame {

    private Usuario usuario;

    private DefaultListModel<Serie> modelo;
    private JList<Serie> lista;

    private JButton btnDetalhes;
    private JButton btnRemover;
    private JButton btnOrdenar;

    public TelaDesejoAssistir(Usuario usuario) {

        this.usuario = usuario;

        configurarJanela();
        criarComponentes();

        setVisible(true);
    }

    private void configurarJanela() {

        setTitle("Séries que Desejo Assistir");

        setSize(700, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int op = JOptionPane.showConfirmDialog(
                        TelaDesejoAssistir.this,
                        "Deseja fechar esta janela?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (op == JOptionPane.YES_OPTION) {

                    dispose();

                }

            }

        });

    }

    private void criarComponentes() {

        setLayout(new BorderLayout());

        modelo = new DefaultListModel<>();

        lista = new JList<>(modelo);

        atualizarLista();

        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel painel = new JPanel(new FlowLayout());

        btnDetalhes = new JButton("Ver Detalhes");

        btnRemover = new JButton("Remover");

        btnOrdenar = new JButton("Ordenar");

        painel.add(btnDetalhes);
        painel.add(btnRemover);
        painel.add(btnOrdenar);

        add(painel, BorderLayout.SOUTH);

        eventos();

    }

    private void eventos() {

        btnDetalhes.addActionListener(e -> mostrarDetalhes());

        btnRemover.addActionListener(e -> removerSerie());

        btnOrdenar.addActionListener(e -> ordenar());

    }

    private void atualizarLista() {

        modelo.clear();

        for (Serie s : usuario.getseriesParaAssistir()) {

            modelo.addElement(s);

        }

    }

    private void mostrarDetalhes() {

        Serie serie = lista.getSelectedValue();

        if (serie == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma série.");

            return;

        }

        JTextArea area = new JTextArea();

        area.setEditable(false);

        area.setText(
                "Nome: " + serie.getNome()
                + "\n\nIdioma: " + serie.getIdioma()
                + "\n\nGêneros: " + String.join(", ", serie.getGenero())
                + "\n\nNota: " + serie.getNota()
                + "\n\nStatus: " + serie.getStatus()
                + "\n\nEstreia: " + serie.getDatadeEstréia()
                + "\n\nTérmino: " + serie.getDataDeEnceramento()
                + "\n\nEmissora: " + serie.getNomeDaEmissora());

        JScrollPane scroll = new JScrollPane(area);

        scroll.setPreferredSize(new Dimension(400,300));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Detalhes",
                JOptionPane.INFORMATION_MESSAGE);

    }

    private void removerSerie() {

        Serie serie = lista.getSelectedValue();

        if (serie == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma série.");

            return;

        }

        int op = JOptionPane.showConfirmDialog(
                this,
                "Remover esta série da lista de desejo?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (op == JOptionPane.YES_OPTION) {

            usuario.removerSeriesParaAssistir(serie);

            JsonManager.salvarUsuario(usuario);

            atualizarLista();

        }

    }

    private void ordenar() {

        String[] opcoes = {
                "Nome",
                "Nota",
                "Status",
                "Data de Estreia"
        };

        String escolha = (String) JOptionPane.showInputDialog(
                this,
                "Ordenar por:",
                "Ordenação",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (escolha == null) {

            return;

        }

        switch (escolha) {

            case "Nome":
                OrdenadorSerie.ordenarPorNome(usuario.getseriesParaAssistir());
                break;

            case "Nota":
                OrdenadorSerie.ordenarPorNota(usuario.getseriesParaAssistir());
                break;

            case "Status":
                OrdenadorSerie.ordenarPorStatus(usuario.getseriesParaAssistir());
                break;

            case "Data de Estreia":
                OrdenadorSerie.ordenarPorDataDeEstreia(usuario.getseriesParaAssistir());
                break;

        }

        JsonManager.salvarUsuario(usuario);

        atualizarLista();

    }

}