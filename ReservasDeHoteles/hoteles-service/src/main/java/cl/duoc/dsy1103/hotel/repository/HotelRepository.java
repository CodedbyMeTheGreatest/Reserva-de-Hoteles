package cl.duoc.dsy1103.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy1103.hotel.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByNombre(String nombre);

    public boolean existsByNombre(String nombre);
}
