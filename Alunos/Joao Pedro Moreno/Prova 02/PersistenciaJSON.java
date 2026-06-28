package com.seriestv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.*;


//Responsável por salvar e carregar os dados do usuário em formato JSON. O arquivo é salvo na pasta do usuário do sistema operacional.
public class PersistenciaJSON {

    private static final String ARQUIVO = "seriestv_dados.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    
    //Salva o usuário e suas listas em um arquivo JSON.
    public void salvar(Usuario usuario) throws Exception {
        String json = gson.toJson(usuario);
        Files.writeString(Path.of(ARQUIVO), json);
    }

    
    //Carrega o usuário do arquivo JSON. Se o arquivo não existir, retorna null.
    public Usuario carregar() throws Exception {
        Path caminho = Path.of(ARQUIVO);
        if (!Files.exists(caminho)) {
            return null; // primeira vez usando o programa
        }
        String json = Files.readString(caminho);
        return gson.fromJson(json, Usuario.class);
    }

    
    //Verifica se já existe um arquivo de dados salvo.
    public boolean existeDadosSalvos() {
        return Files.exists(Path.of(ARQUIVO));
    }
}
