package aula12;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // ATV1
        // Lista de números inteiros, retornando apenas os pares usando Stream API
        List<Integer> numeros = Arrays.asList(10, 3, 5, 8, 12, 7, 20, 15);

        List<Integer> numerosPares = numeros.stream()
                .filter(numero -> numero % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("ATV1 - Números pares:");
        System.out.println(numerosPares);


        // ATV2
        // Convertendo todos os nomes para letras maiúsculas usando Stream API
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(nome -> nome.toUpperCase())
                .collect(Collectors.toList());

        System.out.println("\nATV2 - Nomes em maiúsculo:");
        System.out.println(nomesMaiusculos);


        // ATV3
        // Contando quantas vezes cada palavra aparece na lista usando Stream API
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println("\nATV3 - Contagem de palavras:");
        System.out.println(contagemPalavras);


        // ATV4
        // Filtrando produtos com preço maior que R$ 100,00 usando Stream API
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 120.00),
                new Produto("Mouse", 80.00),
                new Produto("Monitor", 900.00),
                new Produto("Cabo HDMI", 35.00)
        );

        List<Produto> produtosCaros = produtos.stream()
                .filter(produto -> produto.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("\nATV4 - Produtos com preço maior que R$ 100,00:");
        produtosCaros.forEach(produto -> System.out.println(produto));


        // ATV5
        // Somando o valor total dos produtos usando Stream API
        double somaTotal = produtos.stream()
                .mapToDouble(produto -> produto.getPreco())
                .sum();

        System.out.println("\nATV5 - Soma total dos produtos:");
        System.out.println("R$ " + somaTotal);


        // ATV6
        // Ordenando palavras pelo tamanho, da menor para a maior, usando Stream API
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(palavra -> palavra.length()))
                .collect(Collectors.toList());

        System.out.println("\nATV6 - Linguagens ordenadas pelo tamanho:");
        System.out.println(linguagensOrdenadas);
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

    public String toString() {
        return "Produto: " + nome + " | Preço: R$ " + preco;
    }
}