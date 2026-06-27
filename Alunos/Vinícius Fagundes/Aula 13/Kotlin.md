# Atividade Extra — Recursos da Linguagem Kotlin

**Nome:** Vinícius Fagundes



---

## 1. Classes seladas (*sealed classes*)

**Conceito escolhido:** Classes seladas (`sealed class`)  
**Timestamp do vídeo que menciona o conceito:** aproximadamente **1:20**

### O que é?

Uma *sealed class* é uma classe usada quando existem poucas possibilidades conhecidas de tipos ou estados. Ao criar uma classe selada, o programador define quais classes podem herdar dela. Assim, o compilador sabe quais são todas as alternativas existentes.

### Para que serve?

Ela é útil para representar situações com estados bem definidos, como o resultado de um pagamento, o estado de uma tela ou a resposta de uma requisição. Um benefício importante é poder usar `when` para tratar todos os casos, com menos chance de esquecer uma possibilidade.

### Como é normalmente utilizada?

As classes que representam cada estado ficam dentro da hierarquia da classe selada. Em seguida, uma expressão `when` verifica qual tipo de objeto foi recebido e executa a ação correspondente.

### Exemplo de código

```kotlin
sealed class StatusPedido {
    data object Pendente : StatusPedido()
    data class Pago(val codigo: String) : StatusPedido()
    data class Recusado(val motivo: String) : StatusPedido()
}

fun mostrarStatus(status: StatusPedido): String {
    return when (status) {
        StatusPedido.Pendente -> "Aguardando pagamento"
        is StatusPedido.Pago -> "Pagamento aprovado: ${status.codigo}"
        is StatusPedido.Recusado -> "Pagamento recusado: ${status.motivo}"
    }
}
```

No exemplo, um pedido só pode estar pendente, pago ou recusado. Como todos os estados estão previstos, o `when` pode tratar cada um deles de maneira explícita.

---

## 2. Delegação de classes (*delegation*)

**Conceito escolhido:** Delegação de classes (`delegation`)  
**Timestamp do vídeo que menciona o conceito:** aproximadamente **1:35**

### O que é?

Delegação é uma forma de reutilizar o comportamento de outro objeto sem precisar usar herança diretamente. Em Kotlin, uma classe pode implementar uma interface e encaminhar os seus métodos para outro objeto com a palavra-chave `by`.

### Para que serve?

Esse recurso ajuda a reaproveitar código e separar responsabilidades. Em vez de uma classe herdar muitas funções que talvez não precise, ela pode delegar uma tarefa específica para um objeto especializado naquela tarefa.

### Como é normalmente utilizada?

Primeiro, é criada uma interface com os comportamentos desejados. Depois, uma classe implementa essa interface usando `by` e recebe um objeto que já possui a implementação. Os métodos públicos da interface são encaminhados automaticamente para esse objeto.

### Exemplo de código

```kotlin
interface Impressora {
    fun imprimir(texto: String)
}

class ImpressoraConsole : Impressora {
    override fun imprimir(texto: String) {
        println(texto)
    }
}

class Relatorio(
    private val impressora: Impressora
) : Impressora by impressora

fun main() {
    val relatorio = Relatorio(ImpressoraConsole())

    relatorio.imprimir("Relatório gerado com sucesso!")
}
```

Nesse caso, `Relatorio` implementa a interface `Impressora`, mas não precisa repetir o método `imprimir`. A classe usa o objeto `ImpressoraConsole` para executar essa responsabilidade.

---

## Referências

- Vídeo analisado: `Nanowar Of Steel - Kotlin (Official Power Point Video)`.
- Documentação oficial de Kotlin sobre classes seladas e delegação.
