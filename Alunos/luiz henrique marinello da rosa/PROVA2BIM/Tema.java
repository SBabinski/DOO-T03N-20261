import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Aqui ficam as cores, fontes e os botoes do app, tudo num lugar so.
// Assim a tela inteira fica com a mesma cara e o codigo das telas fica limpo.
// E Swing puro, sem lib externa.

public class Tema {

    // ---- cores (tons escuros, estilo streaming) ----
    public static final Color FUNDO        = new Color(0x14, 0x16, 0x22); // fundo geral
    public static final Color FUNDO_PAINEL = new Color(0x1E, 0x21, 0x33); // paineis
    public static final Color CARTAO       = new Color(0x26, 0x2A, 0x40); // cartoes da lista
    public static final Color CARTAO_SEL   = new Color(0x37, 0x3D, 0x66); // cartao selecionado
    public static final Color DESTAQUE      = new Color(0x6C, 0x5C, 0xE7); // roxo principal
    public static final Color DESTAQUE_HOVER = new Color(0x83, 0x74, 0xF2);
    public static final Color PERIGO        = new Color(0xE7, 0x4C, 0x5C); // botao remover
    public static final Color PERIGO_HOVER  = new Color(0xF0, 0x65, 0x74);
    public static final Color SUCESSO       = new Color(0x2E, 0xCC, 0x71);
    public static final Color TEXTO         = new Color(0xF2, 0xF3, 0xF8); // texto claro
    public static final Color TEXTO_FRACO   = new Color(0xA0, 0xA4, 0xB8); // texto secundario
    public static final Color BORDA         = new Color(0x33, 0x37, 0x52);

    // ---- fontes ----
    public static final Font FONTE_TITULO   = new Font("SansSerif", Font.BOLD, 20);
    public static final Font FONTE_SUBTITULO = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONTE_NORMAL    = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONTE_NEGRITO   = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONTE_CARTAO    = new Font("SansSerif", Font.BOLD, 15);
    public static final Font FONTE_PEQUENA    = new Font("SansSerif", Font.PLAIN, 12);

    // ajuste global so do tooltip. os dialogos eu fiz na mao (classe Dialogos),
    // entao nao forco cor global que poderia baguncar outros componentes.
    public static void aplicarGlobais() {
        UIManager.put("ToolTip.background", FUNDO_PAINEL);
        UIManager.put("ToolTip.foreground", TEXTO);
    }

    // ---- botao arredondado e colorido ----
    // passo a cor normal e a cor de hover (mouse em cima)
    public static JButton criarBotao(String texto, Color base, Color hover) {
        return new BotaoArredondado(texto, base, hover);
    }

    // botao principal (roxo)
    public static JButton botaoPrimario(String texto) {
        return criarBotao(texto, DESTAQUE, DESTAQUE_HOVER);
    }

    // botao neutro (cinza)
    public static JButton botaoSecundario(String texto) {
        return criarBotao(texto, CARTAO, CARTAO_SEL);
    }

    // botao vermelho (remover)
    public static JButton botaoPerigo(String texto) {
        return criarBotao(texto, PERIGO, PERIGO_HOVER);
    }

    // ---- rotulos prontos ----
    public static JLabel criarTitulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(TEXTO);
        l.setFont(FONTE_TITULO);
        return l;
    }

    public static JLabel criarSubtitulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(TEXTO_FRACO);
        l.setFont(FONTE_SUBTITULO);
        return l;
    }

    public static JLabel criarRotulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(TEXTO);
        l.setFont(FONTE_NORMAL);
        return l;
    }

    // ---- campo de texto no tema ----
    public static JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setBackground(CARTAO);
        campo.setForeground(TEXTO);
        campo.setCaretColor(TEXTO);
        campo.setFont(FONTE_NORMAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1, true),
                new EmptyBorder(8, 12, 8, 12)));
        return campo;
    }

    // margem (espaco interno) pros paineis
    public static Border margem(int v) {
        return new EmptyBorder(v, v, v, v);
    }

    public static Border margem(int cima, int dir, int baixo, int esq) {
        return new EmptyBorder(cima, esq, baixo, dir);
    }

    // o botao arredondado por dentro 
   
    private static class BotaoArredondado extends JButton {

        private final Color base;
        private final Color hover;
        private boolean mouseEmCima = false;

        BotaoArredondado(String texto, Color base, Color hover) {
            super(texto);
            this.base = base;
            this.hover = hover;

            setForeground(TEXTO);
            setFont(FONTE_NEGRITO);
            setFocusPainted(false);
            setContentAreaFilled(false); // eu pinto, nao o swing
            setBorderPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(9, 18, 9, 18));

            // detecta o mouse pra trocar a cor (hover)
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    mouseEmCima = true;
                    repaint();
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    mouseEmCima = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Color cor = mouseEmCima ? hover : base;
            if (getModel().isPressed()) {
                cor = cor.darker();
            }

            g2.setColor(cor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.dispose();

            super.paintComponent(g); // o texto vem por cima
        }
    }
}
