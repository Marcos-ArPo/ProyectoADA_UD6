package parking.proyectoada_ud6.service;

import parking.proyectoada_ud6.entities.Cliente;
import parking.proyectoada_ud6.entities.Reserva;
import parking.proyectoada_ud6.repositories.ClienteRepository;
import parking.proyectoada_ud6.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
    
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }
    
    public Cliente getClienteByMatricula(String matricula) {
        return clienteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con matrícula: " + matricula));
    }
    
    @Transactional
    public Cliente createCliente(Cliente cliente) {
        if (clienteRepository.existsByMatricula(cliente.getMatricula())) {
            throw new IllegalArgumentException("Ya existe un cliente con la matrícula: " + cliente.getMatricula());
        }
        return clienteRepository.save(cliente);
    }
    
    @Transactional
    public Cliente updateCliente(Long id, Cliente clienteDetails) {
        Cliente cliente = getClienteById(id);
        
        cliente.setNombre(clienteDetails.getNombre());
        cliente.setApellidos(clienteDetails.getApellidos());
        cliente.setTipoCliente(clienteDetails.getTipoCliente());
        cliente.setCuotaPagada(clienteDetails.getCuotaPagada());
        
        if (!cliente.getMatricula().equals(clienteDetails.getMatricula())) {
            if (clienteRepository.existsByMatricula(clienteDetails.getMatricula())) {
                throw new IllegalArgumentException("Ya existe un cliente con la matrícula: " + clienteDetails.getMatricula());
            }
            cliente.setMatricula(clienteDetails.getMatricula());
        }
        
        return clienteRepository.save(cliente);
    }
    
    @Transactional
    public void deleteCliente(Long id) {
        Cliente cliente = getClienteById(id);
        
        if (!cliente.getReservas().isEmpty()) {
            boolean hasActiveReservas = cliente.getReservas().stream()
                    .anyMatch(r -> r.getEstado() == Reserva.EstadoReserva.ACTIVA);
            
            if (hasActiveReservas) {
                throw new IllegalStateException("No se puede eliminar el cliente porque tiene reservas activas");
            }
        }
        
        clienteRepository.delete(cliente);
    }
}
