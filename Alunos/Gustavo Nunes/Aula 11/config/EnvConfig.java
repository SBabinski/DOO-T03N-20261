/*
 * Application configuration class.
 * Responsible for loading environment variables,
 * including the API authentication key.
 */
package config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    
    private static final Dotenv dotenv = Dotenv.load();
    
    public static String getApiKey() {
        return dotenv.get("API_KEY");
    }
    
}
