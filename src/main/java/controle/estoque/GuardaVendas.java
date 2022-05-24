package controle.estoque;

import java.util.ArrayList;

import controle.Keys;

import java.io.Serializable;
import java.text.DecimalFormat;

public class GuardaVendas implements Serializable {

    ArrayList<RegistroVenda> itens = new  ArrayList<RegistroVenda>();

    int novaId(){
        int new_id = 0;
        for (RegistroVenda registroVenda : itens) {
            if (registroVenda.getId()>new_id) new_id=registroVenda.getId();
        }
        return new_id + 1;
    }

    void carrega(RegistroVenda registro){
        itens.add(registro);

    }

    String novaVenda(RegistroVenda registro){
        
        for (itemEstoque saida : registro.getProdutos()){
            App.estoque.diminuiQuantidade(saida.getNome(), saida.getQtd());
            App.financeiro.addvenda(saida);
        }
        itens.add(registro);
        
        return Keys.alertas.msg_venda_realizada;
    }

    String deletaRegistro(int id){

        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getId()==id) itens.remove(i);
        }
        

        return Keys.alertas.msg_item_rmv_com_sucesso;

    }

    ArrayList<String> geraHTML(ArrayList<String> modelo, Aluno[] alunos){
        
        ArrayList<String> escreve = new ArrayList<String>();
        try {
            for (String str : modelo) {
                if (str.equals("{dados-vendas-por-vendedor}")==true) {
                    int qtde;
                    double valor;

                    for (Aluno aluno : alunos) {
                        qtde=0;
                        valor=0;

                        for (RegistroVenda venda : itens) {
                            if (venda.getVendedor().equals(aluno.nome) == true){
                                qtde++;
                                valor+=venda.getValor();
                            }
                        }
                        escreve.add("<tr>\n" + 
                                "<td>"+aluno.nome+"</td>" +
                                "<td>"+qtde+"</td>\n" +
                                "<td>"+valorRS(valor)+"</td>" + 
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

    public String valorRS(double valor){
        return new DecimalFormat("R$ #,###.00").format(valor);
    }

    public String ErroException(String msg, Exception e){
        String alert = msg + "\n#Error: " + e.getMessage();
        System.out.println(alert);
        return alert;
    }
}