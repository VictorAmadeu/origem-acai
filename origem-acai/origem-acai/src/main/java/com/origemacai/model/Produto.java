// Define o pacote onde a classe está localizada
package com.origemacai.model;

// Importa as anotações JPA para persistência com banco de dados
import jakarta.persistence.*;

// Importa a classe BigDecimal para trabalhar com valores monetários com precisão
import java.math.BigDecimal;

// Marca essa classe como uma entidade JPA (uma tabela no banco)
@Entity

// Define o nome da tabela correspondente no banco de dados
@Table(name = "produto")
public class Produto {

    // ✅ 1. Atributos da entidade

    // Identificador único do produto (chave primária)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do produto (ex: Açaí, Tapioca)
    @Column(nullable = false, length = 100)
    private String nome;

    // Tipo do produto (ex: Açaí, Adicional, Bebida)
    @Column(nullable = false, length = 50)
    private String tipo;

    // Preço unitário do produto
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    // Quantidade em estoque
    @Column(nullable = false)
    private int quantidadeEstoque;

    // ✅ 2. Construtores

    // Construtor vazio (necessário para o JPA)
    public Produto() {
    }

    // Construtor completo para facilitar a criação de novos produtos
    public Produto(String nome, String tipo, BigDecimal preco, int quantidadeEstoque) {
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // ✅ 3. Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // ✅ 4. Método toString sobrescrito

    @Override
    public String toString() {
        // Retorna apenas o nome do produto — isso será exibido no ComboBox
        return nome;
    }
}
