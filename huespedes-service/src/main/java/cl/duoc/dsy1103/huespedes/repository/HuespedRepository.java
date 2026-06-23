package cl.duoc.dsy1103.huespedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy1103.huespedes.model.Huesped;

import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped> findByRun(String run);
}
