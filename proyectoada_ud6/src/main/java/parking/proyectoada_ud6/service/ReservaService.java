package parking.proyectoada_ud6.service;

import parking.proyectoada_ud6.entities.Reserva;
import parking.proyectoada_ud6.entities.Cliente;
import parking.proyectoada_ud6.entities.Plaza;
import parking.proyectoada_ud6.exceptions.ResourceNotFoundException;
import parking.proyectoada_ud6.repositories.ReservaRepository;
import parking.proyectoada_ud6.repositories.ClienteRepository;
import parking.proyectoada_ud6.repositories.PlazaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PlazaRepository plazaRepository;
    
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }
    
    public Reserva getReservaById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
    }
    
    public List<Reserva> getReservasByCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId));
        return reservaRepository.findByCliente(cliente);
    }
    
    @Transactional
    public Reserva createReserva(Reserva reserva, Long clienteId, Long plazaId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId));
        
        Plaza plaza = plazaRepository.findById(plazaId)
                .orElseThrow(() -> new ResourceNotFoundException("Plaza no encontrada con id: " + plazaId));
        
        // Validar disponibilidad de la plaza
        if (plaza.getEstado() != Plaza.EstadoPlaza.DISPONIBLE) {
            throw new IllegalStateException("La plaza no está disponible");
        }
        
        // Validar que el cliente VIP puede reservar plaza VIP
        if (plaza.getTipo() == Plaza.TipoPlaza.VIP && cliente.getTipoCliente() != Cliente.TipoCliente.VIP) {
            throw new IllegalStateException("Solo clientes VIP pueden reservar plazas VIP");
        }
        
        // Validar que el cliente VIP tiene la cuota pagada
        if (plaza.getTipo() == Plaza.TipoPlaza.VIP && !cliente.getCuotaPagada()) {
            throw new IllegalStateException("El cliente VIP debe tener la cuota pagada");
        }
        
        // Validar solapamiento de reservas
        List<Reserva> reservasExistentes = reservaRepository.findByFechaResAndEstado(
                reserva.getFechaRes(), Reserva.EstadoReserva.ACTIVA);
        
        for (Reserva existente : reservasExistentes) {
            if (existente.getPlaza().getIdPlaza().equals(plazaId)) {
                // Verificar solapamiento de horarios
                if (reserva.getHoraIni().isBefore(existente.getHoraFin()) && 
                    reserva.getHoraFin().isAfter(existente.getHoraIni())) {
                    throw new IllegalStateException("La plaza ya está reservada en ese horario");
                }
            }
        }
        
        reserva.setCliente(cliente);
        reserva.setPlaza(plaza);
        reserva.setEstado(Reserva.EstadoReserva.ACTIVA);
        
        // Actualizar estado de la plaza
        plaza.setEstado(Plaza.EstadoPlaza.RESERVADA);
        plazaRepository.save(plaza);
        
        return reservaRepository.save(reserva);
    }
    
    @Transactional
    public Reserva updateReserva(Long id, Reserva reservaDetails) {
        Reserva reserva = getReservaById(id);
        
        // Solo permitir actualizar ciertos campos
        reserva.setEstado(reservaDetails.getEstado());
        
        return reservaRepository.save(reserva);
    }
    
    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = getReservaById(id);
        
        if (reserva.getEstado() != Reserva.EstadoReserva.ACTIVA) {
            throw new IllegalStateException("Solo se pueden cancelar reservas activas");
        }
        
        reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
        
        // Liberar la plaza
        Plaza plaza = reserva.getPlaza();
        plaza.setEstado(Plaza.EstadoPlaza.DISPONIBLE);
        plazaRepository.save(plaza);
        
        reservaRepository.save(reserva);
    }
    
    @Transactional
    public void deleteReserva(Long id) {
        Reserva reserva = getReservaById(id);
        
        if (reserva.getEstado() == Reserva.EstadoReserva.ACTIVA) {
            throw new IllegalStateException("No se puede eliminar una reserva activa. Cáncela primero.");
        }
        
        reservaRepository.delete(reserva);
    }
}
