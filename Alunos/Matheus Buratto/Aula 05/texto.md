# Paradigmas de Programação: Imperativo vs Declarativo

Os paradigmas de programação definem as abordagens fundamentais para a estruturação e execução de programas. Dentre os principais, destacam-se os paradigmas imperativo e declarativo, que se diferenciam pela forma como as instruções são especificadas ao computador.

## Paradigma Imperativo

No paradigma imperativo, o desenvolvedor descreve explicitamente cada etapa necessária para a resolução do problema, detalhando o fluxo de execução e as operações a serem realizadas.

## Paradigma Declarativo

No paradigma declarativo, o foco está na definição do resultado desejado, sem a necessidade de explicitar o processo de execução. Nesse caso, o sistema é responsável por determinar como a solução será obtida.

## Comparação: Java (Imperativo) vs Prolog (Declarativo)

### Cálculo de Fatorial

**Imperativo (Java)**

```java
public class Fatorial {
    public static int fatorial(int n) {
        int resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }
    public static void main(String[] args) {
        System.out.println(fatorial(5));
    }
}
```

**Declarativo (Prolog)**

```prolog
fatorial(0, 1).
fatorial(N, F) :-
    N > 0,
    N1 is N - 1,
    fatorial(N1, F1),
    F is N * F1.
```

## Comparação Entre os Paradigmas

O paradigma imperativo caracteriza-se pela descrição detalhada do algoritmo, enquanto o paradigma declarativo enfatiza as relações lógicas entre os dados, delegando ao sistema a responsabilidade de encontrar a solução.

## Vantagens e Desvantagens

- **Imperativo**: Proporciona maior controle sobre a execução e, em muitos casos, melhor desempenho. Entretanto, tende a ser mais verboso e mais suscetível a erros de implementação.
- **Declarativo**: Apresenta maior concisão e legibilidade, sendo especialmente adequado para sistemas baseados em regras. Por outro lado, oferece menor controle direto sobre o processo de execução.

## Conclusão

O paradigma imperativo, exemplificado pelo uso de Java, exige que o programador especifique detalhadamente o fluxo de controle e as operações necessárias para a resolução do problema.

Por sua vez, o paradigma declarativo, como no Prolog, permite que o desenvolvedor descreva apenas a lógica do problema, delegando ao interpretador a responsabilidade pela execução.

Ambas as abordagens conduzem ao mesmo resultado, porém por meio de perspectivas conceituais distintas, evidenciando a diversidade de estilos existentes na programação.
