package RecuperscionOrdinaria_DayanaMartinez.Lizz.Entities.Pedidos;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Entity
@Getter @Setter
@ToString @EqualsAndHashCode
@Table(name = "PEDIDOS")
public class PedidosEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_ID_DLM")
    @SequenceGenerator(sequenceName = "PEDIDO_ID_DLM", name = "PEDIDO_ID_DLM", allocationSize = 1)
    @Column(name = "IDPEDIDO")
    private Long id;

    @Column(name = "IDCLIENTE")
    private Long idCliente;

    @Column(name = "IDPRODUCTO")
    private Long idProducto;

    @Column(name = "CANTIDAD")
    private int cantidad;

    @PastOrPresent
    @Column(name = "FECHA_PEDIDO")
    private LocalDate fecha_Pedido;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "TOTAL")
    private Double total;
}
