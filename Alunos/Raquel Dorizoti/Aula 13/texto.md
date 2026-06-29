# Nome: Raquel Dorizoti

---

# Conceito 1: Sealed Class

**Timestamp no vídeo:** 1:51

## O que é?

A Sealed Class é um recurso do Kotlin que permite criar uma classe com um conjunto limitado de subclasses. Isso significa que apenas as classes definidas no mesmo arquivo podem herdar dela, permitindo que o compilador saiba exatamente quais tipos existem.

## Para que serve?

Ela é bastante utilizada para representar situações em que há um número fixo de possibilidades, como estados de uma aplicação ou o resultado de uma operação. Dessa forma, o compilador consegue verificar se todas as opções foram tratadas, tornando o código mais seguro e evitando erros.

## Como é utilizada?

```kotlin
sealed class Resultado {
    data class Sucesso(val dados: String) : Resultado()
    data class Erro(val mensagem: String) : Resultado()
    object Carregando : Resultado()
}

fun processar(resultado: Resultado) {
    when (resultado) {
        is Resultado.Sucesso -> println("Dados recebidos: ${resultado.dados}")
        is Resultado.Erro -> println("Erro: ${resultado.mensagem}")
        Resultado.Carregando -> println("Carregando")
    }
}

fun main() {
    processar(Resultado.Sucesso("Lista de usuários"))
    processar(Resultado.Erro("Falha na conexão"))
    processar(Resultado.Carregando)
}
```

Nesse exemplo, o `when` trata todas as subclasses da classe `Resultado`, dispensando o uso do `else`, já que o compilador conhece todos os casos possíveis.

---

# Conceito 2: Coroutines

**Timestamp no vídeo:** 3:16

## O que é?

Coroutines são um mecanismo do Kotlin criado para facilitar a execução de tarefas assíncronas. Elas permitem executar operações demoradas, como acessar um banco de dados ou fazer uma requisição para uma API, sem bloquear a execução do restante do programa.

## Para que serve?

Seu objetivo é manter a aplicação responsiva enquanto uma tarefa é executada em segundo plano. Em comparação com as Threads tradicionais do Java, as Coroutines utilizam menos recursos e deixam o código mais organizado e simples de entender.

## Como é utilizada?

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Início")

    val tarefa = launch {
        delay(2000)
        println("Tarefa concluída!")
    }

    println("Executando outras atividades...")

    tarefa.join()

    println("Fim")
}
```

Nesse exemplo, uma Coroutine é iniciada para simular uma tarefa que demora dois segundos para terminar. Enquanto isso, o restante do programa continua sendo executado normalmente. Apenas no comando `join()` o programa aguarda a conclusão da tarefa antes de finalizar.