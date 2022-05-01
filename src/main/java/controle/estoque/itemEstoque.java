package controle.estoque;

public class itemEstoque{
    String nome;
    int quantidade;
    double valor;
    itemEstoque(String nome, int quantidade, double valor){
        this.nome=nome;
        this.quantidade=quantidade;
        this.valor=valor;
    }
    void alteraValor(double valor){
        this.valor=valor;
    }
    void alteraQuantidade(int quantidade){
        this.quantidade=quantidade;
    }
}