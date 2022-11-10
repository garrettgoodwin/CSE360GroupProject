public class Login {
    private Response response;
    public int sessionId;
    public int userId;

    Login(Response response, int sessionId, int userId) {
        this.response = response;
        this.sessionId = sessionId;
        this.userId = userId;
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
        return new Login(response, Session.GUEST_SESSION, User.GUEST_ID);
    }

    public static Login accept(int sessionId, int userId) {
        return new Login(Response.OK, sessionId, userId);
    }
}
