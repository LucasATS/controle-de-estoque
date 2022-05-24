package controle.estoque;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import controle.Keys;

public class cnxSer  {

    public static void save(){
        try {
            FileOutputStream fos = new FileOutputStream(Keys.files.EstoqueBD);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(App.getEstoque());
            oos.flush();
            oos.close();


            fos = new FileOutputStream(Keys.files.GuardaVendasBD);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(App.getVendas());
            oos.flush();
            oos.close();
        
            fos = new FileOutputStream(Keys.files.Fincanceiro);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(App.getFinanceiro());
            oos.flush();
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Keys.alertas.erro_inesperado);
        }
    }

    public static void Carrega(){
        try {
            FileInputStream fis = new FileInputStream(Keys.files.EstoqueBD);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Estoque auxEstoque = (Estoque) ois.readObject();
            ois.close();
            App.setEstoque(auxEstoque);


            fis = new FileInputStream(Keys.files.GuardaVendasBD);
            ois = new ObjectInputStream(fis);
            GuardaVendas auxVendas = (GuardaVendas) ois.readObject();
            ois.close();
            App.setVendas(auxVendas);

            fis = new FileInputStream(Keys.files.Fincanceiro);
            ois = new ObjectInputStream(fis);
            Financeiro auxFinanceiro = (Financeiro) ois.readObject();
            ois.close();
            App.setFinanceiro(auxFinanceiro);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Keys.alertas.erro_inesperado);
        }
    }
    
}
