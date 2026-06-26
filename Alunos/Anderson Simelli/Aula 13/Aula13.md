# Aula 13 — Conceitos presentes na música Kotlin

## Nome

Anderson Simelli

---

## Conceito escolhido 1

String Templates

## Timestamp do vídeo que menciona o conceito

01:21

## O que é?

String Templates é um recurso presente em Kotlin que permite inserir variáveis e expressões diretamente dentro de uma string. Em vez de montar textos usando várias concatenações com o operador `+`, o programador consegue escrever a variável dentro do próprio texto.

Esse recurso deixa o código mais limpo, mais legível e mais fácil de manter, principalmente quando é necessário exibir mensagens com dados variáveis.

## Para que serve?

Serve para criar textos dinâmicos de forma simples. Esse recurso é normalmente utilizado para exibir mensagens ao usuário, montar logs, apresentar dados de cadastro, mostrar resultados de cálculos ou construir respostas dentro de um sistema.

## Como é normalmente utilizado?

Em Kotlin, utiliza-se o símbolo `$` para inserir uma variável dentro de uma string. Quando é necessário usar uma expressão maior, utiliza-se `${}`.

## Exemplo de código

```kotlin
fun main() {
    val nome = "Anderson"
    val idade = 20

    println("Nome: $nome")
    println("Idade no próximo ano: ${idade + 1}")
}
```

Nesse exemplo, `$nome` insere o valor da variável `nome` diretamente no texto. Já `${idade + 1}` executa uma expressão antes de mostrar o resultado.

---

## Conceito escolhido 2

Coroutines

## Timestamp do vídeo que menciona o conceito

03:16

## O que é?

Coroutines são um recurso do Kotlin usado para trabalhar com tarefas assíncronas de forma mais simples. Elas permitem que uma tarefa seja pausada e retomada posteriormente, sem bloquear completamente a execução do programa.

Na prática, isso é útil quando o sistema precisa realizar uma operação que pode demorar, como acessar uma API, consultar um banco de dados, baixar arquivos ou esperar uma resposta da internet.

## Para que serve?

Coroutines servem para evitar que o programa fique travado enquanto espera uma tarefa demorada terminar. Em aplicativos com interface gráfica ou em aplicações mobile, por exemplo, isso ajuda a manter o sistema responsivo, mesmo enquanto uma operação está sendo executada em segundo plano.

## Como é normalmente utilizado?

Elas são bastante utilizadas no desenvolvimento Android, em consumo de APIs, operações de entrada e saída, processamento assíncrono e tarefas que dependem de tempo de resposta externo.

Com coroutines, o código assíncrono fica mais parecido com um código sequencial comum, facilitando a leitura e a manutenção.

## Exemplo de código

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000)
        println("Consulta finalizada!")
    }

    println("Buscando informações...")
}
```

Nesse exemplo, `launch` inicia uma coroutine. O comando `delay` simula uma espera sem travar a execução de forma totalmente bloqueante. Assim, o programa pode lidar melhor com tarefas demoradas, como consultas externas ou chamadas de API.

---

## Conclusão

A música apresenta, de forma bem-humorada, vários recursos da linguagem Kotlin. Entre os conceitos citados, String Templates e Coroutines se destacam por representarem características modernas da linguagem.

String Templates ajudam a criar textos dinâmicos de forma mais simples e legível. Já Coroutines facilitam a execução de tarefas assíncronas sem travar o sistema. Esses recursos mostram como Kotlin busca tornar o desenvolvimento mais produtivo, seguro e adequado para aplicações modernas.