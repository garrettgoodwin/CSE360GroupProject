public class Pizza {
    /* Pizza Types */
    public static final int PEPPERONI = 0;
    public static final int VEGETABLE = 1;
    public static final int CHEESE = 2;

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

    /* Pizza Type & Toppings */
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
}
