import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        
        // ATV 01

        List<Integer> numeros = Arrays.asList(5, 3, 4, 9, 7, 5, 6, 9);
		List<Integer> pares = numeros.stream()
                .filter(n -> n%2 == 0)
                .collect(Collectors.toList());


        System.out.println("\n------------------");
        System.out.println("\nLista de numeros: " + numeros);
        System.out.println("*   *   *");
		System.out.println("Lista de números pares: " + pares);

        // ATV 02

		List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
		List<String> maiusculos = nomes.stream()
				//.map(String::toUpperCase)
				.map(nome-> nome.toUpperCase())
				.collect(Collectors.toList());
		
        System.out.println("\n------------------");
		System.out.println("\nLista de nomes: " + nomes);
        System.out.println("*   *   *");
		System.out.println("Lista em maiusculo: " + maiusculos);				

        // ATV 03

		List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
		Map<String, Long> repeticoes = palavras.stream()
				.collect(Collectors.groupingBy
						(palavra -> palavra, Collectors.counting()));
		
        System.out.println("\n------------------");
		System.out.println("Lista de palavras: "+ palavras);
        System.out.println("*   *   *");
		System.out.println("Lista de palavras e vezes repetidas: " + repeticoes);

        // ATV 04

        List<Produto> produtos = Arrays.asList(
                new Produto("mini xadrez", 50),
                new Produto("lego carro do batman", 110),
                new Produto("carrinho de controle remoto", 200), 
                new Produto("pelucia ursinhos carinhosos", 58));
		
		List<Produto> precos = produtos.stream()
            .filter(prod-> prod.getPreco() > 100)
            .collect(Collectors.toList());
		
        System.out.println("\n------------------");
		System.out.println("\nLista de produtos: ");
		produtos.forEach(p -> System.out.println(p.getNome() + ": " + p.getPreco()));
        System.out.println("*   *   *");
		System.out.println("Produtos acima de 100: ");
		precos.forEach(p -> System.out.println(p.getNome() + ": " + p.getPreco()));        

        // ATV 05

		double soma = produtos.stream()
				.mapToDouble(p-> p.getPreco())
				.sum();
		
        
        System.out.println("\n------------------");
		System.out.printf("\nSoma do preço dos produtos: %.2f\n", soma);
				

        // ATV 06

		List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
		List<String> ordenado = linguagens.stream()
				.sorted(Comparator.comparingInt(linguagem-> linguagem.length()))
				.collect(Collectors.toList());
		
        System.out.println("\n------------------");
		System.out.println("\nLista original: " + linguagens);
        System.out.println("*   *   *");
		System.out.println("Lista ordenada por tamanho: " + ordenado);

    }
}
