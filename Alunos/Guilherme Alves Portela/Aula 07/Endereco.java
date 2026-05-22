public class Endereco {
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String complemento;

    public Endereco(String estado, String cidade, String bairro, String rua, int numero, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }

    // Getters
    public String getEstado() { return this.estado; }

    public String getCidade() { return this.cidade; }

    public String getBairro() { return this.bairro; }

    public String getRua() { return this.rua; }

    public int getNumero() { return this.numero; }

    public String getComplemento() { return this.complemento; }

    // Setters
    public void setEstado(String estado) { this.estado = estado; }

    public void setCidade(String cidade) { this.cidade = cidade; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    public void setRua(String rua) { this.rua = rua; }

    public void setNumero(int numero) { this.numero = numero; }

    public void setComplemento(String complemento) { this.complemento = complemento; }

    // Método para apresentar o logradouro
    public void apresentarLogradouro() {
        String logradouro = String.format("Rua: %s, Nº %d | Bairro: %s | Cidade: %s | Estado: %s | Complemento: %s",
                this.rua, this.numero, this.bairro, this.cidade, this.estado, this.complemento);
        System.out.println(logradouro);
    }
}
