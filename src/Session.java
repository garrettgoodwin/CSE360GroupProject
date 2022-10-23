public class Session {
    public static final int GUEST_SESSION = 0;
    protected int id;
    protected User user;
    protected Order order;
    protected boolean isClosed;
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
        Database db = new Database();
        User user = db.getUser(userId, this.getId());
        setUser(user);
    }
    private void setUser(User user) {
        this.user = user;
    }
    public Order[] getSavedOrders() {
        Database db = new Database();
        Order[] savedOrders = db.getSavedOrders(user.getId(), this.getId());
        return savedOrders;
    }
}
