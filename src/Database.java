import java.sql.Timestamp;  // Timestamp

public class Database {

    /* Database Constants */
    private static String SESSION_TABLE = "sessions";
    private static String USER_ID = "user_id";
    private static String IS_CLOSED = "is_closed";

    private static String USER_TABLE = "users";
    private static String USERNAME = "username";
    private static String NAME = "name";
    private static String ENCRYPTED_PASSWORD = "encrypted_password";
    private static String USER_TYPE = "user_type";
    private static String ASURITE = "asurite";
    private static String EMAIL = "email";
    private static String PHONE_NUMBER = "phone_number";
    private static String SESSION_ID = "session_id";

    private static String ORDER_TABLE = "orders";
    // user_id already defined
    private static String STATUS = "status";

    private static String PIZZA_TABLE = "pizzas";
    private static String ORDER_ID = "order_id";
    private static String PIZZA_TYPE = "pizza_type";
    private static String MUSHROOMS = "mushrooms";
    private static String OLIVES = "olives";
    private static String ONIONS = "onions";
    private static String EXTRA_CHEESE = "extra_cheese";
    private static String QUANTITY = "quantity";

    /*
     * Public methods should be responsible for exception checking/handling
     */

    /* public application interface */
    // static

    public static Login login(String username, String password) {
        if (!usernameExists(username)) {
            return Login.deny(Response.NOT_FOUND);
        }
        int userId = getUserIdFromUsername(username);
        if (!isCorrectPassword(password, userId)) {
            return Login.deny(Response.UNAUTHORIZED);
        }
        int sessionId = createSession(userId);
        return Login.accept(sessionId, userId);
    }

    // create customer account
    public static Login createAccount(String username, String password, String name, String email, int phoneNumber, int asurite) {
        if (usernameExists(username)) {
            return Login.deny(Response.PREEXISTING_USERNAME);
        }
        if (emailExists(email)) {
            return Login.deny(Response.PREEXISTING_EMAIL);
        }
        int type = Customer.TYPE;
        int encryptedPassword = encryptPassword(password);
        int userId = createUser(username, name, type, encryptedPassword, email, phoneNumber, asurite);
        int sessionId = createSession(userId);
        return Login.accept(sessionId, userId);
    }

    // return pizzaId
    public static int createPizza(int sessionId, int orderId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        if (isAdmin(sessionId) || getSession(sessionId).getOrderId() == orderId) {
            return createPizza(orderId, pizzaType, mushrooms, olives, onions, extraCheese, quantity);
        }
        return Pizza.BLANK_ID;
    }

    public static User getUser(int userId, int sessionId) {
        /* START Prototype Code */
        if (userId == User.GUEST_ID)
            return User.GUEST;
        if (userId == PrototypeModel.CUSTOMER.getId())
            return PrototypeModel.CUSTOMER;
        if (userId == PrototypeModel.ADMIN.getId())
            return PrototypeModel.ADMIN;
        if (userId == PrototypeModel.CHEF.getId())
            return PrototypeModel.CHEF;
        if (userId == PrototypeModel.ORDER_PROCESSOR.getId())
            return PrototypeModel.ORDER_PROCESSOR;
        /* END Prototype Code */
        if (hasAccessTo(sessionId, userId)) {
            return getUser(userId);
        }
        return new User();
    }

    public static Session getSession(int sessionId) {
        /* START Prototype Code */
        if (sessionId == Session.GUEST_SESSION)
            return new Session();
        if (sessionId == PrototypeModel.CUSTOMER.getId())
            return new Session(sessionId, PrototypeModel.CUSTOMER, Order.BLANK);
        if (sessionId == PrototypeModel.ADMIN.getId())
            return new Session(sessionId, PrototypeModel.ADMIN, Order.BLANK);
        if (sessionId == PrototypeModel.CHEF.getId())
            return new Session(sessionId, PrototypeModel.CHEF, Order.BLANK);
        if (sessionId == PrototypeModel.ORDER_PROCESSOR.getId())
            return new Session(sessionId, PrototypeModel.ORDER_PROCESSOR, Order.BLANK);
        /* END Prototype Code */

        Connection connection = getConnection();   /* open connection */
        Session session = connection.getSession(sessionId);
        connection.close(); /* close connection */
        return session;
    }

