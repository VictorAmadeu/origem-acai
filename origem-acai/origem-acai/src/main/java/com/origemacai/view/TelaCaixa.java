package com.origemacai.view; // Define o pacote onde a classe está localizada

// Importações necessárias
import com.origemacai.service.CaixaService; // Serviço que fornece o saldo do caixa
import javafx.application.Application; // Necessário para qualquer aplicação JavaFX
import javafx.scene.Scene; // Representa o "palco" (tela) da interface
import javafx.scene.control.Label; // Componente de texto
import javafx.scene.layout.VBox; // Layout vertical para organizar os elementos
import javafx.stage.Stage; // A janela principal

import java.math.BigDecimal; // Classe usada para representar valores monetários

public class TelaCaixa extends Application { // A classe estende Application para funcionar com JavaFX

    private CaixaService caixaService; // Serviço que retorna o saldo do caixa

    // Construtor sem parâmetros (necessário para funcionar com JavaFX e para instanciar via new TelaCaixa())
    public TelaCaixa() {
        this.caixaService = new CaixaService(); // Cria uma instância padrão do serviço (sem injeção de dependência)
    }

    // Método start que é chamado quando esta tela for aberta
    @Override
    public void start(Stage primaryStage) {
        // Chama o método do serviço para buscar o saldo atual do caixa
        BigDecimal saldo = caixaService.getSaldoAtual();

        // Cria um rótulo com o valor do saldo formatado
        Label label = new Label("💰 Saldo atual do caixa: R$ " + saldo);

        // Cria o layout vertical (VBox) com espaçamento de 20px entre elementos
        VBox layout = new VBox(20);
        layout.getChildren().add(label); // Adiciona o rótulo ao layout

        // Cria a cena (área visível) com tamanho 400x200px
        Scene cena = new Scene(layout, 400, 200);

        // Define o título da janela
        primaryStage.setTitle("Controle de Caixa");

        // Define o conteúdo da janela com a cena criada
        primaryStage.setScene(cena);

        // Exibe a janela na tela
        primaryStage.show();
    }
}

