>olá , hoje aqui no progama iremos analisar 2 codigos. O primeiro codigo no imperativo (java)    
>ele vai fazer a parte de controlar passo a passo , por isso segue a ordem colocada de comandos

    import java.util.Scanner;
    public class Idades {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite sua idade: ");
        int idade = scanner.nextInt();

        if (idade >= 18) {
            System.out.println("Você é maior de idade.");
        } else {
            System.out.println("Você é menor de idade.");
        }

        // Classificação extra
        if (idade < 12) {
            System.out.println("Criança");
        } else if (idade < 18) {
            System.out.println("Adolescente");
        } else if (idade < 60) {
            System.out.println("Adulto");
        } else {
            System.out.println("Idoso");
        }

        scanner.close();
    }
    }


------------------------------------------------------------------------------------------------
>>O Prolog é uma linguagem de programação baseada em lógica, diferente de linguagens tradicionais como Java. 
>> Em vez de escrever passo a passo o que o programa deve fazer, no Prolog o programador define fatos e regras, e o próprio sistema encontra as respostas por meio de consultas. 
>> Ele utiliza um processo chamado Backtracking, que tenta diferentes possibilidades até encontrar uma solução. Essa abordagem torna o 
>> Prolog muito útil em áreas como inteligência artificial e resolução de problemas lógicos.

    % Fato de exemplo
    idade(joao, 20).
    idade(maria, 15).
    idade(carlos, 65).

    % Regra: maior de idade
    maior_de_idade(Pessoa) :-
    idade(Pessoa, Idade),
    Idade >= 18.

    % Classificação
    classificacao(Pessoa, crianca) :-
    idade(Pessoa, Idade),
    Idade < 12.

    classificacao(Pessoa, adolescente) :-
    idade(Pessoa, Idade),
    Idade >= 12,
    Idade < 18.

    classificacao(Pessoa, adulto) :-
    idade(Pessoa, Idade),
    Idade >= 18,
    Idade < 60.

    classificacao(Pessoa, idoso) :-
    idade(Pessoa, Idade),
    Idade >= 60.

    ?- maior_de_idade(joao).
    true.

    ?- classificacao(maria, X).
    X = adolescente.