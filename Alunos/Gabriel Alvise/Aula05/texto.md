# Texto Sobre Paradigmas

### O texto abaixo tem por finalidade trazer um resumo sobre o que foi passado na aula de Desenv. Orientado a Objeto no dia 27/03 na qual o conteúdo foi sobre paradigmas da programação (imperativo e declarativo) e também uma comparação entre esses dois tipos.

Os paradigmas da programação definem como um código é escrito e organizado, cada paradigma estabelece um conjunto de princípios, conceitos e estilos que orientam o desenvolvimento de programas. Dentre os paradigmas, se destacam o **paradigma imperativo e o declarativo**.

---

## Paradigma Imperativo

Iniciando pelo **Paradigma Imperativo**, funciona através de comandos e instruções que indicam passo a passo o que o programa deve fazer. Ele utiliza variáveis, estruturas condicionais e laços de repetição para controlar o fluxo do programa.

**EXEMPLO EM JAVA** 
```java
public class Exemplo {
    public static void main(String[] args) {
        int[] numeros = {1, 2, 3, 4, 5};
        int soma = 0;

        for (int i = 0; i < numeros.length; i++) {
            soma += numeros[i];
        }

        System.out.println("Soma: " + soma);
    }
}
```

Nesse exemplo, o programa define uma lista com valores e entra em um laço de repetição onde para cada número da lista ele soma com o número anterior até finalizar a lista, no final exibe o resultado da soma.

---
## Paradigma Declarativo

O paradigma declarativo foca na descrição do resultado esperado, sem detalhar o passo a passo da execução como o imperativo. Nesse caso é definido regras ou relações e o sistema se encarrega de encontrar a solução.

**EXEMPLO EM PROLOG** 
```prolog
% Fatos
numero(1).
numero(2).
numero(3).
numero(4).
numero(5).

% Regra para soma
soma_total(Soma) :-
    findall(N, numero(N), Lista),
    sum_list(Lista, Soma).
```

Consulta:
```prolog
?- soma_total(S).
```

Nesse exemplo do código em Prolog, os números são definidos como fatos e a soma é feita por meio de uma regra que utiliza **findall** para reunir os valores em uma lista e **sum_list** para calcular o total. Diferente do paradigma imperativo, não tem controle explícito de fluxo, pois o próprio Prolog se encarrega de executar os passos necessários para chegar ao resultado.

---
## Comparação entre os códigos

Os dois exemplos apresentados tem o mesmo objetivo, somar uma sequência de números. No entanto, cada um tem sua maneira de ser feito

No código em Java, seguindo o paradigma imperativo, o programador controla todo o processo, definindo variáveis, laço de repetição e a lógica da soma. No caso, ele especifica exatamente como o resultado deve ser obtido.

Já no código em Prolog, o foco está na definição das regras e dos dados. O programador informa o que deseja (a soma dos números) e o próprio sistema resolve como executar essa operação.

---
Os paradigmas imperativo e declarativo nos trazem abordagens diferentes para resolver problemas. Enquanto o imperativo traz maior controle sobre a execução do programa, o declarativo proporciona maior simplicidade e abstração.