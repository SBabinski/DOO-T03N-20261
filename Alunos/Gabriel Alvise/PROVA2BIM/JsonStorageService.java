import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonStorageService {
    private static final String CAMINHO_ARQUIVO = "dados_series.json";

    public boolean existeArquivoDados() {
        return new File(CAMINHO_ARQUIVO).exists();
    }

    public DadosAplicacao carregarDados() {
        File arquivo = new File(CAMINHO_ARQUIVO);

        // Serialização - Transforma objetos Java em JSON
        if (!arquivo.exists()) {
            DadosAplicacao dadosIniciais = DadosPreCarregados.criarDadosIniciais();
            salvarDados(dadosIniciais);
            return dadosIniciais;
        }

        try {
            String json = lerArquivo(arquivo);

            if (json.trim().isEmpty()) {
                return DadosPreCarregados.criarDadosIniciais();
            }

            return converterJsonParaDados(json);
        } catch (Exception erro) {
            System.out.println("Erro ao carregar dados: " + erro.getMessage());
            return DadosPreCarregados.criarDadosIniciais();
        }
    }

    public void salvarDados(DadosAplicacao dadosAplicacao) {
        if (dadosAplicacao == null) {
            return;
        }

        try {
            String json = converterDadosParaJson(dadosAplicacao);
            escreverArquivo(json);
        } catch (Exception erro) {
            System.out.println("Erro ao salvar dados: " + erro.getMessage());
        }
    }

    private String lerArquivo(File arquivo) throws IOException {
        StringBuilder conteudo = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
        }

        return conteudo.toString();
    }

    private void escreverArquivo(String json) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            writer.write(json);
        }
    }

    private String converterDadosParaJson(DadosAplicacao dadosAplicacao) {
        StringBuilder json = new StringBuilder();

        json.append("{\n");

        json.append("  \"usuario\": {\n");
        json.append("    \"nomeOuApelido\": \"")
                .append(escaparTexto(dadosAplicacao.getUsuario().getNomeOuApelido()))
                .append("\"\n");
        json.append("  },\n");

        json.append("  \"favoritos\": ");
        json.append(converterListaSeriesParaJson(dadosAplicacao.getFavoritos()));
        json.append(",\n");

        json.append("  \"seriesAssistidas\": ");
        json.append(converterListaSeriesParaJson(dadosAplicacao.getSeriesAssistidas()));
        json.append(",\n");

        json.append("  \"seriesDesejo\": ");
        json.append(converterListaSeriesParaJson(dadosAplicacao.getSeriesDesejo()));
        json.append("\n");

        json.append("}");

        return json.toString();
    }

    private String converterListaSeriesParaJson(List<Serie> series) {
        StringBuilder json = new StringBuilder();

        json.append("[\n");

        for (int i = 0; i < series.size(); i++) {
            Serie serie = series.get(i);

            json.append("    {\n");
            json.append("      \"id\": ").append(serie.getId()).append(",\n");
            json.append("      \"nome\": \"").append(escaparTexto(serie.getNome())).append("\",\n");
            json.append("      \"idioma\": \"").append(escaparTexto(serie.getIdioma())).append("\",\n");
            json.append("      \"generos\": ").append(converterGenerosParaJson(serie.getGeneros())).append(",\n");
            json.append("      \"notaGeral\": ").append(serie.getNotaGeral()).append(",\n");
            json.append("      \"estado\": \"").append(escaparTexto(serie.getEstado())).append("\",\n");
            json.append("      \"dataEstreia\": \"").append(escaparTexto(serie.getDataEstreia())).append("\",\n");
            json.append("      \"dataTermino\": \"").append(escaparTexto(serie.getDataTermino())).append("\",\n");
            json.append("      \"emissora\": \"").append(escaparTexto(serie.getEmissora())).append("\",\n");
            json.append("      \"imagemUrl\": \"").append(escaparTexto(serie.getImagemUrl())).append("\"\n");
            json.append("    }");

            if (i < series.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("  ]");

        return json.toString();
    }

    private String converterGenerosParaJson(List<String> generos) {
        StringBuilder json = new StringBuilder();

        json.append("[");

        for (int i = 0; i < generos.size(); i++) {
            json.append("\"").append(escaparTexto(generos.get(i))).append("\"");

            if (i < generos.size() - 1) {
                json.append(", ");
            }
        }

        json.append("]");

        return json.toString();
    }

    private DadosAplicacao converterJsonParaDados(String json) {
        Usuario usuario = new Usuario(extrairTexto(json, "nomeOuApelido"));

        List<Serie> favoritos = extrairListaSeries(json, "favoritos");
        List<Serie> seriesAssistidas = extrairListaSeries(json, "seriesAssistidas");
        List<Serie> seriesDesejo = extrairListaSeries(json, "seriesDesejo");

        return new DadosAplicacao(usuario, favoritos, seriesAssistidas, seriesDesejo);
    }

    private List<Serie> extrairListaSeries(String json, String nomeLista) {
        List<Serie> series = new ArrayList<>();

        String conteudoLista = extrairConteudoLista(json, nomeLista);

        if (conteudoLista.isEmpty()) {
            return series;
        }

        Pattern patternObjeto = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
        Matcher matcherObjeto = patternObjeto.matcher(conteudoLista);

        while (matcherObjeto.find()) {
            String objetoSerie = matcherObjeto.group(1);

            Serie serie = new Serie(
                    extrairInteiro(objetoSerie, "id"),
                    extrairTexto(objetoSerie, "nome"),
                    extrairTexto(objetoSerie, "idioma"),
                    extrairGeneros(objetoSerie),
                    extrairDouble(objetoSerie, "notaGeral"),
                    extrairTexto(objetoSerie, "estado"),
                    extrairTexto(objetoSerie, "dataEstreia"),
                    extrairTexto(objetoSerie, "dataTermino"),
                    extrairTexto(objetoSerie, "emissora"),
                    extrairTexto(objetoSerie, "imagemUrl")
            );

            series.add(serie);
        }

        return series;
    }

    private String extrairConteudoLista(String json, String nomeLista) {
        String chave = "\"" + nomeLista + "\"";
        int inicioChave = json.indexOf(chave);

        if (inicioChave == -1) {
            return "";
        }

        int inicioLista = json.indexOf("[", inicioChave);

        if (inicioLista == -1) {
            return "";
        }

        int contadorColchetes = 0;

        for (int i = inicioLista; i < json.length(); i++) {
            char caractere = json.charAt(i);

            if (caractere == '[') {
                contadorColchetes++;
            }

            if (caractere == ']') {
                contadorColchetes--;

                if (contadorColchetes == 0) {
                    return json.substring(inicioLista + 1, i);
                }
            }
        }

        return "";
    }

    private String extrairTexto(String json, String chave) {
        Pattern pattern = Pattern.compile("\"" + chave + "\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return desescaparTexto(matcher.group(1));
        }

        return "Não informado";
    }

    private int extrairInteiro(String json, String chave) {
        Pattern pattern = Pattern.compile("\"" + chave + "\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 0;
    }

    private double extrairDouble(String json, String chave) {
        Pattern pattern = Pattern.compile("\"" + chave + "\"\\s*:\\s*([0-9]+\\.?[0-9]*)");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }

        return 0;
    }

    private List<String> extrairGeneros(String json) {
        List<String> generos = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"generos\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);

        if (!matcher.find()) {
            return generos;
        }

        String conteudoGeneros = matcher.group(1);

        Pattern patternGenero = Pattern.compile("\"(.*?)\"");
        Matcher matcherGenero = patternGenero.matcher(conteudoGeneros);

        while (matcherGenero.find()) {
            generos.add(desescaparTexto(matcherGenero.group(1)));
        }

        return generos;
    }

    private String escaparTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }

    private String desescaparTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\n", "\n")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }
}
