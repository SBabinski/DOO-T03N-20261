package fag.objetos;

public class Item {
	
	private int id;
	private String nome;
	private String tipo;
	private double valor;
	
	public Item() {
		
	}
	
	public Item(int id, String nome, String tipo, double valor) {
		setId(id);
		setNome(nome);
		setTipo(tipo);
		setValor(valor);
	}
	
	
	//getters
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public double getValor() {
		return valor;
	}
	
	
	
	//seters
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	//metodos
	
	public void gerarDescriao() {
		System.out.println("\nID: " + id);
		System.out.println("Tipo: " + tipo);
		System.out.println("Nome: " + nome);
		System.out.printf("Valor R$: %.2f\n", valor);
	}
	
	
	
	
	


}
