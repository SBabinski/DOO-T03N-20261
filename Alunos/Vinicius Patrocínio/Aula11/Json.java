public class Json {

    public static String isolarSecao(String texto, String inicio, String fim) {
        int indexInicio = texto.indexOf(inicio);

        if (indexInicio == -1)
            return "";

        int indexFim = texto.indexOf(fim, indexInicio + inicio.length());

        if (indexFim == -1)
            return "";

        return texto.substring(indexInicio, indexFim);
    }

    public static String extrairValor(String json, String chave) {

        int indexChave = json.indexOf(chave);

        if (indexChave == -1)
            return "N/A";

        int indexInicioValor = indexChave + chave.length();

        int indexFimValor = json.indexOf(",", indexInicioValor);

        if (indexFimValor == -1) {
            indexFimValor = json.indexOf("}", indexInicioValor);
        }

        return json.substring(indexInicioValor, indexFimValor).trim();
    }

    public static String extrairValorTexto(String json, String chave) {

        int indexChave = json.indexOf(chave);

        if (indexChave == -1)
            return "N/A";

        int indexInicioAspas =
                json.indexOf("\"", indexChave + chave.length());

        int indexFimAspas =
                json.indexOf("\"", indexInicioAspas + 1);

        return json.substring(indexInicioAspas + 1, indexFimAspas);
    }
}