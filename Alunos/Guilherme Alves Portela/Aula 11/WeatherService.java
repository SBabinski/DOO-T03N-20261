public interface WeatherService {
    /**
     * Busca os dados climáticos com base em uma localidade.
     * @parameter city O nome da cidade ou região (ex: "Cascavel").
     * @return Um objeto WeatherData populado.
     * @throws Exception Caso ocorra algum erro na requisição ou no parse do JSON.
     */

    WeatherData getWeather(String city) throws Exception;
}