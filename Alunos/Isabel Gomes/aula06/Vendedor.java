package fag.objeto;
import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Pessoa {
	
	public String nome;
	public int idade;
	public String cidade;
	public String bairro;
	public String rua;
	public double salarioBase;	
	public String loja;
	
	public List<Double> salarioRecebido = new ArrayList<>();	
	
	
	public Vendedor(String nome, String cidade, String bairro, String rua, String loja, 
			int idade, double salarioBase) {
		this.nome = nome;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.loja = loja;
		this.idade = idade;
		this.salarioBase = salarioBase;
		//this.salarioRecebido = salarioRecebido; 
	}
	
	
	//getters
	public String getNome() {
		return nome;
	}
	
	public int getIdade() {
		return idade;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public String getBairro() {
		return bairro;
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
	
	public String getRua() {
		return rua;
	}


	//setters
	public void setNome(String nome) {
		if(nome!= null && !nome.isBlank()) {
			this.nome = nome;
		}
		
	}

	

	public void setIdade(int idade) {
		if(idade> 0) {
			this.idade = idade;
		}
		
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	

	public void setRua(String rua) {
		this.rua = rua;
	}

	
	public void setSalarioBase(double salarioBase) {
		if(salarioBase>0) {
			this.salarioBase = salarioBase;
		}
	}	

	public void setLoja(String loja) {
		this.loja = loja;
	}
	
	
	//metodos
	
	@override
	public void apresentarse() {
		System.out.println("\nNOme: " + nome);
		System.out.println("Idade: " + idade);
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
