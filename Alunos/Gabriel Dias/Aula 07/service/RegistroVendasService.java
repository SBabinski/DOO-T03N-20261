package myplant.service;

import myplant.model.RegistroVenda;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para registrar e consultar vendas por dia/mês.
 */
public class RegistroVendasService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final List<RegistroVenda> registros = new ArrayList<>();

    public void registrarVenda(RegistroVenda venda) {
        registros.add(venda);
        System.out.println("✅ Venda registrada com sucesso em " + venda.getData().format(FORMATTER));
    }

    /**
     * Retorna total de vendas de um mês específico.
     */
    public int buscarTotalVendasPorMes(int mes, int ano) {
        return registros.stream()
                .filter(r -> r.getData().getMonthValue() == mes && r.getData().getYear() == ano)
                .mapToInt(RegistroVenda::getQuantidade)
                .sum();
    }

    /**
     * Retorna total de vendas de um dia específico.
     */
    public int buscarTotalVendasPorDia(LocalDate data) {
        return registros.stream()
                .filter(r -> r.getData().equals(data))
                .mapToInt(RegistroVenda::getQuantidade)
                .sum();
    }

    /**
     * Lista todos os registros de um mês/ano.
     */
    public List<RegistroVenda> listarPorMes(int mes, int ano) {
        return registros.stream()
                .filter(r -> r.getData().getMonthValue() == mes && r.getData().getYear() == ano)
                .collect(Collectors.toList());
    }

    public List<RegistroVenda> getTodosRegistros() { return registros; }
}
