# Conceito 1 — Extension Functions

**Nome:** João Pedro Doná Ferreira  
**Conceito escolhido:** Extension Functions (Funções de Extensão)  
**Timestamp do vídeo que menciona o conceito:** [4:09](https://www.youtube.com/watch?v=BsfXZjKLT9A&t=249)

---

## O que é?

Basicamente, extension functions são funções que você "gruda" em uma classe sem precisar mexer no código dela e sem precisar fazer herança. Tipo, imagina que você quer adicionar um método novo na classe `String` do Kotlin — você não tem como editar o código interno dela, mas com extension functions você consegue fazer isso do lado de fora mesmo.

Em Java a gente aprendeu que pra adicionar comportamento numa classe você ou herda ela ou cria aquela classe `StringUtils` com métodos estáticos. Em Kotlin isso sumiu, por conta desse recurso.

---

## Pra que serve?

Serve principalmente pra três coisas:

- Adicionar métodos em classes que você não tem acesso pra editar (biblioteca externa, classes nativas, etc.)
- Deixar o código mais legível, porque ao invés de `StringUtils.verificarPalindromo(str)` você chama `str.isPalindromo()` diretamente
- Evitar criar um monte de classe helper só pra guardar funçõezinhas avulsas

---

## Como é normalmente utilizado?

Você escreve uma função normal, mas coloca o nome da classe seguido de ponto antes do nome da função. Dentro dela, o `this` representa o objeto que chamou a função:

```
fun NomeDaClasse.nomeDaFuncao(parametros): TipoRetorno {
    // this = o objeto em questão
}
```

Daí é só chamar como se fosse um método normal da classe. Quem for usar nem precisa saber que é uma extensão.

---

## Exemplo de código

```kotlin
// Extensão na classe String pra verificar palíndromo
fun String.isPalindromo(): Boolean {
    val limpo = this.lowercase().filter { it.isLetter() }
    return limpo == limpo.reversed()
}

// Extensão na classe Int só pra exemplificar
fun Int.vezesDois(): Int {
    return this * 2
}

fun main() {
    val palavra = "Arara"
    println("'$palavra' é palíndromo? ${palavra.isPalindromo()}") // true

    val frase = "Socorram me, subi no onibus em Marrocos"
    println("'$frase' é palíndromo? ${frase.isPalindromo()}") // true

    val numero = 7
    println("$numero vezes dois = ${numero.vezesDois()}") // 14
}
```

**Saída esperada:**
```
'Arara' é palíndromo? true
'Socorram me, subi no onibus em Marrocos' é palíndromo? true
7 vezes dois = 14
```

O `isPalindromo()` é chamado direto na `String` como se sempre tivesse sido um método dela — mas a classe original não foi tocada em momento nenhum.

---
