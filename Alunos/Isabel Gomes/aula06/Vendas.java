package fag.objeto;
import fag.Calculadora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vendas {
	
	public Vendas() {
		
	}
	
	public Vendas(int quantidade, double precoTotal, double valorUnitario,
				  boolean desconto, LocalDate data) {
		setQuantidade(quantidade);
		setPrecoTotal(precoTotal);
		setValorUnitario(valorUnitario);
	}
	
	private int quantidade;
	private double precoTotal;
	private double valorUnitario;
	private boolean desconto;
	private LocalDate data;
	
	private Cliente cliente;
    private Vendedor vendedor;
	
	public static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	//getters
	
	public LocalDate getData() {
		return data;
	}
	
	public double getPrecoTotal() {
		return precoTotal;
	}
	
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public double getValorUnitario() {
		return valorUnitario;
	}
	
	public boolean isDesconto() {
		return desconto;
	}
	
	public Cliente getCliente() { 
		return cliente; 
	}
	
    public Vendedor getVendedor() { 
    	return vendedor; 
    }
	
	
	//setters
	
	public void setData(String data) {
		this.data = LocalDate.parse(data, FORMATO);
	}
	
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
	
	
	public void setCliente(Cliente cliente) { 
		this.cliente = cliente; 
	}
	
    public void setVendedor(Vendedor vendedor) { 
    	this.vendedor = vendedor; 
    }
	
	
	
	
	
	
	//metodos
	public void alterarDesconto() {
		desconto = !desconto;
	}

	public void mostrarResumo() {
		System.out.println("Data da venda   : " + data.format(FORMATO));
		System.out.println("Plantas vendidas: " + quantidade);
		System.out.printf("Valor da venda: R$%.2f", precoTotal);
		if(desconto) {
			System.out.println("\nPossui desconto de 5%");
		}else {
			System.out.println("\nNao possui desconto");
		}
		System.out.println("Vendedor : " + vendedor.getNome());
        System.out.println("Cliente : " + cliente.getNome());
	}

	

	
	
	
	
	//criar metodos descontos bool
	
	
	//metodo imprimir a listagem d evendas
	
	
	

}
