import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
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
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Tela principal do sistema

public class TelaPrincipal extends JFrame {
    private static final String[] OPCOES_ORDENACAO = {
            "Nome",
            "Nota geral",
            "Estado da serie",
            "Data de estreia"
    };

    private final DadosSistema dados;
    private final TVMazeClient tvMazeClient = new TVMazeClient();

    private final DefaultListModel<Serie> modeloBusca = new DefaultListModel<Serie>();
    private final DefaultListModel<Serie> modeloFavoritos = new DefaultListModel<Serie>();
    private final DefaultListModel<Serie> modeloAssistidas = new DefaultListModel<Serie>();
    private final DefaultListModel<Serie> modeloDesejo = new DefaultListModel<Serie>();

    private final JList<Serie> listaBusca = new JList<Serie>(modeloBusca);
    private final JList<Serie> listaFavoritos = new JList<Serie>(modeloFavoritos);
    private final JList<Serie> listaAssistidas = new JList<Serie>(modeloAssistidas);
    private final JList<Serie> listaDesejo = new JList<Serie>(modeloDesejo);

    private JTextField campoUsuario;
    private JTextField campoBusca;
    private JTextArea areaDetalhes;
    private JComboBox<String> comboFavoritos;
    private JComboBox<String> comboAssistidas;
    private JComboBox<String> comboDesejo;

