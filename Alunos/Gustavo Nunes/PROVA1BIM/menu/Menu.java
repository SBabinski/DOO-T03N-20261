package menu;

import entities.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Inquilino> inquilinos = new ArrayList<>();
    public static ArrayList<Imovel> imoveis = new ArrayList<>();
    public static Imobiliaria imobiliaria = new Imobiliaria();
    
    public static void inicio() {
        
        int op;
        
        do {
            op = mostraMenu();
            direcionaUzuario(op);
        } while (op != 0);
        
        scanner.close();
    }
    
    private static int mostraMenu() {
        System.out.println("\n================ IMOBILIÁRIA ================");
        System.out.println("[1] Cadastrar Inquilino");
        System.out.println("[2] Cadastrar Imóvel");
        System.out.println("[3] Cadastrar Contrato");
        System.out.println("[4] Encerrar Contrato");
        System.out.println("[5] Listar Contratos Ativos");
        System.out.println("[6] Demonstração");
        System.out.println("[0] Sair");
        System.out.println("============================================");
        System.out.print("Escolha uma opção: ");
        
        return scanner.nextInt();
    }
    
    private static void direcionaUzuario(int op) {
        switch (op) {
            
            case 1:
                cadastrarInquilino();
                break;
            
            case 2:
                cadastrarImovel();
                break;
            
            case 3:
                cadastrarContrato();
                break;
            
            case 4:
                encerrarContrato();
                break;
            
            case 5:
                listarContratosAtivos();
                break;
            
            case 6:
                demonstracao();
                break;
            
            case 0:
                System.out.println("\nSAINDO...");
                break;
            
            default:
                System.out.println("\nOpção inválida!");
                break;
        }
    }
    
    private static void cadastrarInquilino() {
        scanner.nextLine();
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        Inquilino novoInquilino = new Inquilino(nome, cpf, telefone);
        
        inquilinos.add(novoInquilino);
        
        System.out.println("\nInquilino cadastrado com sucesso!");
    }
    
    private static void cadastrarImovel() {
        
        scanner.nextLine(); // limpar buffer
        
        System.out.println("Tipo de imóvel:");
        System.out.println("[1] Casa");
        System.out.println("[2] Apartamento");
        int tipo = scanner.nextInt();
        
        scanner.nextLine(); // limpar buffer
        
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        
        System.out.print("Valor do aluguel: ");
        double aluguel = scanner.nextDouble();
        
        Imovel novoImovel = null;
        
        if (tipo == 1) {
            System.out.print("Possui quintal? (true/false): ");
            boolean quintal = scanner.nextBoolean();
            
            novoImovel = new Casa(endereco, aluguel, quintal);
            
        } else if (tipo == 2) {
            System.out.print("Andar: ");
            int andar = scanner.nextInt();
            
            novoImovel = new Apartamento(endereco, aluguel, andar);
            
        } else {
            System.out.println("Tipo inválido!");
            return;
        }
        
        imoveis.add(novoImovel);
        
        System.out.println("\nImóvel cadastrado com sucesso!");
    }
    
    private static void cadastrarContrato() {
        
        if (inquilinos.isEmpty() || imoveis.isEmpty()) {
            System.out.println("Cadastre pelo menos 1 inquilino e 1 imóvel antes.");
            return;
        }
        
        System.out.println("\n=== INQUILINOS ===");
        for (int i = 0; i < inquilinos.size(); i++) {
            System.out.println("[" + i + "] " + inquilinos.get(i));
        }
        
        System.out.print("Escolha o inquilino: ");
        int iInq = scanner.nextInt();
        Inquilino inquilino = inquilinos.get(iInq);
        
        System.out.println("\n=== IMÓVEIS ===");
        for (int i = 0; i < imoveis.size(); i++) {
            System.out.println("[" + i + "] " + imoveis.get(i));
        }
        
        System.out.print("Escolha o imóvel: ");
        int iImo = scanner.nextInt();
        Imovel imovel = imoveis.get(iImo);
        
        System.out.print("Quantidade de meses: ");
        int meses = scanner.nextInt();
        
        Contrato contrato = new Contrato(inquilino, imovel, meses);
        
        imobiliaria.adicionaContrato(contrato);
        
        System.out.println("\nContrato cadastrado com sucesso!");
    }
    
    private static void encerrarContrato() {
        ArrayList<Contrato> contratos = imobiliaria.getContratos();
        
        if (contratos.isEmpty()) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }
        
        System.out.println("\n=== CONTRATOS ATIVOS ===");
        boolean encontrou = false;
        
        for (int i = 0; i < contratos.size(); i++) {
            if (!contratos.get(i).isSituacao()) { // false = ativo
                System.out.println("[" + i + "] " + contratos.get(i));
                encontrou = true;
            }
        }
        
        if (!encontrou) {
            System.out.println("Nenhum contrato ativo.");
            return;
        }
        
        System.out.print("Escolha o contrato para encerrar: ");
        int indice = scanner.nextInt();
        
        Contrato contrato = contratos.get(indice);
        
        if (contrato.isSituacao()) {
            System.out.println("Contrato já está encerrado.");
            return;
        }
        
        contrato.setSituacao(true);
        
        System.out.println("Contrato encerrado com sucesso!");
    }
    
    private static void listarContratosAtivos() {
        ArrayList<Contrato> contratos = imobiliaria.getContratos();
        
        if (contratos.isEmpty()) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }
        
        System.out.println("\n=== CONTRATOS ATIVOS ===");
        
        boolean encontrou = false;
        
        for (Contrato c : contratos) {
            if (!c.isSituacao()) { // false = ativo
                System.out.println(c);
                encontrou = true;
            }
        }
        
        if (!encontrou) {
            System.out.println("Nenhum contrato ativo.");
        }
    }
    
    private static void demonstracao() {
        
        // Inquilinos
        Inquilino i1 = new Inquilino("João", "123", "9999-1111");
        Inquilino i2 = new Inquilino("Maria", "456", "9999-2222");
        
        // Imóveis (AGORA COM ENDEREÇO NO CONSTRUTOR)
        Casa casa = new Casa("Rua A, 123", 1500, true);
        Apartamento ap = new Apartamento("Av B, 456", 1000, 3);
        
        // Contratos
        Contrato c1 = new Contrato(i1, casa, 12); // ativo
        Contrato c2 = new Contrato(i2, ap, 6);    // encerrado
        
        // Encerrando um contrato
        c2.setSituacao(true);
        
        // Adicionando na imobiliária
        imobiliaria.adicionaContrato(c1);
        imobiliaria.adicionaContrato(c2);
        
        // Listar apenas contratos ativos
        System.out.println("\n=== CONTRATOS ATIVOS ===");
        
        for (Contrato c : imobiliaria.getContratos()) {
            if (!c.isSituacao()) {
                System.out.println(c);
            }
        }
    }
    
    
}
