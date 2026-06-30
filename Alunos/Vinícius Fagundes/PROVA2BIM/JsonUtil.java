
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Leitor e escritor JSON pequeno, suficiente para a API e para o arquivo local. */
public final class JsonUtil {
    private JsonUtil() {
    }

    public static Object parse(String json) {
        if (json == null) {
            throw new IllegalArgumentException("Conteúdo JSON ausente.");
        }
        Parser parser = new Parser(json);
        Object valor = parser.lerValor();
        parser.ignorarEspacos();
        if (!parser.terminou()) {
            throw parser.erro("Há conteúdo inesperado após o JSON");
        }
        return valor;
    }

    public static String stringify(Object valor) {
        StringBuilder saida = new StringBuilder();
        escrever(valor, saida, 0);
        saida.append(System.lineSeparator());
        return saida.toString();
    }

    private static void escrever(Object valor, StringBuilder saida, int nivel) {
        if (valor == null) {
            saida.append("null");
        } else if (valor instanceof String) {
            escreverString((String) valor, saida);
        } else if (valor instanceof Number || valor instanceof Boolean) {
            saida.append(valor);
        } else if (valor instanceof Map<?, ?>) {
            escreverMapa((Map<?, ?>) valor, saida, nivel);
        } else if (valor instanceof Iterable<?>) {
            escreverLista((Iterable<?>) valor, saida, nivel);
        } else {
            throw new IllegalArgumentException("Tipo não suportado no JSON: " + valor.getClass().getName());
        }
    }

    private static void escreverMapa(Map<?, ?> mapa, StringBuilder saida, int nivel) {
        saida.append('{');
        if (!mapa.isEmpty()) {
            saida.append(System.lineSeparator());
            int indice = 0;
            for (Map.Entry<?, ?> item : mapa.entrySet()) {
                indentar(saida, nivel + 1);
                escreverString(String.valueOf(item.getKey()), saida);
                saida.append(": ");
                escrever(item.getValue(), saida, nivel + 1);
                if (++indice < mapa.size()) {
                    saida.append(',');
                }
                saida.append(System.lineSeparator());
            }
            indentar(saida, nivel);
        }
        saida.append('}');
    }

    private static void escreverLista(Iterable<?> lista, StringBuilder saida, int nivel) {
        List<Object> itens = new ArrayList<>();
        lista.forEach(itens::add);
        saida.append('[');
        if (!itens.isEmpty()) {
            saida.append(System.lineSeparator());
            for (int i = 0; i < itens.size(); i++) {
                indentar(saida, nivel + 1);
                escrever(itens.get(i), saida, nivel + 1);
                if (i + 1 < itens.size()) {
                    saida.append(',');
                }
                saida.append(System.lineSeparator());
            }
            indentar(saida, nivel);
        }
        saida.append(']');
    }

    private static void escreverString(String texto, StringBuilder saida) {
        saida.append('"');
        for (int i = 0; i < texto.length(); i++) {
            char caractere = texto.charAt(i);
            switch (caractere) {
                case '"':
                    saida.append("\\\"");
                    break;
                case '\\':
                    saida.append("\\\\");
                    break;
                case '\b':
                    saida.append("\\b");
                    break;
                case '\f':
                    saida.append("\\f");
                    break;
                case '\n':
                    saida.append("\\n");
                    break;
                case '\r':
                    saida.append("\\r");
                    break;
                case '\t':
                    saida.append("\\t");
                    break;
                default:
                    if (caractere < 0x20) {
                        saida.append(String.format("\\u%04x", (int) caractere));
                    } else {
                        saida.append(caractere);
                    }
                    break;
            }
        }
        saida.append('"');
    }

    private static void indentar(StringBuilder saida, int nivel) {
        saida.append("  ".repeat(Math.max(0, nivel)));
    }

    private static final class Parser {
        private final String json;
        private int posicao;

        private Parser(String json) {
            this.json = json.startsWith("\uFEFF") ? json.substring(1) : json;
        }

        private Object lerValor() {
            ignorarEspacos();
            if (terminou()) {
                throw erro("Era esperado um valor");
            }
            switch (json.charAt(posicao)) {
                case '{': return lerObjeto();
                case '[': return lerArray();
                case '"': return lerString();
                case 't': return lerLiteral("true", Boolean.TRUE);
                case 'f': return lerLiteral("false", Boolean.FALSE);
                case 'n': return lerLiteral("null", null);
                default: return lerNumero();
            }
        }

