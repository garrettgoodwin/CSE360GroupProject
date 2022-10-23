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


    Database() {
        // initiate connection to database
    }

    /*
     * Public methods should be responsible for exception checking/handling
     */

    /* public */

    public Login login(String username, String password) {
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
    public Login createAccount(String username, String password, String name, String email, int phoneNumber, int asurite) {
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

    public User getUser(int userId, int sessionId) {
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
        if (userId == getSession(sessionId).getUser().getId()) {
            return getUser(userId);
        }
        return new User();
    }

    public Session getSession(int sessionId) {
        return new Session();
    }

    public boolean usernameExists(String username) {
        return true;
    }

    public boolean emailExists(String email) {
        return true;
    }

    public String getUsername(int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            return getUsername(userId);
        }
        return "no";
    }

    public void setUsername(String username, int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            setUsername(username, userId);
        }
    }
    
    public String getName(int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            return getName(userId);
        }
        return "name";
    }

    public void setName(String name, int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            setName(name, userId);
        }
    }
    
    public int getType(int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            return getType(userId);
        }
        return 0;
    }

    public void setType(int type, int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            setType(type, userId);
        }
    }

    public int getAsurite(int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            return getAsurite(userId);
        }
        return 0;
    }

    public void setAsurite(int asurite, int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            setAsurite(asurite, userId);
        }
    }

    public String getEmail(int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            return getEmail(userId);
        }
        return "";
    }

    public void setEmail(String email, int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            setEmail(email, userId);
        }
    }

    public int getPhoneNumber(int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            return getPhoneNumber(userId);
        }
        return 0;
    }

    public void setPhoneNumber(int phoneNumber, int userId, int sessionId) {
        if (hasAuthorityOver(sessionId, userId)) {
            setPhoneNumber(phoneNumber, userId);
        }
    }

    /* private */

    private User getUser(int userId) {
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

        int id = userId;
        String username = getUsername(userId);
        String name = getName(userId);
        int type = getType(userId);
        int asurite = getAsurite(userId);
        String email = getEmail(userId);
        int phoneNumber = getPhoneNumber(userId);
        return new User(id, username, name, type, asurite, email, phoneNumber);
    }

    private String getUsername(int userId) {
        return "username";
    }

    private void setUsername(String username, int userId) {
        userUpdate(Database.USERNAME, username, userId);
    }

    private String getName(int userId) {
        return "name";
    }

    private void setName(String name, int userId) {
        userUpdate(Database.NAME, name, userId);
    }
    
    private int getType(int userId) {
        return 0;
    }

    private void setType(int type, int userId) {
        userUpdate(Database.USER_TYPE, String.valueOf(type), userId);
    }

    private int getAsurite(int userId) {
        return 0;
    }

    private void setAsurite(int asurite, int userId) {
        userUpdate(Database.ASURITE, String.valueOf(asurite), userId);
    }

    private String getEmail(int userId) {
        return "";
    }

    private void setEmail(String email, int userId) {
        userUpdate(Database.EMAIL, email, userId);
    }

    private int getPhoneNumber(int userId) {
        return 0;
    }

    private void setPhoneNumber(int phoneNumber, int userId) {
        userUpdate(Database.PHONE_NUMBER, String.valueOf(phoneNumber), userId);
    }

    private int getUserIdFromUsername(String username) {
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

    private User getSessionUser(int sessionId) {
        Session session = getSession(sessionId);
        return session.getUser();
    }

    // return sessionId
    private int createSession(int userId) {
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
        User user = getUser(userId);
        // create session
        Session session = new Session(sessionId, user, Order.BLANK);
        sessionInsert(sessionId, session);
        return sessionId;
    }

    // return userId
    private int createUser(String username, String name, int type, int encryptedPassword, String email, int phoneNumber, int asurite) {
        int userId = generateRandomId();
        userInsert(userId, getValueList(username, name, type, encryptedPassword, email, phoneNumber, asurite));
        return userId;
    }

    private int generateRandomId() {
        return 12;  // I feel like 12 is pretty random. I just thought of it at the top of my head
    }

    private int getEncryptedPassword(int userId) {
        return 0;
    }

    private boolean isCorrectPassword(String submittedPassword, int userId) {
        /* comment out */
        return true;
        /* uncomment out below */
        // int encryptedSubmittedPassword = encryptPassword(submittedPassword);
        // final int REAL_ENCRYPTED_PASSWORD = getEncryptedPassword(userId);
        // return REAL_ENCRYPTED_PASSWORD == encryptedSubmittedPassword;
    }

    private int encryptPassword(String password) {
        int encryptedPassword = 0;
        return encryptedPassword;
    }

    private boolean hasAuthorityOver(int sessionId, int userId) {
        User sessionUser = getSessionUser(sessionId);
        return sessionUser.getId() == userId || sessionUser.isAdmin();
    }

    protected void finalize() {
        System.out.println("Goodbye");
    }

    private String getValueList(Object... args) {
        return "";
    }

    /* Database Connection */

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

}
