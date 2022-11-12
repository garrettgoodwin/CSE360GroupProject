// import java.util.ArrayList;
// import java.util.List;

// public class PrototypeModel {

//     // Users
//     public static Customer CUSTOMER = new Customer(100, "CUSTOMER", "John Customer", 100, "JCustomer@asu.edu", 100);
//     public static Admin ADMIN = new Admin(101, "ADMIN", "John Admin", 101, "JAdmin@asu.edu", 101);
//     public static Chef CHEF = new Chef(102, "CHEF", "John Chef", 102, "JChef@asu.edu", 102);
//     public static OrderProccessor ORDER_PROCESSOR = new OrderProccessor(103, "PROCESSOR", "John O. Processor", 103, "JOProcessor@asu.edu", 103);

//     // Pizzas
//     // saved
//     public static Pizza PIZZA1 = new Pizza(101, Pizza.CHEESE, true, false, false, false, 1);
//     public static Pizza PIZZA2 = new Pizza(102, Pizza.VEGETABLE, false, true, true, false, 2);
//     public static Pizza PIZZA3 = new Pizza(103, Pizza.PEPPERONI, false, true, false, false, 1);
//     public static Pizza PIZZA4 = new Pizza(104, Pizza.VEGETABLE, true, true, true, false, 3);
//     public static Pizza PIZZA5 = new Pizza(105, Pizza.PEPPERONI, false, false, true, false, 3);
//     // order processor
//     public static Pizza PIZZA6 = new Pizza(106, Pizza.PEPPERONI, true, false, true, false, 3);
//     public static Pizza PIZZA7 = new Pizza(107, Pizza.CHEESE, false, true, true, false, 3);
//     // chef
//     public static Pizza PIZZA8 = new Pizza(108, Pizza.PEPPERONI, true, false, true, false, 3);
//     public static Pizza PIZZA9 = new Pizza(108, Pizza.VEGETABLE, false, true, true, false, 3);
//     public static Pizza PIZZA10 = new Pizza(110, Pizza.CHEESE, true, false, false, true, 3);


//     // Orders
//     // saved
//     public static Order ORDER1 = new Order(101, new ArrayList<Pizza>(List.of(PIZZA1)), Order.PICKED_UP, CUSTOMER.getId());
//     public static Order ORDER2 = new Order(102, new ArrayList<Pizza>(List.of(PIZZA2)), Order.CANCELLED, CUSTOMER.getId());
//     public static Order ORDER3 = new Order(103, new ArrayList<Pizza>(List.of(PIZZA3, PIZZA4)), Order.PICKED_UP, CUSTOMER.getId());
//     public static Order[] SAVED_ORDERS = {
//         PrototypeModel.ORDER1,
//         PrototypeModel.ORDER2,
//         PrototypeModel.ORDER3
//     };
//     // for order processor
//     public static Order ORDER4 = new Order(104, new ArrayList<Pizza>(List.of(PIZZA6)), Order.ACCEPTED, CUSTOMER.getId());
//     public static Order ORDER5 = new Order(105, new ArrayList<Pizza>(List.of(PIZZA7)), Order.ACCEPTED, CUSTOMER.getId());
//     public static Order[] ACCEPTED_ORDERS = {
//         PrototypeModel.ORDER4,
//         PrototypeModel.ORDER5
//     };
//     // for chef
//     public static Order ORDER6 = new Order(106, new ArrayList<Pizza>(List.of(PIZZA8)), Order.READY_TO_COOK, CUSTOMER.getId());
//     public static Order ORDER7 = new Order(107, new ArrayList<Pizza>(List.of(PIZZA9)), Order.COOKING, CUSTOMER.getId());
//     public static Order ORDER8 = new Order(108, new ArrayList<Pizza>(List.of(PIZZA10)), Order.COOKING, CUSTOMER.getId());
//     public static Order[] CHEF_ORDERS = {
//         PrototypeModel.ORDER6,
//         PrototypeModel.ORDER7,
//         PrototypeModel.ORDER8
//     };
// }
