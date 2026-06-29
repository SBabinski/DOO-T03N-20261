package com.seriestv;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListaPanel extends JPanel {

    private Usuario usuario;
    private PersistenciaJSON persistencia;

    private JTabbedPane abas;
    private DefaultTableModel modeloFav, modeloAss, modeloQuer;

    public ListaPanel(Usuario usuario, PersistenciaJSON persistencia) {
        this.usuario = usuario;
        this.persistencia = persistencia;

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Abas com as 3 listas
        abas = new JTabbedPane();
        modeloFav  = criarModelo();
        modeloAss  = criarModelo();
        modeloQuer = criarModelo();

        abas.addTab("Favoritos",      new JScrollPane(criarTabela(modeloFav)));
        abas.addTab("Ja Assistidas",  new JScrollPane(criarTabela(modeloAss)));
        abas.addTab("Quero Assistir", new JScrollPane(criarTabela(modeloQuer)));
        add(abas, BorderLayout.CENTER);

        // Botoes de ordenar e remover
        JButton btnNome    = new JButton("Ordenar por Nome");
        JButton btnNota    = new JButton("Ordenar por Nota");
        JButton btnStatus  = new JButton("Ordenar por Status");
        JButton btnEstreia = new JButton("Ordenar por Estreia");
        JButton btnRemover = new JButton("Remover");

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rodape.add(btnNome);
        rodape.add(btnNota);
        rodape.add(btnStatus);
        rodape.add(btnEstreia);
        rodape.add(btnRemover);
        add(rodape, BorderLayout.SOUTH);

        // Acoes
        btnNome.addActionListener(e    -> { ordenar("nome");    salvar(); });
        btnNota.addActionListener(e    -> { ordenar("nota");    salvar(); });
        btnStatus.addActionListener(e  -> { ordenar("status");  salvar(); });
        btnEstreia.addActionListener(e -> { ordenar("estreia"); salvar(); });
        btnRemover.addActionListener(e -> remover());

        atualizar();
    }

    private DefaultTableModel criarModelo() {
        String[] cols = {"Imagem", "Nome", "Idioma", "Generos", "Nota", "Status", "Estreia", "Termino", "Emissora"};
        return new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }

            public Class<?> getColumnClass(int coluna) {
                return coluna == 0 ? ImageIcon.class : Object.class;
            }
        };
    }

    private JTable criarTabela(DefaultTableModel modelo) {
        JTable tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        configurarTabelaComImagem(tabela);
        return tabela;
    }

    private void configurarTabelaComImagem(JTable tabela) {
        tabela.setRowHeight(90);
        TableColumn colunaImagem = tabela.getColumnModel().getColumn(0);
        colunaImagem.setMinWidth(70);
        colunaImagem.setPreferredWidth(70);
        colunaImagem.setMaxWidth(80);
        colunaImagem.setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, "", isSelected, hasFocus, row, column
                );
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setIcon(value instanceof Icon ? (Icon) value : null);
                return label;
            }
        });
    }

    private void preencher(DefaultTableModel modelo, List<Serie> lista) {
        modelo.setRowCount(0);
        new SwingWorker<List<Object[]>, Void>() {
            protected List<Object[]> doInBackground() {
                List<Object[]> linhas = new ArrayList<>();
                for (Serie s : lista) {
                    linhas.add(new Object[]{
                            ImagemUtil.carregarCapa(s.getImageUrl()), // rede aqui, fora da EDT
                            s.getName(), s.getLanguage(), s.getGenresFormatted(),
                            s.getRating() > 0 ? s.getRating() : "-",
                            s.getStatus(), s.getPremiered(), s.getEnded(), s.getNetwork()
                    });
                }
                return linhas;
            }
            protected void done() {
                try {
                    for (Object[] linha : get()) modelo.addRow(linha);
                } catch (Exception ignored) {}
            }
        }.execute();
    }

    public void atualizar() {
        preencher(modeloFav,  usuario.getFavoritos());
        preencher(modeloAss,  usuario.getJaAssistidas());
        preencher(modeloQuer, usuario.getQueroAssistir());

        abas.setTitleAt(0, "Favoritos ("      + usuario.getFavoritos().size()    + ")");
        abas.setTitleAt(1, "Ja Assistidas ("  + usuario.getJaAssistidas().size() + ")");
        abas.setTitleAt(2, "Quero Assistir (" + usuario.getQueroAssistir().size()+ ")");
    }

    private void ordenar(String criterio) {
        List<Serie> lista = getListaAtual();
        switch (criterio) {
            case "nome"    -> usuario.ordenarPorNome(lista);
            case "nota"    -> usuario.ordenarPorNota(lista);
            case "status"  -> usuario.ordenarPorStatus(lista);
            case "estreia" -> usuario.ordenarPorEstreia(lista);
        }
        atualizar();
    }

    private void remover() {
        int aba   = abas.getSelectedIndex();
        JTable tabela = getTabelaAtual(aba);
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma serie para remover.");
            return;
        }

        Serie serie = getListaAtual().get(linha);
        int ok = JOptionPane.showConfirmDialog(this,
            "Remover \"" + serie.getName() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (ok == JOptionPane.YES_OPTION) {
            switch (aba) {
                case 0 -> usuario.removerFavorito(serie);
                case 1 -> usuario.removerJaAssistida(serie);
                case 2 -> usuario.removerQueroAssistir(serie);
            }
            atualizar();
            salvar();
        }
    }

    private List<Serie> getListaAtual() {
        return switch (abas.getSelectedIndex()) {
            case 1  -> usuario.getJaAssistidas();
            case 2  -> usuario.getQueroAssistir();
            default -> usuario.getFavoritos();
        };
    }

    private JTable getTabelaAtual(int aba) {
        JScrollPane scroll = (JScrollPane) abas.getComponentAt(aba);
        return (JTable) scroll.getViewport().getView();
    }

    private void salvar() {
        try {
            persistencia.salvar(usuario);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }
}
