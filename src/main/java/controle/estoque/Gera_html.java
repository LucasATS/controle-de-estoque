package controle.estoque;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import controle.Keys;

public class Gera_html {

    private final GuardaVendas vendas = new GuardaVendas();
    private final Estoque estoque = new Estoque();

    public  Gera_html(){
        try {
            File arquivomodel = new File(Keys.files.Modelo_relatorio_html);
            Scanner model = new Scanner(arquivomodel);
            String linha;
            ArrayList<String> escreve = new ArrayList<String>();
            while (model.hasNextLine()) {
                linha = model.nextLine();
                escreve.add(linha);
            }

            ArrayList<String> modelo = vendas.geraHTML(escreve);
            modelo = estoque.geraHTML(modelo);

            File arquivo = new File(Keys.files.Relatorio_html);
            arquivo.createNewFile();
            FileWriter novo_item = new FileWriter(arquivo,false);
            for (String string : modelo) {
                System.out.println(string);
                novo_item.append(string);
            }
            model.close();
            novo_item.close();
        } catch (Exception e) {
            System.out.println(Keys.alertas.erro_inesperado);
        }
    }
}
