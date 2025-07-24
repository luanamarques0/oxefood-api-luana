package br.com.ifpe.oxefood_api_luana.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.ifpe.oxefood_api_luana.modelo.acesso.Perfil;
import br.com.ifpe.oxefood_api_luana.modelo.seguranca.JwtAuthenticationFilter;

// Indica que essa classe contém configurações para o Spring (beans, etc)
@Configuration
// Habilita a segurança web do Spring Security na aplicação
@EnableWebSecurity
public class SecurityConfiguration {

    // Provedor de autenticação customizado (ex: verifica usuário e senha)
    private final AuthenticationProvider authenticationProvider;
    // Filtro JWT para autenticação via token em cada requisição
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Construtor para injetar dependências
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Configura a cadeia de filtros de segurança da aplicação
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Configura CORS (Cross-Origin Resource Sharing) para permitir requisições do frontend
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Desabilita proteção CSRF, pois geralmente APIs RESTful usam tokens (não cookies)
                .csrf(c -> c.disable())

                // Configura as regras de autorização das rotas/endpoints
                .authorizeHttpRequests(authorize -> authorize

                        // Permite acesso público (sem autenticação) para criar clientes, funcionários e login (auth)
                        .requestMatchers(HttpMethod.POST, "/api/cliente").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/funcionario").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/entregador").permitAll()

                        // Restringe consulta de produtos para usuários com perfis CLIENTE ou FUNCIONÁRIO (admin ou user)
                        .requestMatchers(HttpMethod.GET, "/api/produto/").hasAnyAuthority(
                                Perfil.ROLE_CLIENTE,
                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                Perfil.ROLE_FUNCIONARIO_USER)

                        // Permite cadastro de produto apenas para funcionários admin e user
                        .requestMatchers(HttpMethod.POST, "/api/produto").hasAnyAuthority(
                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                Perfil.ROLE_FUNCIONARIO_USER)

                        // Permite atualização de produto apenas para funcionários admin e user
                        .requestMatchers(HttpMethod.PUT, "/api/produto/*").hasAnyAuthority(
                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                Perfil.ROLE_FUNCIONARIO_USER)

                        // Permite exclusão de produto apenas para funcionários admin
                        .requestMatchers(HttpMethod.DELETE, "/api/produto/*").hasAnyAuthority(
                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                        // Permite acesso público para documentação da API (Swagger)
                        .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api-docs/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/swagger-ui/*").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )

                // Configura a política de sessão para Stateless (não armazena sessão no servidor)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define o provedor de autenticação customizado da aplicação
                .authenticationProvider(authenticationProvider)

                // Adiciona o filtro de autenticação JWT antes do filtro padrão de username/senha
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Retorna o objeto HttpSecurity configurado para ser usado pelo Spring
        return http.build();
    }

    // Configuração para permitir chamadas CORS específicas para o frontend
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Permite requisições vindas do frontend rodando em localhost:3000
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

        // Permite os métodos HTTP mais comuns usados pela API
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        // Define quais cabeçalhos HTTP são permitidos nas requisições (Authorization para token, Content-Type para JSON)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Permite que cookies e credenciais sejam enviados (importante para segurança e autenticação)
        configuration.setAllowCredentials(true);

        // Mapeia essa configuração para todas as rotas da aplicação (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
