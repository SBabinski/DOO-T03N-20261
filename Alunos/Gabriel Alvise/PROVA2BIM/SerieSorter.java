import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SerieSorter {

    public static List<Serie> ordenarPorNome(List<Serie> series) {
        List<Serie> seriesOrdenadas = copiarLista(series);

        seriesOrdenadas.sort(Comparator.comparing(
                serie -> textoSeguro(serie.getNome()),
                String.CASE_INSENSITIVE_ORDER
        ));

        return seriesOrdenadas;
    }

    public static List<Serie> ordenarPorNota(List<Serie> series) {
        List<Serie> seriesOrdenadas = copiarLista(series);

        seriesOrdenadas.sort(Comparator.comparingDouble(Serie::getNotaGeral).reversed());

        return seriesOrdenadas;
    }

    public static List<Serie> ordenarPorEstado(List<Serie> series) {
        List<Serie> seriesOrdenadas = copiarLista(series);

        seriesOrdenadas.sort(Comparator.comparing(
                serie -> textoSeguro(serie.getEstado()),
                String.CASE_INSENSITIVE_ORDER
        ));

        return seriesOrdenadas;
    }

    public static List<Serie> ordenarPorDataEstreia(List<Serie> series) {
        List<Serie> seriesOrdenadas = copiarLista(series);

        seriesOrdenadas.sort(Comparator.comparing(
                serie -> converterDataSegura(serie.getDataEstreia())
        ));

        return seriesOrdenadas;
    }

    public static List<Serie> ordenarPorTipo(List<Serie> series, String tipoOrdenacao) {
        if (tipoOrdenacao == null) {
            return copiarLista(series);
        }

        switch (tipoOrdenacao) {
            case "Nome":
                return ordenarPorNome(series);

            case "Nota":
                return ordenarPorNota(series);

            case "Estado":
                return ordenarPorEstado(series);

            case "Data de estreia":
                return ordenarPorDataEstreia(series);

            default:
                return copiarLista(series);
        }
    }

    private static List<Serie> copiarLista(List<Serie> series) {
        if (series == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(series);
    }

    private static String textoSeguro(String texto) {
        if (texto == null) {
            return "";
        }

        return texto;
    }

    private static LocalDate converterDataSegura(String data) {
        if (data == null || data.trim().isEmpty() || data.equals("Não informado")) {
            return LocalDate.MAX;
        }

        try {
            return LocalDate.parse(data);
        } catch (DateTimeParseException erro) {
            return LocalDate.MAX;
        }
    }
}