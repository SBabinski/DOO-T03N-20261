import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Ponto de entrada do SeriesFlix.
 *
 * Responsabilidades:
 *  - instalar um tratador global de excecoes para que erros inesperados nunca
 *    fechem o programa silenciosamente;
 *  - carregar (ou criar) o usuario local persistido em JSON;
 *  - solicitar o apelido na primeira execucao;
 *  - abrir a janela principal na thread de eventos do Swing.
 */
public class Main {

    public static void main(String[] args) {
        // Qualquer excecao nao tratada em qualquer thread vira um aviso amigavel.
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Ocorreu um erro inesperado, mas o programa continuara funcionando.\n\n"
                            + "Detalhe: " + e.getMessage(),
                    "Erro inesperado", JOptionPane.ERROR_MESSAGE));
        });

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Mantem o look-and-feel padrao se o do sistema nao estiver disponivel.
        }

        SwingUtilities.invokeLater(Main::iniciar);
    }

    private static void iniciar() {
        PersistenceService persistencia = new PersistenceService();
        TvMazeService api = new TvMazeService();

        Usuario usuario = carregarOuCriarUsuario(persistencia);
        if (usuario == null) {
            // Usuario cancelou a identificacao inicial: encerra com elegancia.
            System.exit(0);
            return;
        }

        try {
            persistencia.salvar(usuario);
        } catch (AppException e) {
            JOptionPane.showMessageDialog(null,
                    "Nao foi possivel gravar o arquivo de dados:\n" + e.getMessage()
                            + "\n\nO programa continuara, mas talvez nao salve seu progresso.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }

        MainFrame janela = new MainFrame(usuario, api, persistencia);
        janela.setVisible(true);
    }

    private static Usuario carregarOuCriarUsuario(PersistenceService persistencia) {
        Usuario usuario = null;
        try {
            usuario = persistencia.carregar();
        } catch (AppException e) {
            int opcao = JOptionPane.showConfirmDialog(null,
                    e.getMessage() + "\n\nDeseja comecar com dados novos? "
                            + "(o arquivo atual sera sobrescrito ao salvar)",
                    "Arquivo de dados", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opcao != JOptionPane.YES_OPTION) return null;
        }

        if (usuario == null) {
            usuario = new Usuario("");
        }

        // Garante que exista um apelido (informacao do usuario local).
        while (usuario.getApelido() == null || usuario.getApelido().isBlank()) {
            String apelido = JOptionPane.showInputDialog(null,
                    "Bem-vindo ao SeriesFlix!\nComo devemos chamar voce?",
                    "Identificacao", JOptionPane.QUESTION_MESSAGE);
            if (apelido == null) return null; // cancelado
            usuario.setApelido(apelido);
            if (usuario.getApelido().isBlank()) {
                JOptionPane.showMessageDialog(null, "Por favor, informe um nome ou apelido.",
                        "Atencao", JOptionPane.WARNING_MESSAGE);
            }
        }
        return usuario;
    }
}
