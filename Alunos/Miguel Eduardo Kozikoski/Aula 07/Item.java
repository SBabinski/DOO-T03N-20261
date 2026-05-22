public class Item {
    String nome;
    String tipo;
    double valor;
    int ID;

    public Item (String nome , String tipo , double valor , int ID){
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.ID = ID;
    }

    public void apresentacaoProduto(){
        System.out.println("---------- DETALHES DO ITEM ----------");
        System.out.printf("🆔 ID:     #%03d%n", ID); // %03d deixa o id como 001, 002...
        System.out.printf("📦 NOME:   %s%n", nome.toUpperCase());
        System.out.printf("🏷️ TIPO:   %s%n", tipo);
        System.out.printf("💰 VALOR:  R$ %.2f%n", valor);
        System.out.println("--------------------------------------");
    }
}
