# Atividade Extra – Nanowar of Steel: Kotlin

**Nome:** João Gabriel Kazmierczak





# Null Safety (Segurança contra valores nulos)

**Timestamp do vídeo:** 0:46

## O que é?

Uma das características mais conhecidas do Kotlin é o sistema de **Null Safety**, criado para evitar um erro muito comum em Java: a `NullPointerException`. Em Kotlin, a linguagem consegue diferenciar variáveis que podem receber `null` das que obrigatoriamente precisam ter um valor.

## Para que serve?

Esse recurso serve para deixar o código mais seguro e evitar erros durante a execução do programa. Em muitas linguagens, é fácil esquecer de verificar se uma variável está nula antes de utilizá-la, o que pode causar falhas e até encerrar a aplicação.

## Como é normalmente utilizado?

Por padrão, uma variável em Kotlin não aceita o valor `null`. Se o programador quiser permitir isso, é necessário adicionar um `?` após o tipo da variável. Dessa forma, o próprio compilador ajuda a lembrar que aquela variável precisa de um tratamento especial.

## Exemplo de código

```kotlin
var nome: String? = null

if (nome != null) {
    println(nome.length)
}
```

Outra forma bastante utilizada é o operador de chamada segura (`?.`):

```kotlin
var nome: String? = null

println(nome?.length)
```

Nesse caso, se `nome` for `null`, o programa não gera um erro e simplesmente retorna `null`.

---

# Conceito escolhido: Coroutines

**Timestamp do vídeo:** 3:16

## O que é?

Coroutines são um recurso do Kotlin que facilita a programação assíncrona e concorrente. Elas permitem executar várias tarefas ao mesmo tempo de uma maneira mais simples do que criar e gerenciar várias threads manualmente.

## Para que serve?

As coroutines são muito úteis quando o programa precisa realizar tarefas demoradas, como acessar um banco de dados, fazer uma requisição para uma API ou baixar informações da internet. Enquanto essas operações acontecem, o restante do programa pode continuar funcionando normalmente.

## Como é normalmente utilizado?

Em vez de criar novas threads para cada tarefa, o programador pode usar coroutines para executar operações em segundo plano de forma mais organizada e com menor consumo de recursos.

## Exemplo de código

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000)
        println("Olá depois de 1 segundo!")
    }

    println("Olá imediatamente!")
}
```

Saída:

```text
Olá imediatamente!
Olá depois de 1 segundo!
```

Nesse exemplo, a mensagem "Olá imediatamente!" aparece primeiro, enquanto a coroutine espera um segundo antes de exibir a segunda mensagem.

---
