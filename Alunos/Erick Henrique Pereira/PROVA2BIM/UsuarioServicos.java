package servicos;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import objetos.Serie;
import objetos.Usuario;

public class UsuarioServicos {
    private Usuario usuario;
    private final PersistenciaDados persistencia;

    public UsuarioServicos() {
        this.persistencia = new PersistenciaDados(new com.fasterxml.jackson.databind.ObjectMapper());
        carregarOuCriar();
    }

	public UsuarioServicos(PersistenciaDados persistencia) {
		this.persistencia = persistencia;
        carregarOuCriar();
	}

    private void carregarOuCriar(){
        try{
            Usuario carregado = persistencia.carregar();
            if(carregado != null){
                this.usuario = carregado;
            }else{
                this.usuario = new Usuario();
            } 
        }catch (IOException excessao){
                this.usuario = new Usuario();
            }
    }

    public void salvar() throws IOException{
        persistencia.salvar(usuario);
    }
    public Usuario getUsuario(){
        return usuario;
    }
    public void setNomeUsuario(String nome){
        usuario.setNome(nome);
    }
    public void adicionarFavorito(Serie serie){
        usuario.adicionarFavorito(serie);
    }
    public void removerFavorito(Serie serie){
        usuario.removerFavorito(serie);
    }
    public void adicionarAssistidos(Serie serie){
        usuario.adicionarAssistidos(serie);
    }
    public void removerAssistidos(Serie serie){
        usuario.removerAssistidos(serie);
    }
    public void adicionarAssistir(Serie serie){
        usuario.adicionarAssistir(serie);
    }
    public void removerAssistir(Serie serie){
        usuario.removerAssistir(serie);
    }
    public void ordenarPorNome(List<Serie> lista){
        lista.sort(Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));
    }

    public void ordenarPorNota(List<Serie> lista){
        lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
    }

    public void ordenarPorStatus(List<Serie> lista){
        lista.sort(Comparator.comparing(Serie::getStatus, String.CASE_INSENSITIVE_ORDER));
    }

    public void ordenarPorDataEstreia(List<Serie> lista){
        lista.sort(Comparator.comparing(serie -> serie.getEstreia() == null ? "" : serie.getEstreia()));
    }
}
