## Paradigmas de Programação: Imperativo vs Declarativo

Os paradigmas de programação representam diferentes formas de pensar e estruturar a solução de problemas computacionais. Entre os mais relevantes estão o **paradigma imperativo** e o **paradigma declarativo**, que se distinguem principalmente pela forma como expressam a lógica de um programa.

O **paradigma imperativo** é baseado na descrição explícita de *como* um problema deve ser resolvido. Nele, o programador define uma sequência de instruções que alteram o estado do sistema ao longo do tempo. Linguagens como Java seguem esse modelo, utilizando variáveis, estruturas de controle (como `if`, `for`, `while`) e atribuições para guiar o fluxo de execução.

Já o **paradigma declarativo** foca em *o que* deve ser resolvido, e não necessariamente em como fazê-lo. Nesse modelo, o programador descreve regras, fatos ou relações, deixando para o sistema a responsabilidade de encontrar a solução. O Prolog é um exemplo clássico desse paradigma, baseado em lógica formal e inferência automática.

---

## Comparação entre Java e Prolog

### Exemplo em Java (Imperativo)

```java
int soma = 0;
for (int i = 1; i <= 5; i++) {
    soma += i;
}
System.out.println(soma);
```

Neste exemplo, o objetivo é calcular a soma dos números de 1 a 5. O código descreve passo a passo como isso deve ser feito:
- Inicializa uma variável `soma` com valor 0;
- Utiliza um laço `for` para iterar de 1 até 5;
- A cada iteração, adiciona o valor de `i` à variável `soma`;
- Ao final, imprime o resultado.

---

### Exemplo em Prolog (Declarativo)

```prolog
soma(0, 0).
soma(N, Resultado) :-
    N > 0,
    N1 is N - 1,
    soma(N1, R1),
    Resultado is N + R1.
```

Consulta:

```prolog
?- soma(5, Resultado).
```

Neste caso, o objetivo é o mesmo: calcular a soma de 1 até N. Porém, a abordagem é diferente:
- Define-se um **caso base** (`soma(0, 0)`);
- Define-se uma **regra recursiva**, que expressa a relação entre o problema atual e um problema menor;
- O Prolog utiliza essas regras para inferir automaticamente o resultado da consulta.

---

## Análise Comparativa

A principal diferença entre os dois paradigmas está na forma de pensamento exigida:

- **Java (imperativo)**: exige que o programador detalhe cada etapa da execução, controlando diretamente variáveis e fluxo.
- **Prolog (declarativo)**: exige que o programador descreva relações e regras, delegando ao interpretador a responsabilidade de encontrar a solução.

Enquanto o paradigma imperativo tende a ser mais intuitivo para iniciantes por sua natureza sequencial, o paradigma declarativo pode oferecer soluções mais elegantes e concisas para problemas baseados em lógica, como sistemas especialistas e inteligência artificial.

---

## Conclusão

Ambos os paradigmas são importantes e possuem aplicações específicas. O paradigma imperativo é amplamente utilizado no desenvolvimento de sistemas tradicionais, enquanto o declarativo se destaca em cenários que envolvem lógica, inferência e manipulação de conhecimento. Compreender essas diferenças amplia a capacidade do desenvolvedor de escolher a abordagem mais adequada para cada problema.
