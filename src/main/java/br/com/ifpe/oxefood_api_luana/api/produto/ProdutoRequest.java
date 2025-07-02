package br.com.ifpe.oxefood_api_luana.api.produto;

import br.com.ifpe.oxefood_api_luana.modelo.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    private Long idCategoria;

    private String titulo;

    private String codigo;

    private String descricao;

    private Double ValorUnitario;

    private Integer tempoEntregaMinimo;

    private Integer tempoEntregaMaximo;

    public Produto build() {
        
        return Produto.builder()
            .titulo(titulo)
            .codigo(codigo)
            .descricao(descricao)
            .ValorUnitario(ValorUnitario)
            .tempoEntregaMinimo(tempoEntregaMinimo)
            .tempoEntregaMaximo(tempoEntregaMaximo)
            .build();
    }
}
