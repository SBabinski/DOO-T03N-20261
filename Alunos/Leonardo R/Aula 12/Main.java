import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //atividade 1
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> numerosPares = numeros.stream()
                .filter(numero -> numero % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("atividade 1 - N\u00fameros pares: " + numerosPares);

        //atividade 2
        List<String> nomes = Arrays.asList("roberto", "jos\u00e9", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(nome -> nome.toUpperCase(Locale.ROOT))
                .collect(Collectors.toList());

        System.out.println("atividade 2 - Nomes em mai\u00fasculo: " + nomesMaiusculos);

        //atividade 3
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "s\u00e1bado", "se", "quarta", "s\u00e1bado");

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println("atividade 3 - Contagem de palavras: " + contagemPalavras);

        //atividade 4
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 80.00),
                new Produto("Mouse", 120.00),
                new Produto("Monitor", 900.00),
                new Produto("Cabo HDMI", 45.00)
        );

        List<Produto> produtosAcimaDeCem = produtos.stream()
                .filter(produto -> produto.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("atividade 4 - Produtos acima de R$ 100,00: " + produtosAcimaDeCem);

        //atividade 5
        double valorTotalProdutos = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.printf("atividade 5 - Valor total dos produtos: R$ %.2f%n", valorTotalProdutos);

        //atividade 6
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("atividade 6 - Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
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
