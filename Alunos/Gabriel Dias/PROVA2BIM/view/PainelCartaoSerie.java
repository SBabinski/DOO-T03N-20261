package view;

import model.Serie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Componente visual de cartão para exibir uma série.
 * Mostra a capa (thumbnail), nome, nota, gêneros, estado e emissora
 * em um layout de cartão clicável.
 */
public class PainelCartaoSerie extends JPanel {

    private static final int LARGURA_CAPA = 72;
    private static final int ALTURA_CAPA = 100;
    private static final Color COR_FUNDO = new Color(255, 255, 255);
    private static final Color COR_FUNDO_HOVER = new Color(235, 240, 255);
    private static final Color COR_BORDA = new Color(210, 215, 230);
    private static final Color COR_BORDA_HOVER = new Color(25, 25, 112);
    private static final Color COR_TITULO = new Color(20, 20, 90);
    private static final Color COR_ROTULO = new Color(100, 100, 140);
    private static final Color COR_VALOR = new Color(40, 40, 60);

    private final Serie serie;
    private Runnable acaoClique;
    private JLabel labelCapa;
    private boolean imagemCarregada = false;

    /**
     * Cria o cartão para a série informada.
     *
     * @param serie série a exibir
     */
    public PainelCartaoSerie(Serie serie) {
        this.serie = serie;
        construirInterface();
        configurarInteratividade();
    }

    /**
     * Define a ação a executar quando o cartão for clicado duas vezes.
     *
     * @param acao runnable de clique
     */
    public void definirAcaoClique(Runnable acao) {
        this.acaoClique = acao;
    }

