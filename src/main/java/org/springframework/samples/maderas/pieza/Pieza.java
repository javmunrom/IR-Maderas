package org.springframework.samples.maderas.pieza;

import org.springframework.samples.maderas.model.BaseEntity;
import org.springframework.samples.maderas.tablero.Tablero;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pieza extends BaseEntity{
    
    Double cantoLadoLargo;

    Double cantoLadoCorto;

    Double medidaLargo;

    Double medidaCorto;

    String diseño;

    @ManyToOne
    @JoinColumn(name = "tablero_id", referencedColumnName = "id")
    Tablero tablero;

    public Pieza() {
    }

    public Pieza(Double cantoLadoLargo, Double cantoLadoCorto, Double medidaLargo, Double medidaCorto, String diseño) {
        this.cantoLadoLargo = cantoLadoLargo;
        this.cantoLadoCorto = cantoLadoCorto;
        this.medidaLargo = medidaLargo;
        this.medidaCorto = medidaCorto;
        this.diseño = diseño;
    }

}
