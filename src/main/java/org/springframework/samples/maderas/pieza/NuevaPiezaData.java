package org.springframework.samples.maderas.pieza;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NuevaPiezaData {
    Double cantoLadoLargo;
    Double cantoLadoCorto;
    Double medidaLargo;
    Double medidaCorto;
    String diseño;
    Integer cantidad;
}
