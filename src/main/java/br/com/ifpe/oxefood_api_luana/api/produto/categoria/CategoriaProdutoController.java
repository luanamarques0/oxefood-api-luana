package br.com.ifpe.oxefood_api_luana.api.produto.categoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood_api_luana.modelo.produto.categoria.CategoriaProduto;
import br.com.ifpe.oxefood_api_luana.modelo.produto.categoria.CategoriaProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/produto/categoria")
@CrossOrigin
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaProduto> save(@RequestBody @Valid CategoriaProdutoRequest request) {

        CategoriaProduto categoria = categoriaService.save(request.build());
        return new ResponseEntity<CategoriaProduto>(categoria, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoriaProduto> listaTodasCategoriasProdutos() {
        return categoriaService.listaTodasCategoriasProdutos();
    }

    @GetMapping("/{id}")
    public CategoriaProduto obterPorId(@PathVariable Long id) {
        return categoriaService.obterPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProduto> update(@PathVariable("id") Long id, @RequestBody CategoriaProdutoRequest request) {
        categoriaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        categoriaService.delete(id);
        return ResponseEntity.ok().build();
    }

}
