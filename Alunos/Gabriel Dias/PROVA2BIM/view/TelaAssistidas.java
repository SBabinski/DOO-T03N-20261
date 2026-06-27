package view;

import exception.ExcecaoUsuario;
import model.Serie;
import model.Usuario;
import service.ServicoSerie;

import java.util.List;

/**
 * Tela que exibe e gerencia a lista de séries já assistidas pelo usuário.
 */
public class TelaAssistidas extends TelaListaSeries {

    /**
     * Constrói a tela de séries já assistidas.
     */
    public TelaAssistidas(Usuario usuario, ServicoSerie servicoSerie) {
        super(usuario, servicoSerie);
    }

    @Override
    protected String obterTituloTela() {
        return "✅ Já Assistidas";
    }

    @Override
    protected List<Serie> obterListaSeries() {
        return usuario.obterJaAssistidas();
    }

    @Override
    protected void removerSerie(Serie serie) throws ExcecaoUsuario {
        servicoSerie.removerJaAssistida(usuario, serie);
    }
}
