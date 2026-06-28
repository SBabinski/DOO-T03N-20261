package service;

import api.ClienteTVMaze;
import exception.ExcecaoApi;
import exception.ExcecaoUsuario;
import model.Serie;
import model.Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Camada de serviço responsável pela lógica de negócio relacionada às séries.
 * Após cada operação de mutação (add/remove), dispara o callback de persistência
 * registrado via {@link #definirCallbackPersistencia(Runnable)}.
 */
public class ServicoSerie {

    private final ClienteTVMaze clienteApi;

    /** Callback chamado após qualquer alteração nas listas do usuário. */
    private Runnable callbackPersistencia;

    public ServicoSerie() {
        this.clienteApi = new ClienteTVMaze();
    }

    /**
     * Registra o callback que será invocado após cada add/remove.
     * Permite que a camada de serviço notifique a persistência sem depender dela.
     *
     * @param callback ação de salvamento
     */
    public void definirCallbackPersistencia(Runnable callback) {
        this.callbackPersistencia = callback;
    }

    /** Dispara o callback de persistência, se estiver registrado. */
    private void notificarPersistencia() {
        if (callbackPersistencia != null) {
            callbackPersistencia.run();
        }
    }

    // -------------------------------------------------------------------------
    // Pesquisa
    // -------------------------------------------------------------------------

    /**
     * Pesquisa séries pelo nome na API do TVMaze.
     *
     * @param nomeSerie nome da série a pesquisar
     * @return lista de séries encontradas
     * @throws ExcecaoUsuario se a pesquisa estiver vazia ou não encontrar resultados
     * @throws ExcecaoApi     se ocorrer erro na comunicação com a API
     */
    public List<Serie> pesquisarSeries(String nomeSerie) throws ExcecaoUsuario, ExcecaoApi {
        if (nomeSerie == null || nomeSerie.trim().isEmpty()) {
            throw new ExcecaoUsuario("Por favor, informe o nome da série para pesquisar.");
        }
        List<Serie> resultados = clienteApi.pesquisarSeries(nomeSerie.trim());
        if (resultados.isEmpty()) {
            throw new ExcecaoUsuario("Nenhuma série encontrada para: \"" + nomeSerie + "\".");
        }
        return resultados;
    }

    // -------------------------------------------------------------------------
    // Favoritos
    // -------------------------------------------------------------------------

    public void adicionarFavorito(Usuario usuario, Serie serie) throws ExcecaoUsuario {
        if (usuario.obterFavoritos().contains(serie)) {
            throw new ExcecaoUsuario("\"" + serie.obterNome() + "\" já está nos seus Favoritos.");
        }
        usuario.obterFavoritos().add(serie);
        notificarPersistencia();
    }

    public void removerFavorito(Usuario usuario, Serie serie) throws ExcecaoUsuario {
        if (!usuario.obterFavoritos().remove(serie)) {
            throw new ExcecaoUsuario("\"" + serie.obterNome() + "\" não está nos seus Favoritos.");
        }
        notificarPersistencia();
    }

    // -------------------------------------------------------------------------
    // Já Assistidas
    // -------------------------------------------------------------------------

    public void adicionarJaAssistida(Usuario usuario, Serie serie) throws ExcecaoUsuario {
        if (usuario.obterJaAssistidas().contains(serie)) {
            throw new ExcecaoUsuario("\"" + serie.obterNome() + "\" já está na lista Já Assistidas.");
        }
        usuario.obterJaAssistidas().add(serie);
        notificarPersistencia();
    }

    public void removerJaAssistida(Usuario usuario, Serie serie) throws ExcecaoUsuario {
        if (!usuario.obterJaAssistidas().remove(serie)) {
            throw new ExcecaoUsuario("\"" + serie.obterNome() + "\" não está na lista Já Assistidas.");
        }
        notificarPersistencia();
    }

    // -------------------------------------------------------------------------
    // Deseja Assistir
    // -------------------------------------------------------------------------

    public void adicionarDesejaAssistir(Usuario usuario, Serie serie) throws ExcecaoUsuario {
        if (usuario.obterDesejaAssistir().contains(serie)) {
            throw new ExcecaoUsuario("\"" + serie.obterNome() + "\" já está na lista Deseja Assistir.");
        }
        usuario.obterDesejaAssistir().add(serie);
        notificarPersistencia();
    }

    public void removerDesejaAssistir(Usuario usuario, Serie serie) throws ExcecaoUsuario {
        if (!usuario.obterDesejaAssistir().remove(serie)) {
            throw new ExcecaoUsuario("\"" + serie.obterNome() + "\" não está na lista Deseja Assistir.");
        }
        notificarPersistencia();
    }

    // -------------------------------------------------------------------------
    // Ordenação
    // -------------------------------------------------------------------------

    /**
     * Ordena uma lista de séries pelo critério informado.
     *
     * @param series   lista de séries a ordenar
     * @param criterio critério: "Nome", "Nota", "Estado" ou "Data de Estreia"
     * @return nova lista ordenada
     */
    public List<Serie> ordenarSeries(List<Serie> series, String criterio) {
        List<Serie> ordenadas = new ArrayList<>(series);
        Comparator<Serie> comparador;
        switch (criterio) {
            case "Nota":
                comparador = Comparator.comparingDouble(Serie::obterNota).reversed();
                break;
            case "Estado":
                comparador = Comparator.comparing(s -> s.obterEstado() != null ? s.obterEstado() : "");
                break;
            case "Data de Estreia":
                comparador = Comparator.comparing(s -> s.obterDataEstreia() != null ? s.obterDataEstreia() : "");
                break;
            default:
                comparador = Comparator.comparing(s -> s.obterNome() != null ? s.obterNome().toLowerCase() : "");
                break;
        }
        ordenadas.sort(comparador);
        return ordenadas;
    }
}
