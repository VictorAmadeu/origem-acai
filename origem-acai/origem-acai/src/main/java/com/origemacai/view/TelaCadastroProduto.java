package com.origemacai.view; // Define o pacote onde a classe está localizada

// Importações de classes do projeto Origem Açaí
import com.origemacai.model.Produto; // Modelo da entidade Produto
import com.origemacai.service.ProdutoService; // Serviço de persistência de produto
import com.origemacai.util.Idioma; // Utilitário para internacionalização
import com.origemacai.config.SpringContext; // Classe para acesso a beans do Spring

// Importações JavaFX para construção da interface gráfica
import javafx.geometry.Insets; // Define margens internas nos layouts
import javafx.scene.Scene; // Representa o conteúdo da janela
import javafx.scene.control.*; // Contém todos os controles como Label, TextField, Button
import javafx.scene.layout.*; // Layouts como VBox, GridPane, HBox
import javafx.stage.Stage; // Representa a janela principal

import java.math.BigDecimal; // Classe para representar valores monetários com precisão

public class TelaCadastroProduto { // Classe pública que representa a tela de cadastro de produto

    // Método principal chamado para abrir a janela de cadastro de produto
    public void start(Stage stageAnterior) {

        Idioma idioma = Idioma.getInstance(); // Obtém a instância do idioma atual (Português ou Espanhol)

        Stage stage = new Stage(); // Cria uma nova janela JavaFX
        stage.setTitle(idioma.get("titulo.cadastro")); // Define o título da janela com base na tradução

        ProdutoService produtoService = SpringContext.getBean(ProdutoService.class); // Recupera o serviço do Spring

        // Criação dos rótulos (labels) com os textos traduzidos
        Label lblNome = new Label(idioma.get("label.nome")); // Ex: "Nome:"
        Label lblTipo = new Label(idioma.get("label.tipo")); // Ex: "Tipo:"
        Label lblPreco = new Label(idioma.get("label.preco")); // Ex: "Preço:"
        Label lblQuantidade = new Label(idioma.get("label.quantidade")); // Ex: "Quantidade em Estoque:"

        // Campos de entrada de dados
        TextField txtNome = new TextField();       // Campo para inserir o nome do produto
        TextField txtTipo = new TextField();       // Campo para inserir o tipo do produto
        TextField txtPreco = new TextField();      // Campo para inserir o preço
        TextField txtQuantidade = new TextField(); // Campo para inserir a quantidade em estoque

        // Criação do botão "Limpar" com sua ação associada
        Button btnLimpar = new Button(idioma.get("botao.limpar")); // Ex: "Limpar"
        btnLimpar.setOnAction(e -> {
            txtNome.clear();       // Limpa o campo nome
            txtTipo.clear();       // Limpa o campo tipo
            txtPreco.clear();      // Limpa o campo preço
            txtQuantidade.clear(); // Limpa o campo quantidade
        });

        // Criação do botão "Salvar" com sua ação associada
        Button btnSalvar = new Button(idioma.get("botao.salvar")); // Ex: "Salvar"
        btnSalvar.setOnAction(e -> {
            try {
                // Obtém os valores dos campos
                String nome = txtNome.getText(); // Captura o texto do campo nome
                String tipo = txtTipo.getText(); // Captura o texto do campo tipo
                BigDecimal preco = new BigDecimal(txtPreco.getText()); // Converte o texto do preço
                int quantidade = Integer.parseInt(txtQuantidade.getText()); // Converte o texto da quantidade

                // Cria um novo objeto Produto com os dados preenchidos
                Produto produto = new Produto(nome, tipo, preco, quantidade);

                // Salva o produto no banco de dados
                produtoService.salvar(produto);

                // Exibe mensagem de sucesso para o usuário
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        idioma.get("titulo.sucesso"),
                        idioma.get("mensagem.sucesso"));

                // ✅ Limpa os campos diretamente após salvar (correção definitiva)
                txtNome.clear();
                txtTipo.clear();
                txtPreco.clear();
                txtQuantidade.clear();

            } catch (Exception ex) {
                // Exibe uma mensagem de erro em caso de falha ao salvar
                mostrarAlerta(Alert.AlertType.ERROR,
                        idioma.get("titulo.erro"),
                        idioma.get("mensagem.erro") + ": " + ex.getMessage());
            }
        });

        // Cria um layout em grade para organizar os rótulos e campos
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20)); // Espaço interno do grid
        grid.setHgap(10); // Espaço horizontal entre colunas
        grid.setVgap(10); // Espaço vertical entre linhas

        // Define proporções para as colunas (35% rótulo, 65% campo)
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(35); // Coluna 0 → rótulos

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(65); // Coluna 1 → campos de entrada

        grid.getColumnConstraints().addAll(col1, col2); // Aplica as restrições

        // Adiciona os rótulos e campos ao grid (coluna, linha)
        grid.add(lblNome, 0, 0);        grid.add(txtNome, 1, 0);
        grid.add(lblTipo, 0, 1);        grid.add(txtTipo, 1, 1);
        grid.add(lblPreco, 0, 2);       grid.add(txtPreco, 1, 2);
        grid.add(lblQuantidade, 0, 3);  grid.add(txtQuantidade, 1, 3);

        // Cria um layout horizontal (HBox) para os botões
        HBox hboxBotoes = new HBox(10, btnLimpar, btnSalvar); // Espaço de 10px entre os botões
        hboxBotoes.setPadding(new Insets(10, 0, 0, 0)); // Margem superior de 10px

        // Cria o layout vertical principal que organiza o grid e os botões
        VBox layout = new VBox(10, grid, hboxBotoes); // Espaçamento vertical entre os elementos
        layout.setPadding(new Insets(20)); // Margem interna da VBox

        VBox.setVgrow(grid, Priority.ALWAYS); // Faz o grid crescer junto com o redimensionamento da janela

        // Cria a cena com tamanho inicial
        Scene scene = new Scene(layout, 500, 300); // Largura: 500px, Altura: 300px

        // Aplica o CSS externo (localizado em /resources/styles/telacadastroproduto.css)
        scene.getStylesheets().add(getClass()
                .getResource("/styles/telacadastroproduto.css")
                .toExternalForm());

        stage.setScene(scene);  // Aplica a cena na janela
        stage.show();           // Exibe a nova janela
        stageAnterior.hide();  // Oculta a janela anterior (ex: dashboard)
    }

    // Método utilitário que exibe alertas de informação ou erro
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);       // Cria o alerta do tipo especificado
        alerta.setTitle(titulo);              // Define o título da janela de alerta
        alerta.setHeaderText(null);           // Remove cabeçalho do alerta
        alerta.setContentText(mensagem);      // Define a mensagem principal
        alerta.showAndWait();                 // Exibe o alerta e espera o usuário clicar em OK
    }
}
