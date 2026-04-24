# Paradigmas de Programação

## Paradigma Imperativo(Java)

No paradigma imperativo a computação é vista como uma série de instruções que alteram o estado da memória. Em Java, por exemplo, o desenvolvedor é responsável por descrever o algoritmo de forma detalhada, ou seja, o passo a passo da execução.

### Exemplo: Busca de Elemento

```java
public class Exemplo {

    public static boolean par(int x) {
        return x % 2 == 0;
    }

    public static void main(String[] args) {
        System.out.println(par(4));
    }
}
```

O método verifica se o resto da divisão por 2 é igual a 0, se a divisão for igual a 0 ele retorna true, assim indicando que o número é par.

## O Paradigma Declarativo(Prolog)

No Prolog, criada no ano de 1972 por Colmerauer e Roussel, o programa é composto por fatos e regras, que representam o conhecimento sobre determinado domínio. A execução ocorre através de um motor de inferência, que utiliza mecanismos de busca e unificação para responder consultas feitas ao sistema.

### Exemplo: Busca de Elemento

```prolog
par(X) :- X mod 2 =:= 0.

?- par(4).
```

Nesse exeplo, é necessário que o programador apenas declare a regra lógica, e o sistema verifica se ela é verdadeira.