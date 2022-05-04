package controle.estoque;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import controle.Keys;
import controle.Keys.files;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class GuardaVendas {
    RegistroVenda[] itens = new RegistroVenda[0];
    Estoque estoque = new Estoque();

    GuardaVendas(){
        try {
            File arquivo = new File(files.GuardaVendasBD);

            Scanner GuardaVendasBD = new Scanner(arquivo);

            while (GuardaVendasBD.hasNextLine()) {
                String[] dados = GuardaVendasBD.nextLine().split(";");
                RegistroVenda registro = new RegistroVenda(
                    Integer.parseInt(dados[0]), dados[1], dados[2], Integer.parseInt(dados[3]), Double.parseDouble(dados[4])
                );
                carrega(registro);
            }

            GuardaVendasBD.close();
        } 
        catch (FileNotFoundException e) {
            salvaFile();
        }
    }
    void salvaFile(){
        try {
            File arquivo = new File(files.GuardaVendasBD);
            arquivo.createNewFile();
            FileWriter escreve = new FileWriter(arquivo,false);
            for (RegistroVenda item : itens){
                escreve.append(item.getId() + ";" + item.getNome() + ";" + item.getVendedor() + ";" + item.getQtd() + ";" + item.getValor() + "\n");
            }
            escreve.close();
        }
        catch (Exception e) {
            ErroException(Keys.alertas.erro_inesperado, e);
        }
    }
    int novaId(){
        int new_id = 0;
        for (RegistroVenda registroVenda : itens) {
            if (registroVenda.getId()>new_id) new_id=registroVenda.getId();
        }
        return new_id + 1;
    }
    void carrega(RegistroVenda registro){
        RegistroVenda[] armazena = this.itens.clone();
        int quantidadeAtual = this.itens.length;
        this.itens = new RegistroVenda[quantidadeAtual+1];
        int i = 0 ;

        for (RegistroVenda item: armazena){
            this.itens[i]=item;
            i++;
        }

        this.itens[i] = registro;
        salvaFile();
    }
    String novaVenda(RegistroVenda registro){
        RegistroVenda[] armazena = this.itens.clone();
        estoque.diminuiQuantidade(registro.getNome(), registro.getQtd());
        int quantidadeAtual = this.itens.length;
        this.itens = new RegistroVenda[quantidadeAtual+1];
        int i = 0 ;

        for (RegistroVenda item: armazena){
            this.itens[i]=item;
            i++;
        }

        this.itens[i] = registro;
        salvaFile();
        
        return Keys.alertas.msg_venda_realizada;
    }
    String deletaRegistro(int id){
        RegistroVenda[] armazena = this.itens.clone();
        int len = 0;
        for (RegistroVenda registroVenda : armazena) {
            if (id==registroVenda.getId()) len++;
        }
        int quantidadeAtual = this.itens.length;
        this.itens = new RegistroVenda[quantidadeAtual-len];
        int i=0;
        for (RegistroVenda item : armazena){
            if (item!=null){
                this.itens[i]=item;
                i++;
            }
        }
        salvaFile();
        return Keys.alertas.msg_item_rmv_com_sucesso;
    }
    ArrayList<String> geraHTML(ArrayList<String> modelo){
        
        ArrayList<String> escreve = new ArrayList<String>();
        try {
            for (String str : modelo) {
                if (str.equals("{dados-vendas-por-vendedor}")==true) {

                    for (RegistroVenda item : itens){
                        String valorRS = new DecimalFormat("R$ #,###.00").format(item.getValor());
                        escreve.add("<tr>\n" + 
                        "<td>"+item.getVendedor()+"</td>" +
                        "<td>"+item.getQtd()+"</td>\n" +
                        "<td>"+valorRS+"</td>" + 
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