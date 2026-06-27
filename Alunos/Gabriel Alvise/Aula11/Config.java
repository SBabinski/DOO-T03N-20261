import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao carregar config.properties"
            );
        }
    }

    public static String getApiKey() {
        return properties.getProperty("api.key");
    }
}