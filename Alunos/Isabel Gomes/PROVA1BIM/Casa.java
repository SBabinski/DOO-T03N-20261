package fag.objeto;

public class Casa extends Imoveis{
	
	private boolean quintal;
	
	public Casa(String endereco, double valorAluguel, boolean quintal) {
		super(endereco, valorAluguel);
		this.quintal = quintal;
	}
	
	//getter setters
	public boolean isQuintal() {
		return quintal;
	}
	
	public void setQuintal(int escolha){
		this.quintal = quintal;
	}
	public void alterarQuintal() {
		quintal = !quintal;
	}
	
	//metodos
	
	@Override
	public void apresentarse() {
		System.out.println("\nEndereco do imovel: " + endereco);
		System.out.printf("Valor do aluguel do imovel RS: %.2f", valorAluguel);
		System.out.println("A casa tem quintal?: " + quintal);
	}

	

}
