package RecuperscionOrdinaria_DayanaMartinez.Lizz.Controllers.Pedidos;

import RecuperscionOrdinaria_DayanaMartinez.Lizz.Exceptions.Pedidos.PedidosExceptionDontRegister;
import RecuperscionOrdinaria_DayanaMartinez.Lizz.Exceptions.Pedidos.PedidosExceptionsNotFound;
import RecuperscionOrdinaria_DayanaMartinez.Lizz.Models.DTO.Pedidos.PedidosDTO;
import RecuperscionOrdinaria_DayanaMartinez.Lizz.Services.Pedidos.PedidosServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

// indica que esta clase es un controlador
@RestController
// Define la ruta base del controlador
@RequestMapping("/api/perdidos")
public class PedidosControllers {

    // Inyección del servicio de pedidos
    @Autowired
    private PedidosServices service;

    //Metodo GET
    // Devuelve todos los pedidos paginados
    @GetMapping("/getAllPedidos")
    private ResponseEntity<Page<PedidosDTO>> getPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        // Valida que el tamaño esté entre 1 y 50
        if (size <= 0 || size > 50){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la página debe estar entre 1 y 50"
            ));
            return ResponseEntity.ok(null);
        }
        // Llama al servicio para obtener la lista de pedidos
        Page<PedidosDTO> Pedidos = service.getAllPedidos(page, size);
        if (Pedidos == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "Error al obtener los datos"
            ));
        }
        // Devuelve la lista de pedidos en formato JSON
        return ResponseEntity.ok(Pedidos);
    }

    //Metodo POST
    // Inserta un nuevo pedido en la base de datos
    @PostMapping("/newPedido")
    private ResponseEntity<Map<String, Object>> inserPedido(@Valid @RequestBody PedidosDTO json, HttpServletRequest request){
        try{
            // Llama al servicio para insertar el pedido
            PedidosDTO response =service.insert(json);
            // Si no se inserta correctamente, devuelve error 400
            if (response == null){

                return ResponseEntity.badRequest().body(Map.of(
                        "Error", "Inserción incorrecta",
                        "Estatus", "Inserción incorrecta",
                        "Descripción", "Verifique los valores"
                ));
            }
            // Si la inserción es correcta, devuelve 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "Estado", "Completado",
                    "data", response
            ));
        }catch (Exception e){
            // Si ocurre un error inesperado, devuelve 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar Categoria",
                            "detail", e.getMessage()
                    ));
        }
    }

    //Metodo PUT
    // Actualiza un pedido existente por su ID
    @PutMapping("/updatePedido/{id}")
    public ResponseEntity<?> modificarPedido(
            @PathVariable Long id,// Obtiene el ID del pedido de la URL
            @Valid @RequestBody PedidosDTO pedidos, // Recibe los datos del pedido
            BindingResult bindingResult){
        // Si hay errores de validación, los devuelve al cliente
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            // Llama al servicio para actualizar el pedido
            PedidosDTO pedidosActualizado = service.update(id, pedidos);
            return ResponseEntity.ok(pedidosActualizado);
        }
        catch (PedidosExceptionsNotFound e){
            // Si no encuentra el pedido, devuelve 404
            return ResponseEntity.notFound().build();
        }
        catch (PedidosExceptionDontRegister e){
            // Si hay datos duplicados, devuelve 409 (CONFLICT)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados","campo", e.PedidosExceptioncolumnDuplicate)
            );
        }
    }

    //Metodo DELETE
    // Elimina un pedido por su ID
    @DeleteMapping("/deletePedidos/{id}")
    public ResponseEntity<Map<String, Object>> eliminarPedido(@PathVariable Long id) {
        try {
            // Intenta eliminar el pedido usando el servicio
            if (!service.delete(id)) {
                // Si no existe, devuelve 404 con un mensaje detallado
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        // Agrega un header personalizado
                        .header("X-Mensaje-Error", "Pedido no encontrada")
                        // Cuerpo de la respuesta con detalles del error
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "La categoria no ha sido encontrada",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                ));
            }

            // Si la eliminación fue exitosa, devuelve 200 OK
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Pedido eliminada exitosamente"  // Mensaje de éxito
            ));

        } catch (Exception e) {
            // Si ocurre un error inesperado, devuelve 500
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar la pedido",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }


}
