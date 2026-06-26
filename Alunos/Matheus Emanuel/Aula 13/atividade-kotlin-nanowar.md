# Atividade Extra — Kotlin (Nanowar of Steel)

## Conceito 1: Sealed class (classe selada)

**Timestamp do vídeo que menciona o conceito:** ~2:05 (trecho que cita *"sealed class"* ao falar de controle de herança)

**O que é?**
Uma *sealed class* (classe selada) é uma classe cujas subclasses são **todas conhecidas e
definidas em tempo de compilação**, dentro do mesmo módulo/arquivo. É como dizer ao
compilador: "essa classe só pode ter *estes* filhos, e mais nenhum". Ela é um meio-termo
entre uma classe normal (que qualquer um pode estender) e uma `enum` (que só tem valores
fixos, sem dados próprios).

**Pra que serve?**
Serve para representar um conjunto **fechado e finito de possibilidades** — por exemplo,
os estados possíveis de uma tela (Carregando, Sucesso, Erro). Como o compilador conhece
todas as subclasses, ele consegue garantir que você tratou **todos os casos** num bloco
`when`. Se faltar um caso, o código nem compila (sem precisar de um `else` "pra garantir").
Isso deixa o código mais seguro e mais fácil de manter.

**Como é normalmente utilizado?**
Geralmente combinada com a expressão `when`, muito usada em apps Android para modelar
estados de UI, respostas de rede ou resultados de operações.

**Exemplo de código:**
```kotlin
// Conjunto fechado de resultados possíveis
sealed class Resultado {
    data class Sucesso(val dados: String) : Resultado()
    data class Erro(val mensagem: String) : Resultado()
    object Carregando : Resultado()
}

fun tratar(resultado: Resultado): String =
    // Não precisa de 'else': o compilador sabe que só existem 3 casos
    when (resultado) {
        is Resultado.Sucesso  -> "Deu certo: ${resultado.dados}"
        is Resultado.Erro     -> "Falhou: ${resultado.mensagem}"
        Resultado.Carregando  -> "Carregando..."
    }

fun main() {
    println(tratar(Resultado.Sucesso("usuário logado")))
    println(tratar(Resultado.Erro("sem internet")))
    println(tratar(Resultado.Carregando))
}
```

---

## Conceito 2: Função infixa (infix function)

**Timestamp do vídeo que menciona o conceito:** ~3:45 (trecho final que cita *"infix functions"* junto com *extension functions*)

**O que é?**
Uma *infix function* é uma função marcada com a palavra-chave `infix` que pode ser chamada
**sem ponto e sem parênteses**, ficando "no meio" dos operandos — igual a um operador.
Em vez de `a.maisQue(b)`, você escreve `a maisQue b`. Para ser infixa, a função precisa
ter **um único parâmetro** e ser um método de uma classe ou uma função de extensão.

**Pra que serve?**
Serve para deixar o código mais **legível e parecido com linguagem natural**, quase como
se você estivesse lendo uma frase. É um "açúcar sintático": não muda o que o programa faz,
só muda a forma de escrever, tornando-a mais clara. A própria biblioteca padrão do Kotlin
usa isso (ex.: `1 to "um"` cria um par, e `5 until 10` cria um intervalo).

**Como é normalmente utilizado?**
Muito usada para criar pequenas DSLs (mini-linguagens dentro do código), construir pares,
intervalos, ou expressar regras de forma fluida em testes e validações.

**Exemplo de código:**
```kotlin
// Função infixa definida dentro de uma classe
class Dinheiro(val valor: Int) {
    infix fun mais(outro: Dinheiro): Dinheiro =
        Dinheiro(this.valor + outro.valor)
}

// Função infixa como extensão de Int
infix fun Int.elevadoA(expoente: Int): Int {
    var resultado = 1
    repeat(expoente) { resultado *= this }
    return resultado
}

fun main() {
    val carteira = Dinheiro(10) mais Dinheiro(5)   // em vez de Dinheiro(10).mais(...)
    println(carteira.valor)                        // 15

    println(2 elevadoA 8)                           // 256  (lê-se "2 elevado a 8")

    // Exemplos infixos já prontos na biblioteca padrão:
    val par = "chave" to "valor"                    // cria um Pair
    for (i in 1 until 4) print("$i ")               // 1 2 3
}
```

---

### Fontes
- Vídeo oficial: [Nanowar Of Steel — Kotlin (Official Power Point Video)](https://www.youtube.com/watch?v=BsfXZjKLT9A)
- Documentação Kotlin — [Sealed classes](https://kotlinlang.org/docs/sealed-classes.html)
- Documentação Kotlin — [Infix notation](https://kotlinlang.org/docs/functions.html#infix-notation)
