package fag.objeto;

public class Imoveis {
	
	protected String endereco;
	protected double valorAluguel;
	
	public Imoveis(String endereco, double valorAluguel) {
		setEndereco(endereco);
		setValorAluguel(valorAluguel);
	}
	
	
	//getters
	public String getEndereco() {
		return endereco;
	}
	
	public double getValorAluguel() {
		return valorAluguel;
	}
	
	
	//setters
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public void setValorAluguel(double valorAluguel) {
		this.valorAluguel = valorAluguel;
	}
	
	//metodos
	public void apresentarse() {
		System.out.println("\nEndereco do imovel: " + endereco);
		System.out.printf("Valor do aluguel do imovel RS: %.2f", valorAluguel);
	}

}
