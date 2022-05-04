//RegistoVendas
package controle.estoque;
import java.text.DecimalFormat;

public class RegistroVenda {

    private String nome, vendedor, str;
    private int quantidade;
    private double valor;
    
    public RegistroVenda(String nome, String vendedor, int quantidade, double valor){
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;

        String valorRS = new DecimalFormat("R$ #,###.00").format(valor);
        String valorRS_total = new DecimalFormat("R$ #,###.00").format(quantidade * valor);

        if (nome=="total"){

            this.str = "VALOR TOTAL....:"+ valorRS;

        }else{

            this.str = 
            nome + " - " +
            quantidade + " - " +
            valorRS + " - " +
            valorRS_total
            ;

        }
        
        this.vendedor = vendedor;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }
    public int getQtd() {
        return quantidade;
    }

    public void setQtd(int quantidade) {
        this.quantidade = quantidade;
    }
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    public String getStr(){
        return str;
    }
}