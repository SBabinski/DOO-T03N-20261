public class WeatherData {

    private final String cidade;
    private final double tempAtual;
    private final double tempMax;
    private final double tempMin;
    private final double humidade;
    private final String condicao;
    private final double precipitacao;
    private final double vento;
    private final double direcaoVento;

    public WeatherData(String cidade, double tempAtual, double tempMax, double tempMin,
                       double humidade, String condicao, double precipitacao,
                       double vento, double direcaoVento) {
        this.cidade = cidade;
        this.tempAtual = tempAtual;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.humidade = humidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.vento = vento;
        this.direcaoVento = direcaoVento;
    }

    public String getCidade() { return cidade; }
    public double getTempAtual() { return tempAtual; }
    public double getTempMax() { return tempMax; }
    public double getTempMin() { return tempMin; }
    public double getHumidade() { return humidade; }
    public String getCondicao() { return condicao; }

    public String getCondicaoPt() {
        if (condicao == null) return "N/A";
        String c = condicao.toLowerCase();
        
        if (c.contains("clear")) return "Céu Limpo";
        if (c.contains("partly cloudy")) return "Parcialmente Nublado";
        if (c.contains("cloudy")) return "Nublado";
        if (c.contains("overcast")) return "Encoberto";
        if (c.contains("rain")) {
            if (c.contains("heavy")) return "Chuva Forte";
            if (c.contains("light")) return "Chuva Leve";
            return "Chuva";
        }
        if (c.contains("snow")) return "Neve";
        if (c.contains("storm") || c.contains("thunderstorm")) return "Tempestade";
        if (c.contains("fog") || c.contains("mist")) return "Neblina";
        
        return condicao; // Caso não encontre, retorna o original
    }

    public double getPrecipitacao() { return precipitacao; }
    public double getVento() { return vento; }

    public String getDirecaoTexto() {
        String[] pts = {"N", "NE", "L", "SE", "S", "SO", "O", "NO"};
        return pts[(int) Math.round(direcaoVento / 45.0) % 8];
    }
}
