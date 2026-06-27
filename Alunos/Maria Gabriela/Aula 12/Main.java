import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //ATV1
        System.out.println("\nATIVIDADE 1");
        Scanner input = new Scanner(System.in);
        List<Integer> numerosDigitados = new ArrayList<>();

        System.out.println("Informe 8 números inteiros:");
        for (int i = 0; i < 8; i++) {
            numerosDigitados.add(input.nextInt());
        }

        List<Integer> listaPares = numerosDigitados.stream()
                .filter(n -> n % 2 == 0)
                .toList();

        System.out.println("Valores pares encontrados:");
        for (Integer valor : listaPares) {
            System.out.println(valor);
        }


        //ATV2
        System.out.println("\nATIVIDADE 2");
        List<String> listaNomes = List.of("roberto", "josé", "caio", "vinicius");

        List<String> resultadoMaiusculo = listaNomes.stream()
                .map(String::toUpperCase)
                .toList();

        System.out.println("Nomes em maiúsculo:");
        resultadoMaiusculo.forEach(System.out::println);


        //ATV3
        System.out.println("\nATIVIDADE 3");
        List<String> listaPalavras = List.of("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> mapaContagem = listaPalavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        System.out.println("Frequência das palavras:");
        System.out.println(mapaContagem);


        //ATV4
        System.out.println("\nATIVIDADE 4");

        List<Produto> listaProdutos = List.of(
                new Produto("Mouse", 80f),
                new Produto("Teclado", 150f),
                new Produto("Monitor", 900f),
                new Produto("Headset", 120f)
        );

        List<Produto> filtrados = listaProdutos.stream()
                .filter(p -> p.getPreco() > 100)
                .toList();

        System.out.println("Produtos acima de R$100:");
        filtrados.forEach(p -> System.out.println(p.getNome() + " - R$ " + p.getPreco()));


        //ATV5
        System.out.println("\nATIVIDADE 5");
        double somaTotal = listaProdutos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.printf("Total dos produtos: R$ %.2f\n", somaTotal);


        //ATV6
        System.out.println("\nATIVIDADE 6");
        List<String> listaLinguagens = List.of("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> ordenadas = listaLinguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList();

        System.out.println("Ordenadas por tamanho:");
        ordenadas.forEach(System.out::println);
    }


    static class Produto {
        private String nome;
        private float preco;

        public Produto(String nome, float preco) {
            this.nome = nome;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public float getPreco() {
            return preco;
        }
    }
}