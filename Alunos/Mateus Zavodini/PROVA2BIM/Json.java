
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Essa classe le e escreve texto em formato JSON, sem usar bibliotecas externas.
public class Json {

    public static Object ler(String texto) {
        Cursor cursor = new Cursor(texto);
        cursor.pularEspacos();
        return lerValor(cursor);
    }

    private static class Cursor {
        String texto;
        int posicao;

        Cursor(String texto) {
            this.texto = texto;
            this.posicao = 0;
        }

        char atual() {
            return texto.charAt(posicao);
        }

        boolean temMais() {
            return posicao < texto.length();
        }

        void pularEspacos() {
            while (temMais() && Character.isWhitespace(atual())) {
                posicao++;
            }
        }
    }

    private static Object lerValor(Cursor cursor) {
        cursor.pularEspacos();
        char c = cursor.atual();
        if (c == '{') return lerObjeto(cursor);
        if (c == '[') return lerLista(cursor);
        if (c == '"') return lerTexto(cursor);
        if (c == 't' || c == 'f') return lerBooleano(cursor);
        if (c == 'n') { cursor.posicao += 4; return null; }
        return lerNumero(cursor);
    }

    private static Map<String, Object> lerObjeto(Cursor cursor) {
        Map<String, Object> mapa = new LinkedHashMap<>();
        cursor.posicao++;
        cursor.pularEspacos();
        if (cursor.atual() == '}') { cursor.posicao++; return mapa; }

        while (true) {
            cursor.pularEspacos();
            String chave = lerTexto(cursor);
            cursor.pularEspacos();
            cursor.posicao++;
            Object valor = lerValor(cursor);
            mapa.put(chave, valor);
            cursor.pularEspacos();
            if (cursor.atual() == ',') { cursor.posicao++; continue; }
            if (cursor.atual() == '}') { cursor.posicao++; break; }
        }
        return mapa;
    }

    private static List<Object> lerLista(Cursor cursor) {
        List<Object> lista = new ArrayList<>();
        cursor.posicao++;
        cursor.pularEspacos();
        if (cursor.atual() == ']') { cursor.posicao++; return lista; }

        while (true) {
            lista.add(lerValor(cursor));
            cursor.pularEspacos();
            if (cursor.atual() == ',') { cursor.posicao++; continue; }
            if (cursor.atual() == ']') { cursor.posicao++; break; }
        }
        return lista;
    }

    private static String lerTexto(Cursor cursor) {
        StringBuilder texto = new StringBuilder();
        cursor.posicao++;
        while (cursor.atual() != '"') {
            char c = cursor.atual();
            if (c == '\\') {
                cursor.posicao++;
                char escape = cursor.atual();
                switch (escape) {
                    case '"': texto.append('"'); break;
                    case '\\': texto.append('\\'); break;
                    case 'n': texto.append('\n'); break;
                    case 't': texto.append('\t'); break;
                    default: texto.append(escape);
                }
            } else {
                texto.append(c);
            }
            cursor.posicao++;
        }
        cursor.posicao++;
        return texto.toString();
    }

    private static Object lerNumero(Cursor cursor) {
        int inicio = cursor.posicao;
        while (cursor.temMais() && "-+.eE0123456789".indexOf(cursor.atual()) >= 0) {
            cursor.posicao++;
        }
        String textoNumero = cursor.texto.substring(inicio, cursor.posicao);
        return Double.parseDouble(textoNumero);
    }

    private static Boolean lerBooleano(Cursor cursor) {
        if (cursor.atual() == 't') {
            cursor.posicao += 4;
            return Boolean.TRUE;
        } else {
            cursor.posicao += 5;
            return Boolean.FALSE;
        }
    }

    public static String escrever(Object valor) {
        StringBuilder resultado = new StringBuilder();
        escreverValor(valor, resultado, 0);
        return resultado.toString();
    }

    @SuppressWarnings("unchecked")
    private static void escreverValor(Object valor, StringBuilder destino, int nivel) {
        if (valor == null) {
            destino.append("null");
        } else if (valor instanceof Map) {
            escreverObjeto((Map<String, Object>) valor, destino, nivel);
        } else if (valor instanceof List) {
            escreverLista((List<Object>) valor, destino, nivel);
        } else if (valor instanceof String) {
            escreverTexto((String) valor, destino);
        } else if (valor instanceof Number || valor instanceof Boolean) {
            destino.append(valor.toString());
        } else {
            escreverTexto(valor.toString(), destino);
        }
    }

    private static void escreverObjeto(Map<String, Object> mapa, StringBuilder destino, int nivel) {
        if (mapa.isEmpty()) { destino.append("{}"); return; }
        destino.append("{\n");
        int i = 0;
        for (Map.Entry<String, Object> entrada : mapa.entrySet()) {
            indentar(destino, nivel + 1);
            escreverTexto(entrada.getKey(), destino);
            destino.append(": ");
            escreverValor(entrada.getValue(), destino, nivel + 1);
            if (i < mapa.size() - 1) destino.append(",");
            destino.append("\n");
            i++;
        }
        indentar(destino, nivel);
        destino.append("}");
    }

    private static void escreverLista(List<Object> lista, StringBuilder destino, int nivel) {
        if (lista.isEmpty()) { destino.append("[]"); return; }
        destino.append("[\n");
        for (int i = 0; i < lista.size(); i++) {
            indentar(destino, nivel + 1);
            escreverValor(lista.get(i), destino, nivel + 1);
            if (i < lista.size() - 1) destino.append(",");
            destino.append("\n");
        }
        indentar(destino, nivel);
        destino.append("]");
    }

    private static void escreverTexto(String texto, StringBuilder destino) {
        destino.append('"');
        for (char c : texto.toCharArray()) {
            if (c == '"') destino.append("\\\"");
            else if (c == '\\') destino.append("\\\\");
            else if (c == '\n') destino.append("\\n");
            else destino.append(c);
        }
        destino.append('"');
    }

    private static void indentar(StringBuilder destino, int nivel) {
        for (int i = 0; i < nivel; i++) destino.append("    ");
    }
}