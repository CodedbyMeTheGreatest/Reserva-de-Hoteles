package cl.duoc.dsy1103.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.dsy1103.hotel.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByNombre(String nombre);

    public boolean existsByNombre(String nombre);
}
