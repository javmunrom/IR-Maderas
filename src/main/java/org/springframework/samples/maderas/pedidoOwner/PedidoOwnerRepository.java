package org.springframework.samples.maderas.pedidoOwner;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PedidoOwnerRepository extends CrudRepository<PedidoOwner, Integer>{

    List<PedidoOwner> findAll();

    Optional<PedidoOwner> findById(Integer id);

    @Query("SELECT p FROM PedidoUser p WHERE p.user.id = ?1")
    List<PedidoOwner> findPedidoByUserId(Integer id);

    PedidoOwner save(PedidoOwner pedidoUser); 
}
