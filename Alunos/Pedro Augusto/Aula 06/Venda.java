import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Venda {
    private int qtdPlantas;
    private float valorTotal;
    private float valorDesconto;
    private LocalDate dataVenda;

    public Venda(int qtdPlantas, float valorTotal, float valorDesconto, int dia, int mes, int ano) {
        this.setQtdPlantas(qtdPlantas);
        this.setValorTotal(valorTotal);
        this.setValorDesconto(valorDesconto);
        this.setDataVenda(dia, mes, ano);
    }

    public int getQtdPlantas() {
        return this.qtdPlantas;
    }

    public float getValorTotal() {
        return this.valorTotal;
    }
    public float getValorDesconto() {
        return this.valorDesconto;
    }

    public LocalDate getDataVenda() {
        return this.dataVenda;
    }

    public void setQtdPlantas(int qtdPlantas) {
        if (qtdPlantas > 0) {
            this.qtdPlantas = qtdPlantas;
        } 
    }

    public void setValorTotal(float valorTotal) {
        if (valorTotal > 0) {
            this.valorTotal = valorTotal;
        } 
    }
    public void setValorDesconto(float valorDesconto) {
        if (valorDesconto > 0) {
            this.valorDesconto = valorDesconto;
        } 
    }

    public void setDataVenda(int dia, int mes, int ano) {
        LocalDate dataDaVenda = LocalDate.of(ano, mes, dia);
        this.dataVenda = dataDaVenda;
    }

    public void exibirRegistro() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = this.dataVenda.format(formatador);

        System.out.print("Data: " + dataFormatada + " - ");
        System.out.print("Valor: R$" + this.getValorTotal() + " - ");
        System.out.print("Plantas: " + this.getQtdPlantas() + " - ");
        System.out.println("Desconto: R$" + this.getValorDesconto());
    }
}
