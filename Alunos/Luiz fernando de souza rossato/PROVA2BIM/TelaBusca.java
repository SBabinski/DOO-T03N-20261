package tv;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
  
public class TelaBusca extends JFrame {

    private Usuario usuario;

    private JTextField txtBusca;
    private JButton btnBuscar;

    private DefaultListModel<Serie> modeloLista;
    private JList<Serie> listaSeries;

    private JTextArea areaDetalhes;

    private JButton btnFavorito;
    private JButton btnAssistida;
    private JButton btnDesejo;

    public TelaBusca(Usuario usuario) {

        this.usuario = usuario;

        configurarJanela();
        criarComponentes();

        setVisible(true);
    }

    private void configurarJanela() {

        setTitle("Buscar Séries");

        setSize(850, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void criarComponentes() {

        setLayout(new BorderLayout(10,10));

        JPanel painelBusca = new JPanel();

        txtBusca = new JTextField(30);

        btnBuscar = new JButton("Buscar");

        painelBusca.add(new JLabel("Nome:"));
        painelBusca.add(txtBusca);
        painelBusca.add(btnBuscar);

        add(painelBusca, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();

        listaSeries = new JList<>(modeloLista);

        JScrollPane scrollLista =
                new JScrollPane(listaSeries);

        areaDetalhes = new JTextArea();

        areaDetalhes.setEditable(false);

        JScrollPane scrollDetalhes =
                new JScrollPane(areaDetalhes);

        JSplitPane split =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        scrollLista,
                        scrollDetalhes);

        split.setDividerLocation(250);

        add(split, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        btnFavorito =
                new JButton("Favoritos");

        btnAssistida =
                new JButton("Assistidas");

        btnDesejo =
                new JButton("Desejo Assistir");

        painelBotoes.add(btnFavorito);
        painelBotoes.add(btnAssistida);
        painelBotoes.add(btnDesejo);

        add(painelBotoes, BorderLayout.SOUTH);

        eventos();
    }

    private void eventos() {

        btnBuscar.addActionListener(e -> buscar());

        listaSeries.addListSelectionListener(
                (ListSelectionEvent e) -> {

                    if (!e.getValueIsAdjusting()) {

                        mostrarDetalhes();

                    }

                });

        btnFavorito.addActionListener(e -> adicionarFavorito());

        btnAssistida.addActionListener(e -> adicionarAssistida());

        btnDesejo.addActionListener(e -> adicionarDesejo());

    }

    private void buscar() {

        try {

            modeloLista.clear();

            areaDetalhes.setText("");

            String nome =
                    txtBusca.getText().trim();

            if (nome.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Digite o nome da série.");

                return;
            }

            List<Serie> lista =
                    Api.buscarSeries(nome);

            if (lista.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Nenhuma série encontrada.");

                return;
            }

            for (Serie serie : lista) {

                modeloLista.addElement(serie);

            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao consultar a API.");

        }

    }

    private void mostrarDetalhes() {

        Serie serie =
                listaSeries.getSelectedValue();

        if (serie == null)
            return;

        String texto =
                "Nome: " + serie.getNome() +

                "\n\nIdioma: " + serie.getIdioma() +

                "\n\nGêneros: " +
                String.join(", ", serie.getGenero()) +

                "\n\nNota: " + serie.getNota() +

                "\n\nStatus: " + serie.getStatus() +

                "\n\nEstreia: " + serie.getDatadeEstréia() +

                "\n\nTérmino: " + serie.getDataDeEnceramento() +

                "\n\nEmissora: " + serie.getNomeDaEmissora();

        areaDetalhes.setText(texto);

    }

    private void adicionarFavorito() {

        Serie serie =
                listaSeries.getSelectedValue();

        if (serie == null)
            return;

        usuario.adicionarSerieFavoritas(serie);

        JsonManager.salvarUsuario(usuario);

        JOptionPane.showMessageDialog(
                this,
                "Adicionada aos favoritos.");

    }

    private void adicionarAssistida() {

        Serie serie =
                listaSeries.getSelectedValue();

        if (serie == null)
            return;

        usuario.adicionarSeriesAssistida(serie);

        JsonManager.salvarUsuario(usuario);

        JOptionPane.showMessageDialog(
                this,
                "Adicionada às assistidas.");

    }

    private void adicionarDesejo() {

        Serie serie =
                listaSeries.getSelectedValue();

        if (serie == null)
            return;

        usuario.adicionarSeriesParaAssistir(serie);

        JsonManager.salvarUsuario(usuario);

        JOptionPane.showMessageDialog(
                this,
                "Adicionada à lista de desejo.");

    }

}