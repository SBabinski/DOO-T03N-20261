import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

// Minhas caixas de dialogo, no mesmo tema escuro do resto do app.
//
// Por que nao usar o JOptionPane normal? Pq o visual dele e cinza e feio,
// destoa do app. Entao fiz JDialogs proprios, com as cores do Tema,
// iconezinho desenhado a mao e meus botoes arredondados. Swing puro.
//
// o que tem aqui:
//   pedirTexto  -> pergunta um texto (ex: nome)
//   info/aviso/erro -> mensagens simples
//   confirmar   -> pergunta Sim/Nao
//   mostrarPainel -> exibe um painel pronto (uso nos detalhes)

public class Dialogos {

    // tipos de mensagem, cada um com sua cor/icone
    public enum Tipo { INFO, AVISO, ERRO, PERGUNTA }

    // ---- pedir um texto (devolve o que digitou, ou null se cancelar) ----
    public static String pedirTexto(Component pai, String titulo,
                                    String mensagem, String valorInicial) {
        final String[] resultado = { null };

        final JDialog dialogo = criarBase(pai, titulo);
        JPanel raiz = criarRaiz();

        raiz.add(envolverIcone(new Icone(Tipo.PERGUNTA)), BorderLayout.WEST);

        // no centro: a mensagem + o campo de texto
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        JLabel msg = criarMensagem(mensagem);
        msg.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JTextField campo = new JTextField(valorInicial == null ? "" : valorInicial, 18);
        campo.setBackground(Tema.CARTAO);
        campo.setForeground(Tema.TEXTO);
        campo.setCaretColor(Tema.TEXTO);
        campo.setFont(Tema.FONTE_NORMAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Tema.BORDA, 1, true),
                new EmptyBorder(8, 12, 8, 12)));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(400, 38));

        centro.add(msg);
        centro.add(Box.createVerticalStrut(12));
        centro.add(campo);
        raiz.add(centro, BorderLayout.CENTER);

        // botoes: Cancelar + OK
        JButton ok = Tema.botaoPrimario("OK");
        JButton cancelar = Tema.botaoSecundario("Cancelar");
        ok.addActionListener(e -> { resultado[0] = campo.getText(); dialogo.dispose(); });
        cancelar.addActionListener(e -> { resultado[0] = null; dialogo.dispose(); });

        raiz.add(criarBarraBotoes(cancelar, ok), BorderLayout.SOUTH);

        dialogo.getRootPane().setDefaultButton(ok); // Enter = OK
        ligarEscape(dialogo);

        // quando abrir, ja foca o campo e seleciona o texto
        dialogo.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                campo.requestFocusInWindow();
                campo.selectAll();
            }
        });

        finalizar(dialogo, raiz, pai);
        return resultado[0];
    }

    // ---- mensagens simples ----
    public static void info(Component pai, String titulo, String mensagem) {
        mensagem(pai, titulo, mensagem, Tipo.INFO);
    }

    public static void aviso(Component pai, String titulo, String mensagem) {
        mensagem(pai, titulo, mensagem, Tipo.AVISO);
    }

    public static void erro(Component pai, String titulo, String mensagem) {
        mensagem(pai, titulo, mensagem, Tipo.ERRO);
    }

    // o miolo das 3 de cima (so muda o icone)
    private static void mensagem(Component pai, String titulo, String mensagem, Tipo tipo) {
        final JDialog dialogo = criarBase(pai, titulo);
        JPanel raiz = criarRaiz();

        raiz.add(envolverIcone(new Icone(tipo)), BorderLayout.WEST);
        raiz.add(criarMensagem(mensagem), BorderLayout.CENTER);

        JButton ok = Tema.botaoPrimario("OK");
        ok.addActionListener(e -> dialogo.dispose());
        raiz.add(criarBarraBotoes(ok), BorderLayout.SOUTH);

        dialogo.getRootPane().setDefaultButton(ok);
        ligarEscape(dialogo);
        finalizar(dialogo, raiz, pai);
    }

    // ---- Sim/Nao (devolve true se clicar Sim) ----
    public static boolean confirmar(Component pai, String titulo, String mensagem) {
        final boolean[] resultado = { false };

        final JDialog dialogo = criarBase(pai, titulo);
        JPanel raiz = criarRaiz();

        raiz.add(envolverIcone(new Icone(Tipo.PERGUNTA)), BorderLayout.WEST);
        raiz.add(criarMensagem(mensagem), BorderLayout.CENTER);

        JButton sim = Tema.botaoPrimario("Sim");
        JButton nao = Tema.botaoSecundario("Nao");
        sim.addActionListener(e -> { resultado[0] = true; dialogo.dispose(); });
        nao.addActionListener(e -> { resultado[0] = false; dialogo.dispose(); });

        raiz.add(criarBarraBotoes(nao, sim), BorderLayout.SOUTH);

        dialogo.getRootPane().setDefaultButton(sim);
        ligarEscape(dialogo);
        finalizar(dialogo, raiz, pai);
        return resultado[0];
    }

    // ---- mostra um painel que ja vem pronto (uso nos detalhes da serie) ----
    public static void mostrarPainel(Component pai, String titulo, JComponent conteudo) {
        final JDialog dialogo = criarBase(pai, titulo);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Tema.FUNDO_PAINEL);
        raiz.setBorder(new EmptyBorder(4, 4, 12, 4));

        raiz.add(conteudo, BorderLayout.CENTER);

        JButton fechar = Tema.botaoPrimario("Fechar");
        fechar.addActionListener(e -> dialogo.dispose());
        raiz.add(criarBarraBotoes(fechar), BorderLayout.SOUTH);

        dialogo.getRootPane().setDefaultButton(fechar);
        ligarEscape(dialogo);
        finalizar(dialogo, raiz, pai);
    }

    // ================== ajudantes internos ==================

    // cria a janelinha modal (trava o resto ate fechar)
    private static JDialog criarBase(Component pai, String titulo) {
        Window dono = (pai == null) ? null : SwingUtilities.getWindowAncestor(pai);
        JDialog dialogo = new JDialog(dono, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.getContentPane().setBackground(Tema.FUNDO_PAINEL);
        return dialogo;
    }

    // o painel de fundo com margem
    private static JPanel criarRaiz() {
        JPanel raiz = new JPanel(new BorderLayout(16, 0));
        raiz.setBackground(Tema.FUNDO_PAINEL);
        raiz.setBorder(new EmptyBorder(22, 24, 16, 24));
        return raiz;
    }

    // poe o icone no topo da coluna da esquerda
    private static JComponent envolverIcone(JComponent icone) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(icone, BorderLayout.NORTH);
        return p;
    }

    // rotulo da mensagem (uso html pra quebrar linha)
    private static JLabel criarMensagem(String texto) {
        String html = "<html><body style='width:260px'>" + escapar(texto) + "</body></html>";
        JLabel l = new JLabel(html);
        l.setForeground(Tema.TEXTO);
        l.setFont(Tema.FONTE_NORMAL);
        return l;
    }

    // a barra de baixo com os botoes a direita
    private static JPanel criarBarraBotoes(JButton... botoes) {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        barra.setOpaque(false);
        barra.setBorder(new EmptyBorder(18, 0, 0, 0));
        for (JButton b : botoes) {
            barra.add(b);
        }
        return barra;
    }

    // faz o Esc fechar a janela
    private static void ligarEscape(final JDialog dialogo) {
        dialogo.getRootPane().registerKeyboardAction(
                e -> dialogo.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    // poe a raiz na janela, ajusta tamanho e mostra
    private static void finalizar(JDialog dialogo, JPanel raiz, Component pai) {
        dialogo.setContentPane(raiz);
        dialogo.pack();
        dialogo.setResizable(false);
        dialogo.setLocationRelativeTo(pai);
        dialogo.setVisible(true);
    }

    // escapa simbolos de html (senao quebra o <html>)
    private static String escapar(String t) {
        if (t == null) {
            return "";
        }
        return t.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\n", "<br>");
    }

    // ---- o iconezinho redondo com um simbolo (i, !, ?) ----
    private static class Icone extends JComponent {
        private final Tipo tipo;

        Icone(Tipo tipo) {
            this.tipo = tipo;
            setPreferredSize(new Dimension(46, 46));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Color cor;
            String simbolo;
            switch (tipo) {
                case INFO:
                    cor = new Color(0x3B, 0x82, 0xF6); simbolo = "i"; break;
                case AVISO:
                    cor = new Color(0xF5, 0x9E, 0x0B); simbolo = "!"; break;
                case ERRO:
                    cor = Tema.PERIGO; simbolo = "!"; break;
                default:
                    cor = Tema.DESTAQUE; simbolo = "?";
            }

            g2.setColor(cor);
            g2.fillOval(3, 3, 40, 40);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 24));
            FontMetrics fm = g2.getFontMetrics();
            int tx = (46 - fm.stringWidth(simbolo)) / 2;
            int ty = (46 - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(simbolo, tx, ty);

            g2.dispose();
        }
    }
}
