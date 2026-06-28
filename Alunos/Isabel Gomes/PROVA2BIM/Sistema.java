package fag;

import java.util.List;

public class Sistema {

	private Usuario usuario;
	private ApiTvMaze api;
	private JsonService jsonService;
	private UsuarioService usuarioService;

	public Sistema() {

		api = new ApiTvMaze();
		jsonService = new JsonService();
		usuarioService = new UsuarioService();

		if (jsonService.arquivoExiste()) {

			usuario = jsonService.carregarUsuario();

			// aqui verifica se existe um usuario nulo num arquivo ja criado
			if (usuario == null) {
				usuario = new Usuario();
			}

		} else {
			usuario = new Usuario();
		}

	}

	//cuida do usuario
	public Usuario getUsuario() {
		return usuario;
	}
	
	public String getNomeUsuario() {
	    return usuario.getNome();
	}
	
	public UsuarioService getUsuarioService() {
	    return usuarioService;
	}

	public void setNomeUsuario(String nome) {

		usuario.setNome(nome);

		salvar();
	}

	//cuida da api
	public List<Serie> buscarSeries(String nome) {

		return api.buscarSeries(nome);
	}

	//cuia da lista de favoritos
	public void adicionarFavorito(Serie serie) {

		usuarioService.adicionarFavorito(usuario, serie);

		salvar();
	}

	public void removerFavorito(Serie serie) {

		usuarioService.removerFavorito(usuario, serie);

		salvar();
	}

	//cuida da lista de assistidos
	public void adicionarAssistida(Serie serie) {

		usuarioService.adicionarAssistido(usuario, serie);

		salvar();
	}

	public void removerAssistida(Serie serie) {

		usuarioService.removerAssistido(usuario, serie);

		salvar();
	}

	//cuida da lista de desejos
	public void adicionarDesejaAssistir(Serie serie) {

		usuarioService.adicionarDesejaAssistir(usuario, serie);

		salvar();
	}

	public void removerDesejaAssistir(Serie serie) {

		usuarioService.removerDesejaAssistir(usuario, serie);

		salvar();
	}


	//salvatudo
	public void salvar() {

		jsonService.salvarUsuario(usuario);
	}

}
