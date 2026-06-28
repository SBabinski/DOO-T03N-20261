package app;

import exception.ExcecaoPersistencia;
import exception.ExcecaoUsuario;
import model.Usuario;
import persistence.RepositorioUsuario;
import service.ServicoSerie;
import service.ServicoUsuario;
import util.UtilitarioExcecao;
import view.TelaPrincipal;

import javax.swing.*;

/**
 * Ponto de entrada da aplicação.
 *
 * <p>Fluxo de inicialização:
 * <ol>
 *   <li>Verifica se existe arquivo de dados salvo.</li>
 *   <li>Se sim, pergunta se deseja carregar os dados ou iniciar do zero.</li>
 *   <li>Se não, solicita nome/apelido e cria um novo usuário.</li>
 *   <li>Registra o callback de persistência no ServicoSerie.</li>
 *   <li>Exibe a janela principal.</li>
 * </ol>
 * </p>
 */
public class Principal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            iniciar();
        });
    }

    private static void iniciar() {
        RepositorioUsuario repositorio = new RepositorioUsuario();
        ServicoUsuario servicoUsuario = new ServicoUsuario();
        ServicoSerie servicoSerie = new ServicoSerie();

        Usuario usuario = tentarCarregarUsuario(repositorio, servicoUsuario);

        // Registra o callback: sempre que uma lista mudar, salva automaticamente
        servicoSerie.definirCallbackPersistencia(() -> {
            try {
                repositorio.salvar(usuario);
            } catch (ExcecaoPersistencia e) {
                // Falha silenciosa no auto-save — o usuário pode salvar manualmente no Perfil
                System.err.println("Auto-save falhou: " + e.getMessage());
            }
        });

        TelaPrincipal janela = new TelaPrincipal(usuario, servicoSerie, repositorio);
        janela.setVisible(true);
    }

    /**
     * Tenta carregar um usuário existente do arquivo JSON.
     * Se não houver dados ou o usuário optar por recomeçar, solicita nome/apelido.
     *
     * @param repositorio   repositório de persistência
     * @param servicoUsuario serviço de usuário
     * @return usuário pronto para uso
     */
    private static Usuario tentarCarregarUsuario(RepositorioUsuario repositorio,
                                                  ServicoUsuario servicoUsuario) {
        if (repositorio.existemDadosSalvos()) {
            int opcao = JOptionPane.showConfirmDialog(
                    null,
                    "<html><b>Dados salvos encontrados!</b><br><br>"
                    + "Deseja carregar suas listas da sessão anterior?<br>"
                    + "<small>Clique em <b>Não</b> para começar do zero.</small></html>",
                    "SeriesTV — Bem-vindo de volta!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcao == JOptionPane.YES_OPTION) {
                try {
                    Usuario usuario = repositorio.carregar();
                    JOptionPane.showMessageDialog(
                            null,
                            "<html>✅ Dados carregados com sucesso!<br>"
                            + "Bem-vindo de volta, <b>" + usuario.obterNomeOuApelido() + "</b>!<br><br>"
                            + "Favoritos: " + usuario.obterFavoritos().size() + " série(s)<br>"
                            + "Já Assistidas: " + usuario.obterJaAssistidas().size() + " série(s)<br>"
                            + "Deseja Assistir: " + usuario.obterDesejaAssistir().size() + " série(s)</html>",
                            "Dados carregados",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return usuario;
                } catch (ExcecaoPersistencia e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Não foi possível carregar os dados salvos:\n" + e.getMessage()
                            + "\n\nA aplicação iniciará com dados em branco.",
                            "Erro ao carregar dados",
                            JOptionPane.WARNING_MESSAGE
                    );
                    // Cai para criação de novo usuário
                }
            }
        }

        // Cria novo usuário
        return criarNovoUsuario(servicoUsuario);
    }

    /**
     * Loop de solicitação de nome/apelido até o usuário fornecer um válido.
     */
    private static Usuario criarNovoUsuario(ServicoUsuario servicoUsuario) {
        while (true) {
            String nomeOuApelido = JOptionPane.showInputDialog(
                    null,
                    "Bem-vindo ao SeriesTV!\n\nInforme seu nome ou apelido para começar:",
                    "SeriesTV — Identificação",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (nomeOuApelido == null) {
                int sair = JOptionPane.showConfirmDialog(
                        null, "Deseja sair da aplicação?", "Sair",
                        JOptionPane.YES_NO_OPTION
                );
                if (sair == JOptionPane.YES_OPTION) System.exit(0);
                continue;
            }

            try {
                return servicoUsuario.criarUsuario(nomeOuApelido);
            } catch (ExcecaoUsuario e) {
                UtilitarioExcecao.exibirAviso(null, e.getMessage());
            }
        }
    }
}
