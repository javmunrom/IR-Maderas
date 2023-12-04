package org.springframework.samples.maderas.reseña;

import org.springframework.samples.maderas.model.BaseEntity;
import org.springframework.samples.maderas.pedidouser.PedidoUser;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Reseña extends BaseEntity{
    
    @Size(min=0, max = 5)
    Integer valoracion;

    String descripcion;

    @OneToOne
    @JoinColumn(name = "pedido_user_id", referencedColumnName = "id")
    PedidoUser pedidoUser;
}
