package persistence;

import model.Serie;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Serializa e desserializa objetos {@link Usuario} para/de JSON puro.
 * Não utiliza nenhuma biblioteca externa — toda a lógica é manual,
 * seguindo o mesmo padrão do parser existente em ClienteTVMaze.
 *
 * Formato do arquivo gerado:
 * <pre>
 * {
 *   "nomeOuApelido": "Bea",
 *   "favoritos": [ { ...campos da serie... }, ... ],
 *   "jaAssistidas": [ ... ],
 *   "desejaAssistir": [ ... ]
 * }
 * </pre>
 */
public class SerializadorJson {

    // -------------------------------------------------------------------------
    // Serialização (objeto → texto JSON)
    // -------------------------------------------------------------------------

    /**
     * Converte um {@link Usuario} inteiro para uma string JSON formatada.
     *
     * @param usuario objeto a serializar
     * @return string JSON com indentação de 2 espaços
     */
    public String serializar(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"nomeOuApelido\": ").append(escaparString(usuario.obterNomeOuApelido())).append(",\n");
        sb.append("  \"favoritos\": ").append(serializarLista(usuario.obterFavoritos(), 2)).append(",\n");
        sb.append("  \"jaAssistidas\": ").append(serializarLista(usuario.obterJaAssistidas(), 2)).append(",\n");
        sb.append("  \"desejaAssistir\": ").append(serializarLista(usuario.obterDesejaAssistir(), 2)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Serializa uma lista de séries como array JSON.
     *
     * @param series lista de séries
     * @param recuo  quantidade de espaços de recuo base
     * @return string JSON do array
     */
    private String serializarLista(List<Serie> series, int recuo) {
        if (series.isEmpty()) return "[]";
        String pad = " ".repeat(recuo);
        String pad2 = " ".repeat(recuo + 2);
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < series.size(); i++) {
            sb.append(serializarSerie(series.get(i), recuo + 2));
            if (i < series.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append(pad).append("]");
        return sb.toString();
    }

    /**
     * Serializa um único objeto {@link Serie} como objeto JSON.
     *
     * @param serie  série a serializar
     * @param recuo  quantidade de espaços de recuo
     * @return string JSON do objeto
     */
    private String serializarSerie(Serie serie, int recuo) {
        String pad = " ".repeat(recuo);
        StringBuilder sb = new StringBuilder(pad + "{\n");
        sb.append(campo(pad, "id", String.valueOf(serie.obterIdSerie()), false)).append(",\n");
        sb.append(campo(pad, "nome", escaparString(serie.obterNome()), true)).append(",\n");
        sb.append(campo(pad, "idioma", escaparString(nullParaVazio(serie.obterIdioma())), true)).append(",\n");
        sb.append(campo(pad, "generos", escaparString(nullParaVazio(serie.obterGeneros())), true)).append(",\n");
        sb.append(campo(pad, "nota", String.valueOf(serie.obterNota()), false)).append(",\n");
        sb.append(campo(pad, "estado", escaparString(nullParaVazio(serie.obterEstado())), true)).append(",\n");
        sb.append(campo(pad, "dataEstreia", escaparString(nullParaVazio(serie.obterDataEstreia())), true)).append(",\n");
        sb.append(campo(pad, "dataTermino", escaparString(nullParaVazio(serie.obterDataTermino())), true)).append(",\n");
        sb.append(campo(pad, "emissora", escaparString(nullParaVazio(serie.obterEmissora())), true)).append(",\n");
        sb.append(campo(pad, "resumo", escaparString(nullParaVazio(serie.obterResumo())), true)).append(",\n");
        sb.append(campo(pad, "urlImagem", escaparString(nullParaVazio(serie.obterUrlImagem())), true)).append("\n");
        sb.append(pad).append("}");
        return sb.toString();
    }

    /**
     * Formata uma linha de campo JSON: {@code "chave": valor}.
     */
    private String campo(String pad, String chave, String valor, boolean jaEhString) {
        // jaEhString=true significa que valor já chegou entre aspas (escaparString)
        return pad + "  \"" + chave + "\": " + valor;
    }

    /**
     * Envolve o texto em aspas duplas e escapa caracteres especiais.
     */
    private String escaparString(String texto) {
        if (texto == null) return "\"\"";
        return "\"" + texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                + "\"";
    }

    private String nullParaVazio(String texto) {
        return texto == null ? "" : texto;
    }

    // -------------------------------------------------------------------------
    // Desserialização (texto JSON → objeto)
    // -------------------------------------------------------------------------

    /**
     * Converte uma string JSON para um objeto {@link Usuario}.
     *
     * @param json string JSON do arquivo
     * @return usuário reconstruído
     * @throws IllegalArgumentException se o JSON estiver malformado
     */
    public Usuario desserializar(String json) {
        json = json.trim();
        if (json.isEmpty() || !json.startsWith("{")) {
            throw new IllegalArgumentException("JSON inválido: não começa com '{'");
        }

        String nomeOuApelido = lerString(json, "nomeOuApelido");
        if (nomeOuApelido == null || nomeOuApelido.isEmpty()) {
            nomeOuApelido = "Usuário";
        }

        Usuario usuario = new Usuario(nomeOuApelido);
        usuario.obterFavoritos().addAll(lerListaSeries(json, "favoritos"));
        usuario.obterJaAssistidas().addAll(lerListaSeries(json, "jaAssistidas"));
        usuario.obterDesejaAssistir().addAll(lerListaSeries(json, "desejaAssistir"));
        return usuario;
    }

    /**
     * Extrai o valor de uma chave de string do JSON raiz.
     */
    private String lerString(String json, String chave) {
        int pos = json.indexOf("\"" + chave + "\"");
        if (pos == -1) return "";
        int doisPontos = json.indexOf(":", pos);
        if (doisPontos == -1) return "";
        int inicioValor = json.indexOf("\"", doisPontos + 1);
        if (inicioValor == -1) return "";
        return extrairStringJson(json, inicioValor);
    }

    /**
     * Extrai e parseia a lista de séries de uma chave de array no JSON.
     */
    private List<Serie> lerListaSeries(String json, String chave) {
        List<Serie> lista = new ArrayList<>();
        int posChave = json.indexOf("\"" + chave + "\"");
        if (posChave == -1) return lista;

        int inicioArray = json.indexOf("[", posChave);
        if (inicioArray == -1) return lista;

        int fimArray = encontrarFimArray(json, inicioArray);
        if (fimArray == -1) return lista;

        String conteudoArray = json.substring(inicioArray + 1, fimArray);

        // Itera pelos objetos {} dentro do array
        int pos = 0;
        while (pos < conteudoArray.length()) {
            int inicioObj = conteudoArray.indexOf("{", pos);
            if (inicioObj == -1) break;
            int fimObj = encontrarFimObjeto(conteudoArray, inicioObj);
            if (fimObj == -1) break;
            String objJson = conteudoArray.substring(inicioObj, fimObj + 1);
            Serie serie = parsearSerie(objJson);
            if (serie != null) lista.add(serie);
            pos = fimObj + 1;
        }
        return lista;
    }

    /**
     * Parseia um objeto JSON de série para um {@link Serie}.
     */
    private Serie parsearSerie(String json) {
        try {
            int id = lerInteiro(json, "id");
            String nome = lerStringCampo(json, "nome");
            String idioma = lerStringCampo(json, "idioma");
            String generos = lerStringCampo(json, "generos");
            double nota = lerDouble(json, "nota");
            String estado = lerStringCampo(json, "estado");
            String dataEstreia = lerStringCampo(json, "dataEstreia");
            String dataTermino = lerStringCampo(json, "dataTermino");
            String emissora = lerStringCampo(json, "emissora");
            String resumo = lerStringCampo(json, "resumo");
            String urlImagem = lerStringCampo(json, "urlImagem");
            if (nome == null || nome.isEmpty()) return null;
            return new Serie(id, nome, idioma, generos, nota, estado,
                    dataEstreia, dataTermino, emissora, resumo, urlImagem);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Lê um campo de string dentro de um objeto JSON.
     */
    private String lerStringCampo(String json, String chave) {
        int pos = json.indexOf("\"" + chave + "\"");
        if (pos == -1) return "";
        int doisPontos = json.indexOf(":", pos + chave.length() + 2);
        if (doisPontos == -1) return "";
        // Avança sobre espaços
        int i = doisPontos + 1;
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) i++;
        if (i >= json.length()) return "";
        if (json.charAt(i) != '"') return ""; // valor não é string
        return extrairStringJson(json, i);
    }

    /**
     * Lê um campo inteiro dentro de um objeto JSON.
     */
    private int lerInteiro(String json, String chave) {
        int pos = json.indexOf("\"" + chave + "\"");
        if (pos == -1) return 0;
        int doisPontos = json.indexOf(":", pos + chave.length() + 2);
        if (doisPontos == -1) return 0;
        int i = doisPontos + 1;
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) i++;
        int fim = i;
        while (fim < json.length() && (Character.isDigit(json.charAt(fim)) || json.charAt(fim) == '-')) fim++;
        try { return Integer.parseInt(json.substring(i, fim)); } catch (Exception e) { return 0; }
    }

    /**
     * Lê um campo double dentro de um objeto JSON.
     */
    private double lerDouble(String json, String chave) {
        int pos = json.indexOf("\"" + chave + "\"");
        if (pos == -1) return 0.0;
        int doisPontos = json.indexOf(":", pos + chave.length() + 2);
        if (doisPontos == -1) return 0.0;
        int i = doisPontos + 1;
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) i++;
        int fim = i;
        while (fim < json.length() && (Character.isDigit(json.charAt(fim))
                || json.charAt(fim) == '.' || json.charAt(fim) == '-')) fim++;
        try { return Double.parseDouble(json.substring(i, fim)); } catch (Exception e) { return 0.0; }
    }

    /**
     * Extrai o conteúdo de uma string JSON iniciando na posição da aspas abertura.
     * Trata sequências de escape (\", \\, \n, \r, \t, \.uXXXX).
     */
    private String extrairStringJson(String json, int posAspasAbertura) {
        StringBuilder resultado = new StringBuilder();
        int i = posAspasAbertura + 1; // pula a aspas de abertura
        while (i < json.length()) {
            char c = json.charAt(i);
            if (c == '"') break; // aspas de fechamento
            if (c == '\\' && i + 1 < json.length()) {
                char prox = json.charAt(i + 1);
                switch (prox) {
                    case '"':  resultado.append('"');  i += 2; continue;
                    case '\\': resultado.append('\\'); i += 2; continue;
                    case 'n':  resultado.append('\n'); i += 2; continue;
                    case 'r':  resultado.append('\r'); i += 2; continue;
                    case 't':  resultado.append('\t'); i += 2; continue;
                    case 'u':
                        if (i + 5 < json.length()) {
                            try {
                                int cp = Integer.parseInt(json.substring(i + 2, i + 6), 16);
                                resultado.append((char) cp);
                                i += 6;
                                continue;
                            } catch (NumberFormatException ignored) {}
                        }
                        break;
                    default: break;
                }
            }
            resultado.append(c);
            i++;
        }
        return resultado.toString();
    }

    /**
     * Encontra o índice de fechamento '}' correspondente ao '{' em {@code inicio}.
     */
    private int encontrarFimObjeto(String json, int inicio) {
        int profundidade = 0;
        boolean emString = false;
        for (int i = inicio; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) emString = !emString;
            if (!emString) {
                if (c == '{') profundidade++;
                else if (c == '}') { profundidade--; if (profundidade == 0) return i; }
            }
        }
        return -1;
    }

    /**
     * Encontra o índice de fechamento ']' correspondente ao '[' em {@code inicio}.
     */
    private int encontrarFimArray(String json, int inicio) {
        int profundidade = 0;
        boolean emString = false;
        for (int i = inicio; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) emString = !emString;
            if (!emString) {
                if (c == '[') profundidade++;
                else if (c == ']') { profundidade--; if (profundidade == 0) return i; }
            }
        }
        return -1;
    }
}
