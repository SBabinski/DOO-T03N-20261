import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> { // iniciar swing
            try {
                JsonStorageService storageService = new JsonStorageService();

                Usuario usuario = storageService.carregarUsuario();

                if (usuario.getNome() == null
                        || usuario.getNome().isBlank()
                        || usuario.getNome().equals("Usuário")) {

                    String nome = JOptionPane.showInputDialog(
                            null,
                            "Digite seu nome ou apelido:",
                            "Usuário do Sistema",
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (nome == null || nome.isBlank()) {
                        nome = "Usuário";
                    }

                    usuario.setNome(nome);
                    storageService.salvarUsuario(usuario);
                }

                SerieController controller = new SerieController(usuario);
                TvMazeService tvMazeService = new TvMazeService();

                MainFrame tela = new MainFrame(controller, tvMazeService, storageService);
                tela.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Ocorreu um erro ao iniciar o sistema: " + e.getMessage()
                );
            }
        });
    }
}