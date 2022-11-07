
public class Customer extends User 
{
    public static final int TYPE = 4;

    Customer(int id, String username, String name, int asurite, String email, int phoneNumber) {
        super(id, username, name, Customer.TYPE, asurite, email, phoneNumber);
    }
}
