package org.springframework.samples.maderas.pedidouser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.maderas.model.BaseEntity;
import org.springframework.samples.maderas.pieza.Pieza;
import org.springframework.samples.maderas.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
public class PedidoUser extends BaseEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime fechaPedido;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime fechaEntregaEstimada;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_user_id")
    List<Pieza> piezas;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    Double precio;

    public PedidoUser() {
    }

    public PedidoUser(LocalDateTime fechaPedido, LocalDateTime fechaEntregaEstimada, Double precio, List<Pieza> piezas) {
        this.fechaPedido = fechaPedido;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.precio = precio;
        this.piezas = piezas;
    }
}