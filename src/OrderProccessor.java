public class OrderProccessor extends User
{
    public static final int TYPE = 3;
    OrderProccessor(int id, String username, String name, int asurite, String email, int phoneNumber) {
        super(id, username, name, OrderProccessor.TYPE, asurite, email, phoneNumber);
    }
}