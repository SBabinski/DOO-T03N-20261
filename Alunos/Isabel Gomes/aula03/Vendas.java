package fag.objeto;
import fag.Calculadora;

public class Vendas {
	
	
	private int quantidade;
	private double precoTotal;
	private double valorUnitario;
	private boolean desconto;
	
	//getters
	
	public double getPrecoTotal() {
		return precoTotal;
	}
	
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public double getValorUnitario() {
		return valorUnitario;
	}
	
	/*public int getDesconto() {
		return desconto;
	}*/
	
	
	//setters
	public void setQuantidade(int quantidade) {
		if(quantidade > 0) {
			this.quantidade = quantidade;
		}else {
			System.out.println("\nINsira uma quantidade valida!\n");
			setQuantidade(Calculadora.scan.nextInt());
		}
	}
	
	
	public void setValorUnitario(double valorUnitario) {
		if(valorUnitario > 0) {
			this.valorUnitario = valorUnitario;
		}else {
			System.out.println("\ninsira um valor valido!\n");
			setValorUnitario(Calculadora.scan.nextDouble());
		}
		
	}
	
	public void setPrecoTotal(double precoTotal) {
		this.precoTotal = precoTotal;
	}
	
	
	/*public double valorFinal() {
		return valorFinal;
	}
	
	public void setValorFinal(double valorFinal) {
		this.valorFinal = valorFinal;
	}*/
	
	
	public boolean isDesconto() {
		return desconto;
	}
	
	public void alterarDesconto() {
		desconto = !desconto;
	}
	
	//metodos
	

	public void mostrarResumo() {
		System.out.println("Plantas vendidas: " + quantidade);
		System.out.printf("Valor da venda: R$%.2f", precoTotal);
		if(desconto) {
			System.out.println("\nPossui desconto de 5%");
		}else {
			System.out.println("\nNao possui desconto");
		}
	}

	

	
	
	
	
	//criar metodos descontos bool
	
	
	//metodo imprimir a listagem d evendas
	
	
	

}
