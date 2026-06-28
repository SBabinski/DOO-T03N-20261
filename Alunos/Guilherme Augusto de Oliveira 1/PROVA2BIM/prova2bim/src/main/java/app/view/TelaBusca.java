package app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JTextField;

import app.model.Serie;
import app.service.ApiService;

public class TelaBusca extends JFrame {

    private JTextField txtBusca;

    private JButton btnPesquisar;

    private DefaultListModel<Serie> modelo;

    private JList<Serie> lista;

    private final ApiService apiService;

    public TelaBusca() {

        apiService = new ApiService();

        setTitle("Buscar Série");

        setSize(720,700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();

        setVisible(true);

    }

    private void inicializarComponentes(){

        Color fundo = new Color(240,242,245);

        Color branco = Color.WHITE;

        JPanel principal = new JPanel(new BorderLayout());

        principal.setBackground(fundo);

        principal.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel topo = new JPanel();

        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));

        topo.setBackground(branco);

        topo.setBorder(BorderFactory.createEmptyBorder(30,35,30,35));

        JLabel titulo = new JLabel("BUSCAR SÉRIE");

        titulo.setAlignmentX(CENTER_ALIGNMENT);

        titulo.setFont(new Font("Segoe UI",Font.BOLD,30));

        JLabel subtitulo = new JLabel("Encontre sua próxima maratona");

        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

        subtitulo.setForeground(Color.GRAY);

        subtitulo.setFont(new Font("Segoe UI",Font.PLAIN,15));

        topo.add(titulo);

        topo.add(Box.createVerticalStrut(6));

        topo.add(subtitulo);

        topo.add(Box.createVerticalStrut(25));

        txtBusca = new JTextField();

        txtBusca.setFont(new Font("Segoe UI",Font.PLAIN,16));

        txtBusca.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));

        topo.add(txtBusca);

        topo.add(Box.createVerticalStrut(18));

        btnPesquisar = new JButton("Pesquisar");

        btnPesquisar.setFont(new Font("Segoe UI",Font.BOLD,16));

        btnPesquisar.setBackground(new Color(33,150,243));

        btnPesquisar.setForeground(Color.WHITE);

        btnPesquisar.setFocusPainted(false);

        btnPesquisar.setAlignmentX(CENTER_ALIGNMENT);

        btnPesquisar.setMaximumSize(new Dimension(220,48));

        topo.add(btnPesquisar);

        topo.add(Box.createVerticalStrut(25));

        principal.add(topo,BorderLayout.NORTH);

        modelo = new DefaultListModel<>();

        lista = new JList<>(modelo);

        lista.setCellRenderer(new SerieRenderer());

        lista.setFixedCellHeight(70);

        lista.setFont(new Font("Segoe UI",Font.PLAIN,15));

        JScrollPane scroll = new JScrollPane(lista);

        scroll.setBorder(BorderFactory.createEmptyBorder());

        principal.add(scroll,BorderLayout.CENTER);

        JLabel rodape = new JLabel("Duplo clique em uma série para visualizar os detalhes.");

        rodape.setHorizontalAlignment(JLabel.CENTER);

        rodape.setFont(new Font("Segoe UI",Font.PLAIN,13));

        rodape.setForeground(Color.GRAY);

        principal.add(rodape,BorderLayout.SOUTH);

        add(principal);

        btnPesquisar.addActionListener(e -> pesquisarSerie());

        txtBusca.addActionListener(e -> pesquisarSerie());

        lista.addMouseListener(new MouseAdapter(){

            @Override

            public void mouseClicked(MouseEvent e){

                if(e.getClickCount()==2){

                    Serie serie = lista.getSelectedValue();

                    if(serie!=null){

                        new TelaDetalhes(serie);

                    }

                }

            }

        });

    }
        private void pesquisarSerie() {

        String nome = txtBusca.getText().trim();

        if (nome.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite o nome de uma série.");

            return;

        }

        try {

            List<Serie> resultados = apiService.buscarSeries(nome);

            modelo.clear();

            if (resultados.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Nenhuma série encontrada.");

                return;

            }

            for (Serie serie : resultados) {

                modelo.addElement(serie);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível conectar à TVMaze.\nVerifique sua conexão com a internet.");

        }

    }

}