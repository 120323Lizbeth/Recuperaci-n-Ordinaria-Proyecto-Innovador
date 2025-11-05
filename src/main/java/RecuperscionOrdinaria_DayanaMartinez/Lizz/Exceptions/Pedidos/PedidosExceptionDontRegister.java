package RecuperscionOrdinaria_DayanaMartinez.Lizz.Exceptions.Pedidos;

public class PedidosExceptionDontRegister extends RuntimeException {
    public String PedidosExceptioncolumnDuplicate;

    public PedidosExceptionDontRegister(String message) {
        super(message);
    }

}
