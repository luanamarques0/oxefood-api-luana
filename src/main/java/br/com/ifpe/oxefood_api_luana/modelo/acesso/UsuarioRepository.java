package br.com.ifpe.oxefood_api_luana.modelo.acesso;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repositório para a entidade Usuario
// Extende JpaRepository para herdar operações básicas de CRUD e mais
// JpaRepository<T, ID> onde T = entidade, ID = tipo da chave primária (Long aqui)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo seu username (login)
    // Retorna um Optional, pois o usuário pode ou não existir
    Optional<Usuario> findByUsername(String username);

}

