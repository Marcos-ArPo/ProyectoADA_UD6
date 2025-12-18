package parking.proyectoada_ud6.service;

import parking.proyectoada_ud6.entities.Plaza;
import parking.proyectoada_ud6.entities.Reserva;
import parking.proyectoada_ud6.exceptions.ResourceNotFoundException;
import parking.proyectoada_ud6.repositories.PlazaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlazaService {
    
    @Autowired
    private PlazaRepository plazaRepository;
    
    public List<Plaza> getAllPlazas() {
        return plazaRepository.findAll();
    }
    
    public Plaza getPlazaById(Long id) {
        return plazaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plaza no encontrada con id: " + id));
    }
    
    public List<Plaza> getPlazasDisponiblesPorTipo(Plaza.TipoPlaza tipo) {
        return plazaRepository.findByTipoAndEstado(tipo, Plaza.EstadoPlaza.DISPONIBLE);
    }
    
    public List<Plaza> getPlazasPorPlanta(String planta) {
        return plazaRepository.findByPlanta(planta);
    }
    
    @Transactional
    public Plaza createPlaza(Plaza plaza) {
        if (plazaRepository.findByNumeroPlaza(plaza.getNumeroPlaza()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una plaza con el número: " + plaza.getNumeroPlaza());
        }
        return plazaRepository.save(plaza);
    }
    
    @Transactional
    public Plaza updatePlaza(Long id, Plaza plazaDetails) {
        Plaza plaza = getPlazaById(id);
        
        plaza.setNumeroPlaza(plazaDetails.getNumeroPlaza());
        plaza.setTipo(plazaDetails.getTipo());
        plaza.setEstado(plazaDetails.getEstado());
        plaza.setPlanta(plazaDetails.getPlanta());
        
        return plazaRepository.save(plaza);
    }
    
    @Transactional
    public void deletePlaza(Long id) {
        Plaza plaza = getPlazaById(id);
        
        // Verificar si la plaza tiene reservas activas
        if (!plaza.getReservas().isEmpty()) {
            boolean hasActiveReservas = plaza.getReservas().stream()
                    .anyMatch(r -> r.getEstado() == Reserva.EstadoReserva.ACTIVA);
            
            if (hasActiveReservas) {
                throw new IllegalStateException("No se puede eliminar la plaza porque tiene reservas activas");
            }
        }
        
        plazaRepository.delete(plaza);
    }
}
