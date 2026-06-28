import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Main {
    public static void main(String[] args) {
        // O Swing deve ser iniciado pela thread de eventos da interface grafica.
        // Por isso usamos SwingUtilities.invokeLater.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Tenta usar a aparencia visual padrao do sistema operacional.
                configureLookAndFeel();

                DadosSistema dados;
                try {
                    // Tenta carregar os dados salvos em dados_series_tv.json.
                    dados = DadosSistema.carregar();
                } catch (IOException exception) {
                    // Se houver erro no arquivo JSON, o programa nao fecha.
                    // Ele mostra um aviso e inicia com dados pre-carregados.
                    dados = DadosSistema.criarComDadosPreCarregados();
                    JOptionPane.showMessageDialog(
                            null,
                            "Nao foi possivel carregar os dados salvos.\nO sistema iniciou com dados padrao.\n\n" + exception.getMessage(),
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                // Cria e exibe a tela principal do sistema.
                TelaPrincipal tela = new TelaPrincipal(dados);
                tela.setVisible(true);
            }
        });
    }

    private static void configureLookAndFeel() {
        try {
            // Usa o tema visual nativo do Windows/Linux/Mac quando disponivel.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // O sistema continua com a aparencia padrao caso o tema nativo falhe.
        }
    }
}
