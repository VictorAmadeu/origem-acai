// Define o pacote onde esta classe está localizada
package com.origemacai.model;

// Importa as anotações de JPA para mapeamento com banco de dados
import jakarta.persistence.*;

// Importa a classe BigDecimal para representar valores monetários com precisão
import java.math.BigDecimal;

// Importa a classe LocalDateTime para registrar a data e hora da venda
import java.time.LocalDateTime;

// Indica que esta classe representa uma entidade JPA (ou seja, uma tabela no banco de dados)
@Entity

// Especifica o nome da tabela que será criada no banco: "venda"
@Table(name = "venda")
public class Venda {

    // Define a chave primária da tabela
    @Id
    // Define que o valor do ID será gerado automaticamente pelo banco (auto-incremento)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cria um relacionamento N:1 com a tabela de produtos
    @ManyToOne(optional = false) // Uma venda está associada obrigatoriamente a um produto
    // Define o nome da coluna estrangeira no banco
    @JoinColumn(name = "produto_id")
    private Produto produto;

    // Armazena a quantidade vendida do produto
    @Column(nullable = false) // Campo obrigatório (não pode ser nulo)
    private int quantidade;

    // Armazena o valor total da venda (preço × quantidade)
    @Column(nullable = false, precision = 10, scale = 2) // Valor monetário com precisão
    private BigDecimal total;

    // Armazena a data e hora em que a venda foi realizada
    @Column(nullable = false)
    private LocalDateTime dataHora;

    // Armazena o nome do cliente (opcionalmente)
    @Column(length = 100) // Limita o campo a 100 caracteres
    private String cliente;

    // Construtor vazio obrigatório para o JPA (Hibernate) funcionar corretamente
    public Venda() {
    }

    // Construtor completo para criar uma nova venda com todos os dados necessários
    public Venda(Produto produto, int quantidade, BigDecimal total, String cliente) {
        this.produto = produto;                 // Produto vendido
        this.quantidade = quantidade;           // Quantidade comprada
        this.total = total;                     // Valor total da venda
        this.cliente = cliente;                 // Nome do cliente
        this.dataHora = LocalDateTime.now();    // Registra automaticamente o momento da venda
    }

    // Getter público para acessar o produto da venda (usado nos relatórios, por exemplo)
    public Produto getProduto() {
        return produto;
    }

    // Getter público para acessar a data e hora da venda
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    // Getter para o ID (pode ser útil para relatórios ou buscas)
    public Long getId() {
        return id;
    }

    // Getter para a quantidade de itens vendidos
    public int getQuantidade() {
        return quantidade;
    }

    // Getter para o valor total da venda
    public BigDecimal getTotal() {
        return total;
    }

    // ✅ MÉTODO ADICIONADO — Soluciona o erro de getValorTotal() não definido
    public BigDecimal getValorTotal() {
        return total;
    }
    // Este método alternativo retorna o mesmo que getTotal(), mas com o nome esperado pela classe VendaService.

    // Getter para o nome do cliente
    public String getCliente() {
        return cliente;
    }

    // Setters caso queira editar os dados depois (ex: em edição de venda no futuro)

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
