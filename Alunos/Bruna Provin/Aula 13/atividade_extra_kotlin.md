# Atividade Extra — Kotlin (Nanowar of Steel)

---

## Conceito 1

**Nome:** Coroutines (Corrotinas)

**Conceito escolhido:** Programação assíncrona e paralela com coroutines

**Timestamp do vídeo que menciona o conceito:** Trecho *"Parallel programming / Made intuitive with coroutines / Unleashing the power of suspending functions"*

---

### O que é?

Coroutines são um recurso do Kotlin que permite executar tarefas de forma **assíncrona e não-bloqueante**, ou seja, o programa pode iniciar uma tarefa demorada (como buscar dados da internet) e continuar fazendo outras coisas enquanto espera o resultado, sem travar a execução.

Diferente de criar várias threads manualmente, as coroutines são muito mais leves e fáceis de gerenciar — você pode ter milhares delas rodando sem comprometer a memória ou a performance.

### Pra que serve?

São usadas quando o programa precisa esperar por algo: uma requisição a uma API, leitura de banco de dados, download de arquivo, etc. Sem coroutines, essas operações bloqueariam a thread principal, deixando o app travado. Com coroutines, a execução "pausa" naquele ponto e libera a thread para outras tarefas, retomando quando o resultado chega.

### Como é normalmente utilizado?

Coroutines são iniciadas dentro de um **escopo** (`CoroutineScope`) com funções como `launch` (dispara e esquece) ou `async` (dispara e espera resultado). Funções que podem pausar a execução são marcadas com a palavra-chave `suspend`. Na música, o `runBlocking` e os dois `launch` paralelos do solo épico ilustram exatamente isso!

### Exemplo de código

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    // Duas coroutines rodando em paralelo
    launch {
        delay(1000L)
        println("Amazing solo")
    }
    launch {
        delay(1000L)
        println("Amazing solo harmonies")
    }
    println("Coroutines iniciadas!")
}

// Saída:
// Coroutines iniciadas!
// Amazing solo
// Amazing solo harmonies
```

---

## Conceito 2

**Nome:** Sealed Class (Classe Selada)

**Conceito escolhido:** Herança controlada com sealed class

**Timestamp do vídeo que menciona o conceito:** Trecho *"Controlled inheritance with sealed class"*

---

### O que é?

Uma **sealed class** é uma classe especial do Kotlin que **restringe quais outras classes podem herdá-la**. Todas as subclasses de uma sealed class precisam ser declaradas no mesmo arquivo, e o compilador sabe exatamente quais são elas. É como dizer: "essa hierarquia de classes é fechada — não existe nenhuma outra subclasse além dessas que eu defini aqui".

### Pra que serve?

Serve para representar um conjunto **fixo e conhecido** de possibilidades. É muito usada para modelar estados ou resultados de operações, onde você sabe de antemão todas as situações possíveis — por exemplo: sucesso, erro ou carregando. O grande benefício é que o compilador consegue verificar se você tratou todos os casos possíveis, evitando bugs.

### Como é normalmente utilizado?

É comum usar sealed classes junto com a expressão `when` do Kotlin. Como o compilador conhece todas as subclasses, o `when` consegue verificar se você cobriu todos os casos e avisa se esqueceu algum — sem precisar de um `else`.

### Exemplo de código

```kotlin
// Definindo uma sealed class com os possíveis resultados de uma operação
sealed class ResultadoBatalha {
    data class Vitoria(val heroi: String) : ResultadoBatalha()
    data class Derrota(val motivo: String) : ResultadoBatalha()
    object Empate : ResultadoBatalha()
}

fun analisarBatalha(resultado: ResultadoBatalha): String {
    // O compilador garante que todos os casos foram cobertos!
    return when (resultado) {
        is ResultadoBatalha.Vitoria -> "Vitória de ${resultado.heroi}! \m/"
        is ResultadoBatalha.Derrota -> "Derrota: ${resultado.motivo}"
        ResultadoBatalha.Empate    -> "Empate — honra para ambos os lados"
    }
}

fun main() {
    val resultado = ResultadoBatalha.Vitoria("Nanowar of Steel")
    println(analisarBatalha(resultado))
    // Saída: Vitória de Nanowar of Steel! \m/
}
```

---

> *"Smooth operators overloading / And controlled inheritance with sealed class / That's why from bytecode and fire / JetBrains forged KOTLIN!"*
> — Nanowar of Steel 🤘
