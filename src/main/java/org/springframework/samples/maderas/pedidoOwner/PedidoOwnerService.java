package org.springframework.samples.maderas.pedidoOwner;


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
import java.util.List;
import java.util.Optional;

@Service
public class PedidoOwnerService {

    private final PedidoOwnerRepository pedidoUserRepository;
    private final UserService userService;

    @Autowired
    public PedidoOwnerService(PedidoOwnerRepository pedidoUserRepository, UserService userService) {
        this.pedidoUserRepository = pedidoUserRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<PedidoOwner> getAllPedidos() {
        return pedidoUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PedidoOwner getPedidoById(Integer id) {
        Optional<PedidoOwner> pedidoUser = pedidoUserRepository.findById(id);
        return pedidoUser.orElse(null);
    }


    @Transactional(readOnly = true)
    public List<PedidoOwner> getPedidoByUserId(Integer userId) {
        List<PedidoOwner> pedidoUser = pedidoUserRepository.findPedidoByUserId(userId);
        return pedidoUser;
    }

    @Transactional
    public PedidoOwner getLastPedidoByUserId(Integer id) {
        List<PedidoOwner> pedidoUserList = pedidoUserRepository.findPedidoByUserId(id);
        System.out.println(pedidoUserList);
        if (!pedidoUserList.isEmpty()) {
            pedidoUserList.sort((p1, p2) -> p2.getFechaPedido().compareTo(p1.getFechaPedido()));
            return pedidoUserList.get(0);
        }

        return null;
    }

    
    @Transactional
    public PedidoOwner createNewPedidoOwner(CompletarPedidoOwner data)  {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PedidoOwner newPedidoOwner = new PedidoOwner();
        User user = userService.findUserbyUsername(username);
        newPedidoOwner.setUser(user);
        newPedidoOwner.setFechaPedido(LocalDateTime.now());
        newPedidoOwner.setTablero(data.getTablero());
        newPedidoOwner.setProveedor(Proveedor.valueOf(data.getProveedor()));
        newPedidoOwner.setCantidad(data.getCantidad());
        return savePedidoOnwer(newPedidoOwner);
    }


    @Transactional
    public void deletePedidoUser(int id) {
        PedidoOwner pedidoUserToDelete = getPedidoById(id);
        if (pedidoUserToDelete != null) {
            pedidoUserRepository.delete(pedidoUserToDelete);
        }
    }

    @Transactional
    public PedidoOwner savePedidoOnwer(PedidoOwner pedidoUserToSave) {
        pedidoUserRepository.save(pedidoUserToSave);
        return pedidoUserToSave;
    }

    @Transactional
    public PedidoOwner updatePedidoUser(PedidoOwner updatedPedidoUser, int id) {
        PedidoOwner existingPedidoUser = getPedidoById(id);

        if (existingPedidoUser == null) {
            throw new ResourceNotFoundException("PedidoUser", "ID", id);
        }

        BeanUtils.copyProperties(updatedPedidoUser, existingPedidoUser, "id", "user");
        return savePedidoOnwer(existingPedidoUser);
    }
}

