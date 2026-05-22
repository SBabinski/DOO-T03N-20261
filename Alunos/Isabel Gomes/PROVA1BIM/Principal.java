package fag;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fag.objeto.Apartamento;
import fag.objeto.Casa;
import fag.objeto.Contrato;
import fag.objeto.Imobiliaria;
import fag.objeto.Imoveis;
import fag.objeto.Inquilino;


public class Principal {
	public static Scanner scan = new Scanner(System.in);

	static List<Imoveis> imoveis = new ArrayList<>();
	static List<Inquilino> inquilinos = new ArrayList<>();
	//static List<Item> itens = new ArrayList<>();
	
	
	static Imobiliaria imobiliaria = new Imobiliaria();
	
	public static void main(String[] args) {
		
		criarDemonstracao();
		mostrarMenu();
		
	}

	private static void mostrarMenu() {
		int escolha = 0;
		
		System.out.println("----- BEM VINDO A IMOBILIARIA -----");
		do {
			System.out.println("\n---- MENU ----\n");
			System.out.println("1 - Cadastrar inquilino");
			System.out.println("2 - Cadastrar imovel");
			System.out.println("3 - Cadastrar contrato");
			System.out.println("4 - Encerrar contrato");
			System.out.println("5 - Listar contatos ativos");
			System.out.println("0 - Sair");
			System.out.println("\nDigite sua escolha: ");
			escolha = scan.nextInt();
			scan.nextLine();
			
			validarEscolha(escolha);
		}while(escolha!=0);
	}

	private static void validarEscolha(int escolha) {
		switch(escolha) {
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
				listarAtivos();
			break;
			case 0:
				System.out.println("SAINDO...");
			break;
			default:
				System.out.println("\nEscolha uma opcao valida");
			break;
		
		}
		
	}

	private static void listarAtivos() {
		imobiliaria.listarAtivos();
	}

	private static void encerrarContrato() {
		System.out.println("\nContratos pendentes\n");
		imobiliaria.listarAtivos();
		
		System.out.println("\nEscolha o indice:");
		int escolha = scan.nextInt();
		scan.nextLine();
		
		imobiliaria.encerrarContrato(escolha);
	}

	private static void cadastrarContrato() {
		for(int i=0; i<inquilinos.size();i++) {
			System.out.println((i+1) + " - " + inquilinos.get(i).getNome());
		}
		System.out.println("\nEscolha o inquinlino para o cadastro: ");
		int inquilino = scan.nextInt();
		scan.nextLine();
		
		for(int i=0; i<imoveis.size(); i++) {
			System.out.println((i+1) + " - " + imoveis.get(i).getEndereco());
		}
		
		System.out.println("\nDigite o imovel que quer cadastar: ");
		int imovel = scan.nextInt();
		scan.nextLine();
		
		System.out.println("digite a situacao");
		boolean situacao = scan.nextBoolean();
		
		System.out.println("digite a data inicio do contrato: ");
	
		contrato.setDataInicio(scan.nextLine());
		
		System.out.println("digite a data final do contrato: ");
		contrato.setDataFim(scan.nextLine());
		Contrato contrato = new Contrato(inquilinos.get(inquilino-1), imoveis.get(imovel-1), situacao);
		
		
	}

	private static void cadastrarImovel() {
		System.out.println("\n 1 - Casa");
		System.out.println("2 - Apartamento");
		int escolha = scan.nextInt();
		scan.nextLine();
		
		System.out.println("\nDigite o endereco: ");
		String endereco = scan.nextLine();
		
		System.out.println("Digite o valor do aluguel");
		double aluguel = scan.nextDouble();
		
		Imoveis imovel = null;
		
		if(escolha == 1) {
			
			imovel = new Casa(endereco, aluguel, true);
			
		}else if(escolha == 2) {
			System.out.println("Digite o andar do ap: ");
			int andar = scan.nextInt();
			scan.nextLine();
			imovel = new Apartamento(endereco, aluguel, andar);
		}else {
			System.out.println("Digite uma opcao valida!");
		}
		
	}

	private static void cadastrarInquilino() {
		System.out.println("\nInsira o nome: ");
		String nome = scan.nextLine();
		
		System.out.println("\nInsira o CPF: ");
		String cpf = scan.nextLine();
		
		System.out.println("\nInsira o telefone: ");
		String telefone = scan.nextLine();
		
		Inquilino inquilino = new Inquilino(nome, cpf, telefone);
		inquilinos.add(inquilino);
		System.out.println("\nLeitor cadastrado com sucesso!");
	}

	private static void criarDemonstracao() {
		//inquilinos
		Inquilino i1 = new Inquilino("Isabel", "111.333.444.66", "4599999-9900");
		Inquilino i2 = new Inquilino("jonas", "222.333.444.55", "4598888-7777");
		
		//dois imoveis
		Apartamento a = new Apartamento("Rua A", 1500.00, 5 );
		Casa c = new Casa("Rua C", 2000.00, true);
		
		imoveis.add(c);
		imoveis.add(a);
		
		//doi contratos
		Contrato c1 = new Contrato(i1, a, true);
		c1.setDataInicio("12/12/2020");
		c1.setDataFim("12/12/2021");
		
		Contrato c2 = new Contrato(i2, c, false);
		c2.setDataInicio("05/05/2010");
		c2.setDataFim("05/05/2011");
		
		//adicionar na imob
		imobiliaria.adicionarContratos(c1);
		imobiliaria.adicionarContratos(c2);
		
	}

}
