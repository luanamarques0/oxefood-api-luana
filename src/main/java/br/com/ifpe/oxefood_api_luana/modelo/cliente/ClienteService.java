package br.com.ifpe.oxefood_api_luana.modelo.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

   @Transactional //Tudo que for feito no db só pode ser criado se tudo der certo, se der um unico erro td a transação falha
   public Cliente save(Cliente cliente) {
        //retorna o cliente com o ID(Pq ele recebe id no db)
       cliente.setHabilitado(Boolean.TRUE);
       return repository.save(cliente);
   }

    
}
