# Paradigmas de Programação: Imperativo vs Declarativo

Os paradigmas **imperativo** e **declarativo** representam duas formas distintas de resolver problemas. O imperativo (ex: Java) descreve *como* a solução deve ser executada, passo a passo. Já o declarativo (ex: Prolog) descreve *o que* deve ser resolvido, deixando a execução para o sistema.

## Comparação de Código

### Java (Imperativo)

```java
public static boolean pertence(int[] lista, int valor) {
    for (int i = 0; i < lista.length; i++) {
        if (lista[i] == valor) {
            return true;
        }
    }
    return false;
}
```

No Java, o programador controla tudo: cria o laço, percorre a lista e decide quando parar. O foco está no **processo** (iteração e verificação manual).



### Prolog (Declarativo)

```prolog
pertence(X, [X|_]).
pertence(X, [_|T]) :- pertence(X, T).
```

No Prolog, não há laço explícito. O código define apenas **regras lógicas**. O próprio Prolog se encarrega de percorrer a lista usando recursão automática e inferência.



## Comparação Direta

* **Controle**:

  * Java → o programador controla o fluxo
  * Prolog → o sistema controla a execução

* **Abordagem**:

  * Java → passo a passo (procedural)
  * Prolog → definição de regras (lógica)

* **Complexidade do código**:

  * Java → mais detalhado
  * Prolog → mais conciso

* **Execução**:

  * Java → sequência de instruções
  * Prolog → busca por soluções baseada em regras

## Conclusão

O paradigma imperativo oferece maior controle e é mais utilizado no desenvolvimento geral. Já o declarativo simplifica a representação de problemas lógicos, tornando o código mais direto e expressivo. Saber utilizar ambos amplia a capacidade de resolver diferentes tipos de problemas.
