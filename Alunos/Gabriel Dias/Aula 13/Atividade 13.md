# Gabriel Heinrick Dias

# Conceitos de Kotlin

## Parallel Programming

**Timestamp do vídeo:** 3:30

### O que é?
A execução simultânea de tarefas usando **coroutines**
que suspendem sem bloquear threads.

### Para que serve?
Para execução de várias operações ao mesmo tempo (requisições, cálculos) de forma eficiente sem travar a thread principal.

### Como é normalmente utilizado?
Com `async` para lançar tarefas paralelas e `await` para coletar os resultados quando prontos.

### Exemplo de código
```kotlin 
import kotlinx.coroutines.*

fun main() = runBlocking {
    val a = async { delay(1000); "resultado-A" }
    val b = async { delay(1000); "resultado-B" }

    println(a.await() + " | " + b.await())
}
```
---

## Syntax Shortcut

**Conceito escolhido:** Syntax shortcut
**Timestamp do vídeo:** 3:30

### O que é?
Recursos da linguagem que reduzem código repetitivo: lambdas com `{ }`, `it` implícito, method reference `::` e *trailing lambda*.

### Para que serve?
Tornar pipelines e chamadas assíncronas mais legíveis, sem sacrificar clareza.

### Como é normalmente utilizado?
Em coleções, coroutines e qualquer função que receba outra função como argumento.

### Exemplo de código

```kotlin
val nomes = listOf("ana", "bob", "carol")

// it implícito — parâmetro único não precisa de nome
nomes.filter { it.length > 3 }
     .map { it.uppercase() }
     .forEach(::println)      // method reference

// Trailing lambda — chaves fora dos parênteses
launch { println("rodando em paralelo") }

// Sem shortcut (verboso)
launch({ println("rodando em paralelo") })
```