package parking.proyectoada_ud6.controllers;

import parking.proyectoada_ud6.entities.Reserva;
import parking.proyectoada_ud6.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parking/proyectoada_ud6/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;
    
    // GET: Obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.getAllReservas();
        return ResponseEntity.ok(reservas);
    }
    
    // GET: Obtener reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Reserva reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }
    
    // GET: Obtener reservas por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Reserva>> getReservasByCliente(@PathVariable Long clienteId) {
        List<Reserva> reservas = reservaService.getReservasByCliente(clienteId);
        return ResponseEntity.ok(reservas);
    }
    
    // POST: Crear nueva reserva
    @PostMapping("/cliente/{clienteId}/plaza/{plazaId}")
    public ResponseEntity<?> createReserva(
            @PathVariable Long clienteId,
            @PathVariable Long plazaId,
            @RequestBody Reserva reserva) {
        try {
            Reserva nuevaReserva = reservaService.createReserva(reserva, clienteId, plazaId);
            return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
        } catch (IllegalStateException | IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // PUT: Actualizar reserva (solo estado)
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Long id, @RequestBody Reserva reservaDetails) {
        Reserva reservaActualizada = reservaService.updateReserva(id, reservaDetails);
        return ResponseEntity.ok(reservaActualizada);
    }
    
    // PUT: Cancelar reserva
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        try {
            reservaService.cancelarReserva(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Reserva cancelada correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // DELETE: Eliminar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable Long id) {
        try {
            reservaService.deleteReserva(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Reserva eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
