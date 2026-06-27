# Aula 13 - Atividade Extra

## Nome:
Gabriel Alvise

---

## Conceito escolhido 1:
Coroutines

## Timestamp do vídeo que menciona o conceito:
Aproximadamente **3:33**

## O que é? Pra que serve? Como é normalmente utilizado?

Coroutines são uma forma do Kotlin lidar com tarefas que podem demorar um pouco para terminar, sem travar o programa inteiro. Na prática, elas ajudam quando o sistema precisa fazer mais de uma coisa ao mesmo tempo, como buscar dados em uma API, consultar um banco de dados ou esperar alguma resposta externa.

O que eu achei interessante é que, em vez de criar várias threads manualmente, o Kotlin permite escrever esse tipo de código de um jeito mais organizado e parecido com um código normal. Com isso, fica mais fácil entender o fluxo do programa, porque parece que as instruções estão acontecendo em sequência, mesmo tendo operações assíncronas por trás.

Um uso bem comum seria em um aplicativo que precisa carregar informações da internet. A tela não pode ficar congelada enquanto espera a resposta, então a coroutine permite fazer essa busca em segundo plano e continuar o funcionamento da aplicação.

## Exemplo de código:

```kotlin
import kotlinx.coroutines.*

suspend fun buscarUsuario(): String {
    delay(1000)
    return "Gabriel"
}

fun main() = runBlocking {
    println("Buscando usuário...")

    val nomeUsuario = buscarUsuario()

    println("Usuário encontrado: $nomeUsuario")
}
```

Nesse exemplo, a função `buscarUsuario` simula uma busca que demora um segundo. O `suspend` indica que essa função pode ser pausada e retomada depois, sem precisar bloquear tudo como aconteceria em uma execução mais simples.

---

## Conceito escolhido 2:
Extension Functions

## Timestamp do vídeo que menciona o conceito:
Aproximadamente **3:33**

## O que é? Pra que serve? Como é normalmente utilizado?

Extension functions são funções de extensão. Elas servem para adicionar uma função a um tipo que já existe, sem precisar alterar a classe original. É como se eu conseguisse “ensinar” uma classe pronta a fazer algo novo, mas sem mexer diretamente nela.

Isso é útil principalmente quando estamos usando classes da própria linguagem ou de alguma biblioteca externa. Muitas vezes não temos como editar essas classes, mas podemos criar uma função de extensão para deixar o código mais limpo e mais fácil de ler.

Um exemplo simples seria criar uma função para formatar uma `String`. Em vez de ficar repetindo a mesma lógica em vários lugares do código, posso criar uma extension function e chamar essa função direto na variável do tipo `String`.

## Exemplo de código:

```kotlin
fun String.primeiraLetraMaiuscula(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}

fun main() {
    val nomeUsuario = "gabriel"

    println(nomeUsuario.primeiraLetraMaiuscula())
}
```

Nesse caso, foi criada uma função chamada `primeiraLetraMaiuscula` para o tipo `String`. Depois disso, qualquer texto pode chamar essa função como se ela já fizesse parte da própria classe `String`. Isso deixa o código mais direto e também evita criar funções soltas com nomes grandes ou repetitivos.
