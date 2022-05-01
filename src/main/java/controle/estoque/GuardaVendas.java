package controle.estoque;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class GuardaVendas {
    RegistroVenda[] itens = new RegistroVenda[0];
    GuardaVendas(){
        try {
            File arquivo = new File("bd/GuardaVendasBD.txt");
            Scanner GuardaVendasBD = new Scanner(arquivo);
            while (GuardaVendasBD.hasNextLine()) {
                String[] dados = GuardaVendasBD.nextLine().split(";");
                novaVenda(dados[0], Integer.parseInt(dados[1]), Double.parseDouble(dados[2]));
            }
            GuardaVendasBD.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro inesperado...");
        }
    }
    void salvaFile(){
        try {
            File arquivo = new File("bd/GuardaVendasBD.txt");
            arquivo.createNewFile();
            FileWriter escreve = new FileWriter(arquivo,false);
            for (RegistroVenda item : itens){
                escreve.append(item.nome + ";"+item.quantidade + ";"+item.valor+"\n");
            }
            escreve.close();
        }
        catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado...");
        }
    }
    String novaVenda(String nome, int quantidade, double valor){
        RegistroVenda[] armazena = this.itens.clone();
        int quantidadeAtual = this.itens.length;
        this.itens = new RegistroVenda[quantidadeAtual+1];
        int i=0;
        for (RegistroVenda item : armazena){
            this.itens[i]=item;
            i++;
        }
        this.itens[i] = new RegistroVenda(nome, quantidade, valor);
        salvaFile();
        return "Venda realizada com Sucesso.";
    }
}

//Inclua uma nova classe que guarde as vendas realizadas na quitanda;
//2) O estoque e as vendas devem ser gravados ao sair do programa;
//3) O estoque e as vendas devem ser lidos ao iniciar o programa;
//4) A quantidade de vendas por vendedor também deve constar no relatório;
//5) (Desafio) Faça uma interface javafx para seu sistema de estoque e vendas;













//RegistoVendas
package controle.estoque;

public class RegistroVenda {
    String nome;
    int quantidade;
    double valor;
    
    RegistroVenda(String nome, int quantidade, double valor){
        this.nome=nome;
        this.quantidade=quantidade;
        this.valor=valor;
    }
}