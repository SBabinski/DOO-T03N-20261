# Comparando Paradigmas de Programação

>Paradigmas de programação são modelos de escrita de código, é um estilo de programação, uma metodologia, que podem ser aplicados a várias linguagens. 

O Paradigma de Programação não é uma linguagem em si mas sim a forma de se solucionar problemas usando determinado código, são um conjunto de regras necessária para implementação de um determinado programa.

## Paradigma Imperativo

Também chamado de *Procedural*, este paradigma tem o foco na execução ou da solução de um problema, este foco esta baseado em **como** ele deve feito, para isso as **ordens** são passadas para o computador na sequência em que devem ser executadas.

A principal vantagem desse paradigma é a sua eficiência, além de permitir uma modelagem tal qual o mundo real, e de ser estabelecido e flexível. Sua desvantagem consiste no fato de que o código-fonte gerado é de difícil legibilidade.

É comum em linguagens como: *C, Basic, COBOL, Lua e PHP*.

## Paradigma Declarativo

O *Paradigma Declarativo* é o contrário do Paradigma Interativo, pois tem foco ***no que*** deve ser resolvido e ***não como*** isso será feito.

>Ao se utilizar este paradigma são declaradas verdades lógicas imútaveis para aas quais os resultados serão sempre os mesmos após suas interações

Exemplos comuns: *SQL, CSS, HTML e Prolog*.

### Imagem de Comparação 

![Paradigmas](https://sawastudio.me/blog/wp-content/uploads/2022/12/Group-80-1-1.png)


## Paradigma Orientado a Objetos

O Paradigma Orientado a Objetos é muito usado devido aos seus benefícios, como a *modularidade* do código e a capacidade de associar diretamente problemas reais em termos de código. Este paradigma é útil no desenvolvimento de aplicações web, e foi o primeiro a permitir a programação multiplataforma.

## Comparação de Códigos

### Java:

~~~ java

import java.util.Scanner;

public class Perimetro{
    public static Scanner scan = new Scanner(System.in);
    public static double base = 0;
    public static double altura = 0;


    public static void main(String[ ] args){
        System.out.println("Bem vindo ao Calculador de perimetro de um retângulo ");
        System.out.println("Entre com o valor da base do retângulo: ");
        base = scan.nextDouble();
        System.out.println("Entre com o valor da altura do retângulo: ");
        altura = scan.nextDouble();

        double resultado = 0;

        resultado = (base * 2) + (altura * 2);

        System.out.println("Valor do perimetro: "+resultado);
    }
}
~~~

### Explicação: 

Esse simples código em java tem o objetivo de calcular os perimetros de um retângulo, solicitando ao usuário a **inserção de dois valores**, sendo esses valores atribuidos aos lados de base e altura do retângulo. Após isso realiza o cálculo do resultado e apresenta ao usúario. Ou seja está utilizando o ***Paradigama Imperativo***, dando ordens de funções ao computador.

### Prolog

~~~ prolog
perimetro(Base, Altura, Resultado) :-
    Resultado is (Base * 2) + (Altura * 2).

:- initialization(main).

main :-
    write('Digite a base (com ponto, ex: 10.): '), nl,
    read(Base),

    write('Digite a altura (com ponto, ex: 5.): '), nl,
    read(Altura),

    perimetro(Base, Altura, Resultado),

    write('Perimetro: '), write(Resultado), nl.
~~~

### Explicação: 

Esse **simples** código em Prolog, faz basicamente o mesmo que o código em Java, porém com um Paradigma diferente, nesse caso utilizamos o ***Paradigma Declarativo***, onde passamos para o computador as **verdades**, ou seja **no que** deve-se fazer. Passamos as variáveis e **o que** o sistema deve fazer com elas, depois solicita para o usúario que insira os valores e após isso o sistema calcula e printa o resultado.