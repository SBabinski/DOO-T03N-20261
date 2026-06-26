import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser e serializador de JSON minimalista, escrito a mao para nao depender
 * de bibliotecas externas (o projeto deve conter apenas arquivos .java + .env).
 *
 * Representacao dos valores:
 *   objeto  -> Map<String, Object> (LinkedHashMap, mantem a ordem)
 *   array   -> List<Object>
 *   string  -> String
 *   numero  -> Double
 *   boolean -> Boolean
 *   null    -> null
 */
public final class Json {

    private final String src;
    private int pos;

    private Json(String src) {
        this.src = src;
    }

    /** Interpreta um texto JSON e devolve a estrutura correspondente. */
    public static Object parse(String text) throws JsonException {
        if (text == null) throw new JsonException("Texto JSON nulo.");
        Json p = new Json(text);
        p.skipWhitespace();
        Object value = p.readValue();
        p.skipWhitespace();
        if (p.pos < p.src.length()) {
            throw new JsonException("Conteudo extra apos o JSON na posicao " + p.pos + ".");
        }
        return value;
    }

    // ---- Leitura -------------------------------------------------------

    private Object readValue() throws JsonException {
        skipWhitespace();
        if (pos >= src.length()) throw new JsonException("Fim inesperado do JSON.");
        char c = src.charAt(pos);
        switch (c) {
            case '{': return readObject();
            case '[': return readArray();
            case '"': return readString();
            case 't': case 'f': return readBoolean();
            case 'n': return readNull();
            default:
                if (c == '-' || (c >= '0' && c <= '9')) return readNumber();
                throw new JsonException("Caractere inesperado '" + c + "' na posicao " + pos + ".");
        }
    }

    private Map<String, Object> readObject() throws JsonException {
        Map<String, Object> map = new LinkedHashMap<>();
        expect('{');
        skipWhitespace();
        if (peek() == '}') { pos++; return map; }
        while (true) {
            skipWhitespace();
            String key = readString();
            skipWhitespace();
            expect(':');
            Object value = readValue();
            map.put(key, value);
            skipWhitespace();
            char c = next();
            if (c == '}') break;
            if (c != ',') throw new JsonException("Esperado ',' ou '}' na posicao " + (pos - 1) + ".");
        }
        return map;
    }

    private List<Object> readArray() throws JsonException {
        List<Object> list = new ArrayList<>();
        expect('[');
        skipWhitespace();
        if (peek() == ']') { pos++; return list; }
        while (true) {
            Object value = readValue();
            list.add(value);
            skipWhitespace();
            char c = next();
            if (c == ']') break;
            if (c != ',') throw new JsonException("Esperado ',' ou ']' na posicao " + (pos - 1) + ".");
        }
        return list;
    }

    private String readString() throws JsonException {
        expect('"');
        StringBuilder sb = new StringBuilder();
        while (pos < src.length()) {
            char c = src.charAt(pos++);
            if (c == '"') return sb.toString();
            if (c == '\\') {
                if (pos >= src.length()) break;
                char esc = src.charAt(pos++);
                switch (esc) {
                    case '"':  sb.append('"');  break;
                    case '\\': sb.append('\\'); break;
                    case '/':  sb.append('/');  break;
                    case 'b':  sb.append('\b'); break;
                    case 'f':  sb.append('\f'); break;
                    case 'n':  sb.append('\n'); break;
                    case 'r':  sb.append('\r'); break;
                    case 't':  sb.append('\t'); break;
                    case 'u':
                        if (pos + 4 > src.length()) throw new JsonException("Sequencia unicode invalida.");
                        String hex = src.substring(pos, pos + 4);
                        pos += 4;
                        try { sb.append((char) Integer.parseInt(hex, 16)); }
                        catch (NumberFormatException e) { throw new JsonException("Unicode invalido: " + hex); }
                        break;
                    default: throw new JsonException("Escape invalido '\\" + esc + "'.");
                }
            } else {
                sb.append(c);
            }
        }
        throw new JsonException("String JSON nao terminada.");
    }

