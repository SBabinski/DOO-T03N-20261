package uteis;

import entities.*;
import entities.enums.TipoENUM;
import repository.LojaRepository;
import repository.PedidoRepository;

import java.util.ArrayList;

public class Uteis {
    
    public static void populaDados(LojaRepository lojas, PedidoRepository pedidos) {
        
        // ===== LOJA 1 =====
        Loja loja1 = new Loja("My Plant Centro", "MP LTDA", "111111111",
                "Cascavel", "Centro", "Rua A");
        
        Vendedor v1 = new Vendedor("João", 30, loja1,
                "Cascavel", "Centro", "Rua 1", 2000);
        v1.setSalario(2000);
        v1.setSalario(2100);
        v1.setSalario(2200);
        
        Vendedor v2 = new Vendedor("Maria", 28, loja1,
                "Cascavel", "Centro", "Rua 2", 2500);
        v2.setSalario(2500);
        v2.setSalario(2600);
        v2.setSalario(2700);
        
        Gerente g1 = new Gerente("Roberto", 45, loja1,
                "Cascavel", "Centro", "Rua A", 5000);
        g1.setSalario(5000);
        g1.setSalario(5200);
        g1.setSalario(5100);
        
        loja1.getVendedores().adicionaVendedor(v1);
        loja1.getVendedores().adicionaVendedor(v2);
        
        Cliente c1 = new Cliente("Carlos", 40, "Cascavel", "Centro", "Rua X");
        Cliente c2 = new Cliente("Ana", 35, "Cascavel", "Centro", "Rua Y");
        
        loja1.getClientes().adicionacliente(c1);
        loja1.getClientes().adicionacliente(c2);
        
        
        // ===== LOJA 2 =====
        Loja loja2 = new Loja("My Plant Norte", "MP LTDA", "222222222",
                "Toledo", "Jardim", "Rua B");
        
        Vendedor v3 = new Vendedor("Pedro", 32, loja2,
                "Toledo", "Jardim", "Rua 3", 2200);
        v3.setSalario(2200);
        v3.setSalario(2300);
        v3.setSalario(2400);
        
        Gerente g2 = new Gerente("Claudia", 38, loja2,
                "Toledo", "Jardim", "Rua B", 4800);
        g2.setSalario(4800);
        g2.setSalario(4900);
        g2.setSalario(5000);
        
        loja2.getVendedores().adicionaVendedor(v3);
        
        Cliente c3 = new Cliente("Fernanda", 29, "Toledo", "Jardim", "Rua Z");
        loja2.getClientes().adicionacliente(c3);
        
        
        // ===== LOJA 3 =====
        Loja loja3 = new Loja("My Plant Sul", "MP LTDA", "333333333",
                "Foz", "Centro", "Rua C");
        
        Vendedor v4 = new Vendedor("Lucas", 27, loja3,
                "Foz", "Centro", "Rua 4", 2100);
        v4.setSalario(2100);
        v4.setSalario(2150);
        v4.setSalario(2200);
        
        Gerente g3 = new Gerente("Patricia", 41, loja3,
                "Foz", "Centro", "Rua C", 4600);
        g3.setSalario(4600);
        g3.setSalario(4700);
        g3.setSalario(4650);
        
        loja3.getVendedores().adicionaVendedor(v4);
        
        Cliente c4 = new Cliente("Juliana", 31, "Foz", "Centro", "Rua W");
        loja3.getClientes().adicionacliente(c4);
        
        
        // ===== ADICIONA LOJAS NO REPOSITORY =====
        lojas.adicionaLoja(loja1);
        lojas.adicionaLoja(loja2);
        lojas.adicionaLoja(loja3);
        
        
        // ===== PEDIDOS FAKE =====
        ArrayList<Item> itens1 = new ArrayList<>();
        itens1.add(new Item("Rosa Vermelha", TipoENUM.FLOR, 15.00));
        itens1.add(new Item("Samambaia", TipoENUM.MUDA, 25.00));
        
        pedidos.adicionaPedido(new Pedido(c1, v1, loja1, itens1));
        
        ArrayList<Item> itens2 = new ArrayList<>();
        itens2.add(new Item("Semente de Girassol", TipoENUM.SEMENTE, 8.50));
        itens2.add(new Item("Orquídea", TipoENUM.FLOR, 45.00));
        itens2.add(new Item("Cacto Muda", TipoENUM.MUDA, 12.00));
        
        pedidos.adicionaPedido(new Pedido(c3, v3, loja2, itens2));
    }
}