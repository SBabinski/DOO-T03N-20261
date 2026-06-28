import java.util.*;
import java.util.stream.*;

public class Main {

    // Classe de Produtos
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
            return nome + " - R$ " + String.format("%.2f", preco);
        }
    }

    public static void main(String[] args) {

        //Atividade 01
        System.out.println("Atividade 01 - Números Pares");
        List<Integer> numeros = Arrays.asList(1, 4, 7, 12, 19, 22, 35, 40, 51, 64);

        List<Integer> numerosPares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Lista original: " + numeros);
        System.out.println("Números pares: " + numerosPares);

        //Atvidade 02
        System.out.println("\nAtividade 02 - Nomes em Maiúsculas");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Lista original: " + nomes);
        System.out.println("Nomes em maiúsculas: " + nomesMaiusculos);

        //Atividade 03
        System.out.println("\nAtividade 03 - Contagem de Palavras");
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        System.out.println("Lista de palavras: " + palavras);
        System.out.println("Contagem de cada palavra:");
        contagemPalavras.forEach((palavra, contagem) ->
                System.out.println("  \"" + palavra + "\": " + contagem + " vez(es)"));

        //Atividade 04
        System.out.println("\nAtividade 04 - Produtos com Preço maior que 100");
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 150.00),
                new Produto("Mouse", 80.00),
                new Produto("Monitor", 900.00),
                new Produto("Headset", 99.90)
        );

        List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("Todos os produtos:");
        produtos.forEach(p -> System.out.println("  " + p));
        System.out.println("Produtos com preço > R$ 100,00:");
        produtosFiltrados.forEach(p -> System.out.println("  " + p));

        //Atividade 05
        System.out.println("\nAtividade 05 - Soma total dos Produtos");
        double somaTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("Valor total de todos os produtos: R$ " + String.format("%.2f", somaTotal));

        //Atividade 06
        System.out.println("\nAtividade 06 - Linguagens separadas por tamanho");
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("Lista original: " + linguagens);
        System.out.println("Ordenadas por tamanho (menor → maior): " + linguagensOrdenadas);
    }
}