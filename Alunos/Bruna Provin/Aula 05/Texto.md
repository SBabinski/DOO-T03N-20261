# Paradigmas de Programação: Imperativo e Declarativo

Os paradigmas de programação representam diferentes formas de organizar o pensamento computacional e estruturar soluções para problemas. Entre os principais paradigmas estudados estão o **imperativo** e o **declarativo**, que se distinguem principalmente pela maneira como descrevem a execução de um programa.

## Paradigma Imperativo

O paradigma imperativo baseia-se na ideia de que o programador deve descrever **como** o computador deve executar determinada tarefa. O foco está na sequência de instruções, no controle explícito do fluxo do programa e na modificação do estado por meio de variáveis.

Nesse modelo, o desenvolvedor define passo a passo o que deve acontecer durante a execução: criação de variáveis, uso de estruturas condicionais (`if`, `else`), laços de repetição (`for`, `while`) e atribuições de valores. A execução ocorre de forma sequencial e controlada diretamente pelo código escrito.

A linguagem **Java** é um exemplo clássico do paradigma imperativo. Observe o seguinte código que calcula o fatorial de um número:

```java
public class Fatorial {
    public static int calcular(int n) {
        int resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }
}
```

### Funcionamento do código em Java

1. É criada uma variável chamada `resultado`, inicializada com valor 1.
2. Um laço de repetição `for` é executado de 1 até `n`.
3. A cada iteração, o valor de `resultado` é multiplicado pelo valor atual de `i`.
4. Ao final do laço, o valor acumulado é retornado.

Nesse caso, o programador controla explicitamente cada etapa do processo. O estado da variável `resultado` é alterado sucessivamente até atingir o valor final. Portanto, o foco está no procedimento detalhado de execução.

---

## Paradigma Declarativo

O paradigma declarativo, por sua vez, concentra-se em descrever **o que** deve ser resolvido, e não os passos exatos para chegar à solução. O programador define regras, fatos ou relações, e o mecanismo da linguagem é responsável por encontrar a solução adequada.

Nesse modelo, não há controle explícito da sequência de execução. Em vez disso, a linguagem utiliza mecanismos internos, como inferência lógica e busca automática, para satisfazer as condições estabelecidas.

A linguagem **Prolog** é um exemplo representativo do paradigma declarativo. Veja o cálculo do fatorial em Prolog:

```prolog
fatorial(0, 1).
fatorial(N, Resultado) :-
    N > 0,
    N1 is N - 1,
    fatorial(N1, R1),
    Resultado is N * R1.
```

### Funcionamento do código em Prolog

1. Define-se um **fato base**: o fatorial de 0 é 1.
2. Define-se uma **regra recursiva** que estabelece que o fatorial de `N` é igual a `N` multiplicado pelo fatorial de `N-1`.
3. Ao realizar a consulta `?- fatorial(5, X).`, o Prolog tenta satisfazer as regras declaradas.
4. O mecanismo interno realiza chamadas recursivas até atingir o caso base e então retorna os valores calculados.

O programador não define um laço ou controla diretamente a ordem de execução. Ele apenas declara as relações matemáticas que caracterizam o problema.

---

## Comparação entre os Dois Paradigmas

Ao comparar os dois trechos de código, percebe-se uma diferença fundamental na abordagem:

- Em **Java**, o cálculo é realizado por meio de instruções sequenciais e manipulação explícita de variáveis.
- Em **Prolog**, o cálculo é descrito como uma relação lógica, e o próprio interpretador resolve o problema.

No paradigma imperativo:
- O foco está no controle do fluxo.
- Há modificação de estado.
- A execução é sequencial e explícita.

No paradigma declarativo:
- O foco está na definição de regras e relações.
- Não há controle explícito do fluxo.
- A execução é conduzida pelo mecanismo de inferência da linguagem.

---

## Conclusão

Os paradigmas imperativo e declarativo representam duas formas distintas de pensar a programação. O paradigma imperativo prioriza o detalhamento dos passos necessários para resolver um problema, sendo amplamente utilizado no desenvolvimento de sistemas tradicionais. Já o paradigma declarativo enfatiza a descrição lógica do problema, delegando à linguagem a responsabilidade de encontrar a solução.

Compreender ambos os paradigmas é fundamental para a formação acadêmica em Computação, pois amplia a capacidade de abstração, fortalece o raciocínio lógico e permite escolher a abordagem mais adequada para cada tipo de problema.