package cl.duoc.dsy1103.habitaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy1103.habitaciones.model.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNumero (String numero);

    boolean existsByNumero(String numero);

}
