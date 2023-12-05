package org.springframework.samples.maderas.pedidouser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PedidoUserRepository extends CrudRepository<PedidoUser, Integer>{

    List<PedidoUser> findAll();

    Optional<PedidoUser> findById(Integer id);

    @Query("SELECT p FROM PedidoUser p WHERE p.user.id = ?1")
    List<PedidoUser> findPedidoByUserId(Integer id);

    PedidoUser save(PedidoUser pedidoUser); 
}
