package RecuperscionOrdinaria_DayanaMartinez.Lizz.Services.Pedidos;

import RecuperscionOrdinaria_DayanaMartinez.Lizz.Entities.Pedidos.PedidosEntities;
import RecuperscionOrdinaria_DayanaMartinez.Lizz.Exceptions.Pedidos.PedidosExceptionDontRegister;
import RecuperscionOrdinaria_DayanaMartinez.Lizz.Exceptions.Pedidos.PedidosExceptionsNotFound;
import RecuperscionOrdinaria_DayanaMartinez.Lizz.Models.DTO.Pedidos.PedidosDTO;
import RecuperscionOrdinaria_DayanaMartinez.Lizz.Repositories.Pedidos.PedidosRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidosServices {

    @Autowired
    public PedidosRepository repo;

    public Page<PedidosDTO> getAllPedidos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PedidosEntities> pageEntity = repo.findAll(pageable);
        return pageEntity.map(this::ConvertirADTO);
    }

    public PedidosDTO insert(@Valid PedidosDTO json) {
        if (json == null){
            throw new IllegalArgumentException("La información del pedidos no puede ser nulo");
        }
        try{
            PedidosEntities objData = ConvertirAEntities(json);
            PedidosEntities pedidosGuardado = repo.save(objData);
            return ConvertirADTO(pedidosGuardado);
        }catch (Exception e){
            log.error("Error al registrar el pedido" + e.getMessage());
            throw new PedidosExceptionDontRegister("El pedido no pudo ser registrado");
        }
    }

    public PedidosDTO update(Long id, @Valid PedidosDTO json) {
        //1. Verificar existencia
        PedidosEntities pedidosExistente = repo.findById(id).orElseThrow(()-> new PedidosExceptionsNotFound("Producto no encontrado."));
        //2. Actualizar campos
        pedidosExistente.setIdCliente(json.getId_Cliente());
        pedidosExistente.setIdProducto(json.getId_Producto());
        pedidosExistente.setCantidad(json.getCantidad());
        pedidosExistente.setFecha_Pedido(json.getFecha_Pedido());
        pedidosExistente.setEstado(json.getEstado());
        pedidosExistente.setTotal(json.getTotal());

        //3. Actualización del registro
        PedidosEntities pedidosActualizado = repo.save(pedidosExistente);
        //4. Convertir a DTO
        return ConvertirADTO(pedidosActualizado);
    }

    public boolean delete(Long id) {
        //1. Verificar la existencia del producto
        PedidosEntities existencia = repo.findById(id).orElse(null);
        //2. Si el valor existe lo elimina
        if (existencia != null){
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    private PedidosDTO ConvertirADTO(PedidosEntities objEntity) {
        PedidosDTO dto = new PedidosDTO();
        dto.setId(objEntity.getId());
        dto.setId_Cliente(objEntity.getIdCliente());
        dto.setId_Producto(objEntity.getIdProducto());
        dto.setCantidad(objEntity.getCantidad());
        dto.setFecha_Pedido(objEntity.getFecha_Pedido());
        dto.setEstado(objEntity.getEstado());
        dto.setTotal(objEntity.getTotal());
        return dto;
    }

    private PedidosEntities ConvertirAEntities(@Valid PedidosDTO json) {
        PedidosEntities entity = new PedidosEntities();
        entity.setIdCliente(json.getId_Cliente());
        entity.setIdProducto(json.getId_Producto());
        entity.setCantidad(json.getCantidad());
        entity.setFecha_Pedido(json.getFecha_Pedido());
        entity.setEstado(json.getEstado());
        entity.setTotal(json.getTotal());
        return entity;
    }
}
