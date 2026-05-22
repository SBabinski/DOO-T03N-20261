package fag;
import java.util.ArrayList;
import java.util.List;

import fag.objetos.Cliente;
import fag.objetos.Loja;
import fag.objetos.Vendedor;
import fag.objetos.Endereco;
import fag.objetos.Gerente;


public class InicializadorDados {
	
	public static List<Loja> criarLojas(){
		
		//enderecos
		Endereco endV1 = new Endereco("PR", "Cascavel", "Centro", "Rua das Flores", "123", "");
        Endereco endV2 = new Endereco("PR", "Medianeira", "Centro", "Rua Piauí", "45", "");
        Endereco endV3 = new Endereco("PR", "Cascavel", "Centro", "Rua das Flores", "200", "");
        Endereco endV4 = new Endereco("PR", "Medianeira", "Centro", "Rua Piauí", "88", "");
        Endereco endV5 = new Endereco("PR", "Ceu azul", "Centro", "Rua 2", "4556", "");
        Endereco endV6 = new Endereco("PR", "Sao miguel", "Centro", "Rua 7", "6667", "");
        
        Endereco endC1 = new Endereco("PR", "Foz", "Centro", "Rua Amapá", "10", "");
        Endereco endC2 = new Endereco("PR", "Matelândia", "Centro", "Rua Principal", "22", "");
        Endereco endC3 = new Endereco("PR", "Foz", "Centro", "Rua Amapá", "55", "");
        Endereco endC4 = new Endereco("PR", "Matelândia", "Centro", "Rua Principal", "99", "");

        Endereco endLoja1 = new Endereco("PR", "Cascavel", "Centro", "Rua Principal", "100", "");
        Endereco endLoja2 = new Endereco("PR", "Cascavel", "Centro", "Rua Principal", "200", "");
		
		//loja1
		
		Vendedor v1 = new Vendedor("Joao","Loja A", endV1, 18, 2000);
		v1.adicionarSalarioRecebido(1600);
		v1.adicionarSalarioRecebido(2400);
		v1.adicionarSalarioRecebido(3000);
		
		Vendedor v2 = new Vendedor("Maria", "Loja A", endV2, 20, 2500);
		v2.adicionarSalarioRecebido(2000);
		v2.adicionarSalarioRecebido(2800);
		v2.adicionarSalarioRecebido(3200);
		
		Gerente g1 = new Gerente("afonso", "Loja A", endV5, 55, 4000);
		g1.adicionarSalarioRecebido(3890);
		g1.adicionarSalarioRecebido(4500);
		g1.adicionarSalarioRecebido(5000);
		
		Cliente c1 = new Cliente("Jonas", 18, endC1);
		Cliente c2 = new Cliente("joana", 20, endC2);
		
		Loja loja1 = new Loja("Loja A", "My plant A", "11.123.456/0001-00", endLoja1);
		loja1.adicionarVendedor(v1);
		loja1.adicionarVendedor(v2);
		loja1.adicionarGerente(g1);

		loja1.adicionarCliente(c1);
		loja1.adicionarCliente(c2);
		
		
		
		//loja 2
		
		Vendedor v3 = new Vendedor("Mario", "Loja B", endV3, 18, 2000);
		v3.adicionarSalarioRecebido(2200);
		v3.adicionarSalarioRecebido(2400);
		v3.adicionarSalarioRecebido(3100);
		
		Vendedor v4 = new Vendedor("Mariaana","Loja B", endV4, 20, 2500);
		v4.adicionarSalarioRecebido(2500);
		v4.adicionarSalarioRecebido(2600);
		v4.adicionarSalarioRecebido(3200);
		
		Gerente g2 = new Gerente("afrodite", "Loja B", endV6, 50, 4200);
		g2.adicionarSalarioRecebido(4000);
		g2.adicionarSalarioRecebido(4500);
		g2.adicionarSalarioRecebido(5700);
		
		Cliente c3 = new Cliente("Jonas", 18, endC3);
		Cliente c4 = new Cliente("joaninha", 20, endC4);
		
		Loja loja2 = new Loja("Loja B", "My plant B", "12.123.456/0001-11", endLoja2);
		loja2.adicionarVendedor(v3);
		loja2.adicionarVendedor(v4);
		loja2.adicionarGerente(g2);
		
		loja2.adicionarCliente(c3);
		loja2.adicionarCliente(c4);
		
		
		
		List<Loja> lojas = new ArrayList<>();
		lojas.add(loja1);
		lojas.add(loja2);
		
	return lojas;	
		
		
		
	}

}
