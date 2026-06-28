# Conceito 1

Nome: Mateus Zanatta

Conceito escolhido: Coroutines

Timestamp do vídeo: 3:30

## O que é?

Coroutines são uma forma de executar tarefas assíncronas de maneira mais simples e eficiente. Elas permitem que uma função seja pausada e retomada posteriormente sem bloquear a execução principal do programa.

## Pra que serve?

São utilizadas para melhorar o desempenho de aplicações que precisam realizar operações demoradas, como chamadas para APIs, acesso a banco de dados ou leitura de arquivos.

## Como é normalmente utilizado?

No Kotlin, as coroutines são amplamente utilizadas no desenvolvimento Android e em aplicações que precisam executar várias tarefas simultaneamente sem criar diversas threads.

## Exemplo de código

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000)
        println("Olá Kotlin!")
    }

    println("Executando...")
}

# Conceito 2

Conceito escolhido: Classes Seladas 

Timestamp do vídeo: 2:45

## O que é?

Classes seladas são um tipo especial de classe que restringe quais outras classes podem herdar dela. Todas as subclasses precisam ser conhecidas e definidas previamente.

## Pra que serve?

Esse recurso aumenta a segurança e previsibilidade do código, pois o compilador consegue saber exatamente quais tipos podem existir dentro de uma hierarquia.

## Como é normalmente utilizado?

É muito utilizado para representar estados de uma aplicação, resultados de operações ou situações onde existe um conjunto limitado de possibilidades.

## Exemplo de código

```kotlin
sealed class Resultado

class Sucesso(val mensagem: String) : Resultado()

class Erro(val motivo: String) : Resultado()

fun processar(resultado: Resultado) {
    when (resultado) {
        is Sucesso -> println(resultado.mensagem)
        is Erro -> println(resultado.motivo)
    }
}
