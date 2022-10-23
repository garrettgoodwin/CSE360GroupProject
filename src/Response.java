public class Response {
    // static responses
    // responses defined by RFC 9110
    public static final Response OK = new Response(200, "OK");
    public static final Response UNAUTHORIZED = new Response(401, "Unauthorized");
    public static final Response NOT_FOUND = new Response(404, "Not Found");
    // custom responses
    public static final Response PREEXISTING_USERNAME = new Response(452, "Username Already Exists");
    public static final Response PREEXISTING_EMAIL = new Response(453, "Email Already Exists");
    public static final Response INVALID_PASSWORD = new Response(460, "Invalid Password");

    public int code;
    public String message;
    Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean equals(Response response) {
        return this.code == response.code;
    }
}
