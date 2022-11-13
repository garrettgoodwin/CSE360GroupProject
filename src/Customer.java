
public class Customer extends User 
{
    public static final int TYPE = 4;
    public static final String TYPE_TEXT = "Customer";

    Customer(int id, String username, String name, int asurite, String email, String phoneNumber) {
        super(id, username, name, Customer.TYPE, asurite, email, phoneNumber);
    }
}
