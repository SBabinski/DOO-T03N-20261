
import java.util.*;
import java.util.stream.Collectors;



public class Listas {
    public static void main(String[] args) throws Exception {
        
        //atividade 1
        System.out.println("atividade 1");

        List<Integer> numeros = Arrays.asList(2,3,4,6,8,11,5,7);

        List<Integer> numerosPares = numeros.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());

        System.out.println("Números pares: " + numerosPares);



        //atividade 2

        System.out.println("atividade 2");

        List<String> nomes = Arrays.asList("roberto", "jose", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        System.out.println("Nomes em maiúsculo: " + nomesMaiusculos);


        //atividade 3

        System.out.println("atividade 3");

        List<String> palavras = Arrays.asList(
            "se", "talvez", "hoje", "sabado",
            "se", "quarta", "sabado"
        );
        Map<String, Long> contagemPalavras = palavras.stream()
            .collect(Collectors.groupingBy(
                palavra -> palavra, Collectors.counting()
            ));
        System.out.println("Contagem de palavras");
            contagemPalavras.forEach((palavra, contagem) -> 
                System.out.println(palavra + ": " + contagem)
            );

            //atividade 4
            System.out.println("atividade 4");

            List<Produto> produtos = Arrays.asList(
                new Produto("Monitor", 3000.0),
                new Produto("Mouse", 30.0),
                new Produto("Teclado", 100.0),
                new Produto("Fone de ouvido", 150.0));

            List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getPreco() > 100.0)
                .collect(Collectors.toList());

                System.out.println("Produtos com preço maior que 100.0:");
                produtosFiltrados.forEach(System.out::println);



            //atividade 5

            System.out.println("atividade 5");

            double somaTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

                System.out.println("Soma total dos preços dos produtos: " + somaTotal);

            
            //atividade 6
            System.out.println("atividade 6");
            List<String> linguagens = Arrays.asList(
                "Java", "Python", "C", "JavaScript",
                "Ruby"
            );
            List<String> LinguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

                System.out.println("Linguagens ordenadas por tamanho:");
                System.out.println(LinguagensOrdenadas);



        }

        public static class Produto {

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
            return nome + " - R$" + preco;
        }
}}
