import java.io.BufferedWriter;  // BufferedWriter
import java.io.FileWriter;  // FileWriter
import java.io.BufferedReader;  // BufferedReader
import java.io.FileReader;  // BufferedReader

import java.io.IOException; // IO Exception
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList; // ArrayList

import java.sql.Timestamp;  // Timestamp

public class Database {

    /*
     * Debug
     */
    public static void test() {
        Connection.test();
    }

    /*
     * Public methods should be responsible for exception checking/handling
     */

    /* public application interface */
    // static

    public static Login login(String username, String password) {
        if (!usernameExists(username)) {
            return Login.deny(Response.USERNAME_NOT_FOUND);
        }

        if (!isCorrectPassword(password, username)) {
            return Login.deny(Response.INCORRECT_PASSWORD);
        }

        int userId = getUserIdFromUsername(username);
        int orderId;
        if (isCustomer(userId)) {
            orderId = createOrder(userId);
        } else {
            orderId = Order.BLANK_ID;
        }
        int sessionId = createSession(userId, orderId);
        return Login.accept(sessionId, userId, orderId);
    }

    // create customer account, session, and order; return Login
    public static Login createAccount(String username, String password, String name, String email, String phoneNumber, int asurite) {
        int type = Customer.TYPE;
        String encryptedPassword = encryptPassword(password);
        createUser(username, name, type, encryptedPassword, email, phoneNumber, asurite);
        return login(username, password);
    }

    public static Login createGuestSession() {
        int userId = createGuest();
        int sessionId = createSession(userId);
        int orderId = createOrder(userId);
        return Login.accept(sessionId, userId, orderId);
    }

    // ADMIN ONLY
    // create any type of account, return Response
    public static Response createAccount(int sessionId, String username, String password, String name, String email, String phoneNumber, int asurite, int type) {
        if (isAdmin(sessionId)) {
            return createAccount(username, password, name, email, phoneNumber, asurite, type);
        }
        return Response.UNAUTHORIZED;
    }

    // ADMIN ONLY
    // Give permissions (change type of) an account via User email
    public static Response givePermissions(int sessionId, String email, int type) {
        if (isAdmin(sessionId)) {
            return givePermissions(email, type);
        }
        return Response.UNAUTHORIZED;
    }


    // return pizzaId
    public static int createPizza(int sessionId, int orderId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        if (isAdmin(sessionId) || getSession(sessionId).getOrderId() == orderId) {
            return createPizza(orderId, pizzaType, mushrooms, olives, onions, extraCheese, quantity);
        }
        return Pizza.BLANK_ID;
    }

    // return paymentId
    public static int createPayment(int userId, String cardNumber, String exp, String cardholderName, int cvv) {
        /* id,time_created,time_updated,user_id,card_number,exp,cardholder_name,cvv */
        int paymentId = generatePaymentId();
        Connection.createPayment(paymentId, userId, cardNumber, exp, cardholderName, cvv);
        return paymentId;
    }

    public static void saveOrder(Order order, int sessionId) {
        if (isAdmin(sessionId) || getSession(sessionId).getOrderId() == order.getId()) {
            saveOrder(order);
        }
    }

