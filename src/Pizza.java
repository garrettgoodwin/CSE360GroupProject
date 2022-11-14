import java.text.NumberFormat;

public class Pizza {
    public static final int BLANK_ID = 0;
    /* Pizza Types */
    public static final int PEPPERONI = 0;
    public static final int VEGETABLE = 1;
    public static final int CHEESE = 2;

    /* Type + Topping Text */
    public static final String PEPPERONI_TEXT = "Pepperoni";
    public static final String VEGETABLE_TEXT = "Vegetable";
    public static final String CHEESE_TEXT = "Cheese";

    public static final String MUSHROOMS_TEXT = "Mushrooms";
    public static final String OLIVES_TEXT = "Olives";
    public static final String ONIONS_TEXT = "Onions";
    public static final String EXTRA_CHEESE_TEXT = "Extra Cheese";

    /* Prices */
    public static final float TOPPING_PRICE = 1.00f;
    public static final float CHEESE_PRICE = 10.00f;
    public static final float PEPPERONI_PRICE = 10.00f;
    public static final float VEGETABLE_PRICE = 12.00f;

    public static final Pizza BLANK_PIZZA = new Pizza();

    private int id;
    private int pizzaType;
    private boolean mushrooms;
    private boolean olives;
    private boolean onions;
    private boolean extraCheese;
    private int quantity;
    Pizza(int id, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        this.id = id;
        this.pizzaType = pizzaType;
        this.mushrooms = mushrooms;
        this.olives = olives;
        this.onions = onions;
        this.extraCheese = extraCheese;
        this.quantity = quantity;
    }
    /* Blank Pizza */
    private Pizza() {
        this(BLANK_ID, PEPPERONI, false, false, false, false, 0);
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
        if (hasOlives())
            price += TOPPING_PRICE;
        if (hasOnions())
            price += TOPPING_PRICE;
        if (hasExtraCheese())
            price += TOPPING_PRICE;

        return price * quantity;
    }
    
    public String getPriceText() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(calculatePrice());
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
    public String getTypeText() {
        if (isCheese())
            return CHEESE_TEXT;
        if (isVegetable())
            return VEGETABLE_TEXT;
        if (isPepperoni())
            return PEPPERONI_TEXT;
    
        return "null";
    }
    public String getToppingsText() {
        final String delimeter = ", ";
        String str = "";

        if (hasMushrooms())
            str += MUSHROOMS_TEXT + delimeter;
        if (hasOlives())
            str += OLIVES_TEXT + delimeter;
        if (hasOnions())
            str += ONIONS_TEXT + delimeter;
        if (hasExtraCheese())
            str += EXTRA_CHEESE_TEXT + delimeter;

        // no toppings
        if (str.length() == 0)
            return "No Toppings";

        // remove final delimeter
        str = str.substring(0, str.length() - delimeter.length());
        return str;
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

    public String getReceiptText() {
        return getTypeText() + " toppings: " + getToppingsText() + " qty: " + getQuantity() + " price: " + getPriceText();
    }

    @Override
    public String toString() {
        String str = "";
        str += getTypeText() + " Pizza\n";
        str += "Toppings: " + getToppingsText() + "\n";
        str += "qty: " + getQuantity() + "\n";
        str += "price: " + getPriceText();
        return str;
    }
}
