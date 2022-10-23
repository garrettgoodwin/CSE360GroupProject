import java.util.ArrayList;
import java.util.List;

public class PrototypeModel {
    // Pizzas
    public static Pizza PIZZA1 = new Pizza(101, Pizza.CHEESE, true, false, false, false, 1);
    public static Pizza PIZZA2 = new Pizza(102, Pizza.VEGETABLE, false, true, true, false, 1);
    public static Pizza PIZZA3 = new Pizza(103, Pizza.PEPPERONI, false, true, false, false, 1);
    public static Pizza PIZZA4 = new Pizza(104, Pizza.VEGETABLE, true, true, true, false, 3);
    public static Pizza PIZZA5 = new Pizza(105, Pizza.PEPPERONI, false, false, true, false, 3);

    // Orders
    public static Order ORDER1 = new Order(new ArrayList<Pizza>(List.of(PIZZA1)), Order.PICKED_UP);
    public static Order ORDER2 = new Order(new ArrayList<Pizza>(List.of(PIZZA2)), Order.CANCELLED);
    public static Order ORDER3 = new Order(new ArrayList<Pizza>(List.of(PIZZA3, PIZZA4)), Order.PICKED_UP);
    public static Order ORDER4 = new Order(new ArrayList<Pizza>(List.of(PIZZA5)), Order.READY);
    public static Order[] ORDERS = {
        PrototypeModel.ORDER1,
        PrototypeModel.ORDER2,
        PrototypeModel.ORDER3,
        PrototypeModel.ORDER4
    };

    // Users
    public static Customer CUSTOMER = new Customer(100, "CUSTOMER", "John Customer", 100, "JCustomer@asu.edu", 100, PrototypeModel.ORDERS);
    public static Admin ADMIN = new Admin(101, "ADMIN", "John Admin", 101, "JAdmin@asu.edu", 101);
    public static Chef CHEF = new Chef(102, "CHEF", "John Chef", 102, "JChef@asu.edu", 102);
    public static OrderProccessor ORDER_PROCESSOR = new OrderProccessor(103, "PROCESSOR", "John O. Processor", 103, "JOProcessor@asu.edu", 103);
}
