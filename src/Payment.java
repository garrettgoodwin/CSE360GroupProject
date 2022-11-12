// import java.lang.reflect.InvocationTargetException;
// import java.sql.Timestamp; // Timestamp
// import java.time.LocalDate;
// import java.util.Calendar;

// public class Payment {
//     private int userId;
//     private String type;
//     private String cardholderName;
//     private String exp;
//     private String lastFourDigits;
    
//     /* MII types */
//     private static int VISA_DIGIT = 4;
//     private static String VISA = "Visa";
//     private static int MASTERCARD_DIGIT = 5;
//     private static String MASTERCARD = "Mastercard";
//     private static int DISCOVER_DIGIT = 6;
//     private static String DISCOVER = "Discover";
//     private static String UNKNOWN_TYPE = "Unknown Type";

//     Payment() {
//         this(User.GUEST_ID, "0000000123456789", "John Cardholder", "01/01");
//     }
//     Payment(int userId, String cardNumber, String cardholderName, String exp) {
//         this(userId, getType(cardNumber), cardholderName, exp, getLastFourDigits(cardNumber));
//     }
//     private Payment(int userId, String type, String cardholderName, String exp, String lastFourDigits) {
//         this.userId = userId;
//         this.type = type;
//         this.cardholderName = cardholderName;
//         this.exp = exp;
//         this.lastFourDigits = lastFourDigits;
//     }

//     // getters
//     public int getUserId() {
//         return userId;
//     }
//     public String getType() {
//         return type;
//     }
//     public String getCardholderName() {
//         return cardholderName;
//     }
//     public String getExp() {
//         return exp;
//     }
//     public String getLastFourDigits() {
//         return lastFourDigits;
//     }
//     public String getProtectedCardNumber() {
//         return "************" + lastFourDigits;
//     }

//     public static Response validate(String cardNumber, String exp, String cvv) {
//         if (cardNumber.length() != 16 || !isNaturalNumber(cardNumber)) {
//             return Response.INVALID_CARDNUMBER;
//         }
//         if (isExpired(exp)) {
//             return Response.CARD_EXPIRED;
//         }
//         if (cvv.length() != 3 || !isNaturalNumber(cvv)) {
//             return Response.INVALID_CVV;
//         }
//         return Response.OK;
//     }

//     private static boolean isExpired(String exp) {
//         /* Test that exp is valid, get month and date */
//         final String MODEL = "mm/yy";
//         if (exp.length() != MODEL.length())
//             return true;    // length mismatch
//         final char MONTH_YEAR_DELIMETER = '/';
//         int delimeterIndex = exp.indexOf(MONTH_YEAR_DELIMETER);
//         if (delimeterIndex != MODEL.indexOf(MONTH_YEAR_DELIMETER))
//             return true;    // format mismatch

//         // month - mm
//         String month = exp.substring(0, delimeterIndex);
//         // year - yyyy
//         String year = "20" + exp.substring(delimeterIndex + 1);

//         if (!isNaturalNumber(month) || !isNaturalNumber(year))
//             return true;    // NaN

//         LocalDate today = LocalDate.now();

//         // month - mm
//         // year - yyyy
//         // day - dd
//         String day = Integer.toString(today.getDayOfMonth());
//         if (day.length() == 1) {
//             day = "0" + day;
//         }
//         // format - yyyy-mm-dd
//         final char delimeter = '-';
//         String expDate = year+delimeter+month+delimeter+day;
//         LocalDate expiration = LocalDate.parse(expDate);
        
//         return expiration.compareTo(today) <= 0;
//     }
//     private static boolean isNaturalNumber(String num) {
//         try {
//             // is a natural number
//             // long - credit card's 16 digits > Integer.MAX_VALUE
//             return Long.parseLong(num) > 0;
//         } catch (Exception e) {
//             // NaN
//             return false;
//         }
//     }
//     private static String getType(String cardNumber) {
//         int mii = getFirstDigit(cardNumber);
//         if (mii == VISA_DIGIT)
//             return VISA;
//         if (mii == MASTERCARD_DIGIT)
//             return MASTERCARD;
//         if (mii == DISCOVER_DIGIT)
//             return DISCOVER;
//         return UNKNOWN_TYPE;
//     }
//     private static int getFirstDigit(String cardNumber) {
//         return Character.getNumericValue(cardNumber.charAt(0));
//     }
//     private static String getLastFourDigits(String cardNumber) {
//         if (cardNumber.length() == 16) {
//             return cardNumber.substring(12);
//         }
//         return "0000";
//     }
// }
