package org.springframework.samples.maderas.pedidouser;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.maderas.auth.payload.response.MessageResponse;
import org.springframework.samples.maderas.exceptions.ResourceNotFoundException;
import org.springframework.samples.maderas.user.UserService;
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

    @GetMapping("/username/{username}")
    public ResponseEntity<PedidoUser> getPedidoByUsername(@PathVariable("username") String username) {
        PedidoUser pedidoUser = pedidoUserService.getPedidoByUsername(username);
        return new ResponseEntity<>(pedidoUser, HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<PedidoUser> getPedidoByUserId(@PathVariable("userId") Integer userId) {
        PedidoUser pedidoUser = pedidoUserService.getPedidoByUserId(userId);
        return new ResponseEntity<>(pedidoUser, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoUser> createPedidoUser(@RequestBody @Valid PedidoUser pedidoUser) throws UnfeasiblePedidoUserUpdate {
        String username = pedidoUser.getUser().getUsername();
        validateUserExists(username);

        PedidoUser newPedidoUser = pedidoUserService.createNewPedidoUser(pedidoUser, username);
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
}

