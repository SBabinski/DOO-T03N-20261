# Conceito 2 — Coroutines

**Nome:** João Pedro Doná Ferreira  
**Conceito escolhido:** Coroutines (Corrotinas)  
**Timestamp do vídeo que menciona o conceito:** [3:51](https://www.youtube.com/watch?v=BsfXZjKLT9A&t=231)

---

## O que é?

Coroutine é uma forma de rodar código de forma assíncrona (ou seja, sem travar tudo enquanto espera alguma coisa) mas escrevendo de um jeito que parece código normal sequencial. A ideia é que uma coroutine pode ser *pausada* no meio da execução e retomada depois, sem bloquear a thread principal.

Pensa assim: quando seu app faz uma requisição pra uma API, ele precisa esperar a resposta. Sem coroutines, ou você trava tudo enquanto espera, ou você precisa usar Threads, Callbacks e afins, que são uma bagunça pra ler e manter. Coroutines resolvem isso de um jeito bem mais limpo.

---

## Pra que serve?

Sempre que o programa precisa esperar por alguma coisa sem deixar o resto travado:

- Chamar uma API ou banco de dados que demora pra responder
- Fazer download/upload de arquivo
- Rodar várias tarefas ao mesmo tempo sem criar uma Thread pra cada uma

---

## Como é normalmente utilizado?

Em Kotlin, coroutines usam os builders `launch` e `async` dentro de um **CoroutineScope**. O `runBlocking` (que aparece na música em *"fun main() runBlocking"*) cria um escopo que segura a thread atual até todas as coroutines dentro dele terminarem — muito usado no `main()` e em testes.

Funções que podem pausar no meio precisam ser marcadas com `suspend`.

---

## Exemplo de código

```kotlin
import kotlinx.coroutines.*

// "suspend" indica que essa função pode ser pausada enquanto espera
suspend fun buscarDados(fonte: String): String {
    delay(1000) // simula 1 segundo de espera (tipo uma chamada de API)
    return "Dados de $fonte recebidos!"
}

fun main() = runBlocking {
    println("Buscando dados...")

    // as duas coroutines rodam ao mesmo tempo
    val job1 = launch { println(buscarDados("Servidor A")) }
    val job2 = launch { println(buscarDados("Servidor B")) }

    job1.join()
    job2.join()

    println("Tudo pronto!")
}
```

**Saída esperada (demora ~1 segundo no total, não 2):**
```
Buscando dados...
Dados de Servidor A recebidos!
Dados de Servidor B recebidos!
Tudo pronto!
```

As duas buscas rodam em paralelo, então o tempo total é ~1 segundo e não 2. Sem coroutines, pra conseguir isso você teria que gerenciar Threads na mão, o que é bem mais chatinho.

---
