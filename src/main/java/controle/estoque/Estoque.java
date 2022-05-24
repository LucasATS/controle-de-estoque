package controle.estoque;

import java.util.ArrayList;


import controle.Keys;

import java.io.Serializable;
import java.text.DecimalFormat;


public class Estoque implements Serializable{
    itemEstoque[] itens = new itemEstoque[0];

    int quantItens(){
        return this.itens.length;
    }
    
    public Boolean exist(){
        return true;
    }

    itemEstoque getItem (String nome){
        itemEstoque item;
        item = null;
        for (itemEstoque itemEstoque : itens) {
            if (itemEstoque.getNome().equals(nome) == true) item = itemEstoque;
        }
        return item;
    }
    
    String novoItem(String nome, int quantidade, double valor, int margem){
        int indice = indiceDe(nome);
        if (indice !=-1){
            return Keys.alertas.erro_item_ja_existe;
        }
        itemEstoque[] armazena = this.itens.clone();
        int quantidadeAtual = this.itens.length;
        this.itens = new itemEstoque[quantidadeAtual+1];
        int i=0;
        for (itemEstoque item : armazena){
            this.itens[i]=item;
            i++;
        }
        this.itens[i] = new itemEstoque(nome, quantidade, valor, margem);
        App.financeiro.addCompra(this.itens[i]);
        return Keys.alertas.msg_item_add_com_sucesso;
    }

    String deletaItem(String nome){
        int indice = indiceDe(nome);
        if (indice ==-1){
            return Keys.alertas.erro_item_inexistente;
        }
        itemEstoque[] armazena = this.itens.clone();
        armazena[indice]=null;
        int quantidadeAtual = this.itens.length;
        this.itens = new itemEstoque[quantidadeAtual-1];
        int i=0;
        for (itemEstoque item : armazena){
            if (item!=null){
                this.itens[i]=item;
                i++;
            }
        }
        return Keys.alertas.msg_item_rmv_com_sucesso;
    }

    int indiceDe(String nome){
        int pos = -1;
        for (int i=0;i<this.itens.length;i++){
            if (this.itens[i].getNome().equals(nome)==true){
                pos=i;
            }
        }
        return pos;
    }
    String adicionaQuantidade(String nome,int quantidade){
        int indice = indiceDe(nome);
        if (indice ==-1){
            return Keys.alertas.erro_item_inexistente;
        }
        this.itens[indice].setQtd(this.itens[indice].getQtd() + quantidade);
        return "Adicionado mais "+nome+" ao estoque.";
    }
    void diminuiQuantidade(String nome,int quantidade){
        int indice = indiceDe(nome);
        this.itens[indice].setQtd(this.itens[indice].getQtd() - quantidade);

    }
    String alterarMargem(String nome,int margem){
        int indice = indiceDe(nome);
        if (indice ==-1){
            return Keys.alertas.erro_item_inexistente;
        }
        this.itens[indice].alteraMargem(margem);

        return "Margem de lucro do "+nome+" atualizado.";
    }
    ArrayList<String> geraHTML(ArrayList<String> modelo){
        ArrayList<String> escreve = new ArrayList<String>();
        try {
            for (String str : modelo) {
                if (str.equals("{dados-estoque}")==true) {

                    for (itemEstoque item : itens){
                        String valorRS = new DecimalFormat("R$ #,###.00").format(item.getValorVenda());
                        escreve.add("<tr>\n" + 
                            "<td>"+item.getQtd()+"</td>" + 
                            "<td>"+valorRS+"</td>" + 
                            "<td>"+item.getQtd()+"</td>\n" +
                            "</tr>\n");
                    }
                }else{
                    escreve.add(str);
                }
            }
        } catch (Exception e) {
            System.out.println(Keys.alertas.erro_inesperado);
        }
        return escreve;
    }

    public String ErroException(String msg, Exception e){
        String alert = msg + "\n#Error: " + e.getMessage();
        System.out.println(alert);
        return alert;
    }
}
