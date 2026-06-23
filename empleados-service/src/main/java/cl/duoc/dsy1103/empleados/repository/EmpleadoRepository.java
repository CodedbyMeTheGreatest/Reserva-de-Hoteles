package cl.duoc.dsy1103.empleados.repository;

import cl.duoc.dsy1103.empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByRun(String run);

    boolean existsByRun(String run);

}
