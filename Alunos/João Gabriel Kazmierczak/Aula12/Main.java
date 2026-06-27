package fag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static final List<Produto> produtos = Arrays.asList(
            new Produto("Mouse Gamer", 150.00),
            new Produto("Teclado Simples", 80.00),
            new Produto("Monitor 24'", 850.00),
            new Produto("Mousepad", 45.00)
    );

    public static void main(String[] args) {

        System.out.println("===== ATV1 =====");
        atv01();

        System.out.println("\n===== ATV2 =====");
        atv02();

        System.out.println("\n===== ATV3 =====");
        atv03();

        System.out.println("\n===== ATV4 =====");
        atv04();

        System.out.println("\n===== ATV5 =====");
        atv05();

        System.out.println("\n===== ATV6 =====");
        atv06();
    }

    // ATV1
    public static void atv01() {

        List<Integer> numeros = new ArrayList<>();

        numeros.add(2);
        numeros.add(13);
        numeros.add(153);
        numeros.add(11);
        numeros.add(12);
        numeros.add(7);
        numeros.add(3);
        numeros.add(6);
        numeros.add(4);
        numeros.add(2);

        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Números pares: " + pares);
    }

    // ATV2
    public static void atv02() {

        List<String> nomes = Arrays.asList(
                "roberto",
                "josé",
                "caio",
                "vinicius"
        );

        List<String> maiusculo = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Nomes em maiúsculo: " + maiusculo);
    }

    // ATV3
    public static void atv03() {

        List<String> palavras = Arrays.asList(
                "se",
                "talvez",
                "hoje",
                "sábado",
                "se",
                "quarta",
                "sábado"
        );

        Map<String, Long> resultado = palavras.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        System.out.println("Contagem das palavras: " + resultado);
    }

    // ATV4
    public static void atv04() {

        List<Produto> acimaDoValor = produtos.stream()
                .filter(p -> p.getPreco() > 100)
                .collect(Collectors.toList());

        System.out.println("Produtos acima de R$100,00:");

        for (Produto prod : acimaDoValor) {
            System.out.println(prod.getNome() + " - R$ " + prod.getPreco());
        }
    }

    // ATV5
    public static void atv05() {

        double soma = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("Soma dos produtos: R$ " + soma);
    }

    // ATV6
    public static void atv06() {

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

        System.out.println("Ordenadas por tamanho: " + ordenadas);
    }
}