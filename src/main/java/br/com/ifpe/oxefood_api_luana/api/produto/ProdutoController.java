package br.com.ifpe.oxefood_api_luana.api.produto;

import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import br.com.ifpe.oxefood_api_luana.modelo.produto.Produto;
import br.com.ifpe.oxefood_api_luana.modelo.produto.ProdutoService;
import br.com.ifpe.oxefood_api_luana.modelo.produto.categoria.CategoriaProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoRequest request) {

        Produto produtoNovo = request.build();
        produtoNovo.setCategoria((categoriaProdutoService.obterPorId(request.getIdCategoria())));
        Produto produto = produtoService.save(produtoNovo);
        return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Produto> listarTodosProdutos() {
        return produtoService.listarTodosProdutos();
    }

    @GetMapping("/{id}")
    public Produto obterPorId(@PathVariable Long id) {
        return produtoService.obterPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable("id") Long id, @RequestBody ProdutoRequest request) {
        Produto produto = request.build();
        produto.setCategoria(categoriaProdutoService.obterPorId(request.getIdCategoria()));
        produtoService.update(id, produto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
