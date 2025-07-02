package br.com.ifpe.oxefood_api_luana.api.segmento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ifpe.oxefood_api_luana.modelo.segmento.Segmento;
import br.com.ifpe.oxefood_api_luana.modelo.segmento.SegmentoService;

@Controller
@RequestMapping("/api/segmento")
@CrossOrigin
public class SegmentoController {

    @Autowired
    private SegmentoService segmentoService;

    @PostMapping
    public ResponseEntity<Segmento> save(@RequestBody SegmentoRequest request) {

        Segmento segmento = segmentoService.save(request.build()); 
        return new ResponseEntity<Segmento>(segmento, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Segmento> listarTodosSegmentos() {
        return segmentoService.listarTodosSegmentos();
    }

    @GetMapping("/{id}")
    public Segmento obterPorID(@PathVariable Long id) {
        return segmentoService.obterPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Segmento> update(@PathVariable("id") Long id, @RequestBody SegmentoRequest request) {

        segmentoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        segmentoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
