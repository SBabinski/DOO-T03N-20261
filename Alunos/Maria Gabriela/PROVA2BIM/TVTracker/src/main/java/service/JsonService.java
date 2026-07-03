package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Usuario;
import java.io.*;

public class JsonService {
private final String arquivo="dados.json";

public void salvar(Usuario usuario)
        throws Exception{

    Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    FileWriter writer =
            new FileWriter(arquivo);

    gson.toJson(usuario,writer);

    writer.close();
}

public Usuario carregar()
        throws Exception{

    File file =
            new File(arquivo);

    if(!file.exists())
        return null;

    Gson gson=new Gson();

    FileReader reader =
            new FileReader(file);

    Usuario usuario =
            gson.fromJson(reader,Usuario.class);

    reader.close();

    return usuario;
}
}