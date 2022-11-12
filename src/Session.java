public class Session {
    public static final int GUEST_SESSION = 0;
    private int id;
    private User user;
    private Order order;
    private boolean isClosed;
    // guest session
    Session() {
        this(GUEST_SESSION, User.GUEST, Order.BLANK);
    }
    Session(int id, User user, Order order) {
        this.id = id;
        this.user = user;
        this.order = order;
        this.isClosed = false;
    }
    public Response login(String username, String password) {
        Login login = Database.login(username, password);
        login(login);
        return login.getResponse();
    }
    public Response createAccount(String username, String password, String name, String email, String phoneNumber, int asurite) {
        // check for non-database-related valid entered information (e.g. username length, etc)
        // Database.createAccount will conduct database-related exception checking
        //      (e.g. username already exists in database)
        Response response;
        response = User.Username.validate(username);
        if (!Response.ok(response)) {
            return response;
        }
        response = User.Email.validate(email);
        if (!Response.ok(response)) {
            return response;
        }
        response = User.Password.validate(password);
        if (!Response.ok(response)) {
            return response;
        }

        // if passes surface tests
        Login login = Database.createAccount(username, password, name, email, phoneNumber, asurite);
        if (login.isAccepted()) {
            login(login);
        }
        return login.getResponse();
    }

    public void continueAsGuest() {
        Login login = Database.createGuestSession();
        login(login);
    }

    /* (Admin) : Create an account of any type */
    /* types: Admin, Chef, OrderProcessor, Customer */
    public Response createAccount(String username, String password, String name, String email, String phoneNumber, int asurite, int type) {
        // check for non-database-related valid entered information (e.g. username length, etc)
        // Database.createAccount will conduct database-related exception checking
        //      (e.g. username already exists in database)
        Response response;
        response = User.Username.validate(username);
        if (!Response.ok(response)) {
            return response;
        }
        response = User.Email.validate(email);
        if (!Response.ok(response)) {
            return response;
        }
        response = User.Password.validate(password);
        if (!Response.ok(response)) {
            return response;
        }

        return Database.createAccount(this.getId(), username, password, name, email, phoneNumber, asurite, type);
    }

    /* (Admin) : Give permissions (change type of) an account via User email */
    public Response givePermissions(String email, int type) {
        return Database.givePermissions(this.getId(), email, type);
    }

    public int getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public Order getOrder() {
        return order;
    }
    public Response placeOrder(String cardNumber, String cardholderName, String exp, int cvv) {
        Response response = Payment.validate(cardNumber, exp, Integer.toString(cvv));
        if (Response.ok(response)) {
            order.setStatus(Order.ACCEPTED);
            Database.saveOrder(order, this.getId());
        }
        return response;
    }
    public float calculateTotal() {
        return order.calculateTotal();
    }
    public int getOrderId() {
        return order.getId();
    }
    public boolean getIsClosed() {
        return isClosed;
    }
    private void login(Login login) {
        if (login.isAccepted()) {
            setId(login.sessionId);
            setUser(login.userId);
            setOrder(login.orderId);
        }
    }
    private void setId(int id) {
        this.id = id;
    }
    private void setUser(int userId) {
        User user = Database.getUser(userId, this.getId());
        setUser(user);
    }
    private void setUser(User user) {
        this.user = user;
    }
    private void setOrder(int orderId) {
        Order order = Database.getOrder(orderId, this.getId());
        setOrder(order);
    }
    private void setOrder(Order order) {
        this.order = order;
    }
    public String getOrderStatusText() {
        return order.getStatusText();
    }
    /* user role */
    public boolean isGuest() {
        return user.isGuest();
    }
    public boolean isLoggedIn() {
        return user.isLoggedIn();
    }
    public boolean isCustomer() {
        return user.isCustomer();
    }
    public boolean isAdmin() {
        return user.isAdmin();
    }
    public boolean isChef() {
        return user.isChef();
    }
    public boolean isOrderProcessor() {
        return user.isOrderProcessor();
    }
    public boolean isEmployee() {
        return user.isEmployee();
    }
    /* pizza customization */
    /* return pizza id */
    public int addPizza(int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        Pizza pizza = Pizza.create(getId(), getOrderId(), pizzaType, mushrooms, olives, onions, extraCheese, quantity);
        order.addPizza(pizza);
        return pizza.getId();
    }
    public void updatePizza(int pizzaId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        order.updatePizza(pizzaId, pizzaType, mushrooms, olives, onions, extraCheese, quantity);
    }
    public void removePizza(int pizzaId) {
        order.removePizza(pizzaId);
    }
    /* order retreival */
    public Order[] getSavedOrders() {
        return Database.getSavedOrders(user.getId(), this.getId());
    }
    // returns empty [] if not order processor or admin
    public Order[] getOrdersForProcessing() {
        return Database.getOrdersForProcessing(this.getId());
    }
    // returns empty [] if not chef or admin
    public Order[] getOrdersForCooking() {
        return Database.getOrdersForCooking(this.getId());
    }

    /* Order Processor */
    public void markOrderReadyToCook(int orderId) {
        Database.markOrderReadyToCook(orderId, this.getId());
    }
    /* Chef */
    public void markOrderCooking(int orderId) {
        Database.markOrderCooking(orderId, this.getId());
    }
    /* Chef */
    public void markOrderReady(int orderId) {
        Database.markOrderReady(orderId, this.getId());
        sendEmailToCustomer(orderId);
    }
    /* I don't think this will ever be used in the actual application */
    public void markOrderPickedUp(int orderId) {
        Database.markOrderPickedUp(orderId, this.getId());
    }
    
    /* saved payment methods */
    public void savePaymentMethod(String cardNumber, String exp, String cardholderName, int cvv) {
        Database.createPayment(user.getId(), cardNumber, exp, cardholderName, cvv);
    }
    public Payment[] getSavedPaymentMethods() {
        return Database.getSavedPaymentMethods(user.getId(), this.getId());
    }

    public void sendEmailToCustomer(int orderId) {
        Order order = Database.getOrder(orderId, this.getId());
        String email = Database.getOrderCustomerEmail(orderId, this.getId());
        String name = Database.getOrderCustomerName(orderId, this.getId());
        String status = order.getStatusText();
        String subject = "SunDevil Pizza: Your Order is " + status;
        String body = name + ",\n"
            + "your SunDevil Pizza order is " + status + ".\n"
            + order.getReceipt();
        User.Email.sendEmail(email, subject, body);
    }
    
    @Override
    public String toString() {
        String str = "Session #" + getId() + "\n\n";
        str += getUser().toString() + "\n\n";
        str += getOrder().toString();
        return str;
    }
}
