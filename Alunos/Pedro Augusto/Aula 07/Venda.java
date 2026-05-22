import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {
    private int qtdPlantas;
    private float valorTotal;
    private float valorDesconto;
    private LocalDate dataVenda;

    public Venda(int qtdPlantas, float valorTotal, float valorDesconto, int dia, int mes, int ano) {
        this.qtdPlantas = qtdPlantas;
        this.valorTotal = valorTotal;
        this.valorDesconto = valorDesconto;
        this.dataVenda = LocalDate.of(ano, mes, dia);
    }

    public int getQtdPlantas() { return qtdPlantas; }
    public float getValorTotal() { return valorTotal; }
    public float getValorDesconto() { return valorDesconto; }
    public LocalDate getDataVenda() { return dataVenda; }

    public void exibirRegistro() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = this.dataVenda.format(formatador);

        System.out.print("Data: " + dataFormatada + " - ");
        System.out.print("Valor: R$" + this.valorTotal + " - ");
        System.out.print("Plantas: " + this.qtdPlantas + " - ");
        System.out.println("Desconto: R$" + this.valorDesconto);
    }
}
