package objetos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Venda {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    static List<Planta> registroVendas = new java.util.ArrayList<>();

    public static void calcularPreco() {
        System.out.print("Digite a espécie da planta: ");
        String especie = scanner.nextLine();
        if (especie.isEmpty()) {
            System.out.println("Espécie não pode ser vazia. Tente novamente.");
            return;
        }

        System.out.print("Digite a quantidade: ");
        int qtd = scanner.nextInt();
        if (qtd <= 0) {
            System.out.println("Quantidade deve ser maior que zero. Tente novamente.");
            return;
        }

        System.out.print("Digite o valor unitário: ");
        double valor = scanner.nextDouble();
        if (valor <= 0) {
            System.out.println("Valor deve ser maior que zero. Tente novamente.");
            return;
        }

        double precoTotal;
        if (qtd >= 10) {
            System.out.println("Desconto de 5% aplicado para compras acima de 10 unidades.");
            precoTotal = (valor * qtd) * 0.95;
        } else {
            precoTotal = qtd * valor;
        }
        System.out.println("Preço total: " + precoTotal);

        Planta venda = new Planta(especie, qtd, precoTotal);
        registrarVenda(venda);
    }

    public static void calcularTroco() {
        System.out.print("Digite o valor pago: ");
        double valorPago = scanner.nextDouble();
        if (valorPago <= 0) {
            System.out.println("Valor pago deve ser maior que zero. Tente novamente.");
            return;
        }

        System.out.print("Digite o preço total: ");
        double precoTotal = scanner.nextDouble();
        if (precoTotal <= 0) {
            System.out.println("Preço total deve ser maior que zero. Tente novamente.");
            return;
        }

        if (valorPago < precoTotal) {
            System.out.println("Valor pago é insuficiente. Tente novamente.");
            return;
        }

        double troco = valorPago - precoTotal;
        System.out.println("Troco: " + troco);
    }

    public static void registrarVenda(Planta venda) {
        registroVendas.add(venda);
        System.out.println("Venda registrada com sucesso em " + venda.getDataFormatada() + "!");
    }

    public static void mostrarRegistroVendas() {
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        System.out.println("=== Registro de Vendas ===");
        for (Planta venda : registroVendas) {
            venda.mostrarResumo();
        }
        System.out.println("Quantidade total de vendas: " + registroVendas.size());
    }

    public static void consultarVendasPorData() {
        System.out.println("=== Consultar Vendas por Data ===");
        System.out.println("1. Consultar por dia (dd/MM/yyyy)");
        System.out.println("2. Consultar por mês (MM/yyyy)");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1 -> consultarPorDia();
            case 2 -> consultarPorMes();
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void consultarPorDia() {
        scanner.nextLine();
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String dataDigitada = scanner.nextLine();

        LocalDate consultarData;
        try {
            consultarData = LocalDate.parse(dataDigitada, FORMATTER_DIA);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
            return;
        }

        List<Planta> vendasDoDia = registroVendas.stream()
                .filter(v -> v.getDataVenda().equals(consultarData))
                .toList();

        if (vendasDoDia.isEmpty()) {
            System.out.println("Nenhuma venda registrada para o dia " + dataDigitada + ".");
        } else {
            System.out.println("=== Vendas do dia " + dataDigitada + " ===");
            vendasDoDia.forEach(Planta::mostrarResumo);
            System.out.println("Total de vendas no dia: " + vendasDoDia.size());
        }
    }

    private static void consultarPorMes() {
        scanner.nextLine(); // limpa buffer
        System.out.print("Digite o mês e ano (MM/yyyy): ");
        String mesAnoDigitado = scanner.nextLine();

        LocalDate referencia;
        try {
            referencia = LocalDate.parse("01/" + mesAnoDigitado, FORMATTER_DIA);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido. Use MM/yyyy.");
            return;
        }

        int mes = referencia.getMonthValue();
        int ano = referencia.getYear();

        List<Planta> vendasDoMes = registroVendas.stream()
                .filter(v -> v.getDataVenda().getMonthValue() == mes
                          && v.getDataVenda().getYear() == ano)
                .toList();

        if (vendasDoMes.isEmpty()) {
            System.out.println("Nenhuma venda registrada para o mês " + mesAnoDigitado + ".");
        } else {
            System.out.println("=== Vendas do mês " + mesAnoDigitado + " ===");
            vendasDoMes.forEach(Planta::mostrarResumo);
            System.out.println("Total de vendas no mês: " + vendasDoMes.size());
        }
    }
}