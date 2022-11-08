public class Pizza {
    public static final int BLANK_ID = 0;
    /* Pizza Types */
    public static final int PEPPERONI = 0;
    public static final int VEGETABLE = 1;
    public static final int CHEESE = 2;

    /* Prices */
    public static final float TOPPING_PRICE = 1.00f;
    public static final float CHEESE_PRICE = 10.00f;
    public static final float PEPPERONI_PRICE = 10.00f;
    public static final float VEGETABLE_PRICE = 12.00f;

    int id;
    int pizzaType;
    boolean mushrooms;
    boolean olives;
    boolean onions;
    boolean extraCheese;
    int quantity;
    Pizza(int id, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        this.id = id;
        this.pizzaType = pizzaType;
        this.mushrooms = mushrooms;
        this.olives = olives;
        this.onions = onions;
        this.extraCheese = extraCheese;
        this.quantity = quantity;
    }
    public int getId() {
        return id;
    }
    public int getQuantity() {
        return quantity;
    }
    public float calculatePrice() {
        float price = 0.0f;
        if (isCheese()) {
            price = CHEESE_PRICE;
        } else if (isPepperoni()) {
            price = PEPPERONI_PRICE;
        } else if (isVegetable()) {
            price = VEGETABLE_PRICE;
        }
        
        if (hasMushrooms())
            price += TOPPING_PRICE;
        if (hasExtraCheese())
            price += TOPPING_PRICE;
        if (hasOlives())
            price += TOPPING_PRICE;
        if (hasOnions())
            price += TOPPING_PRICE;

        return price * quantity;
    }
    public void update(int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        this.pizzaType = pizzaType;
        this.mushrooms = mushrooms;
        this.olives = olives;
        this.onions = onions;
        this.extraCheese = extraCheese;
        this.quantity = quantity;
    }

    /* Pizza Type & Toppings */
    public int getType() {
        return pizzaType;
    }
    public boolean isPepperoni() {
        return this.pizzaType == Pizza.PEPPERONI;
    }
    public boolean isVegetable() {
        return this.pizzaType == Pizza.VEGETABLE;
    }
    public boolean isCheese() {
        return this.pizzaType == Pizza.CHEESE;
    }
    public boolean hasMushrooms() {
        return this.mushrooms;
    }
    public boolean hasOnions() {
        return this.onions;
    }
    public boolean hasOlives() {
        return this.olives;
    }
    public boolean hasExtraCheese() {
        return this.extraCheese;
    }

    /* Create new pizza */
    public static Pizza create(int sessionId, int orderId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        int pizzaId = Database.createPizza(sessionId, orderId, pizzaType, mushrooms, olives, onions, extraCheese, quantity);
        return new Pizza(pizzaId, pizzaType, mushrooms, olives, onions, extraCheese, quantity);
    }
}
