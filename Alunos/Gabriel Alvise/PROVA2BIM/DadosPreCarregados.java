import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DadosPreCarregados {

    public static DadosAplicacao criarDadosIniciais() {
        Usuario usuario = new Usuario("Gabriel");

        List<Serie> favoritos = new ArrayList<>();
        favoritos.add(criarBreakingBad());
        favoritos.add(criarTheLastOfUs());

        List<Serie> seriesAssistidas = new ArrayList<>();
        seriesAssistidas.add(criarFriends());

        List<Serie> seriesDesejo = new ArrayList<>();
        seriesDesejo.add(criarStrangerThings());

        return new DadosAplicacao(
                usuario,
                favoritos,
                seriesAssistidas,
                seriesDesejo
        );
    }

    public static List<Serie> criarSugestoes() {
        List<Serie> sugestoes = new ArrayList<>();

        sugestoes.add(criarBreakingBad());
        sugestoes.add(criarTheLastOfUs());
        sugestoes.add(criarFriends());
        sugestoes.add(criarStrangerThings());

        return sugestoes;
    }

    private static Serie criarBreakingBad() {
        return new Serie(
                169,
                "Breaking Bad",
                "English",
                Arrays.asList("Drama", "Crime", "Thriller"),
                9.2,
                "Ended",
                "2008-01-20",
                "2013-09-29",
                "AMC",
                "https://static.tvmaze.com/uploads/images/medium_portrait/0/2400.jpg"
        );
    }

    private static Serie criarTheLastOfUs() {
        return new Serie(
                46562,
                "The Last of Us",
                "English",
                Arrays.asList("Drama", "Adventure", "Science-Fiction"),
                8.7,
                "Running",
                "2023-01-15",
                "Não informado",
                "HBO",
                "https://static.tvmaze.com/uploads/images/medium_portrait/563/1409008.jpg"
        );
    }

    private static Serie criarFriends() {
        return new Serie(
                431,
                "Friends",
                "English",
                Arrays.asList("Comedy", "Romance"),
                8.6,
                "Ended",
                "1994-09-22",
                "2004-05-06",
                "NBC",
                "https://static.tvmaze.com/uploads/images/medium_portrait/41/104550.jpg"
        );
    }

    private static Serie criarStrangerThings() {
        return new Serie(
                2993,
                "Stranger Things",
                "English",
                Arrays.asList("Drama", "Fantasy", "Horror"),
                8.5,
                "Ended",
                "2016-07-15",
                "2025-12-31",
                "Netflix",
                "https://static.tvmaze.com/uploads/images/medium_portrait/200/501942.jpg"
        );
    }
}