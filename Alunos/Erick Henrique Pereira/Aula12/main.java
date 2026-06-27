import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class main {
    public final static List<Produto> produtos = Arrays.asList(
            new Produto("Mouse Gamer", 150.00),
            new Produto("Teclado Simples", 80.00),
            new Produto("Monitor 24'", 850.00),
            new Produto("Mousepad", 45.00));

    public static void main(String[] args) {
        //atv01();
        //atv02();
        //atv03();
        //atv04();
        //atv05();
        atv06();

    }

    public static void atv06(){
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> ordenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println(ordenadas);
    }

    public static void atv05(){
        double soma = produtos.stream().mapToDouble(Produto::getPreco).sum();
        System.out.println(soma);
    }

    public static void atv04(){
        

        List<Produto> acimaDoValor = produtos.stream().filter(p -> p.getPreco() >100 ).collect(Collectors.toList());
        for (Produto prod : acimaDoValor) {
            System.out.println(prod.getNome());
        }

    }

    public static void atv03(){
        List<String> palavras = new ArrayList<>();
        palavras.add("se");
        palavras.add("talvez");
        palavras.add("hoje");
        palavras.add("sábado");
        palavras.add("se");
        palavras.add("quarta");
        palavras.add("sábado");

        Map<String, Long> resultado = palavras.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(resultado);
    }

    public static void atv02(){
        List<String> nomes = new ArrayList<>();
        nomes.add("roberto");
        nomes.add("josé");
        nomes.add("caio");
        nomes.add("vinicius");

        List<String> maiusculo = nomes.stream().map(String::toUpperCase).collect(Collectors.toList());
        for (String string : maiusculo) {
            System.out.printf("%s ",string);
        }
    }
    public static void atv01(){
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

        List<Integer> pares = numeros.stream().filter(n -> n%2 == 0).collect(Collectors.toList());
        for (Integer n : pares) {
            System.out.printf("%d ",n);
        }
    }
}
