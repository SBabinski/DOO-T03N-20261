public class Venda {
    public int quantidade;
    public double valor;
    public double desconto; 
    
    public Venda(int quantidade, double valor, double desconto){
        if (valor <= 0) {throw new IllegalArgumentException("Valor deve ser maior que zero");}
        
        if (quantidade <= 0) {throw new IllegalArgumentException("Quantidade não pode ser menor ou igual a zero.");}

        this.quantidade = quantidade;
        this.valor = valor;
        this.desconto = desconto;
    }

    //Getters
    public int getQuantidade() { return quantidade; }
    public double getValor() { return valor;}
    public double getDesconto(){ return desconto;}

    public String toString() {
        return "Valor: R$ " + String.format("%.2f", valor) + " | Desconto: " + String.format("R$ %.2f", desconto) + " | Quantidade: " + quantidade  ;
    }
    
}
