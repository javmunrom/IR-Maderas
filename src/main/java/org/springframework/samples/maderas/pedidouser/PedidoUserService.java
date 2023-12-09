package org.springframework.samples.maderas.pedidouser;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.maderas.exceptions.ResourceNotFoundException;
import org.springframework.samples.maderas.pieza.Pieza;
import org.springframework.samples.maderas.user.User;
import org.springframework.samples.maderas.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoUserService {

    private final PedidoUserRepository pedidoUserRepository;
    private final UserService userService;

    @Autowired
    public PedidoUserService(PedidoUserRepository pedidoUserRepository, UserService userService) {
        this.pedidoUserRepository = pedidoUserRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<PedidoUser> getAllPedidos() {
        return pedidoUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PedidoUser getPedidoById(Integer id) {
        Optional<PedidoUser> pedidoUser = pedidoUserRepository.findById(id);
        return pedidoUser.orElse(null);
    }


    @Transactional(readOnly = true)
    public List<PedidoUser> getPedidoByUserId(Integer userId) {
        List<PedidoUser> pedidoUser = pedidoUserRepository.findPedidoByUserId(userId);
        return pedidoUser;
    }

    @Transactional
    public PedidoUser getLastPedidoByUserId(Integer id) {
        List<PedidoUser> pedidoUserList = pedidoUserRepository.findPedidoByUserId(id);
        System.out.println(pedidoUserList);
        if (!pedidoUserList.isEmpty()) {
            pedidoUserList.sort((p1, p2) -> p2.getFechaPedido().compareTo(p1.getFechaPedido()));
            return pedidoUserList.get(0);
        }

        return null;
    }

    @Transactional
    public void eliminarPiezaDePedido(Integer pedidoId, Integer piezaId) {
        PedidoUser pedido = pedidoUserRepository.findById(pedidoId).orElse(null);

        if (pedido != null) {
            List<Pieza> piezas = pedido.getPiezas();
            piezas.removeIf(p -> p.getId().equals(piezaId));
            pedidoUserRepository.save(pedido);
        }
    }
    
    @Transactional
    public PedidoUser createNewPedidoUser(Pieza pieza) throws UnfeasiblePedidoUserUpdate {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PedidoUser newPedidoUser = new PedidoUser();
        User user = userService.findUserbyUsername(username);
        newPedidoUser.setUser(user);
        newPedidoUser.setFechaPedido(LocalDateTime.now());
        List<Pieza> piezas = new ArrayList<>();
        piezas.add(pieza);
        newPedidoUser.setPiezas(piezas);
        newPedidoUser.setEstado(Estado.INCOMPLETO);
        return savePedidoUser(newPedidoUser);
    }
    @Transactional
    public PedidoUser updateEstadoPedido(int pedidoId, String nuevoEstado) {
        PedidoUser pedidoUser = pedidoUserRepository.findById(pedidoId)
                .orElse(null);
        pedidoUser.setEstado(Estado.valueOf(nuevoEstado));
    
        return pedidoUserRepository.save(pedidoUser);
    }

    @Transactional
    public void deletePedidoUser(int id) {
        PedidoUser pedidoUserToDelete = getPedidoById(id);
        if (pedidoUserToDelete != null) {
            pedidoUserRepository.delete(pedidoUserToDelete);
        }
    }

    @Transactional
    public PedidoUser savePedidoUser(PedidoUser pedidoUserToSave) throws UnfeasiblePedidoUserUpdate {
        pedidoUserRepository.save(pedidoUserToSave);
        return pedidoUserToSave;
    }

    @Transactional
    public PedidoUser updatePedidoUser(PedidoUser updatedPedidoUser, int id) throws UnfeasiblePedidoUserUpdate {
        PedidoUser existingPedidoUser = getPedidoById(id);

        if (existingPedidoUser == null) {
            throw new ResourceNotFoundException("PedidoUser", "ID", id);
        }

        BeanUtils.copyProperties(updatedPedidoUser, existingPedidoUser, "id", "user");
        return savePedidoUser(existingPedidoUser);
    }
}

