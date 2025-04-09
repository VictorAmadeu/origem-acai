package com.origemacai.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(length = 100)
    private String cliente;

    public Venda() {
    }

    public Venda(Produto produto, int quantidade, BigDecimal total, String cliente) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.total = total;
        this.cliente = cliente;
        this.dataHora = LocalDateTime.now();
    }

    // Getters e setters (pode gerar com ALT+SHIFT+S ou manualmente)
}
