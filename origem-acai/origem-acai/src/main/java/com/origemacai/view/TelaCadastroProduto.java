// Define o pacote onde esta classe está localizada
package com.origemacai.view;

// Importa o modelo de dados Produto
import com.origemacai.model.Produto;

// Importa o gerenciador de idioma selecionado (Singleton)
import com.origemacai.util.Idioma;

// Importa classes do JavaFX
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.math.BigDecimal; // Usado para valores monetários

// Classe que representa a tela de cadastro de produtos
public class TelaCadastroProduto {

    // Método principal que exibe a tela
    public void start(Stage stageAnterior) {

        // Obtém o idioma atual selecionado
        Idioma idioma = Idioma.getInstance();

        // Cria os rótulos com textos traduzidos
        Label labelNome = new Label(idioma.get("label.nome"));
        Label labelTipo = new Label(idioma.get("label.tipo"));
        Label labelPreco = new Label(idioma.get("label.preco"));
        Label labelEstoque = new Label(idioma.get("label.quantidade"));

        // Cria os campos de entrada
        TextField campoNome = new TextField();
        TextField campoTipo = new TextField();
        TextField campoPreco = new TextField();
        TextField campoEstoque = new TextField();

        // Cria os botões com texto traduzido
        Button btnSalvar = new Button(idioma.get("botao.salvar"));
        Button btnLimpar = new Button(idioma.get("botao.limpar"));
        Button btnVoltar = new Button(idioma.get("botao.voltar"));

        // Ação do botão "Salvar"
        btnSalvar.setOnAction(e -> {
            try {
                // Coleta os dados dos campos
                String nome = campoNome.getText();
                String tipo = campoTipo.getText();
                BigDecimal preco = new BigDecimal(campoPreco.getText());
                int quantidade = Integer.parseInt(campoEstoque.getText());

                // Cria um novo produto com os dados
                Produto novo = new Produto(nome, tipo, preco, quantidade);

                // Exibe mensagem de sucesso
                mostrarAlerta(idioma.get("mensagem.sucesso"), Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                // Exibe mensagem de erro com a descrição
                mostrarAlerta(idioma.get("mensagem.erro") + ": " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Ação do botão "Limpar"
        btnLimpar.setOnAction(e -> {
            campoNome.clear();
            campoTipo.clear();
            campoPreco.clear();
            campoEstoque.clear();
        });

        // Ação do botão "Voltar"
        btnVoltar.setOnAction(e -> {
            stageAnterior.show(); // Reexibe a tela de login
            ((Stage) btnVoltar.getScene().getWindow()).close(); // Fecha esta janela
        });

        // Cria o layout em grade
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setVgap(10);
        layout.setHgap(10);

        // Adiciona os componentes ao layout
        layout.add(labelNome, 0, 0);
        layout.add(campoNome, 1, 0);
        layout.add(labelTipo, 0, 1);
        layout.add(campoTipo, 1, 1);
        layout.add(labelPreco, 0, 2);
        layout.add(campoPreco, 1, 2);
        layout.add(labelEstoque, 0, 3);
        layout.add(campoEstoque, 1, 3);
        layout.add(btnSalvar, 0, 4);
        layout.add(btnLimpar, 1, 4);
        layout.add(btnVoltar, 1, 5);

        // Cria a cena e a nova janela
        Scene cena = new Scene(layout, 450, 300);
        Stage stage = new Stage();
        stage.setTitle(idioma.get("titulo.cadastro")); // Título traduzido
        stage.setScene(cena);
        stage.show();

        // Oculta a tela anterior (login)
        stageAnterior.hide();
    }

    // Método auxiliar para mostrar mensagens ao usuário
    private void mostrarAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);       // Cria o alerta
        alerta.setHeaderText(null);           // Remove o cabeçalho
        alerta.setContentText(mensagem);      // Define a mensagem
        alerta.showAndWait();                 // Exibe a caixa e aguarda resposta
    }
}
