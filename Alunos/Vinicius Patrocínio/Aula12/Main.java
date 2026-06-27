import java.util.*;
import java.util.stream.Collectors;

/*Atv 01
public class Main{
    public static void main(String[]args){
        List<Integer> numeros = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        List<Integer>pares = numeros.stream()
                .filter(n -> n%2 ==0)
                .toList();
        System.out.println(pares);
    }
} */

/*Atv 02
public class Main{
    public static void main(String[] args){
        List<String>nomes = Arrays.asList("roberto" , "caio" , "josé" , "vinicius");
        List<String>nomeCaixasaltas = nomes.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println(nomeCaixasaltas);
    }
}*/

/*Atv 03
public class Main {
    public static void main (String [] args){
        List<String>repeticao = Arrays.asList("se" , "talvez" , "hoje" , "sábado" , "se" , "quarta" , "sábado");
        Map<String , Long> contagem = repeticao.stream()
                .collect(Collectors.groupingBy(repeticaos -> repeticaos , Collectors.counting()));
        System.out.println(contagem);
    }
}*/

/*Atv 04
public class Main {
    public static void main(String[] args){
        List<Produto> produtos = new ArrayList<>();
    produtos.add(new Produto("monitor",90));
    produtos.add(new Produto("teclado",50));
    produtos.add(new Produto("gabinete e peças",2000));

    produtos.stream()
            .filter(produto -> produto.getPreco()>100)
            .forEach(System.out::println);

    System.out.println("Produto acima de R$100,00");
    }
}*/

/*Atv 05
public class Main{
    public static void main(String[] args){
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("moletom",180));
        produtos.add(new Produto("calça",150));
        produtos.add(new Produto("tênis",350);

        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("valor total dos produtos"+ total);
    }
}*/

/*Atv 06
public class Main {
    public static void main(String[] args){
        List<String>linguagem = Arrays.asList("Java" , "JavaScript", "C" , "Python");
        linguagem.stream()
                .sorted(Comparator.comparing(String::length))
                .forEach(System.out::println);
    }
}*/

/*Classe Produto
public class Produto {
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

    public String toString() {
        return nome + " - R$" + preco;
    }
}*/