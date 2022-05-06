package controle.estoque;

import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import controle.Keys;
import controle.Keys.files;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;

public class GuardaVendas {

    ArrayList<RegistroVenda> itens = new  ArrayList<RegistroVenda>();

    Estoque estoque = new Estoque();

    GuardaVendas(){

        try {
            File arquivo1 = new File(files.GuardaVendasBD);
            File arquivo2 = new File(files.SaidaProdutoBD);

            Scanner GuardaVendasBD = new Scanner(arquivo1, "ISO-8859-1");
            Scanner SaidaProdutoBD;

            ArrayList<SaidaProduto> saidas;

            while (GuardaVendasBD.hasNextLine()) {

                String[] dados = GuardaVendasBD.nextLine().split(";");
                int id =  Integer.parseInt(dados[0]);
                String vendedor = dados[1];
                saidas = new ArrayList<SaidaProduto>();

                SaidaProdutoBD = new Scanner(arquivo2, "ISO-8859-1");

                while (SaidaProdutoBD.hasNextLine()){

                    String[] ler = SaidaProdutoBD.nextLine().split(";");
                    if (ler[0].equals(String.valueOf(id)) == true){
                        saidas.add(new SaidaProduto(ler[1], Integer.parseInt(ler[2]), Double.parseDouble(ler[3])));
                    }
                }

                SaidaProdutoBD.close();

                RegistroVenda registro = new RegistroVenda(
                    id, vendedor, saidas
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

        String fileVendas = "", fileSaidas = "";

        for (RegistroVenda item : itens){
            fileVendas += item.getId() + ";" + item.getVendedor()+ "\n";
            for (SaidaProduto produto : item.getProdutos()){
                fileSaidas += item.getId() + ";" + produto.getNome() + ";" + produto.getQtd() + ";" + produto.getValor() + "\n";
            }
        }

        try {
            File arquivo = new File(files.GuardaVendasBD);

            arquivo.createNewFile();
            
            FileOutputStream fos = new FileOutputStream(arquivo);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "ISO-8859-1");
            Writer escreve = new BufferedWriter(osw); 

            escreve.write(fileVendas);

            escreve.close();

            try {

                File arquivo2 = new File(files.SaidaProdutoBD);

                arquivo2.createNewFile();
    
                FileOutputStream fos2 = new FileOutputStream(arquivo2);
                OutputStreamWriter osw2 = new OutputStreamWriter(fos2, "ISO-8859-1");
                Writer escreve2 = new BufferedWriter(osw2); 
    
                escreve2.write(fileSaidas);

                escreve2.close();

            } catch (Exception e) {
                ErroException(Keys.alertas.erro_inesperado, e);
            }
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
        itens.add(registro);

    }

    String novaVenda(RegistroVenda registro){
        
        itens.add(registro);
        salvaFile();
        
        return Keys.alertas.msg_venda_realizada;
    }

    String deletaRegistro(int id){

        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getId()==id) itens.remove(i);
        }
        
        salvaFile();

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