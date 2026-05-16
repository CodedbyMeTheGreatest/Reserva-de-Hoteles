package cl.duoc.dsy1103.check_out.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.dsy1103.check_out.model.CheckOut;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Long> {
    Optional<CheckOut> findByIdReserva(Long idReserva);
    boolean existsByIdReserva(Long idReserva);
}

