package cl.duoc.dsy1103.empleados.repository;

import cl.duoc.dsy1103.empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    /**
     * Busca un empleado con el run entregado
     * @param run
     * @return : Empleado -> encontró | null -> no encontró
     */
    public Optional<Empleado> findByRun(String run);

    /**
     * Existe un empleado con el run entregado.
     * @param  run
     * @return  'true' -> existe | 'false' -> no existe.
     */
    public boolean existsByRun(String run);

}
