package repositorios;

import modelo.RolUsuario;
import modelo.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioRepositorio extends JpaRepository<Servicio, Long> {
}
