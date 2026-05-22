package fag.objetos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import fag.objetos.Item;

public class Pedido {
	
	private int id;
	private LocalDate dataCriacao;
	private LocalDate dataPagamento;
	private LocalDate DataVencimentoReserva;
	private String cliente;
	private String vendedor;
	private String loja;
	
	public Pedido() {
		
	}
	
	public Pedido(int id, String cliente, String vendedor, 
			String loja) {
		setId(id);
		setCliente(cliente);
		setVendedor(vendedor);
		setLoja(loja);
	//	setDataCriacao(dataCriacao);
		//setDataPagamento(dataPagamento);
		//setDataVencimentoReserva(DataVencimentoReserva);
		
		
	}
	
	private List<Item> itens = new ArrayList<>();
	
	public static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public int getId() {
		return id;
	}
	
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
	public LocalDate getDataPagamento() {
		return dataPagamento;
	}
	
	public LocalDate getDataVencimentoReserva() {
		return DataVencimentoReserva;
	}
	
	public String getCliente() {
		return cliente;
	}
	
	public String getVendedor() {
		return vendedor;
	}
	
	public String getLoja() {
		return loja;
	}
	
	public List<Item> getItens() {
		return itens;
	}


	public void setId(int id) {
		this.id = id;
	}

	

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = LocalDate.parse(dataCriacao, FORMATO);
	}

	
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = LocalDate.parse(dataPagamento, FORMATO);
	}

	

	public void setDataVencimentoReserva(String dataVencimentoReserva) {
		this.DataVencimentoReserva = LocalDate.parse(dataVencimentoReserva, FORMATO);
	}

	

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	

	public void setLoja(String loja) {
		this.loja = loja;
	}

	

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	//metodos
	
	public void adicionarItem(Item item) {
		itens.add(item);
	}
	
	public double calcularValorTotal() {
		double total=0;
		for(Item item: itens) {
			total+= item.getValor();
		}
	//	System.out.printf("\nO total do pedido e R$: %.2f", total);
		return total;
		
	}
	
	public void gerarDescricaoVenda() {
		System.out.println("\nData decriacao do pedido: " + dataCriacao.format(FORMATO));
		System.out.printf("Valor total do pedido R$: %.2f", calcularValorTotal());
		
	}


}
