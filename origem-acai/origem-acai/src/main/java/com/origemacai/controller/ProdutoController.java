package com.origemacai.controller;

import com.origemacai.model.Produto;
import com.origemacai.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marca a classe como um controlador REST
@RequestMapping("/api/produtos") // Define o caminho base da API
public class ProdutoController {

    @Autowired // Injeta o serviço de produto
    private ProdutoService produtoService;

    // GET /api/produtos
    // Retorna todos os produtos cadastrados
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    // GET /api/produtos/{id}
    // Retorna um produto pelo seu ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/produtos
    // Cria um novo produto
    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        Produto salvo = produtoService.salvar(produto);
        return ResponseEntity.ok(salvo);
    }

    // PUT /api/produtos/{id}
    // Atualiza um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto atualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/produtos/{id}
    // Remove um produto pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
