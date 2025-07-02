package br.com.ifpe.oxefood_api_luana.modelo.comprador;

import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood_api_luana.modelo.segmento.Segmento;
import br.com.ifpe.oxefood_api_luana.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Comprador")
@SQLRestriction("habilitado = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comprador extends EntidadeAuditavel{

    @ManyToOne
    private Segmento segmento;

    @Column
    private String nome;

    @Column
    private String enderecoComercial;

    @Column 
    private String enderecoResidencial;

    @Column
    private Double comissao;

    @Column
    private Integer qtdComprasMediaMes;

    @Column
    private LocalDate contratadoEM;
}
