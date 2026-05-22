
public class Pessoa {

	public String nome;
	public int idade;
	
	public Pessoa(String nome, int idade) {
		setNome(nome);
		setIdade(idade);
	}
	
	//geters
	public String getNome() {
		return nome;
	}
	
	public int getIdade() {
		return idade;
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
	
	
	//metodos
	public void apresentarse() {
		System.out.println("\nNome: " + nome);
		System.out.println("Idade: " + idade);
	}
	
	
	
	//pessoa > cliente e vendedor. vendedor> gerente
	//fazer override apresentarse
	//criar classe endereço, linkar e puxar metodo;
}
