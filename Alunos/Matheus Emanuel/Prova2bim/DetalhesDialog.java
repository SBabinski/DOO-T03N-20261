import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * Janela modal com todas as informacoes da serie e botoes para adicionar ou
 * remover a serie de cada uma das tres listas. As alteracoes sao persistidas
 * imediatamente atraves do callback fornecido pela janela principal.
 */
public class DetalhesDialog extends JDialog {

    private final Usuario usuario;
    private final Serie serie;
    private final Runnable aoAlterar; // chamado apos mudar uma lista (refresh + salvar)
    private final JPanel painelBotoes;

    public DetalhesDialog(Window pai, Usuario usuario, Serie serie, Runnable aoAlterar) {
        super(pai, "Detalhes da serie", ModalityType.APPLICATION_MODAL);
        this.usuario = usuario;
        this.serie = serie;
        this.aoAlterar = aoAlterar;

        JPanel raiz = new JPanel(new BorderLayout(16, 16));
        raiz.setBackground(Tema.FUNDO);
        raiz.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        raiz.add(criarPoster(), BorderLayout.WEST);
        raiz.add(criarInfo(), BorderLayout.CENTER);

        painelBotoes = new JPanel(new GridLayout(1, 3, 10, 0));
        painelBotoes.setBackground(Tema.FUNDO);
        atualizarBotoes();
        raiz.add(painelBotoes, BorderLayout.SOUTH);

        setContentPane(raiz);
        setSize(640, 440);
        setMinimumSize(new Dimension(560, 380));
        setLocationRelativeTo(pai);
    }

    private JPanel criarPoster() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(Tema.FUNDO);
        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(180, 250));
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(Tema.CARTAO);
        lbl.setForeground(Tema.TEXTO_FRACO);
        lbl.setText("Carregando...");
        lbl.setBorder(BorderFactory.createLineBorder(Tema.BORDA));
        wrap.add(lbl, BorderLayout.NORTH);

        new SwingWorker<Image, Void>() {
            @Override protected Image doInBackground() {
                return ImageLoader.carregar(serie.getUrlImagem(), 180, 250);
            }
            @Override protected void done() {
                try {
                    Image img = get();
                    if (img != null) { lbl.setIcon(new ImageIcon(img)); lbl.setText(null); }
                    else lbl.setText("Sem imagem");
                } catch (Exception e) {
                    lbl.setText("Sem imagem");
                }
            }
        }.execute();

        return wrap;
    }

    private JScrollPane criarInfo() {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Tema.FUNDO);

        JLabel titulo = new JLabel(serie.getNome());
        titulo.setForeground(Tema.TEXTO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setAlignmentX(0f);
        info.add(titulo);
        info.add(Box.createVerticalStrut(12));

        info.add(linha("Idioma", serie.getIdiomaTexto()));
        info.add(linha("Generos", serie.getGenerosTexto()));
        info.add(linha("Nota geral", serie.getNotaTexto()));
        info.add(linha("Estado", serie.getEstadoTexto()));
        info.add(linha("Estreia", serie.getDataEstreiaTexto()));
        info.add(linha("Termino", serie.getDataTerminoTexto()));
        info.add(linha("Emissora", serie.getEmissoraTexto()));

        if (!serie.getResumo().isBlank()) {
            info.add(Box.createVerticalStrut(12));
            JLabel res = new JLabel("Resumo");
            res.setForeground(Tema.VERMELHO);
            res.setFont(Tema.BOTAO);
            res.setAlignmentX(0f);
            info.add(res);
            info.add(Box.createVerticalStrut(4));

            JTextArea area = new JTextArea(serie.getResumo());
            area.setWrapStyleWord(true);
            area.setLineWrap(true);
            area.setEditable(false);
            area.setBackground(Tema.FUNDO);
            area.setForeground(Tema.TEXTO_FRACO);
            area.setFont(Tema.NORMAL);
            area.setAlignmentX(0f);
            info.add(area);
        }

        JScrollPane sp = new JScrollPane(info);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(Tema.FUNDO);
        return sp;
    }

    private JPanel linha(String rotulo, String valor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        p.setBackground(Tema.FUNDO);
        p.setAlignmentX(0f);
        JLabel r = new JLabel(rotulo + ":");
        r.setForeground(Tema.VERMELHO);
        r.setFont(Tema.BOTAO);
        JLabel v = new JLabel("<html><div style='width:340px'>" + escapar(valor) + "</div></html>");
        v.setForeground(Tema.TEXTO);
        v.setFont(Tema.NORMAL);
        p.add(r);
        p.add(v);
        return p;
    }

    /** Recria os tres botoes de acordo com a presenca da serie em cada lista. */
    private void atualizarBotoes() {
        painelBotoes.removeAll();
        for (TipoLista tipo : TipoLista.values()) {
            boolean possui = usuario.contem(tipo, serie);
            String texto = (possui ? "Remover de " : "+ ") + tipo.getRotulo();
            JButton b = possui ? UI.botaoNeutro(texto) : UI.botaoVermelho(texto);
            b.addActionListener(e -> alternar(tipo));
            painelBotoes.add(b);
        }
        painelBotoes.revalidate();
        painelBotoes.repaint();
    }

    private void alternar(TipoLista tipo) {
        if (usuario.contem(tipo, serie)) {
            usuario.remover(tipo, serie);
        } else {
            usuario.adicionar(tipo, serie);
        }
        atualizarBotoes();
        if (aoAlterar != null) aoAlterar.run();
    }

    private static String escapar(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
