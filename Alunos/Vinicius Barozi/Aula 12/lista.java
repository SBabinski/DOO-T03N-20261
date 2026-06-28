import java.util.*;
import java.util.stream.*;

public class lista {

    // Classe Produto (usada nas Atv4 e Atv5)
    static class Produto {
        private String nome;
        private double preco;

        public Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }

        public String getNome() { return nome; }
        public double getPreco() { return preco; }

        @Override
        public String toString() {
            return nome + " (R$ " + String.format("%.2f", preco) + ")";
        }
    }

    public static void main(String[] args) {

        //ATV1
        List<Integer> numeros = Arrays.asList(1, 4, 7, 12, 3, 18, 22, 9, 30, 11);

        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("=== ATV1 - Números Pares ===");
        System.out.println("Lista original: " + numeros);
        System.out.println("Números pares: " + pares);

        //ATV2
        List<String> nomes = Arrays.asList("roberto", "jose", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("\n=== ATV2 - Nomes em Maiusculo ===");
        System.out.println("Lista original: " + nomes);
        System.out.println("Em maiusculo: " + nomesMaiusculos);

        //ATV3
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        System.out.println("\n=== ATV3 - Contagem de Palavras ===");
        System.out.println("Lista original: " + palavras);
        System.out.println("Contagem:");
        contagemPalavras.forEach((palavra, count) ->
                System.out.println("  \"" + palavra + "\": " + count + " vez(es)"));

        //ATV4
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 150.00),
                new Produto("Mouse", 80.00),
                new Produto("Monitor", 900.00),
                new Produto("Headset", 99.90)
        );

        List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("\n=== ATV4 - Produtos com Preço > R$ 100,00 ===");
        System.out.println("Todos os produtos: " + produtos);
        System.out.println("Produtos filtrados: " + produtosFiltrados);

        //ATV5
        double somaTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("\n=== ATV5 - Soma Total dos Produtos ===");
        System.out.printf("Soma total: R$ %.2f%n", somaTotal);

        //ATV6
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("\n=== ATV6 - Linguagens Ordenadas por Tamanho ===");
        System.out.println("Lista original: " + linguagens);
        System.out.println("Ordenadas (menor → maior): " + linguagensOrdenadas);
    }
}