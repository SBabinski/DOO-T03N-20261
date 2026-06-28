package fag;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
	
	public static void main(String[] args) {
	
		//ATV1
		
		List<Integer> numeros = Arrays.asList(5, 6, 7, 8, 10, 12, 13, 11, 1, 2);
		List<Integer> pares = numeros.stream()
				.filter(n -> n%2 == 0)
				.collect(Collectors.toList());
		
		System.out.println("\nLista de numeros: " + numeros);
		System.out.println("-----------");
		System.out.println("Lista de numeros ´pares: "+ pares);

		
		//ATV2
		List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
		List<String> maiusculos = nomes.stream()
				//.map(String::toUpperCase)
				.map(nome-> nome.toUpperCase())
				.collect(Collectors.toList());
		
		System.out.println("\n---------------------------------------------");
		System.out.println("\nLista de nomes: " + nomes);
		System.out.println("---------");
		System.out.println("Lista em maiusculo: " + maiusculos);
				
		
		//ATV3
		List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
		Map<String, Long> contagem = palavras.stream()
				.collect(Collectors.groupingBy
						(palavra -> palavra, Collectors.counting()));
		
		System.out.println("\n---------------------------------------------");
		System.out.println("\nLista de palavras: "+ palavras);
		System.out.println("---------");
		System.out.println("Lista de palavras e suas repeticoes: " + contagem);
		
		
		//ATV4
		List<Produto> produtos = Arrays.asList(
				new Produto("arroz", 25.00),
				new Produto("calca jeans", 200.50),
				new Produto("feijao", 20), 
				new Produto("vestido", 350));
		
		List<Produto> precos = produtos.stream()
				.filter(prod-> prod.getPreco() > 100)
				.collect(Collectors.toList());
		
		System.out.println("\n---------------------------------------------");
		System.out.println("\nLista de produtos: ");
		produtos.forEach(p -> System.out.println(p.getNome() + ": " + p.getPreco()));
		System.out.println("-------");
		System.out.println("Produtos acima de 100: ");
		precos.forEach(prod -> System.out.println(prod.getNome() + ": " + prod.getPreco()));
		
		
		//ATV5
		double soma = produtos.stream()
				.mapToDouble(p-> p.getPreco())
				.sum();
		
		System.out.println("\n---------------------------------------------");
		System.out.printf("\nA soma dos valores dos produtos e: %.2f\n", soma);
				
						
		//ATV6
		List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
		List<String> ordenado = linguagens.stream()
				.sorted(Comparator.comparingInt(linguagem-> linguagem.length()))
				.collect(Collectors.toList());
		
		System.out.println("\n---------------------------------------------");
		System.out.println("\nLista original: " + linguagens);
		System.out.println("--------");
		System.out.println("Lista ordenada por tamanho: " + ordenado);
		
		
		
	}

}
