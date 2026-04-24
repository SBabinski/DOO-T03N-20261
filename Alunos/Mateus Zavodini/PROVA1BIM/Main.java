public class Main {
    public static void main(String[] args) {

        Imobiliaria imob = new Imobiliaria();

        Inquilino i1 = new Inquilino("João", "111", "9999");
        Inquilino i2 = new Inquilino("Maria", "222", "8888");

        Apartamento ap = new Apartamento("Rua A", 1000, 2);
        Casa casa = new Casa("Rua B", 1500, true);

        Contrato c1 = new Contrato(i1, ap, 6);
        Contrato c2 = new Contrato(i2, casa, 12);

        c1.encerrar(); 

        imob.adicionar(c1);
        imob.adicionar(c2);


        System.out.println("CONTRATOS ATIVOS:");
        imob.listarAtivos();
    }
}