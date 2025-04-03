package com.origemacai.repository;

import com.origemacai.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um componente de repositório do Spring
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Podemos adicionar métodos customizados aqui futuramente, como:
    // List<Produto> findByTipo(String tipo);
}
