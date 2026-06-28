package app.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.model.Usuario;

public class JsonService {

    private static final String ARQUIVO = "usuario.json";

    private Gson gson;

    public JsonService() {

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

    }

    public void salvar(Usuario usuario) {

        try (FileWriter writer = new FileWriter(ARQUIVO)) {

            gson.toJson(usuario, writer);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public Usuario carregar() {

        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {

            return null;

        }

        try (FileReader reader = new FileReader(arquivo)) {

            return gson.fromJson(reader, Usuario.class);

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }

    }

}