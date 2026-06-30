
import java.util.ArrayList;
import java.util.List;

/**
 * Mantém a coleção de usuários locais em memória e a sincroniza com o arquivo
 * JSON. Os apelidos são tratados como identificadores e não podem se repetir
 * (a comparação ignora maiúsculas/minúsculas).
 */
public final class RepositorioUsuarios {
    private final PersistenciaJson persistencia;
    private final List<Usuario> usuarios;

    public RepositorioUsuarios(PersistenciaJson persistencia) throws PersistenciaException {
        if (persistencia == null) {
            throw new IllegalArgumentException("A persistência deve ser informada.");
        }
        this.persistencia = persistencia;
        this.usuarios = new ArrayList<>(persistencia.carregar());
    }

    /** Cópia imutável da lista atual de usuários, na ordem em que foram criados. */
    public List<Usuario> listar() {
        return List.copyOf(usuarios);
    }

    public boolean estaVazio() {
        return usuarios.isEmpty();
    }

    public boolean existe(String apelido) {
        return buscar(apelido) != null;
    }

    /** Retorna o usuário com o apelido informado, ou {@code null} se não houver. */
    public Usuario buscar(String apelido) {
        if (apelido == null) {
            return null;
        }
        String alvo = apelido.trim();
        for (Usuario usuario : usuarios) {
            if (usuario.getApelido().equalsIgnoreCase(alvo)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Cria um novo usuário, persiste e o devolve. Lança
     * {@link IllegalArgumentException} se já existir alguém com o mesmo apelido.
     */
    public Usuario criar(String apelido) throws PersistenciaException {
        Usuario novo = new Usuario(apelido);
        if (existe(novo.getApelido())) {
            throw new IllegalArgumentException(
                    "Já existe um usuário chamado \"" + novo.getApelido() + "\".");
        }
        usuarios.add(novo);
        salvar();
        return novo;
    }

    /** Renomeia um usuário existente garantindo que o novo apelido seja único. */
    public void renomear(Usuario usuario, String novoApelido) throws PersistenciaException {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário deve ser informado.");
        }
        String anterior = usuario.getApelido();
        usuario.setApelido(novoApelido);
        Usuario conflito = buscar(usuario.getApelido());
        if (conflito != null && conflito != usuario) {
            usuario.setApelido(anterior);
            throw new IllegalArgumentException(
                    "Já existe um usuário chamado \"" + novoApelido.trim() + "\".");
        }
        try {
            salvar();
        } catch (PersistenciaException erro) {
            usuario.setApelido(anterior);
            throw erro;
        }
    }

    public void remover(Usuario usuario) throws PersistenciaException {
        if (usuario == null || !usuarios.remove(usuario)) {
            return;
        }
        try {
            salvar();
        } catch (PersistenciaException erro) {
            usuarios.add(usuario);
            throw erro;
        }
    }

    /** Persiste a coleção inteira no arquivo JSON. */
    public void salvar() throws PersistenciaException {
        persistencia.salvar(usuarios);
    }
}
