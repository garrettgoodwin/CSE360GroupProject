public class Login {
    private Response response;
    public int sessionId;
    public int userId;
    public int orderId;

    Login(Response response, int sessionId, int userId, int orderId) {
        this.response = response;
        this.sessionId = sessionId;
        this.userId = userId;
        this.orderId = orderId;
    }

    public boolean isAccepted() {
        return Response.ok(response);
    }

    public Response getResponse() {
        return this.response;
    }

    public String getResponseMessage() {
        return this.response.message;
    }

    public static Login deny(Response response) {
        return new Login(response, Session.GUEST_SESSION, User.GUEST_ID, Order.BLANK_ID);
    }

    public static Login accept(int sessionId, int userId, int orderId) {
        return new Login(Response.OK, sessionId, userId, orderId);
    }
}
