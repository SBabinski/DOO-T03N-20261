package entities;

public class Endereco {
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String complemento;
    
    public Endereco(String estado, String cidade, String bairro,
                    String rua, int numero, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }
    
    // Printa os dados de endereço concatenados
    public String apresentarLogradouro() {
        return String.format("%s, %s, %s, %s, nº %d - %s",
                estado, cidade, bairro, rua, numero, complemento);
    }
}
