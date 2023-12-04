package org.springframework.samples.maderas.tablero;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableroService {

    private final TableroRepository tableroRepository;

    @Autowired
    public TableroService(TableroRepository tableroRepository) {
        this.tableroRepository = tableroRepository;
    }

    @Transactional(readOnly = true)
    public List<Tablero> getAllTableros() {
        return tableroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tablero> getFourRandomTableros() {
        List<Tablero> allTableros = tableroRepository.findAll();
        Collections.shuffle(allTableros);
        return allTableros.subList(0, Math.min(4, allTableros.size()));
    }

    @Transactional(readOnly = true)
    public Tablero getTableroById(Integer id) {
        Optional<Tablero> tablero = tableroRepository.findById(id);
        return tablero.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Tablero> getTableroByTipoMaterial(String tipoMaterial) {
        Optional<List<Tablero>> tablero = tableroRepository.findTableroByTipoMaterial(TipoMaterial.valueOf(tipoMaterial.toUpperCase()));
        return tablero.orElse(null);
    }

    @Transactional(readOnly = true)
    public Tablero getTableroByTipoMaterialAndColor(String tipoMaterial, String color) {
        
        Optional<Tablero> tablero = tableroRepository.findTableroByTipoMaterialAndColor(TipoMaterial.valueOf(tipoMaterial.toUpperCase()), Color.valueOf(color.toUpperCase()));
        return tablero.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Tablero> getTableroByColor(String color) {
        Optional<List<Tablero>> tablero = tableroRepository.findTableroByColor(Color.valueOf(color.toUpperCase()));
        return tablero.orElse(null);
    }

    @Transactional
    public Tablero createNewTablero(Tablero tablero) {
        return tableroRepository.save(tablero);
    }

    @Transactional
    public void deleteTablero(int id) {
        Tablero tableroToDelete = getTableroById(id);
        if (tableroToDelete != null) {
            tableroRepository.delete(tableroToDelete);
        }
    }

    @Transactional
    public Tablero saveTablero(Tablero tableroToSave) {
        return tableroRepository.save(tableroToSave);
    }

    @Transactional
    public Tablero updateTablero(Tablero updatedTablero, int id) throws UnfeasibleTableroUpdate {
        Tablero existingTablero = getTableroById(id);

        if (existingTablero == null) {
            throw new UnfeasibleTableroUpdate();
        }

        BeanUtils.copyProperties(updatedTablero, existingTablero, "id");
        return saveTablero(existingTablero);
    }
}
