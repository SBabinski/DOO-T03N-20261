public class WeatherException extends Exception {

    public WeatherException(String mensagem) {
        super(mensagem);
    }

    public static void verificarStatus(int status, String cidade) throws WeatherException {
        switch (status) {
            case 200 -> {
            }
            case 401 -> throw new WeatherException("API Key inválida ou sem autorização.");
            case 404 -> throw new WeatherException("Cidade não encontrada: \"" + cidade + "\".");
            case 429 -> throw new WeatherException("Limite de requisições atingido. Tente mais tarde.");
            default -> throw new WeatherException("Erro na API (HTTP " + status + ").");
        }
    }
}
