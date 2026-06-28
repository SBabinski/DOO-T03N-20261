package app.service;

import app.model.Serie;
import app.model.Usuario;

public class UsuarioService {

    private static UsuarioService instancia;

    private Usuario usuario;
    private JsonService jsonService;

    private UsuarioService() {

        jsonService = new JsonService();

        usuario = jsonService.carregar();

        if (usuario == null) {

            usuario = new Usuario("Convidado");

            jsonService.salvar(usuario);

        }

    }

    public static UsuarioService getInstance() {

        if (instancia == null) {

            instancia = new UsuarioService();

        }

        return instancia;

    }

    public Usuario getUsuario() {

        return usuario;

    }

    public void setUsuario(Usuario usuario) {

        this.usuario = usuario;

        jsonService.salvar(usuario);

    }

    // FAVORITOS

    public void adicionarFavorito(Serie serie) {

        usuario.adicionarFavorito(serie);

        jsonService.salvar(usuario);

    }

    public void removerFavorito(Serie serie) {

        usuario.removerFavorito(serie);

        jsonService.salvar(usuario);

    }

    // ASSISTIDAS

    public void adicionarAssistida(Serie serie) {

        usuario.adicionarAssistida(serie);

        jsonService.salvar(usuario);

    }

    public void removerAssistida(Serie serie) {

        usuario.removerAssistida(serie);

        jsonService.salvar(usuario);

    }

    // DESEJO

    public void adicionarDesejo(Serie serie) {

        usuario.adicionarDesejo(serie);

        jsonService.salvar(usuario);

    }

    public void removerDesejo(Serie serie) {

        usuario.removerDesejo(serie);

        jsonService.salvar(usuario);

    }

    // DEBUG

    public void mostrarFavoritos() {

        System.out.println("\n===== FAVORITOS =====");

        for (Serie serie : usuario.getFavoritos()) {

            System.out.println(serie.getNome());

        }

    }

    public void mostrarAssistidas() {

        System.out.println("\n===== ASSISTIDAS =====");

        for (Serie serie : usuario.getAssistidas()) {

            System.out.println(serie.getNome());

        }

    }

    public void mostrarDesejos() {

        System.out.println("\n===== DESEJO =====");

        for (Serie serie : usuario.getDesejoAssistir()) {

            System.out.println(serie.getNome());

        }

    }
    public boolean primeiroAcesso() {

    return "Convidado".equals(usuario.getNome());

    }
}