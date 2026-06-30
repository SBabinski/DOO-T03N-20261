
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Usuário local e suas coleções pessoais. */
public final class Usuario {
    private String apelido;
    private final Map<TipoLista, LinkedHashMap<Long, Serie>> listas = new EnumMap<>(TipoLista.class);

    public Usuario(String apelido) {
        setApelido(apelido);
        for (TipoLista tipo : TipoLista.values()) {
            listas.put(tipo, new LinkedHashMap<>());
        }
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido == null || apelido.isBlank() ? "Visitante" : apelido.trim();
    }

    public boolean adicionar(TipoLista tipo, Serie serie) {
        validar(tipo, serie);
        return listas.get(tipo).put(serie.getId(), serie) == null;
    }

    public boolean remover(TipoLista tipo, Serie serie) {
        validar(tipo, serie);
        return listas.get(tipo).remove(serie.getId()) != null;
    }

    public boolean contem(TipoLista tipo, Serie serie) {
        validar(tipo, serie);
        return listas.get(tipo).containsKey(serie.getId());
    }

    public List<Serie> getSeries(TipoLista tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("A lista deve ser informada.");
        }
        return new ArrayList<>(listas.get(tipo).values());
    }

    public int quantidade(TipoLista tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("A lista deve ser informada.");
        }
        return listas.get(tipo).size();
    }

    private static void validar(TipoLista tipo, Serie serie) {
        if (tipo == null || serie == null) {
            throw new IllegalArgumentException("A lista e a série devem ser informadas.");
        }
    }
}
