package br.com.ifpe.oxefood_api_luana.modelo.acesso;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

import br.com.ifpe.oxefood_api_luana.util.entity.EntidadeNegocio;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "perfil")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// Classe que representa um perfil/permissão do usuário no sistema
// Extende EntidadeNegocio (provavelmente contém campos comuns, como id)
// Implementa GrantedAuthority para ser usada pelo Spring Security como autoridade/permissão
public class Perfil extends EntidadeNegocio implements GrantedAuthority {

    // Constantes que representam os nomes dos perfis/permissões
    public static final String ROLE_CLIENTE = "CLIENTE";
    public static final String ROLE_FUNCIONARIO_ADMIN = "ROLE_FUNCIONARIO_ADMIN";
    // ADMIN pode ler, deletar, escrever, atualizar

    public static final String ROLE_FUNCIONARIO_USER = "ROLE_FUNCIONARIO_USER";
    // USER pode ler, escrever e atualizar, mas não deletar

    // Nome do perfil, que será usado como a autoridade pelo Spring Security
    private String nome;

    // Método obrigatório da interface GrantedAuthority
    // Retorna o nome da autoridade (perfil) para o Spring Security usar na autenticação/autorização
    @Override
    public String getAuthority() {
        return this.nome;
    }
}