    private Double readNumber() throws JsonException {
        int start = pos;
        if (peek() == '-') pos++;
        while (pos < src.length()) {
            char c = src.charAt(pos);
            if ((c >= '0' && c <= '9') || c == '.' || c == 'e' || c == 'E' || c == '+' || c == '-') pos++;
            else break;
        }
        try {
            return Double.parseDouble(src.substring(start, pos));
        } catch (NumberFormatException e) {
            throw new JsonException("Numero invalido na posicao " + start + ".");
        }
    }

    private Boolean readBoolean() throws JsonException {
        if (src.startsWith("true", pos))  { pos += 4; return Boolean.TRUE; }
        if (src.startsWith("false", pos)) { pos += 5; return Boolean.FALSE; }
        throw new JsonException("Booleano invalido na posicao " + pos + ".");
    }

    private Object readNull() throws JsonException {
        if (src.startsWith("null", pos)) { pos += 4; return null; }
        throw new JsonException("Valor invalido na posicao " + pos + ".");
    }

    // ---- Utilitarios de leitura ---------------------------------------

    private void skipWhitespace() {
        while (pos < src.length()) {
            char c = src.charAt(pos);
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') pos++;
            else break;
        }
    }

    private char peek() {
        return pos < src.length() ? src.charAt(pos) : '\0';
    }

    private char next() throws JsonException {
        if (pos >= src.length()) throw new JsonException("Fim inesperado do JSON.");
        return src.charAt(pos++);
    }

    private void expect(char c) throws JsonException {
        if (pos >= src.length() || src.charAt(pos) != c) {
            throw new JsonException("Esperado '" + c + "' na posicao " + pos + ".");
        }
        pos++;
    }

    // ---- Serializacao --------------------------------------------------

    /** Converte uma estrutura (Map/List/String/Number/Boolean/null) em texto JSON identado. */
    public static String stringify(Object value) {
        StringBuilder sb = new StringBuilder();
        write(value, sb, 0);
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private static void write(Object value, StringBuilder sb, int indent) {
        if (value == null) {
            sb.append("null");
        } else if (value instanceof String) {
            writeString((String) value, sb);
        } else if (value instanceof Boolean) {
            sb.append(value.toString());
        } else if (value instanceof Number) {
            sb.append(formatNumber((Number) value));
        } else if (value instanceof Map) {
            writeObject((Map<String, Object>) value, sb, indent);
        } else if (value instanceof List) {
            writeArray((List<Object>) value, sb, indent);
        } else {
            writeString(value.toString(), sb);
        }
    }

    private static void writeObject(Map<String, Object> map, StringBuilder sb, int indent) {
        if (map.isEmpty()) { sb.append("{}"); return; }
        sb.append("{\n");
        int i = 0;
        for (Map.Entry<String, Object> e : map.entrySet()) {
            indent(sb, indent + 1);
            writeString(e.getKey(), sb);
            sb.append(": ");
            write(e.getValue(), sb, indent + 1);
            if (++i < map.size()) sb.append(',');
            sb.append('\n');
        }
        indent(sb, indent);
        sb.append('}');
    }

    private static void writeArray(List<Object> list, StringBuilder sb, int indent) {
        if (list.isEmpty()) { sb.append("[]"); return; }
        sb.append("[\n");
        for (int i = 0; i < list.size(); i++) {
            indent(sb, indent + 1);
            write(list.get(i), sb, indent + 1);
            if (i + 1 < list.size()) sb.append(',');
            sb.append('\n');
        }
        indent(sb, indent);
        sb.append(']');
    }

    private static void writeString(String s, StringBuilder sb) {
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"':  sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b");  break;
                case '\f': sb.append("\\f");  break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        sb.append('"');
    }

    private static String formatNumber(Number n) {
        double d = n.doubleValue();
        if (d == Math.floor(d) && !Double.isInfinite(d) && Math.abs(d) < 1e15) {
            return Long.toString((long) d);
        }
        return Double.toString(d);
    }

    private static void indent(StringBuilder sb, int level) {
        for (int i = 0; i < level; i++) sb.append("  ");
    }
}
