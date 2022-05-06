//RegistoVendas
package controle.estoque;
import java.text.DecimalFormat;

public class SaidaProduto {

    private String nome, str;
    private int quantidade;
    private double valor, valortotal;
    
    public SaidaProduto(String nome, int quantidade, double valor){
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.valortotal = quantidade * valor;

        String valorRS = new DecimalFormat("R$ #,###.00").format(valor);
        String valorRS_total = new DecimalFormat("R$ #,###.00").format(valortotal);

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

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
    public Double getValortotal() {
        return valortotal;
    }

    public void setValortotal(Double valortotal) {
        this.valortotal = valortotal;
    }
    public String getStr(){
        return str;
    }
}