# Aula 05 - Paradigmas de Programação

## Paradigma Imperativo vs Declarativo

Os paradigmas de programação representam diferentes formas de pensar e estruturar soluções computacionais. Entre os principais, destacam-se o paradigma imperativo e o declarativo.

O paradigma imperativo é baseado na descrição passo a passo de como um problema deve ser resolvido. Nesse modelo, o programador controla explicitamente o fluxo de execução, utilizando comandos, variáveis e estruturas como loops e condicionais. Linguagens como Java são exemplos clássicos desse paradigma.

Já o paradigma declarativo foca no "o que" deve ser resolvido, e não no "como". O programador descreve regras e relações, deixando que o sistema determine a melhor forma de chegar ao resultado. Prolog é uma linguagem representativa desse modelo, muito utilizada em lógica e inteligência artificial.

## Comparação por imagem

![alt text](comparação.png)

### Explicação:
>#### Paradigma Imperativo: 
- Ênfase em comandos e controle de fluxo

- O programador define passo a passo como o problema será resolvido
- Uso de estruturas como: loops (for, while) condicionais (if)
- 👉 foco é “como fazer”

>#### Paradigma Declarativo:
- Foco em descrever o problema, não o processo
- O programador define regras ou relações
- O sistema decide como executar
- 👉 foco é “o que fazer”

## Comparação entre Java e Prolog

Verificar se um número pertence a uma lista.

### Exemplo em Java (Imperativo)

```java
public boolean existe(int[] lista, int valor) {
    for (int i = 0; i < lista.length; i++) {
        if (lista[i] == valor) {
            return true;
        }
    }
    return false;
}
```

Nesse caso, o código descreve exatamente o processo: percorre a lista elemento por elemento e compara com o valor desejado. O controle do fluxo é totalmente explícito.

### Exemplo em Prolog (Declarativo)

```prolog
existe(X, [X|_]).
existe(X, [_|T]) :- existe(X, T).
```

No Prolog, o problema é resolvido por meio de regras. A primeira linha diz que o elemento existe se for o primeiro da lista. A segunda define que ele existe se estiver no restante da lista. Não há controle explícito de loops, pois o próprio mecanismo da linguagem realiza a busca.

## Conclusão
Enquanto o paradigma imperativo exige que o programador detalhe cada passo da execução, o paradigma declarativo permite uma abordagem mais abstrata, focada na definição do problema. Ambos possuem vantagens e são utilizados em diferentes contextos, sendo importante compreender suas diferenças para escolher a melhor abordagem em cada situação.
