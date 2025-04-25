package br.com.ifpe.oxefood_api_luana.api.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood_api_luana.modelo.cliente.Cliente;
import br.com.ifpe.oxefood_api_luana.modelo.cliente.ClienteService;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin //Conseguir receber requisições de outros dominios(REact no nosso caso)
public class ClienteController {

   @Autowired
   private ClienteService clienteService;

   @PostMapping
   public ResponseEntity<Cliente> save(@RequestBody ClienteRequest request) {

       Cliente cliente = clienteService.save(request.build()); //build cria o cliente a partir do request
       return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
   }
}

