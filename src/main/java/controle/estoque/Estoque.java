package controle.estoque;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class Estoque {
    itemEstoque[] itens = new itemEstoque[0];
    int quantItens(){
        return this.itens.length;
    }
    Estoque(){
        try {
            File arquivo = new File("bd/estoqueBD.txt");
            Scanner estoqueBD = new Scanner(arquivo);
            while (estoqueBD.hasNextLine()) {
                String[] dados = estoqueBD.nextLine().split(";");
                novoItem(dados[0], Integer.parseInt(dados[1]), Double.parseDouble(dados[2]));
            }
            estoqueBD.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro inesperado...");
        }
    }
    void salvaFile(){
        try {
            File arquivo = new File("bd/estoqueBD.txt");
            arquivo.createNewFile();
            FileWriter escreve = new FileWriter(arquivo,false);
            for (itemEstoque item : itens){
                escreve.append(item.nome + ";"+item.quantidade + ";"+item.valor+"\n");
            }
            escreve.close();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado...");
        }
    }
    String novoItem(String nome, int quantidade, double valor){
        int indice = indiceDe(nome);
        if (indice !=-1){
            return "Item já existe.";
        }
        itemEstoque[] armazena = this.itens.clone();
        int quantidadeAtual = this.itens.length;
        this.itens = new itemEstoque[quantidadeAtual+1];
        int i=0;
        for (itemEstoque item : armazena){
            this.itens[i]=item;
            i++;
        }
        this.itens[i] = new itemEstoque(nome, quantidade, valor);
        salvaFile();
        return "Item adicionado com Sucesso.";
    }
    String deletaItem(String nome){
        int indice = indiceDe(nome);
        if (indice ==-1){
            return "Item inexistente.";
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
        salvaFile();
        return "Item deletado com Sucesso.";
    }
    int indiceDe(String nome){
        int pos = -1;
        for (int i=0;i<this.itens.length;i++){
            if (this.itens[i].nome.equals(nome)==true){
                pos=i;
            }
        }
        return pos;
    }
    String adicionaQuantidade(String nome,int quantidade){
        int indice = indiceDe(nome);
        if (indice ==-1){
            return "Item inexistente.";
        }
        this.itens[indice].alteraQuantidade(this.itens[indice].quantidade + quantidade);
        salvaFile();
        return "Adicionado mais "+nome+" ao estoque.";
    }
    String alterarValorMedio(String nome,double valor){
        int indice = indiceDe(nome);
        if (indice ==-1){
            return "Item inexistente";
        }
        this.itens[indice].alteraValor(valor);
        salvaFile();
        return "valor do "+nome+" atualizado no estoque.";
    }
    String geraHTML(){

        try {
            File arquivomodel = new File("models/modelo.html");
            Scanner modelo = new Scanner(arquivomodel);
            File arquivo = new File("relatorio.html");
            arquivo.createNewFile();
            FileWriter escreve = new FileWriter(arquivo,false);
            String linha;
            while (modelo.hasNextLine()) {
                linha = modelo.nextLine();
                if (linha.equals("{dados}")==true) {

                    for (itemEstoque item : itens){
                        String valorRS = new DecimalFormat("R$ #,###.00").format(item.valor);
                        escreve.append("                <tr>\n"+
                            "                   <td>"+item.nome+"</td>"+
                            "<td>"+valorRS+"</td>"+
                            "<td>"+item.quantidade+"</td>\n"+
                            "               </tr>\n"
                            );
                    }

                }else{
                    escreve.append(linha);
                }
            }
            modelo.close();
            escreve.close();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado...");
        }
        return "Relatório gerado.";
    }
}
