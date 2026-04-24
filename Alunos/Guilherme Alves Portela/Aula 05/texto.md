A compreensão dos paradigmas de programação é fundamental para entender como diferentes linguagens estruturam a lógica e a execução de tarefas. Os paradigmas **Imperativo** e **Declarativo** representam as duas abordagens primordiais no desenvolvimento de software.

---

## 1. Paradigma Imperativo vs. Declarativo

O **Paradigma Imperativo** foca no **"como"** uma tarefa deve ser realizada. O programador descreve uma sequência de passos e comandos que alteram o estado do programa para atingir um resultado. É análogo a uma receita de bolo detalhada, onde cada instrução de movimento e temperatura é explicitada.

Por outro lado, o **Paradigma Declarativo** foca no **"o quê"** deve ser alcançado, omitindo os detalhes da implementação do controle de fluxo. O programador descreve propriedades do resultado desejado ou fatos sobre o problema, deixando que o motor de execução da linguagem decida a melhor forma de computar a solução.

---

## 2. Comparação de Código: Filtragem de Dados

Para ilustrar a diferença, consideremos o objetivo de identificar se um elemento pertence a uma lista de "aprovados".

### Exemplo em Java (Imperativo)

O Java, embora tenha adotado recursos funcionais recentemente, é classicamente imperativo. Abaixo, vemos o uso de um laço para percorrer a estrutura de dados.

```java
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> aprovados = List.of("Alice", "Bruno", "Caio");
        String busca = "Bruno";
        boolean encontrado = false;

        for (int i = 0; i < aprovados.size(); i++) {
            if (aprovados.get(i).equals(busca)) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            System.out.println(busca + " está aprovado.");
        }
    }
}
```

**Como funciona:**
* O código gerencia explicitamente o estado (a variável `encontrado`).
* Utiliza uma estrutura de controle de fluxo (`for`) para iterar manualmente sobre os índices da lista.
* Instrui o computador a comparar cada elemento um por um e interromper a execução (`break`) quando a condição for satisfeita.

---

### Exemplo em Prolog (Declarativo/Lógico)

O Prolog baseia-se na lógica de predicados. Não há laços `for` ou variáveis de estado mutáveis da mesma forma que no Java.

```prolog
% Fatos: Definição da base de conhecimento
aprovado(alice).
aprovado(bruno).
aprovado(caio).

% Consulta (Execução):
% ?- aprovado(bruno).
```

**Como funciona:**
* **Base de Conhecimento:** Primeiro, declaramos fatos que são verdades absolutas no contexto do programa.
* **Inferência:** Para atingir o objetivo, o usuário faz uma consulta (*query*). O motor do Prolog utiliza um processo chamado **unificação** e **backtracking** para verificar se a consulta pode ser provada como verdadeira a partir dos fatos fornecidos.
* Não descrevemos o algoritmo de busca; apenas declaramos a relação lógica entre os dados.

---

## 3. Síntese Comparativa

| Característica | Java (Imperativo/Procedural) | Prolog (Declarativo/Lógico) |
| :--- | :--- | :--- |
| **Foco** | Sequência de estados e comandos. | Relações, fatos e regras. |
| **Estado** | Alterado via atribuições (ex: `encontrado = true`). | Estático; baseado em verdades lógicas. |
| **Fluxo** | Definido por loops e condicionais. | Gerido pelo motor de inferência da linguagem. |
| **Abstração** | Baixa a Média (detalha o percurso). | Alta (detalha a intenção). |

Em suma, enquanto o Java exige que o desenvolvedor seja um arquiteto de processos, o Prolog exige que ele seja um modelador de domínios. A escolha entre um e outro depende da natureza do problema: sistemas de propósito geral costumam seguir o imperativo, enquanto sistemas especialistas e IA simbólica beneficiam-se do declarativo.