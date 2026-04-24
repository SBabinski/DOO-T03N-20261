public class Imovel {
    String endereco;
    double valor;

    public Imovel(String endereco, double valor) {
        this.endereco = endereco;
        this.valor = valor;
    }

    public void mostrar() {
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor aluguel: " + valor);
    }
}