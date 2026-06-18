package cl.duoc.dsy1103.check_in.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import cl.duoc.dsy1103.check_in.client.EmpleadoClient;
import cl.duoc.dsy1103.check_in.client.ReservaClient;
import cl.duoc.dsy1103.check_in.dto.CheckInRequest;
import cl.duoc.dsy1103.check_in.dto.CheckInResponse;
import cl.duoc.dsy1103.check_in.dto.EmpleadoResponse;
import cl.duoc.dsy1103.check_in.dto.ReservaResponse;
import cl.duoc.dsy1103.check_in.mapper.CheckInMapper;
import cl.duoc.dsy1103.check_in.model.CheckIn;
import cl.duoc.dsy1103.check_in.repository.CheckInRepository;

@ExtendWith(MockitoExtension.class)
public class CheckInServiceTest {
    @Mock
    private CheckInRepository repository;
    @Mock
    private ReservaClient reservaClient;
    @Mock 
    private EmpleadoClient empleadoClient;
    @Mock
    private CheckInMapper mapper;

    @InjectMocks
    private CheckInService service;

    private CheckIn checkInMock;
    private CheckInResponse responseMock;
    private CheckInRequest requestMock;
    private ReservaResponse reservaMock;
    private EmpleadoResponse empleadoMock;

    @BeforeEach
    public void setUp(){
        checkInMock = CheckIn.builder()
            .id(null)
            .idReserva(null)
            .idEmpleado(null)
            .fechaIngreso(null)
            .build();

        responseMock = CheckInResponse.builder()
            .id(null)
            .idReserva(null)
            .idEmpleado(null)
            .fechaIngreso(null)
            .build();

        requestMock = CheckInRequest.builder()
            .idReserva(null)
            .idEmpleado(null)
            .build();

        empleadoMock = EmpleadoResponse.builder()
            .idEmpleado(null)
            .run(null)
            .nombreCompleto(null)
            .cargo(null)
            .idHotel(null)
            .nombreHotel(null)
            .build();

        reservaMock = ReservaResponse.builder()
            .id(null)
            .idHabitacion(null)
            .idHuesped(null)
            .idEmpleado(null)
            .cantDias(0)
            .idCheckIn(null)
            .idCheckOut(null)
            .build();
    }
}
