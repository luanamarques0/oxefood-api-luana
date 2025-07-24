package br.com.ifpe.oxefood_api_luana.modelo.cliente;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.ifpe.oxefood_api_luana.modelo.acesso.Usuario;
import br.com.ifpe.oxefood_api_luana.modelo.cliente.endereco.EnderecoCliente;
import br.com.ifpe.oxefood_api_luana.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cliente")
@SQLRestriction("habilitado = true") // Restrição para que apenas os registros com "habilitado = true" sejam considerados nas queries

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel {

    @OneToOne
    @JoinColumn(nullable = false) // Cria uma chave estrangeira obrigatória
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER) // EAGER para carregar os endereços junto com o cliente // orphanRemoval = true para remover endereços associados quando o cliente for removido
    @Fetch(FetchMode.SUBSELECT) // Estratégia para evitar problema de "Cartesian product" ao buscar vários endereços
    private List<EnderecoCliente> enderecos;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column
    private LocalDate dataNascimento;

    @Column(unique = true)
    private String cpf;

    @Column
    private String foneCelular;

    @Column
    private String foneFixo;

}
