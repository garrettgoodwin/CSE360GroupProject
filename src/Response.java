public class Response {
    /* Responses */
    /* responses defined by RFC 9110 */
    public static final Response OK = new Response(200, "OK");
    public static final Response UNAUTHORIZED = new Response(401, "Unauthorized");
    public static final Response NOT_FOUND = new Response(404, "Not Found");
    /* custom responses */
    /* 45x - preexisting */
    public static final Response PREEXISTING_USERNAME = new Response(452, "Username Already Exists");
    public static final Response PREEXISTING_EMAIL = new Response(453, "Email Already Exists");
    /* 46x - invalid */
    public static final Response INVALID_CHAR = new Response(460, "Invalid Character");
    public static final Response INVALID_EMAIL = new Response(461, "Invalid Email");
    public static final Response INVALID_CARDNUMBER = new Response(468, "Invalid Card Number");
    public static final Response INVALID_CVV = new Response(469, "Invalid CVV");
    /* 48x - misc. */
    public static final Response MISSING_CHAR = new Response(480, "Missing Required Character");
    public static final Response SHORT_ENTRY = new Response(481, "Entry is Too Short");
    public static final Response LONG_ENTRY = new Response(482, "Entry is Too Long");
    public static final Response SHORT_PASSWORD = new Response(483, "Password Must be at Least " + User.Password.MIN_LENGTH + " Characters");
    public static final Response LONG_PASSWORD = new Response(484, "Password Must be at Most " + User.Password.MAX_LENGTH + " Characters");
    public static final Response CARD_EXPIRED = new Response(485, "Credit Card is Expired.");

    public int code;
    public String message;
    // no creating responses dynamically - add to static Responses 
    private Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean equals(Response response) {
        return this.code == response.code;
    }
    public static boolean ok(Response response) {
        return response == OK;
    }
}
