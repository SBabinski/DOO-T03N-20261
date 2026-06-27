import java.util.*;
import java.util.stream.*;

public class Main {

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
        List<Integer> numeros = Arrays.asList(3, 8, 15, 22, 7, 14, 9, 30, 41, 6);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("ATV1 - Números pares: " + pares);

        //ATV2
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("ATV2 - Nomes em maiúsculo: " + nomesMaiusculos);

        //ATV3
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        System.out.println("ATV3 - Contagem de palavras: " + contagem);

        //ATV4
        List<Produto> produtos = Arrays.asList(
                new Produto("Notebook", 2500.00),
                new Produto("Caneta", 5.90),
                new Produto("Mouse", 150.00),
                new Produto("Caderno", 35.00)
        );
        List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());
        System.out.println("ATV4 - Produtos acima de R$ 100,00: " + produtosFiltrados);

        //ATV5
        double somaTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.printf("ATV5 - Soma total dos produtos: R$ %.2f%n", somaTotal);

        //ATV6
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("ATV6 - Linguagens ordenadas por tamanho: " + ordenadas);
    }
}