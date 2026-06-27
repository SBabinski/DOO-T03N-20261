package view;

import exception.ExcecaoUsuario;
import model.Serie;
import model.Usuario;
import service.ServicoSerie;

import java.util.List;

/**
 * Tela que exibe e gerencia a lista de séries favoritas do usuário.
 */
public class TelaFavoritos extends TelaListaSeries {

    /**
     * Constrói a tela de favoritos.
     */
    public TelaFavoritos(Usuario usuario, ServicoSerie servicoSerie) {
        super(usuario, servicoSerie);
    }

    @Override
    protected String obterTituloTela() {
        return "⭐ Favoritos";
    }

    @Override
    protected List<Serie> obterListaSeries() {
        return usuario.obterFavoritos();
    }

    @Override
    protected void removerSerie(Serie serie) throws ExcecaoUsuario {
        servicoSerie.removerFavorito(usuario, serie);
    }
}
