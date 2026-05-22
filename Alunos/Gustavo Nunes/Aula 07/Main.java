import controller.Controller;
import io.IO;
import repository.LojaRepository;
import repository.PedidoRepository;
import repository.VendaRepository;
import service.LojaService;
import service.PedidoService;
import service.VendaService;

public class Main {
    public static IO io = new IO();
    public static VendaService vendaService = new VendaService();
    public static PedidoService pedidoService = new PedidoService();
    public static LojaService lojaService = new LojaService();
    public static VendaRepository vendas = new VendaRepository();
    public static LojaRepository lojas = new LojaRepository();
    public static PedidoRepository pedidos = new PedidoRepository();
    public static Controller controller = new Controller(io, vendaService, pedidoService,
            lojaService, vendas, lojas, pedidos);
    
    public static void main(String[] args) {
        controller.iniciar();
    }
}