public class Venda {
    public int quantidade;
    public double valor;
    public double desconto; 
    
    public Venda(int quantidade, double valor, double desconto){
        if (valor <= 0) {
            System.out.println("Valor da flor deve ser maior que zero.");
            return;
        }
        if (quantidade <= 0) {
           System.out.println("Quantidade de flores não pode ser menor ou igual a zero.");
           return;
        }

        this.quantidade = quantidade;
        this.valor = valor;
        this.desconto = desconto;
    }

    //Getters
    public int getQuantidade() { return quantidade; }
    public double getValor() { return valor;}
    public double getDesconto(){ return desconto}

    public String toString() {
        return "Valor: R$ " + String.format("%.2f", valor) + " | Quantidade: " + quantidade + " | Desconto: " + String.format("%.2f", desconto);
    }
    
    //Setters

    
}
