package org.springframework.samples.maderas.tablero;

import org.springframework.samples.maderas.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Tablero extends BaseEntity{

    @Enumerated(EnumType.STRING)
    TipoMaterial tipoMaterial;

    @Enumerated(EnumType.STRING)
    Color color;

    Double espesor;

    Integer stock;

    String img;
}
