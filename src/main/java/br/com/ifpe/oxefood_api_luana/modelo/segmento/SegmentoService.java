package br.com.ifpe.oxefood_api_luana.modelo.segmento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SegmentoService {

    @Autowired
    private SegmentiRepository repository;

    @Transactional
    public Segmento save(Segmento segmento) {
        segmento.setHabilitado(Boolean.TRUE);
        return repository.save(segmento);
    }

    public List<Segmento> listarTodosSegmentos() {
        return repository.findAll();
    }

    public Segmento obterPorId(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Segmento segmentoAlterado) {

        Segmento segmento = repository.findById(id).get();
        segmento.setDescricao(segmentoAlterado.getDescricao());
        repository.save(segmento);
    }

    @Transactional
    public void delete(Long id) {

        Segmento segmento = repository.findById(id).get();
        segmento.setHabilitado(Boolean.FALSE);

        repository.save(segmento);
    }

}
