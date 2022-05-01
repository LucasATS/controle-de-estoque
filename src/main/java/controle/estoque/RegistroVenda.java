//RegistoVendas
package controle.estoque;

public class RegistroVenda {
    String nome, vendedor;
    int quantidade;
    double valor;
    
    RegistroVenda(String nome, String vendedor, int quantidade, double valor){
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
	this.vendedor = vendedor;
    }
}