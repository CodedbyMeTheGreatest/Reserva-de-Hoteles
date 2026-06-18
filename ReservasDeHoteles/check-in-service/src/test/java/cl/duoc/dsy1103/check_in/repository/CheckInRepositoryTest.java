package cl.duoc.dsy1103.check_in.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import cl.duoc.dsy1103.check_in.model.CheckIn;

@DataJpaTest
@ActiveProfiles("dev")
public class CheckInRepositoryTest {
    @Autowired
    private CheckInRepository repository;

    @BeforeEach
    public void setUp(){
        repository.deleteAll();
        repository.save(CheckIn.builder().idReserva((long) 1).idEmpleado((long)1).fechaIngreso(LocalDateTime.now()).build());
        repository.save(CheckIn.builder().idReserva((long) 2).idEmpleado((long)2).fechaIngreso(LocalDateTime.now()).build());
        repository.save(CheckIn.builder().idReserva((long) 3).idEmpleado((long)1).fechaIngreso(LocalDateTime.now()).build());
    }

    @Test
    @DisplayName("Debe retornar todos los check in")
    void debeRetornarTodosLosCheckIn(){
        List<CheckIn> checkIns = repository.findAll();
        assertThat(checkIns).hasSize(3);
    }

    @Test
    @DisplayName("Debe buscar check in por ID")
    void debeBuscarPorId(){
        CheckIn checkIn = repository.findAll().get(0);

        Optional<CheckIn> resultado = repository.findById(checkIn.getId());
        
        assertThat(resultado).isPresent();
    }

    @Test
    @DisplayName("Debe eliminar un check in por ID")
    void debeEliminarCheckInPorId(){
        CheckIn checkIn = repository.findAll().get(0);
        repository.deleteById(checkIn.getId());

        Optional<CheckIn> eliminado = repository.findById(checkIn.getId());
        assertThat(eliminado).isNotPresent();
    }
}
