package Imobiliaria;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Contrato {

    Inquilino inquilino;
    Imovel imovel;
    Date inicio, fim;
    boolean encerrado = false;

    public Contrato(Inquilino inquilino, Imovel imovel, Date inicio, Date fim) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.inicio = inicio;
        this.fim = fim;
    }

    public void encerrar() {
        encerrado = true;
    }

    public double calcularTotal() {

        long diferenca = fim.getTime() - inicio.getTime();
        long dias = diferenca / (1000 * 60 * 60 * 24);
        long meses = dias / 30;

        return meses * imovel.valor;
    }

    public void mostrar() {

        System.out.println("\nInquilino: " + inquilino.nome);
        imovel.mostrar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Data início: " + sdf.format(inicio));
        System.out.println("Data fim: " + sdf.format(fim));
        System.out.println("Total contrato: R$ " + calcularTotal());
        System.out.println("Status: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("----------------------");
    }
}