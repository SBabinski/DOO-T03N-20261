Nome: Raphael Gomes Henke

## Característica 1: Operator Overloading

Timestamp do vídeo: 01:50

O que é?  
É um recurso do Kotlin que permite mudar o funcionamento de operadores, como +, - e ==, para objetos criados pelo programador.

Pra que serve?  
Serve para deixar o código mais simples e fácil de entender.

Como é normalmente utilizado?  
É usado quando uma classe precisa utilizar operadores de forma personalizada, como em vetores ou números complexos

Exemplo de código:

```kotlin
data class Vetor(val x: Int, val y: Int) {
    operator fun plus(outro: Vetor) = Vetor(x + outro.x, y + outro.y)
}

fun main() {
    val a = Vetor(1, 2)
    val b = Vetor(3, 4)
    println(a + b)
}
```

---

## Característica 2: Coroutines

timestamp do vídeo: 03:20

O que é?  
Coroutines são um recurso do Kotlin que permite executar tarefas em segundo plano de forma simples

Pra que serve?  
Servem para executar tarefas que podem demorar, sem travar o programa

Como é normalmente utilizado?  
São muito usadas em aplicativos Android e em operações como chamadas para APIs e acesso a banco de dados.

Exemplo de código:

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000)
        println("Olá, Kotlin!")
    }
}
```