package org.springframework.samples.maderas.pieza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.maderas.auth.payload.response.MessageResponse;
import org.springframework.samples.maderas.pedidouser.PedidoUser;
import org.springframework.samples.maderas.pedidouser.PedidoUserService;
import org.springframework.samples.maderas.pedidouser.UnfeasiblePedidoUserUpdate;
import org.springframework.samples.maderas.tablero.Tablero;
import org.springframework.samples.maderas.user.User;
import org.springframework.samples.maderas.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/piezas")
public class PiezaController {

    private final PiezaService piezaService;
    private final PedidoUserService pedidoUserService;
    private final UserService userService;

    @Autowired
    public PiezaController(PiezaService piezaService, PedidoUserService pedidoUserService, UserService userService) {
        this.piezaService = piezaService;
        this.pedidoUserService = pedidoUserService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Pieza>> getAllPiezas() {
        List<Pieza> piezas = piezaService.getAllPiezas();
        return new ResponseEntity<>(piezas, HttpStatus.OK);
    }

    @GetMapping("/{piezaId}")
    public ResponseEntity<Pieza> getPiezaById(@PathVariable("piezaId") Long piezaId) {
        Pieza pieza = piezaService.getPiezaById(piezaId);
        return new ResponseEntity<>(pieza, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Pieza> crearPieza(@RequestBody CrearPiezaRequest request) throws UnfeasiblePedidoUserUpdate {
    Tablero tablero = request.getTablero();
    NuevaPiezaData nuevaPiezaData = request.getNuevaPiezaData();
    Pieza nuevaPieza = new Pieza(nuevaPiezaData.getCantoLadoLargo(), nuevaPiezaData.getCantoLadoCorto(), nuevaPiezaData.getMedidaLargo(),nuevaPiezaData.getMedidaCorto(), nuevaPiezaData.getDiseño(), tablero,nuevaPiezaData.getCantidad());
    piezaService.savePieza(nuevaPieza);
    PedidoUser p = pedidoUserService.createNewPedidoUser(nuevaPieza);
    return new ResponseEntity<>(nuevaPieza, HttpStatus.CREATED);
    }

    @PostMapping("/addToPedido")
    public ResponseEntity<Pieza> agregarPiezaAPedido(@RequestBody CrearPiezaRequest request) throws UnfeasiblePedidoUserUpdate {
        try {
            Tablero tablero = request.getTablero();
            NuevaPiezaData nuevaPiezaData = request.getNuevaPiezaData();
            Pieza nuevaPieza = new Pieza(nuevaPiezaData.getCantoLadoLargo(), nuevaPiezaData.getCantoLadoCorto(),
                    nuevaPiezaData.getMedidaLargo(), nuevaPiezaData.getMedidaCorto(), nuevaPiezaData.getDiseño(),
                    tablero, nuevaPiezaData.getCantidad());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findUserbyUsername(username);
            PedidoUser pedidoUser = pedidoUserService.getLastPedidoByUserId(user.getId());


            if (pedidoUser == null) {
                // Manejar el caso en el que el pedido no existe
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Agregar la pieza al pedido existente
            List<Pieza> piezas = pedidoUser.getPiezas();
            piezaService.savePieza(nuevaPieza);
            piezas.add(nuevaPieza);
            pedidoUser.setPiezas(piezas);
            pedidoUserService.savePedidoUser(pedidoUser);

            return new ResponseEntity<>(nuevaPieza, HttpStatus.CREATED);
        } catch (UnfeasiblePedidoUserUpdate e) {
            // Manejar cualquier excepción
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{piezaId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Pieza> updatePieza(@PathVariable("piezaId") Long piezaId, @RequestBody @Valid Pieza pieza) {
        Pieza updatedPieza = piezaService.savePieza(pieza);
        return new ResponseEntity<>(updatedPieza, HttpStatus.OK);
    }

    @DeleteMapping("/{piezaId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> deletePieza(@PathVariable("piezaId") Long piezaId) {
        piezaService.deletePieza(piezaId);
        return new ResponseEntity<>(new MessageResponse("Pieza eliminada correctamente"), HttpStatus.OK);
    }
}
