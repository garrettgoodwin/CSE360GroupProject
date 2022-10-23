public class User {
    /* values for guest / inexplicitly assigned values */
    public static User GUEST = new User();

    private static final int GUEST_INT = 0;
    private static final String GUEST_STRING = "GUEST";

    public static final int GUEST_ID = GUEST_INT;
    private int id;
    public static final String GUEST_USERNAME = GUEST_STRING;
    private String username;
    public static final String GUEST_NAME = GUEST_STRING;
    private String name;
    public static final int GUEST_TYPE = GUEST_INT;
    private int type;
    public static final int NO_ASURITE = 0; // don't need to be a guest to not have an asurite
    private int asurite;
    public static final String GUEST_EMAIL = "GUEST@asu.edu";
    private String email;
    public static final int GUEST_PHONE_NUMBER = GUEST_INT;
    private int phoneNumber;

    // guest
    User() {
        this.id = User.GUEST_ID;
        this.username = GUEST_USERNAME;
        this.name = GUEST_NAME;
        this.type = User.GUEST_TYPE;
        this.asurite = User.NO_ASURITE;
        this.email = User.GUEST_EMAIL;
        this.phoneNumber = User.GUEST_PHONE_NUMBER;
    }

    User(int id, String username, String name, int type, int asurite, String email, int phoneNumber) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.type = type;
        this.asurite = asurite;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /* GETTERS */

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getAsurite() {
        return asurite;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isGuest() {
        return this.type == User.GUEST_TYPE;
    }

    public boolean isCustomer() {
        return isGuest() || this.type == Customer.TYPE;
    }

    public boolean isLoggedIn() {
        return !isGuest();
    }

    public boolean isAdmin() {
        return this.type == Admin.TYPE;
    }

    public boolean isChef() {
        return this.type == Chef.TYPE;
    }

    public boolean isOrderProcessor() {
        return this.type == OrderProccessor.TYPE;
    }

    public boolean isEmployee() {
        return isOrderProcessor() || isChef() || isAdmin();
    }

    public String getTypeString() {
        switch (type) {
            case Admin.TYPE:
                return "ADMIN";
            case Chef.TYPE:
                return "CHEF";
            case OrderProccessor.TYPE:
                return "ORDER PROCESSOR";
            case Customer.TYPE:
                return "CUSTOMER";
            case User.GUEST_TYPE:
                return "GUEST";
        }
        return "NULL";
    }

}
