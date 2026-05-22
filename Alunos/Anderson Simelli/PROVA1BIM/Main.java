package Imobiliaria;

import java.util.Date;
import java.util.Scanner;

public class Main {

    static Contrato[] contratos = new Contrato[10];
    static int totalContratos = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcao;

        Inquilino inquilino = null;
        Imovel imovel = null;

        do {
            System.out.println("\n===== IMOBILIÁRIA =====");
            System.out.println("1 - Cadastrar Inquilino");
            System.out.println("2 - Cadastrar Imóvel");
            System.out.println("3 - Criar Contrato");
            System.out.println("4 - Encerrar Contrato");
            System.out.println("5 - Listar Contratos Ativos");
            System.out.println("6 - Demonstração");
            System.out.println("7 - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();

                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();

                    inquilino = new Inquilino(nome, cpf, tel);
                    System.out.println("Inquilino cadastrado!");
                    break;

                case 2:
                    System.out.println("1 - Apartamento | 2 - Casa");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Endereço: ");
                    String endereco = sc.nextLine();

                    System.out.print("Valor mensal: ");
                    double valor = sc.nextDouble();

                    if (tipo == 1) {
                        System.out.print("Andar: ");
                        int andar = sc.nextInt();
                        imovel = new Apartamento(endereco, valor, andar);
                    } else {
                        System.out.print("Tem quintal (true/false): ");
                        boolean quintal = sc.nextBoolean();
                        imovel = new Casa(endereco, valor, quintal);
                    }

                    System.out.println("Imóvel cadastrado!");
                    break;

                case 3:
                    if (inquilino == null || imovel == null) {
                        System.out.println("Cadastre inquilino e imóvel primeiro!");
                        break;
                    }

                    if (totalContratos < 10) {

                        Date inicio = new Date();
                        Date fim = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 90)); // 90 dias

                        contratos[totalContratos++] = new Contrato(inquilino, imovel, inicio, fim);
                        System.out.println("Contrato criado!");
                    } else {
                        System.out.println("Limite de contratos atingido!");
                    }
                    break;

                case 4:
                    System.out.print("Índice do contrato: ");
                    int i = sc.nextInt();

                    if (i < totalContratos && contratos[i] != null) {
                        contratos[i].encerrar();
                        System.out.println("Contrato encerrado!");
                    }
                    break;

                case 5:
                    System.out.println("\n--- CONTRATOS ATIVOS ---");
                    for (int j = 0; j < totalContratos; j++) {
                        if (!contratos[j].encerrado) {
                            contratos[j].mostrar();
                        }
                    }
                    break;

                case 6:
                    demonstracao();
                    break;
            }

        } while (opcao != 7);

        sc.close();
    }

    // ================= DEMONSTRAÇÃO =================

    public static void demonstracao() {

        Inquilino i1 = new Inquilino("João", "123", "9999");
        Inquilino i2 = new Inquilino("Maria", "456", "8888");

        Imovel ap = new Apartamento("Centro", 1000, 5);
        Imovel casa = new Casa("Bairro", 1500, true);

        Date inicio = new Date();
        Date fim = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 60));

        Contrato c1 = new Contrato(i1, ap, inicio, fim);
        c1.encerrar();

        Contrato c2 = new Contrato(i2, casa, inicio, fim);

        contratos[0] = c1;
        contratos[1] = c2;
        totalContratos = 2;

        System.out.println("\n--- CONTRATOS ATIVOS ---");

        for (int i = 0; i < totalContratos; i++) {
            if (!contratos[i].encerrado) {
                contratos[i].mostrar();
            }
        }
    }
}