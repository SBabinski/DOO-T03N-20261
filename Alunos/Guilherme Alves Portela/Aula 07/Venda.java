import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {
    private int quantidade;
    private double valor;
    private double desconto; 
    private LocalDate data; 

    public Venda(int quantidade, double valor, double desconto){
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser maior que zero");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade inválida");

        this.quantidade = quantidade;
        this.valor = valor;
        this.desconto = desconto;
        this.data = LocalDate.now(); 
    }

    //Getters
    public LocalDate getData(){return this.data;}

    public double getValor(){return this.valor;}

    public int getMes(){ return this.data.getMonthValue();}

    @Override
    public String toString(){
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return String.format("Data: %s | Valor: R$ %.2f | Qtd: %d | Desconto: %.2f", 
        this.data.format(parser), this.valor, this.quantidade, this.desconto);
    }
}