public class Endereco {
    String estado;
    String cidade;
    String bairro;
    String complemento;
    int numero;

    public Endereco(String estado, String cidade ,String bairro , String complemento , int numero ){
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.complemento = complemento;
        this.numero = numero;
    }

    public void apresentaEndereco(){
        System.out.printf("📍 CIDADE/UF: %s - %s%n", cidade.toUpperCase(), estado.toUpperCase()); // toUpperCase -> caixa alta
        System.out.printf("🏠 BAIRRO:    %s%n", bairro);
        System.out.printf("🔢 NÚMERO:    %s%n", numero);
        System.out.printf("➕ COMPLEMENTOS: %s%n", complemento);
    }
}
