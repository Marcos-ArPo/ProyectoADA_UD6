package parking.proyectoada_ud6.controllers;

import parking.proyectoada_ud6.entities.Plaza;
import parking.proyectoada_ud6.service.PlazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parking/proyectoada_ud6/plazas")
@CrossOrigin(origins = "*")
public class PlazaController {
    
    @Autowired
    private PlazaService plazaService;
    
    // GET: Obtener todas las plazas
    @GetMapping
    public ResponseEntity<List<Plaza>> getAllPlazas() {
        List<Plaza> plazas = plazaService.getAllPlazas();
        return ResponseEntity.ok(plazas);
    }
    
    // GET: Obtener plaza por ID
    @GetMapping("/{id}")
    public ResponseEntity<Plaza> getPlazaById(@PathVariable Long id) {
        Plaza plaza = plazaService.getPlazaById(id);
        return ResponseEntity.ok(plaza);
    }
    
    // GET: Obtener plazas disponibles por tipo
    @GetMapping("/disponibles/{tipo}")
    public ResponseEntity<List<Plaza>> getPlazasDisponiblesPorTipo(@PathVariable Plaza.TipoPlaza tipo) {
        List<Plaza> plazas = plazaService.getPlazasDisponiblesPorTipo(tipo);
        return ResponseEntity.ok(plazas);
    }
    
    // GET: Obtener plazas por planta
    @GetMapping("/planta/{planta}")
    public ResponseEntity<List<Plaza>> getPlazasPorPlanta(@PathVariable String planta) {
        List<Plaza> plazas = plazaService.getPlazasPorPlanta(planta);
        return ResponseEntity.ok(plazas);
    }
    
    // POST: Crear nueva plaza
    @PostMapping
    public ResponseEntity<?> createPlaza(@RequestBody Plaza plaza) {
        try {
            Plaza nuevaPlaza = plazaService.createPlaza(plaza);
            return new ResponseEntity<>(nuevaPlaza, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // PUT: Actualizar plaza
    @PutMapping("/{id}")
    public ResponseEntity<Plaza> updatePlaza(@PathVariable Long id, @RequestBody Plaza plazaDetails) {
        Plaza plazaActualizada = plazaService.updatePlaza(id, plazaDetails);
        return ResponseEntity.ok(plazaActualizada);
    }
    
    // DELETE: Eliminar plaza
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaza(@PathVariable Long id) {
        try {
            plazaService.deletePlaza(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Plaza eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
