package fag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Floricultura {

    static Loja loja;

    static ArrayList<Double> quantidades = new ArrayList<>();
    static ArrayList<Double> valores = new ArrayList<>();
    static ArrayList<Double> descontos = new ArrayList<>();
    static ArrayList<LocalDate> datas = new ArrayList<>();

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        loja = new Loja(
                "My Plant",
                "My Plant LTDA",
                "123",
                new Endereco("PR", "Cascavel", "Centro", "123", "Rua A")
        );

        int op;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("[1] Calcular Venda");
            System.out.println("[2] Calcular Troco");
            System.out.println("[3] Relatório de Vendas");
            System.out.println("[4] Buscar por Data");
            System.out.println("[5] Dados da Loja");
            System.out.println("[6] Criar Pedido");
            System.out.println("[7] Sair");

            op = sc.nextInt();

            switch (op) {

                case 1:
                    calcularVenda(sc);
                    break;

                case 2:
                    calcularTroco(sc);
                    break;

                case 3:
                    relatorio();
                    break;

                case 4:
                    buscarPorData(sc);
                    break;

                case 5:
                    loja.mostrarDetalhes();
                    break;

                case 6:
                    criarPedido();
                    break;
            }

        } while (op != 7);
    }

    public static void calcularVenda(Scanner sc) {
        System.out.println("Quantidade:");
        double qtd = sc.nextDouble();

        System.out.println("Preço:");
        double preco = sc.nextDouble();

        double total = qtd * preco;
        double desconto = 0;

        if (qtd > 10) {
            desconto = total * 0.05;
            total -= desconto;
        }

        System.out.println("Total: R$ " + total);

        quantidades.add(qtd);
        valores.add(total);
        descontos.add(desconto);
        datas.add(LocalDate.now());
    }

    public static void calcularTroco(Scanner sc) {
        System.out.println("Valor pago:");
        double pago = sc.nextDouble();

        System.out.println("Valor total:");
        double total = sc.nextDouble();

        System.out.println("Troco: R$ " + (pago - total));
    }

    public static void relatorio() {
        for (int i = 0; i < valores.size(); i++) {
            System.out.println("Venda " + (i + 1));
            System.out.println("Qtd: " + quantidades.get(i));
            System.out.println("Valor: " + valores.get(i));
            System.out.println("Desconto: " + descontos.get(i));
            System.out.println("Data: " + datas.get(i).format(formatter));
        }
    }

    public static void buscarPorData(Scanner sc) {
        System.out.println("Digite data (dd/MM/yyyy):");
        LocalDate data = LocalDate.parse(sc.next(), formatter);

        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).equals(data)) {
                System.out.println("Venda encontrada:");
                System.out.println("Valor: " + valores.get(i));
            }
        }
    }

    public static void criarPedido() {
        ProcessaPedido p = new ProcessaPedido();

        Pedido pedido = p.processar(
                1,
                loja.clientes.get(0),
                loja.vendedores.get(0),
                loja
        );

        pedido.gerarDescricaoVenda();
    }
}