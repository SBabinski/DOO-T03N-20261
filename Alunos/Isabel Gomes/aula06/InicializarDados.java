package fag;
import java.util.ArrayList;
import java.util.List;

import fag.objeto.Cliente;
import fag.objeto.Loja;
import fag.objeto.Vendedor;

public class InicializarDados {
	
	public static List<Loja> criarLojas(){
		
		//loja1
		
		Vendedor v1 = new Vendedor("Joao", "Cascavel", "Centro", "R. das flores", "Loja A", 18, 2000);
		v1.adicionarSalarioRecebido(1600);
		v1.adicionarSalarioRecebido(2400);
		v1.adicionarSalarioRecebido(3000);
		
		Vendedor v2 = new Vendedor("Maria", "Medianeira", "centro", "r. piaui", "Loja A", 20, 2500);
		v2.adicionarSalarioRecebido(2000);
		v2.adicionarSalarioRecebido(2800);
		v2.adicionarSalarioRecebido(3200);
		
		Cliente c1 = new Cliente("Jonas", 18, "Foz", "Centro", "R. amapa");
		Cliente c2 = new Cliente("joana", 20, "Matelandia", "centro", "R. principal");
		
		Loja loja1 = new Loja("Loja A", "My plant A", "11.123.456/0001-00", "Cascavel", "centro", "R principal");
		loja1.adicionarVendedor(v1);
		loja1.adicionarVendedor(v2);
		
		loja1.adicionarCliente(c1);
		loja1.adicionarCliente(c2);
		
		
		
		//loja 2
		
		Vendedor v3 = new Vendedor("Mario", "Cascavel", "Centro", "R. das flores", "Loja B", 18, 2000);
		v3.adicionarSalarioRecebido(2200);
		v3.adicionarSalarioRecebido(2400);
		v3.adicionarSalarioRecebido(3100);
		
		Vendedor v4 = new Vendedor("Mariaana", "Medianeira", "centro", "r. piaui", "Loja B", 20, 2500);
		v4.adicionarSalarioRecebido(2500);
		v4.adicionarSalarioRecebido(2600);
		v4.adicionarSalarioRecebido(3200);
		
		Cliente c3 = new Cliente("Jonas", 18, "Foz", "Centro", "R. amapa");
		Cliente c4 = new Cliente("joana", 20, "Matelandia", "centro", "R. principal");
		
		Loja loja2 = new Loja("Loja B", "My plant B", "12.123.456/0001-11", "Cascavel", "centro", "R principal");
		loja2.adicionarVendedor(v3);
		loja2.adicionarVendedor(v4);
		
		loja2.adicionarCliente(c3);
		loja2.adicionarCliente(c4);
		
		
		
		List<Loja> lojas = new ArrayList<>();
		lojas.add(loja1);
		lojas.add(loja2);
		
	return lojas;	
		
		
		
	}

}
