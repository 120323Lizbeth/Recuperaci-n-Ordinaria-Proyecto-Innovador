package RecuperscionOrdinaria_DayanaMartinez.Lizz.Exceptions.Pedidos;

import lombok.Getter;

public class PedidosExceptioncolumnDuplicate extends RuntimeException {

    @Getter
    private String PedidoscolumnDuplicate;
    public PedidosExceptioncolumnDuplicate(String message) {
        super(message);
    }

    public PedidosExceptioncolumnDuplicate(String message, String PedidoscolumnDuplicate) {
        super(message);
        this.PedidoscolumnDuplicate = PedidoscolumnDuplicate;
    }
}
