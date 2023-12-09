package org.springframework.samples.maderas.pedidoOwner;
 
import org.springframework.samples.maderas.tablero.Tablero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletarPedidoOwner {
    private Tablero tablero;
    private String proveedor;
    private int cantidad;
}