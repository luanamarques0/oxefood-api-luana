package br.com.ifpe.oxefood_api_luana.api.comprador;

import java.time.LocalDate;

import br.com.ifpe.oxefood_api_luana.modelo.comprador.Comprador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompradorRequest {

    private Long idSegmento;

    private String nome;

    private String enderecoComercial;

    private String enderecoResidencial;

    private Double comissao;

    private Integer qtdComprasMediaMes;

    private LocalDate contratadoEM;

    public Comprador build() {

        return Comprador.builder()
                .nome(nome)
                .enderecoComercial(enderecoComercial)
                .enderecoResidencial(enderecoResidencial)
                .comissao(comissao)
                .qtdComprasMediaMes(qtdComprasMediaMes)
                .contratadoEM(contratadoEM)
                .build();
    }
}
