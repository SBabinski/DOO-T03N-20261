package servicos;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import objetos.Usuario;

public class PersistenciaDados{
    private static final String ARQUIVO = "dados.json";
    private final ObjectMapper objectMapper;
    public PersistenciaDados(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public void salvar(Usuario usuario) throws IOException{
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO), usuario);
    }

    public Usuario carregar() throws IOException{
        File arquivo = new File(ARQUIVO);
        if(!arquivo.exists()){
            return null;
        }
        return objectMapper.readValue(arquivo, Usuario.class);
    }


    

}