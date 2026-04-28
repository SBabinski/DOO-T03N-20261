package fag;

import java.util.ArrayList;

public class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    Endereco endereco;

    ArrayList<Vendedor> vendedores = new ArrayList<>();
    ArrayList<Gerente> gerentes = new ArrayList<>();
    ArrayList<Cliente> clientes = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial,
                String cnpj, Endereco endereco) {

        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;

        vendedores.add(new Vendedor("Joao",30,new Endereco("PR","Cascavel","Centro","1","Casa"),this,1800));
        gerentes.add(new Gerente("Carlos",40,new Endereco("PR","Cascavel","Centro","2","Sala"),this,3500));
        clientes.add(new Cliente("Maria",25,new Endereco("PR","Cascavel","Centro","3","Casa")));
    }

    public void mostrarDetalhes() {
        System.out.println(nomeFantasia);
        endereco.apresentarLogradouro();
    }
}