package cl.duoc.dsy1103.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy1103.usuarios.model.Huesped;

import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    /**
     * Busca Huesped por Run,
     * para enviarlo al WebClient de Factura,
     * ya así validar que existe un huésped con este run
     */
    Optional<Huesped> findByRun(String run);
}
