package org.springframework.samples.maderas.pedidoOwner;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.maderas.model.BaseEntity;
import org.springframework.samples.maderas.pieza.Pieza;
import org.springframework.samples.maderas.tablero.Tablero;
import org.springframework.samples.maderas.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
public class PedidoOwner extends BaseEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime fechaPedido;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime fechaPedidoTerminado;

    @ManyToOne // Cambia de @OneToOne a @ManyToOne
    @JoinColumn(name = "tablero_id") // Cambia el nombre de la columna si es necesario
    private Tablero tablero;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    Double precio;

    Integer cantidad;

    Proveedor proveedor;


    public PedidoOwner() {
    }

    public PedidoOwner(LocalDateTime fechaPedido, LocalDateTime fechaPedidoTerminado, Double precio, List<Pieza> piezas) {
        this.fechaPedido = LocalDateTime.now();
        this.fechaPedidoTerminado = fechaPedidoTerminado;
        this.precio = precio;
    }
}
