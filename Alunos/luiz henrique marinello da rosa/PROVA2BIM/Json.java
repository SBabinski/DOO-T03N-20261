import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

// MEU leitor e escritor de JSON, feito na mao em java puro.
//   1) ler a resposta da API
//   2) salvar/carregar o dados.json
//
// O parse devolve tipos basicos do java:
//   objeto {} -> Map     array [] -> List     texto -> String
//   numero -> Double     true/false -> Boolean     null -> null

public class Json {

    //  PARTE 1 - LER (texto -> objetos java)

    private final String texto; // o json inteiro
    private int pos;            // onde estou lendo agora

    private Json(String texto) {
        this.texto = texto;
        this.pos = 0;
    }

    // por aqui que se comeca. pode lancar JsonException se o texto for invalido
    public static Object parse(String json) throws JsonException {
        if (json == null) {
            throw new JsonException("JSON nulo.");
        }
        Json leitor = new Json(json);
        leitor.pularEspacos();
        Object valor = leitor.lerValor();
        leitor.pularEspacos();
        // se sobrar coisa depois, eu ignoro (alguns servidores mandam lixo extra)
        if (leitor.pos < leitor.texto.length()) {
            // tolera sobra
        }
        return valor;
    }

    // olha o proximo caractere e decide o que ler
    private Object lerValor() throws JsonException {
        pularEspacos();
        if (pos >= texto.length()) {
            throw new JsonException("Fim inesperado do JSON.");
        }

        char c = texto.charAt(pos);

        switch (c) {
            case '{':
                return lerObjeto();
            case '[':
                return lerArray();
            case '"':
                return lerString();
            case 't':
            case 'f':
                return lerBooleano();
            case 'n':
                return lerNulo();
            default:
                // se nao for nenhum acima, e numero (comeca com - ou digito)
                return lerNumero();
        }
    }

    // le um objeto {} e devolve um Map
    private Map<String, Object> lerObjeto() throws JsonException {
        Map<String, Object> objeto = new LinkedHashMap<String, Object>();
        consumir('{');
        pularEspacos();

        // objeto vazio {}
        if (espiar() == '}') {
            consumir('}');
            return objeto;
        }

        while (true) {
            pularEspacos();
            String chave = lerString();
            pularEspacos();
            consumir(':');
            Object valor = lerValor();
            objeto.put(chave, valor);

            pularEspacos();
            char c = espiar();
            if (c == ',') {
                consumir(',');
            } else if (c == '}') {
                consumir('}');
                break;
            } else {
                throw new JsonException("Esperava ',' ou '}' na posicao " + pos);
            }
        }
        return objeto;
    }

    // le um array [] e devolve uma List
    private List<Object> lerArray() throws JsonException {
        List<Object> array = new ArrayList<Object>();
        consumir('[');
        pularEspacos();

        // array vazio []
        if (espiar() == ']') {
            consumir(']');
            return array;
        }

        while (true) {
            Object valor = lerValor();
            array.add(valor);

            pularEspacos();
            char c = espiar();
            if (c == ',') {
                consumir(',');
            } else if (c == ']') {
                consumir(']');
                break;
            } else {
                throw new JsonException("Esperava ',' ou ']' na posicao " + pos);
            }
        }
        return array;
    }

    // le um texto entre aspas, tratando os escapes 
    private String lerString() throws JsonException {
        pularEspacos();
        consumir('"');
        StringBuilder sb = new StringBuilder();

        while (pos < texto.length()) {
            char c = texto.charAt(pos);
            pos++;

            if (c == '"') {
                // acabou a string
                return sb.toString();
            }

            if (c == '\\') {
                // veio uma barra: o proximo char e especial
                if (pos >= texto.length()) {
                    throw new JsonException("Escape incompleto no fim do JSON.");
                }
                char escape = texto.charAt(pos);
                pos++;
                switch (escape) {
                    case '"':  sb.append('"');  break;
                    case '\\': sb.append('\\'); break;
                    case '/':  sb.append('/');  break;
                    case 'b':  sb.append('\b'); break;
                    case 'f':  sb.append('\f'); break;
                    case 'n':  sb.append('\n'); break;
                    case 'r':  sb.append('\r'); break;
                    case 't':  sb.append('\t'); break;
                    case 'u':
                        
                        if (pos + 4 > texto.length()) {
                            throw new JsonException("Escape unicode incompleto.");
                        }
                        String hex = texto.substring(pos, pos + 4);
                        pos += 4;
                        try {
                            int codigo = Integer.parseInt(hex, 16);
                            sb.append((char) codigo);
                        } catch (NumberFormatException e) {
                            throw new JsonException("Escape unicode invalido: \\u" + hex);
                        }
                        break;
                    default:
                        throw new JsonException("Escape desconhecido: \\" + escape);
                }
            } else {
                sb.append(c);
            }
        }
        throw new JsonException("String sem aspas de fechamento.");
    }

