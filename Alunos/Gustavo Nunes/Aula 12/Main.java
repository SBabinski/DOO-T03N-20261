import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("\n----------------------------------------");
        System.out.println("Aula 12 - Lista de exercícios StreamAPI");
        System.out.println("----------------------------------------");
        
        atv1();
        atv2();
        atv3();
        atv4();
        atv5();
        atv6();
        
        System.out.println("\n\n========================================");

    }
    
    /// ATV1
    private static void atv1() {
        
        System.out.println("\nATV1 ===================================\n");
        
        List<Integer> inteiros = List
                .of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        
        System.out.println("Antes:");
        
        inteiros.forEach(n ->
                System.out.printf("%d ",n));
        
        List<Integer> pares = inteiros.stream()
                .filter(P -> P % 2 == 0)
                .toList();
        
        System.out.println("\n\nDepois:");
        
        pares.forEach(n ->
                System.out.printf("%d ",n));
        
    }
    
    /// ATV2
    private static void atv2() {
        
        System.out.println("\n\nATV2 ===================================\n");
        
        List<String> minusculos = List.of(
                "roberto", "josé", "caio", "vinicius");
        
        System.out.println("Antes:");
        
        minusculos.forEach(n -> System.out.printf(
                "%s ", n));
        
        List<String> maiusculos = minusculos.stream()
                .map(String::toUpperCase)
                .toList();
        
        System.out.println("\n\nDepois:");
        
        maiusculos.forEach(n -> System.out.printf(
                "%s ", n));
        
    }
    
    /// ATV3
    private static void atv3() {
        
        System.out.println("\n\nATV3 ===================================\n");
        
        List<String> palavras = List.of(
                "se", "talvez", "hoje", "sábado",
                "se", "quarta", "sábado");
        
        palavras.forEach(n -> System.out.printf(
                "%s, ", n));
        
        System.out.println();
        
        List<String> palavrasDiferentes = palavras.stream()
                .distinct()
                .toList();
        
        palavrasDiferentes.forEach(n ->
                System.out.printf("\n%s - Aparece %d vezes",
                        n,
                        palavras.stream()
                                .filter(p -> p.equals(n))
                                .count()));
    }
    
    /// ATV4
    private static void atv4() {
        
        System.out.println("\n\nATV4 ===================================\n");
        
        List<Produto> produtos = List.of(
                new Produto("mochila",120),
                new Produto("caderno", 20),
                new Produto("caneta",3),
                new Produto("notebook", 2000));
        
        System.out.println("Produtos:");
        
        produtos.forEach(System.out::println);
        
        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100)
                .toList();
        
        System.out.println("\nProdutos com preço maior que R$ 100,00:");
        
        produtosCaros.forEach(System.out::println);
    }
    
    /// ATV5
    private static void atv5() {
        
        System.out.println("\nATV5 ===================================\n");
        
        List<Produto> produtos = List.of(
                new Produto("mochila",120),
                new Produto("caderno", 20),
                new Produto("caneta",3),
                new Produto("notebook", 2000));
        
        System.out.println("Produtos:");
        
        produtos.forEach(System.out::println);
        
        System.out.printf("\nTotal: %.2f",
                produtos.stream()
                        .mapToDouble(Produto::getPreco)
                        .sum());
    }
    
    /// ATV6
    private static void atv6() {
        
        System.out.println("\n\nATV6 ===================================");
        
        System.out.println("\nLista desordenada:");
        
        List<String> linguagens = List.of(
                "Java", "Python", "C", "JavaScript", "Ruby");
        
        linguagens.forEach(s ->
                System.out.printf("%s ", s));
        
        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList();
        
        System.out.println("\n\nLista Ordenada:");
        
        linguagensOrdenadas.forEach(s ->
                System.out.printf("%s ", s));
        
    }
        
        
    
        
}