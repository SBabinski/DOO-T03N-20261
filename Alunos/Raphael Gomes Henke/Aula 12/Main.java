import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // ==========================
        // ATV1
        // ==========================
        List<Integer> numeros = Arrays.asList(5, 12, 8, 3, 20, 17, 14, 9, 22);

        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("ATV1 - Números pares:");
        System.out.println(pares);


        // ==========================
        // ATV2
        // ==========================
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("\nATV2 - Nomes em maiúsculo:");
        System.out.println(nomesMaiusculos);


        // ==========================
        // ATV3
        // ==========================
        List<String> palavras = Arrays.asList(
                "se",
                "talvez",
                "hoje",
                "sábado",
                "se",
                "quarta",
                "sábado"
        );

        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(
                        palavra -> palavra,
                        Collectors.counting()
                ));

        System.out.println("\nATV3 - Contagem das palavras:");
        contagem.forEach((palavra, quantidade) ->
                System.out.println(palavra + ": " + quantidade)
        );


        // ==========================
        // ATV4
        // ==========================
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 120.00),
                new Produto("Mouse", 80.00),
                new Produto("Monitor", 900.00),
                new Produto("Headset", 150.00)
        );

        List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getPreco() > 100)
                .collect(Collectors.toList());

        System.out.println("\nATV4 - Produtos com preço maior que R$100:");
        produtosFiltrados.forEach(System.out::println);


        // ==========================
        // ATV5
        // ==========================
        double soma = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("\nATV5 - Soma dos preços dos produtos:");
        System.out.println("Total: R$ " + soma);


        // ==========================
        // ATV6
        // ==========================
        List<String> linguagens = Arrays.asList(
                "Java",
                "Python",
                "C",
                "JavaScript",
                "Ruby"
        );

        List<String> ordenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("\nATV6 - Linguagens ordenadas pelo tamanho:");
        System.out.println(ordenadas);
    }
}

class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + preco;
    }
}