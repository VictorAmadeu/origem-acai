package com.origemacai.view;

import com.origemacai.model.Produto;
import com.origemacai.service.ProdutoService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class FormularioProduto {

    /**
     * Abre o formulário para editar um produto.
     * 
     * @param produtoService serviço que atualiza o produto no banco
     * @param produto objeto que será editado
     * @param callback função chamada após salvar (recebe o produto atualizado)
     */
    public static void mostrar(ProdutoService produtoService, Produto produto, Consumer<Produto> callback) {
        Stage stage = new Stage();
        stage.setTitle("Editar Produto");
        stage.initModality(Modality.APPLICATION_MODAL);

        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField(produto.getNome());

        Label lblTipo = new Label("Tipo:");
        TextField txtTipo = new TextField(produto.getTipo());

        Label lblEstoque = new Label("Quantidade em Estoque:");
        Spinner<Integer> spnEstoque = new Spinner<>(0, Integer.MAX_VALUE, produto.getQuantidadeEstoque());
        spnEstoque.setEditable(true);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            try {
                // Atualiza o objeto com os novos dados
                produto.setNome(txtNome.getText().trim());
                produto.setTipo(txtTipo.getText().trim());
                produto.setQuantidadeEstoque(spnEstoque.getValue());

                // Salva no banco
                produtoService.atualizar(produto.getId(), produto);

                // Fecha o formulário
                stage.close();

                // Chama o callback de atualização da tabela
                if (callback != null) {
                    callback.accept(produto);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Produto atualizado com sucesso!");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("Erro");
                erro.setHeaderText("Erro ao salvar produto");
                erro.setContentText(ex.getMessage());
                erro.showAndWait();
            }
        });

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setPadding(new Insets(20));
        form.add(lblNome, 0, 0);
        form.add(txtNome, 1, 0);
        form.add(lblTipo, 0, 1);
        form.add(txtTipo, 1, 1);
        form.add(lblEstoque, 0, 2);
        form.add(spnEstoque, 1, 2);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        VBox layoutFinal = new VBox(15, form, botoes);
        layoutFinal.setPadding(new Insets(10));

        Scene scene = new Scene(layoutFinal, 400, 250);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
