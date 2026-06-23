package cl.duoc.dsy1103.check_in.repository;

import cl.duoc.dsy1103.check_in.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    boolean existsByIdReserva(Long idReserva);
}
