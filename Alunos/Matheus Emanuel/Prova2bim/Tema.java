import java.awt.Color;
import java.awt.Font;

/** Paleta e fontes inspiradas na Netflix, centralizadas para reuso. */
public final class Tema {

    public static final Color FUNDO        = new Color(0x14, 0x14, 0x14);
    public static final Color FUNDO_BARRA  = new Color(0x0B, 0x0B, 0x0B);
    public static final Color CARTAO       = new Color(0x22, 0x22, 0x22);
    public static final Color CARTAO_HOVER = new Color(0x33, 0x33, 0x33);
    public static final Color VERMELHO     = new Color(0xE5, 0x09, 0x14);
    public static final Color VERMELHO_ESC = new Color(0xB2, 0x07, 0x10);
    public static final Color TEXTO        = new Color(0xF5, 0xF5, 0xF5);
    public static final Color TEXTO_FRACO  = new Color(0xAA, 0xAA, 0xAA);
    public static final Color BORDA        = new Color(0x3A, 0x3A, 0x3A);

    public static final Font LOGO    = new Font("SansSerif", Font.BOLD, 26);
    public static final Font TITULO  = new Font("SansSerif", Font.BOLD, 18);
    public static final Font NORMAL  = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font PEQUENA = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font BOTAO   = new Font("SansSerif", Font.BOLD, 13);

    private Tema() {}
}
