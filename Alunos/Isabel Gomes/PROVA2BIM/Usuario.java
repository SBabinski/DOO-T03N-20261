package fag;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
	private String nome;
	private List<Serie> favoritos;
	private List<Serie> desejaAssistir;
	private List<Serie> assistidas;
	
	public Usuario() {
		//this.nome = nome;
		favoritos = new ArrayList<>();
		desejaAssistir = new ArrayList<>();
		assistidas = new ArrayList<>();
	}
	
	//getters
	public String getNome() {
		return nome;
	}
	
	public List<Serie> getFavoritos() {
		return favoritos;
	}
	
	public List<Serie> getDesejaAssistir() {
		return desejaAssistir;
	}
	
	public List<Serie> getAssistidas() {
		return assistidas;
	}
	
	//setters
	public void setNome(String nome) {
		if(nome != null && !nome.isBlank()) {
			this.nome = nome;	
		}
	}
	
	public void setFavoritos(List<Serie> favoritos) {
		this.favoritos = favoritos;
	}
	
	public void setDesejaAssistir(List<Serie> desejaAssistir) {
		this.desejaAssistir = desejaAssistir;
	}
	
	public void setAssistidas(List<Serie> assistidas) {
		this.assistidas = assistidas;
	}
	

}
