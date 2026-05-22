import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Retorna a data de hoje
    public static LocalDate hoje() {
        return LocalDate.now();
    }

    // Retorna uma data X dias a partir de hoje
    public static LocalDate daquiEmDias(int dias) {
        return LocalDate.now().plusDays(dias);
    }

    // Formata um LocalDate para String "dd/MM/yyyy"
    public static String formatar(LocalDate data) {
        if (data == null)
            return "Não informado";
        return data.format(FORMATO);
    }

    // Converte uma String "dd/MM/yyyy" para LocalDate
    public static LocalDate parsear(String texto) {
        return LocalDate.parse(texto, FORMATO);
    }

    // Verifica se uma data já passou (está vencida)
    public static boolean estaVencido(LocalDate data) {
        return LocalDate.now().isAfter(data);
    }
}
