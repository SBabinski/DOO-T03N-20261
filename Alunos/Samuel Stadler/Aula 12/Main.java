import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(3, 8, 15, 22, 9, 14, 7, 20, 11, 4);
        List<Integer> numerosPares = numeros.stream()
                .filter(numero -> numero % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("ATV1 - Números pares: " + numerosPares);
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(nome -> nome.toUpperCase())
                .collect(Collectors.toList());

        System.out.println("ATV2 - Nomes em maiúsculo: " + nomesMaiusculos);
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));
        System.out.println("ATV3 - Contagem de palavras: " + contagemPalavras);
        List<Produto> produtos = Arrays.asList(
                new Produto("Mouse", 50.00),
                new Produto("Teclado", 120.00),
                new Produto("Monitor", 800.00),
                new Produto("Cabo USB", 25.00)
        );
        List<Produto> produtosCaros = produtos.stream()
                .filter(produto -> produto.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("ATV4 - Produtos com preço maior que R$100,00:");
        for (Produto produto : produtosCaros) {
            System.out.println(produto);
        }
        double somaTotal = produtos.stream()
                .mapToDouble(produto -> produto.getPreco())
                .sum();

        System.out.println("ATV5 - Soma total dos produtos: R$" + somaTotal);
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted((palavra1, palavra2) -> palavra1.length() - palavra2.length())
                .collect(Collectors.toList());

        System.out.println("ATV6 - Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
    }
}
