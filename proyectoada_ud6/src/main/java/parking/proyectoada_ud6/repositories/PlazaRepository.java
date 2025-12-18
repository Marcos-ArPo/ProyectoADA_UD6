package parking.proyectoada_ud6.repositories;

import parking.proyectoada_ud6.entities.Plaza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlazaRepository extends JpaRepository<Plaza, Long> {
    List<Plaza> findByTipoAndEstado(Plaza.TipoPlaza tipo, Plaza.EstadoPlaza estado);
    List<Plaza> findByPlanta(String planta);
    Optional<Plaza> findByNumeroPlaza(String numeroPlaza);
    List<Plaza> findByEstado(Plaza.EstadoPlaza estado);
}
