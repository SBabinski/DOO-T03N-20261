import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        atv1();
        atv2();
        atv3();

        List<Produto> produtos = criarProdutos();
        atv4(produtos);
        atv5(produtos);

        atv6();
    }

    // ==================== ATV1 ====================
    // Recebe uma lista de inteiros e retorna apenas os números pares.
    private static void atv1() {
        System.out.println("===== ATV1 - Números pares =====");
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        try {
            validarLista(numeros, "A lista de números");

            List<Integer> pares = numeros.stream()
                    .filter(Objects::nonNull)        // ignora elementos nulos (evita NPE)
                    .filter(n -> n % 2 == 0)
                    .collect(Collectors.toList());

            System.out.println("Lista original: " + numeros);
            System.out.println("Números pares:  " + pares);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na ATV1: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== ATV2 ====================
    // Converte todos os nomes para letras maiúsculas.
    private static void atv2() {
        System.out.println("===== ATV2 - Nomes em maiúsculas =====");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        try {
            validarLista(nomes, "A lista de nomes");

            List<String> nomesMaiusculos = nomes.stream()
                    .filter(Objects::nonNull)
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());

            System.out.println("Lista original: " + nomes);
            System.out.println("Em maiúsculas:  " + nomesMaiusculos);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na ATV2: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== ATV3 ====================
    // Conta quantas vezes cada palavra única aparece na lista.
    private static void atv3() {
        System.out.println("===== ATV3 - Contagem de palavras =====");
        List<String> palavras = Arrays.asList(
                "se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        try {
            validarLista(palavras, "A lista de palavras");

            Map<String, Long> contagem = palavras.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(
                            palavra -> palavra,
                            Collectors.counting()));

            System.out.println("Contagem de cada palavra:");
            contagem.forEach((palavra, qtd) ->
                    System.out.println("  " + palavra + " = " + qtd));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na ATV3: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== ATV4 ====================
    // Filtra os produtos cujo preço seja maior que R$ 100,00.
    private static void atv4(List<Produto> produtos) {
        System.out.println("===== ATV4 - Produtos acima de R$ 100,00 =====");
        try {
            validarLista(produtos, "A lista de produtos");

            List<Produto> produtosCaros = produtos.stream()
                    .filter(p -> p.getPreco() > 100.00)
                    .collect(Collectors.toList());

            System.out.println("Produtos com preço maior que R$ 100,00:");
            produtosCaros.forEach(p ->
                    System.out.printf("  %s - R$ %.2f%n", p.getNome(), p.getPreco()));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na ATV4: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== ATV5 ====================
    // Soma o valor total de todos os produtos da lista (a mesma da ATV4).
    private static void atv5(List<Produto> produtos) {
        System.out.println("===== ATV5 - Soma total dos produtos =====");
        try {
            validarLista(produtos, "A lista de produtos");

            double total = produtos.stream()
                    .mapToDouble(Produto::getPreco)
                    .sum();

            System.out.printf("Valor total dos produtos: R$ %.2f%n", total);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na ATV5: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== ATV6 ====================
    // Ordena a lista pelo tamanho da palavra, da menor para a maior.
    private static void atv6() {
        System.out.println("===== ATV6 - Linguagens ordenadas por tamanho =====");
        List<String> linguagens = Arrays.asList(
                "Java", "Python", "C", "JavaScript", "Ruby");

        try {
            validarLista(linguagens, "A lista de linguagens");

            List<String> ordenadas = linguagens.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingInt(String::length))
                    .collect(Collectors.toList());

            System.out.println("Lista original:       " + linguagens);
            System.out.println("Ordenada por tamanho: " + ordenadas);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na ATV6: " + e.getMessage());
        }
        System.out.println();
    }


    private static List<Produto> criarProdutos() {
        try {
            return Arrays.asList(
                    new Produto("Teclado", 80.00),
                    new Produto("Monitor", 750.00),
                    new Produto("Mouse", 45.50),
                    new Produto("Cadeira Gamer", 1200.00));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar os produtos: " + e.getMessage());
            return List.of(); // devolve lista vazia para o programa não quebrar
        }
    }

    // Garante que a lista não seja nula nem vazia antes de ser processada.
    private static void validarLista(List<?> lista, String nome) {
        if (lista == null) {
            throw new IllegalArgumentException(nome + " não pode ser nula.");
        }
        if (lista.isEmpty()) {
            throw new IllegalArgumentException(nome + " não pode estar vazia.");
        }
    }
}

class Produto {
    private final String nome;
    private final double preco;

    public Produto(String nome, double preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException(
                    "O preço não pode ser negativo (recebido: " + preco + ").");
        }
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