        private Map<String, Object> lerObjeto() {
            consumir('{');
            Map<String, Object> objeto = new LinkedHashMap<>();
            ignorarEspacos();
            if (tentarConsumir('}')) {
                return objeto;
            }
            while (true) {
                ignorarEspacos();
                if (terminou() || json.charAt(posicao) != '"') {
                    throw erro("Era esperada uma chave entre aspas");
                }
                String chave = lerString();
                ignorarEspacos();
                consumir(':');
                objeto.put(chave, lerValor());
                ignorarEspacos();
                if (tentarConsumir('}')) {
                    return objeto;
                }
                consumir(',');
            }
        }

        private List<Object> lerArray() {
            consumir('[');
            List<Object> array = new ArrayList<>();
            ignorarEspacos();
            if (tentarConsumir(']')) {
                return array;
            }
            while (true) {
                array.add(lerValor());
                ignorarEspacos();
                if (tentarConsumir(']')) {
                    return array;
                }
                consumir(',');
            }
        }

        private String lerString() {
            consumir('"');
            StringBuilder texto = new StringBuilder();
            while (!terminou()) {
                char caractere = json.charAt(posicao++);
                if (caractere == '"') {
                    return texto.toString();
                }
                if (caractere != '\\') {
                    texto.append(caractere);
                    continue;
                }
                if (terminou()) {
                    throw erro("Sequência de escape incompleta");
                }
                char escape = json.charAt(posicao++);
                switch (escape) {
                    case '"':
                    case '\\':
                    case '/':
                        texto.append(escape);
                        break;
                    case 'b':
                        texto.append('\b');
                        break;
                    case 'f':
                        texto.append('\f');
                        break;
                    case 'n':
                        texto.append('\n');
                        break;
                    case 'r':
                        texto.append('\r');
                        break;
                    case 't':
                        texto.append('\t');
                        break;
                    case 'u':
                        texto.append(lerUnicode());
                        break;
                    default:
                        throw erro("Escape inválido: \\" + escape);
                }
            }
            throw erro("String não terminada");
        }

        private char lerUnicode() {
            if (posicao + 4 > json.length()) {
                throw erro("Escape Unicode incompleto");
            }
            String hexadecimal = json.substring(posicao, posicao + 4);
            posicao += 4;
            try {
                return (char) Integer.parseInt(hexadecimal, 16);
            } catch (NumberFormatException erro) {
                throw erro("Escape Unicode inválido");
            }
        }

        private Object lerNumero() {
            int inicio = posicao;
            if (json.charAt(posicao) == '-') {
                posicao++;
            }
            lerDigitos();
            boolean decimal = false;
            if (!terminou() && json.charAt(posicao) == '.') {
                decimal = true;
                posicao++;
                lerDigitos();
            }
            if (!terminou() && (json.charAt(posicao) == 'e' || json.charAt(posicao) == 'E')) {
                decimal = true;
                posicao++;
                if (!terminou() && (json.charAt(posicao) == '+' || json.charAt(posicao) == '-')) {
                    posicao++;
                }
                lerDigitos();
            }
            String numero = json.substring(inicio, posicao);
            try {
                if (decimal) {
                    return Double.valueOf(numero);
                }
                return Long.valueOf(numero);
            } catch (NumberFormatException erro) {
                throw erro("Número inválido");
            }
        }

        private void lerDigitos() {
            int inicio = posicao;
            while (!terminou() && Character.isDigit(json.charAt(posicao))) {
                posicao++;
            }
            if (inicio == posicao) {
                throw erro("Era esperado um dígito");
            }
        }

        private Object lerLiteral(String literal, Object valor) {
            if (!json.startsWith(literal, posicao)) {
                throw erro("Literal inválido");
            }
            posicao += literal.length();
            return valor;
        }

        private void consumir(char esperado) {
            ignorarEspacos();
            if (terminou() || json.charAt(posicao) != esperado) {
                throw erro("Era esperado '" + esperado + "'");
            }
            posicao++;
        }

        private boolean tentarConsumir(char caractere) {
            ignorarEspacos();
            if (!terminou() && json.charAt(posicao) == caractere) {
                posicao++;
                return true;
            }
            return false;
        }

        private void ignorarEspacos() {
            while (!terminou() && Character.isWhitespace(json.charAt(posicao))) {
                posicao++;
            }
        }

        private boolean terminou() {
            return posicao >= json.length();
        }

        private IllegalArgumentException erro(String mensagem) {
            return new IllegalArgumentException(mensagem + " na posição " + posicao + ".");
        }
    }
}
