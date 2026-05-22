# Paradigmas de Programação: imperativo e declarativo

Os paradigmas de programação representam diferentes formas de pensar, estruturar e resolver problemas computacionais. Entre os paradigmas apresentados em aula, destacam-se o **imperativo** e o **declarativo**, que possuem propostas bastante distintas. Enquanto o paradigma imperativo concentra-se em descrever **como** a solução deve ser executada, o paradigma declarativo tem como foco expressar **o que** se deseja obter como resultado.

O paradigma **imperativo** é amplamente utilizado em linguagens como Java. Nesse modelo, o programador define uma sequência de instruções que será executada passo a passo pelo computador. Normalmente, há uso de variáveis, atribuições, estruturas de repetição, condicionais e alteração de estado ao longo da execução. Assim, o código descreve detalhadamente o procedimento necessário para atingir o objetivo desejado.

Já o paradigma **declarativo** trabalha de forma diferente. Em vez de detalhar cada etapa do processo, o programador descreve fatos, regras ou condições que representam o problema. A linguagem Prolog é um exemplo clássico desse paradigma, pois nela o foco está na lógica do problema e nas relações entre os elementos, deixando para o mecanismo de inferência da linguagem a responsabilidade de encontrar a resposta adequada.

A seguir, pode-se observar um exemplo simples em Java e outro em Prolog, ambos com o objetivo de verificar se uma pessoa é maior de idade.

## Exemplo em Java

```java
public class ExemploImperativo {
    public static void main(String[] args) {
        int idade = 20;

        if (idade >= 18) {
            System.out.println("Maior de idade");
        } else {
            System.out.println("Menor de idade");
        }
    }
}
```

No código em Java, o funcionamento ocorre de maneira sequencial. Primeiramente, a variável `idade` recebe um valor. Em seguida, o programa avalia a condição `idade >= 18`. Caso a condição seja verdadeira, o sistema imprime a mensagem **"Maior de idade"**; caso contrário, imprime **"Menor de idade"**. Nesse caso, percebe-se claramente a lógica imperativa, pois o programador informa ao computador cada passo necessário para produzir a saída.

## Exemplo em Prolog

```prolog
maior_de_idade(Idade) :- Idade >= 18.
```

Em Prolog, a abordagem é diferente. O código não descreve uma sequência de passos detalhada, mas sim uma regra lógica: uma pessoa será considerada maior de idade se sua idade for maior ou igual a 18. Ao realizar uma consulta como:

```prolog
?- maior_de_idade(20).
```

o interpretador responderá `true`, pois o valor informado satisfaz a regra declarada. Caso a consulta fosse feita com um valor menor que 18, a resposta seria `false`.

## Comparação entre os dois trechos

Embora os dois códigos tenham o mesmo objetivo, eles atingem esse objetivo de maneiras diferentes. O trecho em Java mostra explicitamente o fluxo de execução, com variável, teste condicional e impressão do resultado. Já o trecho em Prolog apresenta apenas a regra lógica necessária para determinar se a condição é verdadeira ou falsa.

Dessa forma, pode-se afirmar que o Java, no exemplo apresentado, adota uma visão mais operacional e procedural, orientada à execução passo a passo. O Prolog, por sua vez, utiliza uma lógica declarativa, na qual o problema é expresso em termos de relações e condições. Em outras palavras, no paradigma imperativo o programador controla mais diretamente o processo de execução, enquanto no paradigma declarativo o foco está na descrição do conhecimento e das regras do problema.

## Conclusão

Conclui-se que os paradigmas imperativo e declarativo representam formas distintas de desenvolver soluções computacionais. O paradigma imperativo, exemplificado pelo Java, enfatiza o controle da execução e a definição explícita das instruções. O paradigma declarativo, exemplificado pelo Prolog, prioriza a descrição lógica do problema e deixa para o sistema a tarefa de encontrar a solução com base nas regras estabelecidas. Ambos são importantes para a formação em programação, pois ampliam a compreensão sobre diferentes maneiras de modelar e resolver problemas na computação.
