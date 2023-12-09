package org.springframework.samples.maderas.pedidoOwner;

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
@RequestMapping("/api/v1/pedidosOwner")
public class PedidoOwnerController {

    private final PedidoOwnerService pedidoOwnerService;
    private final UserService userService;

    @Autowired
    public PedidoOwnerController(PedidoOwnerService pedidoOwnerService, UserService userService) {
        this.pedidoOwnerService = pedidoOwnerService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoOwner>> getAllPedidos() {
        List<PedidoOwner> pedidos = pedidoOwnerService.getAllPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoOwner> getPedidoById(@PathVariable("pedidoId") int pedidoId) {
        PedidoOwner pedidoUser = pedidoOwnerService.getPedidoById(pedidoId);
        return new ResponseEntity<>(pedidoUser, HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<PedidoOwner>> getPedidoByUserId(@PathVariable("userId") Integer userId) {
        List<PedidoOwner> pedidoUser = pedidoOwnerService.getPedidoByUserId(userId);
        return new ResponseEntity<>(pedidoUser, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoOwner> createPedidoOwner(@RequestBody CompletarPedidoOwner data) {
        PedidoOwner newPedidoOwner = pedidoOwnerService.createNewPedidoOwner(data);
        return new ResponseEntity<>(newPedidoOwner, HttpStatus.CREATED);
    }

    @PutMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PedidoOwner> updatePedidoUser(@PathVariable("pedidoId") int pedidoId, @RequestBody @Valid PedidoOwner pedidoUser)  {
        validatePedidoUserExists(pedidoId);
        validateUserExists(pedidoUser.getUser().getUsername());

        PedidoOwner updatedPedidoUser = pedidoOwnerService.updatePedidoUser(pedidoUser, pedidoId);
        return new ResponseEntity<>(updatedPedidoUser, HttpStatus.OK);
    }


    @DeleteMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> deletePedidoUser(@PathVariable("pedidoId") int pedidoId) {
        validatePedidoUserExists(pedidoId);

        pedidoOwnerService.deletePedidoUser(pedidoId);
        return new ResponseEntity<>(new MessageResponse("PedidoUser eliminado correctamente"), HttpStatus.OK);
    }

    private void validateUserExists(String username) {
        if (!userService.existsUser(username)) {
            throw new ResourceNotFoundException("El usuario con el nombre: " + username + " no existe");
        }
    }

    private void validatePedidoUserExists(int pedidoId) {
        if (pedidoOwnerService.getPedidoById(pedidoId) == null) {
            throw new ResourceNotFoundException("No existe el pedido con la ID: " + pedidoId);
        }
    }


}

