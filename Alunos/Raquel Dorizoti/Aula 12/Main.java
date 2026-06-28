import java.util.*;
import java.util.stream.Collectors;

/*Atividade 01
public class Main{
    public static void main(String[]args){
        List<Integer> numeros = Arrays.asList(1,2,3,4,5,6,7,8);
        List<Integer>pares = numeros.stream()
                .filter(n -> n%2 ==0)
                .toList();
        System.out.println(pares);
    }
}*/

/*Atividade 02
public class Main{
    public static void main(String[] args){
        List<String>nomes = Arrays.asList("roberto" , "josé" , "caio" , "vinicius");
        List<String>nomeCaixasaltas = nomes.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println(nomeCaixasaltas);
    }
}*/

/*Atividade 03
public class Main {
    public static void main (String [] args){
        List<String>repeticao = Arrays.asList("se", "talvez", "hoje" "sábado", "se", "quarta", "sábado");
        Map<String , Long> contagem = repeticao.stream()
                .collect(Collectors.groupingBy(repeticaos -> repeticaos , Collectors.counting()));
        System.out.println(contagem);
    }
}*/

/*Atividade 04
public class Main {
    public static void main(String[] args){
        List<Produto> produtos = new ArrayList<>();
    produtos.add(new Produto("monitor",90));
    produtos.add(new Produto("teclado",50));
    produtos.add(new Produto("gabinete e peças",2000));

    produtos.stream()
            .filter(produto -> produto.getPreco()>100)
            .forEach(System.out::println);

    System.out.println("Produto acima de R$1000,00");
    }
}*/

/*Atividade 05
public class Main{
    public static void main(String[] args){
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("monitor",90));
        produtos.add(new Produto("teclado",50));
        produtos.add(new Produto("gabinete e peças",2000));

        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("valor total dos produtos"+ total);
    }
}

/*Atividade 06
public class Main {
    public static void main(String[] args){
        List<String>linguagem = Arrays.asList("Java" , "Ruby", "C" , "Python");
        linguagem.stream()
                .sorted(Comparator.comparing(String::length))
                .forEach(System.out::println);
    }
}*/