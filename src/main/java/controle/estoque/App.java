package controle.estoque;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import javafx.geometry.Pos;

public class App extends Application {

    public Estoque estoque = new Estoque();
    public GuardaVendas guardaVenda = new GuardaVendas();

    public Aluno[] alunos = new Aluno[]{
        new Aluno("Bárbara Marcheti Fiorin", "2021101634","N30"),
        new Aluno("Gabriela Espinoza de Souza","2022103060","N20"),
        new Aluno("Lucas Almeida Tiburtino da Silva", "2021101577","N30"),
        new Aluno("Matheus Fernandes de Figueiredo", "2021101596","N30"),
        new Aluno("Raylla do Sol Dias", "2021101569","N30"),
        new Aluno("Thales Tayson do Nascimento Vargas", "2021101581","N30"),
    };
    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Controle de Estoque");
        tela_Principal(stage);
        stage.show();
    }

    public void tela_Principal(Stage stage){

        Pane painel = new Pane();
        ToolBar menu = cria_barraMenu(stage);

        GridPane tabelaNomes = nomesGrupo();
        
        Label hello = new Label("Seja Bem Vindo");
        hello.setLayoutX(10);
        hello.setLayoutY(40);

        Label tituloTabela = new Label("Alunos:");
        tituloTabela.setLayoutX(10);
        tituloTabela.setLayoutY(80);

        Scene sc = new Scene(painel,900,700);

        painel.getChildren().add(tituloTabela);
        painel.getChildren().add(tabelaNomes);
        painel.getChildren().add(menu);
        painel.getChildren().add(hello);

        stage.setScene(sc);
    }

    public void addProduto(Stage stage){
        Pane painel = new Pane();
        ToolBar menu = cria_barraMenu(stage);

        Label lb_nome = new Label("Produto: ");
        Label lb_quantidade = new Label("Quantidade: ");
        Label lb_valor = new Label("Valor: ");
        TextField tb_nome = new TextField();
        TextField tb_quantidade = new TextField();
        TextField tb_valor = new TextField();
        Button btn_adicionar = new Button("Adicionar ao estoque");

        btn_adicionar.setOnAction(evento ->{
            String retorno;
            retorno = estoque.novoItem(
                tb_nome.getText(), 
                Integer.parseInt(tb_quantidade.getText()), 
                Double.parseDouble(tb_valor.getText().replace(",", "."))
            );
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Estoque:");
            message.setHeaderText("Resultado:");
            message.setContentText(retorno);
            message.showAndWait();
            
            tela_Principal(stage);
        });

        lb_nome.setLayoutX(10);
        lb_nome.setLayoutY(40);

        lb_quantidade.setLayoutX(10);
        lb_quantidade.setLayoutY(80);

        lb_valor.setLayoutX(10);
        lb_valor.setLayoutY(120);

        tb_nome.setLayoutX(100);
        tb_nome.setLayoutY(40);

        tb_quantidade.setLayoutX(100);
        tb_quantidade.setLayoutY(80);

        tb_valor.setLayoutX(100);
        tb_valor.setLayoutY(120);

        btn_adicionar.setLayoutX(70);
        btn_adicionar.setLayoutY(160);
        
        Scene sc = new Scene(painel,900,700);

        painel.getChildren().add(menu);
        painel.getChildren().add(lb_nome);
        painel.getChildren().add(lb_quantidade);
        painel.getChildren().add(lb_valor);
        painel.getChildren().add(tb_nome);
        painel.getChildren().add(tb_quantidade);
        painel.getChildren().add(tb_valor);
        painel.getChildren().add(btn_adicionar);

        stage.setScene(sc);
    }

    public void delItem(Stage stage){
        Pane painel = new Pane();
        ToolBar menu = cria_barraMenu(stage);


        String itens[] = new String[estoque.quantItens()];
        for (int i=0; i< estoque.quantItens();i++){
            itens[i] = estoque.itens[i].nome;
        }
        
        Label lb_nome = new Label("Produto: ");
        ComboBox<String> cb_nome =new ComboBox<>(FXCollections.observableArrayList(itens));

        Button btn_deletar = new Button("Deletar");

        btn_deletar.setOnAction(evento ->{
            String retorno;
            retorno = estoque.deletaItem(cb_nome.getValue().toString());

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Estoque:");
            message.setHeaderText("Resultado:");
            message.setContentText(retorno);
            message.showAndWait();
            
            tela_Principal(stage);
        });

        lb_nome.setLayoutX(10);
        lb_nome.setLayoutY(40);

        cb_nome.setLayoutX(100);
        cb_nome.setLayoutY(40);

        btn_deletar.setLayoutX(70);
        btn_deletar.setLayoutY(80);

        
        Scene sc = new Scene(painel,900,700);

        painel.getChildren().add(menu);
        painel.getChildren().add(lb_nome);
        painel.getChildren().add(cb_nome);
        painel.getChildren().add(btn_deletar);

        stage.setScene(sc);
    }

    public void aumentaEstoque(Stage stage){
        Pane painel = new Pane();
        ToolBar menu = cria_barraMenu(stage);


        String itens[] = new String[estoque.quantItens()];
        for (int i=0; i< estoque.quantItens();i++){
            itens[i] = estoque.itens[i].nome;
        }
        
        Label lb_nome = new Label("Produto: ");
        ComboBox<String> cb_nome =new ComboBox<>(
            FXCollections.observableArrayList(itens)
        );
        Label lb_quantidade = new Label("Adicionar +: ");
        TextField tb_quantidade = new TextField();
        Button btn_add = new Button("Adicionar");

        btn_add.setOnAction(evento ->{
            String retorno;
            retorno = estoque.adicionaQuantidade(
                cb_nome.getValue().toString(),
                Integer.parseInt(tb_quantidade.getText())
            );

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Estoque:");
            message.setHeaderText("Resultado:");
            message.setContentText(retorno);
            message.showAndWait();
            
            tela_Principal(stage);
        });

        lb_nome.setLayoutX(10);
        lb_nome.setLayoutY(40);

        cb_nome.setLayoutX(100);
        cb_nome.setLayoutY(40);

        lb_quantidade.setLayoutX(10);
        lb_quantidade.setLayoutY(80);

        tb_quantidade.setLayoutX(100);
        tb_quantidade.setLayoutY(80);

        btn_add.setLayoutX(70);
        btn_add.setLayoutY(120);

        Scene sc = new Scene(painel,900,700);

        painel.getChildren().add(menu);
        painel.getChildren().add(lb_nome);
        painel.getChildren().add(cb_nome);
        painel.getChildren().add(lb_quantidade);
        painel.getChildren().add(tb_quantidade);
        painel.getChildren().add(btn_add);

        stage.setScene(sc);
    }

    public void alteraValor(Stage stage){
        Pane painel = new Pane();
        ToolBar menu = cria_barraMenu(stage);


        String itens[] = new String[estoque.quantItens()];
        for (int i=0; i< estoque.quantItens();i++){
            itens[i] = estoque.itens[i].nome;
        }
        
        Label lb_nome = new Label("Produto: ");
        ComboBox<String> cb_nome =new ComboBox<>(
            FXCollections
            .observableArrayList(itens)
        );
        Label lb_valor = new Label("novo Valor: ");
        TextField tb_valor = new TextField();
        Button btn_salva = new Button("Salvar");

        btn_salva.setOnAction(evento ->{
            String retorno;
            retorno = estoque.alterarValorMedio(
                cb_nome.getValue().toString(),
                Double.parseDouble(tb_valor.getText().replace(",", "."))
            );

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Estoque:");
            message.setHeaderText("Resultado:");
            message.setContentText(retorno);
            message.showAndWait();
            
            tela_Principal(stage);

        });
        lb_nome.setLayoutX(10);
        lb_nome.setLayoutY(40);

        cb_nome.setLayoutX(100);
        cb_nome.setLayoutY(40);

        lb_valor.setLayoutX(10);
        lb_valor.setLayoutY(80);

        tb_valor.setLayoutX(100);
        tb_valor.setLayoutY(80);

        btn_salva.setLayoutX(70);
        btn_salva.setLayoutY(120);

        Scene sc = new Scene(painel,900,700);

        painel.getChildren().add(menu);
        painel.getChildren().add(lb_nome);
        painel.getChildren().add(cb_nome);
        painel.getChildren().add(lb_valor);
        painel.getChildren().add(tb_valor);
        painel.getChildren().add(btn_salva);

        stage.setScene(sc);
    }
    
    public ToolBar cria_barraMenu(Stage stage){

        Button btn_principal = new Button("Inicio");
        Button btn_novaVenda = new Button("Nova Venda");
        Button btn_novoItem = new Button("Novo Item");
        Button btn_delItem = new Button("Deletar Item");
        Button btn_addAoItem = new Button("Adicionar quantidade ao Item");
        Button btn_alteraValor = new Button("Alterar valor do Item");
        Button btn_geraHTML = new Button("Gera relatorio HTML");

        ToolBar toolbar = new ToolBar();
        toolbar.getItems().add(btn_principal);
        toolbar.getItems().add(btn_novaVenda);
        toolbar.getItems().add(btn_novoItem);
        toolbar.getItems().add(btn_delItem);
        toolbar.getItems().add(btn_addAoItem);
        toolbar.getItems().add(btn_alteraValor);
        toolbar.getItems().add(btn_geraHTML);
        btn_principal.setOnAction(evento ->{
            tela_Principal(stage);
        });
        btn_novoItem.setOnAction(evento ->{
            addProduto(stage);
        });
        btn_delItem.setOnAction(evento ->{
            delItem(stage);
        });
        btn_addAoItem.setOnAction(evento ->{
            aumentaEstoque(stage);
        });
        btn_alteraValor.setOnAction(evento ->{
            alteraValor(stage);
        });
        btn_geraHTML.setOnAction(evento ->{
            String r1, r2, retorno;
            retorno = "";
            r1 = guardaVenda.geraHTML();
            r2 = estoque.geraHTML();
            if(r1 == r2) retorno = r2;           

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Relatório:");
            message.setHeaderText("Resultado:");
            message.setContentText(retorno);
            message.showAndWait();
            
            tela_Principal(stage);
        });
        btn_novaVenda.setOnAction(evento ->{
            telaVenda venda = new telaVenda();
            venda.view(stage);
        });
        toolbar.setLayoutX(0);
        toolbar.setLayoutY(0);

        return toolbar;
    }
    public GridPane nomesGrupo(){
        int count = alunos.length;

        Label[][] labels = new Label[count][3];
        for (int i=0;i<count;i++){
            labels[i][0] = new Label(alunos[i].nome);
            labels[i][1] = new Label(alunos[i].ra);
            labels[i][2] = new Label(alunos[i].turma);
        }
        GridPane tabela = new GridPane();
        tabela.setStyle(
            "-fx-background-color: white;"+         
            "-fx-grid-lines-visible: true"
        );
        Label[] titulo = new Label[]{
            new Label("NOME"),
            new Label("TURMA"),
            new Label("RA")
        };
        titulo[0].setMaxWidth(250);
        titulo[0].setMinWidth(250);
        titulo[1].setMaxWidth(70);
        titulo[1].setMinWidth(70);
        titulo[2].setMinWidth(100);
        titulo[2].setMaxWidth(100);
        titulo[0].setAlignment(Pos.CENTER);
        titulo[1].setAlignment(Pos.CENTER);
        titulo[2].setAlignment(Pos.CENTER);
        String style = 
            "-fx-background-color: black;"+
            "-fx-text-fill: white;"+
            "-fx-font-weight: bold;"
        ;
        titulo[0].setStyle(style);
        titulo[1].setStyle(style);
        titulo[2].setStyle(style);

        tabela.add(titulo[0], 0, 0);
        tabela.add(titulo[1], 1, 0);
        tabela.add(titulo[2], 2, 0);
        for (int i=0;i<count;i++){
            tabela.add(labels[i][0], 0, i+1);
            tabela.add(labels[i][1], 2, i+1);
            tabela.add(labels[i][2], 1, i+1);
            labels[i][0].setMaxWidth(250);
            labels[i][0].setMinWidth(250);
            labels[i][2].setMaxWidth(70);
            labels[i][2].setMinWidth(70);
            labels[i][1].setMaxWidth(100);
            labels[i][1].setMinWidth(100);
            labels[i][1].setAlignment(Pos.CENTER);
            labels[i][2].setAlignment(Pos.CENTER);
        }
        tabela.setLayoutX(10);
        tabela.setLayoutY(100);
        return tabela;                
    }
    public static void main(String[] args) {
        launch();
    }
}