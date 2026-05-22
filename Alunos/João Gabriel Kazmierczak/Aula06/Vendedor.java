package fag;

import java.util.ArrayList;

public class Vendedor {

	String nome;
	int idade;
	Loja loja;
	String cidade;
	String bairro;
	String rua;
	double salarioBase;
	ArrayList<Double> salarios = new ArrayList<>();

	public Vendedor(String nome, int idade, Loja loja, String cidade, String bairro, String rua, double salarioBase) {
		this.nome = nome;
		this.idade = idade;
		this.loja = loja;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.salarioBase = salarioBase;

		salarios.add(2000.0);
		salarios.add(2100.0);
		salarios.add(2200.0);
	}

	public void apresentarSe() {
		System.out.println(nome + " - " + idade + " - " + loja.nomeFantasia);
	}

	public double calcularMedia() {
		double soma = 0;
		for (double s : salarios) soma += s;
		return soma / salarios.size();
	}

	public double calcularBonus() {
		return salarioBase * 0.2;
	}
}