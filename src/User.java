import java.util.regex.Pattern;   // regex

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
    public static final String GUEST_TYPE_TEXT = "Guest";
    private int type;
    public static final int NO_ASURITE = 0; // don't need to be a guest to not have an asurite
    private int asurite;
    public static final String GUEST_EMAIL = "GUEST@asu.edu";
    private String email;
    public static final String GUEST_PHONE_NUMBER = GUEST_STRING;
    private String phoneNumber;

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

    User(int id, String username, String name, int type, int asurite, String email, String phoneNumber) {
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

    public String getFirstName() {
        String firstName = getName();
        int index;
        index = firstName.indexOf(' ');
        if (index != -1) {
            firstName = firstName.substring(0, index);
        }
        index = firstName.indexOf('-');
        if (index != -1) {
            firstName = firstName.substring(0, index);
        }
        return firstName;
    }

    public int getAsurite() {
        return asurite;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // parse phone # x+xxx-xxx-xxxx
    public String getPhoneNumberString() {
        String phoneNumber = getPhoneNumber();
        if (phoneNumber.length() < 10)
            return phoneNumber;
        
        String parsed = "";
        int digits;
        int i = phoneNumber.length();

        /*
         * parse string from back to front (last 4, middle 3, front 3, (optional) country code)
         */

        // last 4 digits
        digits = 4;
        parsed = phoneNumber.substring(i-digits, i);
        i -= digits;

        // middle 3 digits
        digits = 3;
        parsed = phoneNumber.substring(i-digits, i) + "-" + parsed;
        i -= digits;

        // digits = 3
        // area code / front 3 digits
        parsed = phoneNumber.substring(i-digits, i) + "-" + parsed;
        i -= digits;

        // country code
        if (i > 0) {
            parsed = phoneNumber.substring(0, i) + "+" + parsed;
        }
        return parsed;
    }

    public boolean isGuest() {
        return this.type == User.GUEST_TYPE;
    }

    public boolean isLoggedIn() {
        return !isGuest();
    }
    
    public boolean isCustomer() {
        return isGuest() || this.type == Customer.TYPE;
    }

    public boolean isAdmin() {
        return this.type == Admin.TYPE;
    }

    public boolean isChef() {
        return this.type == Chef.TYPE;
    }

    public boolean isOrderProcessor() {
        return this.type == OrderProcessor.TYPE;
    }

    public boolean isEmployee() {
        return isOrderProcessor() || isChef() || isAdmin();
    }

    public String getTypeString() {
        switch (type) {
            case Admin.TYPE:
                return Admin.TYPE_TEXT;
            case Chef.TYPE:
                return Chef.TYPE_TEXT;
            case OrderProcessor.TYPE:
                return OrderProcessor.TYPE_TEXT;
            case Customer.TYPE:
                return Customer.TYPE_TEXT;
            case User.GUEST_TYPE:
                return User.GUEST_TYPE_TEXT;
        }
        return "NULL";
    }

    public static int textToType(String typeText) {
        switch (typeText) {
            case Admin.TYPE_TEXT:
                return Admin.TYPE;
            case Chef.TYPE_TEXT:
                return Chef.TYPE;
            case OrderProcessor.TYPE_TEXT:
                return OrderProcessor.TYPE;
            case Customer.TYPE_TEXT:
                return Customer.TYPE;
            case User.GUEST_TYPE_TEXT:
                return User.GUEST_TYPE;
        }
        return -1;
    }

    /* Static */

    /* Valid Data Creation */

    public static class Password {
        public static final int MIN_LENGTH = 8;
        public static final int MAX_LENGTH = 80;
        public static String SPECIAL_CHARACTERS = "!@#$%^&*()_+-={}[]|\\:;\"'<>.?";
        public static Response validate(String password) {

            if (password.length() < MIN_LENGTH)
                return Response.SHORT_PASSWORD;

            if (password.length() > MAX_LENGTH)
                return Response.LONG_PASSWORD;

            /* one upper case, one lower case, one number, one special character */
            String upperCaseRegex = "[A-Z]";
            String lowerCaseRegex = "[a-z]";
            String numberRegex = "[0-9]";

            boolean upper = false;
            boolean lower = false;
            boolean number = false;
            boolean special = false;

            /* includes necessary characters */
            /* only includes allowed characters */
            for (int i = 0; i < password.length(); i++) {
                String s = String.valueOf(password.charAt(i));
                if (Pattern.matches(upperCaseRegex, s)) {
                    upper = true;
                } else if (Pattern.matches(lowerCaseRegex, s)) {
                    lower = true;
                } else if (Pattern.matches(numberRegex, s)) {
                    number = true;
                } else if (SPECIAL_CHARACTERS.contains(s)) {
                    special = true;
                } else {
                    // is not valid character
                    return Response.FORBIDDEN_PASSWORD_CHARACTER;
                }
            }
            if (!upper || !lower || !number || !special) {
                // does not include ≥1 upper, lower, number, and special character
                return Response.PASSWORD_MISSING_NEEDED_CHARACTER;
            }
            
            // all tests passed
            return Response.OK;
        }

        public static boolean isPasswordResponse(Response response) {
            return response == Response.FORBIDDEN_PASSWORD_CHARACTER
                || response == Response.INCORRECT_PASSWORD
                || response == Response.PASSWORD_MISSING_NEEDED_CHARACTER;
        }

    }

    public static class Username {
        public static final int MIN_LENGTH = 3;
        public static final int MAX_LENGTH = 25;
        public static Response validate(String username) {

            if (username.length() < MIN_LENGTH)
                return Response.SHORT_USERNAME;

            if (username.length() > MAX_LENGTH)
                return Response.LONG_USERNAME;

            if (Database.usernameExists(username))
                return Response.PREEXISTING_USERNAME;
                
            // invalid character
            // allowed characters: a-zA-Z0-9 -_
            String allowedCharactersRegex = "[a-zA-Z0-9_-]";
            for (int i = 0; i < username.length(); i++) {
                String s = String.valueOf(username.charAt(i));
                if (!Pattern.matches(allowedCharactersRegex, s))
                    return Response.FORBIDDEN_USERNAME_CHARACTER;
            }

            // all tests passed
            return Response.OK;
        }

        public static boolean isUsernameResponse(Response response) {
            return response == Response.USERNAME_NOT_FOUND
                || response == Response.SHORT_USERNAME
                || response == Response.LONG_USERNAME
                || response == Response.PREEXISTING_USERNAME
                || response == Response.FORBIDDEN_USERNAME_CHARACTER;
        }
    }

    public static class Email {
        public static Response validate(String email) {
            String validEmailRegex = "[A-Za-z][A-Za-z0-9\\.]{0,}@[A-Za-z0-9]{1,}\\.([A-Za-z0-9]\\.)*[A-Za-z0-9]{1,}";
            if (!Pattern.matches(validEmailRegex, email)) {
                return Response.INVALID_EMAIL;
            }

            if (Database.emailExists(email))
                return Response.PREEXISTING_EMAIL;

            // all tests passed
            return Response.OK;
        }

        public static void sendEmail(String email, String subject, String body) {
            /* Send Email */
        }

        public static boolean isEmailResponse(Response response) {
            return response == Response.EMAIL_NOT_FOUND
                || response == Response.INVALID_EMAIL
                || response == Response.PREEXISTING_EMAIL;
        }
    }

    public static class Name {
        public static int MIN_LENGTH = 3;
        public static int MAX_LENGTH = 80;
        public static Response validate(String name) {
            if (name.length() < MIN_LENGTH)
                return Response.SHORT_NAME;
            if (name.length() > MAX_LENGTH)
                return Response.LONG_NAME;

            // note: space character included
            String validNameRegex = "[A-Za-z\\- ]*";
            if (!Pattern.matches(validNameRegex, name)) {
                return Response.FORBIDDEN_NAME_CHARACTER;
            }
            return Response.OK;
        }
        public static boolean isNameResponse(Response response) {
            return response == Response.FORBIDDEN_NAME_CHARACTER
                || response == Response.SHORT_NAME
                || response == Response.LONG_NAME;
        }
    }

    public static class Asurite {
        public static Response validate(String asurite) {
            if (asurite.length() > 0) {
                try {
                    if (Integer.parseInt(asurite) >= 0) {
                        return Response.OK;
                    }
                } catch (Exception e) {
                    return Response.INVALID_ASURITE;
                }
            }
            return Response.OK;
        }
        public static boolean isAsuriteResponse(Response response) {
            return response == Response.INVALID_ASURITE;
        }
    }

    public static class PhoneNumber {
        public static Response validate(String phoneNumber) {
            try {
                Long.parseLong(phoneNumber);
            } catch (Exception e) {
                return Response.INVALID_PHONE_NUMBER;
            }
            return Response.OK;
        }
        public static boolean isPhoneNumberResponse(Response response) {
            return response == Response.INVALID_PHONE_NUMBER;
        }
    }

    @Override
    public String toString() {
        String str = "User #" + getId() + "\n\n";
        str += "username: " + getUsername() + "\n";
        str += "name: " + getName() + "\n";
        str += "type: " + getTypeString() + "\n";
        str += "asurite: " + getAsurite() + "\n";
        str += "email: " + getEmail() + "\n";
        str += "phoneNumber: " + getPhoneNumberString();
        return str;
    }

}
