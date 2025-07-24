package br.com.ifpe.oxefood_api_luana.modelo.entregador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood_api_luana.modelo.acesso.Perfil;
import br.com.ifpe.oxefood_api_luana.modelo.acesso.PerfilRepository;
import br.com.ifpe.oxefood_api_luana.modelo.acesso.Usuario;
import br.com.ifpe.oxefood_api_luana.modelo.acesso.UsuarioService;

import java.time.LocalDate;
import java.util.List;
import jakarta.transaction.Transactional;

@Service
public class EntregadorService {
    @Autowired
    private EntregadorRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;

    @Transactional
    public Entregador save(Entregador entregador, Usuario usuarioLogado) {

        usuarioService.save(entregador.getUsuario());

        for (Perfil perfil : entregador.getUsuario().getRoles()) {
            perfil.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfil);
        }

        entregador.setHabilitado(Boolean.TRUE);
        entregador.setCriadoPor(usuarioLogado);

        Entregador entregadorSalvo = repository.save(entregador);

        return entregadorSalvo;

    }

    public List<Entregador> listarTodosEntregadores() {
        return repository.findAll();
    }

    public Entregador obterPorId(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Entregador entregadorAlterado, Usuario usuarioLogado) {

        Entregador entregador = repository.findById(id).get();
        entregador.setNome(entregadorAlterado.getNome());
        entregador.setCpf(entregadorAlterado.getCpf());
        entregador.setRg(entregadorAlterado.getRg());
        entregador.setDataNascimento(entregadorAlterado.getDataNascimento());
        entregador.setFoneCelular(entregadorAlterado.getFoneCelular());
        entregador.setFoneFixo(entregadorAlterado.getFoneFixo());
        entregador.setQtdEntregasRealizadas(entregadorAlterado.getQtdEntregasRealizadas());
        entregador.setValorFrete(entregadorAlterado.getValorFrete());
        entregador.setEnderecoRua(entregadorAlterado.getEnderecoRua());
        entregador.setEnderecoComplemento(entregadorAlterado.getEnderecoComplemento());
        entregador.setEnderecoNumero(entregadorAlterado.getEnderecoNumero());
        entregador.setEnderecoBairro(entregadorAlterado.getEnderecoBairro());
        entregador.setEnderecoCidade(entregadorAlterado.getEnderecoCidade());
        entregador.setEnderecoCep(entregadorAlterado.getEnderecoCep());
        entregador.setEnderecoUf(entregadorAlterado.getEnderecoUf());
        entregador.setAtivo(entregadorAlterado.getAtivo());

        entregador.setUltimaModificacaoPor(usuarioLogado);
        entregador.setDataUltimaModificacao(LocalDate.now());

        repository.save(entregador);
    }

    @Transactional
    public void delete(Long id) {

        Entregador entregador = repository.findById(id).get();
        entregador.setHabilitado(Boolean.FALSE);

        repository.save(entregador);
    }
}
