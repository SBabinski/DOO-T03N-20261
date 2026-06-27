# Nome:
Luiz Fernando de Souza Rossato

# Conceito escolhido:
## Coroutines

**Timestamp do vídeo que menciona o conceito:** Aproximadamente 03:20

### O que é?
Coroutines são uma funcionalidade da linguagem Kotlin que permite executar várias tarefas ao mesmo tempo sem travar o programa.

### Pra que serve?
Elas servem para deixar as aplicações mais rápidas e organizadas. Por exemplo, quando um aplicativo precisa buscar informações na internet, ele pode continuar funcionando normalmente enquanto espera a resposta.

### Como é normalmente utilizado?
É muito comum encontrar Coroutines em aplicativos Android, principalmente para fazer consultas em APIs, acessar bancos de dados ou executar tarefas que podem demorar alguns segundos.

### Exemplo de código em kotlin
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        println("Executando uma tarefa em paralelo")
    }
}
```

# Conceito escolhido:
## Sealed Classes

**Timestamp do vídeo que menciona o conceito:** Aproximadamente 01:51

### O que é?
Sealed Classes são um tipo especial de classe do Kotlin que limita quais classes podem herdar dela. Isso ajuda o programador a controlar melhor as possibilidades existentes no sistema.

### Pra que serve?
Elas servem para aumentar a segurança do código e evitar situações inesperadas. Como o número de subclasses é conhecido, fica mais fácil tratar todos os casos possíveis.

### Como é normalmente utilizado?
São bastante utilizadas para representar estados de uma aplicação, como sucesso, erro ou carregamento de dados. Também são usadas em respostas de APIs e em sistemas que precisam lidar com diferentes tipos de resultado.

### Exemplo de código em kotlin

```kotlin
sealed class Resultado

class Sucesso(val mensagem: String) : Resultado()
class Erro(val codigo: Int) : Resultado()

fun main() {
    val resultado: Resultado = Sucesso("Operação realizada com sucesso!")

    when (resultado) {
        is Sucesso -> println(resultado.mensagem)
        is Erro -> println("Erro: ${resultado.codigo}")
    }
}
```