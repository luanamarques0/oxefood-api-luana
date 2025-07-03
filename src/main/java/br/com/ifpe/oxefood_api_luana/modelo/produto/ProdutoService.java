package br.com.ifpe.oxefood_api_luana.modelo.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood_api_luana.util.exception.ProdutoException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Produto save(Produto produto) {

        if ((produto.getValorUnitario() < 20) && (produto.getValorUnitario() > 100)) {
            throw new ProdutoException(ProdutoException.MSG_VALOR_MINIMO_E_MAXIMO_PRODUTO);
        }

        produto.setHabilitado(Boolean.TRUE);
        return repository.save(produto);
    }

    public List<Produto> listarTodosProdutos() {
        return repository.findAll();
    }

    public Produto obterPorId(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Produto produtoAlterado) {

        Produto produto = repository.findById(id).get();
        produto.setCategoria(produtoAlterado.getCategoria());
        produto.setTitulo(produtoAlterado.getTitulo());
        produto.setCodigo(produtoAlterado.getCodigo());
        produto.setDescricao(produtoAlterado.getDescricao());
        produto.setValorUnitario(produtoAlterado.getValorUnitario());
        produto.setTempoEntregaMinimo(produtoAlterado.getTempoEntregaMinimo());
        produto.setTempoEntregaMaximo(produtoAlterado.getTempoEntregaMaximo());
        repository.save(produto);
    }

    @Transactional
    public void delete(Long id) {

        Produto produto = repository.findById(id).get();
        produto.setHabilitado(Boolean.FALSE);

        repository.save(produto);
    }

}
