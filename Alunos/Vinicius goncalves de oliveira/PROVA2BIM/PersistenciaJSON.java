package com.seriestv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.*;

public class PersistenciaJSON {

    private static final String ARQUIVO = "seriestv_dados.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // salva o arquivo json
    public void salvar(Usuario usuario) throws Exception {
        String json = gson.toJson(usuario);
        Files.writeString(Path.of(ARQUIVO), json);
    }

//carrega o arquivo json
    public Usuario carregar() throws Exception {
        Path caminho = Path.of(ARQUIVO);
        if (!Files.exists(caminho)) {
            return null; // primeira vez usando o programa
        }
        String json = Files.readString(caminho);
        return gson.fromJson(json, Usuario.class);
    }
    public boolean existeDadosSalvos() {
        return Files.exists(Path.of(ARQUIVO));
    }
}
