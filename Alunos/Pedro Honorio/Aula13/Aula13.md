# Nome

Pedro Henrique Honorio

# Conceito 1

## Conceito escolhido

Garbage Collection

## Timestamp do vídeo que menciona o conceito

2:45

## O que é?

Garbage Collection é um mecanismo da JVM responsável por liberar automaticamente a memória ocupada por objetos que não estão mais sendo utilizados pelo programa.

## Para que serve?

Serve para evitar desperdício de memória e facilitar o desenvolvimento, pois o programador não precisa gerenciar a memória manualmente.

## Como é normalmente utilizado?

O programador cria e utiliza objetos normalmente. Quando esses objetos deixam de ser referenciados, a JVM pode removê-los da memória automaticamente.

## Exemplo de código

```java
String texto = new String("Java");
texto = null;
```

# Conceito 2

## Conceito escolhido

NullPointerException

## Timestamp do vídeo que menciona o conceito

3:50

## O que é?

NullPointerException é uma exceção que ocorre quando tentamos acessar um método ou atributo de uma variável que possui valor `null`.

## Para que serve?

Ela indica que um objeto esperado não foi inicializado corretamente ou perdeu sua referência.

## Como é normalmente utilizado?

Não é utilizada propositalmente. É um erro que normalmente deve ser tratado ou evitado através de validações.

## Exemplo de código

```java
String nome = null;
System.out.println(nome.length());
```
