package org.springframework.samples.maderas.pedidouser;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.maderas.auth.payload.response.MessageResponse;
import org.springframework.samples.maderas.exceptions.ResourceNotFoundException;
import org.springframework.samples.maderas.pieza.Pieza;
import org.springframework.samples.maderas.user.User;
import org.springframework.samples.maderas.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/api/v1/pedidos")
public class PedidoUserController {

    private final PedidoUserService pedidoUserService;
    private final UserService userService;

    @Autowired
    public PedidoUserController(PedidoUserService pedidoUserService, UserService userService) {
        this.pedidoUserService = pedidoUserService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoUser>> getAllPedidos() {
        List<PedidoUser> pedidos = pedidoUserService.getAllPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoUser> getPedidoById(@PathVariable("pedidoId") int pedidoId) {
        PedidoUser pedidoUser = pedidoUserService.getPedidoById(pedidoId);
        return new ResponseEntity<>(pedidoUser, HttpStatus.OK);
    }

    @GetMapping("/piezas")
    public ResponseEntity<List<Pieza>> getLastPedidoByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserbyUsername(username);
        PedidoUser pedidoUser = pedidoUserService.getLastPedidoByUserId(user.getId());
        List<Pieza> piezas = pedidoUser.getPiezas();
        System.out.println(piezas);
        return new ResponseEntity<>(piezas, HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<PedidoUser>> getPedidoByUserId(@PathVariable("userId") Integer userId) {
        List<PedidoUser> pedidoUser = pedidoUserService.getPedidoByUserId(userId);
        return new ResponseEntity<>(pedidoUser, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoUser> createPedidoUser(@RequestBody Pieza pieza) throws UnfeasiblePedidoUserUpdate {
        PedidoUser newPedidoUser = pedidoUserService.createNewPedidoUser(pieza);
        return new ResponseEntity<>(newPedidoUser, HttpStatus.CREATED);
    }

    @PutMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PedidoUser> updatePedidoUser(@PathVariable("pedidoId") int pedidoId, @RequestBody @Valid PedidoUser pedidoUser) throws UnfeasiblePedidoUserUpdate {
        validatePedidoUserExists(pedidoId);
        validateUserExists(pedidoUser.getUser().getUsername());

        PedidoUser updatedPedidoUser = pedidoUserService.updatePedidoUser(pedidoUser, pedidoId);
        return new ResponseEntity<>(updatedPedidoUser, HttpStatus.OK);
    }

    @PutMapping("/{pedidoId}/estado")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PedidoUser> updateEstadoPedido(@PathVariable("pedidoId") int pedidoId, @RequestBody NuevoEstado nuevoEstado) {
        System.out.println("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII: "+nuevoEstado.getNuevoEstado());
        PedidoUser updatedPedidoUser = pedidoUserService.updateEstadoPedido(pedidoId, nuevoEstado.getNuevoEstado());
        return new ResponseEntity<>(updatedPedidoUser, HttpStatus.OK);
    }

    @DeleteMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> deletePedidoUser(@PathVariable("pedidoId") int pedidoId) {
        validatePedidoUserExists(pedidoId);

        pedidoUserService.deletePedidoUser(pedidoId);
        return new ResponseEntity<>(new MessageResponse("PedidoUser eliminado correctamente"), HttpStatus.OK);
    }

    private void validateUserExists(String username) {
        if (!userService.existsUser(username)) {
            throw new ResourceNotFoundException("El usuario con el nombre: " + username + " no existe");
        }
    }

    private void validatePedidoUserExists(int pedidoId) {
        if (pedidoUserService.getPedidoById(pedidoId) == null) {
            throw new ResourceNotFoundException("No existe el pedido con la ID: " + pedidoId);
        }
    }

    @DeleteMapping("/piezas/{piezaId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> deletePiezaFromPedido(@PathVariable("piezaId") int piezaId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserbyUsername(username);
        Integer pedidoId = pedidoUserService.getLastPedidoByUserId(user.getId()).getId();
        pedidoUserService.eliminarPiezaDePedido(pedidoId, piezaId);

        return new ResponseEntity<>(new MessageResponse("Pieza eliminada del pedido correctamente"), HttpStatus.OK);
    }


}

