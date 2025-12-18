package parking.proyectoada_ud6.repositories;

import parking.proyectoada_ud6.entities.Reserva;
import parking.proyectoada_ud6.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCliente(Cliente cliente);
    List<Reserva> findByFechaResAndEstado(LocalDate fecha, Reserva.EstadoReserva estado);
    List<Reserva> findByEsVipAndEstado(Boolean esVip, Reserva.EstadoReserva estado);
    List<Reserva> findByEstado(Reserva.EstadoReserva estado);
    List<Reserva> findByClienteAndEstado(Cliente cliente, Reserva.EstadoReserva estado);
}
