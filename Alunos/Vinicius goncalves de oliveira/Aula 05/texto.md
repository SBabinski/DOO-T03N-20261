# Paradigmas de Programação: Imperativo e Declarativo

## Paradigma Imperativo

No paradigma imperativo, o programador descreve **como** o problema deve ser resolvido, definindo passo a passo as instruções que a máquina deve executar. O controle de fluxo é explícito, usando estruturas como condicionais, laços e atribuição de variáveis. Java é um exemplo clássico desse paradigma.

## Paradigma Declarativo

Já no paradigma declarativo, o foco é em **o que** se quer como resultado, sem especificar como chegar lá. O programador declara fatos e regras, e o próprio sistema se encarrega de encontrar a solução. Prolog é uma linguagem representativa desse paradigma, baseada em lógica formal.

---

## Comparação de Código

### Java — Verificar se um número é par

```java
public class Main {
    public static void main(String[] args) {
        int numero = 4;
        if (numero % 2 == 0) {
            System.out.println("É par");
        } else {
            System.out.println("É ímpar");
        }
    }
}
```

O programa verifica explicitamente se o resto da divisão por 2 é zero e imprime o resultado. Cada etapa é controlada pelo programador.

### Prolog — Verificar se um número é par

```prolog
par(0).
par(N) :- N > 0, N1 is N - 2, par(N1).

% Consulta: ?- par(4).
% Resultado: true.
```

Aqui são declarados apenas o fato base (`0` é par) e a regra recursiva. Ao fazer a consulta, o motor do Prolog busca automaticamente a resposta por unificação, sem que o programador precise controlar esse processo.

---

## Conclusão

A principal diferença entre os dois paradigmas está no nível de controle: no imperativo (Java), o programador dita cada passo da execução; no declarativo (Prolog), ele apenas descreve o problema e delega a resolução ao sistema.
