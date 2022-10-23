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
    public void login(String username, String password) {
        Database db = new Database();
        Login login = db.login(username, password);
        login(login);
    }
    public Response createAccount(String username, String password, String name, String email, int phoneNumber, int asurite) {
        // check for non-database-related valid entered information (e.g. username length, etc)
        // Database.createAccount will conduct database-related exception checking
        //      (e.g. username already exists in database)


        // if passes surface tests
        Database db = new Database();
        Login login = db.createAccount(username, password, name, email, phoneNumber, asurite);
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
    public Order[] getSavedOrders() {
        Order[] savedOrders = Database.getSavedOrders(user.getId(), this.getId());
        return savedOrders;
    }
}
