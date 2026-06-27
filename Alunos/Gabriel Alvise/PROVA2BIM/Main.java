import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JsonStorageService jsonStorageService = new JsonStorageService();
            boolean primeiroAcesso = !jsonStorageService.existeArquivoDados();
            DadosAplicacao dadosAplicacao = jsonStorageService.carregarDados();

            SerieRepository serieRepository = new SerieRepository(
                    dadosAplicacao,
                    jsonStorageService
            );

            if (primeiroAcesso) {
                String nomeUsuario = TelaBoasVindas.solicitarNomeUsuario();
                serieRepository.atualizarUsuario(nomeUsuario);
            }

            TvMazeService tvMazeService = new TvMazeService();

            MainFrame mainFrame = new MainFrame(
                    serieRepository,
                    tvMazeService
            );

            mainFrame.setVisible(true);
        });
    }
}
