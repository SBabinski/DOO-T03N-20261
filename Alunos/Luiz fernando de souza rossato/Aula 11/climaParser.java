package clima;

import clima.Clima;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;



import java.io.IOException;

public class climaParser {
     public static Clima converter(String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(json);

        JsonNode atual = root.get("currentConditions");

        JsonNode hoje = root.get("days").get(0);

        Clima clima = new Clima();

        clima.setCidade(root.get("resolvedAddress").asText());

        clima.setTemperatura(
                atual.get("temp").asDouble());

        clima.setUmidade(
                atual.get("humidity").asDouble());

        clima.setCondicao(
                atual.get("conditions").asText());

        clima.setPrecipitacao(
                atual.get("precip").asDouble());

        clima.setVento(
                atual.get("windspeed").asDouble());

        clima.setDirecaoVento(
                atual.get("winddir").asDouble());

        clima.setMaxima(
                hoje.get("tempmax").asDouble());

        clima.setMinima(
                hoje.get("tempmin").asDouble());

        return clima;
    }
}
    

