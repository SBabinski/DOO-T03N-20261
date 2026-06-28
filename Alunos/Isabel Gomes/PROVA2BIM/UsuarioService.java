package fag;

import java.util.List;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

public class UsuarioService {

//cuida da lista de favoritos
	public void adicionarFavorito(Usuario usuario, Serie serie) {

		if (!usuario.getFavoritos().contains(serie)) {
			usuario.getFavoritos().add(serie);
		}
	}

	public void removerFavorito(Usuario usuario, Serie serie) {

		usuario.getFavoritos().remove(serie);
	}

//cuida da lista de ja assistidos
	public void adicionarAssistido(Usuario usuario, Serie serie) {

		if (!usuario.getAssistidas().contains(serie)) {
			usuario.getAssistidas().add(serie);
		}
	}

	public void removerAssistido(Usuario usuario, Serie serie) {

		usuario.getAssistidas().remove(serie);
	}

//cuidada lista de desejados
	public void adicionarDesejaAssistir(Usuario usuario, Serie serie) {

		if (!usuario.getDesejaAssistir().contains(serie)) {
			usuario.getDesejaAssistir().add(serie);
		}
	}

	public void removerDesejaAssistir(Usuario usuario, Serie serie) {

		usuario.getDesejaAssistir().remove(serie);
	}
	
	
// ORDENAÇÕES
	
	//NOME
	public void ordenarPorNome(List<Serie> lista) {
		Collections.sort(lista,Comparator.comparing(Serie::getNome));
	}
	
	//MNOTA
	public void ordenarPorNota(List<Serie> lista) {
		//reversed pega do maior pro menor, se não, seria do menor pro maior
		Collections.sort(lista, Comparator.comparing(Serie::getNotaGeral).reversed());	
	}
	
	//STATUS
	public void ordenarPorStatus(List<Serie> lista) {

	    Collections.sort(lista, Comparator.comparingInt(this::prioridadeStatus)
	    );
	}

	private int prioridadeStatus(Serie serie) {

	    switch (serie.getStatus()) {

	        case "Running":
	            return 1;

	        case "In Development":
	            return 2;

	        case "Ended":
	            return 3;

	        case "Canceled":
	            return 4;

	        default:
	            return 5;
	    }
	}
	
	//DATA
	public void ordenarPorDataEstreia(List<Serie> lista) {
		
		Collections.sort(lista, Comparator.comparing(this::converterData));
		
	}
	
	//deixei private pq so essa classe mexe
	private LocalDate converterData(Serie serie) {
		
		try {
			
			return LocalDate.parse(serie.getDataInicio());
			
		}catch (Exception e){
			
			return LocalDate.MAX;//retorna o maior numero possivel
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}