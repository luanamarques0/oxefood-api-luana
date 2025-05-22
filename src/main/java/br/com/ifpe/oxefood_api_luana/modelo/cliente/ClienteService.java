package br.com.ifpe.oxefood_api_luana.modelo.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Transactional // Tudo que for feito no db só pode ser criado se tudo der certo, se der um
                   // unico erro td a transação falha
    public Cliente save(Cliente cliente) {
        // retorna o cliente com o ID(Pq ele recebe id no db)
        cliente.setHabilitado(Boolean.TRUE);
        return repository.save(cliente);
    }

    public List<Cliente> listarTodos() {

        return repository.findAll();
    }

    public Cliente obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado) {

        Cliente cliente = repository.findById(id).get();
        cliente.setNome(clienteAlterado.getNome());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setFoneCelular(clienteAlterado.getFoneCelular());
        cliente.setFoneFixo(clienteAlterado.getFoneFixo());

        repository.save(cliente);// O save serve para cadastrar e para alterar, se passar os parametros com id
                                 // ele cria um novo, se não altera.
    }

    @Transactional
    public void delete(Long id) {

        Cliente cliente = repository.findById(id).get();
        cliente.setHabilitado(Boolean.FALSE);

        repository.save(cliente);
    }

}
