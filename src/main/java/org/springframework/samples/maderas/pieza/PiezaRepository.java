package org.springframework.samples.maderas.pieza;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface PiezaRepository extends CrudRepository<Pieza, Long> {

    List<Pieza> findAll();

    Optional<Pieza> findById(Long id);

}
