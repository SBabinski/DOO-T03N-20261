import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        atividade1();
        atividade2();
        atividade3();
        atividade4();
        atividade5();
        atividade6();
    }

    //ATV1
    public static void atividade1() {
        List<Integer> valores = new ArrayList<>();
        valores.add(4);
        valores.add(9);
        valores.add(17);
        valores.add(22);
        valores.add(31);
        valores.add(40);
        valores.add(56);
        valores.add(63);

        List<Integer> pares = valores.stream()
                .filter(valor -> valor % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Números pares: " + pares);
    }

    //ATV2
    public static void atividade2() {
        List<String> nomes = new ArrayList<>();
        nomes.add("roberto");
        nomes.add("josé");
        nomes.add("caio");
        nomes.add("vinicius");

        List<String> nomesEmCaixaAlta = nomes.stream()
                .map(nome -> nome.toUpperCase())
                .collect(Collectors.toList());

        System.out.println("Nomes em maiúsculas: " + nomesEmCaixaAlta);
    }

    //ATV3
    public static void atividade3() {
        List<String> palavras = new ArrayList<>();
        palavras.add("se");
        palavras.add("talvez");
        palavras.add("hoje");
        palavras.add("sábado");
        palavras.add("se");
        palavras.add("quarta");
        palavras.add("sábado");

        Map<String, Long> frequencia = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println("Frequência de cada palavra: " + frequencia);
    }

    //ATV4
    public static void atividade4() {
        List<Produto> catalogo = criarCatalogo();

        List<Produto> caros = catalogo.stream()
                .filter(produto -> produto.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("Produtos acima de R$100,00:");
        for (Produto produto : caros) {
            System.out.println(produto.getNome() + " - R$" + produto.getPreco());
        }
    }

    //ATV5
    public static void atividade5() {
        List<Produto> catalogo = criarCatalogo();

        double total = catalogo.stream()
                .mapToDouble(produto -> produto.getPreco())
                .sum();

        System.out.println("Soma total dos produtos: R$" + total);
    }

    //ATV6
    public static void atividade6() {
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> ordenadasPorTamanho = linguagens.stream()
                .sorted((l1, l2) -> l1.length() - l2.length())
                .collect(Collectors.toList());

        System.out.println("Linguagens ordenadas por tamanho: " + ordenadasPorTamanho);
    }

    private static List<Produto> criarCatalogo() {
        List<Produto> catalogo = new ArrayList<>();
        catalogo.add(new Produto("Notebook", 3500.00));
        catalogo.add(new Produto("Mouse", 50.00));
        catalogo.add(new Produto("Teclado", 120.00));
        catalogo.add(new Produto("Monitor", 899.90));
        return catalogo;
    }
}