package fag.objetos;

import java.util.ArrayList;
import java.util.List;

public class Gerente extends Vendedor{
	
	public Gerente(String nome, String loja, Endereco endereco,
			int idade, double salarioBase) {
		super(nome, loja, endereco, idade, salarioBase);		 
	}
	
	
	@Override
	public void calcularBonus() {
		double bonus = getSalarioBase() * 0.35;
		double salarioBonus = bonus+ getSalarioBase();
		System.out.println("\nSeu bonus e de R$: " + bonus + ", total com salario R$: " + salarioBonus);
	}

}
