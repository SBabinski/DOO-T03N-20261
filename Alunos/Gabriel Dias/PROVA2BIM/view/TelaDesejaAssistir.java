package view;

import exception.ExcecaoUsuario;
import model.Serie;
import model.Usuario;
import service.ServicoSerie;

import java.util.List;

/**
 * Tela que exibe e gerencia a lista de séries que o usuário deseja assistir.
 */
public class TelaDesejaAssistir extends TelaListaSeries {

    /**
     * Constrói a tela de séries que o usuário deseja assistir.
     */
    public TelaDesejaAssistir(Usuario usuario, ServicoSerie servicoSerie) {
        super(usuario, servicoSerie);
    }

    @Override
    protected String obterTituloTela() {
        return "🕐 Deseja Assistir";
    }

    @Override
    protected List<Serie> obterListaSeries() {
        return usuario.obterDesejaAssistir();
    }

    @Override
    protected void removerSerie(Serie serie) throws ExcecaoUsuario {
        servicoSerie.removerDesejaAssistir(usuario, serie);
    }
}
