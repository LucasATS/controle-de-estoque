package controle.estoque;

import java.io.Serializable;

import java.text.DecimalFormat;

public class itemEstoque implements Serializable{
    private String nome, str;
    private int quantidade;
    private double valor, valorVenda, valortotal;

    itemEstoque(String nome, int quantidade, double valor, int margem){
        margem/=100;
        margem+=1;
        this.nome=nome;
        this.quantidade=quantidade;
        this.valor=valor;
        this.valorVenda= valor*margem;
        this.valortotal = quantidade * this.valorVenda;

        String valorRS = new DecimalFormat("R$ #,###.00").format(valorVenda);
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

    public void alteraMargem(int margem){
        margem/=100;
        margem+=1;
        this.valorVenda=this.valor*margem;
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

    public Double getValorVenda() {
        return valorVenda;
    }
    public Double getValortotal(){
        return valortotal;
    }
    public String getStr(){
        return str;
    }
    
}