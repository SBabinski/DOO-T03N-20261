package fag.objetos;
import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Pessoa {
	
	private Endereco endereco;
	private double salarioBase;	
	private String loja;
	
	public List<Double> salarioRecebido = new ArrayList<>();	
	
	
	public Vendedor(String nome, String loja, Endereco endereco,
			int idade, double salarioBase) {
		super(nome, idade);
		this.endereco = endereco;
		this.loja = loja;
		this.salarioBase = salarioBase;
		//this.salarioRecebido = salarioRecebido; 
	}
	
	
	//getters
	
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public double getSalarioBase() {
		return salarioBase;
	}
	
	/* double getSalarioRecebido() {
		return salarioRecebido;
	}*/
	
	public String getLoja() {
		return loja;
	}
	
	


	//setters

	
	public void setSalarioBase(double salarioBase) {
		if(salarioBase>0) {
			this.salarioBase = salarioBase;
		}
	}	

	public void setLoja(String loja) {
		this.loja = loja;
	}
	
	
	//metodos
	
	@Override
	public void apresentarse() {
		super.apresentarse();
		System.out.println("Loja: " + loja);
	}
	
	
	public void adicionarSalarioRecebido( double salario) {
		salarioRecebido.add(salario);
	}
	
	
	public void CalcularMedia() {
		int ct =0 ;
		double soma = 0;
		for(int i=0; i<salarioRecebido.size(); i++) {
			soma+= salarioRecebido.get(i);
			ct++;
		}
		double media = soma/ct;
		System.out.printf("\nMedia salarial: R$%.2f", media);
	}
	
	
	public void calcularBonus() {
		double bonus = salarioBase * 0.2;
		double salarioBonus = bonus+salarioBase;
		System.out.println("\nSeu bonus e de R$: " + bonus + ", total com salario R$: " + salarioBonus);
	}
	
	
	
	
	
	

}
