# Paradigmas de Programação: Imperativo vs. Declarativo

## Introdução aos Paradigmas

Na programação, os **paradigmas** definem a forma como o programador estrutura e organiza o código para resolver problemas. Dois paradigmas fundamentais são o **imperativo** e o **declarativo**.

- **Paradigma Imperativo**: Foca em *como* resolver o problema, descrevendo passo a passo as instruções que a máquina deve executar. É sequencial e mutável, com ênfase em comandos, loops, condicionais e alteração de estado. Linguagens como Java exemplificam isso.

- **Paradigma Declarativo**: Foca no *o que* deve ser computado, sem especificar os passos exatos. O sistema subjacente decide *como* alcançar o resultado. É baseado em regras e fatos, comum em linguagens lógicas como Prolog.

## Comparação de Códigos: Verificação de Nota

Para ilustrar, comparamos trechos que **lêem uma nota do usuário (0-10)** e determinam o resultado: **aprovado** (≥7), **recuperação** (4-6.9) ou **reprovado** (<4).

### Código em Java (Paradigma Imperativo)

```java
import java.util.Scanner;

public class Nota {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite sua nota (0-10): ");
        double nota = scanner.nextDouble();

        // Lógica imperativa com condicionais explícitas
        if (nota >= 7.0) {
            System.out.println("APROVADO!");
        } else if (nota >= 4.0) {
            System.out.println("RECUPERAÇÃO!");
        } else {
            System.out.println("REPROVADO!");
        }

        scanner.close();
    }
}
```

Funcionamento :

Lê entrada do usuário com Scanner.
Use if-else instruções explícitas para verificar intervalos sequencialmente.
O programador controla exatamente o fluxo: entrada → comparação → saída.

### Código em Prolog (Paradigma Declarativo)

```prolog
% Regras declarativas para resultado da nota
aprovado(Nota) :- Nota >= 7.0.
recuperacao(Nota) :- Nota >= 4.0, Nota < 7.0.
reprovado(Nota) :- Nota < 4.0.

% Consulta (no interpretador Prolog):
% ?- aprovado(8.5).
% true.
% ?- recuperacao(5.0).
% true.
% ?- reprovado(2.5).
% true.
```

Funcionamento :

Definir regras lógicas puras: cada predicado declara o que constitui o resultado.
O motor de Prolog disponibiliza automaticamente as condições para uma nota dada.
Sem fluxo de controle explícito: basta consultar aprovado(8.5) e o sistema unificar/verificar.

## Análise Comparativa

Java (Imperativo): Foco em como executar - usa condicionais explícitas if-else para controlar o fluxo sequencial. Ideal para programas interativos com entrada/saída.

Prolog (Declarativo): Foco no o que é verdade - define regras lógicas que o motor de inferência avalia automaticamente. Perfeito para sistemas baseados em regras.

## Conclusão

Java é prático para aplicativos interativos; Prolog se destaca em sistemas baseados em regras. Ambos alcançam o objetivo, mas com filosofias opostas.
