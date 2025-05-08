package br.com.ifpe.oxefood_api_luana.api.entregador;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.oxefood_api_luana.modelo.entregador.Entregador;

public class EntregadorRequest {

    private String nome;

    private String cpf;

    private String rg;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String foneCelular;

    private String foneFixo;

    private Integer qtdEntregasRealizadas;

    private Double valorFrete;

    private String enderecoRua;

    private String enderecoComplemento;

    private String enderecoNumero;

    private String enderecoBairro;

    private String enderecoCidade;

    private String enderecoCep;

    private String enderecoUf;

    public Entregador build() {
        return Entregador.builder()
            .nome(nome)
            .cpf(cpf)
            .rg(rg)
            .dataNascimento(dataNascimento)
            .foneCelular(foneCelular)
            .foneFixo(foneFixo)
            .qtdEntregasRealizadas(qtdEntregasRealizadas)
            .valorFrete(valorFrete)
            .enderecoRua(enderecoRua)
            .enderecoComplemento(enderecoComplemento)
            .enderecoNumero(enderecoNumero)
            .enderecoBairro(enderecoBairro)
            .enderecoCidade(enderecoCidade)
            .enderecoCep(enderecoCep)
            .enderecoUf(enderecoUf)
            .build();
    }

    
}
