package fag.objeto;

public class Inquilino {
	
	
	private String nome;
	private String cpf;
	private String telefone;
	
	
	public Inquilino(String nome, String cpf, String telefone) {
		setNome(nome);
		setCpf(cpf);
		setTelefone(telefone);
	}
	
	
	//getters
	public String getNome() {
		return nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	//setters
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	//metodos
	public void apresentarse() {
		System.out.println("\nNOME: " + nome);
		System.out.println("CPF: " + cpf);
		System.out.println("TELEFONE: " + telefone);
	}
	

}
