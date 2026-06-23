package cl.duoc.dsy1103.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy1103.pagos.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    

}
