//RegistoVendas
package controle.estoque;
import java.text.DecimalFormat;

public class RegistroVenda {

    private String nome, vendedor, str;
    private int id, quantidade;
    private double valor;
    
    public RegistroVenda(int id, String nome, String vendedor, int quantidade, double valor){
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.id = id;
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
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
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