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
import org.springframework.web.bind.annotation.RequestParam;
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

    // Injeta o serviço responsável pela lógica de negócio de Produto
    @Autowired
    private ProdutoService produtoService;

    // Injeta o serviço para manipular Categorias de Produto
    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    // Endpoint POST para criar um novo produto
    // Recebe os dados no corpo da requisição (JSON) e valida com @Valid
    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoRequest request) {

        // Converte a requisição em um objeto Produto (provavelmente método de fábrica no DTO)
        Produto produtoNovo = request.build();

        // Define a categoria do produto buscando pelo id da categoria enviado no request
        produtoNovo.setCategoria((categoriaProdutoService.obterPorId(request.getIdCategoria())));

        // Salva o produto usando o serviço
        Produto produto = produtoService.save(produtoNovo);

        // Retorna a resposta HTTP 201 Created com o produto criado no corpo
        return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
    }

    // Endpoint GET para listar todos os produtos cadastrados
    @GetMapping
    public List<Produto> listarTodosProdutos() {
        return produtoService.listarTodosProdutos();
    }

    // Endpoint GET para obter um produto pelo seu ID
    @GetMapping("/{id}")
    public Produto obterPorId(@PathVariable Long id) {
        return produtoService.obterPorId(id);
    }

    // Endpoint PUT para atualizar um produto existente pelo ID
    // Recebe no corpo da requisição os dados atualizados (ProdutoRequest)
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable("id") Long id, @RequestBody ProdutoRequest request) {

        // Converte a requisição em um objeto Produto
        Produto produto = request.build();

        // Define a categoria do produto com base no ID informado na requisição
        produto.setCategoria(categoriaProdutoService.obterPorId(request.getIdCategoria()));

        // Atualiza o produto no banco de dados
        produtoService.update(id, produto);

        // Retorna resposta HTTP 200 OK sem corpo
        return ResponseEntity.ok().build();
    }

    // Endpoint DELETE para "deletar" um produto pelo ID (delete lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        // Chama o serviço para desabilitar o produto
        produtoService.delete(id);

        // Retorna resposta HTTP 200 OK sem corpo
        return ResponseEntity.ok().build();
    }

    // Endpoint POST para filtrar produtos por código, título e/ou categoria
    // Recebe os parâmetros opcionais como query params
    @PostMapping("/filtrar")
    public List<Produto> filtrar(
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "idCategoria", required = false) Long idCategoria) {

        // Chama o serviço para filtrar e retorna a lista de produtos filtrados
        return produtoService.filtrar(codigo, titulo, idCategoria);
    }

}
