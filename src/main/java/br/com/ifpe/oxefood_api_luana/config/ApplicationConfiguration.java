package br.com.ifpe.oxefood_api_luana.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.ifpe.oxefood_api_luana.modelo.acesso.UsuarioRepository;

// Classe de configuração do Spring para componentes relacionados à segurança e autenticação
@Configuration
public class ApplicationConfiguration {

    // Repositório de usuários injetado para buscar dados no banco
    private final UsuarioRepository userRepository;

    // Construtor para injeção do repositório de usuários
    public ApplicationConfiguration(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Bean que fornece a implementação do UserDetailsService
    // Usado pelo Spring Security para buscar dados do usuário pelo username (login)
    @Bean
    UserDetailsService userDetailsService() {
        // Retorna uma função lambda que recebe um username e busca no banco
        // Caso o usuário não seja encontrado, lança exceção UsernameNotFoundException
        return username -> userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Bean que define o codificador de senhas usado para criptografar e verificar senhas
    // Aqui está usando BCrypt, que é um algoritmo forte e recomendado
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean que retorna o AuthenticationManager, responsável por gerenciar a autenticação
    // Pega o AuthenticationManager configurado pelo Spring através do AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean que configura o AuthenticationProvider (provedor de autenticação)
    // Usa o DaoAuthenticationProvider que autentica via UserDetailsService e PasswordEncoder
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Define o serviço que busca o usuário pelo username
        authProvider.setUserDetailsService(userDetailsService());
        // Define o codificador de senha usado para comparar as senhas
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}


