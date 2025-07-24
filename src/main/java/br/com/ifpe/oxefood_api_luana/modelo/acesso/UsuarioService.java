package br.com.ifpe.oxefood_api_luana.modelo.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood_api_luana.modelo.seguranca.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

// Indica que essa classe é um serviço gerenciado pelo Spring (camada de negócio)
@Service
public class UsuarioService implements UserDetailsService {

    // Injeta automaticamente o repositório de usuários para acesso ao banco
    @Autowired
    private UsuarioRepository repository;

    // Injeta o serviço para lidar com JWT (tokens)
    @Autowired
    private JwtService jwtService;

    // Encodificador de senha (ex: bcrypt)
    private final PasswordEncoder passwordEncoder;

    // Gerenciador de autenticação do Spring Security
    private final AuthenticationManager authenticationManager;

    // Construtor para injeção de dependências obrigatórias (passwordEncoder e authenticationManager)
    public UsuarioService(UsuarioRepository userRepository, AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método que autentica um usuário recebendo username e senha
    public Usuario authenticate(String username, String password) {
        // Tenta autenticar usando o AuthenticationManager (lança exceção se falhar)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // Se autenticado com sucesso, retorna o usuário do banco pelo username
        return repository.findByUsername(username).orElseThrow();
    }

    // Busca um usuário pelo username no banco, dentro de uma transação
    @Transactional
    public Usuario findByUsername(String username) {
        return repository.findByUsername(username).get();
    }

    // Método obrigatório da interface UserDetailsService para carregar usuário pelo username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).get();
    }

    // Salva um usuário novo ou existente no banco, dentro de uma transação
    @Transactional
    public Usuario save(Usuario user) {
        // Codifica a senha usando PasswordEncoder antes de salvar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Marca o usuário como habilitado
        user.setHabilitado(Boolean.TRUE);
        // Salva e retorna o usuário
        return repository.save(user);
    }

    // Obtém o usuário atualmente logado a partir do token JWT presente no cabeçalho da requisição
    public Usuario obterUsuarioLogado(HttpServletRequest request) {

        Usuario usuarioLogado = null;
        // Pega o valor do cabeçalho Authorization (que deve conter "Bearer <token>")
        String authHeader = request.getHeader("Authorization");

        // Se o cabeçalho existir
        if (authHeader != null) {
            // Remove o prefixo "Bearer " para extrair só o token JWT
            String jwt = authHeader.substring(7);
            // Usa o serviço JWT para extrair o username (email) do token
            String userEmail = jwtService.extractUsername(jwt);
            // Busca o usuário pelo username extraído
            usuarioLogado = findByUsername(userEmail);
            return usuarioLogado;
        }

        // Retorna null caso não tenha o token no cabeçalho
        return usuarioLogado;
    }

}

