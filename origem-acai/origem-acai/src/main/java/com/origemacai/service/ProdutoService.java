// Define o pacote onde a classe ProdutoService está localizada.
// Isso ajuda o Java e o Spring a organizarem e localizarem os arquivos do projeto.
package com.origemacai.service;

// Importa a classe Produto que representa a entidade (tabela) de produtos do banco de dados.
import com.origemacai.model.Produto;

// Importa a interface ProdutoRepository que faz a conexão com o banco (persistência dos dados).
import com.origemacai.repository.ProdutoRepository;

// Importa a anotação @Autowired para permitir a injeção automática de dependências no Spring.
import org.springframework.beans.factory.annotation.Autowired;

// Importa a anotação @Service para indicar que esta classe é um serviço gerenciado pelo Spring.
import org.springframework.stereotype.Service;

// Importa utilitários do Java que permitem trabalhar com listas e valores opcionais.
import java.util.List;
import java.util.Optional;

// Indica que esta classe é um "Serviço" da aplicação, ou seja, ela contém as regras de negócio.
// O Spring irá gerenciar esta classe automaticamente e permitir a injeção de dependências.
@Service
public class ProdutoService {

    // Indica que o Spring deve injetar automaticamente o objeto ProdutoRepository nesta variável.
    // Isso evita que você tenha que instanciar manualmente a classe, e garante que ela tenha todas as dependências resolvidas.
    @Autowired
    private ProdutoRepository produtoRepository;

    // Método público que retorna uma lista com todos os produtos cadastrados no banco.
    // Utiliza o método findAll() do JpaRepository, que busca todos os registros da tabela "produto".
    public List<Produto> listarTodos() {
        return produtoRepository.findAll(); // Busca todos os produtos no banco e retorna como lista.
    }

    // Método público que busca um único produto com base no seu ID.
    // Retorna um Optional<Produto>, que pode ou não conter o resultado (evita erros de null).
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id); // Busca o produto com o ID fornecido.
    }

    // Método público que salva um novo produto no banco de dados.
    // Antes de salvar, ele realiza validações para garantir que os dados estejam corretos.
    public Produto salvar(Produto produto) {
        validarProduto(produto); // Chama o método privado para validar os dados do produto.
        return produtoRepository.save(produto); // Se estiver tudo certo, salva o produto no banco.
    }

    // Método público que atualiza os dados de um produto já existente no banco.
    // Ele localiza o produto original pelo ID, atualiza os campos e salva novamente.
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        // Busca o produto existente pelo ID. Se não encontrar, lança uma exceção.
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Atualiza o nome do produto com o novo valor informado.
        produtoExistente.setNome(produtoAtualizado.getNome());

        // Atualiza o tipo do produto (ex: açaí, tapioca, adicional).
        produtoExistente.setTipo(produtoAtualizado.getTipo());

        // Atualiza o preço do produto com o valor informado.
        produtoExistente.setPreco(produtoAtualizado.getPreco());

        // Atualiza a quantidade de estoque com a nova quantidade informada.
        produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        // Salva as mudanças no banco de dados.
        return produtoRepository.save(produtoExistente);
    }

    // Método público que remove um produto do banco de dados com base no seu ID.
    public void deletar(Long id) {
        produtoRepository.deleteById(id); // Usa o método deleteById do JpaRepository.
    }

    // Método privado que realiza validações obrigatórias antes de salvar ou atualizar um produto.
    // Se alguma regra for violada, ele lança uma exceção com uma mensagem explicativa.
    private void validarProduto(Produto produto) {
        // Verifica se o nome do produto está vazio ou nulo.
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }

        // Verifica se o preço do produto é válido: não pode ser nulo e deve ser maior ou igual a zero.
        if (produto.getPreco() == null || produto.getPreco().doubleValue() < 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior ou igual a zero.");
        }

        // Verifica se a quantidade em estoque é negativa, o que não é permitido.
        if (produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }
    }

    // Método público que reduz o estoque de um produto após uma venda.
    // Recebe o ID do produto e a quantidade vendida, realiza verificações e atualiza o estoque.
    public void reduzirEstoque(Long produtoId, int quantidadeVendida) {
        // Busca o produto no banco. Se não existir, lança uma exceção.
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se a quantidade vendida é válida (precisa ser maior que zero).
        if (quantidadeVendida <= 0) {
            throw new IllegalArgumentException("Quantidade vendida deve ser maior que zero.");
        }

        // Verifica se há estoque suficiente para realizar a venda.
        if (produto.getQuantidadeEstoque() < quantidadeVendida) {
            throw new IllegalArgumentException("Estoque insuficiente para a venda.");
        }

        // Calcula o novo valor de estoque após a venda.
        int novoEstoque = produto.getQuantidadeEstoque() - quantidadeVendida;

        // Atualiza o produto com o novo valor de estoque.
        produto.setQuantidadeEstoque(novoEstoque);

        // Salva a atualização no banco de dados.
        produtoRepository.save(produto);
    }
}
