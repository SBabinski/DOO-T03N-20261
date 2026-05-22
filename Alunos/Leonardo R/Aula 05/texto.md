Paradigmas de Programação: Imperativo vs Declarativo

Os paradigmas de programação representam diferentes abordagens para a construção de soluções computacionais. Entre os mais relevantes estão o paradigma imperativo e o paradigma declarativo, que se distinguem principalmente pela forma como expressam a lógica de um programa. Enquanto o paradigma imperativo enfatiza a sequência de comandos necessários para atingir um objetivo (como fazer), o paradigma declarativo foca na descrição do problema e nas condições para sua resolução (o que fazer).

No paradigma imperativo, utilizado em linguagens como Java, o programador especifica detalhadamente cada passo da execução. Isso inclui controle de fluxo, manipulação de variáveis e atualização de estados. A lógica é construída por meio de estruturas como laços, condicionais e funções. Abaixo, apresenta-se um exemplo mais completo em Java que calcula a soma, a média e identifica o maior valor de uma lista de números:

import java.util.Scanner;

public class ProcessamentoLista {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Quantos números deseja inserir? ");
        int n = scan.nextInt();

        int[] lista = new int[n];

        // Entrada de dados
        for (int i = 0; i < n; i++) {
            System.out.print("Digite o número " + (i + 1) + ": ");
            lista[i] = scan.nextInt();
        }

        // Processamento
        int soma = 0;
        int maior = lista[0];

        for (int i = 0; i < lista.length; i++) {
            soma += lista[i];

            if (lista[i] > maior) {
                maior = lista[i];
            }
        }

        double media = (double) soma / n;

        // Saída de dados
        System.out.println("\nResultados:");
        System.out.println("Soma: " + soma);
        System.out.println("Média: " + media);
        System.out.println("Maior valor: " + maior);

        scan.close();
    }
}

Nesse exemplo, observa-se claramente o controle explícito do fluxo do programa: primeiro ocorre a entrada de dados, depois o processamento por meio de laços e condicionais, e por fim a exibição dos resultados. O programador determina cada etapa da execução, evidenciando o caráter procedural do paradigma imperativo.

Por outro lado, o paradigma declarativo, exemplificado pela linguagem Prolog, adota uma abordagem baseada em lógica e regras. O programador define relações e propriedades, deixando para o interpretador a responsabilidade de encontrar as soluções. Abaixo, um exemplo mais robusto em Prolog que calcula a soma, o tamanho da lista, a média e o maior elemento:

% Soma dos elementos de uma lista
soma([], 0).
soma([H|T], Resultado) :-
    soma(T, Parcial),
    Resultado is H + Parcial.

% Tamanho da lista
tamanho([], 0).
tamanho([_|T], Resultado) :-
    tamanho(T, Parcial),
    Resultado is Parcial + 1.

% Encontrar o maior elemento
maior([X], X).
maior([H|T], Resultado) :-
    maior(T, Parcial),
    (H > Parcial -> Resultado = H ; Resultado = Parcial).

% Média da lista
media(Lista, Media) :-
    soma(Lista, Soma),
    tamanho(Lista, Tamanho),
    Tamanho > 0,
    Media is Soma / Tamanho.

Diferentemente do Java, o código em Prolog não especifica um fluxo de execução sequencial. Em vez disso, ele define regras lógicas que descrevem como os resultados se relacionam com os dados. A execução ocorre por meio de consultas, como:

?- soma([10, 20, 30], S).
?- media([10, 20, 30], M).
?- maior([10, 20, 30], X).

Comparando os dois trechos, percebe-se que ambos atingem os mesmos objetivos — processar uma lista de números — mas com filosofias distintas. No Java, o programador guia explicitamente cada passo do processo, controlando variáveis e fluxo. Já no Prolog, o foco está na definição das regras e relações, e o sistema se encarrega de aplicar essas regras para encontrar as respostas.

Em conclusão, o paradigma imperativo oferece maior controle e previsibilidade na execução, sendo amplamente utilizado em aplicações gerais. Por outro lado, o paradigma declarativo proporciona maior nível de abstração e clareza na representação de problemas, sendo especialmente útil em áreas como inteligência artificial e sistemas baseados em conhecimento.