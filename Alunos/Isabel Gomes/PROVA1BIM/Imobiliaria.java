package fag.objeto;

public class Imobiliaria {
	
	public Contrato[] contratos = new Contrato[10];
	private int contador=0;
	
	
	public void adicionarContratos(Contrato contrato) {
		if(contador<10) {
			contratos[contador] = contrato;
			contador++;
		}else {
			System.out.println("\nA lista de contratos ja esta cheia!");
		}
	}
	
	
	public void listarEncerrados() {
		for(int i=0; i<contador; i++) {
			if(contratos[i].isSituacao()) {
				System.out.println((i+1) + " - " );
				contratos[i].visualizarDados();
				System.out.println("\n------\n");
			}
		}
	}
	
	public void listarAtivos() {
		for(int i=0; i<10; i++) {
			if(!contratos[i].isSituacao()) {
				System.out.println("\n ---- " + (i+1));
				contratos[i].visualizarDados();
				System.out.println("\n------\n");
			}
		}
	}
	
	public void encerrarContrato(int escolha) {
		if(escolha>0 && escolha<contratos.length) {
			Contrato contrato = contratos[escolha];
			contrato.alterarSituacao();
			System.out.println("\nContrato encerrado!");
		}else {
			System.out.println("\nEscolha invalida!");
		}
		
	}
	
	

}
