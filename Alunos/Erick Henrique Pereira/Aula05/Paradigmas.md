# Trabalho sobre paradigmas
**Paradigmas** da programação são estilos e/ou filosofias que dizem como um código é estruturado e organizado para resolver determinado problema, ou seja, são diferentes **modos** para se lidar com problemas.

Dessa forma eles servem como regras que devem guiar o programador, assim cada linguagem de programação é pautada em um desses paradigmas.

Eles definem o que deve, e o que não deve ser feito num código

Na programação trabalhamos com 2 principais, sendo eles:

* Paradigma imperativo e
* Paradigma declarativo

## Paradigma Imperativo

É uma abordagem da programação que se baseia em **Ordenanças e Passo a Passos**, que ditam como a máquina irá realizar determinada tarefa modificando o estado do programa (variáveis e memória) sequencialmente.

Algumas características dela:

* _Foco:_ descreve exatamente o algoritmo e a ordem de execução

* _Estado:_ O programa manipula dados na memória através de variáveis

* _Estrutura sequêncial:_ A ordem dos comandos é fundamental para o resultado final.

## Paradigma Declarativo
É um paradigma focado no **que** se deve fazer, descrevendo dessa forma o resultado esperado ao invés de se dizer o como para alcançar o resultado, ela deixa a parte de consultas SQL, legibilidade e interfaces mais fáceis de serem realizadadas.

Algumas características são:

_Foco no resultado:_ Descreve como deve ser o resultado, não a sequência de comandos para o alcançar.

_Imutabilidade:_ Frequentemente evita estados mutáveis e efeitos colaterias

_Abstração:_ O computador gerencia a execução e os detalhes técnicos

### Para compararmos:
```java
public class SistemaNotas {
    public static void main(String[] args) {
        double[] notas = {7.5, 8.0, 6.0, 9.0};
        double soma = 0;

        // Somando as notas
        for (int i = 0; i < notas.length; i++) {
            soma += notas[i];
        }

        // Calculando a média
        double media = soma / notas.length;

        // Verificando aprovação
        if (media >= 7.0) {
            System.out.println("Aprovado com média: " + media);
        } else {
            System.out.println("Reprovado com média: " + media);
        }
    }
}
```
No código em Java, o funcionamento é totalmente controlado pelo programador. Primeiro, define-se um vetor de notas, depois o programa percorre esse vetor com um laço for para somar os valores, calcula a média e, por fim, utiliza uma estrutura condicional (if/else) para decidir se o aluno foi aprovado ou reprovado. Cada etapa do processo é explicitamente programada, determinando exatamente a ordem de execução.
```Prolog
    % Definição das notas
notas([7.5, 8.0, 6.0, 9.0]).

% Soma dos elementos da lista
soma([], 0).
soma([H|T], S) :-
    soma(T, S1),
    S is H + S1.

% Tamanho da lista
tamanho([], 0).
tamanho([_|T], N) :-
    tamanho(T, N1),
    N is N1 + 1.

% Cálculo da média
media(Lista, M) :-
    soma(Lista, S),
    tamanho(Lista, N),
    M is S / N.

% Regra de aprovação
aprovado(M) :-
    M >= 7.

% Consulta principal
resultado :-
    notas(L),
    media(L, M),
    (aprovado(M) ->
        write('Aprovado com média: '), write(M)
    ;
        write('Reprovado com média: '), write(M)
    ).
```
No código em Prolog, o funcionamento ocorre de forma diferente. Em vez de definir um fluxo passo a passo, o programa descreve relações e regras: como somar uma lista, como calcular o tamanho, como obter a média e o que significa estar aprovado. Quando a consulta resultado. é executada, o Prolog utiliza essas regras para chegar à resposta, realizando chamadas recursivas e aplicando inferência lógica automaticamente.

