import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // ATV1
        List<Integer> numeros = Arrays.asList(10, 15, 22, 33, 40, 57, 68, 71);

        List<Integer> numerosPares = numeros.stream()
                .filter(numero -> numero % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("ATV1 - Números pares: " + numerosPares);


        // ATV2
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("ATV2 - Nomes em maiúsculo: " + nomesMaiusculos);


        // ATV3
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println("ATV3 - Contagem das palavras: " + contagemPalavras);


        // ATV4
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 80.00),
                new Produto("Mouse Gamer", 120.00),
                new Produto("Monitor", 850.00),
                new Produto("Cabo USB", 35.00)
        );

        List<Produto> produtosAcimaDeCem = produtos.stream()
                .filter(produto -> produto.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("ATV4 - Produtos acima de R$ 100,00: " + produtosAcimaDeCem);


        // ATV5
        double somaTotalProdutos = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("ATV5 - Soma total dos produtos: R$ " + somaTotalProdutos);


        // ATV6
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted((linguagem1, linguagem2) -> Integer.compare(linguagem1.length(), linguagem2.length()))
                .collect(Collectors.toList());

        System.out.println("ATV6 - Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
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