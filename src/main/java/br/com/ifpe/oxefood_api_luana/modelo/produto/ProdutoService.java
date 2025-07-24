package br.com.ifpe.oxefood_api_luana.modelo.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood_api_luana.util.exception.ProdutoException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    // Injeta o repositório de Produto para operações de banco
    @Autowired
    private ProdutoRepository repository;

    // Método para salvar um produto (create ou update)
    // @Transactional garante que a operação seja feita dentro de uma transação
    @Transactional //Só vai no create, update e delete
    public Produto save(Produto produto) {
        // Validação do valor unitário: se estiver fora do intervalo [20,100], lança exceção
        // Atenção: A condição abaixo está incorreta (veja explicação após)
        if ((produto.getValorUnitario() < 20) && (produto.getValorUnitario() > 100)) {
            throw new ProdutoException(ProdutoException.MSG_VALOR_MINIMO_E_MAXIMO_PRODUTO);
        }

        // Marca o produto como habilitado (ativo)
        produto.setHabilitado(Boolean.TRUE);
        // Salva o produto no banco (novo ou update)
        return repository.save(produto);
    }

    // Retorna todos os produtos do banco
    public List<Produto> listarTodosProdutos() {
        return repository.findAll();
    }

    // Retorna um produto pelo seu id
    public Produto obterPorId(Long id) {
        return repository.findById(id).get();
    }

    // Atualiza os dados de um produto existente
    @Transactional
    public void update(Long id, Produto produtoAlterado) {
        // Busca o produto existente no banco
        Produto produto = repository.findById(id).get();
        // Atualiza os campos com os valores do produtoAlterado
        produto.setCategoria(produtoAlterado.getCategoria());
        produto.setTitulo(produtoAlterado.getTitulo());
        produto.setCodigo(produtoAlterado.getCodigo());
        produto.setDescricao(produtoAlterado.getDescricao());
        produto.setValorUnitario(produtoAlterado.getValorUnitario());
        produto.setTempoEntregaMinimo(produtoAlterado.getTempoEntregaMinimo());
        produto.setTempoEntregaMaximo(produtoAlterado.getTempoEntregaMaximo());
        // Salva as alterações
        repository.save(produto);
    }

    // "Delete" lógico do produto (não remove do banco, só desabilita)
    @Transactional
    public void delete(Long id) {
        // Busca o produto pelo id
        Produto produto = repository.findById(id).get();
        // Marca como desabilitado
        produto.setHabilitado(Boolean.FALSE);
        // Salva a alteração
        repository.save(produto);
    }

    // Método para filtrar produtos por código, título e/ou categoria
    public List<Produto> filtrar(String codigo, String titulo, Long idCategoria) {

        // Começa buscando todos os produtos
        List<Produto> listaProdutos = repository.findAll();

        // Verifica cada combinação dos filtros para chamar o método adequado do repositório

        if ((codigo != null && !"".equals(codigo)) &&
                (titulo == null || "".equals(titulo)) &&
                (idCategoria == null)) {

            // Busca por código
            listaProdutos = repository.consultarPorCodigo(codigo);

        } else if ((codigo == null || "".equals(codigo)) &&
                (titulo != null && !"".equals(titulo)) &&
                (idCategoria == null)) {

            // Busca por título (contendo o texto, case insensitive, ordenado pelo título)
            listaProdutos = repository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);

        } else if ((codigo == null || "".equals(codigo)) &&
                (titulo == null || "".equals(titulo)) &&
                (idCategoria != null)) {

            // Busca por categoria
            listaProdutos = repository.consultarPorCategoria(idCategoria);

        } else if ((codigo == null || "".equals(codigo)) &&
                (titulo != null && !"".equals(titulo)) &&
                (idCategoria != null)) {

            // Busca por título e categoria juntos
            listaProdutos = repository.consultarPorTituloECategoria(titulo, idCategoria);

        }

        // Retorna a lista filtrada
        return listaProdutos;
    }

}

