package service;
import model.Usuario;

public class UsuarioService {

private Usuario usuario;

private JsonService json =
        new JsonService();

public Usuario getUsuarioAtual(){
    return usuario;
}

public void iniciar(String nome){
    usuario =
            new Usuario(nome);
}

public void salvar()
        throws Exception{
    json.salvar(usuario);
}

public void carregar()
        throws Exception{
    usuario =
            json.carregar();
}
}