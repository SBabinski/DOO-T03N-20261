package loja.controller;

import loja.model.Venda;
import loja.service.VendaService;

import java.util.ArrayList;
import java.util.Scanner;

public class VendaController {

    private Scanner scanner = new Scanner(System.in);

    // onde serão armazenadas as vendas
    private  ArrayList<Venda> vendas = new ArrayList<>();

    // o que move o programa
    public void iniciar() {

        int op;

        do {
            op = mostraMenu();
            processaOp(op);
        } while (op != 6);
    }

    // Mostra menu ao usuário
    private int mostraMenu() {

        System.out.printf("\n=============================================================\n" +
                "[1] - Cadastrar nova Venda\n" +
                "[2] - Listar vendas cadastradas\n" +
                "[3] - Apenas calcular preço\n" +
                "[4] - Apenas calcular troco\n" +
                "[5] - Apenas calcular desconto\n" +
                "[6] - Sair\n" +
                "=============================================================\n" +
                "Entre com a opção : ");

        return scanner.nextInt();
    }

    // direciona usuário para opção escolhida
    private void processaOp(int op) {

        switch (op){

            case 1:
                cadastraVenda();
                break;

            case 2:
                listaCompras();
                break;

            case 3:
                calculaPreco();
                break;

            case 4:
                calculaTroco();
                break;

            case 5:
                calculaDesconto();
                break;

            case 6:
                System.out.println("\nSAINDO...");
                break;

            default:
                System.out.println("\nOpção inválida! Tente novamente!");
                break;
        }
    }

    // Cadastra nova compra
    private void cadastraVenda() {
        System.out.println("\nQuntidade: ");
        int quant = scanner.nextInt();
        System.out.println("\nValor unitário: ");
        double valUni = scanner.nextDouble();

        double valVenda = VendaService.calculaPreco(quant,valUni);
        double desconto = VendaService.calculaDesconto(quant,valVenda);
        vendas.add(VendaService.cadastraVenda(quant,valVenda,desconto));

        System.out.println("venda cadastrada com sucesso!");
    }


    // Lista compras realizadas
    private void listaCompras() {

        if (vendas.isEmpty()) {
            System.out.println("\nNenhuma compra cadastrada.");
            return;
        }

        System.out.printf(
                "\n-------------------------------------------------------------\n" +
                "  %-3s   %-10s   %-10s   %-10s   %-10s  \n" +
                "-------------------------------------------------------------\n",
                "ID", "QUANTIDADE", "V.TOTAL", "DESCONTO", "V.PAGO");

        for (Venda m : vendas) {
            System.out.println(m);
        }

        System.out.println("-------------------------------------------------------------");

    }

    // Calculo do Preço
    private void calculaPreco() {

        System.out.println("\nQuntidade: ");
        int quant = scanner.nextInt();
        System.out.println("\nValor unitário: ");
        double valUni = scanner.nextDouble();

        double preco = VendaService.calculaPreco(quant,valUni);

        System.out.printf("\nPreço = R$%.2f",preco);

    }

    // Calculo do troco
    private void calculaTroco() {

        System.out.println("\nValor pago: ");
        double valPag = scanner.nextDouble();
        System.out.println("\nValor devido: ");
        double valDev = scanner.nextDouble();

        double troco = VendaService.calculaTroco(valPag,valDev);

        System.out.printf("\nTroco = R$%.2f",troco);

    }

    // calcula desconto
    private void calculaDesconto(){

        System.out.println("\nQuntidade: ");
        int quant = scanner.nextInt();
        System.out.println("\nValor Total: ");
        double valTot = scanner.nextDouble();

        double desconto = VendaService.calculaDesconto(quant, valTot);

        System.out.printf("\nDesconto = R$%.2f",desconto);
    }

}
