package fag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Serie {
	
	private String nome;
	private int id;
	private String idioma;
	private List<String> generos;
	private double notaGeral;
	private String status;
	private String dataInicio;
	private String dataFim;
	private String emissora;
	
	public Serie () {
		generos = new ArrayList<>();
	}
	
	
	//getters
	public String getNome() {
		return nome;
	}
	
	public int getId() {
		return id;
	}
	
	public String getIdioma() {
		return idioma;
	}
	
	public List<String> getGeneros() {
		return generos;
	}
	
	public double getNotaGeral() {
		return notaGeral;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getDataInicio() {
		return dataInicio;
	}
	
	public String getDataFim() {
		return dataFim;
	}
	
	public String getEmissora() {
		return emissora;
	}
	
	//setters
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}
	
	public void setNotaGeral(double notaGeral) {
		this.notaGeral = notaGeral;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
	public void setEmissora(String emissora) {
		this.emissora = emissora;
	}
	
	@Override
	public String toString() {
		return nome;
				
	}
	
	@Override
    public boolean equals(Object obj) {
//verifica o objeto selecionado
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Serie outraSerie = (Serie) obj;

        return id == outraSerie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
