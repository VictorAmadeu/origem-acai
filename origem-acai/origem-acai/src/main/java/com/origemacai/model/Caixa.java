package com.origemacai.model; 
// Define o pacote ao qual essa classe pertence: com.origemacai.model (modelo/entidades)

import jakarta.persistence.*; 
// Importa as anotações do Jakarta Persistence (JPA) para mapear a entidade no banco de dados

import java.math.BigDecimal; 
// Importa BigDecimal, uma classe ideal para trabalhar com valores monetários com precisão

import java.time.LocalDateTime; 
// Importa LocalDateTime, usada para registrar a data e hora exata do registro

@Entity 
// Indica que esta classe representa uma tabela no banco de dados (entidade JPA)

@Table(name = "caixa") 
// Especifica que essa entidade será mapeada para a tabela chamada "caixa"

public class Caixa { 
// Declaração pública da classe Caixa

    @Id 
    // Define que o campo abaixo será a chave primária da tabela

    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // Indica que o ID será gerado automaticamente pelo banco (auto-incremento)

    private Long id; 
    // Campo privado do tipo Long que armazena o identificador único do registro no banco

    private LocalDateTime dataHora; 
    // Campo para armazenar a data e hora exata da movimentação no caixa

    private BigDecimal valor; 
    // Campo que armazena o valor da movimentação (entrada ou saída de dinheiro)

    private String tipo; 
    // Campo que indica se é uma "ENTRADA" ou "SAÍDA" de valor no caixa

    private String descricao; 
    // Campo que armazena uma descrição da movimentação (ex: "Venda de produto")

    public Caixa() { 
    // Construtor padrão vazio — necessário para que o JPA possa instanciar a classe automaticamente
    }

    public Caixa(BigDecimal valor, String tipo, String descricao) { 
    // Construtor com parâmetros para facilitar a criação de objetos já preenchidos

        this.dataHora = LocalDateTime.now(); 
        // Ao criar um novo objeto, define automaticamente a data/hora atual

        this.valor = valor; 
        // Atribui o valor recebido ao atributo valor

        this.tipo = tipo; 
        // Atribui o tipo ("ENTRADA" ou "SAÍDA") ao atributo tipo

        this.descricao = descricao; 
        // Atribui a descrição da movimentação ao atributo descricao
    }

    public String getTipo() { 
    // Método getter que retorna o valor atual do atributo tipo
        return tipo; 
    }

    public BigDecimal getValor() { 
    // Método getter que retorna o valor da movimentação
        return valor; 
    }

    public LocalDateTime getDataHora() { 
    // Método getter que retorna a data e hora em que a movimentação foi registrada
        return dataHora; 
    }

    public String getDescricao() { 
    // Método getter que retorna a descrição da movimentação
        return descricao; 
    }

    // Os setters são opcionais e só devem ser adicionados se for necessário alterar os valores depois da criação.
}

