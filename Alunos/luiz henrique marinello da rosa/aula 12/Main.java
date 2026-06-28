import java.util.*;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) {

        // ATV1
        // Recebe uma lista de inteiros e retorna apenas os números pares usando Stream API
        List<Integer> numeros = Arrays.asList(3, 8, 15, 22, 7, 44, 13, 90, 5, 66);

        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("=== ATV1 - Números pares ===");
        System.out.println("Lista original: " + numeros);
        System.out.println("Números pares:  " + pares);


        // ATV2
        // Converte lista de nomes para letras maiúsculas usando Stream API
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("\n=== ATV2 - Nomes em maiúsculas ===");
        System.out.println("Original:    " + nomes);
        System.out.println("Maiúsculas:  " + nomesMaiusculos);


        // ATV3
        // Conta quantas vezes cada palavra única aparece na lista usando Stream API
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        System.out.println("\n=== ATV3 - Contagem de palavras ===");
        System.out.println("Lista: " + palavras);
        System.out.println("Contagem:");
        contagemPalavras.forEach((palavra, contagem) ->
                System.out.println("  \"" + palavra + "\" → " + contagem + " vez(es)"));


        // ATV4
        // Filtra produtos com preço maior que R$ 100,00 usando Stream API
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado",     89.90),
                new Produto("Monitor",    899.00),
                new Produto("Mouse",       59.99),
                new Produto("Headset",    199.90)
        );

        List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("\n=== ATV4 - Produtos com preço > R$ 100,00 ===");
        System.out.println("Todos os produtos:");
        produtos.forEach(p -> System.out.printf("  %-10s  R$ %.2f%n", p.getNome(), p.getPreco()));
        System.out.println("Produtos filtrados:");
        produtosFiltrados.forEach(p -> System.out.printf("  %-10s  R$ %.2f%n", p.getNome(), p.getPreco()));


        // ATV5
        // Soma o valor total dos produtos da lista usando Stream API
        double totalProdutos = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("\n=== ATV5 - Soma total dos produtos ===");
        System.out.printf("Valor total: R$ %.2f%n", totalProdutos);


        // ATV6
        // Ordena lista de linguagens pelo tamanho da palavra (menor → maior) usando Stream API
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("\n=== ATV6 - Linguagens ordenadas por tamanho ===");
        System.out.println("Original:  " + linguagens);
        System.out.println("Ordenada:  " + linguagensOrdenadas);
    }
}

class Produto {

    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome  = nome;
        this.preco = preco;
    }

    public String getNome()  { return nome;  }
    public double getPreco() { return preco; }
}