package br.com.ifpe.oxefood_api_luana.modelo.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood_api_luana.modelo.acesso.Perfil;
import br.com.ifpe.oxefood_api_luana.modelo.acesso.PerfilRepository;
import br.com.ifpe.oxefood_api_luana.modelo.acesso.Usuario;
import br.com.ifpe.oxefood_api_luana.modelo.acesso.UsuarioService;
import br.com.ifpe.oxefood_api_luana.modelo.cliente.endereco.EnderecoCliente;
import br.com.ifpe.oxefood_api_luana.modelo.cliente.endereco.EnderecoClienteRepository;
import br.com.ifpe.oxefood_api_luana.modelo.menssagens.EmailService;
import br.com.ifpe.oxefood_api_luana.util.exception.ClienteException;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;

    @Transactional // Tudo que for feito no db só pode ser criado se tudo der certo, se der um
                   // unico erro td a transação falha
    public Cliente save(Cliente cliente, Usuario usuarioLogado) {
        if ((!cliente.getFoneCelular().startsWith("(81)"))) {
            throw new ClienteException(ClienteException.MSG_TELEFON_INVALIDO);
        }
        usuarioService.save(cliente.getUsuario());

        for (Perfil perfil : cliente.getUsuario().getRoles()) {
            perfil.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfil);

        }

        cliente.setHabilitado(Boolean.TRUE);
        cliente.setCriadoPor(usuarioLogado); //setar o usuario logado
        Cliente clienteSalvo = repository.save(cliente);

        emailService.enviarEmailConfirmacaoCadastroCliente(clienteSalvo); //Envia o amail definido no arq html

        return clienteSalvo; // retorna o cliente com o ID(Pq ele recebe id no db)

    }

    public List<Cliente> listarTodos() {

        return repository.findAll();
    }

    public Cliente obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado, Usuario usuarioLogado) {

        Cliente cliente = repository.findById(id).get();
        cliente.setNome(clienteAlterado.getNome());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setFoneCelular(clienteAlterado.getFoneCelular());
        cliente.setFoneFixo(clienteAlterado.getFoneFixo());

        cliente.setUltimaModificacaoPor(usuarioLogado); // Setar o usuario logado

        repository.save(cliente);// O save serve para cadastrar e para alterar, se passar os parametros com id
                                 // ele cria um novo, se não altera.
    }

    @Transactional
    public void delete(Long id) {

        Cliente cliente = repository.findById(id).get();
        cliente.setHabilitado(Boolean.FALSE);

        repository.save(cliente);
    }

    // -------------------------------- Métodos de endereço --------------------------------

    @Transactional
    public EnderecoCliente adicionarEnderecoCliente(Long clienteId, EnderecoCliente endereco) {

        Cliente cliente = this.obterPorID(clienteId);

        // Primeiro salva o EnderecoCliente:

        endereco.setCliente(cliente);
        endereco.setHabilitado(Boolean.TRUE);
        enderecoClienteRepository.save(endereco);

        // Depois acrescenta o endereço criado ao cliente e atualiza o cliente:

        List<EnderecoCliente> listaEnderecoCliente = cliente.getEnderecos();

        if (listaEnderecoCliente == null) {
            listaEnderecoCliente = new ArrayList<EnderecoCliente>();
        }

        listaEnderecoCliente.add(endereco);
        cliente.setEnderecos(listaEnderecoCliente);
        repository.save(cliente);

        return endereco;
    }

    @Transactional
    public EnderecoCliente atualizarEnderecoCliente(Long id, EnderecoCliente enderecoAlterado) {

        EnderecoCliente endereco = enderecoClienteRepository.findById(id).get();
        endereco.setRua(enderecoAlterado.getRua());
        endereco.setNumero(enderecoAlterado.getNumero());
        endereco.setBairro(enderecoAlterado.getBairro());
        endereco.setCep(enderecoAlterado.getCep());
        endereco.setCidade(enderecoAlterado.getCidade());
        endereco.setEstado(enderecoAlterado.getEstado());
        endereco.setComplemento(enderecoAlterado.getComplemento());

        return enderecoClienteRepository.save(endereco);
    }

    @Transactional
    public void removerEnderecoCliente(Long idEndereco) {

        EnderecoCliente endereco = enderecoClienteRepository.findById(idEndereco).get();
        endereco.setHabilitado(Boolean.FALSE);
        enderecoClienteRepository.save(endereco);

        Cliente cliente = this.obterPorID(endereco.getCliente().getId());
        cliente.getEnderecos().remove(endereco);
        repository.save(cliente);
    }

}
