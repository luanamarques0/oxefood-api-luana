package br.com.ifpe.oxefood_api_luana.api.entregador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood_api_luana.modelo.acesso.UsuarioService;
import br.com.ifpe.oxefood_api_luana.modelo.entregador.Entregador;
import br.com.ifpe.oxefood_api_luana.modelo.entregador.EntregadorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/entregador")
@CrossOrigin
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Entregador> save(@RequestBody @Valid EntregadorRequest entregadorRequest, HttpServletRequest request) {

        Entregador entregador = entregadorService.save(entregadorRequest.build(), usuarioService.obterUsuarioLogado(request));
        return new ResponseEntity<Entregador> (entregador, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Entregador> listarTodosEntregadores() {
        return entregadorService.listarTodosEntregadores();
    }

    @GetMapping("/{id}")
    public Entregador obterPorId(@PathVariable Long id) {
        return entregadorService.obterPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entregador> update(@PathVariable("id") Long id, @RequestBody EntregadorRequest entregadorRequest, HttpServletRequest request) {

        entregadorService.update(id, entregadorRequest.build(), usuarioService.obterUsuarioLogado(request));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        entregadorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
