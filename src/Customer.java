
public class Customer extends User 
{
    public static final int TYPE = 4;
    protected Order[] savedOrders;

    Customer(int id, String username, String name, int asurite, String email, int phoneNumber, Order[] savedOrders) {
        super(id, username, name, Customer.TYPE, asurite, email, phoneNumber);
        this.savedOrders = savedOrders;
    }
}
