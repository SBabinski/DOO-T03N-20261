package fag.objeto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Contrato {
	
	private Inquilino inquilino;
	private Imoveis imovel;
	private LocalDate dataInicio = null;
	private LocalDate dataFim;
	private boolean situacao;
	
	public static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public Contrato(Inquilino inquilino, Imoveis imovel, 
			 boolean situacao) {
		setInquilino(inquilino);
		setImovel(imovel);
		//setDataInicio(dataInicio);
		this.situacao = situacao;
	}
	
	//ghetters
	public Inquilino getInquilino() {
		return inquilino;
	}
	
	public Imoveis getImovel() {
		return imovel;
	}
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	
	public LocalDate getDataFim() {
		return dataFim;
	}
	
	//setters
	public void setInquilino(Inquilino inquilino) {
		this.inquilino = inquilino;
	}
	
	public void setImovel(Imoveis imovel) {
		this.imovel = imovel;
	}
	
	public void setDataInicio(String dataInicio) {
		this.dataInicio = LocalDate.parse(dataInicio, FORMATO);
	}
	
	public void setDataFim(String dataFim) {
		this.dataFim = LocalDate.parse(dataFim, FORMATO);
	}
	
	public void setSituacao() {
		this.situacao = situacao;
	}
	
	public boolean isSituacao() {
		return situacao;
	}
	
	public void alterarSituacao() {
		situacao = !situacao;
	}
	
	
	//metodos
	Period periodo = Period.between(dataInicio, dataFim);
	long meses = periodo.toTotalMonths();
	
	
	public double calcularValorAluguel(long meses, Imoveis imovel) {
		
		double valorTotal = meses*imovel.getValorAluguel();
		return valorTotal;
	}
	
	
	public void visualizarDados() {
		System.out.println("\n ---- DADOS DO CONTRATO ----\n");
		System.out.println("INQUILINO: " + inquilino.getNome());
		System.out.println("IMOVEL: \n" );
		imovel.apresentarse();
		System.out.println("INICIO DO CONTRATO: " + dataInicio.format(FORMATO));
		System.out.println("FIM DO CONTRATO: " + dataFim.format(FORMATO));
		if(situacao) {
			System.out.println("\nCONTRATO ENCERRADO");
		}else {
			System.out.println("\nCONTRATO NAO ENCERRADO");
		}
		System.out.printf("VALOR TOTAL DO CONTRATO R$: %.2f", calcularValorAluguel(meses, imovel));
	}
	
	
	
	
	

}
