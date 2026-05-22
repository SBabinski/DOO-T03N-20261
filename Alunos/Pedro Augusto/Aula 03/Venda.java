public class Venda {
    private int qtdPlantas;
    private float valorTotal;
    private float valorDesconto;
    
    public Venda(int qtdPlantas, float valorTotal, float valorDesconto) {
        this.setQtdPlantas(qtdPlantas);
        this.setValorTotal(valorTotal);
        this.setValorDesconto(valorDesconto);
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

    public void exibirRegistro() {
        System.out.print("Valor: R$" + this.getValorTotal() + " - ");
        System.out.print("Plantas: " + this.getQtdPlantas() + " - ");
        System.out.println("Desconto: R$" + this.getValorDesconto());
    }
}
