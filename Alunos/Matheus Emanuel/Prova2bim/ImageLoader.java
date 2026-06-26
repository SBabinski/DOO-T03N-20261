import java.awt.Image;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;

/**
 * Baixa e mantem em cache os posters das series. Falhas (sem rede, URL
 * invalida, formato nao suportado) sao silenciosamente tratadas devolvendo
 * null, para que a interface apenas exiba um espaco reservado.
 */
public final class ImageLoader {

    private static final Map<String, Image> cache = new ConcurrentHashMap<>();

    private ImageLoader() {}

    /** Devolve a imagem ja em cache, ou null se ainda nao foi carregada. */
    public static Image emCache(String url) {
        return (url == null) ? null : cache.get(url);
    }

    /**
     * Carrega a imagem da URL (operacao de rede; chamar fora da thread da UI).
     * Devolve null em qualquer falha, sem lancar excecao.
     */
    public static Image carregar(String url, int largura, int altura) {
        if (url == null || url.isBlank()) return null;
        Image cacheada = cache.get(url);
        if (cacheada != null) return cacheada;
        try {
            Image original = ImageIO.read(URI.create(url).toURL());
            if (original == null) return null;
            Image escalada = original.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            cache.put(url, escalada);
            return escalada;
        } catch (Exception e) {
            // Sem internet, URL invalida ou imagem corrompida: ignora.
            return null;
        }
    }
}
