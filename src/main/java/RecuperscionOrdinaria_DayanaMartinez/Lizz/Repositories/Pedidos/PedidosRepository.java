package RecuperscionOrdinaria_DayanaMartinez.Lizz.Repositories.Pedidos;

import RecuperscionOrdinaria_DayanaMartinez.Lizz.Entities.Pedidos.PedidosEntities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Anotación que indica que esta interfaz es un componente de tipo "Repositorio"
// Esto le dice a Spring que debe crear una implementación automáticamente
@Repository
public interface PedidosRepository extends JpaRepository<PedidosEntities, Long> {
    // Este //
    // méodo obtiene todos
    // los registros de la entidad pedidosEntities
    // pero aplicando paginación (page y size) mediante el objeto Pageable
    Page<PedidosEntities> findAll(Pageable pageable);
}
