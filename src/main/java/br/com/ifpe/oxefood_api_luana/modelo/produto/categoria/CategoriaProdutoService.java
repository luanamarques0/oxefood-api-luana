package br.com.ifpe.oxefood_api_luana.modelo.produto.categoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CategoriaProdutoService {
    @Autowired
    private CategoriaProdutoRepository repository;

    @Transactional
    public CategoriaProduto save(CategoriaProduto categoria) {
        categoria.setHabilitado(Boolean.TRUE);
        return repository.save(categoria);
    }

    public List<CategoriaProduto> listaTodasCategoriasProdutos() {
        return repository.findAll();
    }

    public CategoriaProduto obterPorId(Long id){
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, CategoriaProduto categoriaAlterada) {

        CategoriaProduto categoria = repository.findById(id).get();
        categoria.setDescricao(categoriaAlterada.getDescricao());
        
        repository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {

        CategoriaProduto categoria = repository.findById(id).get();
        categoria.setHabilitado(Boolean.FALSE);

        repository.save(categoria);
    }

}
