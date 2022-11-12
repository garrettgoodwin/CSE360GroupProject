// import java.util.ArrayList; // ArrayList

// public class Order {
//     public static int BLANK_ID = 0;
//     public static Order BLANK = new Order();
//     /* Order Statuses */
//     public static int NOT_YET_PLACED = 0;
//     public static String NOT_YET_PLACED_TEXT = "Not Yet Placed";
//     public static int ACCEPTED = 1;
//     public static String ACCEPTED_TEXT = "Accepted";
//     public static int READY_TO_COOK = 2;
//     public static String READY_TO_COOK_TEXT = "Ready To Cook";
//     public static int COOKING = 3;
//     public static String COOKING_TEXT = "Cooking";
//     public static int READY = 4;
//     public static String READY_TEXT = "Ready For Pick Up";
//     public static int PICKED_UP = 5;
//     public static String PICKED_UP_TEXT = "Picked Up";
//     public static int CANCELLED = 6;
//     public static String CANCELLED_TEXT = "Cancelled";

//     private int id;
//     private ArrayList<Pizza> pizzas;
//     private int status;
//     private int userId;
//     private boolean saved;

//     // creating new order as guest
//     Order() {
//         this(Order.BLANK_ID, User.GUEST_ID);
//     }
//     // creating new order while logged in
//     Order(int id, int userId) {
//         this(id, new ArrayList<Pizza>(), Order.NOT_YET_PLACED, userId, false);
//     }
//     // pulling up saved orders
//     Order(int id, ArrayList<Pizza> pizzas, int status, int userId, boolean saved) {
//         this.id = id;
//         this.pizzas = pizzas;
//         this.status = status;
//         this.userId = userId;
//         this.saved = saved;
//     }
    
//     public int getId() {
//         return id;
//     }

//     public void addPizza(Pizza pizza) {
//         pizzas.add(pizza);
//     }

//     // update pizza with id pizzaId
//     public void updatePizza(int pizzaId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
//         // first find pizza with id pizzaId
//         int i = 0;
//         while (i < pizzas.size() && pizzas.get(i).getId() != pizzaId) {
//             i++;
//         }
//         if (i < pizzas.size())  // pizza found
//             // update pizza
//             pizzas.get(i).update(pizzaType, mushrooms, olives, onions, extraCheese, quantity);
//     }

//     // remove pizza with id pizzaId
//     public void removePizza(int pizzaId) {
//         // first find pizza with id pizzaId
//         int i = 0;
//         while (i < pizzas.size() && pizzas.get(i).getId() != pizzaId) {
//             i++;
//         }
//         if (i < pizzas.size())  // pizza found
//             // remove pizza
//             pizzas.remove(i);
//     }

//     public Pizza[] getPizzas() {
//         Pizza[] pizzas = new Pizza[this.pizzas.size()];
//         return this.pizzas.toArray(pizzas);
//     }
//     public void setStatus(int status) {
//         this.status = status;
//     }
//     public int getCount() {
//         /*  # pizzas ≠ pizzas.size()
//             Pizza object has quantity variable */
//         int count = 0;
//         for (int i = 0; i < pizzas.size(); i++) {        
//             count += pizzas.get(i).getQuantity(); 
//         }
//         return count;
//     }
//     public float calculateTotal() {
//         float price = 0.0f;
//         for (int i = 0; i < pizzas.size(); i++) {        
//             price += pizzas.get(i).calculatePrice(); 
//         }
//         return price;
//     }
//     public void setUser(int userId) {
//         this.userId = userId;
//     }
//     public boolean isUser(int userId, int sessionId) {
//         return this.userId == userId;
//     }
//     public void setSaved(boolean saved) {
//         this.saved = saved;
//     } 
//     public boolean isSaved() {
//         return saved;
//     }
//     public void save() {
//         setSaved(true);
//     } 
//     public void unsave() {
//         setSaved(false);
//     } 
//     /* Status checks */
//     public String getStatusText() {
//         if (isAccepted())
//             return Order.ACCEPTED_TEXT;
//         if (isReadyToCook())
//             return Order.READY_TO_COOK_TEXT;
//         if (isCooking())
//             return Order.COOKING_TEXT;
//         if (isReady())
//             return Order.READY_TEXT;
//         if (isPickedUp())
//             return Order.PICKED_UP_TEXT;
//         if (isCancelled())
//             return Order.CANCELLED_TEXT;

//         return Order.NOT_YET_PLACED_TEXT;
//     }
//     public boolean isPlaced() {
//         return this.status != Order.NOT_YET_PLACED;
//     }
//     public boolean isAccepted() {
//         return this.status == Order.ACCEPTED;
//     }
//     public boolean isReadyToCook() {
//         return this.status == Order.READY_TO_COOK;
//     }
//     public boolean isCooking() {
//         return this.status == Order.COOKING;
//     }
//     public boolean isReady() {
//         return this.status == Order.READY;
//     }
//     public boolean isPickedUp() {
//         return this.status == Order.PICKED_UP;
//     }
//     public boolean isCancelled() {
//         return this.status == Order.CANCELLED;
//     }

//     private int getUserId() {
//         return userId;
//     }

//     @Override
//     public String toString() {
//         String str = "Order #" + getId() + "\n\n";
//         str += "status: " + getStatusText() + "\n";
//         str += "is saved: " + isSaved() + "\n";
//         str += "total: " + calculateTotal() + "\n";
//         str += "Pizzas: ";
//         for (int i = 0; i < pizzas.size(); i++) {
//             str += "\n\n" + pizzas.get(i).toString();
//         }
//         if (pizzas.size() == 0) {
//             str += "No Pizzas";
//         }
//         return str;
//     }
// }