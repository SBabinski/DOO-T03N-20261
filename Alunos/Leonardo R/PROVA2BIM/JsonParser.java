import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Parser JSON simples criado para este trabalho.
// Ele transforma texto JSON em objetos Java (Map, List, String, Number, Boolean e null)
// e tambem faz o contrario: transforma objetos Java em texto JSON.

public class JsonParser {
    // Texto JSON que esta sendo lido.
    private final String text;

    // Posicao atual da leitura dentro do texto.
    private int index;

    private JsonParser(String text) {
        this.text = text == null ? "" : text;
        this.index = 0;
    }

    public static Object parse(String json) {
        // Metodo publico usado para ler um JSON.
        // Ele cria um parser e comeca lendo o primeiro valor.
        JsonParser parser = new JsonParser(json);
        Object value = parser.readValue();
        parser.skipWhitespace();

        // Depois de ler o valor principal, nao deve sobrar texto util.
        if (!parser.isAtEnd()) {
            throw new IllegalArgumentException("JSON possui conteudo extra na posicao " + parser.index);
        }
        return value;
    }

    public static String stringify(Object value) {
        // Metodo publico usado para transformar objetos Java em texto JSON.
        StringBuilder builder = new StringBuilder();
        writeValue(builder, value);
        return builder.toString();
    }

    private Object readValue() {
    
        skipWhitespace();
        if (isAtEnd()) {
            throw new IllegalArgumentException("JSON vazio ou incompleto");
        }

        char current = peek();
        
        if (current == '{') {
            return readObject();
        }
        if (current == '[') {
            return readArray();
        }
        if (current == '"') {
            return readString();
        }
        if (current == 't') {
            readLiteral("true");
            return Boolean.TRUE;
        }
        if (current == 'f') {
            readLiteral("false");
            return Boolean.FALSE;
        }
        if (current == 'n') {
            readLiteral("null");
            return null;
        }
        if (current == '-' || Character.isDigit(current)) {
            return readNumber();
        }

        throw new IllegalArgumentException("Valor JSON invalido na posicao " + index);
    }

    private Map<String, Object> readObject() {
        // Le objetos JSON, que usam chaves: { "nome": "valor" }.
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        expect('{');
        skipWhitespace();

        // Objeto vazio: {}.
        if (tryRead('}')) {
            return map;
        }

        while (true) {
            // Cada item do objeto tem chave, dois pontos e valor.
            skipWhitespace();
            String key = readString();
            skipWhitespace();
            expect(':');
            Object value = readValue();
            map.put(key, value);
            skipWhitespace();

            if (tryRead('}')) {
                break;
            }
            expect(',');
        }

        return map;
    }

    private List<Object> readArray() {
        // Le arrays JSON, que usam colchetes: [1, 2, 3].
        List<Object> list = new ArrayList<Object>();
        expect('[');
        skipWhitespace();

        // Array vazio: [].
        if (tryRead(']')) {
            return list;
        }

        while (true) {
            // Cada valor lido e adicionado na lista.
            list.add(readValue());
            skipWhitespace();

            if (tryRead(']')) {
                break;
            }
            expect(',');
        }

        return list;
    }

    private String readString() {
        // Le textos JSON que sempre ficam entre aspas.
        expect('"');
        StringBuilder builder = new StringBuilder();

        while (!isAtEnd()) {
            char current = next();
            if (current == '"') {
                return builder.toString();
            }
            if (current == '\\') {
                // Trata caracteres especiais como \n, \t e \".
                builder.append(readEscapedCharacter());
            } else {
                builder.append(current);
            }
        }

        throw new IllegalArgumentException("Texto JSON nao finalizado");
    }

    private char readEscapedCharacter() {
        // Depois de uma barra invertida, o JSON permite alguns escapes especiais.
        if (isAtEnd()) {
            throw new IllegalArgumentException("Escape JSON incompleto");
        }

        char escaped = next();
        if (escaped == '"' || escaped == '\\' || escaped == '/') {
            return escaped;
        }
        if (escaped == 'b') {
            return '\b';
        }
        if (escaped == 'f') {
            return '\f';
        }
        if (escaped == 'n') {
            return '\n';
        }
        if (escaped == 'r') {
            return '\r';
        }
        if (escaped == 't') {
            return '\t';
        }
        if (escaped == 'u') {
            // Escape unicode, por exemplo: \u00e1.
            return readUnicodeCharacter();
        }

        throw new IllegalArgumentException("Escape JSON invalido: \\" + escaped);
    }

    private char readUnicodeCharacter() {
        // Le quatro digitos hexadecimais e transforma em caractere.
        if (index + 4 > text.length()) {
            throw new IllegalArgumentException("Unicode JSON incompleto");
        }
        String hex = text.substring(index, index + 4);
        index += 4;
        try {
            return (char) Integer.parseInt(hex, 16);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Unicode JSON invalido: " + hex);
        }
    }