    /**
     * Monta os componentes visuais do cartão.
     */
    private void construirInterface() {
        setLayout(new BorderLayout(12, 0));
        setBackground(COR_FUNDO);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1, true),
                new EmptyBorder(10, 10, 10, 14)
        ));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // --- Capa ---
        labelCapa = new JLabel();
        labelCapa.setPreferredSize(new Dimension(LARGURA_CAPA, ALTURA_CAPA));
        labelCapa.setMinimumSize(new Dimension(LARGURA_CAPA, ALTURA_CAPA));
        labelCapa.setMaximumSize(new Dimension(LARGURA_CAPA, ALTURA_CAPA));
        labelCapa.setHorizontalAlignment(SwingConstants.CENTER);
        labelCapa.setVerticalAlignment(SwingConstants.CENTER);
        labelCapa.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 210), 1));
        labelCapa.setBackground(new Color(230, 230, 240));
        labelCapa.setOpaque(true);
        labelCapa.setText("<html><center><font color='#999999' size='2'>sem<br>capa</font></center></html>");
        carregarImagemAsync();

        // --- Informações ---
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setOpaque(false);

        // Nome
        JLabel lblNome = new JLabel(serie.obterNome());
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNome.setForeground(COR_TITULO);
        lblNome.setAlignmentX(LEFT_ALIGNMENT);

        // Linha: nota + estado
        JPanel linhaNota = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        linhaNota.setOpaque(false);
        linhaNota.setAlignmentX(LEFT_ALIGNMENT);

        if (serie.obterNota() > 0) {
            JLabel lblNota = criarPilula(
                    String.format("★ %.1f", serie.obterNota()),
                    new Color(255, 193, 7), new Color(80, 50, 0)
            );
            linhaNota.add(lblNota);
            linhaNota.add(Box.createHorizontalStrut(6));
        }

        String estado = serie.obterEstado() != null && !serie.obterEstado().isEmpty()
                ? serie.obterEstado() : null;
        if (estado != null) {
            Color corEstado = estado.equals("Em exibição")
                    ? new Color(34, 139, 34) : new Color(120, 120, 120);
            JLabel lblEstado = criarPilula(estado, corEstado, Color.WHITE);
            linhaNota.add(lblEstado);
        }

        // Gêneros
        JLabel lblGeneros = criarCampoInfo("Gêneros", emptyParaDash(serie.obterGeneros()));

        // Idioma + Emissora na mesma linha
        JPanel linhaMetadados = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        linhaMetadados.setOpaque(false);
        linhaMetadados.setAlignmentX(LEFT_ALIGNMENT);
        linhaMetadados.add(criarCampoInline("Idioma", emptyParaDash(serie.obterIdioma())));
        linhaMetadados.add(Box.createHorizontalStrut(16));
        linhaMetadados.add(criarCampoInline("Estreia", formatarData(serie.obterDataEstreia())));
        linhaMetadados.add(Box.createHorizontalStrut(16));
        linhaMetadados.add(criarCampoInline("Emissora", emptyParaDash(serie.obterEmissora())));

        painelInfo.add(lblNome);
        painelInfo.add(Box.createVerticalStrut(5));
        painelInfo.add(linhaNota);
        painelInfo.add(Box.createVerticalStrut(4));
        painelInfo.add(lblGeneros);
        painelInfo.add(Box.createVerticalStrut(2));
        painelInfo.add(linhaMetadados);

        JLabel dica = new JLabel("Clique duas vezes para ver detalhes e adicionar às listas");
        dica.setFont(new Font("SansSerif", Font.ITALIC, 10));
        dica.setForeground(new Color(160, 160, 180));
        dica.setAlignmentX(LEFT_ALIGNMENT);
        painelInfo.add(Box.createVerticalStrut(4));
        painelInfo.add(dica);

        add(labelCapa, BorderLayout.WEST);
        add(painelInfo, BorderLayout.CENTER);
    }

    /**
     * Configura eventos de hover e clique.
     */
    private void configurarInteratividade() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(COR_FUNDO_HOVER);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_BORDA_HOVER, 2, true),
                        new EmptyBorder(9, 9, 9, 13)
                ));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(COR_FUNDO);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1, true),
                        new EmptyBorder(10, 10, 10, 14)
                ));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && acaoClique != null) {
                    acaoClique.run();
                }
            }
        });
    }

    /**
     * Carrega a imagem da capa de forma assíncrona para não travar a UI.
     */
    private void carregarImagemAsync() {
        String url = serie.obterUrlImagem();
        if (url == null || url.isEmpty()) return;

        new Thread(() -> {
            try {
                java.net.URL urlImagem = new java.net.URL(url);
                java.awt.image.BufferedImage imgOriginal =
                        javax.imageio.ImageIO.read(urlImagem);
                if (imgOriginal == null) return;

                Image imgRedimensionada = imgOriginal.getScaledInstance(
                        LARGURA_CAPA, ALTURA_CAPA, Image.SCALE_SMOOTH);
                ImageIcon icone = new ImageIcon(imgRedimensionada);

                SwingUtilities.invokeLater(() -> {
                    labelCapa.setText("");
                    labelCapa.setIcon(icone);
                    imagemCarregada = true;
                });
            } catch (Exception ignored) {
                // Mantém o placeholder se a imagem falhar
            }
        }).start();
    }

    /**
     * Cria um "badge" colorido (pequena pílula com texto e fundo colorido).
     */
    private JLabel criarPilula(String texto, Color corFundo, Color corTexto) {
        JLabel label = new JLabel(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(corFundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        label.setForeground(corTexto);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        label.setBorder(new EmptyBorder(2, 7, 2, 7));
        label.setOpaque(false);
        return label;
    }

    /**
     * Cria um rótulo de campo com label cinza e valor escuro, em formato bloco.
     */
    private JLabel criarCampoInfo(String rotulo, String valor) {
        JLabel label = new JLabel("<html><font color='#6464A0'>" + rotulo + ": </font>"
                + "<font color='#282840'>" + valor + "</font></html>");
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    /**
     * Cria um campo inline compacto para metadados como idioma e emissora.
     */
    private JLabel criarCampoInline(String rotulo, String valor) {
        JLabel label = new JLabel("<html><font color='#9090B8'><i>" + rotulo + "</i></font> "
                + "<font color='#282840'>" + valor + "</font></html>");
        label.setFont(new Font("SansSerif", Font.PLAIN, 11));
        return label;
    }

    /**
     * Formata a data yyyy-MM-dd para dd/MM/yyyy.
     */
    private String formatarData(String data) {
        if (data == null || data.isEmpty()) return "—";
        if (data.length() == 10 && data.charAt(4) == '-') {
            return data.substring(8) + "/" + data.substring(5, 7) + "/" + data.substring(0, 4);
        }
        return data;
    }

    /**
     * Retorna "—" se vazio.
     */
    private String emptyParaDash(String texto) {
        return (texto == null || texto.isEmpty()) ? "—" : texto;
    }

    /**
     * Retorna a série associada a este cartão.
     */
    public Serie obterSerie() {
        return serie;
    }
}
