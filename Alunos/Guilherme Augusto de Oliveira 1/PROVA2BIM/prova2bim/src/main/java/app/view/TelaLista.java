package app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.model.Serie;
import app.service.OrdenacaoService;
import app.service.UsuarioService;

public class TelaLista extends JFrame {

    private DefaultListModel<Serie> modeloLista;

    private JList<Serie> listaSeries;

    private final List<Serie> series;

    private final UsuarioService usuarioService;

    private final String tituloTela;

    public TelaLista(String titulo, List<Serie> series) {

        this.series = series;
        this.tituloTela = titulo;
        this.usuarioService = UsuarioService.getInstance();

        setTitle(titulo);

        setSize(720,650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();

        setVisible(true);

    }

    private void inicializarComponentes(){

        Color fundo = new Color(240,242,245);

        JPanel principal = new JPanel(new BorderLayout());

        principal.setBackground(fundo);

        principal.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel topo = new JPanel();

        topo.setBackground(Color.WHITE);

        topo.setLayout(new BoxLayout(topo,BoxLayout.Y_AXIS));

        topo.setBorder(BorderFactory.createEmptyBorder(25,30,25,30));

        JLabel titulo = new JLabel(tituloTela);

        titulo.setAlignmentX(CENTER_ALIGNMENT);

        titulo.setFont(new Font("Segoe UI",Font.BOLD,30));

        JLabel quantidade = new JLabel(series.size()+" série(s)");

        quantidade.setAlignmentX(CENTER_ALIGNMENT);

        quantidade.setForeground(Color.GRAY);

        quantidade.setFont(new Font("Segoe UI",Font.PLAIN,14));

        topo.add(titulo);

        topo.add(Box.createVerticalStrut(5));

        topo.add(quantidade);

        topo.add(Box.createVerticalStrut(20));

        principal.add(topo,BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();

        atualizarLista();

        listaSeries = new JList<>(modeloLista);

        listaSeries.setCellRenderer(new SerieRenderer());

        listaSeries.setFixedCellHeight(70);

        listaSeries.addMouseListener(new MouseAdapter(){

            @Override

            public void mouseClicked(MouseEvent e){

                if(e.getClickCount()==2){

                    abrirDetalhes();

                }

            }

        });

        JScrollPane scroll = new JScrollPane(listaSeries);

        scroll.setBorder(BorderFactory.createEmptyBorder());

        principal.add(scroll,BorderLayout.CENTER);

        JPanel botoes = new JPanel(new GridLayout(1,4,10,0));

        botoes.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));

        botoes.setOpaque(false);

        JButton btnDetalhes = criarBotao("Detalhes");

        JButton btnRemover = criarBotao("Remover");

        JButton btnOrdenar = criarBotao("Ordenar");

        JButton btnFechar = criarBotao("Fechar");

        botoes.add(btnDetalhes);

        botoes.add(btnRemover);

        botoes.add(btnOrdenar);

        botoes.add(btnFechar);

        principal.add(botoes,BorderLayout.SOUTH);

        add(principal);

        btnDetalhes.addActionListener(e->abrirDetalhes());

        btnRemover.addActionListener(e->removerSerie());

        btnOrdenar.addActionListener(e->ordenarLista());

        btnFechar.addActionListener(e->dispose());

    }

    private JButton criarBotao(String texto){

        JButton botao = new JButton(texto);

        botao.setBackground(new Color(33,150,243));

        botao.setForeground(Color.WHITE);

        botao.setFocusPainted(false);

        botao.setFont(new Font("Segoe UI",Font.BOLD,15));

        botao.setPreferredSize(new Dimension(150,45));

        return botao;

    }    private void abrirDetalhes() {

        Serie serie = listaSeries.getSelectedValue();

        if (serie == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma série.");

            return;

        }

        new TelaDetalhes(serie);

    }

    private void removerSerie() {

        Serie serie = listaSeries.getSelectedValue();

        if (serie == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma série.");

            return;

        }

        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja remover \"" + serie.getNome() + "\"?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (opcao != JOptionPane.YES_OPTION) {

            return;

        }

        if (series == usuarioService.getUsuario().getFavoritos()) {

            usuarioService.removerFavorito(serie);

        } else if (series == usuarioService.getUsuario().getAssistidas()) {

            usuarioService.removerAssistida(serie);

        } else {

            usuarioService.removerDesejo(serie);

        }

        atualizarLista();

    }

    private void ordenarLista() {

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

                OrdenacaoService.ordenarPorNome(series);

                break;

            case "Nota":

                OrdenacaoService.ordenarPorNota(series);

                break;

            case "Status":

                OrdenacaoService.ordenarPorStatus(series);

                break;

            case "Data de Estreia":

                OrdenacaoService.ordenarPorEstreia(series);

                break;

        }

        atualizarLista();

    }

    private void atualizarLista() {

        modeloLista.clear();

        for (Serie serie : series) {

            modeloLista.addElement(serie);

        }

    }

}