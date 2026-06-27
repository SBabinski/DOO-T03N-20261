import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // ATV1
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("ATV1:");
        System.out.println(pares);
        // ATV2
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("\nATV2:");
        System.out.println(nomesMaiusculos);
        // ATV3
        List<String> palavras = Arrays.asList(
                "se", "talvez", "hoje", "sábado",
                "se", "quarta", "sábado"
        );
        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(
                        palavra -> palavra,
                        Collectors.counting()
                ));
        System.out.println("\nATV3:");
        System.out.println(contagem);
        // ATV4
        List<Produto> produtos = Arrays.asList(
                new Produto("Mouse", 80.0),
                new Produto("Teclado", 120.0),
                new Produto("Monitor", 900.0),
                new Produto("Headset", 150.0)
        );
        List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getPreco() > 100)
                .collect(Collectors.toList());
        System.out.println("\nATV4:");
        produtosFiltrados.forEach(System.out::println);
        // ATV5
        double soma = produtos.stream()
                .map(Produto::getPreco)
                .reduce(0.0, Double::sum);
        System.out.println("\nATV5:");
        System.out.println("Total = R$ " + soma);
        // ATV6
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
        System.out.println("\nATV6:");
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
