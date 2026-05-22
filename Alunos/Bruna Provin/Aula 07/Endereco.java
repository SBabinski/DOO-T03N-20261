public class Endereco {
    String estado;
    String cidade;
    String bairro;
    String rua;
    String numero;
    String complemento;

    public Endereco(String estado, String cidade, String bairro,
                    String rua, String numero, String complemento) {
        this.estado      = estado;
        this.cidade      = cidade;
        this.bairro      = bairro;
        this.rua         = rua;
        this.numero      = numero;
        this.complemento = complemento;
    }

    public void apresentarLogradouro() {
        System.out.println("Endereco: " + rua + ", " + numero +
                (complemento != null && !complemento.isEmpty() ? " - " + complemento : "") +
                " | " + bairro + " | " + cidade + " - " + estado);
    }
}