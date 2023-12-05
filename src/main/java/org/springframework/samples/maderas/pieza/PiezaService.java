package org.springframework.samples.maderas.pieza;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.maderas.tablero.Tablero;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PiezaService {

    private final PiezaRepository piezaRepository;

    @Autowired
    public PiezaService(PiezaRepository piezaRepository) {
        this.piezaRepository = piezaRepository;
    }

    @Transactional(readOnly = true)
    public List<Pieza> getAllPiezas() {
        return piezaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pieza getPiezaById(Long id) {
        Optional<Pieza> pieza = piezaRepository.findById(id);
        return pieza.orElse(null);
    }

    @Transactional
    public Pieza savePieza(Pieza piezaToSave) {
        piezaRepository.save(piezaToSave);
        return piezaToSave;
    }

    @Transactional
    public void deletePieza(Long id) {
        Pieza piezaToDelete = getPiezaById(id);
        if (piezaToDelete != null) {
            piezaRepository.delete(piezaToDelete);
        }
    }

    // You can add more methods as needed
}
