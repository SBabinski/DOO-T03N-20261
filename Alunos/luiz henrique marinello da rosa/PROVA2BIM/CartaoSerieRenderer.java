import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Faz cada item da lista (JList) virar um "cartao" bonito, em vez da
// linha de texto sem graca que o Swing usa por padrao. Cada cartao tem:
//   - nome em destaque
//   - bolinha colorida do estado (verde/roxo/vermelho)
//   - nota, estado, ano e generos numa linha menor
// e POLIMORFISMO: implemento a interface ListCellRenderer e o Swing me chama.

public class CartaoSerieRenderer extends JPanel implements ListCellRenderer<Serie> {

    private final JLabel rotuloNome;
    private final JLabel rotuloInfo;
    private final PainelEstado bolinha;

    public CartaoSerieRenderer() {
        setLayout(new BorderLayout(12, 0));
        setBorder(new EmptyBorder(10, 14, 10, 14));

        bolinha = new PainelEstado();

        // no centro: nome em cima, info embaixo
        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        rotuloNome = new JLabel();
        rotuloNome.setFont(Tema.FONTE_CARTAO);
        rotuloNome.setForeground(Tema.TEXTO);
        rotuloNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        rotuloInfo = new JLabel();
        rotuloInfo.setFont(Tema.FONTE_PEQUENA);
        rotuloInfo.setForeground(Tema.TEXTO_FRACO);
        rotuloInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        textos.add(rotuloNome);
        textos.add(Box.createVerticalStrut(3));
        textos.add(rotuloInfo);

        // a esquerda: a bolinha, centralizada na vertical
        JPanel esquerda = new JPanel(new GridBagLayout());
        esquerda.setOpaque(false);
        esquerda.add(bolinha);

        add(esquerda, BorderLayout.WEST);
        add(textos, BorderLayout.CENTER);
    }

    // o Swing chama isso pra cada serie da lista. eu preencho os rotulos e devolvo "this"
    @Override
    public Component getListCellRendererComponent(JList<? extends Serie> lista,
                                                  Serie serie, int indice,
                                                  boolean selecionado, boolean comFoco) {
        if (serie == null) {
            rotuloNome.setText("");
            rotuloInfo.setText("");
            return this;
        }

        rotuloNome.setText(serie.getNome());

        // linha de info: nota | estado | ano | generos
        String nota = (serie.getNotaGeral() > 0)
                ? String.format("★ %.1f", serie.getNotaGeral())  // estrela + nota
                : "sem nota";
        String ano = anoDe(serie.getDataEstreia());
        String generos = serie.getGenerosTexto();

        StringBuilder info = new StringBuilder();
        info.append(nota);
        info.append("   •   ").append(serie.getEstado().getDescricao());
        if (!ano.isEmpty()) {
            info.append("   •   ").append(ano);
        }
        info.append("   •   ").append(generos);
        rotuloInfo.setText(info.toString());

        // pinta a bolinha conforme o estado
        bolinha.setCor(corDoEstado(serie.getEstado()));

        // muda o fundo quando ta selecionado
        if (selecionado) {
            setBackground(Tema.CARTAO_SEL);
        } else {
            setBackground(Tema.CARTAO);
        }
        setOpaque(true);

        return this;
    }

    // pega so o ano "yyyy" de uma data "yyyy-MM-dd"
    private String anoDe(String data) {
        if (data == null || data.length() < 4) {
            return "";
        }
        return data.substring(0, 4);
    }

    // cada estado tem sua cor
    private Color corDoEstado(EstadoSerie estado) {
        switch (estado) {
            case TRANSMITINDO: return Tema.SUCESSO;
            case CONCLUIDA:    return Tema.DESTAQUE;
            case CANCELADA:    return Tema.PERIGO;
            default:           return Tema.TEXTO_FRACO;
        }
    }

    // a bolinha colorida do estado (desenho a mao, um circulo)
    private static class PainelEstado extends JComponent {
        private Color cor = Tema.TEXTO_FRACO;

        PainelEstado() {
            setPreferredSize(new Dimension(14, 14));
        }

        void setCor(Color cor) {
            this.cor = cor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(cor);
            g2.fillOval(0, 0, 14, 14);
            g2.dispose();
        }
    }
}
