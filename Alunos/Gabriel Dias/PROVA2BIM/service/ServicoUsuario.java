package service;

import exception.ExcecaoUsuario;
import model.Usuario;

/**
 * Camada de serviço responsável pela lógica de negócio relacionada ao usuário.
 */
public class ServicoUsuario {

    private Usuario usuarioAtual;

    /**
     * Cria ou atualiza o usuário com o nome/apelido fornecido.
     *
     * @param nomeOuApelido nome ou apelido do usuário
     * @return o usuário criado
     * @throws ExcecaoUsuario se o nome estiver vazio ou nulo
     */
    public Usuario criarUsuario(String nomeOuApelido) throws ExcecaoUsuario {
        if (nomeOuApelido == null || nomeOuApelido.trim().isEmpty()) {
            throw new ExcecaoUsuario("Por favor, informe seu nome ou apelido para continuar.");
        }
        usuarioAtual = new Usuario(nomeOuApelido.trim());
        return usuarioAtual;
    }

    /**
     * Retorna o usuário atualmente logado.
     *
     * @return usuário atual ou null se não definido
     */
    public Usuario obterUsuarioAtual() {
        return usuarioAtual;
    }

    /**
     * Atualiza o nome/apelido do usuário atual.
     *
     * @param novoNome novo nome ou apelido
     * @throws ExcecaoUsuario se o nome estiver vazio
     */
    public void atualizarNome(String novoNome) throws ExcecaoUsuario {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new ExcecaoUsuario("O nome ou apelido não pode ser vazio.");
        }
        if (usuarioAtual != null) {
            usuarioAtual.definirNomeOuApelido(novoNome.trim());
        }
    }
}
