package parking.proyectoada_ud6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoadaUd6Application {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoadaUd6Application.class, args);
		System.out.println("Parking API iniciada en http://localhost:8080");
        System.out.println("Endpoints disponibles:");
        System.out.println("  - GET    /parking/proyectoada_ud6/clientes");
        System.out.println("  - GET    /parking/proyectoada_ud6/reservas");
        System.out.println("  - GET    /parking/proyectoada_ud6/plazas");
	}

}
