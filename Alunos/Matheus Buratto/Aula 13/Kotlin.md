### Nome:
Matheus Buratto

# Conceito escolhido:

## Extension Functions

### O que é?
Extension Functions são funções que permitem adicionar novos comportamentos a uma classe
já existente sem precisar modificar o seu código-fonte ou criar uma subclasse. É como
se você "estendesse" as capacidades de uma classe de fora dela.

### Pra que serve?
Servem para deixar o código mais limpo e expressivo, evitando a criação de classes
utilitárias cheias de métodos estáticos (como era comum em Java). Com elas, dá pra
adicionar funcionalidades até mesmo em classes de bibliotecas externas que você não
tem acesso para editar.

### Como é normalmente utilizado?
São muito usadas em projetos Android para estender classes do SDK, como adicionar
métodos de formatação a `String`, simplificar operações em `View`, ou criar helpers
para tipos numéricos. A sintaxe é simples: `fun NomeDaClasse.nomeDaFuncao()`.

### Exemplo de código em Kotlin

```kotlin
fun String.isPalindromo(): Boolean {
    return this == this.reversed()
}

fun main() {
    println("arara".isPalindromo()) // true
    println("kotlin".isPalindromo()) // false
}
```

---

# Conceito escolhido:
## Data Classes

### O que é?
Data Class é um tipo especial de classe do Kotlin criada especificamente para armazenar
dados. Ao declará-la com a palavra-chave `data`, o compilador gera automaticamente os
métodos `equals()`, `hashCode()`, `toString()` e `copy()`, poupando muito código
repetitivo.

### Pra que serve?
Serve para modelar objetos que representam apenas dados como respostas de uma API,
registros de banco de dados ou entidades de domínio de forma enxuta. O método `copy()`
é especialmente útil para criar variações de um objeto sem alterar o original, o que
casa bem com o paradigma de imutabilidade.

### Como é normalmente utilizado?
São amplamente usadas em arquiteturas como MVVM e Clean Architecture para representar
modelos de dados. Também funcionam muito bem com o operador `when` e com listas, já
que o `equals()` automático torna a comparação de objetos muito mais confiável.

### Exemplo de código em Kotlin

```kotlin
data class Usuario(val nome: String, val idade: Int)

fun main() {
    val user1 = Usuario("Matheus", 21)
    val user2 = user1.copy(idade = 22)

    println(user1) // Usuario(nome=Matheus, idade=23)
    println(user2) // Usuario(nome=Matheus, idade=24)
    println(user1 == user2) // false
}
```