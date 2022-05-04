package controle.estoque;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;

import controle.Keys;

public class telaVenda extends App {

    // table view de produtos sendo vendidos
    private final TableView<RegistroVenda> carrinho = new TableView<>();
    private final TableView<RegistroVenda> visCupon = new TableView<>();
    private final ObservableList<RegistroVenda> cacheVenda = FXCollections.observableArrayList();
    private final ObservableList<RegistroVenda> listCupon = FXCollections.observableArrayList();

    public void view(Stage stage){
        Pane painel = new Pane();
        ToolBar menu = cria_barraMenu(stage);

        carrinho.setId("carrinho");
        visCupon.setId("visCupon");

        //itens de estoque
        String itens[] = new String[estoque.quantItens()];
        for (int i=0; i< estoque.quantItens();i++){
            itens[i] = estoque.itens[i].nome;
        }
        ComboBox<String> produto = new ComboBox<>(
            FXCollections.observableArrayList(itens)
        );
        produto.setId("produto");


        //lista de Vendedores (Alunos)
        ArrayList<String> vendedores = new ArrayList<String>();
        for (Aluno vendedor : alunos){
            vendedores.add(vendedor.nome);
        }
        ComboBox<String> vendedor = new ComboBox<>(
            FXCollections.observableArrayList(vendedores)
        );
        vendedor.setId("vendedor");


        // textbox para add quantidade e valor mais os btn's

        TextField tb_quantidade = new TextField();
        tb_quantidade.setId("tb_quantidade");
        TextField tb_valor = new TextField();
        tb_valor.setId("tb_valor");
        Button btn_adicionar = new Button("+");
        btn_adicionar.setId("btn_adicionar");
        Button registrarVenda = new Button("REGISTRAR VENDA");
        registrarVenda.setId("registrarVenda");

        //Config das Tables
        carrinho.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        carrinho.setPrefWidth(435);
        carrinho.setPrefHeight(550);
        visCupon.setEditable(false);

        visCupon.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        visCupon.setPrefWidth(300);
        visCupon.setPrefHeight(595);
        visCupon.setEditable(false);

        //Adicionando a Lista
        carrinho.setItems(cacheVenda);
        visCupon.setItems(listCupon);

        TableColumn<RegistroVenda, String> col1 = new TableColumn<>();
        col1.setCellValueFactory(new PropertyValueFactory<>("str"));
        col1.setId("col1");

        TableColumn<RegistroVenda, String> colTitle = new TableColumn<>("QUITANDA");
        TableColumn<RegistroVenda, String> colSubTitle = new TableColumn<>("PROD | QTD | VALOR.Ú | VALOR.T");

        colSubTitle.setCellValueFactory(new PropertyValueFactory<>("str"));
        carrinho.getColumns().addAll(col1);
        colTitle.getColumns().addAll(colSubTitle);
        visCupon.getColumns().addAll(colTitle);
        buttonRemove();
        
        // Event click button +
        btn_adicionar.setOnAction(evento ->{
            if ((produto.getValue() != null && vendedor.getValue() != null && 
                tb_quantidade.getText() != null && tb_valor.getText() != null)
            ){
                if (produto.getValue().toString() != "" && vendedor.getValue().toString() != "" && 
                tb_quantidade.getText() != "" && tb_valor.getText() != ""){
                    cacheVenda.add(new RegistroVenda(
                        produto.getValue().toString(),
                        vendedor.getValue().toString(), 
                        Integer.parseInt(tb_quantidade.getText()), 
                        Double.parseDouble(tb_valor.getText().replace(",", "."))
                    ));
                    geraCupon();
                }
            }
        });


        vendedor.setLayoutX(10);
        vendedor.setLayoutY(40);

        produto.setLayoutX(10);
        produto.setLayoutY(80);

        tb_quantidade.setLayoutX(215);
        tb_quantidade.setLayoutY(80);

        tb_valor.setLayoutX(300);
        tb_valor.setLayoutY(80);

        btn_adicionar.setLayoutX(385);
        btn_adicionar.setLayoutY(80);

        carrinho.setLayoutX(10);
        carrinho.setLayoutY(120);

        visCupon.setLayoutX(500);
        visCupon.setLayoutY(40);

        registrarVenda.setLayoutX(500);
        registrarVenda.setLayoutY(640);

        ocultaHeader();
        
        Scene sc = new Scene(painel,900,700);
        sc.getStylesheets().add(getClass().getResource(Keys.files.telaVenda_css).toExternalForm());
        painel.getChildren().add(menu);
        painel.getChildren().add(vendedor);
        painel.getChildren().add(produto);
        painel.getChildren().add(tb_quantidade);
        painel.getChildren().add(tb_valor);
        painel.getChildren().add(btn_adicionar);
        painel.getChildren().add(carrinho);
        painel.getChildren().add(visCupon);
        painel.getChildren().add(registrarVenda);
        stage.setScene(sc);
    }
    private void buttonRemove() {
        TableColumn<RegistroVenda, Void> colBtn = new TableColumn();

        Callback<TableColumn<RegistroVenda, Void>, TableCell<RegistroVenda, Void>> cellFactory = new Callback<TableColumn<RegistroVenda, Void>, TableCell<RegistroVenda, Void>>() {
            @Override
            public TableCell<RegistroVenda, Void> call(final TableColumn<RegistroVenda, Void> param) {
                final TableCell<RegistroVenda, Void> cell = new TableCell<RegistroVenda, Void>() {

                    
                    private final Button btn = new Button("REMOVER");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            getTableView().getItems().remove(getIndex());
                            geraCupon();
                        });
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
        colBtn.setId("colBtn");
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
    private void geraCupon() {
        listCupon.clear();
        listCupon.addAll(cacheVenda);
        int indice = -1;
        double soma = 0;
        for (int i = 0; i < listCupon.size(); i++) {
            if (listCupon.get(i).getNome()=="total") {
                indice=i;
            }else{
                soma += listCupon.get(i).getValor()*listCupon.get(i).getQtd();
            }
        }
        if (indice != -1) listCupon.remove(indice);
        listCupon.add(new RegistroVenda("total","total", 0, soma));
    }

}