    private Number readNumber() {
        // Le numeros inteiros ou decimais do JSON.
        int start = index;

        if (peek() == '-') {
            index++;
        }

        while (!isAtEnd() && Character.isDigit(peek())) {
            index++;
        }

        boolean decimal = false;
        if (!isAtEnd() && peek() == '.') {
            // Se tiver ponto, o numero sera tratado como Double.
            decimal = true;
            index++;
            while (!isAtEnd() && Character.isDigit(peek())) {
                index++;
            }
        }

        if (!isAtEnd() && (peek() == 'e' || peek() == 'E')) {
            // Tambem aceita notacao cientifica, como 1.2e3.
            decimal = true;
            index++;
            if (!isAtEnd() && (peek() == '+' || peek() == '-')) {
                index++;
            }
            while (!isAtEnd() && Character.isDigit(peek())) {
                index++;
            }
        }

        String number = text.substring(start, index);
        try {
            if (decimal) {
                // Numero decimal.
                return Double.valueOf(number);
            }
            // Numero inteiro.
            return Long.valueOf(number);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Numero JSON invalido: " + number);
        }
    }

    private void readLiteral(String literal) {
        // Le palavras fixas do JSON: true, false e null.
        for (int i = 0; i < literal.length(); i++) {
            if (isAtEnd() || next() != literal.charAt(i)) {
                throw new IllegalArgumentException("Literal JSON invalido: " + literal);
            }
        }
    }

    private void skipWhitespace() {
        // Ignora espacos, quebras de linha e tabulacoes entre valores JSON.
        while (!isAtEnd() && Character.isWhitespace(peek())) {
            index++;
        }
    }

    private boolean tryRead(char expected) {
        // Tenta consumir um caractere se ele for o esperado.
        if (!isAtEnd() && peek() == expected) {
            index++;
            return true;
        }
        return false;
    }

    private void expect(char expected) {
        // Obriga que o proximo caractere seja exatamente o esperado.
        if (isAtEnd() || next() != expected) {
            throw new IllegalArgumentException("Esperado '" + expected + "' na posicao " + index);
        }
    }

    private char peek() {
        // Olha o caractere atual sem avancar.
        return text.charAt(index);
    }

    private char next() {
        // Retorna o caractere atual e avanca uma posicao.
        return text.charAt(index++);
    }

    private boolean isAtEnd() {
        // Verifica se chegamos ao fim do texto.
        return index >= text.length();
    }

    @SuppressWarnings("unchecked")
    private static void writeValue(StringBuilder builder, Object value) {
        // Escolhe como escrever cada tipo Java em formato JSON.
        if (value == null) {
            builder.append("null");
        } else if (value instanceof String) {
            writeString(builder, (String) value);
        } else if (value instanceof Number || value instanceof Boolean) {
            builder.append(String.valueOf(value));
        } else if (value instanceof Map) {
            writeObject(builder, (Map<String, Object>) value);
        } else if (value instanceof Iterable) {
            writeArray(builder, (Iterable<?>) value);
        } else {
            writeString(builder, String.valueOf(value));
        }
    }

    private static void writeObject(StringBuilder builder, Map<String, Object> map) {
        // Escreve um Map como objeto JSON.
        builder.append('{');
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) {
                builder.append(',');
            }
            first = false;
            writeString(builder, entry.getKey());
            builder.append(':');
            writeValue(builder, entry.getValue());
        }
        builder.append('}');
    }

    private static void writeArray(StringBuilder builder, Iterable<?> values) {
        // Escreve uma lista Java como array JSON.
        builder.append('[');
        boolean first = true;
        for (Object value : values) {
            if (!first) {
                builder.append(',');
            }
            first = false;
            writeValue(builder, value);
        }
        builder.append(']');
    }

    private static void writeString(StringBuilder builder, String value) {
        // Escreve texto com aspas e escapa caracteres especiais.
        builder.append('"');
        for (int i = 0; i < value.length(); i++) {
            char current = value.charAt(i);
            if (current == '"') {
                builder.append("\\\"");
            } else if (current == '\\') {
                builder.append("\\\\");
            } else if (current == '\b') {
                builder.append("\\b");
            } else if (current == '\f') {
                builder.append("\\f");
            } else if (current == '\n') {
                builder.append("\\n");
            } else if (current == '\r') {
                builder.append("\\r");
            } else if (current == '\t') {
                builder.append("\\t");
            } else if (current < 32) {
                String hex = Integer.toHexString(current);
                builder.append("\\u");
                for (int padding = hex.length(); padding < 4; padding++) {
                    builder.append('0');
                }
                builder.append(hex);
            } else {
                builder.append(current);
            }
        }
        builder.append('"');
    }
}
