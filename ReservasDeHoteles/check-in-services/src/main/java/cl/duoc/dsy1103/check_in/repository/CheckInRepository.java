package cl.duoc.dsy1103.check_in.repository;


import cl.duoc.dsy1103.check_in.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

}
