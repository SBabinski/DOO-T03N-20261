import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Monta e mostra a telinha com TODOS os dados de uma serie (o que o trabalho pede):
// nome, idioma, generos, nota, estado, datas de estreia/termino e emissora.

public class DetalhesSerie {

    // versao em texto puro dos detalhes. deixei aqui pq e simples e util.
    public static String montarTexto(Serie s) {
        if (s == null) {
            return "Nenhuma serie selecionada.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(s.getNome()).append("\n");
        sb.append("Idioma: ").append(textoOuPadrao(s.getIdioma())).append("\n");
        sb.append("Generos: ").append(s.getGenerosTexto()).append("\n");

        if (s.getNotaGeral() > 0) {
            sb.append("Nota geral: ").append(String.format("%.1f", s.getNotaGeral())).append("\n");
        } else {
            sb.append("Nota geral: sem nota\n");
        }

        sb.append("Estado: ").append(s.getEstado().getDescricao()).append("\n");
        sb.append("Data de estreia: ").append(textoOuPadrao(s.getDataEstreia())).append("\n");
        sb.append("Data de termino: ").append(terminoTexto(s)).append("\n");
        sb.append("Emissora: ").append(textoOuPadrao(s.getEmissora()));

        return sb.toString();
    }

    // abre a janelinha bonita (no tema escuro) com os detalhes
    public static void mostrar(Component pai, Serie s) {
        if (s == null) {
            Dialogos.info(pai, "Detalhes", "Nenhuma serie selecionada.");
            return;
        }

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Tema.FUNDO_PAINEL);
        painel.setBorder(new EmptyBorder(18, 22, 18, 22));

        // titulo = nome da serie em destaque
        JLabel titulo = new JLabel(s.getNome());
        titulo.setFont(Tema.FONTE_TITULO);
        titulo.setForeground(Tema.TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(titulo);

        // subtitulo = nota + estado
        String nota = (s.getNotaGeral() > 0)
                ? String.format("Nota %.1f", s.getNotaGeral())
                : "sem nota";
        JLabel sub = new JLabel(nota + "   -   " + s.getEstado().getDescricao());
        sub.setFont(Tema.FONTE_SUBTITULO);
        sub.setForeground(Tema.TEXTO_FRACO);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(sub);

        painel.add(Box.createVerticalStrut(16));

        // o resto, em linhas "rotulo: valor"
        painel.add(linha("Idioma", textoOuPadrao(s.getIdioma())));
        painel.add(linha("Generos", s.getGenerosTexto()));
        painel.add(linha("Estado", s.getEstado().getDescricao()));
        painel.add(linha("Estreia", textoOuPadrao(s.getDataEstreia())));
        painel.add(linha("Termino", terminoTexto(s)));
        painel.add(linha("Emissora", textoOuPadrao(s.getEmissora())));

        Dialogos.mostrarPainel(pai, "Detalhes da serie", painel);
    }

    // cria uma linha tipo "Idioma:  English"
    private static JComponent linha(String rotulo, String valor) {
        JPanel p = new JPanel(new BorderLayout(10, 0));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(4, 0, 4, 0));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(460, 60));

        JLabel chave = new JLabel(rotulo);
        chave.setFont(Tema.FONTE_NEGRITO);
        chave.setForeground(Tema.TEXTO_FRACO);
        chave.setPreferredSize(new Dimension(90, 20));

        // uso html pra quebrar linha se o texto for grande
        JLabel val = new JLabel("<html><body style='width:330px'>" + escapar(valor) + "</body></html>");
        val.setFont(Tema.FONTE_NORMAL);
        val.setForeground(Tema.TEXTO);

        p.add(chave, BorderLayout.WEST);
        p.add(val, BorderLayout.CENTER);
        return p;
    }

    // texto da data de termino. se ta no ar, mostra "ainda em transmissao"
    private static String terminoTexto(Serie s) {
        String termino = s.getDataTermino();
        if (termino == null || termino.isEmpty()) {
            if (s.getEstado() == EstadoSerie.TRANSMITINDO) {
                return "ainda em transmissao";
            }
            return "nao informada";
        }
        return termino;
    }

    // escapa simbolos de html (senao quebra o <html> do rotulo)
    private static String escapar(String t) {
        if (t == null) {
            return "";
        }
        return t.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    // se o campo ta vazio, mostro "Nao informado" em vez de em branco
    private static String textoOuPadrao(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "Nao informado";
        }
        return valor;
    }
}
