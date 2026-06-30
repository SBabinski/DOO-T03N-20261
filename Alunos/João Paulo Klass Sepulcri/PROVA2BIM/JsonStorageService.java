import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonStorageService {

    private static final String NOME_ARQUIVO = "dados_usuario.json";

    public void salvarUsuario(Usuario usuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            writer.write("{\n");
            writer.write("  \"nome\": \"" + tratarTexto(usuario.getNome()) + "\",\n");

            writer.write("  \"favoritos\": ");
            salvarLista(writer, usuario.getFavoritos());
            writer.write(",\n");

            writer.write("  \"assistidas\": ");
            salvarLista(writer, usuario.getAssistidas());
            writer.write(",\n");

            writer.write("  \"desejaAssistir\": ");
            salvarLista(writer, usuario.getDesejaAssistir());
            writer.write("\n");

            writer.write("}");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public Usuario carregarUsuario() {
        File arquivo = new File(NOME_ARQUIVO);

        if (!arquivo.exists()) {
            Usuario usuario = new Usuario("Usuário");

            // Dados pré-carregados para agilizar a utilização, como pedido no trabalho
            usuario.adicionarFavorito(new Serie("Breaking Bad", "English", criarGeneros("Drama", "Crime"), 9.2,
                    "Ended", "2008-01-20", "2013-09-29", "AMC"));

            usuario.adicionarAssistida(new Serie("Friends", "English", criarGeneros("Comedy", "Romance"), 8.5,
                    "Ended", "1994-09-22", "2004-05-06", "NBC"));

            usuario.adicionarDesejaAssistir(new Serie("The Last of Us", "English", criarGeneros("Drama", "Action", "Horror"), 8.7,
                    "Running", "2023-01-15", "Não informado", "HBO"));

            salvarUsuario(usuario);
            return usuario;
        }

        try {
            String json = lerArquivo();
            Usuario usuario = new Usuario();

            usuario.setNome(extrairValor(json, "nome"));
            usuario.setFavoritos(extrairLista(json, "favoritos"));
            usuario.setAssistidas(extrairLista(json, "assistidas"));
            usuario.setDesejaAssistir(extrairLista(json, "desejaAssistir"));

            return usuario;

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados. Criando novo usuário.");

            Usuario usuario = new Usuario("Usuário");
            salvarUsuario(usuario);
            return usuario;
        }
    }

    // Escreve uma lista de séries no arquivo JSON
    private void salvarLista(BufferedWriter writer, List<Serie> lista) throws IOException {
        writer.write("[\n");

        for (int i = 0; i < lista.size(); i++) {
            Serie serie = lista.get(i);

            writer.write("    {\n");
            writer.write("      \"nome\": \"" + tratarTexto(serie.getNome()) + "\",\n");
            writer.write("      \"idioma\": \"" + tratarTexto(serie.getIdioma()) + "\",\n");
            writer.write("      \"generos\": \"" + tratarTexto(serie.getGenerosComoTexto()) + "\",\n");
            writer.write("      \"nota\": " + serie.getNota() + ",\n");
            writer.write("      \"estado\": \"" + tratarTexto(serie.getEstado()) + "\",\n");
            writer.write("      \"dataEstreia\": \"" + tratarTexto(serie.getDataEstreia()) + "\",\n");
            writer.write("      \"dataTermino\": \"" + tratarTexto(serie.getDataTermino()) + "\",\n");
            writer.write("      \"emissora\": \"" + tratarTexto(serie.getEmissora()) + "\"\n");
            writer.write("    }");

            if (i < lista.size() - 1) {
                writer.write(",");
            }

            writer.write("\n");
        }

        writer.write("  ]");
    }

    // Lê todo o conteúdo do arquivo JSON
    private String lerArquivo() throws IOException {
        StringBuilder conteudo = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
        }

        return conteudo.toString();
    }

    // Extrai um valor simples do JSON pelo nome do campo
    private String extrairValor(String json, String campo) {
        String procura = "\"" + campo + "\": \"";
        int inicio = json.indexOf(procura);

        if (inicio == -1) {
            return "Não informado";
        }

        inicio += procura.length();
        int fim = json.indexOf("\"", inicio);

        if (fim == -1) {
            return "Não informado";
        }

        return json.substring(inicio, fim);
    }

    // Extrai uma lista de séries do JSON
    private List<Serie> extrairLista(String json, String nomeLista) {
        List<Serie> lista = new ArrayList<>();

        String procura = "\"" + nomeLista + "\": [";
        int inicio = json.indexOf(procura);

        if (inicio == -1) {
            return lista;
        }

        inicio += procura.length();
        int fim = json.indexOf("]", inicio);

        if (fim == -1) {
            return lista;
        }

        String conteudoLista = json.substring(inicio, fim);
        String[] objetos = conteudoLista.split("\\},");

        for (String objeto : objetos) {
            objeto = objeto.replace("{", "").replace("}", "").trim();

            if (!objeto.isEmpty()) {
                Serie serie = new Serie();

                serie.setNome(extrairValor(objeto, "nome"));
                serie.setIdioma(extrairValor(objeto, "idioma"));
                serie.setGeneros(criarGenerosAPartirDoTexto(extrairValor(objeto, "generos")));
                serie.setNota(extrairNota(objeto));
                serie.setEstado(extrairValor(objeto, "estado"));
                serie.setDataEstreia(extrairValor(objeto, "dataEstreia"));
                serie.setDataTermino(extrairValor(objeto, "dataTermino"));
                serie.setEmissora(extrairValor(objeto, "emissora"));

                lista.add(serie);
            }
        }

        return lista;
    }

    // Extrai a nota, diferente numero
    private double extrairNota(String objeto) {
        String procura = "\"nota\": ";
        int inicio = objeto.indexOf(procura);

        if (inicio == -1) {
            return 0.0;
        }

        inicio += procura.length();
        int fim = objeto.indexOf(",", inicio);

        if (fim == -1) {
            fim = objeto.length();
        }

        try {
            return Double.parseDouble(objeto.substring(inicio, fim).trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Cria uma lista de gêneros manualmente
    private List<String> criarGeneros(String... generos) {
        List<String> lista = new ArrayList<>();

        for (String genero : generos) {
            lista.add(genero);
        }

        return lista;
    }

    // Converte texto como "Drama, Crime" em lista
    private List<String> criarGenerosAPartirDoTexto(String texto) {
        List<String> lista = new ArrayList<>();

        if (texto == null || texto.equals("Não informado")) {
            return lista;
        }

        String[] partes = texto.split(",");

        for (String parte : partes) {
            lista.add(parte.trim());
        }

        return lista;
    }

    // Evita erro caso algum texto venha nulo
    private String tratarTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return "Não informado";
        }

        return texto.replace("\"", "'");
    }
}