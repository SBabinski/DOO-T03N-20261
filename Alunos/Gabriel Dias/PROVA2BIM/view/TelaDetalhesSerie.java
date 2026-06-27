package view;

import exception.ExcecaoUsuario;
import model.Serie;
import model.Usuario;
import service.ServicoSerie;
import util.UtilitarioExcecao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Janela de detalhes de uma série.
 * Layout: capa à esquerda + campos estruturados à direita + botões de lista na base.
 */
public class TelaDetalhesSerie extends JDialog {

    private static final Color COR_FUNDO = new Color(248, 248, 252);
    private static final Color COR_CABECALHO = new Color(25, 25, 112);
    private static final Color COR_ROTULO = new Color(90, 90, 140);
    private static final Color COR_VALOR = new Color(30, 30, 55);
    private static final Color COR_SEPARADOR = new Color(220, 220, 235);

    private final Serie serie;
    private final Usuario usuario;
    private final ServicoSerie servicoSerie;
    private final TelaPrincipal telaPrincipal;

    public TelaDetalhesSerie(Serie serie, Usuario usuario, ServicoSerie servicoSerie, TelaPrincipal telaPrincipal) {
        super((Frame) null, serie.obterNome(), true);
        this.serie = serie;
        this.usuario = usuario;
        this.servicoSerie = servicoSerie;
        this.telaPrincipal = telaPrincipal;
        construirInterface();
        pack();
        setMinimumSize(new Dimension(640, 420));
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void construirInterface() {
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(0, 0));

        // ── Faixa do título ──────────────────────────────────────────────────
        JPanel faixaTitulo = new JPanel(new BorderLayout());
        faixaTitulo.setBackground(COR_CABECALHO);
        faixaTitulo.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel lblTitulo = new JLabel(serie.obterNome());
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        String subTitulo = montarSubtitulo();
        JLabel lblSub = new JLabel(subTitulo);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSub.setForeground(new Color(190, 200, 240));

        JPanel painelTextosTitulo = new JPanel(new BorderLayout(0, 2));
        painelTextosTitulo.setOpaque(false);
        painelTextosTitulo.add(lblTitulo, BorderLayout.CENTER);
        painelTextosTitulo.add(lblSub, BorderLayout.SOUTH);
        faixaTitulo.add(painelTextosTitulo, BorderLayout.CENTER);

        // ── Corpo: capa + campos ─────────────────────────────────────────────
        JPanel corpoExterno = new JPanel(new BorderLayout(0, 0));
        corpoExterno.setBackground(COR_FUNDO);
        corpoExterno.setBorder(new EmptyBorder(20, 20, 12, 20));

        // Capa (esquerda)
        JLabel labelCapa = new JLabel();
        labelCapa.setPreferredSize(new Dimension(130, 190));
        labelCapa.setMinimumSize(new Dimension(130, 190));
        labelCapa.setMaximumSize(new Dimension(130, 190));
        labelCapa.setHorizontalAlignment(SwingConstants.CENTER);
        labelCapa.setVerticalAlignment(SwingConstants.CENTER);
        labelCapa.setBackground(new Color(210, 212, 230));
        labelCapa.setOpaque(true);
        labelCapa.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 210), 1));
        labelCapa.setText("<html><center><font color='#888888'>sem<br>capa</font></center></html>");
        carregarImagemAsync(labelCapa);

        JPanel wrapCapa = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapCapa.setOpaque(false);
        wrapCapa.setBorder(new EmptyBorder(0, 0, 0, 20));
        wrapCapa.add(labelCapa);

        // Campos (direita)
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 0, 5, 0);

        int linha = 0;
        linha = adicionarCampo(painelCampos, gbc, linha, "Idioma",
                emptyParaDash(serie.obterIdioma()));
        linha = adicionarCampo(painelCampos, gbc, linha, "Gêneros",
                emptyParaDash(serie.obterGeneros()));
        linha = adicionarCampo(painelCampos, gbc, linha, "Nota",
                serie.obterNota() > 0 ? String.format("★ %.1f / 10", serie.obterNota()) : "—");
        linha = adicionarCampo(painelCampos, gbc, linha, "Estado",
                emptyParaDash(serie.obterEstado()));
        linha = adicionarCampo(painelCampos, gbc, linha, "Data de estreia",
                formatarData(serie.obterDataEstreia()));
        linha = adicionarCampo(painelCampos, gbc, linha, "Data de término",
                formatarData(serie.obterDataTermino()));
        linha = adicionarCampo(painelCampos, gbc, linha, "Emissora",
                emptyParaDash(serie.obterEmissora()));

        // Separador antes do resumo
        if (serie.obterResumo() != null && !serie.obterResumo().isEmpty()) {
            JSeparator sep = new JSeparator();
            sep.setForeground(COR_SEPARADOR);
            gbc.gridx = 0; gbc.gridy = linha++;
            gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(8, 0, 8, 0);
            painelCampos.add(sep, gbc);
            gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(5, 0, 5, 0);

            // Rótulo "Resumo"
            gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0;
            JLabel rotResumo = new JLabel("Resumo");
            rotResumo.setFont(new Font("SansSerif", Font.BOLD, 12));
            rotResumo.setForeground(COR_ROTULO);
            rotResumo.setBorder(new EmptyBorder(0, 0, 0, 16));
            painelCampos.add(rotResumo, gbc);

            // Texto do resumo
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            JTextArea areaResumo = new JTextArea(serie.obterResumo());
            areaResumo.setFont(new Font("SansSerif", Font.PLAIN, 12));
            areaResumo.setForeground(COR_VALOR);
            areaResumo.setEditable(false);
            areaResumo.setLineWrap(true);
            areaResumo.setWrapStyleWord(true);
            areaResumo.setBackground(COR_FUNDO);
            areaResumo.setOpaque(false);
            areaResumo.setRows(4);
            areaResumo.setBorder(null);
            painelCampos.add(areaResumo, gbc);
        }

        // Espaçador no fim para empurrar tudo para cima
        gbc.gridx = 0; gbc.gridy = linha + 1;
        gbc.weighty = 1.0; gbc.gridwidth = 2;
        painelCampos.add(Box.createGlue(), gbc);

        corpoExterno.add(wrapCapa, BorderLayout.WEST);
        corpoExterno.add(painelCampos, BorderLayout.CENTER);

        // ── Rodapé com botões de lista ───────────────────────────────────────
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        rodape.setBackground(new Color(238, 239, 248));
        rodape.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COR_SEPARADOR));

        JButton btnFav = criarBotao("⭐  Favoritos", new Color(220, 140, 0));
        JButton btnAssistida = criarBotao("✅  Já Assisti", new Color(34, 130, 34));
        JButton btnDeseja = criarBotao("🕐  Quero Assistir", new Color(50, 110, 190));
        JButton btnFechar = criarBotao("Fechar", new Color(110, 110, 130));

        btnFav.addActionListener(e -> adicionarALista("favoritos"));
        btnAssistida.addActionListener(e -> adicionarALista("assistidas"));
        btnDeseja.addActionListener(e -> adicionarALista("desejaAssistir"));
        btnFechar.addActionListener(e -> dispose());

        rodape.add(btnFav);
        rodape.add(btnAssistida);
        rodape.add(btnDeseja);
        rodape.add(btnFechar);

        add(faixaTitulo, BorderLayout.NORTH);
        add(corpoExterno, BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);
    }

    /**
     * Adiciona uma linha rótulo + valor ao painel com GridBagLayout.
     * Retorna o próximo índice de linha disponível.
     */
    private int adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha,
                                String rotulo, String valor) {
        // Coluna 0: rótulo
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblRot = new JLabel(rotulo);
        lblRot.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblRot.setForeground(COR_ROTULO);
        lblRot.setBorder(new EmptyBorder(0, 0, 0, 16));
        painel.add(lblRot, gbc);

        // Coluna 1: valor
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JLabel lblVal = new JLabel(valor);
        lblVal.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblVal.setForeground(COR_VALOR);
        painel.add(lblVal, gbc);

        return linha + 1;
    }

    /**
     * Monta a linha de subtítulo com estado e ano de estreia.
     */
    private String montarSubtitulo() {
        StringBuilder sb = new StringBuilder();
        String estado = serie.obterEstado();
        if (estado != null && !estado.isEmpty()) sb.append(estado);
        String estreia = serie.obterDataEstreia();
        if (estreia != null && estreia.length() >= 4) {
            if (sb.length() > 0) sb.append("  ·  ");
            sb.append(estreia.substring(0, 4));
        }
        String idioma = serie.obterIdioma();
        if (idioma != null && !idioma.isEmpty()) {
            if (sb.length() > 0) sb.append("  ·  ");
            sb.append(idioma);
        }
        return sb.length() > 0 ? sb.toString() : " ";
    }

    /**
     * Carrega a imagem da capa de forma assíncrona.
     */
    private void carregarImagemAsync(JLabel destino) {
        String url = serie.obterUrlImagem();
        if (url == null || url.isEmpty()) return;
        new Thread(() -> {
            try {
                java.awt.image.BufferedImage img =
                        javax.imageio.ImageIO.read(new java.net.URL(url));
                if (img == null) return;
                Image redim = img.getScaledInstance(130, 190, Image.SCALE_SMOOTH);
                SwingUtilities.invokeLater(() -> {
                    destino.setText("");
                    destino.setIcon(new ImageIcon(redim));
                });
            } catch (Exception ignored) {}
        }).start();
    }

    /**
     * Adiciona a série à lista especificada.
     */
    private void adicionarALista(String lista) {
        try {
            switch (lista) {
                case "favoritos":
                    servicoSerie.adicionarFavorito(usuario, serie);
                    UtilitarioExcecao.exibirInformacao(this,
                            "\"" + serie.obterNome() + "\" adicionada aos Favoritos!");
                    break;
                case "assistidas":
                    servicoSerie.adicionarJaAssistida(usuario, serie);
                    UtilitarioExcecao.exibirInformacao(this,
                            "\"" + serie.obterNome() + "\" adicionada em Já Assistidas!");
                    break;
                case "desejaAssistir":
                    servicoSerie.adicionarDesejaAssistir(usuario, serie);
                    UtilitarioExcecao.exibirInformacao(this,
                            "\"" + serie.obterNome() + "\" adicionada em Deseja Assistir!");
                    break;
            }
        } catch (ExcecaoUsuario e) {
            UtilitarioExcecao.exibirAviso(this, e.getMessage());
        }
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton b = new JButton(texto);
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setPreferredSize(new Dimension(155, 36));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private String formatarData(String data) {
        if (data == null || data.isEmpty()) return "—";
        if (data.length() == 10 && data.charAt(4) == '-') {
            return data.substring(8) + "/" + data.substring(5, 7) + "/" + data.substring(0, 4);
        }
        return data;
    }

    private String emptyParaDash(String texto) {
        return (texto == null || texto.isEmpty()) ? "—" : texto;
    }
}
