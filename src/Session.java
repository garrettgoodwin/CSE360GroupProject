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
    public Response createAccount(String username, String password, String name, String email, int phoneNumber, int asurite) {
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
    public int getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public Order getOrder() {
        return order;
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
    public void addPizza(int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        Pizza pizza = Pizza.create(getId(), getOrderId(), pizzaType, mushrooms, olives, onions, extraCheese, quantity);
        order.addPizza(pizza);
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
}
