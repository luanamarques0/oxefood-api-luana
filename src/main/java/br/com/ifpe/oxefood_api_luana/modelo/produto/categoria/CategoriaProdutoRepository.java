package br.com.ifpe.oxefood_api_luana.modelo.produto.categoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
    // List<CategoriaProduto> findByChaveEmpresaOrderByDescricao(String chaveEmpresa);
    @Query(value = "SELECT c FROM CategoriaProduto c WHERE c.id = :idProduto AND c.chaveEmpresa = :chaveEmpresa")
    CategoriaProduto findByIdAndChave(Long id, String chaveEmpresa);


}
