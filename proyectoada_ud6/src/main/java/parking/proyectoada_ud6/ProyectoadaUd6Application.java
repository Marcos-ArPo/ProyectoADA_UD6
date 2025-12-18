package parking.proyectoada_ud6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoadaUd6Application {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoadaUd6Application.class, args);
		System.out.println("Parking API iniciada en http://localhost:8080");
		System.out.println("Antes de hacer los metodos GET, asegúrate de haber agregado datos a la base de datos.");
		System.out.println("La BBDD se crea automaticamente, solo tienes que insertar los datos de prueba");
		System.out.println("El archivos de datos para la BBDD es el archivo Inserciones.sql");
        System.out.println("Endpoints disponibles:");
        System.out.println("  - GET    /parking/proyectoada_ud6/clientes");
        System.out.println("  - GET    /parking/proyectoada_ud6/reservas");
        System.out.println("  - GET    /parking/proyectoada_ud6/plazas");
	}

}
