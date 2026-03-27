import java.time.LocalDateTime;

public class Vendas {
    int valorplantas;
    int totalplantas;
    double desconto;
    double total;
    private static LocalDateTime dataHora;

    public Vendas(int valorplantas , int totalplantas ,double desconto, double total){
        this.valorplantas = valorplantas;
        this.totalplantas = totalplantas;
        this.desconto = desconto;
        this.total = total;
        dataHora = LocalDateTime.now();
    }

    public static LocalDateTime getDataHora(){
        return dataHora;
    }


}
