package fag;

public class Clima {
	
	private double temperatura;
	private double tempMaxima;
	private double tempMinima;
	private double humidade;
	private String condicao;
	private double qtdChuva;
	private double dirVento;
	private double velVento;	
	
	//getters
	public double getTemperatura() {
		return temperatura;
	}
	
	public double getTempMaxima() {
		return tempMaxima;
	}
	
	public double getTempMinima() {
		return tempMinima;
	}
	
	public double getHumidade() {
		return humidade;
	}
	
	public String getCondicao() {
		return condicao;
	}
	
	public double getQtdChuva() {
		return qtdChuva;
	}
	
	public double getDirVento() {
		return dirVento;
	}
	
	public double getVelVento() {
		return velVento;
	}
	
	//setters
	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}
	
	public void setTempMaxima(double tempMaxima) {
		this.tempMaxima = tempMaxima;
	}
	
	public void setTempMinima(double tempMinima) {
		this.tempMinima = tempMinima;
	}
	
	public void setHumidade(double humidade) {
		this.humidade = humidade;
	}
	
	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}
	
	public void setQtdChuva(double qtdChuva) {
		this.qtdChuva = qtdChuva;
	}
	
	public void setDirVento(double dirVento) {
		this.dirVento = dirVento;
	}
	
	public void setVelVento(double velVento) {
		this.velVento = velVento;
	}

}
