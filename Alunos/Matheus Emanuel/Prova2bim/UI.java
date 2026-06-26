import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/** Fabricas e atalhos para componentes e dialogos padronizados. */
public final class UI {

    private UI() {}

    /** Botao com visual plano no estilo do tema. */
    public static JButton botao(String texto, Color fundo, Color fundoHover) {
        JButton b = new JButton(texto);
        b.setFont(Tema.BOTAO);
        b.setForeground(Tema.TEXTO);
        b.setBackground(fundo);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.addChangeListener(e -> {
            javax.swing.ButtonModel m = b.getModel();
            b.setBackground(m.isRollover() ? fundoHover : fundo);
        });
        return b;
    }

    public static JButton botaoVermelho(String texto) {
        return botao(texto, Tema.VERMELHO, Tema.VERMELHO_ESC);
    }

    public static JButton botaoNeutro(String texto) {
        return botao(texto, Tema.CARTAO, Tema.CARTAO_HOVER);
    }

    public static void erro(Component pai, String mensagem) {
        JOptionPane.showMessageDialog(pai, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void aviso(Component pai, String mensagem) {
        JOptionPane.showMessageDialog(pai, mensagem, "Atencao", JOptionPane.WARNING_MESSAGE);
    }

    public static void info(Component pai, String mensagem) {
        JOptionPane.showMessageDialog(pai, mensagem, "Informacao", JOptionPane.INFORMATION_MESSAGE);
    }
}
