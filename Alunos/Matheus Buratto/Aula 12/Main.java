import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        atv1();
        atv2();
        atv3();
        atv4();
        atv5();
        atv6();
    }

    private static void atv1() {
        System.out.println("\n// ATV1");
        List<Integer> numeros = Arrays.asList(3, 8, 15, 22, 7, 10, 19, 24, 1, 30);
        System.out.println("Lista original: " + numeros);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Numeros pares: " + pares);
    }

    private static void atv2() {
        System.out.println("\n// ATV2");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        System.out.println("Nomes originais: " + nomes);
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Nomes em maiusculas: " + nomesMaiusculos);
    }

    private static void atv3() {
        System.out.println("\n// ATV3");
        List<String> palavras = Arrays.asList(
                "se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        System.out.println("Palavras: " + palavras);
        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        System.out.println("Contagem de ocorrencias:");
        contagem.forEach((palavra, qtd) ->
                System.out.println("  " + palavra + "-> " + qtd + (qtd == 1 ? " vez" : " vezes")));
    }

    private static void atv4() {
        System.out.println("\n// ATV4");
        List<Produto> produtos = criarProdutos();
        System.out.println("Lista de produtos:");
        produtos.forEach(p -> System.out.println("  " + p));
        List<Produto> caros = produtos.stream()
                .filter(p -> p.getPreco() > 100.0)
                .collect(Collectors.toList());
        System.out.println("Produtos acima de R$ 100,00:");
        caros.forEach(p -> System.out.println("  " + p));
    }

    private static void atv5() {
        System.out.println("\n// ATV5");
        List<Produto> produtos = criarProdutos();
        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.printf("Valor total em estoque: R$ %.2f%n", total);
    }


    private static void atv6() {
        System.out.println("\n// ATV6");
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        System.out.println("Antes de ordenar: " + linguagens);
        List<String> ordenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("Ordenadas por tamanho: " + ordenadas);
    }

    // Metodo auxiliar ATV4 e ATV5
    private static List<Produto> criarProdutos() {
        return Arrays.asList(
                new Produto("Mouse", 80.0),
                new Produto("Teclado", 150.0),
                new Produto("Monitor", 900.0),
                new Produto("Headset", 120.0)
        );
    }

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

        @Override
        public String toString() {
            return nome + " - R$ " + preco;
        }
    }
}