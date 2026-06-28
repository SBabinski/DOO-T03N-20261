package com.weatherapp.model;

public class WeatherData {
	private double tempAtual;
	private double tempMaxima;
	private double tempMinima;
	private double umidade;
	private String condicao;
	private double precipitacao;
	private double velocidadeVento;
	private double direcaoVento;

	public WeatherData(double tempAtual, double tempMaxima, double tempMinima, double umidade, String condicao,
			double precipitacao, double velocidadeVento, double direcaoVento) {
		this.tempAtual = tempAtual;
		this.tempMaxima = tempMaxima;
		this.tempMinima = tempMinima;
		this.umidade = umidade;
		this.condicao = condicao;
		this.precipitacao = precipitacao;
		this.velocidadeVento = velocidadeVento;
		this.direcaoVento = direcaoVento;
	}

	public double getTempAtual() {
		return tempAtual;
	}

	public double getTempMaxima() {
		return tempMaxima;
	}

	public double getTempMinima() {
		return tempMinima;
	}

	public double getUmidade() {
		return umidade;
	}

	public String getCondicao() {
		return condicao;
	}

	public double getPrecipitacao() {
		return precipitacao;
	}

	public double getVelocidadeVento() {
		return velocidadeVento;
	}

	public double getDirecaoVento() {
		return direcaoVento;
	}
}