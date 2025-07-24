package br.com.ifpe.oxefood_api_luana.modelo.produto.categoria;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood_api_luana.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoria_produto")
@SQLRestriction("habilitado = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaProduto extends EntidadeAuditavel {
    @NotNull
    @Column(nullable = false)
    private String chaveEmpresa;

    @NotNull
    @Column(nullable = false, length = 100)
    private String descricao;

}
