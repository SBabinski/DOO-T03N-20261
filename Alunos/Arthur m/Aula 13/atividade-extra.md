# Aula 13 - Atividade Extra

Nome: Arthur Miranda do Carmo  

---

## Conceito 1: Sealed Class

**Timestamp no vídeo:** 1:51

### O que é?

Uma Sealed Class (classe selada) é um tipo especial de classe no Kotlin que restringe quais outras classes podem herdá-la. Todas as subclasses de uma Sealed Class precisam ser declaradas no mesmo arquivo. Isso dá ao compilador conhecimento total de todos os tipos possíveis, tornando o código mais seguro e previsível.

### Pra que serve?

É muito utilizada para representar um conjunto fixo e conhecido de estados ou resultados, como o resultado de uma operação que pode ser sucesso ou erro. Com ela, o compilador consegue verificar se todos os casos foram tratados, evitando bugs por esquecer de tratar alguma situação.

### Como é normalmente utilizado?

```kotlin
// Definindo uma Sealed Class para representar o resultado de uma operação
sealed class Resultado {
    data class Sucesso(val dados: String) : Resultado()
    data class Erro(val mensagem: String) : Resultado()
    object Carregando : Resultado()
}

fun processar(resultado: Resultado) {
    when (resultado) {
        is Resultado.Sucesso   -> println("Dados recebidos: ${resultado.dados}")
        is Resultado.Erro      -> println("Ocorreu um erro: ${resultado.mensagem}")
        is Resultado.Carregando -> println("Carregando...")
    }
    // O compilador garante que todos os casos foram tratados!
}

fun main() {
    processar(Resultado.Sucesso("Lista de usuários"))
    processar(Resultado.Erro("Sem conexão com a internet"))
    processar(Resultado.Carregando)
}
```

---

## Conceito 2: Coroutines

**Timestamp no vídeo:** 3:16

### O que é?

Coroutines são uma funcionalidade do Kotlin para executar tarefas assíncronas (que podem demorar, como chamadas de rede ou leitura de arquivos) de forma simples e sem travar o programa. Diferente das Threads tradicionais do Java, as Coroutines são muito mais leves e fáceis de usar.

### Pra que serve?

Sempre que uma operação pode demorar (buscar dados de uma API, acessar banco de dados, ler um arquivo), as Coroutines permitem que o programa continue rodando normalmente enquanto aguarda o resultado, sem bloquear a execução. Em Java isso seria feito com Threads ou callbacks, que são muito mais complexos de gerenciar.

### Como é normalmente utilizado?

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Início")

    // Lança uma coroutine que roda em paralelo
    val tarefa = launch {
        delay(2000) // Simula uma operação demorada (2 segundos)
        println("Tarefa concluída!")
    }

    println("Fazendo outras coisas enquanto aguarda...")
    tarefa.join() // Aguarda a coroutine terminar
    println("Fim")
}

// Saída:
// Início
// Fazendo outras coisas enquanto aguarda...
// Tarefa concluída!
// Fim
```