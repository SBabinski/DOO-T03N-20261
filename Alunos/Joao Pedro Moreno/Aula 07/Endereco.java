public class Endereco {

    private String cidade;
    private String bairro;
    private String rua;

    public Endereco() {
    }

    // Construtor completo
    public Endereco(String cidade, String bairro, String rua) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    // Getters e setters
    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    // Método toString para exibir o endereço formatado
    @Override
    public String toString() {
        return rua + ", " + bairro + ", " + cidade;
    }
}
