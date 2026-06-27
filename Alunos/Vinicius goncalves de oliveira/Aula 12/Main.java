import java.util.*;
import java.util.stream.*;

public class Main {

    static class Produto {
        String nome;
        double preco;

        Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }
    }

    public static void main(String[] args) {

        //ATV1
        List<Integer> numeros = Arrays.asList(1, 4, 7, 8, 13, 20, 33, 42, 55, 60);
        List<Integer> pares = numeros.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
        System.out.println(pares);

        //ATV2
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> maiusculos = nomes.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(maiusculos);

        //ATV3
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        System.out.println(contagem);

        //ATV4
        List<Produto> produtos = Arrays.asList(
            new Produto("Teclado",  80.00),
            new Produto("Monitor", 850.00),
            new Produto("Mouse",    60.00),
            new Produto("Headset", 150.00)
        );
        List<Produto> caros = produtos.stream().filter(p -> p.preco > 100).collect(Collectors.toList());
        caros.forEach(p -> System.out.println(p.nome + " - R$ " + p.preco));

        //ATV5
        double total = produtos.stream().mapToDouble(p -> p.preco).sum();
        System.out.println(total);

        //ATV6
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadas = linguagens.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
        System.out.println(ordenadas);
    }
}
