package org.springframework.samples.maderas.pedidouser;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.maderas.exceptions.ResourceNotFoundException;
import org.springframework.samples.maderas.user.User;
import org.springframework.samples.maderas.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PedidoUser getPedidoByUsername(String username) {
        Optional<PedidoUser> pedidoUser = pedidoUserRepository.findPedidoByUsername(username);
        return pedidoUser.orElse(null);
    }

    @Transactional(readOnly = true)
    public PedidoUser getPedidoByUserId(Integer userId) {
        Optional<PedidoUser> pedidoUser = pedidoUserRepository.findPedidoByUserId(userId);
        return pedidoUser.orElse(null);
    }

    @Transactional
    public PedidoUser createNewPedidoUser(PedidoUser pedidoUser, String username) throws UnfeasiblePedidoUserUpdate {
        if (!userService.existsUser(username)) {
            throw new ResourceNotFoundException("El usuario con el nombre: " + username + " no existe");
        }

        PedidoUser newPedidoUser = new PedidoUser();
        BeanUtils.copyProperties(pedidoUser, newPedidoUser, "id");

        User user = userService.findUserbyUsername(username);
        newPedidoUser.setUser(user);
        return savePedidoUser(newPedidoUser);
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

