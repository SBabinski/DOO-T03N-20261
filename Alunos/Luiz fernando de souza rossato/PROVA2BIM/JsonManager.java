package tv;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonManager {

    private static final String ARQUIVO =
            "usuario.json";

    public static void salvarUsuario(
            Usuario usuario) {

        try {

            ObjectMapper mapper =
                    new ObjectMapper();

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(
                            new File(ARQUIVO),
                            usuario);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static Usuario carregarUsuario() {

        try {

            File arquivo =
                    new File(ARQUIVO);

            if (!arquivo.exists()) {

                return new Usuario();

            }

            ObjectMapper mapper =
                    new ObjectMapper();

            return mapper.readValue(
                    arquivo,
                    Usuario.class);

        } catch (Exception e) {

            return new Usuario();

        }

    }

}