package controle.estoque;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat.Style;
import controle.Keys;
import javafx.geometry.Pos;

public class telaVenda extends App {

    // table view de produtos sendo vendidos
    private final TableView<CacheVenda> carrinho = new TableView<>();
    private final ObservableList<CacheVenda> listVenda = FXCollections.observableArrayList();

    public void view(Stage stage){
        Pane painel = new Pane();
        ToolBar menu = cria_barraMenu(stage);
    

        //itens de estoque
        String itens[] = new String[estoque.quantItens()];
        for (int i=0; i< estoque.quantItens();i++){
            itens[i] = estoque.itens[i].nome;
        }
        ComboBox<String> produto = new ComboBox<>(
            FXCollections.observableArrayList(itens)
        );


        //lista de Vendedores (Alunos)
        ArrayList<String> vendedores = new ArrayList<String>();
        for (Aluno vendedor : alunos){
            vendedores.add(vendedor.nome);
        }
        ComboBox<String> vendedor = new ComboBox<>(
            FXCollections.observableArrayList(vendedores)
        );

        // textbox para add quantidade e valor mais o btn +

        TextField tb_quantidade = new TextField();
        TextField tb_valor = new TextField();
        Button btn_adicionar = new Button("+");

        carrinho.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        carrinho.setPrefWidth(600);
        carrinho.setPrefHeight(600);

        carrinho.setItems(listVenda);

        TableColumn<CacheVenda, String> col1 = new TableColumn<>();
        col1.setCellValueFactory(new PropertyValueFactory<>("str"));

        

        carrinho.getColumns().addAll(col1);

        buttonRemove();
        

        

        btn_adicionar.setOnAction(evento ->{
            
            listVenda.add(new CacheVenda(
                produto.getValue().toString(),
                vendedor.getValue().toString(), 
                Integer.parseInt(tb_quantidade.getText()), 
                Double.parseDouble(tb_valor.getText().replace(",", "."))
            ));
            
        });


        vendedor.setLayoutX(10);
        vendedor.setLayoutY(40);

        produto.setLayoutX(10);
        produto.setLayoutY(80);

        tb_quantidade.setLayoutX(215);
        tb_quantidade.setLayoutY(80);

        tb_valor.setLayoutX(295);
        tb_valor.setLayoutY(80);

        btn_adicionar.setLayoutX(375);
        btn_adicionar.setLayoutY(80);

        carrinho.setLayoutX(10);
        carrinho.setLayoutY(120);

        ocultaHeader();
        
        Scene sc = new Scene(painel,900,700);
        sc.getStylesheets().add(Keys.files.camninholocal+Keys.files.telaVenda_css);
        //sc.getStylesheets().add(getClass().getResource(Keys.files.telaVenda_css).toExternalForm());
        painel.getChildren().add(menu);
        painel.getChildren().add(vendedor);
        painel.getChildren().add(produto);
        painel.getChildren().add(tb_quantidade);
        painel.getChildren().add(tb_valor);
        painel.getChildren().add(btn_adicionar);
        painel.getChildren().add(carrinho);
        stage.setScene(sc);
    }
    private void buttonRemove() {
        TableColumn<CacheVenda, Void> colBtn = new TableColumn();

        Callback<TableColumn<CacheVenda, Void>, TableCell<CacheVenda, Void>> cellFactory = new Callback<TableColumn<CacheVenda, Void>, TableCell<CacheVenda, Void>>() {
            @Override
            public TableCell<CacheVenda, Void> call(final TableColumn<CacheVenda, Void> param) {
                final TableCell<CacheVenda, Void> cell = new TableCell<CacheVenda, Void>() {

                    
                    private final Button btn = new Button("REMOVER");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            System.out.println(getTableView().getItems().get(getIndex()).str);
                            getTableView().getItems().remove(getIndex());
                        });
                        btn.setStyle("-fx-background-color: black;"+
                        "-fx-text-fill: white;");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        carrinho.getColumns().add(colBtn);
    }

    private void ocultaHeader() {
        carrinho.skinProperty().addListener((a, b, newSkin) ->
        {
          Pane header = (Pane) carrinho.lookup("TableHeaderRow");
          header.setMinHeight(0);
          header.setPrefHeight(0);
          header.setMaxHeight(0);
          header.setVisible(false);
        });
    }
    public class CacheVenda {

        private String nome, vendedor, str;
        private int quantidade;
        private double valor;
        
        private CacheVenda(String nome, String vendedor, int quantidade, double valor){
            this.nome = nome;
            this.quantidade = quantidade;
            this.valor = valor;
    
            this.str = 
            nome + " - " +
            quantidade + " - " +
            valor + " - " +
            (quantidade * valor)
            ;
            
            this.vendedor = vendedor;
        }
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
        public String getVendedor() {
            return vendedor;
        }

        public void setVendedor(String vendedor) {
            this.vendedor = vendedor;
        }
        public int getQtd() {
            return quantidade;
        }

        public void setQtd(int quantidade) {
            this.quantidade = quantidade;
        }
        public Double getValor() {
            return valor;
        }

        public void setValor(Double valor) {
            this.valor = valor;
        }
        public String getStr(){
            return str;
        }
    }
}
