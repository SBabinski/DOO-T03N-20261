package fag.objeto;

public class Cliente extends Pessoa {
	
	private String nome;
	private int idade;
	private String cidade;
	private String bairro;
	private String rua;
	
	public Cliente (String nome, int idade, String cidade, String bairro, String rua) {
		
		this.nome = nome;
		this.idade = idade;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
	}
	
	public Cliente() {
		
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
	
	public String getRua() {
		return rua;
	}
	
	
	//setters
	
	public void setNome(String nome) {
		if(nome != null && !nome.isBlank()) {
			this.nome = nome;
		}
	}
	
	public void setIdade(int idade) {
		if(idade > 0) {
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
	
	
	//metodos
	
	@override
	public void apresentarse() {
		System.out.println("Nome: " + nome);
		System.out.println("Idade: "+ idade);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
