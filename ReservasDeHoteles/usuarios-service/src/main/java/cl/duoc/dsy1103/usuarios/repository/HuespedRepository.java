package cl.duoc.dsy1103.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    /**
     * Busca Huesped por Run,
     * para enviarlo al WebClient de Factura,
     * ya así validar que existe un huésped con este run
     */
    Optional<Huesped> findByRun(String run);
}
