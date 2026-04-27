package Objetos;

import java.util.ArrayList;
import java.util.List;

public class DadosIniciais {

    public static List<Loja> carregar() {
        List<Loja> lojas = new ArrayList<>();

        Loja loja1 = new Loja("My Plant Centro", "My Plant Ltda", "12.345.678/0001-90",
                new Endereco("PR", "Maringá", "Centro", "100", "Loja Térreo"));

        loja1.adicionarVendedor(new Vendedor("Carlos Silva", 35,
                new Endereco("PR", "Maringá", "Centro", "10"), loja1, 2500.00));
        loja1.adicionarVendedor(new Vendedor("Ana Souza", 28,
                new Endereco("PR", "Maringá", "Zona Norte", "200"), loja1, 2200.00));
        loja1.adicionarGerente(new Gerente("Fernanda Rocha", 40,
                new Endereco("PR", "Maringá", "Centro", "15"), loja1, 5000.00));
        loja1.adicionarCliente(new Cliente("João Pereira", 42,
                new Endereco("PR", "Maringá", "Centro", "10")));
        loja1.adicionarCliente(new Cliente("Maria Lima", 30,
                new Endereco("PR", "Maringá", "Zona Sul", "20")));
        lojas.add(loja1);

        Loja loja2 = new Loja("My Plant Zona Sul", "My Plant Sul Ltda", "98.765.432/0001-10",
                new Endereco("PR", "Maringá", "Zona Sul", "300"));

        loja2.adicionarVendedor(new Vendedor("Bruno Alves", 31,
                new Endereco("PR", "Maringá", "Zona Sul", "300"), loja2, 2300.00));
        loja2.adicionarCliente(new Cliente("Pedro Costa", 25,
                new Endereco("PR", "Maringá", "Zona Sul", "30")));
        lojas.add(loja2);

        return lojas;
    }
}
