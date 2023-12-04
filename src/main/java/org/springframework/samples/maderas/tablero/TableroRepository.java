package org.springframework.samples.maderas.tablero;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TableroRepository extends CrudRepository<Tablero, Integer> {

    List<Tablero> findAll();

    Optional<Tablero> findById(Integer id);

    @Query("SELECT t FROM Tablero t WHERE t.tipoMaterial = ?1")
    Optional<List<Tablero>> findTableroByTipoMaterial(TipoMaterial tipoMaterial);

    @Query("SELECT t FROM Tablero t WHERE t.color = ?1")
    Optional<List<Tablero>> findTableroByColor(Color color);

     @Query("SELECT t FROM Tablero t WHERE t.tipoMaterial = ?1 AND t.color = ?2")
    Optional<Tablero> findTableroByTipoMaterialAndColor(TipoMaterial tipoMaterial, Color color);

    Tablero save(Tablero tablero);
}
