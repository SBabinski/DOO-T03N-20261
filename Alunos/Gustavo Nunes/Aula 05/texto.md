### **Paradigmas de Programação**



#### **Introdução**



Durante a aula foi apresentado que um paradigma de programação representa a forma como um problema é resolvido utilizando código. Ou seja, não é uma linguagem específica, mas sim um estilo de pensamento usado pelo programador para desenvolver uma solução.



Entre os paradigmas estudados, os dois principais foram o imperativo e o declarativo, que possuem formas bem diferentes de funcionamento.



#### **Paradigma Imperativo**



O paradigma imperativo é baseado em instruções passo a passo. Nesse modelo, o programador precisa dizer exatamente como o computador deve resolver o problema



* declarar variáveis
* pedir dados ao usuário
* fazer cálculos
* mostrar o resultado



Linguagens como Java utilizam muito esse tipo de paradigma



#### **Paradigma Declarativo**



O paradigma declarativo funciona de forma diferente. Em vez de dizer como resolver o problema, o programador descreve o que precisa ser resolvido



Nesse modelo, o foco não está na sequência de passos, mas sim na lógica do problema. A linguagem se encarrega de encontrar a solução com base nas regras definidas



Um exemplo de linguagem declarativa é o Prolog



#### **Código em Java (Paradigma Imperativo):**



```java

import java.util.Scanner;

public class DobroNumero {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Digite um número:");
        int numero = scan.nextInt();

        int resultado = numero * 2;

        System.out.println("O dobro é: " + resultado);
    }
}

```



diz exatamente como o problema deve ser resolvido:



1. cria uma variável para guardar o número
2. pede o valor ao usuário
3. faz o cálculo
4. mostra o resultado



#### **Código em Prolog (Paradigma Declarativo):**



```prolog

dobro(Numero, Resultado) :-
    Resultado is Numero * 2.

:- initialization(main).

main :-
    write('Digite um número: '),
    read(Numero),

    dobro(Numero, Resultado),

    write('O dobro é: '),
    write(Resultado), nl.

```



o foco está em definir a regra, e não em explicar passo a passo como o cálculo deve ser feito:



* o dobro de um número é igual ao número multiplicado por 2, o programa apenas utiliza essa regra quando o usuário informa um valor



#### **Conclusão**



Com base nos exemplos apresentados, é possível perceber que os dois paradigmas resolvem o mesmo problema, porém de maneiras diferentes.



O paradigma imperativo trabalha com comandos e etapas bem definidas, sendo mais comum em linguagens como Java. Já o paradigma declarativo trabalha com regras lógicas e foco no resultado, sendo utilizado em linguagens como Prolog.



Mesmo sendo diferentes, ambos são importantes, pois cada paradigma pode ser mais eficiente dependendo do tipo de problema que precisa ser resolvido.

