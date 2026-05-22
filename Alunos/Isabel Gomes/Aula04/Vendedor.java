package fag.objeto;
import java.util.ArrayList;
import java.util.List;

public class Vendedor {
	
	private String nome;
	private int idade;
	private String cidade;
	private String bairro;
	private String rua;
	private double salarioBase;
	private double salarioRecebido;	
	private String loja;
	
	public Vendedor(String nome, String cidade, String bairro, String rua, String loja, 
			int idade, double salarioBase, double salarioRecebido) {
		this.nome = nome;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.loja = loja;
		this.idade = idade;
		this.salarioBase = salarioBase;
		this.salarioRecebido = salarioRecebido; 
	}
	

}
