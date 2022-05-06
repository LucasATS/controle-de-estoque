package controle.estoque;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.Scanner;

import controle.Keys;

public class Gera_html {

    private final GuardaVendas vendas = new GuardaVendas();
    private final Estoque estoque = new Estoque();

    public  Gera_html(Aluno[] alunos){
        try {
            File arquivomodel = new File(Keys.files.Modelo_relatorio_html);
            Scanner model = new Scanner(arquivomodel);
            String linha;
            ArrayList<String> escreve = new ArrayList<String>();
            while (model.hasNextLine()) {
                linha = model.nextLine();
                if (linha.equals("{Titulo}") == true) {
                    escreve.add("RELATÓRIO - QUITANDA");
                    continue;
                }
                escreve.add(linha);
            }

            ArrayList<String> modelo = vendas.geraHTML(escreve,alunos);
            modelo = estoque.geraHTML(modelo);

            File arquivo = new File(Keys.files.Relatorio_html);
            arquivo.createNewFile();
            
            FileOutputStream fos2 = new FileOutputStream(arquivo);
            OutputStreamWriter osw2 = new OutputStreamWriter(fos2, "ISO-8859-1");
            Writer novo_item = new BufferedWriter(osw2);

            for (String string : modelo) {
                novo_item.append(string + "\n");
            }
            model.close();
            novo_item.close();
        } catch (Exception e) {
            System.out.println(Keys.alertas.erro_inesperado);
        }
    }

    public Gera_html() {
    }
}
