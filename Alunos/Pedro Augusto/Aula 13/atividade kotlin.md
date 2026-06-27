---

## Conceito 1: Sealed Classes

**Nome:** Pedro Augusto

**Conceito escolhido:** Sealed Classes (Classes Seladas)

**Timestamp do vídeo:** aproximadamente 1:52 — *"And controlled inheritance with sealed class!"*

### O que é?
Uma `sealed class` em Kotlin é uma classe cuja hierarquia de herança é **controlada e restrita** — apenas as subclasses declaradas no mesmo arquivo (ou mesmo pacote, a partir do Kotlin 1.5) podem estendê-la.

### Para que serve?
Serve para representar um conjunto **fechado e conhecido** de tipos possíveis. É muito útil quando você quer modelar estados ou resultados que só podem assumir um número fixo de formas.

### Como é normalmente utilizado?
É frequentemente combinada com o `when`, que consegue verificar **todos os casos possíveis** sem precisar de um `else`, pois o compilador já sabe todas as subclasses existentes.

### Exemplo de código

```kotlin
sealed class Resultado {
    data class Sucesso(val dados: String) : Resultado()
    data class Erro(val mensagem: String) : Resultado()
    object Carregando : Resultado()
}

fun processar(resultado: Resultado) {
    when (resultado) {
        is Resultado.Sucesso    -> println("Dados: ${resultado.dados}")
        is Resultado.Erro       -> println("Erro: ${resultado.mensagem}")
        is Resultado.Carregando -> println("Aguarde...")
    }
}
```

---

## Conceito 2: Extension Functions

**Timestamp do vídeo:** ~3:30 — *"With infix and extension functions"*

### O que é?
Extension functions permitem **adicionar novos métodos a uma classe existente sem precisar herdar dela ou modificar seu código-fonte**. Você "estende" a classe de fora, como se fosse um método nativo dela.

### Para que serve?
É útil para adicionar comportamentos a classes que você não controla (como classes do SDK do Android, da biblioteca padrão do Java, etc.) de forma limpa e sem criar classes utilitárias cheias de métodos estáticos.

### Como é normalmente utilizado?
Declara-se a função com o tipo receptor antes do nome da função. Dentro dela, `this` se refere ao objeto receptor.

### Exemplo de código

```kotlin
// Adicionando um método à classe String sem modificá-la
fun String.isPalindromo(): Boolean {
    return this == this.reversed()
}

fun main() {
    println("arara".isPalindromo()) // true
    println("kotlin".isPalindromo()) // false
}
```
