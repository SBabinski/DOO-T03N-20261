public class Teste {

    public static void main(String[] args) {

        Inquilino i1 =
            new Inquilino(
                "Carlos",
                "12345678900",
                "99999-1111"
            );

        Apartamento ap =
            new Apartamento(
                "Rua Central 100",
                1200,
                5
            );

        Casa casa =
            new Casa(
                "Rua Verde 90",
                1800,
                true
            );

        i1.exibirDados();

        ap.exibirInformacoes();

        casa.exibirInformacoes();

    }
}