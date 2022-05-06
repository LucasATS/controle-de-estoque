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
import java.util.Arrays;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;

import controle.Keys;
import controle.Keys.alertas;

public class telaVenda extends App {

    // table view de produtos sendo vendidos
    private final GuardaVendas vendas = new GuardaVendas();
    private final Estoque estoque = new Estoque();
    private final TableView<SaidaProduto> carrinho = new TableView<>();
    private final TableView<SaidaProduto> visCupon = new TableView<>();
    private final ObservableList<SaidaProduto> cacheVenda = FXCollections.observableArrayList();
    private final ObservableList<SaidaProduto> listCupon = FXCollections.observableArrayList();

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

        TableColumn<SaidaProduto, String> col1 = new TableColumn<>();
        col1.setCellValueFactory(new PropertyValueFactory<>("str"));
        col1.setId("col1");

        TableColumn<SaidaProduto, String> colTitle = new TableColumn<>("QUITANDA");
        TableColumn<SaidaProduto, String> colSubTitle = new TableColumn<>("PROD | QTD | VALOR.Ú | VALOR.T");

        colSubTitle.setCellValueFactory(new PropertyValueFactory<>("str"));
        carrinho.getColumns().addAll(Arrays.asList(col1));
        colTitle.getColumns().addAll(Arrays.asList(colSubTitle));
        visCupon.getColumns().addAll(Arrays.asList(colTitle));
        buttonRemove();
        

        // Event seleciona produto
        produto.setOnAction(evento ->{
            try {
                double valor = estoque.getItem(produto.getValue().toString()).valor;
                tb_valor.setText(String.valueOf(valor));
            } catch (Exception e) {
                tb_valor.setText("0.0");
            }
        });
        // Event click button +
        btn_adicionar.setOnAction(evento ->{
            if ((produto.getValue() != null && 
                tb_quantidade.getText() != null && tb_valor.getText() != null)
            ){
                if (produto.getValue().toString() != "" && 
                tb_quantidade.getText() != "" && tb_valor.getText() != ""){

                    cacheVenda.add(new SaidaProduto(
                        produto.getValue().toString(),
                        Integer.parseInt(tb_quantidade.getText()), 
                        Double.parseDouble(tb_valor.getText().replace(",", "."))
                    ));
                    produto.setValue(null);
                    tb_quantidade.setText(null); 
                    tb_valor.setText(null);
                    geraCupon();
                }
            }
        });

        // Event click button registrarVenda
        registrarVenda.setOnAction(evento ->{
            String retorno = "Selecione pelo menos um produto";
            if(vendedor.getValue() != null){
                if(vendedor.getValue().toString() != ""){

                    ArrayList<SaidaProduto> produtos = new ArrayList<SaidaProduto>();
                    produtos.addAll(cacheVenda);

                    RegistroVenda registro = new RegistroVenda(
                        vendas.novaId(), vendedor.getValue().toString(), produtos
                    );
                    vendas.novaVenda(registro);

                    vendedor.setValue(null);
                    produto.setValue(null);
                    vendedor.setValue(null);
                    tb_quantidade.setText(null); 
                    tb_valor.setText(null);
                    cacheVenda.clear();
                    listCupon.clear();
                    retorno = alertas.msg_venda_realizada;
                    
                }else{retorno = "Selecione um vendedor";}
            }else{retorno = "Selecione um vendedor";}
            

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Registro de Venda:");
            message.setHeaderText("Resultado:");
            message.setContentText(retorno);
            message.showAndWait();

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
        
        Scene sc = new Scene(painel,810,675);
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
        TableColumn<SaidaProduto, Void> colBtn = new TableColumn<>();

        Callback<TableColumn<SaidaProduto, Void>, TableCell<SaidaProduto, Void>> cellFactory = new Callback<TableColumn<SaidaProduto, Void>, TableCell<SaidaProduto, Void>>() {
            @Override
            public TableCell<SaidaProduto, Void> call(final TableColumn<SaidaProduto, Void> param) {
                final TableCell<SaidaProduto, Void> cell = new TableCell<SaidaProduto, Void>() {

                    
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
        listCupon.add(new SaidaProduto("total", 0, soma));
    }

}
