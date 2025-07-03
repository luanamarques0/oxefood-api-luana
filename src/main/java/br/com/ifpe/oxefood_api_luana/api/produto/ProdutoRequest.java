package br.com.ifpe.oxefood_api_luana.api.produto;

import br.com.ifpe.oxefood_api_luana.modelo.produto.Produto;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "O titulo é preenchimento obrigatório")
    private String titulo;

    @NotBlank(message = "O código do produto é preenchimento obrigatório")
    private String codigo;

    private String descricao;

    @NotBlank(message = "O valor unitário do produto é preenchimento obrigatório")
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