    public static boolean usernameExists(String username) {
        Connection connection = getConnection();   /* open connection */
        boolean exists = connection.usernameExists(username);
        connection.close(); /* close connection */
        return exists;
    }

    public static boolean emailExists(String email) {
        Connection connection = getConnection();   /* open connection */
        boolean exists = connection.emailExists(email);
        connection.close(); /* close connection */
        return exists;
    }

    public static String getUsername(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getUsername(userId);
        }
        return "no";
    }

    public static void setUsername(String username, int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            setUsername(username, userId);
        }
    }
    
    public static String getName(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getName(userId);
        }
        return "name";
    }

    public static void setName(String name, int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            setName(name, userId);
        }
    }
    
    public static int getType(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getType(userId);
        }
        return 0;
    }

    public static void setType(int type, int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            setType(type, userId);
        }
    }

    public static int getAsurite(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getAsurite(userId);
        }
        return 0;
    }

    public static void setAsurite(int asurite, int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            setAsurite(asurite, userId);
        }
    }

    public static String getEmail(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getEmail(userId);
        }
        return "";
    }

    public static void setEmail(String email, int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            setEmail(email, userId);
        }
    }

    public static int getPhoneNumber(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getPhoneNumber(userId);
        }
        return 0;
    }

    public static void setPhoneNumber(int phoneNumber, int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            setPhoneNumber(phoneNumber, userId);
        }
    }

    public static Order[] getSavedOrders(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getSavedOrders(userId);
        }
        return new Order[0];
    }

    public static Order[] getOrdersForProcessing(int sessionId) {
        if (isAdmin(sessionId) || isOrderProcessor(sessionId)) {
            return getOrdersForProcessing();
        }
        return new Order[0];
    }

    public static Order[] getOrdersForCooking(int sessionId) {
        if (isAdmin(sessionId) || isChef(sessionId)) {
            return getOrdersForCooking();
        }
        return new Order[0];
    }

    /* private */
    // interface with back-end database

    /* Database private methods */
    /* static */

    // return sessionId
    private static int createSession(int userId) {
        /* START Prototype Code */
        if (userId == User.GUEST_ID)
            return userId;
        if (userId == PrototypeModel.CUSTOMER.getId())
            return userId;
        if (userId == PrototypeModel.ADMIN.getId())
            return userId;
        if (userId == PrototypeModel.CHEF.getId())
            return userId;
        if (userId == PrototypeModel.ORDER_PROCESSOR.getId())
            return userId;
        /* END Prototype Code */

        int sessionId = generateRandomId();

        Connection connection = getConnection();   /* open connection */
        connection.createSession(sessionId, userId);
        connection.close(); /* close connection */

        return sessionId;
    }

    // return userId
    private static int createUser(String username, String name, int type, int encryptedPassword, String email, int phoneNumber, int asurite) {
        int userId = generateRandomId();

        Connection connection = getConnection();   /* open connection */
        connection.createUser(userId, username, name, type, encryptedPassword, email, phoneNumber, asurite);
        connection.close(); /* close connection */

        return userId;
    }

    // return pizzaId
    private static int createPizza(int orderId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        int pizzaId = generateRandomId();

        Connection connection = getConnection();   /* open connection */
        connection.createPizza(pizzaId, orderId, pizzaType, mushrooms, olives, onions, extraCheese, quantity);
        connection.close(); /* close connection */

        return pizzaId;
    }

    private static int generateRandomId() {
        return 12;  // I feel like 12 is pretty random. I just thought of it at the top of my head
    }

    private static int getEncryptedPassword(int userId) {
        return 0;
    }

    private static boolean isCorrectPassword(String submittedPassword, int userId) {
        int encryptedSubmittedPassword = encryptPassword(submittedPassword);
        final int REAL_ENCRYPTED_PASSWORD = getEncryptedPassword(userId);
        return REAL_ENCRYPTED_PASSWORD == encryptedSubmittedPassword;
    }

    private static int encryptPassword(String password) {
        int encryptedPassword = 0;
        return encryptedPassword;
    }

    private static boolean hasAccessTo(int sessionId, int userId) {
        User sessionUser = getSessionUser(sessionId);
        return sessionUser.getId() == userId || sessionUser.isAdmin();
    }

    private static boolean isCustomer(int sessionId) {
        User sessionUser = getSessionUser(sessionId);
        return sessionUser.isCustomer();
    }

    private static boolean isOrderProcessor(int sessionId) {
        User sessionUser = getSessionUser(sessionId);
        return sessionUser.isOrderProcessor();
    }

    private static boolean isChef(int sessionId) {
        User sessionUser = getSessionUser(sessionId);
        return sessionUser.isChef();
    }

    private static boolean isAdmin(int sessionId) {
        User sessionUser = getSessionUser(sessionId);
        return sessionUser.isAdmin();
    }

    private static User getSessionUser(int sessionId) {
        Session session = getSession(sessionId);
        return session.getUser();
    }

    /* Connection interface */

    /* Get Open Connection */
    /* Warning: Make sure to close connection later */
    private static Connection getConnection() {
        Database db = new Database();
        Connection connection = db.new Connection();    
        connection.connect();  /* open connection */
        return connection;
    }

    /* User Getters + Setters */

    private static User getUser(int userId) {
        Connection connection = getConnection();   /* open connection */
        User user = connection.getUser(userId);
        connection.close(); /* close connection */
        return user;
    }

    private static String getUsername(int userId) {
        Connection connection = getConnection();   /* open connection */
        String username = connection.getUsername(userId);
        connection.close(); /* close connection */
        return username;
    }

    private static void setUsername(String username, int userId) {
        Connection connection = getConnection();   /* open connection */
        connection.setUsername(username, userId);
        connection.close(); /* close connection */
    }

    private static String getName(int userId) {
        Connection connection = getConnection();   /* open connection */
        String name = connection.getName(userId);
        connection.close(); /* close connection */
        return name;
    }

    private static void setName(String name, int userId) {
        Connection connection = getConnection();   /* open connection */
        connection.setName(name, userId);
        connection.close(); /* close connection */
    }
    
    private static int getType(int userId) {
        Connection connection = getConnection();   /* open connection */
        int type = connection.getType(userId);
        connection.close(); /* close connection */
        return type;
    }

    private static void setType(int type, int userId) {
        Connection connection = getConnection();   /* open connection */
        connection.setType(type, userId);
        connection.close(); /* close connection */
    }

    private static int getAsurite(int userId) {
        Connection connection = getConnection();   /* open connection */
        int asurite = connection.getAsurite(userId);
        connection.close(); /* close connection */
        return asurite;
    }

    private static void setAsurite(int asurite, int userId) {
        Connection connection = getConnection();   /* open connection */
        connection.setAsurite(asurite, userId);
        connection.close(); /* close connection */
    }

    private static String getEmail(int userId) {
        Connection connection = getConnection();   /* open connection */
        String email = connection.getEmail(userId);
        connection.close(); /* close connection */
        return email;
    }

    private static void setEmail(String email, int userId) {
        Connection connection = getConnection();   /* open connection */
        connection.setEmail(email, userId);
        connection.close(); /* close connection */
    }

    private static int getPhoneNumber(int userId) {
        Connection connection = getConnection();   /* open connection */
        int phoneNumber = connection.getPhoneNumber(userId);
        connection.close(); /* close connection */
        return phoneNumber;
    }

    private static void setPhoneNumber(int phoneNumber, int userId) {
        Connection connection = getConnection();   /* open connection */
        connection.setPhoneNumber(phoneNumber, userId);
        connection.close(); /* close connection */
    }

    private static Order[] getSavedOrders(int userId) {
        /* START Prototype Code */
        if (userId == PrototypeModel.CUSTOMER.getId())
            return PrototypeModel.SAVED_ORDERS;
        /* END Prototype Code */

        Connection connection = getConnection();
        connection.connect();   /* open connection */
        Order[] savedOrders = connection.getSavedOrders(userId);
        connection.close(); /* close connection */
        return savedOrders;
    }

    private static Order[] getOrdersForProcessing() {
        Connection connection = getConnection();
        connection.connect();   /* open connection */
        Order[] orders = connection.getOrdersForProcessing();
        connection.close(); /* close connection */
        return orders;
    }

    private static Order[] getOrdersForCooking() {
        Connection connection = getConnection();
        connection.connect();   /* open connection */
        Order[] orders = connection.getOrdersForCooking();
        connection.close(); /* close connection */
        return orders;
    }

    private static int getUserIdFromUsername(String username) {
        /* START Prototype Code */
        if (username == User.GUEST_USERNAME)
            return User.GUEST_ID;
        if (username == PrototypeModel.CUSTOMER.getUsername())
            return PrototypeModel.CUSTOMER.getId();
        if (username == PrototypeModel.ADMIN.getUsername())
            return PrototypeModel.ADMIN.getId();
        if (username == PrototypeModel.CHEF.getUsername())
            return PrototypeModel.CHEF.getId();
        if (username == PrototypeModel.ORDER_PROCESSOR.getUsername())
            return PrototypeModel.ORDER_PROCESSOR.getId();
        /* END Prototype Code */
        
        Connection connection = getConnection();   /* open connection */
        int userId = connection.getUserIdFromUsername(username);
        connection.close(); /* close connection */
        return userId;
    }


    /* Connection */
    /* non-static */
    private class Connection {
        private boolean connected;
        Connection() {
            this.connected = false;
        }

        /* managing connection */
        public boolean connect() {
            this.connected = true;
            // connect
            return isConnected();
        }
        public void close() {
            // close
            this.connected = false;
        }
        public boolean isConnected() {
            return connected;
        }
        public boolean isClosed() {
            return !isConnected();
        }

        /* database manipulation interface */

        public User getUser(int userId) {
            String username = getUsername(userId);
            String name = getName(userId);
            int type = getType(userId);
            int asurite = getAsurite(userId);
            String email = getEmail(userId);
            int phoneNumber = getPhoneNumber(userId);

            return User.GUEST;
        }

        public Session getSession(int sessionId) {
            return new Session();
        }

        public boolean usernameExists(String username) {
            return true;
        }

        public boolean emailExists(String username) {
            return true;
        }
        
        public String getUsername(int userId) {
            return "username";
        }
    
        public void setUsername(String username, int userId) {
            userUpdate(Database.USERNAME, username, userId);
        }
    
        public String getName(int userId) {
            return "name";
        }
    
        public void setName(String name, int userId) {
            userUpdate(Database.NAME, name, userId);
        }
        
        public int getType(int userId) {
            return 0;
        }
    
        public void setType(int type, int userId) {
            userUpdate(Database.USER_TYPE, String.valueOf(type), userId);
        }
    
        public int getAsurite(int userId) {
            return 0;
        }
    
        public void setAsurite(int asurite, int userId) {
            userUpdate(Database.ASURITE, String.valueOf(asurite), userId);
        }
    
        public String getEmail(int userId) {
            return "";
        }
    
        public void setEmail(String email, int userId) {
            userUpdate(Database.EMAIL, email, userId);
        }
    
        public int getPhoneNumber(int userId) {
            return 0;
        }
    
        public void setPhoneNumber(int phoneNumber, int userId) {
            userUpdate(Database.PHONE_NUMBER, String.valueOf(phoneNumber), userId);
        }
    
        public Order[] getSavedOrders(int userId) {
            /* START Prototype Code */
            if (userId == PrototypeModel.CUSTOMER.getId())
                return PrototypeModel.SAVED_ORDERS;
            /* END Prototype Code */
            return new Order[0];
        }

        public Order[] getOrdersForProcessing() {
            /* START Prototype Code */
            return PrototypeModel.ACCEPTED_ORDERS;
            /* END Prototype Code */

            // return new Order[0];
        }

        public Order[] getOrdersForCooking() {
            /* START Prototype Code */
            return PrototypeModel.CHEF_ORDERS;
            /* END Prototype Code */

            // return new Order[0];
        }
    
        public int getUserIdFromUsername(String username) {
            /* START Prototype Code */
            if (username == User.GUEST_USERNAME)
                return User.GUEST_ID;
            if (username == PrototypeModel.CUSTOMER.getUsername())
                return PrototypeModel.CUSTOMER.getId();
            if (username == PrototypeModel.ADMIN.getUsername())
                return PrototypeModel.ADMIN.getId();
            if (username == PrototypeModel.CHEF.getUsername())
                return PrototypeModel.CHEF.getId();
            if (username == PrototypeModel.ORDER_PROCESSOR.getUsername())
                return PrototypeModel.ORDER_PROCESSOR.getId();
            /* END Prototype Code */
            return 0;
        }
    
        public User getSessionUser(int sessionId) {
            Session session = getSession(sessionId);
            return session.getUser();
        }
    
        public String getValueList(Object... args) {
            return "";
        }

        // return sessionId
        public int createSession(int sessionId, int userId) {
            return sessionId;
        }

        // return userId
        public int createUser(int userId, String username, String name, int type, int encryptedPassword, String email, int phoneNumber, int asurite) {
            return userId;
        }

        // return pizzaId
        public int createPizza(int pizzaId, int orderId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
            return pizzaId;
        }
    
        /* Database Querying */
        /* Private */
    
        // need to have similar functions for different return types
        private void sql(String query) {
            // send query to db
        }
    
        /* INSERT */
    
        private void insertTable(String table, String variableList, String valueList) {
            // e.g. INSERT INTO cars(MAKE,MODEL,ID) VALUES('Ford','F-150',101);
            sql("INSERT INTO " + table + "(" + variableList + ") VALUES(" + valueList + ");");
        }
    
        private void userInsert(int userId, String valueList) {
            final String variableList = "username,name,user_type,encrypted_password,asurite,email,phone_number,session_id";
            insertTable(Database.USER_TABLE, variableList, valueList);
        }
    
        private void sessionInsert(int sessionId, Session session) {
            final String variableList = "user_id,is_closed";
            String valueList = getValueList(session.getId(), session.getIsClosed());
        }
    
        /* UPDATE */
    
        private void updateTable(String table, String column, String value, int id) {
            // e.g. UPDATE cars set make = ford where ID = 101;
            sql("UPDATE " + table + " set " + column + " = " + value + " where ID = " + id +";");
        }
    
        private void sessionUpdate(String column, String value, int id) {
            updateTable(Database.SESSION_TABLE, column, value, id);
        }
    
        private void userUpdate(String column, String value, int id) {
            updateTable(Database.USER_TABLE, column, value, id);
        }
    
        private void orderUpdate(String column, String value, int id) {
            updateTable(Database.ORDER_TABLE, column, value, id);
        }
    
        private void pizzaUpdate(String column, String value, int id) {
            updateTable(Database.PIZZA_TABLE, column, value, id);
        }
    
        /* SELECT */
    
        /* DELETE */
    
    }   // end of Connection

}
