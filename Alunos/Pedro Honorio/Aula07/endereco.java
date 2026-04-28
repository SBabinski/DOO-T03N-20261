public class Endereco {
    String estado, cidade, bairro, rua, complemento;
    int numero;

    public void apresentarLogradouro() {
        System.out.println(rua + ", " + numero + " - " + bairro + " (" + cidade + "/" + estado + ")");
    }
}
