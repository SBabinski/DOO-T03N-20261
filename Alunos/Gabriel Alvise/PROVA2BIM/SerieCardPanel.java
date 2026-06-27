import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.function.Consumer;

public class SerieCardPanel extends JPanel {
    private static final Color COR_FUNDO_CARD = new Color(32, 32, 32);
    private static final Color COR_FUNDO_CARD_SELECIONADO = new Color(65, 65, 65);
    private static final Color COR_TEXTO_PRINCIPAL = new Color(245, 245, 245);
    private static final Color COR_TEXTO_SECUNDARIO = new Color(180, 180, 180);
    private static final Color COR_DESTAQUE = new Color(229, 9, 20);

    private Serie serie;
    private JLabel labelImagem;
    private JLabel labelNome;
    private JLabel labelInfo;
    private boolean selecionado;

    public SerieCardPanel(Serie serie, Consumer<Serie> aoSelecionarSerie) {
        this.serie = serie;
        this.selecionado = false;

        configurarCard();
        criarComponentes();
        montarLayout();
        configurarEventoClique(aoSelecionarSerie);
        carregarImagem();
    }

    private void configurarCard() {
        setPreferredSize(new Dimension(170, 285));
        setMaximumSize(new Dimension(170, 285));
        setMinimumSize(new Dimension(170, 285));
        setBackground(COR_FUNDO_CARD);
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(50, 50, 50), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void criarComponentes() {
        labelImagem = new JLabel("Carregando...");
        labelImagem.setHorizontalAlignment(SwingConstants.CENTER);
        labelImagem.setVerticalAlignment(SwingConstants.CENTER);
        labelImagem.setPreferredSize(new Dimension(150, 210));
        labelImagem.setOpaque(true);
        labelImagem.setBackground(new Color(20, 20, 20));
        labelImagem.setForeground(COR_TEXTO_SECUNDARIO);
        labelImagem.setFont(new Font("Arial", Font.PLAIN, 12));

        labelNome = new JLabel(limitarTexto(serie.getNome(), 24));
        labelNome.setForeground(COR_TEXTO_PRINCIPAL);
        labelNome.setFont(new Font("Arial", Font.BOLD, 13));
        labelNome.setHorizontalAlignment(SwingConstants.CENTER);
        labelNome.setToolTipText(serie.getNome());

        labelInfo = new JLabel("★ " + serie.getNotaFormatada() + " • " + serie.getEstado());
        labelInfo.setForeground(COR_TEXTO_SECUNDARIO);
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void montarLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        labelImagem.setAlignmentX(CENTER_ALIGNMENT);
        labelNome.setAlignmentX(CENTER_ALIGNMENT);
        labelInfo.setAlignmentX(CENTER_ALIGNMENT);

        add(labelImagem);
        add(javax.swing.Box.createVerticalStrut(8));
        add(labelNome);
        add(javax.swing.Box.createVerticalStrut(4));
        add(labelInfo);
    }

    private void configurarEventoClique(Consumer<Serie> aoSelecionarSerie) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                if (aoSelecionarSerie != null) {
                    aoSelecionarSerie.accept(serie);
                }
            }

            @Override
            public void mouseEntered(MouseEvent evento) {
                if (!selecionado) {
                    setBackground(new Color(45, 45, 45));
                }
            }

            @Override
            public void mouseExited(MouseEvent evento) {
                if (!selecionado) {
                    setBackground(COR_FUNDO_CARD);
                }
            }
        });
    }

    private void carregarImagem() {
        if (!serie.temImagem()) {
            mostrarImagemIndisponivel();
            return;
        }

        new Thread(() -> {
            try {
                Image imagemOriginal = ImageIO.read(new URL(serie.getImagemUrl()));

                if (imagemOriginal == null) {
                    SwingUtilities.invokeLater(this::mostrarImagemIndisponivel);
                    return;
                }

                Image imagemRedimensionada = imagemOriginal.getScaledInstance(
                        150,
                        210,
                        Image.SCALE_SMOOTH
                );

                ImageIcon icone = new ImageIcon(imagemRedimensionada);

                SwingUtilities.invokeLater(() -> {
                    labelImagem.setText("");
                    labelImagem.setIcon(icone);
                });
            } catch (Exception erro) {
                SwingUtilities.invokeLater(this::mostrarImagemIndisponivel);
            }
        }).start();
    }

    private void mostrarImagemIndisponivel() {
        labelImagem.setText("<html><center>Imagem<br>indisponível</center></html>");
        labelImagem.setIcon(null);
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;

        if (selecionado) {
            setBackground(COR_FUNDO_CARD_SELECIONADO);
            setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(COR_DESTAQUE, 2, true),
                    BorderFactory.createEmptyBorder(7, 7, 7, 7)
            ));
        } else {
            setBackground(COR_FUNDO_CARD);
            setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(50, 50, 50), 1, true),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
        }

        repaint();
    }

    public Serie getSerie() {
        return serie;
    }

    private String limitarTexto(String texto, int limite) {
        if (texto == null) {
            return "";
        }

        if (texto.length() <= limite) {
            return texto;
        }

        return texto.substring(0, limite - 3) + "...";
    }
}