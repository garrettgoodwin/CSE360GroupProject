public class OrderProcessor extends User
{
    public static final int TYPE = 3;
    public static final String TYPE_TEXT = "Order Processor";

    OrderProcessor(int id, String username, String name, int asurite, String email, String phoneNumber) {
        super(id, username, name, OrderProcessor.TYPE, asurite, email, phoneNumber);
    }
}
