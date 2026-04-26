public class Endereco {
    String estado, cidade, bairro, rua, complemento;
    int numero;

    public Endereco(String estado, String cidade, String bairro, String rua, int numero, String complemento){
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }

    public void apresentarLogradouro(){
        System.out.println("Rua " + rua + ", " + numero + ", " + bairro + ", " + cidade + " - " + estado + ", complemento: " + complemento);
    }
}
