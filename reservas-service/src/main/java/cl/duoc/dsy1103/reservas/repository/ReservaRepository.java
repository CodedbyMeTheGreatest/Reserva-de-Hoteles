package cl.duoc.dsy1103.reservas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy1103.reservas.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByIdEmpleado(Long idEmpleado);

}
