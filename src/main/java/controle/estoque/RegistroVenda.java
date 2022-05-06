//RegistoVendas
package controle.estoque;
import java.util.ArrayList;

public class RegistroVenda {

    private int id;
    private String vendedor;
    private double valor;
    private ArrayList<SaidaProduto> produtos;

    public RegistroVenda(int id, String vendedor, ArrayList<SaidaProduto> produtos){
        this.id = id;
        this.vendedor = vendedor;
        this.produtos = produtos;

        for (SaidaProduto produto : produtos){
            this.valor += produto.getValortotal();
        }

    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public ArrayList<SaidaProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<SaidaProduto> produtos) {
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