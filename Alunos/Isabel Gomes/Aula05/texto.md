
# **PARADIGMAS DA PROGRAMAÇÃO**
        Paradigmas da programação são conjuntos de conceitos, regras e prinípios 
    que definem como será escrito o código, servindo como um guia aos programadores.
        Dentro dos paradigmas, temos subdivisões das abordagens, sendo a abordagem IMPERATIVA e a DECLARATIVA.

## ABORDAGEM IMPERATIVA
        O paradigma imperativo é uma forma de programar que segue uma sequência de 
    instruções concretas para se realizar uma tarefa, como uma receita de bolo, 
    que diz como e o que fazer. 
        Nesse paradigma, temos 3 tipos de abordagens, a Programação estruturada,
    a Procedural e a Programação Orientada a Objetos.


### *_Programação estruturada_*
        Também conhecido como programação modular, foca na organização lógica do
    fluxos de controle, usando blocos de tomada de decisão(if, else), 
    blocos de construção de iteração(for, while...) e funções.
         A melhor intenção é melhorar a legibilidade e compreensão do código.
        As principais linguagens estruturadas são:
- COBOL
- PHP
- Perl
- Go



### *_Programação Procedural_*
        Baseia-se na chamade de procedimentos para a organização do fluxo.
        O foco é a modularização, onde funções recebem dados, processam e retornam resultados.
         As principais linguagens procedurais são: 
- C
- Java
- Pascal
    

### *_Programação Orientada a Objetos_*
        Criado em 1970, se baseia na criação de objetos, 
    que possuem propriedades e métodos. Seus principais elementos são a
    Classe, o Objeto, atributos e métodos.
        Além disso possuem 4 pilares:
- Herança;
- Polimorfismo;
- Abstração; e
- Encapsulamento
    



## ABORDAGEM DECLARATIVA 
        Aqui o fluxo de controle não é o principal, a abordagem tem maior foco na 
    lógica de programação e no resultado final. Ao invez de escrever uma sequência
    lógica detalhada, o programador declara as propriedades e relações 
    que o resultado final deve ter.
        Nesse paradigma, temos 2 tipos de abordagens, a programação Funcional e a Lógica.

### *_Programação Funcional_*
        Parte do princípio de que tudo são funções, não existe lista
    de instruções, mas sim um conjunto de funções que operam juntas no código. 
        Valoriza "o que" fazer, focando na tranformação de dados por meio da imutabilidade 
    e funções puras. Além disso, utiliza a recursividade para abordar problemas de maneira 
    direta e concisa. É implementada em linguagens como:
- Haskell;
- Scala
- JS;
- CLojure
     

### *_Programação Lógica_*
        O programador usa regras lógicas para definir a relação entre os elementos.
    Ele especifica as condições e relações que devem ser verdadeiras para obter o 
    resultado desejado, e o sistema deduz a solução com base nessas regras. 
    Linguagens:
- Prolog;
- DataLog
    

---


## COMPARAÇÃO DE UM CÓDIGO PROLOG COM JAVA

### JAVA

```CÓDIGO```
```JAVA
    public static void main(String[] args){
        int a=4;
        int b=6;
        int c=8;
        int media = (a + b + c)/ 3

        System.Out.println("A media dos tres valores e: " + media);
    }
```

---

### PROLOG
```CÓDIGO```
```PROLOG
    media :-
    A = 4,
    B = 6,
    C = 8,
    Media is (A + B + C) / 3,
    write('A media dos tres valores e: '),
    write(Media).
```

---


> Em java, definimos as variaveis e calculamos a media, dividindo por 3 e atribuindo o resultado a variavel média, sendo assim uma linguagem **imperativa**.
---
>Em ProLog definimos uma operação, **"media"**, onde passamos os valores, descrevendo o calculo e usando **"is"** para realizar aoperação e **"write"** para printar 




















