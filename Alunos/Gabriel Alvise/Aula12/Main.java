import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //ATV1
        System.out.println("\nATV 1");
        Scanner sc = new Scanner(System.in);
        List<Integer> listNumeros = new ArrayList<>();

        System.out.println("Digite os números:");
        for (int i=0; i < 8; i++) {
            int numero = sc.nextInt();
            listNumeros.add(numero);
        }

        List<Integer> numerosPares = listNumeros.stream().filter(numero -> numero % 2 == 0).toList();

        System.out.println("Números Pares: ");
        for (int i=0; i<numerosPares.size(); i++) {
            System.out.println(numerosPares.get(i));
        }


        //ATV2
        System.out.println("\nATV 2");
        List<String> nomes = List.of("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream().map(nome -> nome.toUpperCase()).toList();

        System.out.println("Lista Formatada:");

        for (int i=0; i<nomesMaiusculos.size(); i++) {
            System.out.println(nomesMaiusculos.get(i));
        }


        //ATV3
        System.out.println("\nATV 3");
        List<String> palavras = List.of("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> quantidadePalavras = palavras.stream().collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println(quantidadePalavras);


        //ATV4
        System.out.println("\nATV 4");

        List<Produto> produtos = List.of(
                new Produto("Mouse", 80),
                new Produto("Teclado", 150),
                new Produto("Monitor", 900),
                new Produto("Fone", 120)
        );

        List<Produto> produtosCaros = produtos.stream()
                .filter(produto -> produto.getPreco() > 100)
                .toList();

        produtosCaros.forEach(produto -> System.out.println(produto.getNome() + " - R$ " + produto.getPreco()));


        //ATV5
        System.out.println("\nATV 5");
        double valorTotal = produtos.stream()
                .mapToDouble(produto -> produto.getPreco())
                .sum();

        System.out.printf("Soma total produtos ATV4: %.2f\n", valorTotal);


        //ATV6
        System.out.println("\nATV 6");
        List<String> linguagens = List.of("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream().sorted(Comparator.comparingInt(palavra -> palavra.length())).toList();

        System.out.println(linguagensOrdenadas);

    }

    static class Produto {
        private String nome;
        private float preco;

        public Produto(String nome, float preco){
            this.nome = nome;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public float getPreco() {
            return preco;
        }
    }
}
