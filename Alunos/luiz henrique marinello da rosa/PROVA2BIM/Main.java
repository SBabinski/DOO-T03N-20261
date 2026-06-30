import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// Comeco de tudo (o metodo main roda primeiro).
// Faz 3 coisas: carrega os dados salvos, pergunta meu nome e abre a janela.
// Tudo em try/catch pra, se der ruim, mostrar aviso em vez de fechar do nada.

public class Main {

    public static void main(String[] args) {

        // tento deixar o visual com o tema Nimbus (aceita bem cor customizada).
        // se nao rolar, segue no visual padrao mesmo.
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // de boa, ignoro
        }

        // ajustes globais do meu tema (cor de tooltip etc)
        Tema.aplicarGlobais();

        // o Swing tem que iniciar na thread dele (EDT). e o jeito certo.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                iniciar();
            }
        });
    }

    private static void iniciar() {
        Persistencia persistencia = new Persistencia();
        Usuario usuario;

        // 1) carrega os dados (se o arquivo estiver corrompido, comeco do zero)
        try {
            usuario = persistencia.carregar();
        } catch (PersistenciaException e) {
            Dialogos.aviso(null, "Aviso",
                    "Houve um problema ao carregar seus dados:\n" + e.getMessage()
                    + "\n\nO sistema vai iniciar com uma lista nova.");
            usuario = new Usuario("luiz");
        }

        // 2) pergunta como o usuario quer ser chamado
        try {
            String nome = Dialogos.pedirTexto(null, "Bem-vindo",
                    "Como voce quer ser chamado(a)?", usuario.getNome());
            if (nome != null && !nome.trim().isEmpty()) {
                usuario.setNome(nome);
            }
        } catch (Exception e) {
            // se der erro aqui, mantenho o nome que ja tinha
        }

        // 3) abre a janela principal
        final Usuario usuarioFinal = usuario;
        final Persistencia persistenciaFinal = persistencia;
        try {
            JanelaPrincipal janela = new JanelaPrincipal(usuarioFinal, persistenciaFinal);
            janela.setVisible(true);
        } catch (Exception e) {
            Dialogos.erro(null, "Erro",
                    "Erro ao abrir a janela do programa:\n" + e.getMessage());
        }
    }
}
