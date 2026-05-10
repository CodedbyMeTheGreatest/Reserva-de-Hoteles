package cl.duoc.dsy1103.facturas.repository;

import cl.duoc.dsy1103.facturas.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByFolio(String folio);
}
