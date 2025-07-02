package br.com.ifpe.oxefood_api_luana.api.comprador;

import java.util.List;

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

import br.com.ifpe.oxefood_api_luana.modelo.comprador.Comprador;
import br.com.ifpe.oxefood_api_luana.modelo.comprador.CompradorService;
import br.com.ifpe.oxefood_api_luana.modelo.segmento.SegmentoService;

@RestController
@RequestMapping("/api/comprador")
@CrossOrigin
public class CompradorController {

    @Autowired
    private CompradorService compradorService;

    @Autowired
    private SegmentoService segmentoService;

    @PostMapping
    public ResponseEntity<Comprador> save(@RequestBody CompradorRequest request) {

        Comprador compradorNovo = request.build();
        compradorNovo.setSegmento((segmentoService.obterPorId(request.getIdSegmento())));
        Comprador comprador = compradorService.save(compradorNovo);
        return new ResponseEntity<Comprador>(comprador, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Comprador> listarTodosCompradores() {
        return compradorService.listarTodosCompradores();
    }

    @GetMapping("/{id}")
    public Comprador obterPorID(@PathVariable Long id) {
        return compradorService.obterPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comprador> update(@PathVariable("id") Long id, @RequestBody CompradorRequest request) {

        Comprador comprador = request.build();
        comprador.setSegmento(segmentoService.obterPorId(request.getIdSegmento()));
        compradorService.update(id, comprador);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        compradorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
