package com.origemacai.view;

import com.origemacai.model.Produto;
import com.origemacai.service.ProdutoService;
import com.origemacai.util.Idioma;
import com.origemacai.config.SpringContext;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class TelaCadastroProduto {

    public void start(Stage stageAnterior) {
        Idioma idioma = Idioma.getInstance();

        Label labelNome = new Label(idioma.get("label.nome"));
        Label labelTipo = new Label(idioma.get("label.tipo"));
        Label labelPreco = new Label(idioma.get("label.preco"));
        Label labelEstoque = new Label(idioma.get("label.quantidade"));

        TextField campoNome = new TextField();
        TextField campoTipo = new TextField();
        TextField campoPreco = new TextField();
        TextField campoEstoque = new TextField();

        Button btnSalvar = new Button(idioma.get("botao.salvar"));
        Button btnLimpar = new Button(idioma.get("botao.limpar"));
        Button btnVoltar = new Button(idioma.get("botao.voltar"));

        ProdutoService produtoService = SpringContext.getBean(ProdutoService.class);

        btnSalvar.setOnAction(e -> {
            try {
                String nome = campoNome.getText();
                String tipo = campoTipo.getText();
                BigDecimal preco = new BigDecimal(campoPreco.getText());
                int quantidade = Integer.parseInt(campoEstoque.getText());

                Produto novo = new Produto(nome, tipo, preco, quantidade);
                produtoService.salvar(novo);

                mostrarAlerta(idioma.get("mensagem.sucesso"), Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                mostrarAlerta(idioma.get("mensagem.erro") + ": " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnLimpar.setOnAction(e -> {
            campoNome.clear();
            campoTipo.clear();
            campoPreco.clear();
            campoEstoque.clear();
        });

        btnVoltar.setOnAction(e -> {
            stageAnterior.show();
            ((Stage) btnVoltar.getScene().getWindow()).close();
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setVgap(10);
        layout.setHgap(10);

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

        Scene cena = new Scene(layout, 450, 300);
        Stage stage = new Stage();
        stage.setTitle(idioma.get("titulo.cadastro"));
        stage.setScene(cena);
        stage.show();
        stageAnterior.hide();
    }

    private void mostrarAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}

