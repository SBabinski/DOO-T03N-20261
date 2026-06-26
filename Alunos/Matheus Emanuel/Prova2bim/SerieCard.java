import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

/**
 * Cartao visual de uma serie (poster + nome + nota/estado), no estilo dos
 * "tiles" da Netflix. Clicar no cartao dispara um callback (abrir detalhes).
 */
public class SerieCard extends JPanel {

    static final int LARGURA = 170;
    static final int ALTURA_POSTER = 230;

    private final Serie serie;
    private final PosterPanel poster;

    public SerieCard(Serie serie, Consumer<Serie> aoClicar) {
        this.serie = serie;
        setLayout(new BorderLayout(0, 6));
        setBackground(Tema.CARTAO);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDA, 1),
                BorderFactory.createEmptyBorder(8, 8, 10, 8)));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(LARGURA + 18, ALTURA_POSTER + 78));

        poster = new PosterPanel();
        poster.setPreferredSize(new Dimension(LARGURA, ALTURA_POSTER));
        add(poster, BorderLayout.CENTER);

        JPanel info = new JPanel(new BorderLayout());
        info.setOpaque(false);

        JLabel nome = new JLabel("<html><div style='width:" + LARGURA + "px'>"
                + escapar(serie.getNome()) + "</div></html>");
        nome.setForeground(Tema.TEXTO);
        nome.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel sub = new JLabel("★ " + serie.getNotaTexto() + "  •  " + serie.getEstadoTexto());
        sub.setForeground(Tema.TEXTO_FRACO);
        sub.setFont(Tema.PEQUENA);

        info.add(nome, BorderLayout.NORTH);
        info.add(sub, BorderLayout.SOUTH);
        add(info, BorderLayout.SOUTH);

        MouseAdapter handler = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { aoClicar.accept(serie); }
            @Override public void mouseEntered(MouseEvent e) { setBackground(Tema.CARTAO_HOVER); }
            @Override public void mouseExited(MouseEvent e)  { setBackground(Tema.CARTAO); }
        };
        addMouseListener(handler);
        poster.addMouseListener(handler);

        carregarPoster();
    }

    private void carregarPoster() {
        Image jaTem = ImageLoader.emCache(serie.getUrlImagem());
        if (jaTem != null) {
            poster.definir(jaTem);
            return;
        }
        if (serie.getUrlImagem().isBlank()) return;

        new SwingWorker<Image, Void>() {
            @Override protected Image doInBackground() {
                return ImageLoader.carregar(serie.getUrlImagem(), LARGURA, ALTURA_POSTER);
            }
            @Override protected void done() {
                try {
                    Image img = get();
                    if (img != null) poster.definir(img);
                } catch (Exception ignored) {
                    // Mantem o espaco reservado em caso de falha.
                }
            }
        }.execute();
    }

    private static String escapar(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /** Area do poster: pinta a imagem ou um espaco reservado com as iniciais. */
    private class PosterPanel extends JPanel {
        private Image imagem;

        PosterPanel() {
            setBackground(new Color(0x18, 0x18, 0x18));
        }

        void definir(Image img) {
            this.imagem = img;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (imagem != null) {
                g2.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
            } else {
                g2.setColor(new Color(0x2A, 0x2A, 0x2A));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Tema.VERMELHO);
                g2.setFont(new Font("SansSerif", Font.BOLD, 40));
                String ini = iniciais(serie.getNome());
                int sw = g2.getFontMetrics().stringWidth(ini);
                g2.drawString(ini, (getWidth() - sw) / 2, getHeight() / 2);
                g2.setColor(Tema.TEXTO_FRACO);
                g2.setFont(Tema.PEQUENA);
                String aviso = "sem imagem";
                int aw = g2.getFontMetrics().stringWidth(aviso);
                g2.drawString(aviso, (getWidth() - aw) / 2, getHeight() / 2 + 24);
            }
            g2.dispose();
        }

        private String iniciais(String nome) {
            String[] partes = nome.trim().split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (String p : partes) {
                if (!p.isEmpty()) sb.append(Character.toUpperCase(p.charAt(0)));
                if (sb.length() >= 2) break;
            }
            return sb.length() == 0 ? "?" : sb.toString();
        }
    }

    // Necessario para o JLabel multilinha calcular altura; ignora SwingConstants warning.
    @SuppressWarnings("unused")
    private static final int ALINHAMENTO = SwingConstants.LEFT;
}