    // le um numero (devolvo sempre como Double pra simplificar)
    private Double lerNumero() throws JsonException {
        int inicio = pos;

        // vou andando enquanto for char de numero (sinal, digito, ponto, e/E)
        while (pos < texto.length()) {
            char c = texto.charAt(pos);
            if ((c >= '0' && c <= '9') || c == '-' || c == '+'
                    || c == '.' || c == 'e' || c == 'E') {
                pos++;
            } else {
                break;
            }
        }

        String numeroTexto = texto.substring(inicio, pos);
        if (numeroTexto.isEmpty()) {
            throw new JsonException("Numero invalido na posicao " + inicio);
        }
        try {
            return Double.parseDouble(numeroTexto);
        } catch (NumberFormatException e) {
            throw new JsonException("Numero invalido: " + numeroTexto);
        }
    }

    // le true ou false
    private Boolean lerBooleano() throws JsonException {
        if (texto.startsWith("true", pos)) {
            pos += 4;
            return Boolean.TRUE;
        }
        if (texto.startsWith("false", pos)) {
            pos += 5;
            return Boolean.FALSE;
        }
        throw new JsonException("Valor booleano invalido na posicao " + pos);
    }

    // le null
    private Object lerNulo() throws JsonException {
        if (texto.startsWith("null", pos)) {
            pos += 4;
            return null;
        }
        throw new JsonException("Valor 'null' invalido na posicao " + pos);
    }

    // ---- ajudantes da leitura ----

    // pula espacos, quebras de linha, tabs
    private void pularEspacos() {
        while (pos < texto.length()) {
            char c = texto.charAt(pos);
            if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
                pos++;
            } else {
                break;
            }
        }
    }

    // espia o proximo char sem andar
    private char espiar() throws JsonException {
        if (pos >= texto.length()) {
            throw new JsonException("Fim inesperado do JSON.");
        }
        return texto.charAt(pos);
    }

    // exige que o proximo char seja o esperado e anda
    private void consumir(char esperado) throws JsonException {
        if (pos >= texto.length() || texto.charAt(pos) != esperado) {
            throw new JsonException("Esperava '" + esperado + "' na posicao " + pos);
        }
        pos++;
    }

    // ============ PARTE 2 - ESCREVER (objetos java -> texto) ============

    // recebe Map/List/String/etc e devolve o texto JSON. uso pra salvar no disco
    public static String escrever(Object valor) {
        StringBuilder sb = new StringBuilder();
        escreverValor(valor, sb);
        return sb.toString();
    }

    // olha o tipo do objeto e escreve do jeito certo
    private static void escreverValor(Object valor, StringBuilder sb) {
        if (valor == null) {
            sb.append("null");
        } else if (valor instanceof String) {
            escreverString((String) valor, sb);
        } else if (valor instanceof Boolean) {
            sb.append(valor.toString());
        } else if (valor instanceof Number) {
            escreverNumero((Number) valor, sb);
        } else if (valor instanceof Map) {
            escreverObjeto((Map<?, ?>) valor, sb);
        } else if (valor instanceof List) {
            escreverArray((List<?>) valor, sb);
        } else {
            // qualquer outra coisa eu jogo como texto (seguranca)
            escreverString(valor.toString(), sb);
        }
    }

    // escreve um Map como objeto {chave:valor,...}
    private static void escreverObjeto(Map<?, ?> mapa, StringBuilder sb) {
        sb.append('{');
        boolean primeiro = true;
        for (Map.Entry<?, ?> entrada : mapa.entrySet()) {
            if (!primeiro) {
                sb.append(',');
            }
            primeiro = false;
            escreverString(String.valueOf(entrada.getKey()), sb);
            sb.append(':');
            escreverValor(entrada.getValue(), sb);
        }
        sb.append('}');
    }

    // escreve uma List como array [a,b,c]
    private static void escreverArray(List<?> lista, StringBuilder sb) {
        sb.append('[');
        for (int i = 0; i < lista.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            escreverValor(lista.get(i), sb);
        }
        sb.append(']');
    }

    // escreve numero. se for inteiro, tiro o ".0" pra ficar limpo
    private static void escreverNumero(Number numero, StringBuilder sb) {
        double d = numero.doubleValue();
        if (d == Math.floor(d) && !Double.isInfinite(d)) {
            sb.append(Long.toString((long) d));
        } else {
            sb.append(Double.toString(d));
        }
    }

    // escreve texto entre aspas, escapando o que precisa (\n, \", etc)
    private static void escreverString(String texto, StringBuilder sb) {
        sb.append('"');
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            switch (c) {
                case '"':  sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b");  break;
                case '\f': sb.append("\\f");  break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) {
                        // char de controle 
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
    }
}
