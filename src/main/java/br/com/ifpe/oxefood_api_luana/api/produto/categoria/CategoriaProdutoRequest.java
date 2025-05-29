package br.com.ifpe.oxefood_api_luana.api.produto.categoria;

import br.com.ifpe.oxefood_api_luana.modelo.produto.categoria.CategoriaProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProdutoRequest {

    private String descricao;

    public CategoriaProduto build(){
        
        return CategoriaProduto.builder()
            .descricao(descricao)
            .build();
    }
}
