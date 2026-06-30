import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

// A "ponte" entre o JSON (Map/List que o Json me devolve) e o objeto Serie.
// Tudo aqui le com cuidado: se um campo nao existir ou vier null, uso um
// valor padrao em vez de deixar quebrar. Ajuda no "nao fechar inesperadamente".

public class ConversorSerie {

    // ---- JSON da API -> Serie ----
    // a busca da API devolve uma lista de { "score":..., "show": {dados} }.
    // aqui ja recebo o "show" (um Map) e monto a Serie.
    public static Serie deMapaApi(Map<String, Object> show) {
        if (show == null) {
            return null;
        }

        int id = (int) pegarNumero(show, "id", 0);
        String nome = pegarTexto(show, "name", "");
        String idioma = pegarTexto(show, "language", "Nao informado");

        // generos vem como lista em "genres"
        List<String> generos = new ArrayList<String>();
        Object generosObj = show.get("genres");
        if (generosObj instanceof List) {
            for (Object g : (List<?>) generosObj) {
                if (g != null) {
                    generos.add(g.toString());
                }
            }
        }

        // a nota fica escondida em rating -> average
        double nota = 0.0;
        Object ratingObj = show.get("rating");
        if (ratingObj instanceof Map) {
            Map<?, ?> rating = (Map<?, ?>) ratingObj;
            Object media = rating.get("average");
            if (media instanceof Number) {
                nota = ((Number) media).doubleValue();
            }
        }

        // estado = campo "status" (Running, Ended...)
        EstadoSerie estado = EstadoSerie.apartirDoTexto(pegarTexto(show, "status", null));

        // datas: premiered = estreia, ended = termino
        String estreia = pegarTexto(show, "premiered", "");
        String termino = pegarTexto(show, "ended", "");

        // emissora pode estar em "network" (TV) ou "webChannel" (streaming)
        String emissora = "Nao informado";
        Object networkObj = show.get("network");
        if (networkObj instanceof Map) {
            emissora = pegarTexto((Map<?, ?>) networkObj, "name", "Nao informado");
        } else {
            Object webObj = show.get("webChannel");
            if (webObj instanceof Map) {
                emissora = pegarTexto((Map<?, ?>) webObj, "name", "Nao informado");
            }
        }


        return new Serie(id, nome, idioma, generos, nota, estado, estreia, termino, emissora);
    }

    // ---- Serie -> Map (pra salvar no meu arquivo dados.json) ----
    public static Map<String, Object> paraMapa(Serie s) {
        Map<String, Object> mapa = new LinkedHashMap<String, Object>();
        mapa.put("id", s.getId());
        mapa.put("nome", s.getNome());
        mapa.put("idioma", s.getIdioma());
        mapa.put("generos", new ArrayList<Object>(s.getGeneros()));
        mapa.put("notaGeral", s.getNotaGeral());
        mapa.put("estado", s.getEstado().name()); // salvo o nome do enum, ex: "CONCLUIDA"
        mapa.put("dataEstreia", s.getDataEstreia());
        mapa.put("dataTermino", s.getDataTermino());
        mapa.put("emissora", s.getEmissora());
        return mapa;
    }

    // ---- Map (do meu arquivo) -> Serie ----
    public static Serie deMapaLocal(Map<String, Object> mapa) {
        if (mapa == null) {
            return null;
        }

        int id = (int) pegarNumero(mapa, "id", 0);
        String nome = pegarTexto(mapa, "nome", "");
        String idioma = pegarTexto(mapa, "idioma", "Nao informado");

        List<String> generos = new ArrayList<String>();
        Object generosObj = mapa.get("generos");
        if (generosObj instanceof List) {
            for (Object g : (List<?>) generosObj) {
                if (g != null) {
                    generos.add(g.toString());
                }
            }
        }

        double nota = pegarNumero(mapa, "notaGeral", 0.0);

        // o estado foi salvo como texto do enum. se vier algo torto, vira DESCONHECIDO
        EstadoSerie estado = EstadoSerie.DESCONHECIDO;
        String estadoTexto = pegarTexto(mapa, "estado", "DESCONHECIDO");
        try {
            estado = EstadoSerie.valueOf(estadoTexto);
        } catch (IllegalArgumentException e) {
            estado = EstadoSerie.DESCONHECIDO;
        }

        String estreia = pegarTexto(mapa, "dataEstreia", "");
        String termino = pegarTexto(mapa, "dataTermino", "");
        String emissora = pegarTexto(mapa, "emissora", "Nao informado");

        return new Serie(id, nome, idioma, generos, nota, estado, estreia, termino, emissora);
    }

    // ---- ajudantes que leem sem quebrar ----

    // le um texto; se nao tiver, devolve o padrao
    private static String pegarTexto(Map<?, ?> mapa, String chave, String padrao) {
        Object valor = mapa.get(chave);
        if (valor == null) {
            return padrao;
        }
        String texto = valor.toString().trim();
        return texto.isEmpty() ? padrao : texto;
    }

    // le um numero; se nao tiver/for invalido, devolve o padrao
    private static double pegarNumero(Map<?, ?> mapa, String chave, double padrao) {
        Object valor = mapa.get(chave);
        if (valor instanceof Number) {
            return ((Number) valor).doubleValue();
        }
        return padrao;
    }
}
