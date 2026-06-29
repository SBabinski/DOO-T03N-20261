package com.seriestv;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImagemUtil {

    private static final int LARGURA_CAPA = 60;
    private static final int ALTURA_CAPA = 84;
    private static final Map<String, ImageIcon> CACHE = new ConcurrentHashMap<>();

    private ImagemUtil() {
    }

    public static ImageIcon carregarCapa(String enderecoImagem) {
        if (enderecoImagem == null || enderecoImagem.isBlank()) {
            return null;
        }

        return CACHE.computeIfAbsent(enderecoImagem, ImagemUtil::baixarCapa);
    }

    private static ImageIcon baixarCapa(String enderecoImagem) {
        try {
            URLConnection conexao = new URL(enderecoImagem).openConnection();
            conexao.setConnectTimeout(3000);
            conexao.setReadTimeout(3000);

            try (InputStream entrada = conexao.getInputStream()) {
                BufferedImage imagem = ImageIO.read(entrada);
                if (imagem == null) {
                    return null;
                }

                Image reduzida = imagem.getScaledInstance(LARGURA_CAPA, ALTURA_CAPA, Image.SCALE_SMOOTH);
                return new ImageIcon(reduzida);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
