package org.springframework.samples.maderas.tablero;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.maderas.auth.payload.response.MessageResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tableros")
public class TableroController {

    private final TableroService tableroService;

    @Autowired
    public TableroController(TableroService tableroService) {
        this.tableroService = tableroService;
    }

    @GetMapping
    public ResponseEntity<List<Tablero>> getAllTableros() {
        List<Tablero> tableros = tableroService.getAllTableros();
        return new ResponseEntity<>(tableros, HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<List<Tablero>> getFourRandomsTableros() {
        List<Tablero> tableros = tableroService.getFourRandomTableros();
        return new ResponseEntity<>(tableros, HttpStatus.OK);
    }

    @GetMapping("/{tableroId}")
    public ResponseEntity<Tablero> getTableroById(@PathVariable("tableroId") int tableroId) {
        Tablero tablero = tableroService.getTableroById(tableroId);
        return new ResponseEntity<>(tablero, HttpStatus.OK);
    }

    @GetMapping("/material/{tipoMaterial}")
    public ResponseEntity<List<Tablero>> getTableroByTipoMaterial(@PathVariable("tipoMaterial") String tipoMaterial) {
        List<Tablero> tablero = tableroService.getTableroByTipoMaterial(tipoMaterial);
        return new ResponseEntity<>(tablero, HttpStatus.OK);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Tablero>> getTableroByColor(@PathVariable("color") String color) {
        List<Tablero> tablero = tableroService.getTableroByColor(color);
        return new ResponseEntity<>(tablero, HttpStatus.OK);
    }
    @GetMapping("/materialcolor/{tipoMaterial}/{color}")
    public ResponseEntity<Tablero> getTableroByTipoMaterialAndColor(@PathVariable("tipoMaterial") String tipoMaterial, @PathVariable("color") String color) {
        Tablero tablero = tableroService.getTableroByTipoMaterialAndColor(tipoMaterial, color);
        return new ResponseEntity<>(tablero, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tablero> createTablero(@RequestBody @Valid Tablero tablero) {
        Tablero newTablero = tableroService.createNewTablero(tablero);
        return new ResponseEntity<>(newTablero, HttpStatus.CREATED);
    }

    @PutMapping("/{tableroId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Tablero> updateTablero(@PathVariable("tableroId") int tableroId, @RequestBody @Valid Tablero tablero) throws UnfeasibleTableroUpdate {
        Tablero updatedTablero = tableroService.updateTablero(tablero, tableroId);
        return new ResponseEntity<>(updatedTablero, HttpStatus.OK);
    }

    @DeleteMapping("/{tableroId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> deleteTablero(@PathVariable("tableroId") int tableroId) {
        tableroService.deleteTablero(tableroId);
        return new ResponseEntity<>(new MessageResponse("Tablero eliminado correctamente"), HttpStatus.OK);
    }
}