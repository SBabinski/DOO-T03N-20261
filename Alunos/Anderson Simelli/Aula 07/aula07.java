package aprimorar;

import java.util.Date;

//================= MAIN =================

public class aula07 {

 public static void main(String[] args) {

     Endereco endereco = new Endereco("PR", "Corbélia", "Centro", 123, "Loja");

     Loja loja = new Loja("My Plant", "123456789", endereco);

     Vendedor vendedor = new Vendedor("João", 30, loja, endereco, 2000);
     Cliente cliente = new Cliente("Maria", 25, endereco);

     Item item1 = new Item(1, "Planta A", "Ornamental", 50);
     Item item2 = new Item(2, "Planta B", "Exótica", 100);

     Item[] itens = {item1, item2};

     ProcessaPedido processador = new ProcessaPedido();

     Pedido pedido = processador.processar(
             1,
             new Date(),
             new Date(),
             new Date(System.currentTimeMillis() + 10000000),
             cliente,
             vendedor,
             loja,
             itens
     );

     pedido.gerarDescricaoVenda();
 }
}

//================= ENDERECO =================

class Endereco {

 String estado, cidade, bairro;
 int numero;
 String complemento;

 public Endereco(String estado, String cidade, String bairro, int numero, String complemento) {
     this.estado = estado;
     this.cidade = cidade;
     this.bairro = bairro;
     this.numero = numero;
     this.complemento = complemento;
 }

 public void apresentarLogradouro() {
     System.out.println(cidade + " - " + bairro + ", Nº " + numero + " (" + complemento + ")");
 }
}

//================= PESSOA =================

class Pessoa {

 String nome;
 int idade;
 Endereco endereco;

 public Pessoa(String nome, int idade, Endereco endereco) {
     this.nome = nome;
     this.idade = idade;
     this.endereco = endereco;
 }

 public void apresentarse() {
     System.out.println("Nome: " + nome + " | Idade: " + idade);
 }
}

//================= VENDEDOR =================

class Vendedor extends Pessoa {

 Loja loja;
 double salarioBase;
 double[] salarioRecebido = {2000, 2100, 2200};

 public Vendedor(String nome, int idade, Loja loja, Endereco endereco, double salarioBase) {
     super(nome, idade, endereco);
     this.loja = loja;
     this.salarioBase = salarioBase;
 }

 public void apresentarse() {
     System.out.println("Vendedor: " + nome + " | Idade: " + idade + " | Loja: " + loja.nomeFantasia);
 }

 public double calcularMedia() {
     double soma = 0;
     for (double s : salarioRecebido) soma += s;
     return soma / salarioRecebido.length;
 }

 public double calcularBonus() {
     return salarioBase * 0.2;
 }
}

//================= GERENTE =================

class Gerente extends Vendedor {

 public Gerente(String nome, int idade, Loja loja, Endereco endereco, double salarioBase) {
     super(nome, idade, loja, endereco, salarioBase);
 }


 public double calcularBonus() {
     return salarioBase * 0.35;
 }
}

//================= CLIENTE =================

class Cliente extends Pessoa {

 public Cliente(String nome, int idade, Endereco endereco) {
     super(nome, idade, endereco);
 }
}

//================= LOJA =================

class Loja {

 String nomeFantasia;
 String cnpj;
 Endereco endereco;

 public Loja(String nomeFantasia, String cnpj, Endereco endereco) {
     this.nomeFantasia = nomeFantasia;
     this.cnpj = cnpj;
     this.endereco = endereco;
 }
}

//================= ITEM =================

class Item {

 int id;
 String nome, tipo;
 double valor;

 public Item(int id, String nome, String tipo, double valor) {
     this.id = id;
     this.nome = nome;
     this.tipo = tipo;
     this.valor = valor;
 }

 public void gerarDescricao() {
     System.out.println("ID: " + id + " | Nome: " + nome + " | Tipo: " + tipo + " | Valor: " + valor);
 }
}

//================= PEDIDO =================

class Pedido {

 int id;
 Date dataCriacao, dataPagamento, dataVencimentoReserva;
 Cliente cliente;
 Vendedor vendedor;
 Loja loja;
 Item[] itens;

 public Pedido(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
               Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens) {

     this.id = id;
     this.dataCriacao = dataCriacao;
     this.dataPagamento = dataPagamento;
     this.dataVencimentoReserva = dataVencimentoReserva;
     this.cliente = cliente;
     this.vendedor = vendedor;
     this.loja = loja;
     this.itens = itens;
 }

 public double calcularValorTotal() {
     double total = 0;
     for (Item item : itens) {
         total += item.valor;
     }
     return total;
 }

 public void gerarDescricaoVenda() {

	    System.out.println("\n===== RESUMO DO PEDIDO =====");

	    System.out.println("Data: " + dataCriacao);
	    System.out.println("Cliente: " + cliente.nome);
	    System.out.println("Vendedor: " + vendedor.nome);
	    System.out.println("Loja: " + loja.nomeFantasia);

	    System.out.println("\nItens do pedido:");
	    for (Item item : itens) {
	        item.gerarDescricao();
	    }

	    System.out.println("\nTotal do pedido: R$ " + calcularValorTotal());
	}
}

//================= PROCESSA PEDIDO =================

class ProcessaPedido {

 public Pedido processar(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
                         Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens) {

     if (confirmarPagamento(dataVencimentoReserva)) {
         System.out.println("Pagamento confirmado!");
         return new Pedido(id, dataCriacao, dataPagamento, dataVencimentoReserva,
                 cliente, vendedor, loja, itens);
     } else {
         System.out.println("Reserva vencida!");
         return null;
     }
 }

 private boolean confirmarPagamento(Date dataVencimentoReserva) {
     Date hoje = new Date();
     return hoje.before(dataVencimentoReserva);
 }
}