// Define que esta classe faz parte do pacote de serviços da aplicação
package com.origemacai.service;

// Importa a classe Caixa, que representa os lançamentos financeiros no caixa
import com.origemacai.model.Caixa;

// Importa o repositório responsável por acessar os dados da entidade Caixa no banco
import com.origemacai.repository.CaixaRepository;

// Importa a anotação @Autowired, que permite ao Spring injetar automaticamente dependências (como o repositório)
import org.springframework.beans.factory.annotation.Autowired;

// Indica que esta classe é um "serviço" dentro da arquitetura da aplicação
// Com a anotação @Service, o Spring Boot passa a gerenciar essa classe automaticamente (injeção de dependência e outros recursos)
import org.springframework.stereotype.Service;

// Importa a classe BigDecimal, que é usada para representar valores monetários com precisão (evita erros de ponto flutuante)
import java.math.BigDecimal;

// Importa a classe List, que representa uma lista de objetos (neste caso, uma lista de lançamentos do caixa)
import java.util.List;

// Define a classe pública CaixaService, que será responsável pela lógica de controle financeiro (entradas, saídas, saldo)
@Service // Essa anotação é essencial para que o Spring reconheça esta classe como um "bean" do tipo serviço
public class CaixaService {

    // Injeta automaticamente o repositório CaixaRepository dentro desta classe
    // Assim, você pode usar métodos como save(), findAll(), etc. diretamente sem criar o objeto manualmente
    @Autowired
    private CaixaRepository caixaRepository;

    // Método público que registra uma nova ENTRADA de valor no caixa
    // Recebe dois parâmetros: o valor a ser adicionado e uma descrição da operação
    public void registrarEntrada(BigDecimal valor, String descricao) {

        // Cria um novo objeto Caixa com os dados informados:
        // valor recebido, tipo definido como "ENTRADA" e a descrição informada
        Caixa entrada = new Caixa(valor, "ENTRADA", descricao);

        // Salva esse novo lançamento de entrada no banco de dados usando o repositório
        caixaRepository.save(entrada);
    }

    // Método público que retorna o saldo atual do caixa (resultado das entradas menos as saídas)
    public BigDecimal getSaldoAtual() {

        // Busca todos os lançamentos registrados no caixa (entradas e saídas) usando o repositório
        List<Caixa> movimentos = caixaRepository.findAll();

        // Inicializa o saldo com valor zero (ponto de partida para o cálculo)
        BigDecimal saldo = BigDecimal.ZERO;

        // Percorre todos os lançamentos (movimentos) da lista
        for (Caixa c : movimentos) {

            // Se o tipo do lançamento for "ENTRADA", o valor é somado ao saldo
            if (c.getTipo().equals("ENTRADA")) {
                saldo = saldo.add(c.getValor());
            }
            // Se o tipo não for "ENTRADA", assume-se que é "SAÍDA", e o valor é subtraído do saldo
            else {
                saldo = saldo.subtract(c.getValor());
            }
        }

        // Retorna o saldo final calculado (após todas as somas e subtrações)
        return saldo;
    }

    // Método público que retorna uma lista com todos os lançamentos financeiros do caixa
    public List<Caixa> getTodosMovimentos() {

        // Busca e retorna todos os registros da entidade Caixa salvos no banco de dados
        return caixaRepository.findAll();
    }
}

