package controle.estoque;

import java.io.File;
import java.util.Scanner;

import controle.Keys;
import controle.Keys.files;

import java.io.FileNotFoundException;
import java.io.FileWriter;

public class GuardaVendas {
    RegistroVenda[] itens = new RegistroVenda[0];
    GuardaVendas(){
        try {
            File arquivo = new File(files.GuardaVendasBD);

            Scanner GuardaVendasBD = new Scanner(arquivo);

            while (GuardaVendasBD.hasNextLine()) {
                String[] dados = GuardaVendasBD.nextLine().split(";");
                novaVenda(dados[0], dados[1], Integer.parseInt(dados[2]), Double.parseDouble(dados[3]));
            }

            GuardaVendasBD.close();
        } 
        catch (FileNotFoundException e) {
            ErroException(Keys.alertas.erro_arquivo_nao_encontrado, e);
        }
    }
    void salvaFile(){
        try {
            File arquivo = new File(files.GuardaVendasBD);
            arquivo.createNewFile();
            FileWriter escreve = new FileWriter(arquivo,false);
            for (RegistroVenda item : itens){
                escreve.append(item.nome + ";" + item.vendedor + ";" + item.quantidade + ";" + item.valor + "\n");
            }
            escreve.close();
        }
        catch (Exception e) {
            ErroException(Keys.alertas.erro_inesperado, e);
        }
    }
    String novaVenda(String nome, String vendedor, int quantidade, double valor){
        RegistroVenda[] armazena = this.itens.clone();
        int quantidadeAtual = this.itens.length;
        this.itens = new RegistroVenda[quantidadeAtual+1];
        int i = 0 ;

        for (RegistroVenda item: armazena){
            this.itens[i]=item;
            i++;
        }

        this.itens[i] = new RegistroVenda(nome, vendedor, quantidade, valor);
        salvaFile();
        
        return Keys.alertas.msg_venda_realizada;
    }
    String geraHTML(){
        try {
            File arquivomodel = new File(Keys.files.Modelo_relatorio_html);
            Scanner modelo = new Scanner(arquivomodel);
            File arquivo = new File(Keys.files.Relatorio_html);
            arquivo.createNewFile();
            FileWriter escreve = new FileWriter(arquivo,false);
            String linha;
            while (modelo.hasNextLine()) {
                linha = modelo.nextLine();
                if (linha.equals("{dados-vendas-por-vendedor}")==true) {

                    for (RegistroVenda item : itens){
                        //String valorRS = new DecimalFormat("R$ #,###.00").format(item.valor);
                        escreve.append("<tr>\n" + 
                            "<td>"+item.vendedor+"</td>" + 
                            "<td>"+item.valor+"</td>" + 
                            "<td>"+item.quantidade+"</td>\n" +
                            "</tr>\n");
                    }
                }else{
                    escreve.append(linha);
                }
            }
            modelo.close();
            escreve.close();
        } catch (Exception e) {
            System.out.println(Keys.alertas.erro_inesperado);
        }
        return Keys.alertas.msg_relatorio_gerado_com_sucesso;
    }

    public String ErroException(String msg, Exception e){
        String alert = msg + "\n#Error: " + e.getMessage();
        System.out.println(alert);
        return alert;
    }
}