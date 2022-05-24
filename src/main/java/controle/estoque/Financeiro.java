package controle.estoque;


import java.io.Serializable;

import controle.Keys;


public class Financeiro implements Serializable{
    
    private double valorCaixa=2000, valorGasto = 0 , 
        valorVendas = 0, valorLucro = 0;

    public String iniciaCaixa(double valor){
        this.valorCaixa = valor;
        return Keys.alertas.msg_Caixa_Iniciado;
    }

    public void addCompra(itemEstoque item){
        this.valorGasto += item.getValor() * item.getQtd();
        this.valorCaixa -= item.getValor() * item.getQtd();  
    }

    public void addvenda(itemEstoque item){
        int quantidade = item.getQtd();
        this.valorVendas += item.getValorVenda() * quantidade;
        this.valorLucro += (item.getValorVenda() * quantidade) - (item.getValor()* quantidade);
        this.valorCaixa += item.getValorVenda() * quantidade;
    }
    
    public Double getValorCaixa(){
        return this.valorCaixa;
    }  
    public Double getValorGasto(){
        return this.valorGasto;
    }  
    public Double getValorVendas(){
        return this.valorVendas;
    }  
    public Double getValorLucro(){
        return this.valorLucro;
    }  
}
