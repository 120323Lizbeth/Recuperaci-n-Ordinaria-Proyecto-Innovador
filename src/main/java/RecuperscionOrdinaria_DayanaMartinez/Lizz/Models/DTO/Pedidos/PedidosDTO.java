package RecuperscionOrdinaria_DayanaMartinez.Lizz.Models.DTO.Pedidos;

import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDate;

@Getter @Setter
@ToString
@EqualsAndHashCode
public class PedidosDTO {
    private Long id;
    private Long id_Cliente;
    private Long id_Producto;

    private int cantidad;
    @PastOrPresent(message = "La fecha de ingreso debe ser en el pasado o presente")
    private LocalDate fecha_Pedido;
    private String estado;
    private double total; //Usuario que lo registro

}
