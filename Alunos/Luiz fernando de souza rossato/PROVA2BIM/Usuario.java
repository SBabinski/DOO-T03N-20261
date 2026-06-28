package tv;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    
    private List<Serie> seriesAssistidas;
    private List<Serie> seriesFavoritas;
    private List<Serie> seriesParaAssistir;

    public Usuario() {
        seriesAssistidas = new ArrayList<>();
        seriesFavoritas = new ArrayList<>();
        seriesParaAssistir = new ArrayList<>();
    }

    public Usuario(String nome, List<Serie> seriesAssistidas,
         List<Serie> seriesFavoritas, List<Serie> seriesParaAssistir) {
        this.nome = nome;
        this.seriesAssistidas = seriesAssistidas;
        this.seriesFavoritas = seriesFavoritas;
        this.seriesParaAssistir = seriesParaAssistir;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getseriesFavoritas(){
        return seriesFavoritas;
    }

    public List<Serie> getseriesAssistidas(){
        return seriesAssistidas;
        
    }

    public List<Serie> getseriesParaAssistir(){
        return seriesParaAssistir;
    }

   public void adicionarSerieFavoritas(Serie serie) {

    if(!seriesFavoritas.contains(serie)) {
        seriesFavoritas.add(serie);
    }
}

public void adicionarSeriesAssistida(Serie serie) {

    if(!seriesAssistidas.contains(serie)) {
        seriesAssistidas.add(serie);
    }
}

public void adicionarSeriesParaAssistir(Serie serie) {

    if(!seriesParaAssistir.contains(serie)) {
        seriesParaAssistir.add(serie);
    }
}

public void removerSeriesFavoritas(Serie serie){
    seriesFavoritas.remove(serie);
}

public void removerSeriesAssistida(Serie serie){
    seriesAssistidas.remove(serie);
}

public void removerSeriesParaAssistir(Serie serie){
    seriesParaAssistir.remove(serie);
}
}