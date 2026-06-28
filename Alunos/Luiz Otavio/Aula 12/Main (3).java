import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("Aula 12 - Lista de exercicios StreamAPI");
        System.out.println("==========================================");

        atv1();
        atv2();
        atv3();
        atv4();
        atv5();
        atv6();

        System.out.println("\n==========================================");
    }

    // ATV1 - Filtrar numeros pares
    private static void atv1() {
        System.out.println("\n--- ATV1 ---");

        List<Integer> numeros = Arrays.asList(3, 8, 15, 22, 7, 10, 19, 24, 1, 30);

        System.out.println("Lista original: " + numeros);

        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Numeros pares: " + pares);
    }

    // ATV2 - Converter nomes para maiusculas
    private static void atv2() {
        System.out.println("\n--- ATV2 ---");

        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        System.out.println("Nomes originais: " + nomes);

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Nomes em maiusculas: " + nomesMaiusculos);
    }

    // ATV3 - Contar ocorrencias de cada palavra
    private static void atv3() {
        System.out.println("\n--- ATV3 ---");

        List<String> palavras = Arrays.asList(
                "se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        System.out.println("Palavras: " + palavras);

        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        System.out.println("Contagem de ocorrencias:");
        contagem.forEach((palavra, qtd) ->
                System.out.println(palavra + " -> " + qtd + " vez(es)"));
    }

    // ATV4 - Produtos com preco maior que R$ 100,00
    private static void atv4() {
        System.out.println("\n--- ATV4 ---");

        List<Produto> produtos = criarProdutos();

        System.out.println("Lista de produtos:");
        produtos.forEach(p -> System.out.println("  " + p));

        List<Produto> caros = produtos.stream()
                .filter(p -> p.getPreco() > 100.0)
                .collect(Collectors.toList());

        System.out.println("Produtos acima de R$ 100,00:");
        caros.forEach(p -> System.out.println("  " + p));
    }

    // ATV5 - Soma do valor total dos produtos
    private static void atv5() {
        System.out.println("\n--- ATV5 ---");

        List<Produto> produtos = criarProdutos();

        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.printf("Valor total em estoque: R$ %.2f%n", total);
    }

    // ATV6 - Ordenar lista pelo tamanho das palavras
    private static void atv6() {
        System.out.println("\n--- ATV6 ---");

        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        System.out.println("Antes de ordenar: " + linguagens);

        List<String> ordenadas = linguagens.stream()
                .sorted((a, b) -> a.length() - b.length())
                .collect(Collectors.toList());

        System.out.println("Ordenadas por tamanho: " + ordenadas);
    }

    private static List<Produto> criarProdutos() {
        return Arrays.asList(
                new Produto("Mochila", 120.0),
                new Produto("Caderno", 20.0),
                new Produto("Caneta", 3.0),
                new Produto("Notebook", 2000.0)
        );
    }
}
