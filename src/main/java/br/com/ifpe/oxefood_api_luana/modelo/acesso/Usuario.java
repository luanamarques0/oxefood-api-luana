package br.com.ifpe.oxefood_api_luana.modelo.acesso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.oxefood_api_luana.util.entity.EntidadeNegocio;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usuario")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends EntidadeNegocio implements UserDetails {

   @Column(nullable = false, unique = true)
   private String username;

   @JsonIgnore
   @Column(nullable = false)
   private String password;

   @JsonIgnore
   // @ElementCollection para armazenar uma coleção de elementos básicos ou embutidos (Perfil)
   @ElementCollection(fetch = FetchType.EAGER) // FetchType.EAGER garante que os perfis sejam carregados junto com o usuário
   @Builder.Default // @Builder.Default para garantir inicialização padrão do atributo (ArrayList vazio)
   private List<Perfil> roles = new ArrayList<>();

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
       return roles;
   }

   @Override
   public String getUsername() {
       return username;
   }

   public String getPassword() {
       return password;
   }

   // Indica se a conta não expirou (sempre true aqui, ou seja, conta está válida)
   @Override
   public boolean isAccountNonExpired() {
       return true;
   }

   // Indica se a conta não está bloqueada (sempre true)
   @Override
   public boolean isAccountNonLocked() {
       return true;
   }

   // Indica se as credenciais (senha) não expiraram (sempre true)
   @Override
   public boolean isCredentialsNonExpired() {
       return true;
   }

   // Indica se o usuário está habilitado (sempre true)
   @Override
   public boolean isEnabled() {
       return true;
   }
}


// @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles;
//    }

//    Ele é um método da interface UserDetails do Spring Security.

// Serve para retornar as permissões (autoridades) que o usuário possui no sistema.

// Essas permissões são usadas pelo Spring Security para controlar o que o usuário pode ou não acessar.
// Quando um usuário faz login, o Spring Security consulta esse método para saber quais autoridades ele tem.

// Com isso, ele decide se pode acessar certas rotas, recursos ou funcionalidades do sistema.

// Por exemplo, só usuários com a role ADMIN podem acessar a página administrativa.