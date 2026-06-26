import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Usuario local do sistema. Guarda o apelido e as tres listas de series.
 * Encapsula as regras de adicao/remocao (sem duplicatas) e a conversao
 * de/para Map para persistencia em JSON.
 */
public class Usuario {

    private String apelido;
    private final Map<TipoLista, List<Serie>> listas;

    public Usuario(String apelido) {
        this.apelido = (apelido == null) ? "" : apelido.trim();
        this.listas = new EnumMap<>(TipoLista.class);
        for (TipoLista t : TipoLista.values()) {
            listas.put(t, new ArrayList<>());
        }
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = (apelido == null) ? "" : apelido.trim();
    }

    /** Devolve uma copia da lista para evitar modificacoes externas indevidas. */
    public List<Serie> getLista(TipoLista tipo) {
        return new ArrayList<>(listas.get(tipo));
    }

    public boolean contem(TipoLista tipo, Serie serie) {
        return listas.get(tipo).contains(serie);
    }

    /** Adiciona a serie na lista; devolve false se ja existia. */
    public boolean adicionar(TipoLista tipo, Serie serie) {
        if (serie == null) return false;
        List<Serie> lista = listas.get(tipo);
        if (lista.contains(serie)) return false;
        lista.add(serie);
        return true;
    }

    /** Remove a serie da lista; devolve false se nao existia. */
    public boolean remover(TipoLista tipo, Serie serie) {
        if (serie == null) return false;
        return listas.get(tipo).remove(serie);
    }

    // ---- Persistencia --------------------------------------------------

    public Map<String, Object> toMap() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("apelido", apelido);
        Map<String, Object> mapaListas = new LinkedHashMap<>();
        for (TipoLista t : TipoLista.values()) {
            List<Object> series = new ArrayList<>();
            for (Serie s : listas.get(t)) {
                series.add(s.toMap());
            }
            mapaListas.put(t.name(), series);
        }
        m.put("listas", mapaListas);
        return m;
    }

    @SuppressWarnings("unchecked")
    public static Usuario fromMap(Map<String, Object> m) {
        Object ap = m.get("apelido");
        Usuario u = new Usuario(ap == null ? "" : ap.toString());

        Object listasObj = m.get("listas");
        if (listasObj instanceof Map) {
            Map<String, Object> mapaListas = (Map<String, Object>) listasObj;
            for (TipoLista t : TipoLista.values()) {
                Object arr = mapaListas.get(t.name());
                if (arr instanceof List) {
                    for (Object o : (List<Object>) arr) {
                        if (o instanceof Map) {
                            u.listas.get(t).add(Serie.fromMap((Map<String, Object>) o));
                        }
                    }
                }
            }
        }
        return u;
    }
}
