import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Classe usada nas Atv4 e Atv5
    static class Produto {
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
    }

    public static void main(String[] args) {
        atv1();
        atv2();
        atv3();
        atv4();
        atv5();
        atv6();
    }

    //ATV1
    private static void atv1() {
        List<Integer> numeros = Arrays.asList(3, 8, 15, 22, 7, 10, 19, 4, 12, 5);

        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("ATV1 - Números pares: " + pares);
    }

    //ATV2
    private static void atv2() {
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("ATV2 - Nomes em maiúsculas: " + nomesMaiusculos);
    }

    //ATV3
    private static void atv3() {
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println("ATV3 - Contagem de palavras: " + contagem);
    }

    // Lista de produtos compartilhada entre Atv4 e Atv5
    private static List<Produto> getProdutos() {
        return Arrays.asList(
                new Produto("Notebook", 3500.00),
                new Produto("Mouse", 50.00),
                new Produto("Teclado", 150.00),
                new Produto("Monitor", 800.00)
        );
    }

    //ATV4
    private static void atv4() {
        List<Produto> produtos = getProdutos();

        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("ATV4 - Produtos com preço acima de R$ 100,00:");
        produtosCaros.forEach(p -> System.out.println(" - " + p.getNome() + ": R$ " + p.getPreco()));
    }

    //ATV5
    private static void atv5() {
        List<Produto> produtos = getProdutos();

        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("ATV5 - Soma total dos produtos: R$ " + total);
    }

    //ATV6
    private static void atv6() {
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> ordenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("ATV6 - Ordenado por tamanho: " + ordenadas);
    }
}
