//RegistoVendas
package controle.estoque;
import javafx.scene.control.Button;

public class RegistroVenda {

    String str;
    Button remove = new Button("Remover");

    String nome, vendedor;
    int quantidade;
    double valor;
    
    RegistroVenda(String nome, String vendedor, int quantidade, double valor){
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;

        this.str = 
        nome + " " +
        quantidade + " " +
        valor + " " +
        (quantidade * valor)
        ;
	    this.vendedor = vendedor;
    }
}