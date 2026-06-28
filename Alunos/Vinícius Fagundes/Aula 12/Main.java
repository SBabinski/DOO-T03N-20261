import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
        public static void main(String[] args) {

                // ATIVIDADE 1
                System.out.println("ATIVIDADE 1");
                List<Integer> numeros = Arrays.asList(10, 7, 15, 22, 8, 31, 44, 9);

                List<Integer> pares = numeros.stream()
                                .filter(n -> n % 2 == 0)
                                .collect(Collectors.toList());

                System.out.println("Números pares: " + pares);

                // ATIVIDADE 2
                System.out.println("\nATIVIDADE 2");
                List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

                List<String> nomesMaiusculos = nomes.stream()
                                .map(String::toUpperCase)
                                .collect(Collectors.toList());

                System.out.println("Nomes em maiúsculo: " + nomesMaiusculos);

                // ATIVIDADE 3
                System.out.println("\nATIVIDADE 3");
                List<String> palavras = Arrays.asList(
                                "se", "talvez", "hoje", "sábado",
                                "se", "quarta", "sábado");

                Map<String, Long> contagemPalavras = palavras.stream()
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

                System.out.println("Contagem de palavras:");
                contagemPalavras.forEach((palavra, quantidade) -> System.out.println(palavra + " = " + quantidade));

                // ATIVIDADE 4
                System.out.println("\nATIVIDADE 4");
                List<Produto> produtos = Arrays.asList(
                                new Produto("Mouse", 80.00),
                                new Produto("Teclado", 120.00),
                                new Produto("Monitor", 900.00),
                                new Produto("Headset", 150.00));

                List<Produto> produtosFiltrados = produtos.stream()
                                .filter(p -> p.getPreco() > 100.00)
                                .collect(Collectors.toList());

                System.out.println("Produtos com preço maior que R$100:");
                produtosFiltrados.forEach(System.out::println);

                // ATIVIDADE 5
                System.out.println("\nATIVIDADE 5");

                double somaTotal = produtos.stream()
                                .mapToDouble(Produto::getPreco)
                                .sum();

                System.out.println("Soma total dos produtos: R$ " + somaTotal);

                // ATIVIDADE 6
                System.out.println("\nATIVIDADE 6");

                List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

                List<String> ordenadas = linguagens.stream()
                                .sorted(Comparator.comparingInt(String::length))
                                .collect(Collectors.toList());

                System.out.println("Ordenadas por tamanho:");
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
