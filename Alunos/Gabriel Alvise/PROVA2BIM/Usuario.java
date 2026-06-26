public class Usuario {
    private String nomeOuApelido;

    public Usuario() {
        this.nomeOuApelido = "Usuário";
    }

    public Usuario(String nomeOuApelido) {
        setNomeOuApelido(nomeOuApelido);
    }

    public String getNomeOuApelido() {
        return nomeOuApelido;
    }

    public void setNomeOuApelido(String nomeOuApelido) {
        if (nomeOuApelido == null || nomeOuApelido.trim().isEmpty()) {
            this.nomeOuApelido = "Usuário";
            return;
        }

        this.nomeOuApelido = nomeOuApelido.trim();
    }
}