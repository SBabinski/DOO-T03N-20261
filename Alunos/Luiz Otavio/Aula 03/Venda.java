public class Venda {

    int quantidade;
    double valorVenda;
    double desconto;

    public Venda(int quantidade, double valorVenda, double desconto) {
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
        this.desconto = desconto;
    }

    public void mostrarVenda() {
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Valor da venda: R$ " + valorVenda);
        System.out.println("Desconto: R$ " + desconto);
        System.out.println("----------------------");
    }
}