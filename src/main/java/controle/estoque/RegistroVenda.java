//RegistoVendas
package controle.estoque;
import java.util.ArrayList;
import java.io.Serializable;

public class RegistroVenda implements Serializable {

    private int id;
    private String vendedor;
    private double valor;
    private ArrayList<itemEstoque> produtos;

    public RegistroVenda(int id, String vendedor, ArrayList<itemEstoque> produtos){
        this.id = id;
        this.vendedor = vendedor;
        this.produtos = produtos;

        for (itemEstoque produto : produtos){
            this.valor += produto.getValortotal();
        }

    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public ArrayList<itemEstoque> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<itemEstoque> produtos) {
        this.produtos = produtos;
    }
    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}