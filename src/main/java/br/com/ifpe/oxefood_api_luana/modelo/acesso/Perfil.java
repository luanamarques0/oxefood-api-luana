package br.com.ifpe.oxefood_api_luana.modelo.acesso;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "perfil")
@SQLRestriction("habilitado = true")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {
    public static final String ROLE_CLIENTE = "CLIENTE";
    public static final String ROLE_FUNCIONARIO_ADMIN = "ROLE_FUNCIONARIO_ADMIN"; // READ, DELETE, WRITE, UPDATE.
    public static final String ROLE_FUNCIONARIO_USER = "ROLE_FUNCIONARIO_USER"; // READ, WRITE, UPDATE.

    private String nome;

    @Override
    public String getAuthority() {
        return this.nome;
    }

}
