# Paradigmas Imperativo e Declarativo

Os paradigmas de programacao representam diferentes formas de pensar e estruturar a solucao de um problema computacional. Entre os paradigmas apresentados, destacam-se o **imperativo** e o **declarativo**, que possuem diferencas importantes na maneira como descrevem o comportamento de um programa.

O **paradigma imperativo** e baseado na ideia de informar ao computador, passo a passo, o que deve ser feito para alcancar determinado resultado. Nesse modelo, o programador controla diretamente o fluxo de execucao, utilizando variaveis, comandos de repeticao, estruturas condicionais e alteracoes de estado. Linguagens como **Java**, C e Python sao exemplos comuns de linguagens que permitem esse estilo de programacao.

Um exemplo simples em Java seria:

```java
public class Main {
    public static void main(String[] args) {
        int numero = 5;
        int fatorial = 1;

        for (int i = 1; i <= numero; i++) {
            fatorial = fatorial * i;
        }

        System.out.println("O fatorial de " + numero + " é " + fatorial);
    }
}