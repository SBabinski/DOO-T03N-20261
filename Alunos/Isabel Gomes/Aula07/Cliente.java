package fag.objetos;
import fag.objetos.Endereco;

public class Cliente extends Pessoa {
	
	private Endereco endereco;
	
	public Cliente (String nome, int idade, Endereco endereco) {
		super(nome, idade);
		this.endereco = endereco;
	}
	
	
	
	//getters

	
	public Endereco getEndereco() {
		return endereco;
	}
	
	@Override
	public void apresentarse() {
	    super.apresentarse();
	    endereco.apresentarLogradouro();
	}
	
	
	
	

}