    public static User getUser(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getUser(userId);
        }
        return User.GUEST;
    }

    public static User getUser(String username, String password) {
        if (isCorrectPassword(password, username)) {
            return getUserFromUsername(username);
        }
        return User.GUEST;
    }

    public static Session getSession(int sessionId) {
        return Connection.getSession(sessionId);
    }

    public static boolean usernameExists(String username) {
        return Connection.usernameExists(username);
    }

    public static boolean emailExists(String email) {
        return Connection.emailExists(email);
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

    public static String getPhoneNumber(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getPhoneNumber(userId);
        }
        return "null";
    }

    public static void setPhoneNumber(String phoneNumber, int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            setPhoneNumber(phoneNumber, userId);
        }
    }

    public static Order getOrder(int orderId) {
        return Connection.getOrder(orderId);
    }

    public static Order getLastPlacedOrder(String username, String password) {
        if (isCorrectPassword(password, username)) {
            int userId = getUserIdFromUsername(username);
            return getLastPlacedOrder(userId);
        }
        return Order.BLANK;
    }

    public static Order[] getSavedOrders(int userId, int sessionId) {
        if (hasAccessTo(sessionId, userId)) {
            return getSavedOrders(userId);
        }
        return new Order[0];  // Access Denied
    }

    public static Order[] getOrdersForProcessing(int sessionId) {
        if (isAdmin(sessionId) || isOrderProcessor(sessionId)) {
            return getOrdersForProcessing();
        }
        return new Order[0];  // Access Denied
    }

    public static Order[] getOrdersForCooking(int sessionId) {
        if (isAdmin(sessionId) || isChef(sessionId)) {
            return getOrdersForCooking();
        }
        return new Order[0];  // Access Denied
    }

    /* Order Processor */
    public static void markOrderReadyToCook(int orderId, int sessionId) {
        if (isAdmin(sessionId) || isOrderProcessor(sessionId)) {
            setOrderStatus(orderId, Order.READY_TO_COOK);
        }
    }

    /* Chef */
    public static void markOrderCooking(int orderId, int sessionId) {
        if (isAdmin(sessionId) || isChef(sessionId)) {
            setOrderStatus(orderId, Order.COOKING);
        }
    }

    /* Chef */
    public static void markOrderReady(int orderId, int sessionId) {
        if (isAdmin(sessionId) || isChef(sessionId)) {
            setOrderStatus(orderId, Order.READY);
        }
    }

    public static void markOrderPickedUp(int orderId, int sessionId) {
        if (isAdmin(sessionId) || isOrderProcessor(sessionId)) {
            setOrderStatus(orderId, Order.COMPLETE);
        }
    }

    public static String getOrderCustomerEmail(int orderId, int sessionId) {
        if (isAdmin(sessionId) || isChef(sessionId) || isOrderProcessor(sessionId)) {
            return getOrderCustomerEmail(orderId);
        }
        return User.GUEST_EMAIL;
    }

    public static String getOrderCustomerName(int orderId, int sessionId) {
        if (isAdmin(sessionId) || isChef(sessionId) || isOrderProcessor(sessionId)) {
            return getOrderCustomerName(orderId);
        }
        return User.GUEST_NAME;
    }

    public static Payment[] getSavedPaymentMethods(int userId, int sessionId) {
        if (isAdmin(sessionId) || isChef(sessionId)) {
            return getSavedPaymentMethods(userId);
        }
        return new Payment[0];  // Access Denied
    }


    /* private */
    // interface with back-end database

    private static Response createAccount(String username, String password, String name, String email, String phoneNumber, int asurite, int type) {
        String encryptedPassword = encryptPassword(password);
        createUser(username, name, type, encryptedPassword, email, phoneNumber, asurite);
        return Response.OK;
    }

    private static Response givePermissions(String email, int type) {
        return Connection.givePermissions(email, type);
    }

    // return sessionId
    private static int createSession(int userId) {
        return createSession(userId, Order.BLANK_ID);
    }

    private static int createSession(int userId, int orderId) {
        int sessionId = generateSessionId();
        Connection.createSession(sessionId, userId, orderId);
        Connection.setSessionId(sessionId, userId);
        return sessionId;
    }

    // return userId
    private static int createUser(String username, String name, int type, String encryptedPassword, String email, String phoneNumber, int asurite) {
        int userId = generateUserId();
        Connection.createUser(userId, username, name, type, encryptedPassword, email, phoneNumber, asurite);

        return userId;
    }

    // return guestId
    private static int createGuest() {
        int userId = generateUserId();
        String username = User.GUEST_USERNAME + userId;
        String name = User.GUEST_NAME + userId;
        int type = User.GUEST_TYPE;
        String encryptedPassword = "null";
        String email = User.GUEST_EMAIL + userId;
        String phoneNumber = User.GUEST_PHONE_NUMBER + userId;
        int asurite = User.NO_ASURITE;
        return createUser(username, name, type, encryptedPassword, email, phoneNumber, asurite);
    }

    // return pizzaId
    private static int createPizza(int orderId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
        int pizzaId = generatePizzaId();
        Connection.createPizza(pizzaId, orderId, pizzaType, mushrooms, olives, onions, extraCheese, quantity);

        return pizzaId;
    }

    // return orderId
    private static int createOrder(int userId) {
        int orderId = generateOrderId();
        Connection.createOrder(orderId, userId);
        return orderId;
    }

    public static void saveOrder(Order order) {
        Pizza[] pizzas = order.getPizzas();
        int orderId = order.getId();
        for (int i = 0; i < pizzas.length; i++) {
            Pizza pizza = pizzas[i];
            int pizzaType = pizza.getType();
            boolean mushrooms = pizza.hasMushrooms();
            boolean olives = pizza.hasOlives();
            boolean onions = pizza.hasOnions();
            boolean extraCheese = pizza.hasExtraCheese();
            int quantity = pizza.getQuantity();

            createPizza(orderId, pizzaType, mushrooms, olives, onions, extraCheese, quantity);
        }
    }

    public static boolean hasForbiddenCharacter(String entry) {
        return Connection.hasForbiddenCharacter(entry);
    }

    public static String encode(String entry) {
        return Connection.encode(entry);
    }

    public static String decode(String entry) {
        return Connection.decode(entry);
    }

    private static int generateUserId() {
        return Connection.generateUserId();
    }

    private static int generateSessionId() {
        return Connection.generateSessionId();
    }

    private static int generateOrderId() {
        return Connection.generateOrderId();
    }

    private static int generatePizzaId() {
        return Connection.generatePizzaId();
    }

    private static int generatePaymentId() {
        return Connection.generatePaymentId();
    }

    private static String getEncryptedPassword(int userId) {
        return Connection.getEncryptedPassword(userId);
    }

    private static boolean isCorrectPassword(String submittedPassword, String username) {
        int userId = getUserIdFromUsername(username);
        return isCorrectPassword(submittedPassword, userId);
    }

    private static boolean isCorrectPassword(String submittedPassword, int userId) {
        String encryptedSubmittedPassword = encryptPassword(submittedPassword);
        final String REAL_ENCRYPTED_PASSWORD = getEncryptedPassword(userId);

        return REAL_ENCRYPTED_PASSWORD.equals(encryptedSubmittedPassword);
    }

    private static String encryptPassword(String password) {
        String encryptedPassword = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            for (int i = 0; i < bytes.length; i++) {
                encryptedPassword += Integer.toString((bytes[i] & 0xff) + 0x100, 16);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    private static boolean isUser(int sessionId, int userId) {
        User sessionUser = getSessionUser(sessionId);
        return sessionUser.getId() == userId;
    }

    private static boolean hasAccessTo(int sessionId, int userId) {
        User sessionUser = getSessionUser(sessionId);
        return sessionUser.getId() == userId || sessionUser.isAdmin();
    }

    private static boolean isCustomer(int userId) {
        User user = getUser(userId);
        return user.isCustomer();
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
        return Connection.getSessionUser(sessionId);
    }

    /* Connection interface */

    /* User Getters + Setters */

    private static User getUser(int userId) {
        return Connection.getUser(userId);
    }

    private static User getUserFromUsername(String username) {
        int userId = getUserIdFromUsername(username);
        return getUser(userId);
    }

    private static String getUsername(int userId) {
        return Connection.getUsername(userId);
    }

    private static void setUsername(String username, int userId) {
        Connection.setUsername(username, userId);
    }

    private static String getName(int userId) {
        return Connection.getName(userId);
    }

    private static void setName(String name, int userId) {
        Connection.setName(name, userId);
    }
    
    private static int getType(int userId) {
        return Connection.getType(userId);
    }

    private static void setType(int type, int userId) {
        Connection.setType(type, userId);
    }

    private static int getAsurite(int userId) {
        return Connection.getAsurite(userId);
    }

    private static void setAsurite(int asurite, int userId) {
        Connection.setAsurite(asurite, userId);
    }

    private static String getEmail(int userId) {
        return Connection.getEmail(userId);
    }

    private static void setEmail(String email, int userId) {
        Connection.setEmail(email, userId);
    }

    private static String getPhoneNumber(int userId) {
        return Connection.getPhoneNumber(userId);
    }

    private static void setPhoneNumber(String phoneNumber, int userId) {
        Connection.setPhoneNumber(phoneNumber, userId);
    }

    private static Order getLastPlacedOrder(int userId) {
        return Connection.getLastPlacedOrder(userId);
    }

    private static Order[] getSavedOrders(int userId) {
        return Connection.getSavedOrders(userId);
    }

    private static Order[] getOrdersForProcessing() {
        return Connection.getOrdersForProcessing();
    }

    private static Order[] getOrdersForCooking() {
        Order[] readyToCook = Connection.getReadyToCookOrders();
        Order[] cooking = Connection.getCookingOrders();
        // combine arrays
        Order[] orders = new Order[readyToCook.length + cooking.length];
        for (int i = 0; i < readyToCook.length; i++) {
            orders[i] = readyToCook[i];
        }
        for (int i = 0; i < cooking.length; i++) {
            orders[readyToCook.length + i] = cooking[i];
        }
        return orders;
    }

    private static void setOrderStatus(int orderId, int status) {
        Connection.setOrderStatus(orderId, status);
    }

    private static String getOrderCustomerEmail(int orderId) {
        return Connection.getOrderCustomerEmail(orderId);
    }

    private static String getOrderCustomerName(int orderId) {
        return Connection.getOrderCustomerName(orderId);
    }

    private static Payment[] getSavedPaymentMethods(int userId) {
        return Connection.getSavedPaymentMethods(userId);
    }

    private static int getUserIdFromUsername(String username) {        
        return Connection.getUserIdFromUsername(username);
    }


    /* Connection */
    private static class Connection {

        /* Delimeters */
        private static char COLUMN_DELIMETER = ',';
        private static char ROW_DELIMETER = '\n';

        private static String DATABASE_PATH = "database/";

        /* Session Table */
        private static String SESSION_TABLE = DATABASE_PATH + "sessions.csv";
        private static String ID = "id";
        private static String TIME_CREATED = "time_created";
        private static String TIME_UPDATED = "time_updated";
        private static String USER_ID = "user_id";
        private static String ORDER_ID = "order_id";
        private static String IS_CLOSED = "is_closed";
        /* id,time_created,time_updated,user_id,order_id,is_closed */
        private static String SESSION_HEADER = delimetRow(ID,TIME_CREATED,TIME_UPDATED,USER_ID,ORDER_ID,IS_CLOSED);

        /* User Table */
        private static String USER_TABLE = DATABASE_PATH + "users.csv";
        /* id */
        /* time_created */
        /* time_updated */
        private static String USERNAME = "username";
        private static String NAME = "name";
        private static String USER_TYPE = "user_type";
        private static String ENCRYPTED_PASSWORD = "encrypted_password";
        private static String ASURITE = "asurite";
        private static String EMAIL = "email";
        private static String PHONE_NUMBER = "phone_number";
        private static String SESSION_ID = "session_id";
        /* id,time_created,time_updated,username,name,user_type,encrypted_password,asurite,email,phone_number,session_id */
        private static String USER_HEADER = delimetRow(ID,TIME_CREATED,TIME_UPDATED,USERNAME,NAME,USER_TYPE,ENCRYPTED_PASSWORD,ASURITE,EMAIL,PHONE_NUMBER,SESSION_ID);


        /* Order Table */
        private static String ORDER_TABLE = DATABASE_PATH + "orders.csv";
        /* id */
        /* time_created */
        /* time_updated */
        /* user_id */
        private static String STATUS = "status";
        private static String DELIVERY_METHOD = "delivery_method";
        private static String IS_SAVED = "is_saved";
        /* id,time_created,time_updated,user_id,status,delivery_method,is_saved */
        private static String ORDER_HEADER = delimetRow(ID,TIME_CREATED,TIME_UPDATED,USER_ID,STATUS,DELIVERY_METHOD,IS_SAVED);

        /* Pizza Table */
        private static String PIZZA_TABLE = DATABASE_PATH + "pizzas.csv";
        /* id */
        /* time_created */
        /* time_updated */
        /* order_id */
        private static String PIZZA_TYPE = "pizza_type";
        private static String MUSHROOMS = "mushrooms";
        private static String OLIVES = "olives";
        private static String ONIONS = "onions";
        private static String EXTRA_CHEESE = "extra_cheese";
        private static String QUANTITY = "quantity";
        /* id,time_created,time_updated,order_id,pizza_type,mushrooms,olives,onions,extra_cheese,quantity */
        private static String PIZZA_HEADER = delimetRow(ID,TIME_CREATED,TIME_UPDATED,ORDER_ID,PIZZA_TYPE,MUSHROOMS,OLIVES,ONIONS,EXTRA_CHEESE,QUANTITY);

        /* Payment Information Table */
        private static String PAYMENT_TABLE = DATABASE_PATH + "payments.csv";
        /* id */
        /* time_created */
        /* time_updated */
        /* user_id */
        private static String CARD_NUMBER = "card_number";
        private static String EXP = "exp";
        private static String CARDHOLDER_NAME = "cardholder_name";
        private static String CVV = "cvv";
        /* id,time_created,time_updated,user_id,card_number,exp,cardholder_name,cvv */
        private static String PAYMENT_HEADER = delimetRow(ID,TIME_CREATED,TIME_UPDATED,USER_ID,CARD_NUMBER,EXP,CARDHOLDER_NAME,CVV);

        /*
         * Debug
         */
        public static void test() {

        }

        /* database manipulation interface */

        public static User getUser(int userId) {
            String userData = selectUser(userId);
            return parseUser(userData);
        }

        public static Session getSession(int sessionId) {
            String sessionData = selectSession(sessionId);
            return parseSession(sessionData);
        }

        public static Order getOrder(int orderId) {
            String orderData = selectOrder(orderId);
            return parseOrder(orderData);
        }

        public static Pizza getPizza(int pizzaId) {
            String pizzaData = selectPizza(pizzaId);
            return parsePizza(pizzaData);
        }

        public static Payment getPayment(int paymentId) {
            String paymentData = selectPizza(paymentId);
            return parsePayment(paymentData);
        }

        public static boolean usernameExists(String username) {
            return selectAll(USERNAME, username, USER_TABLE).length > 0;
        }

        public static boolean emailExists(String email) {
            return selectAll(EMAIL, email, USER_TABLE).length > 0;
        }
        
        public static String getUsername(int userId) {
            return selectUser(USERNAME, userId);
        }
    
        public static void setUsername(String username, int userId) {
            updateUser(USERNAME, username, userId);
        }
    
        public static String getName(int userId) {
            return selectUser(NAME, userId);
        }
    
        public static void setName(String name, int userId) {
            updateUser(NAME, name, userId);
        }
        
        public static int getType(int userId) {
            return Integer.parseInt(selectUser(USER_TYPE, userId));
        }
    
        public static void setType(int type, int userId) {
            updateUser(USER_TYPE, String.valueOf(type), userId);
        }

        public static String getEncryptedPassword(int userId) {
            return selectUser(ENCRYPTED_PASSWORD, userId);
        }
    
        public static void setEncryptedPassword(String encryptedPassword, int userId) {
            updateUser(ENCRYPTED_PASSWORD, encryptedPassword, userId);
        }
    
        public static int getAsurite(int userId) {
            return Integer.parseInt(selectUser(ASURITE, userId));
        }
    
        public static void setAsurite(int asurite, int userId) {
            updateUser(ASURITE, String.valueOf(asurite), userId);
        }
    
        public static String getEmail(int userId) {
            return selectUser(EMAIL, userId);
        }
    
        public static void setEmail(String email, int userId) {
            updateUser(EMAIL, email, userId);
        }
    
        public static String getPhoneNumber(int userId) {
            return selectUser(PHONE_NUMBER, userId);
        }
    
        public static void setPhoneNumber(String phoneNumber, int userId) {
            updateUser(PHONE_NUMBER, phoneNumber, userId);
        }

        public static int getSessionId(int userId) {
            return Integer.parseInt(selectUser(SESSION_ID, userId));
        }

        public static void setSessionId(int sessionId, int userId) {
            updateUser(SESSION_ID, Integer.toString(sessionId), userId);
        }

        public static int getOrderUserId(int orderId) {
            return Integer.parseInt(selectOrder(USER_ID, orderId));
        }

        public static Order getLastOrder(int userId) {
            // all orders
            ArrayList<Order> allOrders = getUsersOrders(userId);
            if (allOrders.size() > 0) {
                return allOrders.get(0);
            } 
            // no placed order
            return Order.BLANK;
        }

        public static Order getLastPlacedOrder(int userId) {
            // all orders
            ArrayList<Order> allOrders = getUsersOrders(userId);
            for (int i = 0; i < allOrders.size(); i++) {
                if (allOrders.get(i).isPlaced()) {
                    return allOrders.get(i);
                }
            }
            // no placed order
            return Order.BLANK;
        }
    
        public static Order[] getSavedOrders(int userId) {
            // saved and unsaved orders
            ArrayList<Order> allOrders = getUsersOrders(userId);
            // rm unsaved orders
            for (int i = 0; i < allOrders.size(); i++) {
                while (!allOrders.get(i).isSaved()) {
                    allOrders.remove(i);
                }
            }
            // cast saved orders to array
            Order[] savedOrders = new Order[allOrders.size()];
            allOrders.toArray(savedOrders);
            return savedOrders;
        }

        private static ArrayList<Order> getUsersOrders(int userId) {
            String[] orderRows = selectAll(USER_ID, String.valueOf(userId), ORDER_TABLE);
            ArrayList<Order> orders = new ArrayList<Order>();
            for (int i = 0; i < orderRows.length; i++) {
                orders.add(parseOrder(orderRows[i]));
            }
            return orders;
        }

        public static Order[] getOrdersForProcessing() {
            return getOrdersByCookingStatus(Order.ACCEPTED);
        }

        public static Order[] getReadyToCookOrders() {
            return getOrdersByCookingStatus(Order.READY_TO_COOK);
        }

        public static Order[] getCookingOrders() {
            return getOrdersByCookingStatus(Order.COOKING);
        }

        private static Order[] getOrdersByCookingStatus(int status) {
            String[] orderRows = selectAll(STATUS, String.valueOf(status), ORDER_TABLE);
            Order[] orders = new Order[orderRows.length];
            for (int i = 0; i < orders.length; i++) {
                orders[i] = parseOrder(orderRows[i]);
            }
            return orders;
        }
        
        public static ArrayList<Pizza> getPizzas(int orderId) {
            String[] pizzaRows = selectAll(ORDER_ID, String.valueOf(orderId), PIZZA_TABLE);
            ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
            for (int i = 0; i < pizzaRows.length; i++) {
                pizzas.add(parsePizza(pizzaRows[i]));
            }
            return pizzas;
        }

        public static void setOrderStatus(int orderId, int status) {
            updateOrder(STATUS, Integer.toString(status), orderId);
        }

        public static Payment[] getSavedPaymentMethods(int userId) {
            String[] paymentRows = selectAll(USER_ID, String.valueOf(userId), PAYMENT_TABLE);
            Payment[] payments = new Payment[paymentRows.length];
            for (int i = 0; i < payments.length; i++) {
                payments[i] = parsePayment(paymentRows[i]);
            }
            return payments;
        }
    
        public static int getUserIdFromUsername(String username) {
            String[] users = selectAll(USERNAME, username, USER_TABLE);
            if (users.length != 1)
                return 0;
            return parseId(users[0], USER_TABLE);
        }

        public static int getUserIdFromEmail(String email) {
            String[] users = selectAll(EMAIL, email, USER_TABLE);
            if (users.length != 1)
                return 0;
            return parseId(users[0], USER_TABLE);
        }

        public static String getOrderCustomerEmail(int orderId) {
            int userId = getOrderUserId(orderId);
            User user = getUser(userId);
            return user.getEmail();
        }

        public static String getOrderCustomerName(int orderId) {
            int userId = getOrderUserId(orderId);
            User user = getUser(userId);
            return user.getName();
        }

        // change user type via email
        public static Response givePermissions(String email, int type) {
            int id = getUserIdFromEmail(email);
            if (id == 0)
                return Response.EMAIL_NOT_FOUND;
            updateUser(USER_TYPE, Integer.toString(type), id);
            return Response.OK;
        }
    
        public static User getSessionUser(int sessionId) {
            if (sessionId == Session.GUEST_SESSION) {
                return User.GUEST;
            }
            int userId = Integer.parseInt(selectSession(USER_ID, sessionId));
            return getUser(userId);
        }

        public static void createSession(int sessionId, int userId, int orderId) {
            /*id,time_created,time_updated,user_id,order_id,is_closed */

            // first close last session (set is_closed = false)
            updateAll(USER_ID, Integer.toString(userId), IS_CLOSED, Boolean.toString(true), SESSION_TABLE);

            String timeCreated = getTime();
            String timeUpdated = timeCreated;
            boolean isClosed = false;
            
            insertSession(delimetRow(Integer.toString(sessionId),timeCreated,timeUpdated,Integer.toString(userId),Integer.toString(orderId), Boolean.toString(isClosed)));
        }

        public static void createUser(int userId, String username, String name, int type, String encryptedPassword, String email, String phoneNumber, int asurite) {
            /* id,time_created,time_updated,username,name,user_type,encrypted_password,asurite,email,phone_number,session_id */
            String timeCreated = getTime();
            String timeUpdated = timeCreated;
            insertUser(delimetRow(Integer.toString(userId),timeCreated,timeUpdated,username,name,Integer.toString(type),encryptedPassword,Integer.toString(asurite),email,phoneNumber,Integer.toString(Session.GUEST_SESSION)));

        }

        public static void createOrder(int orderId, int userId) {
            /* id,time_created,time_updated,user_id,status,delivery_method,is_saved */
            String timeCreated = getTime();
            String timeUpdated = timeCreated;
            int status = Order.NOT_YET_PLACED;
            int deliveryMethod = Order.PICK_UP;
            boolean isSaved = false;
            insertOrder(delimetRow(Integer.toString(orderId),timeCreated,timeUpdated,Integer.toString(userId),Integer.toString(status),Integer.toString(deliveryMethod),Boolean.toString(isSaved)));
        }

        public static void createPizza(int pizzaId, int orderId, int pizzaType, boolean mushrooms, boolean olives, boolean onions, boolean extraCheese, int quantity) {
            /* id,time_created,time_updated,order_id,pizza_type,mushrooms,olives,onions,extra_cheese,quantity */
            String timeCreated = getTime();
            String timeUpdated = timeCreated;
            insertPizza(delimetRow(Integer.toString(pizzaId),timeCreated,timeUpdated,Integer.toString(orderId),Integer.toString(pizzaType),Boolean.toString(mushrooms),Boolean.toString(olives),Boolean.toString(onions),Boolean.toString(extraCheese),Integer.toString(quantity)));
        }

        public static void createPayment(int paymentId, int userId, String cardNumber, String exp, String cardholderName, int cvv) {
            /* id,time_created,time_updated,user_id,card_number,exp,cardholder_name,cvv */
            String timeCreated = getTime();
            String timeUpdated = timeCreated;
            insertPayment(delimetRow(Integer.toString(paymentId),timeCreated,timeUpdated,Integer.toString(userId),cardNumber,exp,cardholderName,Integer.toString(cvv)));
        }
        
        private static int generateId(String tableName) {
            // random, min, or max id
            final int MIN = 0;
            final int MAX = Integer.MAX_VALUE;
            String header = getTableHeader(tableName);
            String text = read(tableName);
            String rows = textAfterTableHeader(text);
            int largest = MIN;
            int smallest = MAX;
            // random in middle 80% of possible range
            int random = (int) (MAX*0.1 + Math.random() * MAX*0.8);
            boolean randomFound = false;
            int nextRow = rows.indexOf(ROW_DELIMETER) + 1;
            while (nextRow > 0) {
                String row = rows.substring(0, nextRow);
                int id = parseId(row, header);
                if (id > largest) {
                    largest = id;
                }
                if (id < smallest) {
                    smallest = id;
                }
                if (id == random) {
                    randomFound = true;
                }
                rows = rows.substring(nextRow);
                nextRow = rows.indexOf(ROW_DELIMETER) + 1;
            }
            // default to random
            if (!randomFound)
                return random;
            // otherwise default to filling smallest ids in
            if (smallest > 1)
                return smallest - 1;
            // otherwise create largest id
            return largest + 1;
        }

        public static int generateSessionId() {
            return generateId(SESSION_TABLE);
        }

        public static int generateUserId() {
            return generateId(USER_TABLE);
        }

        public static int generateOrderId() {
            return generateId(ORDER_TABLE);
        }

        public static int generatePizzaId() {
            return generateId(PIZZA_TABLE);
        }

        public static int generatePaymentId() {
            return generateId(PAYMENT_TABLE);
        }

        /* Private */

        private static String getTime() {
            return String.valueOf(new Timestamp(System.currentTimeMillis()));
        }

        /* Create Write Connection */
        private static BufferedWriter connectWriter(String tableName) throws IOException
        {
            return new BufferedWriter(new FileWriter(tableName));
        }

        /* Create Read Connection */
        private static BufferedReader connectReader(String tableName) throws IOException
        {
            return new BufferedReader(new FileReader(tableName));
        }

        /* Create table */
        private static void createTable(String tableName) {
            if (tableName.equals(SESSION_TABLE))
                write(SESSION_HEADER, SESSION_TABLE);
            if (tableName.equals(USER_TABLE))
                write(USER_HEADER, USER_TABLE);
            if (tableName.equals(ORDER_TABLE))
                write(ORDER_HEADER, ORDER_TABLE);
            if (tableName.equals(PIZZA_TABLE))
                write(PIZZA_HEADER, PIZZA_TABLE);
            if (tableName.equals(PAYMENT_TABLE))
                write(PAYMENT_HEADER, PAYMENT_TABLE);
        }

        /* text parsing */

        /* Read all text in table */
        private static String read(String tableName) {
            return read(tableName, false);
        }

        /* Read all text in table */
        /* create table on fail if not tried before */
        private static String read(String tableName, boolean exceptionCalled) {
            String text = "";
            try {
                BufferedReader reader = connectReader(tableName);
                // first line
                text = reader.readLine() + "\n";
                String line;
                while ((line = reader.readLine()) != null) {
                    text += line + "\n";
                }
                reader.close();
            } catch (IOException e) {
                if (exceptionCalled) {
                    createTable(tableName);
                    return read(tableName, true);
                } else {
                    e.printStackTrace();
                }
            }
            return text;
        }

        /* Replace text in table */
        private static void write(String text, String tableName) {
            try {
                BufferedWriter writer = connectWriter(tableName);
                writer.write(text);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        private static String nextColumn(String row) {
            int index = row.indexOf(COLUMN_DELIMETER);
            final int NOT_FOUND = -1;
            if (index == NOT_FOUND) {
                return "";
            }
            return row.substring(0, index);
        }

        private static String columnAt(String row, int n) {
            for (int i = 0; i < n; i++) {
                int nextDelimeter = row.indexOf(COLUMN_DELIMETER);
                if (nextDelimeter == -1) {
                    return "";
                }
                row = row.substring(nextDelimeter + 1);
            }
            // found

            // get rid of delimeters right of column
            int nextDelimeter = row.indexOf(COLUMN_DELIMETER);
            if (nextDelimeter != -1) {
                // not column in row
                row = row.substring(0, nextDelimeter);
            }
            nextDelimeter = row.indexOf(ROW_DELIMETER);
            if (nextDelimeter != -1) {
                // last column in row
                row = row.substring(0, nextDelimeter);
            }
            return row;
        }

        private static String delimetRow(String... args) {
            if (args.length == 0) {
                // this shouldn't ever occur, but-
                return String.valueOf(ROW_DELIMETER);
            }
            String row = args[0];
            for (int i = 1; i < args.length; i++) {
                // encode any delimeter arg may have
                row += COLUMN_DELIMETER + encode(args[i]);
            }
            row += ROW_DELIMETER;
            return row;
        }

        /* encode delimeters so table won't break */
        public static String encode(String column) {
            return column.replaceAll(Character.toString(COLUMN_DELIMETER), "%" + (int) COLUMN_DELIMETER).replaceAll(Character.toString(ROW_DELIMETER), "%" + (int) ROW_DELIMETER);
        }

        /* decode delimeters so table won't break */
        public static String decode(String column) {
            return column.replaceAll("%" + (int) COLUMN_DELIMETER, Character.toString(COLUMN_DELIMETER)).replaceAll("%" + (int) ROW_DELIMETER, Character.toString(ROW_DELIMETER));
        }

        public static boolean hasForbiddenCharacter(String entry) {
            return entry.indexOf(COLUMN_DELIMETER) != -1
                || entry.indexOf(ROW_DELIMETER) != -1;
        }

        /*
         * Helper for functions with tableInfo
         */
        private static boolean isTableHeader(String tableInfo) {
            return tableInfo.equals(SESSION_HEADER)
                || tableInfo.equals(USER_HEADER)
                || tableInfo.equals(ORDER_HEADER)
                || tableInfo.equals(PIZZA_HEADER)
                || tableInfo.equals(PAYMENT_HEADER);
        }

        /*
         * @param tableInfo = tableName OR tableHeader
         */
        private static String getTableHeader(String tableInfo) {
            if (isTableHeader(tableInfo)) {
                return tableInfo;
            }
            String tableName = tableInfo;

            /* Retreive Hardcoded table header */
            if (tableName.equals(SESSION_TABLE))
                return SESSION_HEADER;
            if (tableName.equals(USER_TABLE))
                return USER_HEADER;
            if (tableName.equals(ORDER_TABLE))
                return ORDER_HEADER;
            if (tableName.equals(PIZZA_TABLE))
                return PIZZA_HEADER;
            if (tableName.equals(PAYMENT_TABLE))
                return PAYMENT_HEADER;

            return "null";
        }

        /*
         * @param tableInfo - tableName OR tableInfo
         */
        private static int parseId(String row, String tableInfo) {
            String tableHeader = getTableHeader(tableInfo);
            return Integer.valueOf(parseValue(tableHeader, row, ID));
        }

        private static String parseValue(String tableHeader, String row, String column) {
            int n = columnDelimetersBeforeMatch(tableHeader, column);
            return columnAt(row, n);
        }

        private static int parseIntegerValue(String tableHeader, String row, String column) {
            return Integer.parseInt(parseValue(tableHeader, row, column));
        }

        private static boolean parseBooleanValue(String tableHeader, String row, String column) {
            return Boolean.valueOf(parseValue(tableHeader, row, column));
        }

        private static String parseRowUpdate(String tableHeader, String row, String column, String value) {
            int n = columnDelimetersBeforeMatch(tableHeader, column);
            String valuesBefore = "";
            int nextDelimeter;
            for (int i = 0; i < n; i++) {
                nextDelimeter = row.indexOf(COLUMN_DELIMETER);
                valuesBefore += row.substring(0, nextDelimeter + 1);
                row = row.substring(nextDelimeter + 1);
            }
            nextDelimeter = row.indexOf(COLUMN_DELIMETER);
            String valuesAfter;
            
            if (nextDelimeter != -1) {
                valuesAfter = row.substring(nextDelimeter);
            } else {
                valuesAfter = Character.toString(ROW_DELIMETER);
            }

            return valuesBefore + value + valuesAfter;
        }

        private static int columnDelimetersBeforeMatch(String row, String column) {
            int delimeters = 0;
            int nextDelimeter = row.indexOf(COLUMN_DELIMETER);
            int rowEnd = row.indexOf(ROW_DELIMETER);
            if (rowEnd == -1)
                rowEnd = Integer.MAX_VALUE;


            while (nextDelimeter != -1 && nextDelimeter < rowEnd) {
                String currentColumn = row.substring(0, nextDelimeter);
                if (currentColumn.equals(column)) {
                    return delimeters;
                }
                row = row.substring(nextDelimeter + 1);
                rowEnd -= (nextDelimeter + 1);
                delimeters ++;
                nextDelimeter = row.indexOf(COLUMN_DELIMETER);
            }
            // no delimeter or not found

            // remove row delimeter
            rowEnd = row.indexOf(ROW_DELIMETER);
            if (rowEnd != -1)
                row = row.substring(0, rowEnd);

            // no delimeter, only column in row is match
            if (row.equals(column)) {
                return delimeters;
            }

            // not found
            return -1;
        }

        // return number of column delimeters in row
        private static int columnDelimeters(String row) {
            int delimeters = 0;
            int rowEnd = row.indexOf(ROW_DELIMETER);
            if (rowEnd == -1)
                rowEnd = Integer.MAX_VALUE;
            int nextDelimeter = row.indexOf(COLUMN_DELIMETER);

            while (nextDelimeter != -1 && nextDelimeter < rowEnd) {
                delimeters ++;
                row = row.substring(nextDelimeter + 1);
                rowEnd -= (nextDelimeter + 1);
                nextDelimeter = row.indexOf(COLUMN_DELIMETER);
            }
            return delimeters;
        }

        // return number of column and row delimeters in text
        private static int countDelimeters(String text) {
            int nextDelimeter;
            int delimeters = 0;
            do {
                int nextColumn = text.indexOf(COLUMN_DELIMETER);
                int nextRow = text.indexOf(ROW_DELIMETER);
                nextDelimeter = Math.min(nextColumn, nextRow);
                if (nextDelimeter == -1) {
                    nextDelimeter = Math.max(nextColumn, nextRow);
                }
                if (nextDelimeter != -1) {
                    text = text.substring(nextDelimeter + 1);
                    delimeters ++;
                }
            } while (nextDelimeter != -1);
            return delimeters;
        }

        private static boolean matchingDelimeterCount(String row1, String row2) {
            return countDelimeters(row1) == countDelimeters(row2);
        }

        /* read row with matching nth entry from table */

        private static String textAfterTableHeader(String text) {
            int index = text.indexOf(ROW_DELIMETER);
            return text.substring(index + 1);
        }

        private static Session parseSession(String sessionData) {
            /* id,time_created,time_updated,user_id,order_id,is_closed */
            String header = SESSION_HEADER;
            if (!matchingDelimeterCount(sessionData, header))
                return new Session();
            int id = parseId(sessionData, header);
            int userId = parseIntegerValue(header, sessionData, USER_ID);
            User user = getUser(userId);
            Order order = getLastOrder(userId);

            return new Session(id, user, order);
        }

        private static User parseUser(String userData) {
            /* id,time_created,time_updated,username,name,user_type,encrypted_password,asurite,email,phone_number,session_id */
            String header = USER_HEADER;
            if (!matchingDelimeterCount(userData, header))
                return User.GUEST;
            int id = parseIntegerValue(header, userData, ID);
            String username = parseValue(header, userData, USERNAME);
            String name = parseValue(header, userData, NAME);
            int type = parseIntegerValue(header, userData, USER_TYPE);
            int asurite = parseIntegerValue(header, userData, ASURITE);
            String email = parseValue(header, userData, EMAIL);
            String phoneNumber = parseValue(header, userData, PHONE_NUMBER);

            return new User(id, username, name, type, asurite, email, phoneNumber);
        }

        private static Order parseOrder(String orderData) {
            /* id,time_created,time_updated,user_id,status,delivery_method,is_saved */
            String header = ORDER_HEADER;
            if (!matchingDelimeterCount(orderData, header))
                return Order.BLANK;
            int id = parseId(orderData, header); 
            ArrayList<Pizza> pizzas = getPizzas(id);
            int userId = parseIntegerValue(header, orderData, USER_ID);
            int status = parseIntegerValue(header, orderData, STATUS);
            int deliveryMethod = parseIntegerValue(header, orderData, DELIVERY_METHOD);
            boolean saved = parseBooleanValue(header, orderData, IS_SAVED);
            return new Order(id, pizzas, status, userId,deliveryMethod, saved);
        }

        private static Pizza parsePizza(String pizzaData) {
            /* id,time_created,time_updated,order_id,pizza_type,mushrooms,olives,onions,extra_cheese,quantity */
            String header = PIZZA_HEADER;

            if (!matchingDelimeterCount(pizzaData, header))
                return Pizza.BLANK_PIZZA;

            int id = parseId(pizzaData, header);
            int pizzaType = parseIntegerValue(header, pizzaData, PIZZA_TYPE);
            boolean mushrooms = parseBooleanValue(header, pizzaData, MUSHROOMS);
            boolean olives = parseBooleanValue(header, pizzaData, OLIVES);
            boolean onions = parseBooleanValue(header, pizzaData, ONIONS);
            boolean extraCheese = parseBooleanValue(header, pizzaData, EXTRA_CHEESE);
            int quantity = parseIntegerValue(header, pizzaData, QUANTITY);
            return new Pizza(id, pizzaType, mushrooms, olives, onions, extraCheese, quantity);
        }

        private static Payment parsePayment(String paymentData) {
            /* id,time_created,time_updated,user_id,card_number,exp,cardholder_name,cvv */
            String header = PAYMENT_HEADER;
            if (!matchingDelimeterCount(paymentData, header))
                return Payment.BLANK_PAYMENT;

            int userId = parseIntegerValue(header, paymentData, USER_ID);
            String cardNumber = parseValue(header, paymentData, CARD_NUMBER);
            String cardholderName = parseValue(header, paymentData, CARDHOLDER_NAME);
            String exp = parseValue(header, paymentData, EXP);
            return new Payment(userId, cardNumber, cardholderName, exp);
        }

        
        /* 
         * Queries 
         */

        /* Row Operations */

        /* insert new row into table */
        private static void insert(String row, String tableName) {
            // "INSERT into <table> (tableHeader) VALUES(row)"
            String text = read(tableName);
            String tableHeader = getTableHeader(tableName);
            if (!matchingDelimeterCount(row, tableHeader))
                return; // error - bad # of delimeters will wonk up the table

            String rows = textAfterTableHeader(text);
            rows = row + rows;
            text = tableHeader + rows;
            write(text, tableName);
        }

        /* update row in table, by id parsed from row */
        private static void update(String row, String tableName) {
            // UPDATE <table> set (tableHeader) = VALUES(row) where ID = <id>
            int id = parseId(row, tableName);
            /* Delete Row */
            delete(id, tableName);
            /* Insert New Row */
            insert(row, tableName);
        }

        /* select row from table */
        private static String select(int id, String tableName) {
            // SELECT * from <table> where ID = <id>
            String text = read(tableName);

            String tableHeader = getTableHeader(tableName);

            String rows = textAfterTableHeader(text);

            int n = columnDelimetersBeforeMatch(tableHeader, ID);
            int nextRow = rows.indexOf(ROW_DELIMETER) + 1;
            while (nextRow > 0) {
                int rowId = Integer.parseInt(columnAt(rows, n));
                if (rowId == id) {
                    return rows.substring(0, nextRow);
                }
                rows = rows.substring(nextRow);
                nextRow = rows.indexOf(ROW_DELIMETER) + 1;
            }
            // find matching id
            return "null";
        }

        /* delete row from table, by column */
        private static void delete(String column, String value, String tableName) {
            String text = read(tableName);
            String tableHeader = getTableHeader(tableName);

            String rows = textAfterTableHeader(text);

            int n = columnDelimetersBeforeMatch(tableHeader, column);
            int nextRow = rows.indexOf(ROW_DELIMETER) + 1;
            String rowsBefore = "";
            while (nextRow > 0) {
                String rowValue = columnAt(rows, n);
                // terminal case : row found
                if (rowValue.equals(value)) {
                    String rowsAfter = rows.substring(nextRow);
                    text = tableHeader + rowsBefore + rowsAfter;
                    write(text, tableName);
                    return;
                }
                // not correct row
                rowsBefore += rows.substring(0, nextRow);
                rows = rows.substring(nextRow);
                nextRow = rows.indexOf(ROW_DELIMETER) + 1;
            }
            // row not found - not deleted
        }

        /* delete row from table, by id */
        private static void delete(int id, String tableName) {
            delete(ID, Integer.toString(id), tableName);
        }

        /* Column Operations */

        /* update column in table */
        private static void update(String column, String value, int id, String tableName) {
            // UPDATE <table> set <column> = <value> where ID = <id>
            String row = select(id, tableName);
            String tableHeader = getTableHeader(tableName);
            String newRow = parseRowUpdate(tableHeader, row, column, value);
            // update time_updated
            if (!column.equals(TIME_UPDATED)) {
                newRow = parseRowUpdate(tableHeader, newRow, TIME_UPDATED, getTime());
            }
            update(newRow, tableName);
        }

        /* select column in table */
        private static String select(String column, int id, String tableName) {
            // SELECT <column> from <table> where ID = <id>
            String row = select(id, tableName);
            String tableHeader = getTableHeader(tableName);
            return parseValue(tableHeader, row, column);
        }

        /* Multi-Row Operations */

        /* select all rows in table */
        private static String[] selectAll(String column, String value, String tableName) {
            // SELECT * from <table> where <column> = <value>

            ArrayList<String> rowsList = new ArrayList<String>();

            String text = read(tableName);

            String tableHeader = getTableHeader(tableName);

            text = textAfterTableHeader(text);

            int n = columnDelimetersBeforeMatch(tableHeader, column);
            int nextRow = text.indexOf(ROW_DELIMETER) + 1;
            while (nextRow > 0) {
                String rowValue = columnAt(text, n);
                if (rowValue.equals(value)) {
                    rowsList.add(text.substring(0, nextRow));
                }
                text = text.substring(nextRow);
                nextRow = text.indexOf(ROW_DELIMETER) + 1;
            }


            String[] rows = new String[rowsList.size()];
            return rowsList.toArray(rows);
        }

        /* Update all rows in table */
        private static void updateAll(String selectColumn, String selectValue, String changeColumn, String changeValue, String tableName) {
            /* Update * rows in tableName where selectColumn=selectValue set changeColumn=changeValue */

            // first select all rows
            String[] rows = selectAll(selectColumn, selectValue, tableName);

            // for each row
            for (int i = 0; i < rows.length; i++) {
                // get row id
                int id = parseId(rows[i], tableName);
                // update row
                update(changeColumn, changeValue, id, tableName);
            }
        }

        /* INSERT */

        /* Insert Row */

        private static void insertSession(String row) {
            insert(row, SESSION_TABLE);
        }

        private static void insertUser(String row) {
            insert(row, USER_TABLE);
        }

        private static void insertOrder(String row) {
            insert(row, ORDER_TABLE);
        }

        private static void insertPizza(String row) {
            insert(row, PIZZA_TABLE);
        }

        private static void insertPayment(String row) {
            insert(row, PAYMENT_TABLE);
        }

        /* UPDATE */
    
        /* Update Column */

        private static void updateSession(String column, String value, int id) {
            update(column, value, id, SESSION_TABLE);
        }
    
        private static void updateUser(String column, String value, int id) {
            update(column, value, id, USER_TABLE);
        }
    
        private static void updateOrder(String column, String value, int id) {
            update(column, value, id, ORDER_TABLE);
        }
    
        private static void updatePizza(String column, String value, int id) {
            update(column, value, id, PIZZA_TABLE);
        }

        private static void updatePayment(String column, String value, int id) {
            update(column, value, id, PAYMENT_TABLE);
        }

        /* Update Row */

        private static void updateSession(String row) {
            update(row, SESSION_TABLE);
        }
    
        private static void updateUser(String row) {
            update(row, USER_TABLE);
        }
    
        private static void updateOrder(String row) {
            update(row, ORDER_TABLE);
        }
    
        private static void updatePizza(String row) {
            update(row, PIZZA_TABLE);
        }

        private static void updatePayment(String row) {
            update(row, PAYMENT_TABLE);
        }
    
        /* SELECT */

        /* Select Column */

        private static String selectSession(String column, int id) {
            return select(column, id, SESSION_TABLE);
        }
    
        private static String selectUser(String column, int id) {
            return select(column, id, USER_TABLE);
        }

        private static String selectOrder(String column, int id) {
            return select(column, id, ORDER_TABLE);
        }

        private static String selectPizza(String column, int id) {
            return select(column, id, PIZZA_TABLE);
        }

        private static String selectPayment(String column, int id) {
            return select(column, id, PAYMENT_TABLE);
        }

        /* Select Row */

        private static String selectSession(int id) {
            return select(id, SESSION_TABLE);
        }
    
        private static String selectUser(int id) {
            return select(id, USER_TABLE);
        }

        private static String selectOrder(int id) {
            return select(id, ORDER_TABLE);
        }

        private static String selectPizza(int id) {
            return select(id, PIZZA_TABLE);
        }

        private static String selectPayment(int id) {
            return select(id, PAYMENT_TABLE);
        }
    
        /* DELETE */

        // delete by column
        private static void deleteSession(String column, String value) {
            delete(column, value, SESSION_TABLE);
        }

        private static void deleteUser(String column, String value) {
            delete(column, value, USER_TABLE);
        }

        private static void deleteOrder(String column, String value) {
            delete(column, value, ORDER_TABLE);
        }

        private static void deletePizza(String column, String value) {
            delete(column, value, PIZZA_TABLE);
        }

        private static void deletePayment(String column, String value) {
            delete(column, value, PAYMENT_TABLE);
        }

        // delete by id

        private static void deleteSession(int id) {
            delete(id, SESSION_TABLE);
        }
    
        private static void deleteUser(int id) {
            delete(id, USER_TABLE);
        }

        private static void deleteOrder(int id) {
            delete(id, ORDER_TABLE);
        }

        private static void deletePizza(int id) {
            delete(id, PIZZA_TABLE);
        }

        private static void deletePayment(int id) {
            delete(id, PAYMENT_TABLE);
        }
    
    }   // end of Connection

}
