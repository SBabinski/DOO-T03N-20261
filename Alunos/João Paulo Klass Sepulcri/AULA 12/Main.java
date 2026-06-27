import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //ATV1
        List<Integer> numeros = Arrays.asList(10, 3, 7, 8, 12, 15, 20, 25);

        List<Integer> numerosPares = numeros.stream()
                .filter(numero -> numero % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("//ATV1");
        System.out.println("Números pares: " + numerosPares);


        //ATV2
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("\n//ATV2");
        System.out.println("Nomes em maiúsculo: " + nomesMaiusculos);


        //ATV3
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(
                        palavra -> palavra,
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        System.out.println("\n//ATV3");
        System.out.println("Contagem de palavras: " + contagemPalavras);


        //ATV4
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 120.00),
                new Produto("Mouse", 80.00),
                new Produto("Monitor", 900.00),
                new Produto("Cabo USB", 35.00)
        );

        List<Produto> produtosAcimaDeCem = produtos.stream()
                .filter(produto -> produto.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("\n//ATV4");
        System.out.println("Produtos com preço maior que R$ 100,00: " + produtosAcimaDeCem);


        //ATV5
        double valorTotalProdutos = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("\n//ATV5");
        System.out.printf("Valor total dos produtos: R$ %.2f%n", valorTotalProdutos);


        //ATV6
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("\n//ATV6");
        System.out.println("Linguagens ordenadas pelo tamanho: " + linguagensOrdenadas);
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
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}