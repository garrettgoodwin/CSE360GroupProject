import java.util.ArrayList; // ArrayList

public class Order {
    public static Order BLANK = new Order();
    /* Order Statuses */
    public static int NOT_YET_PLACED = 0;
    public static int ACCEPTED = 1;
    public static int READY_TO_COOK = 2;
    public static int COOKING = 3;
    public static int READY = 4;
    public static int PICKED_UP = 5;
    public static int CANCELLED = 6;

    private ArrayList<Pizza> pizzas;
    private int status;
    private int userId;

    // creating new order as guest
    Order() {
        this(User.GUEST_ID);
    }
    // creating new order while logged in
    Order(int userId) {
        this(new ArrayList<Pizza>(), Order.NOT_YET_PLACED, userId);
    }
    // pulling up saved orders
    Order(ArrayList<Pizza> pizzas, int status, int userId) {
        this.pizzas = pizzas;
        this.status = status;
        this.userId = userId;
    }
    
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }
    public Pizza[] getPizzas() {
        Pizza[] pizzas = new Pizza[this.pizzas.size()];
        return this.pizzas.toArray(pizzas);
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getCount() {
        /*  # pizzas ≠ pizzas.size()
            Pizza object has quantity variable */
        int count = 0;
        for (int i = 0; i < pizzas.size(); i++) {        
            count += pizzas.get(i).getQuantity(); 
        }
        return count;
    }
    public void setUser(int userId) {
        this.userId = userId;
    }
    public boolean isUser(int userId, int sessionId) {
        return this.userId == userId;
    }
    /* Status checks */
    public boolean isPlaced() {
        return this.status != Order.NOT_YET_PLACED;
    }
    public boolean isAccepted() {
        return this.status == Order.ACCEPTED;
    }
    public boolean isReadyToCook() {
        return this.status == Order.READY_TO_COOK;
    }
    public boolean isCooking() {
        return this.status == Order.COOKING;
    }
    public boolean isReady() {
        return this.status == Order.READY;
    }
    public boolean isPickedUp() {
        return this.status == Order.PICKED_UP;
    }
    public boolean isCancelled() {
        return this.status == Order.CANCELLED;
    }
}
