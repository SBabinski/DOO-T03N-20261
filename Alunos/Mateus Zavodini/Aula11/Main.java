import java.util.Scanner;

public class Main {

    private static final String CHAVE_API = "4QW5AKXEVM5SQSZ4MRGPREJ56";

    public static void main(String[] args) {
        Scanner leitorEntrada = new Scanner(System.in);

        System.out.print("Digite o nome da cidade: ");
        String cidade = leitorEntrada.nextLine().trim();
        leitorEntrada.close();

        try {
            BuscadorClima buscador = new BuscadorClima(CHAVE_API);
            DadosClima clima = buscador.buscarClima(cidade);

            System.out.println("\n===== CLIMA EM: " + clima.cidade + " =====");
            System.out.printf("Temperatura atual : %.1f C\n", clima.temperaturaAtual);
            System.out.printf("Maxima / Minima   : %.1f C / %.1f C\n", clima.temperaturaMaxima, clima.temperaturaMinima);
            System.out.printf("Humidade          : %.1f %%\n", clima.humidade);
            System.out.printf("Condicao do tempo : %s\n", clima.condicaoTempo);
            System.out.printf("Precipitacao      : %.1f mm\n", clima.precipitacao);
            System.out.printf("Vento             : %.1f km/h direcao %s\n", clima.velocidadeVento, clima.direcaoVento);

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}