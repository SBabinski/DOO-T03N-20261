package fag.objeto;

public class Apartamento extends Imoveis{
	
	private int andar;
	
	
	public Apartamento(String endereco, double valorAluguel, int andar) {
		super(endereco, valorAluguel);
		setAndar(andar);
	}
	

	public int getAndar() {
		return andar;
	}

	public void setAndar(int andar) {
		this.andar = andar;
	}
	
	
	//metodos
	
	@Override
	public void apresentarse() {
		System.out.println("\nEndereco do imovel: " + endereco);
		System.out.printf("Valor do aluguel do imovel R$: %.2f", valorAluguel);
		System.out.println("Andar do apartamento: " + andar);
	}
	
	

}
