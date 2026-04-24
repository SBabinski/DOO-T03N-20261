package uteis;

import Entities.Cliente;
import Entities.Loja;
import Entities.Vendedor;
import repository.LojaRepository;

public class Uteis {
    
    public static void populaDados(LojaRepository lojas) {
        
        // ===== LOJA 1 =====
        Loja loja1 = new Loja("My Plant Centro", "MP LTDA", "111111111",
                "Cascavel", "Centro", "Rua A");
        
        Vendedor v1 = new Vendedor("João", 30, loja1,
                "Cascavel", "Centro", "Rua 1", 2000);
        v1.depositaSalario(2000);
        v1.depositaSalario(2100);
        v1.depositaSalario(2200);
        
        Vendedor v2 = new Vendedor("Maria", 28, loja1,
                "Cascavel", "Centro", "Rua 2", 2500);
        v2.depositaSalario(2500);
        v2.depositaSalario(2600);
        v2.depositaSalario(2700);
        
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
        v3.depositaSalario(2200);
        v3.depositaSalario(2300);
        v3.depositaSalario(2400);
        
        loja2.getVendedores().adicionaVendedor(v3);
        
        Cliente c3 = new Cliente("Fernanda", 29, "Toledo", "Jardim", "Rua Z");
        loja2.getClientes().adicionacliente(c3);
        
        
        // ===== LOJA 3 =====
        Loja loja3 = new Loja("My Plant Sul", "MP LTDA", "333333333",
                "Foz", "Centro", "Rua C");
        
        Vendedor v4 = new Vendedor("Lucas", 27, loja3,
                "Foz", "Centro", "Rua 4", 2100);
        v4.depositaSalario(2100);
        v4.depositaSalario(2150);
        v4.depositaSalario(2200);
        
        loja3.getVendedores().adicionaVendedor(v4);
        
        Cliente c4 = new Cliente("Juliana", 31, "Foz", "Centro", "Rua W");
        loja3.getClientes().adicionacliente(c4);
        
        
        // ===== ADICIONA NO REPOSITORY =====
        lojas.adicionaLoja(loja1);
        lojas.adicionaLoja(loja2);
        lojas.adicionaLoja(loja3);
    }
}
