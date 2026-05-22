package Objetos;

public class Endereco {

    private String estado;
    private String cidade;
    private String bairro;
    private String numero;
    private String complemento;

    public Endereco(String estado, String cidade, String bairro, String numero, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Endereco(String estado, String cidade, String bairro, String numero) {
        this(estado, cidade, bairro, numero, "");
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public void apresentarLogradouro() {
        String base = complemento.isBlank()
                ? String.format("%s - %s, %s/%s", bairro, numero, cidade, estado)
                : String.format("%s %s - %s, %s/%s", complemento, bairro, numero, cidade, estado);
        System.out.println("📍 " + base);
    }
}
