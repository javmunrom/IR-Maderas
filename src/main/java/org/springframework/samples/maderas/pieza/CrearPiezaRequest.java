package org.springframework.samples.maderas.pieza;

import org.springframework.samples.maderas.tablero.Tablero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CrearPiezaRequest {
    Tablero tablero;
    NuevaPiezaData nuevaPiezaData;
}