    public TelaPrincipal(DadosSistema dados) {
        this.dados = dados;

        setTitle("prova final java series - " + dados.getUsuario());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(900, 560));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                fecharSalvando();
            }
        });

        montarTela();
        atualizarListasSalvas();
        setSize(1000, 620);
        setLocationRelativeTo(null);
    }

    private void montarTela() {
        JPanel raiz = new JPanel(new BorderLayout(10, 10));
        raiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(raiz);

        raiz.add(criarPainelUsuario(), BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("pesquisar", criarAbaBusca());

        comboFavoritos = new JComboBox<String>(OPCOES_ORDENACAO);
        comboAssistidas = new JComboBox<String>(OPCOES_ORDENACAO);
        comboDesejo = new JComboBox<String>(OPCOES_ORDENACAO);

        abas.addTab("Favoritos", criarAbaLista("favoritos", dados.getFavoritos(),
        modeloFavoritos, listaFavoritos, comboFavoritos));
        abas.addTab("Ja assistidas", criarAbaLista("series ja assistidas", dados.getAssistidas(),
        modeloAssistidas, listaAssistidas, comboAssistidas));
        abas.addTab("Desejo assistir", criarAbaLista("series que deseja assistir", dados.getDesejoAssistir(),
        modeloDesejo, listaDesejo, comboDesejo));

        raiz.add(abas, BorderLayout.CENTER);
        raiz.add(criarPainelDetalhes(), BorderLayout.EAST);
    }

    private JPanel criarPainelUsuario() {
        JPanel painel = new JPanel(new BorderLayout(8, 0));
        campoUsuario = new JTextField(dados.getUsuario());
        JButton botaoSalvar = new JButton("Salvar usuario");

        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                salvarUsuario();
            }
        });

        painel.add(new JLabel("Usuario:"), BorderLayout.WEST);
        painel.add(campoUsuario, BorderLayout.CENTER);
        painel.add(botaoSalvar, BorderLayout.EAST);
        return painel;
    }

    private JPanel criarAbaBusca() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));

        JPanel topo = new JPanel(new BorderLayout(8, 0));
        campoBusca = new JTextField();
        JButton botaoBuscar = new JButton("Buscar");
        topo.add(new JLabel("Nome da serie:"), BorderLayout.WEST);
        topo.add(campoBusca, BorderLayout.CENTER);
        topo.add(botaoBuscar, BorderLayout.EAST);
        painel.add(topo, BorderLayout.NORTH);

        conectarSelecaoComDetalhes(listaBusca);
        painel.add(new JScrollPane(listaBusca), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botaoFavorito = new JButton("Adicionar aos favoritos");
        JButton botaoAssistida = new JButton("Adicionar as assistidas");
        JButton botaoDesejo = new JButton("Adicionar a desejo assistir");

        botaoFavorito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                adicionarSerie(dados.getFavoritos(), "favoritos");
            }
        });
        botaoAssistida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                adicionarSerie(dados.getAssistidas(), "series ja assistidas");
            }
        });
        botaoDesejo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                adicionarSerie(dados.getDesejoAssistir(), "series que deseja assistir");
            }
        });

        botoes.add(botaoFavorito);
        botoes.add(botaoAssistida);
        botoes.add(botaoDesejo);
        painel.add(botoes, BorderLayout.SOUTH);

        ActionListener acaoBuscar = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buscarSeries();
            }
        };
        botaoBuscar.addActionListener(acaoBuscar);
        campoBusca.addActionListener(acaoBuscar);

        return painel;
    }

    private JPanel criarAbaLista(final String nomeLista, final List<Serie> listaDados,
                                final DefaultListModel<Serie> modelo, final JList<Serie> listaVisual,
                                final JComboBox<String> comboOrdenacao) {
        JPanel painel = new JPanel(new BorderLayout(8, 8));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botaoRemover = new JButton("Remover selecionada");

        topo.add(new JLabel("Ordenar por:"));
        topo.add(comboOrdenacao);
        topo.add(botaoRemover);
        painel.add(topo, BorderLayout.NORTH);

        conectarSelecaoComDetalhes(listaVisual);
        painel.add(new JScrollPane(listaVisual), BorderLayout.CENTER);

        comboOrdenacao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ordenarLista(listaDados, String.valueOf(comboOrdenacao.getSelectedItem()));
                preencherModelo(modelo, listaDados);
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                removerSerie(listaVisual, listaDados, nomeLista);
            }
        });

        return painel;
    }

    private JScrollPane criarPainelDetalhes() {
        areaDetalhes = new JTextArea("Selecione uma serie para ver os detalhes.");
        areaDetalhes.setEditable(false);
        areaDetalhes.setLineWrap(true);
        areaDetalhes.setWrapStyleWord(true);
        areaDetalhes.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(areaDetalhes);
        scroll.setPreferredSize(new Dimension(330, 100));
        return scroll;
    }

    private void buscarSeries() {
        String nome = campoBusca.getText().trim();
        if (nome.isEmpty()) {
            mostrarAviso("Digite o nome de uma serie que quer buscar");
            return;
        }

        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            List<Serie> resultado = tvMazeClient.buscarSeries(nome);
            preencherModelo(modeloBusca, resultado);

            if (resultado.isEmpty()) {
                areaDetalhes.setText("Nenhuma serie foi encontrada");
            } else {
                listaBusca.setSelectedIndex(0);
            }
        } catch (Exception exception) {
            mostrarErro("Nao foi possivel buscar na API da TVmaze", exception);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void adicionarSerie(List<Serie> listaDestino, String nomeLista) {
        Serie serie = listaBusca.getSelectedValue();
        if (serie == null) {
            mostrarAviso("Selecione uma serie da busca primeiro");
            return;
        }

        if (dados.adicionarNaLista(listaDestino, serie)) {
            atualizarListasSalvas();
            salvarSemFechar();
            mostrarAviso("Serie adicionada em " + nomeLista );
        } else {
            mostrarAviso("Essa serie ja esta na lista");
        }
    }

    private void removerSerie(JList<Serie> listaVisual, List<Serie> listaDados, String nomeLista) {
        Serie serie = listaVisual.getSelectedValue();
        if (serie == null) {
            mostrarAviso("Selecione uma serie para remover");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Remover \"" + serie.getNome() + "\" de " + nomeLista + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION && dados.removerDaLista(listaDados, serie)) {
            atualizarListasSalvas();
            salvarSemFechar();
            areaDetalhes.setText("Serie removida.");
        }
    }

    private void atualizarListasSalvas() {
        ordenarLista(dados.getFavoritos(), comboFavoritos == null ? "Nome" : String.valueOf(comboFavoritos.getSelectedItem()));
        ordenarLista(dados.getAssistidas(), comboAssistidas == null ? "Nome" : String.valueOf(comboAssistidas.getSelectedItem()));
        ordenarLista(dados.getDesejoAssistir(), comboDesejo == null ? "Nome" : String.valueOf(comboDesejo.getSelectedItem()));

        preencherModelo(modeloFavoritos, dados.getFavoritos());
        preencherModelo(modeloAssistidas, dados.getAssistidas());
        preencherModelo(modeloDesejo, dados.getDesejoAssistir());
    }

    private void preencherModelo(DefaultListModel<Serie> modelo, List<Serie> series) {
        modelo.clear();
        for (Serie serie : series) {
            modelo.addElement(serie);
        }
    }

    private void ordenarLista(List<Serie> series, final String criterio) {
        Collections.sort(series, new Comparator<Serie>() {
            public int compare(Serie primeira, Serie segunda) {
                int resultado;

                if ("Nota geral".equals(criterio)) {
                    resultado = Double.compare(segunda.getNotaParaOrdenar(), primeira.getNotaParaOrdenar());
                } else if ("Estado da serie".equals(criterio)) {
                    resultado = primeira.getEstadoTexto().compareToIgnoreCase(segunda.getEstadoTexto());
                } else if ("Data de estreia".equals(criterio)) {
                    resultado = primeira.getEstreiaParaOrdenar().compareTo(segunda.getEstreiaParaOrdenar());
                } else {
                    resultado = primeira.getNome().compareToIgnoreCase(segunda.getNome());
                }

                if (resultado == 0) {
                    resultado = primeira.getNome().compareToIgnoreCase(segunda.getNome());
                }
                return resultado;
            }
        });
    }

    private void conectarSelecaoComDetalhes(final JList<Serie> lista) {
        lista.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    Serie serie = lista.getSelectedValue();
                    if (serie != null) {
                        areaDetalhes.setText(serie.detalhes());
                    }
                }
            }
        });
    }

    private void salvarUsuario() {
        dados.setUsuario(campoUsuario.getText());
        setTitle("prova final java series - " + dados.getUsuario());
        salvarSemFechar();
        mostrarAviso("Usuario salvo.");
    }

    private void salvarSemFechar() {
        try {
            dados.salvar();
        } catch (IOException exception) {
            mostrarErro("Nao foi possivel salvar o JSON.", exception);
        }
    }

    private void fecharSalvando() {
        dados.setUsuario(campoUsuario.getText());
        try {
            dados.salvar();
            dispose();
        } catch (IOException exception) {
            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Nao foi possivel salvar os dados. Deseja sair mesmo assim?",
                    "Erro ao salvar",
                    JOptionPane.YES_NO_OPTION
            );
            if (resposta == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sistema de Series", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarErro(String mensagem, Exception exception) {
        JOptionPane.showMessageDialog(this, mensagem + "\n\n" + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
