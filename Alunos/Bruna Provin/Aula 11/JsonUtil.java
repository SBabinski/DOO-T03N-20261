public class JsonUtil {

    public static double extrairNumero(String json, String chave) {
        try {
            String busca = "\"" + chave + "\":";
            int inicio = json.indexOf(busca);

            if (inicio == -1) {
                return 0;
            }

            inicio += busca.length();

            int fim = inicio;

            while (fim < json.length()
                    && "0123456789.-".indexOf(json.charAt(fim)) != -1) {
                fim++;
            }

            return Double.parseDouble(json.substring(inicio, fim));

        } catch (Exception e) {
            return 0;
        }
    }

    public static String extrairTexto(String json, String chave) {
        try {
            String busca = "\"" + chave + "\":\"";
            int inicio = json.indexOf(busca);

            if (inicio == -1) {
                return "";
            }

            inicio += busca.length();

            int fim = json.indexOf("\"", inicio);

            return json.substring(inicio, fim);

        } catch (Exception e) {
            return "";
        }
    }
}