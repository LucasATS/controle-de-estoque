package controle;


public class Keys {
    public static class alertas{
        public static final String msg_venda_realizada = "Venda realizada com Sucesso.";
        public static final String msg_item_rmv_com_sucesso = "Item removido com Sucesso.";
        public static final String msg_item_add_com_sucesso = "Item adicionado com Sucesso.";
        public static final String msg_relatorio_gerado_com_sucesso = "Relatório gerado com Sucesso.";
        public static final String msg_Caixa_Iniciado = "Caixa iniciado com Sucesso.";
        

        public static final String erro_item_ja_existe = "Ops: Item já Existe...";
        public static final String erro_item_inexistente = "Ops: Item Inexistente...";
        public static final String erro_inesperado = "Ops: Ocorreu um erro Inesperado..."; 
        public static final String erro_arquivo_nao_encontrado = "Ops: Arquivo não Encontrado...";
    }

    public static class files{
        public static final String EstoqueBD = "bd/EstoqueBD.chup";
        public static final String Relatorio_html = "relatorio.html";
        public static final String GuardaVendasBD = "bd/GuardaVendasBD.chup";
        public static final String SaidaProdutoBD = "bd/SaidaProdutoBD.chup";
        public static final String Modelo_relatorio_html = "models/modelo.html";
        public static final String telaVenda_css= "CSS/telaVenda.css";
        public static final String styles_css= "CSS/styles.css";
        public static final String Fincanceiro = "bd/Financeiro.chup";
    }
}