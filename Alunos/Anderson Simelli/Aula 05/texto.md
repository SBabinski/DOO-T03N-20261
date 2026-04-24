# Paradigmas Imperativo e Declarativo

Os paradigmas de programação representam diferentes formas de pensar e estruturar a solução de problemas computacionais. Entre os principais paradigmas estudados, destacam-se o imperativo e o declarativo.

O paradigma imperativo é baseado na ideia de descrever passo a passo tudo o que o computador deve fazer para atingir determinado resultado. Nesse modelo, o programador controla diretamente variáveis, estruturas de repetição, condições e a sequência das instruções. Linguagens como Java seguem fortemente esse paradigma.

Já o paradigma declarativo funciona de maneira diferente. Em vez de informar cada etapa necessária para resolver um problema, o programador descreve apenas as regras, relações e condições que devem ser satisfeitas. Assim, o sistema é responsável por encontrar a solução. A linguagem Prolog é um exemplo clássico desse paradigma.

## Exemplo em Java

```java
public class Soma {
    public static void main(String[] args) {
        int numero1 = 10;
        int numero2 = 20;
        int resultado = numero1 + numero2;

        System.out.println("Resultado: " + resultado);
    }
}
```

Nesse exemplo em Java, o programador define explicitamente cada passo: cria as variáveis, realiza a soma e depois exibe o resultado. O computador apenas executa as instruções na ordem em que foram escritas.

## Exemplo em Prolog

```prolog
soma(X, Y, Resultado) :-
    Resultado is X + Y.
```

Para utilizar:

```prolog
?- soma(10, 20, Resultado).
```

Resultado esperado:

```prolog
Resultado = 30.
```

No caso do Prolog, o programador apenas declara a regra da soma. Não é necessário informar passo a passo como o cálculo será executado. Basta definir a relação entre os valores e o mecanismo da linguagem encontra a resposta.

## Comparação entre Java e Prolog

A principal diferença entre os dois paradigmas está na forma como o problema é resolvido. No Java, o foco está em “como fazer”, exigindo que o programador detalhe cada ação. Já no Prolog, o foco está em “o que deve ser feito”, deixando a responsabilidade da busca pela solução para a linguagem.

O paradigma imperativo costuma ser mais utilizado em aplicações comerciais, sistemas desktop, aplicativos e jogos, pois oferece maior controle sobre o fluxo do programa. Já o paradigma declarativo é muito útil em sistemas especialistas, inteligência artificial e problemas que envolvem regras lógicas.

Dessa forma, ambos os paradigmas possuem vantagens e aplicações específicas. Entender as diferenças entre eles é importante para escolher a melhor abordagem de acordo com o tipo de problema que precisa ser resolvido.
